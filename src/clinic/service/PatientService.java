package clinic.service;

import clinic.model.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PatientService {
    private final List<Patient> patients;

    public PatientService() {
        this.patients = new ArrayList<>();
    }

    // Dodawanie pacjenta
    public void createPatient(String firstName, String lastName, String pesel, LocalDate dateOfBirth, String phoneNumber, String email) {
        if (findPatientByPesel(pesel).isPresent()) {
            throw new IllegalArgumentException("Patient with PESEL already exists.");
        }
        patients.add(new Patient(firstName, lastName, pesel, dateOfBirth, phoneNumber, email));
    }

    // Wyszukiwanie pacjenta po PESEL
    public Optional<Patient> findPatientByPesel(String pesel) {
        return patients.stream()
                .filter(patient -> patient.getPesel().equals(pesel))
                .findFirst();
    }

    // Wyszukiwanie pacjentów po nazwisku
    public List<Patient> findPatientsByLastName(String lastName) {
        return patients.stream()
                .filter(patient -> patient.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    // Pobieranie wszystkich pacjentów
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }
}
