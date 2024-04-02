public class CLINimekirjaElement implements CLIAknaElement {
    private String tekst;
    public enum Tuup {
        OBJEKT,
        KASK
    }
    private Tuup tuup;

    public CLINimekirjaElement(String tekst, Tuup tuup) {
        this.tekst = tekst;
        this.tuup = tuup;
    }

    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, CLIAken aken) {
        return EscKoodid.muudaKursoriAsukohta(yKoordinaat, xKoordinaat) +
                switch (tuup) {
                    case OBJEKT -> CLITeema.TEKSTI_VARV_NIMEKIRJAS;
                    case KASK -> CLITeema.KASU_VARV_NIMEKIRJAS;
                } +
                this.tekst;
    }

    @Override
    public String uuendaCLIElement() {
        return null;
    }

    @Override
    public int valjastaElemendiPikkus() {
        return 2;
    }
}
