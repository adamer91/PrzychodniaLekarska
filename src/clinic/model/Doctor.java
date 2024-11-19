package clinic.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private final String firstName;
    private final String lastName;
    private final String id;
    private final LocalDate dateOfBirth;
    private final int age;
    private final String phoneNumber;
    private final String email;
    private final List<String> specializations;

    public Doctor(String firstName, String lastName, String id, LocalDate dateOfBirth, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.age = calculateAge(dateOfBirth);
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.specializations = new ArrayList<>();
    }

    public void addSpecialization(String specialization) {
        this.specializations.add(specialization);
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

    public List<String> getSpecializations() {
        return specializations;
    }

    private int calculateAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getYear() - dateOfBirth.getYear();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id='" + id + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", specializations=" + specializations +
                '}';
    }
}