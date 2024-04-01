import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;

public class Ulesanne implements Comparable<Ulesanne> {
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

    public void lisaPomodorodNimekirja(ArrayList<Pomodoro> pomodorod) {
        this.pomodorod.addAll(pomodorod);
        Collections.sort(this.pomodorod);
    }

    public ArrayList<Pomodoro> getPomodorod() {
        return pomodorod;
    }

    @Override
    public String toString() {
        return nimi;
    }

    @Override
    public int compareTo(Ulesanne vorreldavUlesanne) {
        Timestamp uusimSisestus = this.getPomodorod().getLast().getSisestusAeg();
        Timestamp vorreldavaUusimSisestus = vorreldavUlesanne.getPomodorod().getLast().getSisestusAeg();
        return uusimSisestus.compareTo(vorreldavaUusimSisestus);
    }
}
