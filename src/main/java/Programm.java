import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Programm {
    public static final ArrayList<String> lopetusSoned =
            new ArrayList<>(Arrays.asList("exit", "close", "lõpeta", "lopeta", "sule", "lahku"));
    public static final ArrayList<String> tagasiSoned = new ArrayList<>(Arrays.asList("back", "tagasi", "eelmine"));
    public static final ArrayList<String> uusSoned = new ArrayList<>(Arrays.asList("uus"));
    public static ArrayList<Kasutaja> kasutajad;

    public static void loeAndmedMallu(Andmebaas andmebaas) {
        kasutajad = andmebaas.tagastaKasutajateOlemid();
        for (Kasutaja kasutaja : kasutajad) {
            kasutaja.lisaUlesandedNimekirja(andmebaas.tagastaUlesanneteOlemid(kasutaja.getKasutajaID()));
            for (Ulesanne ulesanne : kasutaja.getUlesanded()) {
                ulesanne.lisaPomodorodNimekirja(andmebaas.tagastaPomodorodeOlemid(ulesanne.getUlesandeID()));
            }
        }
    }

    public static String valikuAken(ArrayList<CLINimekirjaElement> valikud, String tuup) {
        CLITekst valiKasutajaTekst = new CLITekst(String.format("Vali %s: ", tuup), CLITeema.TEKSTI_VARV, true);
        CLINimekiri kasutajateNimekiri = new CLINimekiri(valikud,
                CLITeema.TEKSTI_VARV_NIMEKIRJAS, CLITeema.KASU_VARV_NIMEKIRJAS);

        CLISisend sisendiElement = new CLISisend("Sisend: ", CLITeema.TEKSTI_VARV, 0);
        CLIAknaElement[] sisendiAknaSisu = {sisendiElement};

        CLIAken sisendiAken = new CLIAken("", sisendiAknaSisu, 2, 1);

        CLIAknaElement[] kasutajateAknaSisu = {valiKasutajaTekst, kasutajateNimekiri, sisendiAken};
        CLIAken kasutjateAken = new CLIAken("Kasutajavalik", kasutajateAknaSisu, 4, 2);

        int minLaius = 100;
        int x = (KonsooliFunktsioonid.getKonsooliLaius() - minLaius) / 2;
        int y = (KonsooliFunktsioonid.getKonsooliPikkus() - kasutjateAken.valjastaElemendiPikkus()) / 2;
        return KonsooliFunktsioonid.puhastaKonsool() + kasutjateAken.looCLIElement(x, y, minLaius);
    }

    public static String looUusObjektAken(String tuup) {
        CLITekst uusObjektTekst = new CLITekst(String.format("Sisesta uue %s nimi: ", tuup), CLITeema.TEKSTI_VARV, true);

        CLISisend sisend = new CLISisend("Sisend: ", CLITeema.TEKSTI_VARV, 0);
        CLIAknaElement[] sisendiAknaSisu = {sisend};
        CLIAken sisendiAken = new CLIAken("", sisendiAknaSisu, 2, 1);

        CLIAknaElement[] aknaElemendid = {uusObjektTekst, sisendiAken};
        CLIAken aken = new CLIAken(String.format("Uus %s", tuup), aknaElemendid, 4, 2);
        int aknaLaius = 75;
        int x = (KonsooliFunktsioonid.getKonsooliLaius() - aknaLaius) / 2;
        int y = (KonsooliFunktsioonid.getKonsooliPikkus() - aken.valjastaElemendiPikkus()) / 2;
        return aken.looCLIElement(x, y, aknaLaius);
    }

    /**
     * Meetod programmi alustamiseks, loob ühenduse andmebaasiga
     *
     * @return Andmebaasi isend edasiseks töötlemiseks
     */
    public static Andmebaas alustaProgramm() {
        return new Andmebaas();
    }

    /**
     * Meetod programmi esimese osa töötlemiseks, kasutaja, ülesande ja taimeri valik/loomine
     *
     * @param andmebaas Võtab argumendiks kasutatava andmebaasi
     * @return Tagastab valitud või loodud taimeri isendi, mis kuulub kindlale kasutajale ja kindlale ülesandele
     */
    public static void kasutajaValik(Andmebaas andmebaas, Scanner luger) {
        ArrayList<CLINimekirjaElement> kasutajaNimed = new ArrayList<CLINimekirjaElement>();
        HashMap<String, Kasutaja> kasutajateValik = new HashMap<>();

        for (int i = 0; i < kasutajad.size(); i++) {
            Kasutaja kasutaja = kasutajad.get(i);
            kasutajaNimed.add(new CLINimekirjaElement(i, kasutaja.getNimi(),
                    CLINimekirjaElement.Tuup.OBJEKT));
            kasutajateValik.put(kasutaja.getNimi(), kasutaja);
        }

        CLIValjund valjund = new CLIValjund();
        valjund.lisaValjundisse(valikuAken(kasutajaNimed, "kasutaja"));
        valjund.valjastaValjund();

        String sisend = luger.next();


        if (lopetusSoned.contains(sisend)) {
            //System.exit(0);
        } else if (tagasiSoned.contains(sisend)) {
            // System.exit(0);
        } else if (uusSoned.contains(sisend)) {

            valjund.lisaValjundisse(looUusObjektAken("kasutaja"));
            valjund.valjastaValjund();
            sisend = luger.next();
            kasutajad.add(andmebaas.looUusKasutaja(sisend));
            kasutajaValik(andmebaas, luger);
        } else if (kasutajateValik.containsKey(sisend)) {
            ulesandeValik(andmebaas, luger, kasutajateValik.get(sisend));
        }
    }

    public static void ulesandeValik(Andmebaas andmebaas, Scanner luger, Kasutaja kasutaja) {
        CLITekst valiUlesanneTekst = new CLITekst("Vali ülesanne: ", CLITeema.TEKSTI_VARV, true);
        ArrayList<CLINimekirjaElement> ulesandeNimed = new ArrayList<CLINimekirjaElement>();
        HashMap<String, Ulesanne> ulesanneteValik = new HashMap<>();

        for (int i = 0; i < kasutaja.getUlesanded().size(); i++) {
            Ulesanne ulesanne = kasutaja.getUlesanded().get(i);
            ulesandeNimed.add(new CLINimekirjaElement(i, ulesanne.getNimi(),
                    CLINimekirjaElement.Tuup.OBJEKT));
            ulesanneteValik.put(ulesanne.getNimi(), ulesanne);
        }


        CLIValjund valjund = new CLIValjund();
        valjund.lisaValjundisse(valikuAken(ulesandeNimed, "ülesanne"));
        valjund.valjastaValjund();

        String sisend = luger.next();

        if (ulesanneteValik.containsKey(sisend)) {
            uusPomodoro(andmebaas, luger, ulesanneteValik.get(sisend));
        } else if (lopetusSoned.contains(sisend)) {
            // System.exit(0);
        } else if (tagasiSoned.contains(sisend)) {
            kasutajaValik(andmebaas, luger);
        } else if (uusSoned.contains(sisend)) {
            valjund.lisaValjundisse(looUusObjektAken("ülesanne"));
            valjund.valjastaValjund();
            sisend = luger.next();
            andmebaas.looUusUlesanne(sisend, kasutaja);
            ulesandeValik(andmebaas, luger, kasutaja);
        }
    }

    public static void uusPomodoro(Andmebaas andmebaas, Scanner luger, Ulesanne ulesanne) {
        CLISisend produktiivsusAjaSisend = new CLISisend("Sisesta produktiivsusaeg: ", CLITeema.TEKSTI_VARV, 2);
        CLISisend puhkeAjaSisend = new CLISisend("Sisesta puhkeaeg: ", CLITeema.TEKSTI_VARV, 2);
        CLIAknaElement[] sisendid = {produktiivsusAjaSisend, puhkeAjaSisend};
        CLIAken aken = new CLIAken("Alusta pomodorot", sisendid, 4, 2);
        CLIValjund valjund = new CLIValjund();
        int aknaLaius = 50;
        int x = (KonsooliFunktsioonid.getKonsooliLaius() - aknaLaius) / 2;
        int y = (KonsooliFunktsioonid.getKonsooliPikkus() - aken.valjastaElemendiPikkus()) / 2;
        valjund.lisaValjundisse(KonsooliFunktsioonid.puhastaKonsool());
        valjund.lisaValjundisse(aken.looCLIElement(x, y, 60));
        valjund.lisaValjundisse(produktiivsusAjaSisend.taastaKursoriAsukoht());
        valjund.valjastaValjund();
        Duration produktiivneAeg = sisendiTeisendusAjaks(luger.next());
        valjund.lisaValjundisse(puhkeAjaSisend.taastaKursoriAsukoht());
        valjund.valjastaValjund();
        Duration puhkeAeg = sisendiTeisendusAjaks(luger.next());
        Pomodoro pomodoro = andmebaas.looUusPomodoro(produktiivneAeg, puhkeAeg, ulesanne);
        pomodoroTaimer(andmebaas, luger, pomodoro);

    }

    public static void pomodoroTaimer(Andmebaas andmebaas, Scanner luger, Pomodoro pomodoro) {
        luger.nextLine();
        taimer(luger, pomodoro.getProduktiivneAeg(), "Produktiivne aeg");
        teateAken(luger, Lõpusõnad.lõpusõnad());
        taimer(luger, pomodoro.getPuhkeAeg(), "Puhke aeg");
        if (kasUuesti(luger)) {
            pomodoroTaimer(andmebaas, luger, pomodoro);
        }
    }

    public static boolean taimer(Scanner luger, Duration aeg, String tuup) {
        System.out.print(KonsooliFunktsioonid.puhastaKonsool() +
                EscKoodid.muudaKursoriAsukohta(0, 0) + EscKoodid.salvestaKursoriAsukoht());
        CLIEdenemisRiba edenemisRiba = new CLIEdenemisRiba(1, 0);
        CLIAknaElement[] edenemisRibaElement = {edenemisRiba};
        CLIAken edenemisRibaKast = new CLIAken("", edenemisRibaElement, 2, 1);

        CLIKuvaAeg ajaKuvar = new CLIKuvaAeg(aeg.toMillis(), 1);
        CLIAknaElement[] elemendid = {edenemisRibaKast, ajaKuvar};

        CLIAken aken = new CLIAken(tuup, elemendid, 2, 1);

        int aknaLaius = 100;
        int x = (KonsooliFunktsioonid.getKonsooliLaius() - aknaLaius) / 2;
        int y = (KonsooliFunktsioonid.getKonsooliPikkus() - aken.valjastaElemendiPikkus()) / 2;

        System.out.print(aken.looCLIElement(x, y, aknaLaius));
        Taimer taimer = new Taimer(aeg, aken, edenemisRiba, ajaKuvar);
        taimer.alustaLoendust();
        while (!luger.hasNextLine());
        taimer.lopetaLoendus();
        if (taimer.getProtsent() >= 90) return true;
        else return false;
    }

    public static boolean kasUuesti(Scanner luger) {
        CLISisend sisend = new CLISisend("Uuesti? ", CLITeema.TEKSTI_VARV, 2);
        CLIAknaElement[] elemendid = {sisend};
        CLIAken aken = new CLIAken("Pomodoro tehtud", elemendid, 4, 2);

        int aknaLaius = 75;
        int x = (KonsooliFunktsioonid.getKonsooliLaius() - aknaLaius) / 2;
        int y = (KonsooliFunktsioonid.getKonsooliPikkus() - aken.valjastaElemendiPikkus()) / 2;

        System.out.print(KonsooliFunktsioonid.puhastaKonsool() + aken.looCLIElement(x, y, aknaLaius));

        String sisestatud = luger.next();
        if (sisestatud.equals("jah") ||sisestatud.equals("Jah")) return true;
        return false;
    }

    public static void teateAken(Scanner luger, String teade) {
        luger.nextLine();
        CLITekst tekst = new CLITekst(teade, CLITeema.TEKSTI_VARV, true);
        CLIAknaElement[] elemendid = {tekst};
        CLIAken aken = new CLIAken("Teade", elemendid, 4, 2);

        int aknaLaius = 75;
        int x = (KonsooliFunktsioonid.getKonsooliLaius() - aknaLaius) / 2;
        int y = (KonsooliFunktsioonid.getKonsooliPikkus() - aken.valjastaElemendiPikkus()) / 2;

        System.out.print(KonsooliFunktsioonid.puhastaKonsool() + aken.looCLIElement(x, y, aknaLaius));
        luger.nextLine();
    }


//        try (Scanner scanner = new Scanner(System.in)) {
//            String input;
//            Kasutaja valitud = null;
//
//            while (true) {
//                System.out.println("Valige enda kasutaja: ");
//                System.out.println(andmebaas.tagastaKasutajateOlemid());
//                input = scanner.nextLine();
//
//                if (input.equalsIgnoreCase("exit")) {
//                    System.out.println("Lõpetan programmi");
//                    break;
//                }
//
//               // Otsib andmebaasi kasutajate seast soovitud kasutajat
//                for (Kasutaja kasutaja : andmebaas.tagastaKasutajateOlemid()) {
//                    if (kasutaja.getNimi().equalsIgnoreCase(input)) {
//                        System.out.println("Valisite kasutaja: " + kasutaja.getNimi());
//                        valitud = kasutaja;
//                        break;
//                    }
//                }
//
//                if (valitud != null) {
//                    System.out.println("Kas soovite valida ülesannet või luua uut? (uus/valik) ");
//                    String valik = scanner.nextLine();
//                    if (valik.equalsIgnoreCase("uus")) {
//                        System.out.println("Sisesta uue ülesande nimi: ");
//                        String nimi = scanner.nextLine();
//                        Ulesanne ulesanne = andmebaas.looUusUlesanne(nimi, valitud); // Loob uue ülesande valitud kasutajale, mille nimetuseks saab sisestatud nimi
//                        System.out.println("Uus ülesanne loodud");
//                        System.out.println("Kas soovite valida Pomodoro taimerit või luua uut? (uus/valik) ");
//                        valik = scanner.nextLine();
//                        if (valik.equalsIgnoreCase("uus")) {
//                            System.out.println("Sisesta uue Pomodoro produktiivsusaeg (MM:SS)");
//                            String aeg1String = scanner.nextLine();
//                            Duration aeg1 = sisendiTeisendusAjaks(aeg1String); // Kasutab meetodit, et teisendada sisestatud aeg String tüübist Duration tüüpi
//                            System.out.println("Sisesta uue Pomodoro puhkeaeg (MM:SS)");
//                            String aeg2String = scanner.nextLine();
//                            Duration aeg2 = sisendiTeisendusAjaks(aeg2String);
//                            Pomodoro pomodoro = andmebaas.looUusPomodoro(aeg1, aeg2, ulesanne);
//                            System.out.println("Uus pomodoro loodud");
//                            pomodoros.add(pomodoro);
//                            break;
//                        } else if (valik.equalsIgnoreCase("valik")) {
//                            System.out.println(andmebaas.tagastaPomodorodeOlemid(ulesanne.getUlesandeID()));
//                            ArrayList<Pomodoro> pomodorod = andmebaas.tagastaPomodorodeOlemid(ulesanne.getUlesandeID());
//                            System.out.println("Mitmendat taimerit soovite?"); // Võimaldab valida andmebaasi pomodoro taimerite seast ning valib taimerite järjendist soovitud taimeri.
//                            int soov2 = Integer.parseInt(scanner.nextLine());
//                            System.out.println("Valisite taimeri: ");
//                            Pomodoro pomodoro = pomodorod.get(soov2 - 1);
//                            System.out.println(pomodoro);
//                            pomodoros.add(pomodoro);
//                            break;
//                        } else {
//                            System.out.println("Valik ei sobi");
//                        }
//                    } else if (valik.equalsIgnoreCase("valik")) {
//                        System.out.println(andmebaas.tagastaUlesanneteOlemid(valitud.getKasutajaID())); // Võimaldab valida andmebaasi ülesannete listist soovitud ülesannet
//                        ArrayList<Ulesanne> ulesanded = andmebaas.tagastaUlesanneteOlemid(valitud.getKasutajaID());
//                        System.out.println("Mitmendat ülesannet soovite?");
//                        int soov = Integer.parseInt(scanner.nextLine());
//                        System.out.println("Valisite ülesande: ");
//                        Ulesanne ulesanne = ulesanded.get(soov - 1);
//                        System.out.println(ulesanne.getNimi());
//                        System.out.println("Kas soovite valida Pomodoro taimerit või luua uut? (uus/valik)");
//                        valik = scanner.nextLine();
//                        if (valik.equalsIgnoreCase("uus")) {
//                            String aeg1String = scanner.nextLine();
//                            Duration aeg1 = sisendiTeisendusAjaks(aeg1String);
//                            System.out.println("Sisesta uue Pomodoro puhkeaeg (MM:SS)");
//                            String aeg2String = scanner.nextLine();
//                            Duration aeg2 = sisendiTeisendusAjaks(aeg2String);
//                            Pomodoro pomodoro = andmebaas.looUusPomodoro(aeg1, aeg2, ulesanne);
//                            System.out.println("Uus pomodoro loodud");
//                            pomodoros.add(pomodoro);
//                            break;
//                        } else if (valik.equalsIgnoreCase("valik")) {
//                            System.out.println(andmebaas.tagastaPomodorodeOlemid(ulesanne.getUlesandeID()));
//                            ArrayList<Pomodoro> pomodorod = andmebaas.tagastaPomodorodeOlemid(ulesanne.getUlesandeID());
//                            System.out.println("Mitmendat taimerit soovite?");
//                            int soov2 = Integer.parseInt(scanner.nextLine());
//                            System.out.println("Valisite taimeri: ");
//                            Pomodoro pomodoro = pomodorod.get(soov2 - 1);
//                            pomodoros.add(pomodoro);
//                            break;
//                        } else {
//                            System.out.println("Valik ei sobi");
//                        }
//                    } else {
//                        System.out.println("Valik ei sobi");
//                    }
//                } else {
//                    System.out.println("Kasutajat ei leitud");
//                }
//            }
//        }
//        return pomodoros.getFirst();
//    }

    /**
     * Meetod String formaadis sisestatud aja teisendamiseks Duration tüüpi
     * @param sisend Sisend-String, meetodis võetakse kasutaja enda inputist.
     * @return Tagastab sisestatud ajapikkuse Duration tüübina, et oleks võimalik luua Pomodoro isend
     */
    public static Duration sisendiTeisendusAjaks(String sisend) {
        // https://www.baeldung.com/regular-expressions-java
        String formaat1String = "(\\d+):(\\d\\d)";
        Pattern formaat1 = Pattern.compile(formaat1String);
        Matcher formaadiKontroll1 = formaat1.matcher(sisend);

        String formaat2String = "(\\d+)";
        Pattern formaat2 = Pattern.compile(formaat2String);
        Matcher formaadiKontroll2 = formaat2.matcher(sisend);

        if (formaadiKontroll1.find()) {
            int minutid = Integer.parseInt(formaadiKontroll1.group(1));
            int sekundid = Integer.parseInt(formaadiKontroll1.group(2));
            int sekundidKokku = minutid * 60 + sekundid;
            return Duration.ofSeconds(sekundidKokku);
        }
        if (formaadiKontroll2.find()) {
            int minutid = Integer.parseInt(formaadiKontroll2.group(1));
            int sekundidKokku = minutid * 60;
            return Duration.ofSeconds(sekundidKokku);
        }

        return null;
    }
}
