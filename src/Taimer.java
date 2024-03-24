import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Taimer {
    private int aegaJarel;
    private int esialgneAeg;
    Scanner loeKlaviatuurilt = new Scanner(System.in);
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
        System.out.println("\033[0J");  // Kustuta kõik alates kursorist
        System.out.print("\033[2E");  // Aseta kursor kaks rida edasi vasakusse äärde
        System.out.print("Sisend: ");
        taimer.scheduleAtFixedRate(loendaja, 0, 1000);  // Alustab loeandust
        while (!loeKlaviatuurilt.hasNextLine());  // Väga halb variant, tuleks midagi paremat välja mõelda
        taimer.cancel();  // Lõpetab loenduse
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
        System.out.printf("[%s%s]\n", "#".repeat(100 - protsent), "-".repeat(protsent));

        // Arvutab minutid ja sekundid
        aegSekundites = Math.abs(aegSekundites);
        int minuteid = aegSekundites / 60;
        int sekundeid = aegSekundites % 60;

        // Näitab aega protsentriba all, joondatud keskele
        int minutiMarke = Math.max(2, String.valueOf(minuteid).length());
        System.out.printf("%s%c%02d:%02d", " ".repeat(50 - minutiMarke), mark, minuteid, sekundeid);

        System.out.print("\033[u");  // Taastab kursori salvestatud positsiooni, läheb algsesse kohta tagasi
    }
}
