import pl.wsb.lab.Patient;

public class Main {
    public static void main(String[] args) {
        Patient patient = new Patient("Jan", "Kowalski", "12345678901", "01-01-1980", 42, "123-456-789", "jan.kowalski@example.com");
        System.out.println("Pacjent: " + patient.getFirstName() + " " + patient.getLastName());
    }
}
