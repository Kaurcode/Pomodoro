
public class CLIAken implements CLIAknaElement {
    private String aknaPealkiri;

    private CLIAknaElement[] CLIElemendid;
    private int suurimElemendiLaius;

    private int laius;
    private int pikkus;

    private int xKoordinaat;
    private int yKoordinaat;

    public int xPuhver;
    public int yPuhver;

    public CLIAken(String aknaPealkiri, CLIAknaElement[] CLIElemendid, int xPuhver, int yPuhver) {

        this.aknaPealkiri = aknaPealkiri;
        this.CLIElemendid = CLIElemendid;

        this.xPuhver = xPuhver;
        this.yPuhver = yPuhver;

        int elementidePikkus = 2;
        for (CLIAknaElement cliElement : CLIElemendid) {
            elementidePikkus += cliElement.valjastaElemendiPikkus();
        }
        this.pikkus = elementidePikkus;

        this.suurimElemendiLaius = 0;
        for (CLIAknaElement aknaElement : CLIElemendid) {
            this.suurimElemendiLaius = Math.max(this.suurimElemendiLaius, aknaElement.valjastaElemendiLaius());
        }
    }

    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, int minLaius) {
        this.laius = Math.min(Math.max(minLaius, this.suurimElemendiLaius + 2 * xPuhver),
                KonsooliFunktsioonid.getKonsooliLaius());

        xKoordinaat -= (this.laius - minLaius) / 2;

        String horisontaalneLairiba = CLITeema.HORISONTAALNE_RIBA.repeat(laius - 2);
        String vertikaalsedLairibad = CLITeema.VERTIKAALNE_RIBA + " ".repeat(laius - 2)
                + CLITeema.VERTIKAALNE_RIBA;

        StringBuilder aken = new StringBuilder();
        aken.append(EscKoodid.peidaKursor());
        aken.append(CLITeema.AKNA_PIIRJOONE_VARV);
        aken.append(CLITeema.AKNA_TAUST);

        int pealkirjaPikkus = aknaPealkiri.length();

        aken.append(EscKoodid.muudaKursoriAsukohta(yKoordinaat, xKoordinaat))
                .append(CLITeema.NURK_1)
                .append(CLITeema.HORISONTAALNE_RIBA)
                .append(aknaPealkiri)
                .append(CLITeema.HORISONTAALNE_RIBA.repeat(laius - 3 - pealkirjaPikkus))
                .append(CLITeema.NURK_2);

        for (int y = yKoordinaat + 1; y <= yKoordinaat + pikkus - 1; y++) {
            aken.append(EscKoodid.muudaKursoriAsukohta(y, xKoordinaat))
                    .append(vertikaalsedLairibad);
        }

        aken.append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + pikkus, xKoordinaat))
                .append(CLITeema.NURK_3)
                .append(horisontaalneLairiba)
                .append(CLITeema.NURK_4);

        int pikkus = 0;
        for (CLIAknaElement element : CLIElemendid) {
            aken.append(element.looCLIElement(xKoordinaat + xPuhver,
                    yKoordinaat + yPuhver + pikkus, laius - 2 * xPuhver));
            pikkus += element.valjastaElemendiPikkus();
        }

        aken.append(EscKoodid.tavalineTekst());
        aken.append(EscKoodid.tavalineTaust());
        aken.append(EscKoodid.naitaKursor());

        return aken.toString();
    }

    public int getLaius() {
        return laius;
    }

    @Override
    public String uuendaCLIElement() {
        StringBuilder uuendus = new StringBuilder();
        uuendus.append(EscKoodid.salvestaKursoriAsukoht());
        uuendus.append(EscKoodid.peidaKursor());
        uuendus.append(CLITeema.AKNA_TAUST);

        for (CLIAknaElement cliElement : CLIElemendid) {
            if (cliElement.kasVajabUuendamist()) {
                uuendus.append(cliElement.uuendaCLIElement());
            }
        }

        uuendus.append(EscKoodid.tavalineTekst())
                .append(EscKoodid.tavalineTaust());

        uuendus.append(EscKoodid.naitaKursor());
        uuendus.append(EscKoodid.taastaKursoriAsukoht());

        return uuendus.toString();
    }

    @Override
    public int valjastaElemendiPikkus() {
        return this.pikkus + yPuhver + 1;
    }

    @Override
    public int valjastaElemendiLaius() {
        return this.laius;
    }

    @Override
    public boolean kasVajabUuendamist() {
        for (CLIAknaElement element : CLIElemendid) {
            if (element.kasVajabUuendamist()) return true;
        }
        return false;
    }
}
