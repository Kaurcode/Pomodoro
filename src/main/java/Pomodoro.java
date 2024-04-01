import java.sql.Timestamp;
import java.time.Duration;

public class Pomodoro implements Comparable<Pomodoro> {
    private int pomodoroID;
    private Duration produktiivneAeg;
    private Duration puhkeAeg;

    private int kordused;
    private Duration produktiivsusAegKokku;
    private Timestamp sisestusAeg;

    public Pomodoro(int pomodoroID, Duration produktiivneAeg, Duration puhkeAeg, int kordused,
                    Duration produktiivsusAegKokku, Timestamp sisestusAeg) {
        this.pomodoroID = pomodoroID;
        this.produktiivneAeg = produktiivneAeg;
        this.puhkeAeg = puhkeAeg;
        this.kordused = kordused;
        this.produktiivsusAegKokku = produktiivsusAegKokku;
        this.sisestusAeg = sisestusAeg;
    }

    public Pomodoro(int pomodoroID, Duration produktiivneAeg, Duration puhkeAeg) {
        this.pomodoroID = pomodoroID;
        this.produktiivneAeg = produktiivneAeg;
        this.puhkeAeg = puhkeAeg;
        kordused = 0;
    }

    public Timestamp getSisestusAeg() {
        return sisestusAeg;
    }

    // Testimiseks
    @Override
    public String toString() {
        return "Pomodoro produktiivsusajaga " + produktiivneAeg + " ja puhkeajaga " + puhkeAeg;
    }

    @Override
    public int compareTo(Pomodoro vorreldavPomodoro) {
        return vorreldavPomodoro.getSisestusAeg().compareTo(this.getSisestusAeg());
    }
}