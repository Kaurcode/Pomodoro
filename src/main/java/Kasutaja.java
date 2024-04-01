import java.util.ArrayList;

public class Kasutaja {
    private int kasutajaID;
    private String nimi;
    private ArrayList<Ulesanne> ulesanded;

    public Kasutaja(int kasutajaID, String nimi) {
        this.kasutajaID = kasutajaID;
        this.nimi = nimi;
        this.ulesanded = new ArrayList<Ulesanne>();
    }

    public Kasutaja(String nimi) {
        this.nimi = nimi;
        this.ulesanded = new ArrayList<Ulesanne>();
    }

    public int getKasutajaID() {
        return kasutajaID;
    }

    public String getNimi() {
        return nimi;
    }

    public void lisaUlesanneNimekirja(Ulesanne ulesanne) {
        ulesanded.add(ulesanne);
    }

    @Override
    public String toString() {
        return nimi;
    }
}
