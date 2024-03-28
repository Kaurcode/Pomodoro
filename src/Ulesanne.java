import java.util.ArrayList;

public class Ulesanne {
    private String nimi;
    private ArrayList<Pomodoro> pomodorod;

    public Ulesanne(String nimi) {
        this.nimi = nimi;
        pomodorod = new ArrayList<Pomodoro>();
    }

    public void lisaPomodoro(Pomodoro pomodoro) {
        pomodorod.add(pomodoro);
    }
}
