package clinic.service;

import java.time.LocalDate;
import clinic.model.Doctor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorService {
    private final List<Doctor> doctors;

    public DoctorService() {
        this.doctors = new ArrayList<>();
    }

    public void createDoctor(String firstName, String lastName, String id, LocalDate dateOfBirth, String phoneNumber, String email) {
        Doctor doctor = new Doctor(firstName, lastName, id, dateOfBirth, phoneNumber, email);
        doctors.add(doctor);
    }

    public void addSpecializationToDoctor(String doctorId, String specialization) {
        Optional<Doctor> doctor = findDoctorById(doctorId);
        doctor.ifPresent(d -> d.addSpecialization(specialization));
    }

    public Optional<Doctor> findDoctorById(String id) {
        return doctors.stream().filter(d -> d.getId().equals(id)).findFirst();
    }

    public List<Doctor> findDoctorsBySpecialization(String specialization) {
        return doctors.stream().filter(d -> d.getSpecializations().contains(specialization)).toList();
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors);
    }
}