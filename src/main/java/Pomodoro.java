import java.time.Duration;

public class Pomodoro {
    private int pomodoroID;
    private Duration produktiivneAeg;
    private Duration puhkeAeg;

    private int kordused;
    private Duration produktiivsusAegKokku;

    public Pomodoro(int pomodoroID, Duration produktiivneAeg, Duration puhkeAeg, int kordused,
                    Duration produktiivsusAegKokku) {
        this.pomodoroID = pomodoroID;
        this.produktiivneAeg = produktiivneAeg;
        this.puhkeAeg = puhkeAeg;
        this.kordused = kordused;
        this.produktiivsusAegKokku = produktiivsusAegKokku;
    }

    public Pomodoro(int pomodoroID, Duration produktiivneAeg, Duration puhkeAeg) {
        this.pomodoroID = pomodoroID;
        this.produktiivneAeg = produktiivneAeg;
        this.puhkeAeg = puhkeAeg;
        kordused = 0;
    }

    // Testimiseks
    @Override
    public String toString() {
        return "Pomodoro{" +
                "pomodoroID=" + pomodoroID +
                ", produktiivneAeg=" + produktiivneAeg +
                ", puhkeAeg=" + puhkeAeg +
                ", kordused=" + kordused +
                ", produktiivsusAegKokku=" + produktiivsusAegKokku +
                '}';
    }
}