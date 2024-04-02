import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class Taimer {
    private long aegMs;
    private long loendus;
    private Timer taimer;
    private TimerTask loendaja;
    private float protsent;

    public Taimer(Duration aeg) {
        this.aegMs = aeg.toMillis();
        this.loendus = this.aegMs;
        this.taimer = new Timer();
        this.loendaja = looLoendaja();
    }

    private TimerTask looLoendaja() {
        return new TimerTask() {
            @Override
            public void run() {
                loendus -= 10;
                protsent = 100 - Math.max(0, 100 * ((float) loendus / aegMs));
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
