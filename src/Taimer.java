import java.util.Timer;
import java.util.TimerTask;

public class Taimer {
    private int aegaJarel = 300;
    private int esialgneAeg = aegaJarel;
    private Timer taimer = new Timer();

    TimerTask loendaja = new TimerTask() {
        @Override
        public void run() {
            aegaJarel--;
            valjastaAeg(aegaJarel);
        }
    };

    public void alustaLoendust() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        taimer.scheduleAtFixedRate(loendaja, 0, 1000);
    }

    public void valjastaAeg(int aegSekundites) {
        System.out.print("\b".repeat(54));  // Et IDE-s näeks parem välja, konsoolis pole vaja kasutada
        System.out.print("\033[H");  // https://www.javatpoint.com/how-to-clear-screen-in-java -> Töötab ainult konsoolis
        char mark = switch(Integer.signum(aegSekundites)) {
            case 1 -> '-';
            case -1 -> '+';
            default -> ' ';
        };
        int protsent = Math.max(0, Math.round(100 * ((float) aegSekundites / esialgneAeg)));
        System.out.printf("[%s%s]\n", "#".repeat(100 - protsent), "-".repeat(protsent));

        aegSekundites = Math.abs(aegSekundites);
        int minuteid = aegSekundites / 60;
        int sekundeid = aegSekundites % 60;

        int minutiMarke = Math.max(2, String.valueOf(minuteid).length());
        System.out.printf("%s%c%02d:%02d", " ".repeat(50 - minutiMarke), mark, minuteid, sekundeid);
    }
}
