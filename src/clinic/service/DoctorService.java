package clinic.service;

import clinic.model.Doctor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorService {
    private final List<Doctor> doctors;

    public DoctorService() {
        this.doctors = new ArrayList<>();
    }

    public void createDoctor(String firstName, String lastName, String id, LocalDate dateOfBirth, String phoneNumber, String email) {
        if (findDoctorById(id).isPresent()) {
            throw new IllegalArgumentException("Doctor with ID already exists.");
        }
        doctors.add(new Doctor(firstName, lastName, id, dateOfBirth, phoneNumber, email));
    }

    public void addSpecializationToDoctor(String doctorId, String specialization) {
        Optional<Doctor> doctorOptional = findDoctorById(doctorId);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctor.addSpecialization(specialization);
        } else {
            throw new IllegalArgumentException("Doctor not found.");
        }
    }

    public Optional<Doctor> findDoctorById(String id) {
        return doctors.stream().filter(d -> d.getId().equals(id)).findFirst();
    }

    public List<Doctor> findDoctorsBySpecialization(String specialization) {
        return doctors.stream()
                .filter(doctor -> doctor.getSpecializations().contains(specialization))
                .toList();
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors);
    }
}
