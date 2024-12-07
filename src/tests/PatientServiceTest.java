package tests;

import clinic.service.PatientService;
import clinic.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientServiceTest {
    private PatientService patientService;

    @BeforeEach
    public void setUp() {
        patientService = new PatientService();

        // Dodanie przykładowych pacjentów
        patientService.createPatient("John", "Smith", "12345678901", LocalDate.of(1990, 1, 1), "123456789", "john.smith@example.com");
        patientService.createPatient("Jane", "Smith", "98765432109", LocalDate.of(1995, 5, 15), "987654321", "jane.smith@example.com");
        patientService.createPatient("Alice", "Brown", "56789012345", LocalDate.of(1985, 3, 10), "567890123", "alice.brown@example.com");
    }

    @Test
    public void testFindPatientsByLastName() {
        // Wyszukiwanie pacjentów o nazwisku "Smith"
        List<Patient> results = patientService.findPatientsByLastName("Smith");

        // Sprawdzanie wyników
        assertEquals(2, results.size());
    }
}
