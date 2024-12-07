package clinic.service;

import clinic.model.Appointment;
import clinic.model.Doctor;
import clinic.model.Patient;

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

    public AppointmentService(PatientService patientService, DoctorService doctorService, ScheduleService scheduleService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.scheduleService = scheduleService;
        this.appointments = new ArrayList<>();
    }

    // Tworzenie wizyty
    public void createAppointment(String patientPesel, String doctorId, LocalDate date, LocalTime time) {
        Optional<Patient> patientOptional = patientService.findPatientByPesel(patientPesel);
        Optional<Doctor> doctorOptional = doctorService.findDoctorById(doctorId);

        if (patientOptional.isEmpty() || doctorOptional.isEmpty()) {
            throw new IllegalArgumentException("Patient or doctor not found.");
        }

        Patient patient = patientOptional.get();
        Doctor doctor = doctorOptional.get();

        boolean isAvailable = scheduleService.isDoctorAvailable(doctorId, date, time);
        if (!isAvailable) {
            throw new IllegalArgumentException("Doctor is not available at the specified time.");
        }

        for (Appointment existingAppointment : appointments) {
            if (existingAppointment.getDoctor().equals(doctor)
                    && existingAppointment.getDate().equals(date)
                    && existingAppointment.getTime().equals(time)) {
                throw new IllegalArgumentException("Doctor already has an appointment at this time.");
            }
        }

        Appointment newAppointment = new Appointment(patient, doctor, date, time);
        appointments.add(newAppointment);
    }

    // Pobieranie wszystkich wizyt
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    // Pobieranie wizyt dla danego lekarza w danym dniu
    public List<Appointment> getAppointmentsByDoctorAndDate(String doctorId, LocalDate date) {
        return appointments.stream()
                .filter(appointment -> appointment.getDoctor().getId().equals(doctorId) && appointment.getDate().equals(date))
                .toList();
    }
}
