
// https://en.wikipedia.org/wiki/ANSI_escape_code - Escape koodid -> kursori asukoha ning värvide muutmiseks

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class TaimerVana {
    String tavalineVarv = "\033[0m";
    String rohelineVarv = "\033[92m";
    String punaneVarv = "\033[91m";
    String lillaVarv = "\033[95m";
    String kollaneVarv = "\033[93m";

    private int aegaJarel;
    private int esialgneAeg;
    Scanner klaviatuuriSisend = new Scanner(System.in);
    private Timer taimer = new Timer();

    TimerTask loendaja = new TimerTask() {
        @Override
        public void run() {
            aegaJarel--;
            valjastaAeg(aegaJarel);
        }
    };


    public TaimerVana(int aeg) {
        esialgneAeg = aeg * 100;
        aegaJarel = aeg * 100;
    }

    public void alustaLoendust() {
        StringBuilder valjund = new StringBuilder();
        valjund.append(EscKoodid.muudaKursoriAsukohta(1, 1));
        valjund.append(EscKoodid.kustutaValjundAlatesKursorist());  // Kustuta kõik alates kursorist

        valjund.append(EscKoodid.liigutaKursorAllaReaAlgusesse(2));  // Aseta kursor kaks rida edasi vasakusse äärde
        valjund.append(EscKoodid.heleRohelineTekst());
        valjund.append("Sisend: ");
        valjund.append(EscKoodid.tagastaTavaline());
        System.out.print(valjund);
        valjund.delete(0, valjund.length());
        taimer.scheduleAtFixedRate(loendaja, 0, 10);  // Alustab loendust
        while(!klaviatuuriSisend.hasNextLine());  // Suht halb variant, võiks midagi paremat välja mõelda
        taimer.cancel();  // Lõpetab loenduse

        valjund.append(EscKoodid.muudaKursoriAsukohta(1, 1));  // Paneb kursori ülemisse vasakusse nurka
        valjund.append(EscKoodid.kustutaValjundAlatesKursorist());  // Kustuta kõik alates kursorist
        System.out.print(valjund);
        valjund.delete(0, valjund.length());
    }

    public void valjastaAeg(int aegSekundites) {
        int valjundiLaius = 102;
        StringBuilder valjund = new StringBuilder();  // Et saaks väljastada kogu väljund korraga (vähem probleeme)
        valjund.append("\033[s");  // Salvestab kursori positsiooni (Et saaks hiljem konsooli kirjutada)
        valjund.append(EscKoodid.peidaKursor());  // Peidab kursori -> ei ole näha liikumist

        // Kui loendab, aega, siis on ees miinus märk ("-"), kui on üle aja läinud, siis on ees pluss märk ("+")
        char mark = switch(Integer.signum(aegSekundites)) {
            case 1 -> '-';
            case -1 -> '+';
            default -> ' ';
        };
        valjund.append("\033[2F");  // Kursor läheb kaks rida üles ja vasakusse äärde
        valjund.append("\r");  // Kustututab reas (et näeks IDE-s parem välja, konsoolis pole vaja)

        // Näitsab protsentribal, kui palju aega eesmärgist on kulunud
        int protsent = 100 - Math.max(0, Math.round(100 * ((float) aegSekundites / esialgneAeg)));

        valjund.append(String.format("%s[%s", lillaVarv, tavalineVarv));
        valjund.append(CLIElemendid.edenemisRiba(protsent, valjundiLaius - 2, 1, esialgneAeg - aegaJarel, 1));
        valjund.append(String.format("%s]%s", lillaVarv, tavalineVarv));

        // Arvutab minutid ja sekundid
        aegSekundites = Math.abs(aegSekundites);
        int minuteid = aegSekundites / 6000;
        int sekundeid = aegSekundites / 100 % 60;

        // Näitab aega protsentriba all, joondatud keskele
        int minutiMarke = Math.max(2, String.valueOf(minuteid).length());
        valjund.append(String.format("\n%s", " ".repeat(valjundiLaius / 2 - minutiMarke)));  // Kursor keskele
        valjund.append(String.format("%s%c%02d:%02d%s", lillaVarv, mark, minuteid, sekundeid, tavalineVarv));

        valjund.append("\033[u");  // Taastab kursori salvestatud positsiooni, läheb algsesse kohta tagasi
        valjund.append(EscKoodid.naitaKursor());  // Näitab uuesti kursorit
        System.out.print(valjund);
        valjund.delete(0, valjund.length());
    }
}
