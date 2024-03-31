import java.time.Duration;

public class TestAndmebaas {
    public static void main(String[] args) {
        Andmebaas andmebaas = new Andmebaas();
//        int kasutajaID = andmebaas.lisaUusKasutaja("Kaur");
//        int ulesandeID = andmebaas.lisaUusUlesanne("OOP", kasutajaID);
//        int pomodoroID = andmebaas.lisaUusPomodoro(Duration.ofMinutes(25), Duration.ofMinutes(5), ulesandeID);
//        System.out.println(pomodoroID);

//        andmebaas.lisaUusKasutaja("Karl");
//        andmebaas.lisaUusKasutaja("Siim");
//        andmebaas.lisaUusKasutaja("Taavi");
//        andmebaas.lisaUusKasutaja("Oskar");
//        andmebaas.lisaUusKasutaja("Alo");

        System.out.println(andmebaas.tagastaKasutajateOlemid());

        andmebaas.katkestaUhendus();
    }
}
