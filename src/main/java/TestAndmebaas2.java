import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

public class TestAndmebaas2 {
    public static void main(String[] args) {
        try (Andmebaas andmebaas = new Andmebaas()) {
//            int kasutajaID = andmebaas.lisaUusKasutaja("Kaur");
//            int ulesandeID = andmebaas.lisaUusUlesanne("OOP", kasutajaID);
//            int pomodoroID = andmebaas.lisaUusPomodoro(Duration.ofMinutes(25), Duration.ofMinutes(5), ulesandeID);
//            System.out.println(pomodoroID);

//            andmebaas.lisaUusKasutaja("Karl");
//            andmebaas.lisaUusKasutaja("Siim");
//            andmebaas.lisaUusKasutaja("Taavi");
//            andmebaas.lisaUusKasutaja("Oskar");
//            andmebaas.lisaUusKasutaja("Alo");

            ArrayList<Kasutaja> kasutajad = andmebaas.tagastaKasutajateOlemid();
            Collections.sort(kasutajad);
            System.out.println(kasutajad);

            ArrayList<Pomodoro> pomodorod = andmebaas.tagastaPomodorodeOlemid(1);
            Pomodoro pomodoro = pomodorod.get(0);

            System.out.println(pomodoro);
        }

    }
}
