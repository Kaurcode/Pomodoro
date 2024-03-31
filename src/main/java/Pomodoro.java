import java.time.LocalDateTime;
import java.time.LocalTime;

public class Pomodoro {
    private LocalDateTime kuupaev;

    private LocalTime produktiivsusAegSekundites;
    private LocalTime puhkeAegSekundites;

    private int pomodoroTsukleid;
    private int produktiivsusAegKokku = 0;

    private boolean kasTehtud;

    public Pomodoro(LocalTime produktiivsusAegSekundites, LocalTime puhkeAegSekundites, boolean kasTehtud) {
        this.kuupaev = LocalDateTime.now();
        this.produktiivsusAegSekundites = (produktiivsusAegSekundites);
        this.puhkeAegSekundites = (puhkeAegSekundites);
        this.kasTehtud = kasTehtud;
    }

    public Pomodoro(LocalTime produktiivsusAegSekundites, LocalTime puhkeAegSekundites) {
        this(produktiivsusAegSekundites, puhkeAegSekundites, false);
    }

    public LocalTime getProduktiivsusAegSekundites() {
        return produktiivsusAegSekundites;
    }

    public LocalTime getPuhkeAegSekundites() {
        return puhkeAegSekundites;
    }

    public String toString() {
        return "Pomodoro taimer produktiivsusajaga " + produktiivsusAegSekundites + " ja puhkeajaga " + puhkeAegSekundites;
    }
}