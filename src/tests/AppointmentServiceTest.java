package tests;

import clinic.model.Appointment;
import clinic.service.AppointmentService;
import clinic.service.DoctorService;
import clinic.service.PatientService;
import clinic.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceTest {
    private PatientService patientService;
    private DoctorService doctorService;
    private ScheduleService scheduleService;
    private AppointmentService appointmentService;

    @BeforeEach
    public void setUp() {
        patientService = new PatientService();
        doctorService = new DoctorService();
        scheduleService = new ScheduleService(doctorService);
        appointmentService = new AppointmentService(patientService, doctorService, scheduleService);

        // Tworzenie pacjenta
        patientService.createPatient("Jane", "Doe", "12345678901", LocalDate.of(1990, 1, 1), "123456789", "jane.doe@example.com");

        // Tworzenie lekarza
        doctorService.createDoctor("John", "Doe", "1", LocalDate.of(1980, 1, 1), "123456789", "john.doe@example.com");

        // Tworzenie grafiku dla lekarza
        scheduleService.createSchedule("1", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0), LocalTime.of(17, 0));
    }

    @Test
    public void testCreateAppointment() {
        appointmentService.createAppointment("12345678901", "1", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0));
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorAndDate("1", LocalDate.of(2023, 10, 1));
        assertEquals(1, appointments.size());
        assertTrue(appointments.get(0).getTime().equals(LocalTime.of(9, 0)));
    }

    @Test
    public void testCreateAppointmentNotAvailable() {
        appointmentService.createAppointment("12345678901", "1", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0));
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.createAppointment("12345678901", "1", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0));
        });
    }

    @Test
    public void testCreateAppointmentNotFree() {
        appointmentService.createAppointment("12345678901", "1", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0));
        assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.createAppointment("12345678901", "1", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0));
        });
    }

    @Test
    public void testGetAppointmentsByDoctorAndDate() {
        appointmentService.createAppointment("12345678901", "1", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0));
        appointmentService.createAppointment("12345678901", "1", LocalDate.of(2023, 10, 1), LocalTime.of(10, 0));
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctorAndDate("1", LocalDate.of(2023, 10, 1));
        assertEquals(2, appointments.size());
    }

    @Test
    public void testGetAllAppointments() {
        appointmentService.createAppointment("12345678901", "1", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0));
        appointmentService.createAppointment("12345678901", "1", LocalDate.of(2023, 10, 2), LocalTime.of(9, 0));
        List<Appointment> appointments = appointmentService.getAllAppointments();
        assertEquals(2, appointments.size());
    }
}