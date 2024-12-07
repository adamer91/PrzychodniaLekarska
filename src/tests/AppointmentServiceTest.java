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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppointmentServiceTest {
    private AppointmentService appointmentService;
    private PatientService patientService;
    private DoctorService doctorService;
    private ScheduleService scheduleService;

    @BeforeEach
    public void setUp() {
        patientService = new PatientService();
        doctorService = new DoctorService();
        scheduleService = new ScheduleService(doctorService);
        appointmentService = new AppointmentService(patientService, doctorService, scheduleService);

        // Dodanie pacjenta
        patientService.createPatient("John", "Smith", "12345678901", LocalDate.of(1990, 1, 1), "123456789", "john.smith@example.com");

        // Dodanie lekarza
        doctorService.createDoctor("Dr.", "Who", "1", LocalDate.of(1980, 1, 1), "987654321", "dr.who@example.com");

        // Dodanie grafiku dla lekarza
        scheduleService.createSchedule("1", LocalDate.of(2023, 10, 1), LocalTime.of(9, 0), LocalTime.of(17, 0));
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
        appointmentService.createAppointment("12345678901", "1", LocalDate.of(2023, 10, 2), LocalTime.of(10, 0));

        List<Appointment> appointments = appointmentService.getAllAppointments();

        assertEquals(2, appointments.size());
    }
}
