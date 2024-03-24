
// https://en.wikipedia.org/wiki/ANSI_escape_code - Escape koodid -> kursori asukoha ning värvide muutmiseks

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Taimer {
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


    public Taimer(int aeg) {
        esialgneAeg = aeg;
        aegaJarel = aeg;
    }

    public void alustaLoendust() {
        System.out.print("\033[1;1H");  // Paneb kursori ülemisse vasakusse nurka
        System.out.print("\033[0J");  // Kustuta kõik alates kursorist

        System.out.print("\033[2E");  // Aseta kursor kaks rida edasi vasakusse äärde
        System.out.print(rohelineVarv);
        System.out.print("Sisend: ");
        System.out.print(tavalineVarv);
        taimer.scheduleAtFixedRate(loendaja, 0, 1000);  // Alustab loendust
        while(!klaviatuuriSisend.hasNextLine());  // Suht halb variant, võiks midagi paremat välja mõelda
        taimer.cancel();  // Lõpetab loenduse

        System.out.print("\033[1;1H");  // Paneb kursori ülemisse vasakusse nurka
        System.out.print("\033[0J");  // Kustuta kõik alates kursorist
    }

    public void valjastaAeg(int aegSekundites) {
        System.out.print("\033[s");  // Salvestab kursori positsiooni (Et saaks hiljem konsooli kirjutada)
        // Kui loendab, aega, siis on ees miinus märk ("-"), kui on üle aja läinud, siis on ees pluss märk ("+")
        char mark = switch(Integer.signum(aegSekundites)) {
            case 1 -> '-';
            case -1 -> '+';
            default -> ' ';
        };

        System.out.print("\033[2F");  // Kursor läheb kaks rida üles ja vasakusse äärde
        System.out.print("\r");  // Kustututab reas (et näeks IDE-s parem välja, konsoolis pole vaja)

        // Näitsab protsentribal, kui palju aega eesmärgist on kulunud
        int protsent = Math.max(0, Math.round(100 * ((float) aegSekundites / esialgneAeg)));

        System.out.printf("%s[%s", lillaVarv, tavalineVarv);
        System.out.printf("%s%s", rohelineVarv, "=".repeat(100 - protsent));
        System.out.printf("%s%s", punaneVarv, "-".repeat(protsent));
        System.out.printf("%s]%s", lillaVarv, tavalineVarv);

        // Arvutab minutid ja sekundid
        aegSekundites = Math.abs(aegSekundites);
        int minuteid = aegSekundites / 60;
        int sekundeid = aegSekundites % 60;

        // Näitab aega protsentriba all, joondatud keskele
        int minutiMarke = Math.max(2, String.valueOf(minuteid).length());
        System.out.printf("\n%s", " ".repeat(50 - minutiMarke));  // Kursor keskele
        System.out.printf("%s%c%02d:%02d%s", lillaVarv, mark, minuteid, sekundeid, tavalineVarv);

        System.out.print("\033[u");  // Taastab kursori salvestatud positsiooni, läheb algsesse kohta tagasi
    }
}
