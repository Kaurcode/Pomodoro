import java.time.Duration;

public class Pomodoro {
    private int pomodoroID;
    private Duration produktiivneAeg;
    private Duration puhkeAeg;

    private int kordused;
    private Duration produktiivsusAegKokku;
    private int ulesandeID;

    public Pomodoro(int pomodoroID, Duration produktiivneAeg, Duration puhkeAeg, int kordused,
                    Duration produktiivsusAegKokku, int ulesandeID) {
        this.pomodoroID = pomodoroID;
        this.produktiivneAeg = produktiivneAeg;
        this.puhkeAeg = puhkeAeg;
        this.kordused = kordused;
        this.produktiivsusAegKokku = produktiivsusAegKokku;
        this.ulesandeID = ulesandeID;
    }

    public Pomodoro(Duration produktiivneAeg, Duration puhkeAeg, int kordused, Duration produktiivsusAegKokku,
                    int ulesandeID) {
        this.produktiivneAeg = produktiivneAeg;
        this.puhkeAeg = puhkeAeg;
        this.kordused = kordused;
        this.produktiivsusAegKokku = produktiivsusAegKokku;
        this.ulesandeID = ulesandeID;
    }

    public Duration getProduktiivneAeg() {
        return produktiivneAeg;
    }

    public Duration getPuhkeAeg() {
        return puhkeAeg;
    }

    public int getUlesandeID() {
        return ulesandeID;
    }

    public void setPomodoroID(int pomodoroID) {
        this.pomodoroID = pomodoroID;
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