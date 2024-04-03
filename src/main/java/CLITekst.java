public class CLITekst implements CLIAknaElement {
    private String tekst;
    private int laius;
    private boolean kasKursiiv;
    private String tekstiVarv;

    public CLITekst(String tekst, String tekstiVarv, Boolean kasKursiiv) {
        this.tekst = tekst;
        this.laius = tekst.length();
        this.kasKursiiv = kasKursiiv;
        this.tekstiVarv = tekstiVarv;
    }

    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, int laius) {
        this.laius = laius;

        StringBuilder tekstiElement = new StringBuilder();

        if (kasKursiiv) {
            tekstiElement.append(EscKoodid.kursiiv());
        }

        tekstiElement.append(tekstiVarv)
                .append(EscKoodid.muudaKursoriAsukohta(yKoordinaat, xKoordinaat));

        if (kasKursiiv) {
            tekstiElement.append(this.tekst).append(EscKoodid.mitteKursiiv());
        }

        return tekstiElement.toString();
    }

    @Override
    public int valjastaElemendiPikkus() {
        return 2;
    }

    @Override
    public String uuendaCLIElement() {
        return null;
    }

    @Override
    public int valjastaElemendiLaius() {
        return this.laius;
    }

    @Override
    public boolean kasVajabUuendamist() {
        return false;
    }
}
