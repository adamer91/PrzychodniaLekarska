package clinic.model;

import java.time.LocalDate;

public class Patient {
    private final String firstName;
    private final String lastName;
    private final String pesel;
    private final LocalDate dateOfBirth;
    private final String phoneNumber;
    private final String email;

    public Patient(String firstName, String lastName, String pesel, LocalDate dateOfBirth, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + pesel + ")";
    }
}
