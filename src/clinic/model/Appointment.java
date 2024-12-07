package clinic.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private final Patient patient;
    private final Doctor doctor;
    private final LocalDate date;
    private final LocalTime time;

    public Appointment(Patient patient, Doctor doctor, LocalDate date, LocalTime time) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "patient=" + patient +
                ", doctor=" + doctor +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
