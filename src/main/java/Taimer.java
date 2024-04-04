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
    private CLIKuvaAeg ajaKuvar;

    public Taimer(Duration aeg, CLIAken aken, CLIEdenemisRiba edenemisRiba, CLIKuvaAeg ajaKuvar) {
        this.aegMs = aeg.toMillis();
        this.loendus = this.aegMs;
        this.taimer = new Timer();
        this.loendaja = looLoendaja();
        this.aken = aken;
        this.edenemisRiba = edenemisRiba;
        this.ajaKuvar = ajaKuvar;
    }

    /**
     * Loob TimerTask objekti, mida käitatakse iga mingisuguse aja tagant
     * @return Timertask objekt
     */
    private TimerTask looLoendaja() {
        return new TimerTask() {
            @Override
            public void run() {
                loendus -= 10;
                protsent = 100 - Math.max(0, 100 * ((float) loendus / aegMs));
                edenemisRiba.setProtsent(protsent);
                ajaKuvar.setAegMs(loendus);
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
