import java.util.ArrayList;

public class Kasutaja {
    public static String kasutajateKaustaNimi = "kasutajad";
    private Failihaldur kasutajaFail;
    private String nimi;
    private ArrayList<Ulesanne> ulesanded;

    public Kasutaja(String nimi, Failihaldur kasutajaFail) {
        this.nimi = nimi;
        this.kasutajaFail = kasutajaFail;
        this.ulesanded = new ArrayList<Ulesanne>();
    }

    public static Kasutaja looKasutaja(String nimi) {
        Failihaldur kasutajaFail = Failihaldur.looFail(kasutajateKaustaNimi, nimi);
        if (kasutajaFail == null) return null;
        kasutajaFail.kirjutaRida(String.format("Nimi: %s\n\n", nimi));
        return new Kasutaja(nimi, kasutajaFail);
    }

    public String getNimi() {
        return nimi;
    }

    public ArrayList<Ulesanne> getUlesanded() {
        return ulesanded;
    }

    public void lisaUlesanne(Ulesanne ulesanne) {
        ulesanded.add(ulesanne);
        kasutajaFail.kirjutaRida(String.format("%sUlesanne|Algus%s", "-".repeat(25), "-".repeat(25)));
        kasutajaFail.kirjutaRida(String.format("Nimi: %s", ulesanne.getNimi()));
    }
}
