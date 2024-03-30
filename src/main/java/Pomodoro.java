import java.time.LocalDateTime;

public class Pomodoro {
    private LocalDateTime kuupaev;

    private int produktiivsusAegSekundites;
    private int puhkeAegSekundites;

    private int pomodoroTsukleid;
    private int produktiivsusAegKokku = 0;

    private boolean kasTehtud;

    public Pomodoro(int produktiivsusAegSekundites, int puhkeAegSekundites, boolean kasTehtud) {
        this.kuupaev = LocalDateTime.now();
        this.produktiivsusAegSekundites = produktiivsusAegSekundites;
        this.puhkeAegSekundites = puhkeAegSekundites;
        this.kasTehtud = kasTehtud;
    }

    public Pomodoro(int produktiivsusAegSekundites, int puhkeAegSekundites) {
        this(produktiivsusAegSekundites, puhkeAegSekundites, false);
    }

    public void tsukkelTehtud() {
        pomodoroTsukleid++;
        produktiivsusAegKokku += produktiivsusAegSekundites;
    }

    public int getProduktiivsusAegSekundites() {
        return produktiivsusAegSekundites;
    }

    public int getPuhkeAegSekundites() {
        return puhkeAegSekundites;
    }
}
