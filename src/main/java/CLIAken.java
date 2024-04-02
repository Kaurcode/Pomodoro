
public class CLIAken {
    private String aknaPealkiri;

    private CLIAknaElement[] CLIElemendid;

    private int laius;
    private int pikkus;

    private int xKoordinaat;
    private int yKoordinaat;

    public static final int XPUHVER = 4;

    public CLIAken(String aknaPealkiri, CLIAknaElement[] CLIElemendid, int minLaius, int minPikkus) {

        this.aknaPealkiri = aknaPealkiri;
        this.CLIElemendid = CLIElemendid;

        int elementidePikkus = 2;
        for (CLIAknaElement cliElement : CLIElemendid) {
            elementidePikkus += cliElement.valjastaElemendiPikkus();
        }

        this.laius = Math.max((int) Math.round((double) KonsooliFunktsioonid.getKonsooliLaius() * 0.7), minLaius);
        this.pikkus = Math.max(minPikkus, elementidePikkus);

        xKoordinaat = (KonsooliFunktsioonid.getKonsooliLaius() - this.laius) / 2;
        yKoordinaat = (KonsooliFunktsioonid.getKonsooliPikkus() - this.pikkus) / 2;
    }

    public String uuendaCLIAken() {
        StringBuilder uuendus = new StringBuilder();
        uuendus.append(EscKoodid.salvestaKursoriAsukoht());
        uuendus.append(EscKoodid.peidaKursor());

        for (CLIAknaElement cliElement : CLIElemendid) {
            if (cliElement.vajabUuendamist) {
                uuendus.append(cliElement.uuendaCLIElement());
            }
        }

        uuendus.append(EscKoodid.tavalineTekst())
                .append(EscKoodid.tavalineTaust());

        uuendus.append(EscKoodid.naitaKursor());
        uuendus.append(EscKoodid.taastaKursoriAsukoht());

        return uuendus.toString();
    }

    public String looCLIAken() {
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
        for (int elemendiIndeks = 0; elemendiIndeks < CLIElemendid.length; elemendiIndeks++) {
            CLIAknaElement element = CLIElemendid[elemendiIndeks];
            aken.append(element
                    .looCLIElement(xKoordinaat + XPUHVER, yKoordinaat + 2 + pikkus, this));
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
}
