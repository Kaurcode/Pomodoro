import java.util.ArrayList;

public class UlesanneVana {
    private String nimi;
    private ArrayList<Pomodoro> pomodorod;

    public UlesanneVana(String nimi) {
        this.nimi = nimi;
        pomodorod = new ArrayList<Pomodoro>();
    }

    public void lisaPomodoro(Pomodoro pomodoro) {
        pomodorod.add(pomodoro);
    }

    public String getNimi() {
        return nimi;
    }

    public String toString() {
        return "Ulesanne " + nimi;
    }
}
