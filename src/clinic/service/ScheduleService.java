package clinic.service;

import clinic.model.Doctor;
import clinic.model.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScheduleService {
    private final DoctorService doctorService;
    private final List<Schedule> schedules;

    public ScheduleService(DoctorService doctorService) {
        this.doctorService = doctorService;
        this.schedules = new ArrayList<>();
    }

    public void createSchedule(String doctorId, LocalDate date, LocalTime start, LocalTime end) {
        Optional<Doctor> doctorOptional = doctorService.findDoctorById(doctorId);
        if (doctorOptional.isEmpty()) {
            throw new IllegalArgumentException("Doctor with ID " + doctorId + " not found.");
        }

        Doctor doctor = doctorOptional.get();
        Schedule schedule = new Schedule(doctor);
        schedule.addAvailability(date, start, end);
        schedules.add(schedule);
    }

    public boolean isDoctorAvailable(String doctorId, LocalDate date, LocalTime time) {
        return schedules.stream()
                .filter(schedule -> schedule.getDoctor().getId().equals(doctorId))
                .anyMatch(schedule -> schedule.isAvailable(date, time));
    }

    public List<Schedule> getAllSchedules() {
        return new ArrayList<>(schedules);
    }

    public List<Schedule> getSchedulesByDoctor(String doctorId) {
        return schedules.stream()
                .filter(schedule -> schedule.getDoctor().getId().equals(doctorId))
                .toList();
    }

    public void removeSchedule(String doctorId, LocalDate date) {
        schedules.removeIf(schedule ->
                schedule.getDoctor().getId().equals(doctorId) &&
                        !schedule.getAvailability(date).isEmpty());
    }
}
