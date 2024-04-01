import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class Taimer {
    long aegMs;
    long loendus;
    Timer taimer;
    TimerTask loendaja;

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
            }
        };
    }

    public Duration alustaLoendust() {
        taimer.scheduleAtFixedRate(loendaja, 0, 10);

        // Kood taimeri lõpetamiseks (kasutaja sisend)

        return Duration.ofMillis(this.loendus * -1 + this.aegMs);
    }
}
