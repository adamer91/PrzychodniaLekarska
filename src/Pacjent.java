
public class Pacjent {
    private String imie;
    private String nazwisko;
    private String pesel;
    private String dataUrodzenia;
    private int wiek;
    private String numerTelefonu;
    private String adresEmail;

    // Konstruktor
    public Pacjent(String imie, String nazwisko, String pesel, String dataUrodzenia, int wiek, String numerTelefonu, String adresEmail) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.dataUrodzenia = dataUrodzenia;
        this.wiek = wiek;
        this.numerTelefonu = numerTelefonu;
        this.adresEmail = adresEmail;
    }

    // Gettery i settery dla każdego pola
    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(String dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public int getWiek() {
        return wiek;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public String getNumerTelefonu() {
        return numerTelefonu;
    }

    public void setNumerTelefonu(String numerTelefonu) {
        this.numerTelefonu = numerTelefonu;
    }

    public String getAdresEmail() {
        return adresEmail;
    }

    public void setAdresEmail(String adresEmail) {
        this.adresEmail = adresEmail;
    }
}
public class Main {
    public static void main(String[] args) {
        Pacjent pacjent = new Pacjent("Jan", "Kowalski", "12345678901", "01-01-1980", 42, "123-456-789", "jan.kowalski@example.com");
        System.out.println("Pacjent: " + pacjent.getImie() + " " + pacjent.getNazwisko());
    }
}
