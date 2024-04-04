public class CLISisend implements CLIAknaElement {
    private String tekst;
    private String tekstiVarv;
    private int ridu;
    private int kursoriAlgusKohtX;
    private int kursoriAlgusKohtY;

    public CLISisend(String tekst, String tekstiVarv, int ridu) {
        this.tekst = tekst;
        this.tekstiVarv = tekstiVarv;
        this.ridu = ridu;
    }

    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, int laius) {
        kursoriAlgusKohtX = xKoordinaat + tekst.length();
        kursoriAlgusKohtY = yKoordinaat;

        return EscKoodid.muudaKursoriAsukohta(yKoordinaat, xKoordinaat) +
                CLITeema.AKNA_TAUST +
                tekstiVarv + tekst + EscKoodid.salvestaKursoriAsukoht();
    }

    public String taastaKursoriAsukoht() {
        return EscKoodid.muudaKursoriAsukohta(kursoriAlgusKohtY, kursoriAlgusKohtX)
                + CLITeema.AKNA_TAUST;
    }

    @Override
    public String uuendaCLIElement() {
        return null;
    }

    @Override
    public int valjastaElemendiPikkus() {
        return this.ridu;
    }

    @Override
    public int valjastaElemendiLaius() {
        return tekst.length();
    }

    @Override
    public boolean kasVajabUuendamist() {
        return false;
    }
}
