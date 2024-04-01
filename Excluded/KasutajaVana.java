import java.util.ArrayList;

public class KasutajaVana {
    public static final String KASUTAJATE_KAUSTA_NIMI = "kasutajad";
    private Failihaldur kasutajaFail;
    private String nimi;
    private int kasutajaID;
    private ArrayList<Ulesanne> ulesanded;

    public KasutajaVana(String nimi, int kasutajaID) {
        this.nimi = nimi;
        this.kasutajaID = kasutajaID;
        this.ulesanded = new ArrayList<Ulesanne>();
    }

    public static String kasutajaTahistusFailis(boolean kasAlgus) {
        String asukoht = kasAlgus ? "Algus" : "Lopp";
        return String.format("%s[Kasutaja|%s]%s", "-".repeat(25), asukoht, "-".repeat(25));
    }

    public static KasutajaVana looKasutaja(String nimi) {
        Failihaldur kasutajaFail = Failihaldur.looFail(KASUTAJATE_KAUSTA_NIMI, nimi);
        if (kasutajaFail == null) return null;
        kasutajaFail.kirjutaRida(kasutajaTahistusFailis(true));
        kasutajaFail.kirjutaRida(String.format("Nimi: %s\n", nimi));
        return new KasutajaVana(nimi, kasutajaFail);
    }

    public static ArrayList<KasutajaVana> loeKasutajadKaustast() {
        Failihaldur.kontrolliKaust(KASUTAJATE_KAUSTA_NIMI);
        Failihaldur[] failihaldurid = Failihaldur.looKaustastFailihaldurid(KASUTAJATE_KAUSTA_NIMI);
        return null;
    }

    public static KasutajaVana loeKasutajaFailist(Failihaldur failihaldur) {
        ArrayList<String> kasutajaFailiRead = failihaldur.loeKoikReadFailist();
        int kasutajaFailiRidadeArv = kasutajaFailiRead.size();
        int reaNumber = 0;
        for (; reaNumber < kasutajaFailiRidadeArv; reaNumber++) {
            if (kasutajaFailiRead.get(reaNumber).equals(kasutajaTahistusFailis(true))) {
                reaNumber++;
                break;
            };
        }

        String kasutajaNimi = kasutajaFailiRead.get(reaNumber).split(": ")[1].trim();
        KasutajaVana kasutaja = new KasutajaVana(kasutajaNimi, failihaldur);
        reaNumber++;

        return null;
    }

    public String getNimi() {
        return nimi;
    }

    public ArrayList<Ulesanne> getUlesanded() {
        return ulesanded;
    }

    public void lisaUlesanne(Ulesanne ulesanne) {
        ulesanded.add(ulesanne);
        kasutajaFail.kirjutaRida(String.format("%s[Ulesanne|Algus]%s", "-".repeat(25), "-".repeat(25)));
        kasutajaFail.kirjutaRida(String.format("Nimi: %s\n", ulesanne.getNimi()));
        kasutajaFail.kirjutaRida(String.format("%s[Ulesanne|Lopp]%s", "-".repeat(25), "-".repeat(25)));
    }

    public Ulesanne lisaUlesanne(String ulesandeNimi) {
        Ulesanne ulesanne = new Ulesanne(ulesandeNimi);
        lisaUlesanne(ulesanne);
        return ulesanne;
    }
}
