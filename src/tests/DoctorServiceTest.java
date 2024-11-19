package tests;

import clinic.model.Doctor;
import clinic.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorServiceTest {
    private DoctorService doctorService;

    @BeforeEach
    public void setUp() {
        doctorService = new DoctorService();
    }

    @Test
    public void testCreateDoctor() {
        doctorService.createDoctor("John", "Doe", "1", LocalDate.of(1980, 1, 1), "123456789", "john.doe@example.com");
        Optional<Doctor> doctor = doctorService.findDoctorById("1");
        assertTrue(doctor.isPresent());
        assertEquals("John", doctor.get().getFirstName());
        assertEquals("Doe", doctor.get().getLastName());
    }

    @Test
    public void testAddSpecializationToDoctor() {
        doctorService.createDoctor("John", "Doe", "1", LocalDate.of(1980, 1, 1), "123456789", "john.doe@example.com");
        doctorService.addSpecializationToDoctor("1", "Cardiologist");
        Optional<Doctor> doctor = doctorService.findDoctorById("1");
        assertTrue(doctor.isPresent());
        assertTrue(doctor.get().getSpecializations().contains("Cardiologist"));
    }

    @Test
    public void testFindDoctorById() {
        doctorService.createDoctor("John", "Doe", "1", LocalDate.of(1980, 1, 1), "123456789", "john.doe@example.com");
        Optional<Doctor> doctor = doctorService.findDoctorById("1");
        assertTrue(doctor.isPresent());
    }

    @Test
    public void testFindDoctorsBySpecialization() {
        doctorService.createDoctor("John", "Doe", "1", LocalDate.of(1980, 1, 1), "123456789", "john.doe@example.com");
        doctorService.addSpecializationToDoctor("1", "Cardiologist");
        List<Doctor> doctors = doctorService.findDoctorsBySpecialization("Cardiologist");
        assertEquals(1, doctors.size());
    }

    @Test
    public void testGetAllDoctors() {
        doctorService.createDoctor("John", "Doe", "1", LocalDate.of(1980, 1, 1), "123456789", "john.doe@example.com");
        doctorService.createDoctor("Jane", "Smith", "2", LocalDate.of(1985, 1, 1), "987654321", "jane.smith@example.com");
        List<Doctor> doctors = doctorService.getAllDoctors();
        assertEquals(2, doctors.size());
    }
}