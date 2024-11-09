package pl.wsb.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Klinika {

    private List<Pacjent> pacjenci;

    public Klinika() {
        this.pacjenci = new ArrayList<>();
    }

    public Pacjent utworzProfilPacjenta(String imie, String nazwisko, String pesel, String dataUrodzenia, int wiek, String numerTelefonu, String adresEmail) {
        Pacjent nowyPacjent = new Pacjent(imie, nazwisko, pesel, dataUrodzenia, wiek, numerTelefonu, adresEmail);
        this.pacjenci.add(nowyPacjent);
        return nowyPacjent;
    }

    public Pacjent znajdzPacjentaPoPesel(String pesel) {
        for (Pacjent pacjent : pacjenci) {
            if (pacjent.getPesel().equals(pesel)) {
                return pacjent;
            }
        }
        return null; // Zwraca null, jeśli pacjent o podanym PESEL nie został znaleziony
    }

    public List<Pacjent> wyszukajPacjentowPoNazwisku(String nazwisko) {
        return pacjenci.stream()
                .filter(pacjent -> pacjent.getNazwisko().equalsIgnoreCase(nazwisko))
                .collect(Collectors.toList());
    }
}
