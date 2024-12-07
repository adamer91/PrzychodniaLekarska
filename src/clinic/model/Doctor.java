package clinic.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private final String firstName;
    private final String lastName;
    private final String id;
    private final LocalDate dateOfBirth;
    private final String phoneNumber;
    private final String email;
    private final List<String> specializations;

    public Doctor(String firstName, String lastName, String id, LocalDate dateOfBirth, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.specializations = new ArrayList<>();
    }

    public void addSpecialization(String specialization) {
        if (!specializations.contains(specialization)) {
            specializations.add(specialization);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }

    public List<String> getSpecializations() {
        return specializations;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + id + ") - " + specializations;
    }
}
