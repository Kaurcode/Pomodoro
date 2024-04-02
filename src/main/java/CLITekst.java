public class CLITekst implements CLIAknaElement {
    private String tekst;

    public CLITekst(String tekst) {
        this.tekst = tekst;
    }

    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, CLIAken aken) {

        return CLITeema.TEKSTI_VARV +
                EscKoodid.kursiiv() +
                EscKoodid.muudaKursoriAsukohta(yKoordinaat, xKoordinaat) +
                this.tekst +
                EscKoodid.mitteKursiiv();
    }

    @Override
    public int valjastaElemendiPikkus() {
        return 2;
    }

    @Override
    public String uuendaCLIElement() {
        return null;
    }
}
