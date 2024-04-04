import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgrammKoopia {
    public ArrayList<Kasutaja> loeAndmedMallu(Andmebaas andmebaas) {
        ArrayList<Kasutaja> kasutajad = andmebaas.tagastaKasutajateOlemid();
        for (Kasutaja kasutaja : kasutajad) {
            kasutaja.lisaUlesandedNimekirja(andmebaas.tagastaUlesanneteOlemid(kasutaja.getKasutajaID()));
            for (Ulesanne ulesanne : kasutaja.getUlesanded()) {
                ulesanne.lisaPomodorodNimekirja(andmebaas.tagastaPomodorodeOlemid(ulesanne.getUlesandeID()));
            }
        }
        return kasutajad;
    }
    /**
     * Meetod programmi alustamiseks, loob ühenduse andmebaasiga
     * @return Andmebaasi isend edasiseks töötlemiseks
     */
    public static Andmebaas alustaProgramm() {
        return new Andmebaas();
    }

    /**
     * Meetod programmi esimese osa töötlemiseks, kasutaja, ülesande ja taimeri valik/loomine
     * @param andmebaas Võtab argumendiks kasutatava andmebaasi
     * @return Tagastab valitud või loodud taimeri isendi, mis kuulub kindlale kasutajale ja kindlale ülesandele
     */
    public static Pomodoro kasutajaValik(Andmebaas andmebaas) {
        ArrayList<Pomodoro> pomodoros = new ArrayList<>();
        try (Scanner scanner = new Scanner(System.in)) {
            String input;
            Kasutaja valitud = null;

            while (true) {
                System.out.println("Valige enda kasutaja: ");
                System.out.println(andmebaas.tagastaKasutajateOlemid());
                input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Lõpetan programmi");
                    break;
                }

               // Otsib andmebaasi kasutajate seast soovitud kasutajat
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
                        Ulesanne ulesanne = andmebaas.looUusUlesanne(nimi, valitud); // Loob uue ülesande valitud kasutajale, mille nimetuseks saab sisestatud nimi
                        System.out.println("Uus ülesanne loodud");
                        System.out.println("Kas soovite valida Pomodoro taimerit või luua uut? (uus/valik) ");
                        valik = scanner.nextLine();
                        if (valik.equalsIgnoreCase("uus")) {
                            System.out.println("Sisesta uue Pomodoro produktiivsusaeg (MM:SS)");
                            String aeg1String = scanner.nextLine();
                            Duration aeg1 = sisendiTeisendusAjaks(aeg1String); // Kasutab meetodit, et teisendada sisestatud aeg String tüübist Duration tüüpi
                            System.out.println("Sisesta uue Pomodoro puhkeaeg (MM:SS)");
                            String aeg2String = scanner.nextLine();
                            Duration aeg2 = sisendiTeisendusAjaks(aeg2String);
                            Pomodoro pomodoro = andmebaas.looUusPomodoro(aeg1, aeg2, ulesanne);
                            System.out.println("Uus pomodoro loodud");
                            pomodoros.add(pomodoro);
                            break;
                        } else if (valik.equalsIgnoreCase("valik")) {
                            System.out.println(andmebaas.tagastaPomodorodeOlemid(ulesanne.getUlesandeID()));
                            ArrayList<Pomodoro> pomodorod = andmebaas.tagastaPomodorodeOlemid(ulesanne.getUlesandeID());
                            System.out.println("Mitmendat taimerit soovite?"); // Võimaldab valida andmebaasi pomodoro taimerite seast ning valib taimerite järjendist soovitud taimeri.
                            int soov2 = Integer.parseInt(scanner.nextLine());
                            System.out.println("Valisite taimeri: ");
                            Pomodoro pomodoro = pomodorod.get(soov2 - 1);
                            System.out.println(pomodoro);
                            pomodoros.add(pomodoro);
                            break;
                        } else {
                            System.out.println("Valik ei sobi");
                        }
                    } else if (valik.equalsIgnoreCase("valik")) {
                        System.out.println(andmebaas.tagastaUlesanneteOlemid(valitud.getKasutajaID())); // Võimaldab valida andmebaasi ülesannete listist soovitud ülesannet
                        ArrayList<Ulesanne> ulesanded = andmebaas.tagastaUlesanneteOlemid(valitud.getKasutajaID());
                        System.out.println("Mitmendat ülesannet soovite?");
                        int soov = Integer.parseInt(scanner.nextLine());
                        System.out.println("Valisite ülesande: ");
                        Ulesanne ulesanne = ulesanded.get(soov - 1);
                        System.out.println(ulesanne.getNimi());
                        System.out.println("Kas soovite valida Pomodoro taimerit või luua uut? (uus/valik)");
                        valik = scanner.nextLine();
                        if (valik.equalsIgnoreCase("uus")) {
                            String aeg1String = scanner.nextLine();
                            Duration aeg1 = sisendiTeisendusAjaks(aeg1String);
                            System.out.println("Sisesta uue Pomodoro puhkeaeg (MM:SS)");
                            String aeg2String = scanner.nextLine();
                            Duration aeg2 = sisendiTeisendusAjaks(aeg2String);
                            Pomodoro pomodoro = andmebaas.looUusPomodoro(aeg1, aeg2, ulesanne);
                            System.out.println("Uus pomodoro loodud");
                            pomodoros.add(pomodoro);
                            break;
                        } else if (valik.equalsIgnoreCase("valik")) {
                            System.out.println(andmebaas.tagastaPomodorodeOlemid(ulesanne.getUlesandeID()));
                            ArrayList<Pomodoro> pomodorod = andmebaas.tagastaPomodorodeOlemid(ulesanne.getUlesandeID());
                            System.out.println("Mitmendat taimerit soovite?");
                            int soov2 = Integer.parseInt(scanner.nextLine());
                            System.out.println("Valisite taimeri: ");
                            Pomodoro pomodoro = pomodorod.get(soov2 - 1);
                            pomodoros.add(pomodoro);
                            break;
                        } else {
                            System.out.println("Valik ei sobi");
                        }
                    } else {
                        System.out.println("Valik ei sobi");
                    }
                } else {
                    System.out.println("Kasutajat ei leitud");
                }
            }
        }
        return pomodoros.getFirst();
    }

    /**
     * Meetod String formaadis sisestatud aja teisendamiseks Duration tüüpi
     * @param sisend Sisend-String, meetodis võetakse kasutaja enda inputist.
     * @return Tagastab sisestatud ajapikkuse Duration tüübina, et oleks võimalik luua Pomodoro isend
     */
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
