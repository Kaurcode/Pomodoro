import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestAndmebaas {
    public static void main(String[] args) {
        try (Andmebaas andmebaas = new Andmebaas()) {
            try (Scanner scanner = new Scanner(System.in)) {

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
                                    String aeg1String = scanner.nextLine();
                                    Duration aeg1 = sisendiTeisendusAjaks(aeg1String);
                                    System.out.println("Valige taimeri puhkeaeg");
                                    String aeg2String = scanner.nextLine();
                                    Duration aeg2 = sisendiTeisendusAjaks(aeg2String);
                                    andmebaas.lisaUusPomodoro(aeg1, aeg2, ulesanne.getUlesandeID());
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
            }
        }
    }

    public static Duration sisendiTeisendusAjaks(String sisend) {
        // https://www.baeldung.com/regular-expressions-java
        String formaat1String = "\\d:\\d\\d";
        Pattern formaat1 = Pattern.compile(formaat1String);
        Matcher formaadiKontroll1 = formaat1.matcher(sisend);

        if (formaadiKontroll1.find()) {
            String[] sisendiAndmed = sisend.split(":");
            int minutid = Integer.parseInt(sisendiAndmed[0]);
            int sekundid = Integer.parseInt(sisendiAndmed[1]);
            int sekundidKokku = minutid * 60 + sekundid;
            return Duration.ofSeconds(sekundidKokku);
        }

        return null;
    }
}
