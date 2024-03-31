import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class TestAndmebaas {
    public static void main(String[] args) throws SQLException {
        Andmebaas andmebaas = new Andmebaas();
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println("Valige enda kasutaja:");

            System.out.println(andmebaas.tagastaKasutajateOlemid());
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Lõpetan programmi");
                break;
            }

            Kasutaja valitud = null;
            for (Kasutaja kasutaja : andmebaas.tagastaKasutajateOlemid()) {
                if (kasutaja.getNimi().equalsIgnoreCase(input)) {
                    System.out.println("Valisite kasutaja: " + kasutaja.getNimi());
                    valitud = kasutaja;
                    break;
                }
            }

            if (valitud != null) {
                System.out.println("Kas soovite valida ülesannet või luua uut? (uus/valik) ");
                String valik = scanner.nextLine();
                if (valik.equalsIgnoreCase("uus")) {
                    System.out.println("Sisesta uue ülesande nimi: ");
                    String nimi = scanner.nextLine();
                    andmebaas.lisaUusUlesanne(nimi, valitud.getKasutajaID());
                    System.out.println("Uus ulesanne loodud");
                    //Siin loo samuti uus pomodoro
                } else if (valik.equalsIgnoreCase("valik")) {
                    System.out.println(andmebaas.tagastaUlesanneteOlemid(valitud.getKasutajaID()));
                    int arv = Integer.parseInt(scanner.nextLine());
                    if (arv > (andmebaas.tagastaUlesanneteOlemid(valitud.getKasutajaID()).size())) {
                        System.out.println("Nii palju ülesandeid ei leidu");
                    } else {
                        System.out.println("Valisite ülesande: ");
                        Ulesanne ulesanne = andmebaas.tagastaUlesanneteOlemid(valitud.getKasutajaID()).get(arv - 1);
                        System.out.println(ulesanne);
                        System.out.println("Kas soovite uut pomodoro taimerit või valite juba loodud taimeri? (uus/valik)");
                        valik = scanner.nextLine();
                        if (valik.equalsIgnoreCase("uus")) {
                            System.out.println("Valige pomodoro taimeri produktiivsusaeg (HH:MM)");
                            String aeg1 = scanner.nextLine();
                            System.out.println("Valige taimeri puhkeaeg");
                            String aeg2 = scanner.nextLine();
                            andmebaas.lisaUusPomodoro(Duration.parse(aeg1), Duration.parse(aeg2), ulesanne.getUlesanneID()); // Siin tekib error DateTimeParseException
                            //Uus pomodoro saab loodud, siia tuleb kood, et siis taimer alustada
                        }
                    }
                } else {
                    System.out.println("Valik ei sobi");
                }
            } else {
                System.out.println("Kasutajat ei leitud");
            }
        }

        scanner.close();
        andmebaas.katkestaUhendus();


    }
}
