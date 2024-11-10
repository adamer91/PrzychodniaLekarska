package pl.wsb.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Clinic {

    private List<Patient> patient;

    public Clinic() {
        this.patient = new ArrayList<>();
    }

    public Patient createPatientAccount(String firstName, String lastName, String pesel, String birthDay
            , int age, String phoneNo, String emailAddress) {
        Patient newPatient = new Patient(firstName, lastName, pesel, birthDay, age, phoneNo, emailAddress);
        this.patient.add(newPatient);
        return newPatient;
    }

    public Patient findPatientByPesel(String pesel) {
        for (Patient patient : patient) {
            if (patient.getPesel().equals(pesel)) {
                return patient;
            }
        }
        return null; // Zwraca null, jeśli pacjent o podanym PESEL nie został znaleziony
    }

    public List<Patient> wyszukajPacjentowPoNazwisku(String lastName) {
        return patient.stream()
                .filter(patient -> patient.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }
}
