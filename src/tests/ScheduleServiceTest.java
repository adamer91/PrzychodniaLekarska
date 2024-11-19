package tests;

import clinic.model.Schedule;
import clinic.service.DoctorService;
import clinic.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleServiceTest {
    private ScheduleService scheduleService;
    private DoctorService doctorService;

    @BeforeEach
    public void setUp() {
        doctorService = new DoctorService();
        scheduleService = new ScheduleService(doctorService);

        // Tworzenie lekarza
        doctorService.createDoctor("John", "Doe", "1", LocalDate.of(1980, 1, 1), "123456789", "john.doe@example.com");
    }

    @Test
    public void testCreateSchedule() {
        scheduleService.createSchedule("1", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0), LocalTime.of(17, 0));
        List<Schedule> schedules = scheduleService.getAllSchedules();
        assertEquals(1, schedules.size());
        assertTrue(schedules.get(0).isAvailable(LocalDate.of(2023, 10, 1), LocalTime.of(9, 0)));
    }

    @Test
    public void testGetSchedulesByDoctorId() {
        scheduleService.createSchedule("1", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0), LocalTime.of(17, 0));
        List<Schedule> schedules = scheduleService.getSchedulesByDoctorId("1");
        assertEquals(1, schedules.size());
    }

    @Test
    public void testGetAllSchedules() {
        scheduleService.createSchedule("1", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0), LocalTime.of(17, 0));
        scheduleService.createSchedule("1", LocalDate.of(2023, 10, 2), LocalTime.of(9, 0), LocalTime.of(17, 0));
        List<Schedule> schedules = scheduleService.getAllSchedules();
        assertEquals(2, schedules.size());
    }
}