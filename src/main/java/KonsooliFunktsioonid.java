import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KonsooliFunktsioonid {
    private static int konsooliLaius;
    private static int konsooliPikkus;

    public static int[] punane = new int[1530];
    public static int[] roheline = new int[1530];
    public static int[] sinine = new int[1530];

    public static int[] konsooliSuurus(Scanner luger, String sonum) {
        CLIValjund valjund = new CLIValjund();
        valjund.lisaValjundisse(EscKoodid.peidaKursor());
        valjund.lisaValjundisse(EscKoodid.muudaKursoriAsukohta(0, 0));
        valjund.lisaValjundisse(EscKoodid.kustutaValjundAlatesKursorist());
        valjund.lisaValjundisse(EscKoodid.muudaKursoriAsukohta(5000, 5000));
        valjund.valjastaValjund();

        int[] koordinaadid = tagastaKursoriAsukoht(luger, sonum);
        konsooliPikkus = koordinaadid[0];
        konsooliLaius = koordinaadid[1];

        valjund.lisaValjundisse(EscKoodid.muudaKursoriAsukohta(0, 0));
        valjund.lisaValjundisse(EscKoodid.kustutaValjundAlatesKursorist());
        valjund.lisaValjundisse(EscKoodid.naitaKursor());
        valjund.valjastaValjund();

        return koordinaadid;
    }

    public static String puhastaKonsool() {
        return EscKoodid.peidaKursor() +
                EscKoodid.salvestaKursoriAsukoht() +
                EscKoodid.muudaKursoriAsukohta(0, 0) +
                EscKoodid.kustutaValjundAlatesKursorist() +
                EscKoodid.taastaKursoriAsukoht() +
                EscKoodid.naitaKursor();
    }

    public static int[] tagastaKursoriAsukoht(Scanner luger, String sonum) {
        CLIValjund valjund = new CLIValjund();
        valjund.lisaValjundisse(EscKoodid.tagastaKursoriAsukoht());
        String koordinaadid;
        valjund.valjastaValjund();

        valjund.lisaValjundisse(String.format(" %s - ", sonum));
        valjund.valjastaValjund();

        koordinaadid = luger.next();

        return teisendaKoordinaatideks(koordinaadid);
    }

    public static int[] teisendaKoordinaatideks(String sisend) {
        int[] koordinaadid = {-1, -1};

        String koordinaatideFormaatString = "\033\\[(\\d+);(\\d+)R";
        Pattern koordinaatideFormaat = Pattern.compile(koordinaatideFormaatString);
        Matcher formaadiKontroll = koordinaatideFormaat.matcher(sisend);

        if (formaadiKontroll.find()) {
            int rida = Integer.parseInt(formaadiKontroll.group(1));
            int veerg = Integer.parseInt(formaadiKontroll.group(2));
            koordinaadid[0] = rida;
            koordinaadid[1] = veerg;
        } else {
            System.out.println("Viga koordinaatide leidmisel");
        }

        return koordinaadid;
    }

    public static int getKonsooliLaius() {
        return konsooliLaius;
    }

    public static int getKonsooliPikkus() {
        return konsooliPikkus;
    }

    public static void looRGB() {
        // 1. samm: Punane -> Kollane
        for (int i = 0; i < 255; i++) {
            punane[i] = 255;
            roheline[i] = i;
            sinine[i] = 0;
        }
        // 2. samm: Kollane -> Roheline
        for (int i = 255; i < 510; i++) {
            punane[i] = 255 - (i - 255);
            roheline[i] = 255;
            sinine[i] = 0;
        }
        // 3. samm: Roheline -> Tsüaan
        for (int i = 510; i < 765; i++) {
            punane[i] = 0;
            roheline[i] = 255;
            sinine[i] = i - 510;
        }
        // 4. samm: Tsüaan -> Sinine
        for (int i = 765; i < 1020; i++) {
            punane[i] = 0;
            roheline[i] = 255 - (i - 765);
            sinine[i] = 255;
        }
        // 5. samm: Sinine -> Purpurpunane
        for (int i = 1020; i < 1275; i++) {
            punane[i] = i - 1020;
            roheline[i] = 0;
            sinine[i] = 255;
        }
        // 6. samm: Purpurpunane -> Punane
        for (int i = 1275; i < 1530; i++) {
            punane[i] = 255;
            roheline[i] = 0;
            sinine[i] = 255 - (i - 1275);
        }
    }
}
