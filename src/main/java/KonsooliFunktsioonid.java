import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KonsooliFunktsioonid {
    private static int konsooliLaius;
    private static int konsooliPikkus;

    public static int[] konsooliSuurus(String sonum) {
        CLIValjund valjund = new CLIValjund();
        valjund.lisaValjundisse(EscKoodid.peidaKursor());
        valjund.lisaValjundisse(EscKoodid.muudaKursoriAsukohta(0, 0));
        valjund.lisaValjundisse(EscKoodid.kustutaValjundAlatesKursorist());
        valjund.lisaValjundisse(EscKoodid.muudaKursoriAsukohta(5000, 5000));
        valjund.valjastaValjund();

        int[] koordinaadid = tagastaKursoriAsukoht(sonum);
        konsooliPikkus = koordinaadid[0];
        konsooliLaius = koordinaadid[1];

        valjund.lisaValjundisse(EscKoodid.muudaKursoriAsukohta(0, 0));
        valjund.lisaValjundisse(EscKoodid.kustutaValjundAlatesKursorist());
        valjund.lisaValjundisse(EscKoodid.naitaKursor());
        valjund.valjastaValjund();

        return koordinaadid;
    }

    public static int[] tagastaKursoriAsukoht(String sonum) {
        CLIValjund valjund = new CLIValjund();
        valjund.lisaValjundisse(EscKoodid.tagastaKursoriAsukoht());
        String koordinaadid;
        try (Scanner luger = new Scanner(System.in)) {
            valjund.valjastaValjund();

            valjund.lisaValjundisse(String.format(" %s - ", sonum));
            valjund.valjastaValjund();

            koordinaadid = luger.next();
        }

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
}
