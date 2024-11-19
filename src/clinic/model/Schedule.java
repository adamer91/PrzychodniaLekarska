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

    public Doctor getDoctor() {
        return doctor;
    }

    public Map<LocalDate, Map<LocalTime, Boolean>> getAvailability() {
        return availability;
    }

    public void addAvailability(LocalDate date, LocalTime start, LocalTime end) {
        availability.putIfAbsent(date, new HashMap<>());
        for (LocalTime time = start; !time.isAfter(end); time = time.plusMinutes(15)) {
            availability.get(date).put(time, true);
        }
    }

    public boolean isAvailable(LocalDate date, LocalTime time) {
        if (!availability.containsKey(date)) return false;
        return availability.get(date).getOrDefault(time, false);
    }

    public void setBusy(LocalDate date, LocalTime time) {
        if (availability.containsKey(date) && availability.get(date).containsKey(time)) {
            availability.get(date).put(time, false);
        }
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "doctor=" + doctor +
                ", availability=" + availability +
                '}';
    }
}