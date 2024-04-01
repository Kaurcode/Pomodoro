import java.util.ArrayList;

public class Ulesanne {
    private int ulesandeID;
    private String nimi;
    private int kasutajaID;
    private ArrayList<Pomodoro> pomodorod;

    public Ulesanne(String nimi, int kasutajaID) {
        this.nimi = nimi;
        this.kasutajaID = kasutajaID;
        pomodorod = new ArrayList<Pomodoro>();
    }
    public Ulesanne(int ulesandeID, String nimi, int kasutajaID) {
        this.ulesandeID = ulesandeID;
        this.nimi = nimi;
        this.kasutajaID = kasutajaID;
        pomodorod = new ArrayList<Pomodoro>();
    }

    public int getUlesandeID() {
        return ulesandeID;
    }

    public String getNimi() {
        return nimi;
    }

    public int getKasutajaID() {
        return kasutajaID;
    }

    public void setUlesandeID(int ulesandeID) {
        this.ulesandeID = ulesandeID;
    }

    public void lisaPomodoro(Pomodoro pomodoro) {
        pomodorod.add(pomodoro);
    }

    @Override
    public String toString() {
        return nimi;
    }
}
