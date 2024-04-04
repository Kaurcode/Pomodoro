import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class Taimer {
    private long aegMs;
    private long loendus;
    private Timer taimer;
    private TimerTask loendaja;
    private float protsent;
    private CLIAken aken;
    private CLIEdenemisRiba edenemisRiba;

    public Taimer(Duration aeg, CLIAken aken, CLIEdenemisRiba edenemisRiba) {
        this.aegMs = aeg.toMillis();
        this.loendus = this.aegMs;
        this.taimer = new Timer();
        this.loendaja = looLoendaja();
        this.aken = aken;
        this.edenemisRiba = edenemisRiba;
    }

    private TimerTask looLoendaja() {
        return new TimerTask() {
            @Override
            public void run() {
                loendus -= 10;
                protsent = 100 - Math.max(0, 100 * ((float) loendus / aegMs));
                edenemisRiba.setProtsent(protsent);
                System.out.print(aken.uuendaCLIElement());
            }
        };
    }

    public void alustaLoendust() {
        taimer.scheduleAtFixedRate(loendaja, 0, 10);
    }

    public Duration lopetaLoendus() {
        taimer.cancel();
        return Duration.ofMillis(this.loendus * -1 + this.aegMs);
    }

    public float getProtsent() {
        return protsent;
    }
}
