import pl.wsb.lab.Pacjent;

public class Main {
    public static void main(String[] args) {
        Pacjent pacjent = new Pacjent("Jan", "Kowalski", "12345678901", "01-01-1980", 42, "123-456-789", "jan.kowalski@example.com");
        System.out.println("Pacjent: " + pacjent.getImie() + " " + pacjent.getNazwisko());
    }
}
