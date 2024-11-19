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
        Optional<Doctor> doctor = doctorService.findDoctorById(doctorId);
        doctor.ifPresent(d -> {
            Schedule schedule = new Schedule(d);
            schedule.addAvailability(date, start, end);
            schedules.add(schedule);
        });
    }

    public List<Schedule> getSchedulesByDoctorId(String doctorId) {
        return schedules.stream().filter(s -> s.getDoctor().getId().equals(doctorId)).toList();
    }

    public List<Schedule> getAllSchedules() {
        return new ArrayList<>(schedules);
    }
}