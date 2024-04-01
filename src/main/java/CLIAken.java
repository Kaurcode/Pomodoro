import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLIAken {
    public static int[] CLIAknaSuurus(String sonum) {
        StringBuilder valjund = new StringBuilder();
        valjund.append(EscKoodid.muudaKursoriAsukohta(0, 0));
        valjund.append(EscKoodid.kustutaValjundAlatesKursorist());
        valjund.append(EscKoodid.muudaKursoriAsukohta(5000, 5000));
        valjund.append(EscKoodid.tagastaKursoriAsukoht());
        valjund.append(String.format(" %s - ", sonum));

        String koordinaadid;
        try (Scanner luger = new Scanner(System.in)) {
            System.out.print(valjund);
            System.out.flush();

            koordinaadid = luger.next();
        }

        valjund.delete(0, valjund.length());
        valjund.append(EscKoodid.muudaKursoriAsukohta(0, 0));
        valjund.append(EscKoodid.kustutaValjundAlatesKursorist());
        System.out.print(valjund);
        System.out.flush();

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
}
