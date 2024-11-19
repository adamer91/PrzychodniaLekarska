package tests;

import clinic.model.Patient;
import clinic.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PatientServiceTest {
    private PatientService patientService;

    @BeforeEach
    public void setUp() {
        patientService = new PatientService();
    }

    @Test
    public void testCreatePatient() {
        patientService.createPatient("John", "Doe", "12345678901", LocalDate.of(1990, 1, 1), "123456789", "john.doe@example.com");
        Optional<Patient> patient = patientService.findPatientByPesel("12345678901");
        assertTrue(patient.isPresent());
        assertEquals("John", patient.get().getFirstName());
        assertEquals("Doe", patient.get().getLastName());
    }

    @Test
    public void testFindPatientByPesel() {
        patientService.createPatient("John", "Doe", "12345678901", LocalDate.of(1990, 1, 1), "123456789", "john.doe@example.com");
        Optional<Patient> patient = patientService.findPatientByPesel("12345678901");
        assertTrue(patient.isPresent());
    }

    @Test
    public void testFindPatientsByLastName() {
        patientService.createPatient("John", "Doe", "12345678901", LocalDate.of(1990, 1, 1), "123456789", "john.doe@example.com");
        patientService.createPatient("Jane", "Doe", "12345678902", LocalDate.of(1992, 1, 1), "123456790", "jane.doe@example.com");
        List<Patient> patients = patientService.findPatientsByLastName("Doe");
        assertEquals(2, patients.size());
    }

    @Test
    public void testGetAllPatients() {
        patientService.createPatient("John", "Doe", "12345678901", LocalDate.of(1990, 1, 1), "123456789", "john.doe@example.com");
        patientService.createPatient("Jane", "Doe", "12345678902", LocalDate.of(1992, 1, 1), "123456790", "jane.doe@example.com");
        List<Patient> patients = patientService.getAllPatients();
        assertEquals(2, patients.size());
    }
}