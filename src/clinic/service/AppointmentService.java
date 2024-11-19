package clinic.service;

import clinic.model.Appointment;
import clinic.model.Doctor;
import clinic.model.Patient;
import clinic.model.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppointmentService {
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final ScheduleService scheduleService;
    private final List<Appointment> appointments;

    public AppointmentService(PatientService patientService, DoctorService doctorService, ScheduleService scheduleService ) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.scheduleService = scheduleService;
        this.appointments = new ArrayList<>();
    }

    public void createAppointment(String patientPesel, String doctorId, LocalDate date, LocalTime time) {
        Optional<Patient> patientOptional = patientService.findPatientByPesel(patientPesel);
        Optional<Doctor> doctorOptional = doctorService.findDoctorById(doctorId);
        List<Schedule> schedules = scheduleService.getSchedulesByDoctorId(doctorId);
        List<Appointment> appointments = this.appointments.stream()
                .filter(a -> a.getDoctor().getId().equals(doctorId) && a.getDate().equals(date))
                .toList();

        if (patientOptional.isPresent() && doctorOptional.isPresent()) {
            Patient patient = patientOptional.get();
            Doctor doctor = doctorOptional.get();

            boolean isDoctorAvailable = schedules.stream()
                    .anyMatch(schedule -> schedule.isAvailable(date, time));

            boolean isDoctorFree = appointments.stream()
                    .noneMatch(appointment -> appointment.getTime().equals(time));

            if (isDoctorAvailable && isDoctorFree) {
                Appointment appointment = new Appointment(patient, doctor, date, time);
                this.appointments.add(appointment);
                schedules.forEach(schedule -> schedule.setBusy(date, time));
            } else {
                throw new IllegalArgumentException("Doctor is not available at this time.");
            }
        } else {
            throw new IllegalArgumentException("Patient or doctor not found.");
        }
    }

    public List<Appointment> getAppointmentsByDoctorAndDate(String doctorId, LocalDate date) {
        return appointments.stream()
                .filter(appointment -> appointment.getDoctor().getId().equals(doctorId) && appointment.getDate().equals(date))
                .toList();
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }
}