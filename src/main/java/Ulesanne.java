import java.util.ArrayList;

public class Ulesanne {
    private int ulesandeID;
    private String nimi;
    private ArrayList<Pomodoro> pomodorod;

    public Ulesanne(int ulesandeID, String nimi) {
        this.ulesandeID = ulesandeID;
        this.nimi = nimi;
        pomodorod = new ArrayList<Pomodoro>();
    }

    public int getUlesandeID() {
        return ulesandeID;
    }

    public String getNimi() {
        return nimi;
    }

    public void lisaPomodoro(Pomodoro pomodoro) {
        pomodorod.add(pomodoro);
    }

    @Override
    public String toString() {
        return nimi;
    }
}
