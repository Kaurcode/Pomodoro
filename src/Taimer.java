import java.util.Timer;
import java.util.TimerTask;

public class Taimer {
    private int aegaJarel;
    private int esialgneAeg;
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
        System.out.print("\033[H\033[2J");  // Kustutab ekraani
        System.out.flush();
        taimer.scheduleAtFixedRate(loendaja, 0, 1000);  // Alustab loeandust
    }

    public void valjastaAeg(int aegSekundites) {
        System.out.print("\b".repeat(54));  // Et IDE-s näeks parem välja, konsoolis pole vaja kasutada
        System.out.print("\033[H");  // https://www.javatpoint.com/how-to-clear-screen-in-java -> Töötab ainult konsoolis

        // Kui loendab, aega, siis on ees miinus märk ("-"), kui on üle aja läinud, siis on ees pluss märk ("+")
        char mark = switch(Integer.signum(aegSekundites)) {
            case 1 -> '-';
            case -1 -> '+';
            default -> ' ';
        };

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
    }
}
