package clinic.service;

import java.time.LocalDate;
import clinic.model.Patient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientService{
    private final List<Patient> patients;

    public PatientService() {
        this.patients = new ArrayList<>();
    }

    public void createPatient(String firstName, String lastName, String pesel, LocalDate dateOfBirth, String phoneNumber, String email) {
        Patient patient = new Patient(firstName, lastName, pesel, dateOfBirth, phoneNumber, email);
        patients.add(patient);
    }

    public Optional<Patient> findPatientByPesel(String pesel) {
        return patients.stream().filter(patient -> patient.getPesel().equals(pesel)).findFirst();
    }

    public List<Patient> findPatientsByLastName(String lastName) {
        return patients.stream().filter(patient -> patient.getLastName().equalsIgnoreCase(lastName)).toList();
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients);
    }
}