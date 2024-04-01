
public class CLIAken {
    private final String NURK_1 = "┌";
    private final String NURK_2 = "┐";
    private final String NURK_3 = "└";
    private final String NURK_4 = "┘";

    private final String HORISONTAALNE_RIBA = "─";
    private final String VERTIKAALNE_RIBA = "│";

    private String aknaPealkiri;

    private CLIElement[] CLIElemendid;

    private int laius;
    private int pikkus;

    private int xKoordinaat;
    private int yKoordinaat;

    private String piirjooneVarv;
    private String aknaTaust;

    public CLIAken(String aknaPealkiri, CLIElement[] CLIElemendid, String piirjooneVarv, String aknaTaust, int minLaius,
                   int minPikkus) {

        this.aknaPealkiri = aknaPealkiri;
        this.CLIElemendid = CLIElemendid;

        this.piirjooneVarv = piirjooneVarv;
        this.aknaTaust = aknaTaust;

        this.laius = minLaius;
        this.pikkus = minPikkus;

        xKoordinaat = (KonsooliFunktsioonid.getKonsooliLaius() - this.laius) / 2;
        yKoordinaat = (KonsooliFunktsioonid.getKonsooliPikkus() - this.pikkus) / 2;
    }

    public String looCLIAken() {
        String horisontaalneLairiba = HORISONTAALNE_RIBA.repeat(laius - 2);
        String vertikaalsedLairibad = VERTIKAALNE_RIBA + " ".repeat(laius - 2) + VERTIKAALNE_RIBA;

        StringBuilder aken = new StringBuilder();
        aken.append(EscKoodid.peidaKursor());
        aken.append(piirjooneVarv);
        aken.append(aknaTaust);

        int pealkirjaPikkus = aknaPealkiri.length();

        aken.append(EscKoodid.muudaKursoriAsukohta(yKoordinaat, xKoordinaat))
                .append(NURK_1)
                .append(HORISONTAALNE_RIBA)
                .append(aknaPealkiri)
                .append(HORISONTAALNE_RIBA.repeat(laius - 3 - pealkirjaPikkus))
                .append(NURK_2);

        for (int y = yKoordinaat + 1; y <= yKoordinaat + pikkus - 1; y++) {
            aken.append(EscKoodid.muudaKursoriAsukohta(y, xKoordinaat))
                    .append(vertikaalsedLairibad);
        }

        aken.append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + pikkus, xKoordinaat))
                .append(NURK_3)
                .append(horisontaalneLairiba)
                .append(NURK_4);

        for (int elemendiIndeks = 0; elemendiIndeks < CLIElemendid.length; elemendiIndeks++) {
            aken.append(CLIElemendid[elemendiIndeks]
                    .looCLIElement(xKoordinaat + 5, yKoordinaat + 2 + elemendiIndeks,
                            piirjooneVarv, aknaTaust));
        }

        aken.append(EscKoodid.tavalineTekst());
        aken.append(EscKoodid.tavalineTaust());
        aken.append(EscKoodid.naitaKursor());

        return aken.toString();
    }
}
