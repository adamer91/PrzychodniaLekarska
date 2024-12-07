package clinic.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Schedule {
    private final Doctor doctor;
    private final Map<LocalDate, Map<LocalTime, Boolean>> availability;

    public Schedule(Doctor doctor) {
        this.doctor = doctor;
        this.availability = new HashMap<>();
    }

    // Dodawanie dostępności w określonym dniu
    public void addAvailability(LocalDate date, LocalTime start, LocalTime end) {
        availability.putIfAbsent(date, new HashMap<>());
        for (LocalTime time = start; !time.isAfter(end); time = time.plusMinutes(15)) {
            availability.get(date).put(time, true);
        }
    }

    // Sprawdzanie, czy lekarz jest dostępny w określonym dniu i godzinie
    public boolean isAvailable(LocalDate date, LocalTime time) {
        return availability.containsKey(date) && availability.get(date).getOrDefault(time, false);
    }

    // Pobieranie dostępności na dany dzień
    public Map<LocalTime, Boolean> getAvailability(LocalDate date) {
        return availability.getOrDefault(date, new HashMap<>());
    }

    public Doctor getDoctor() {
        return doctor;
    }
}
