package clinic.model;

import java.time.LocalDate;

public class Patient {
    private final String firstName;
    private final String lastName;
    private final String pesel;
    private final LocalDate dateOfBirth;
    private final int age;
    private final String phoneNumber;
    private final String email;

    public Patient(String firstName, String lastName, String pesel, LocalDate dateOfBirth, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.dateOfBirth = dateOfBirth;
        this.age = calculateAge(dateOfBirth);
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

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    private int calculateAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear() - dateOfBirth.getYear();
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}