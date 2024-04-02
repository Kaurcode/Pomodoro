public class CLIEdenemisRiba implements CLIAknaElement {
    int xKoordinaat;
    int yKoordinaat;
    public boolean vajabUuendamist;
    private final static int HELEDA_AARE_PROTSENT = 50;
    private int heledaAarePikkus;
    private float protsent;
    private int ribaKaugus;
    private int ribaLaius;
    private int hetk;
    private int samm;


    public CLIEdenemisRiba(int samm, float protsent) {
        this.samm = samm;
        this.protsent = protsent;
        this.hetk = 0;
    }

    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, CLIAken cliAken) {
        this.xKoordinaat = xKoordinaat;
        this.yKoordinaat = yKoordinaat;

        int laius = cliAken.getLaius() - 2 * CLIAken.XPUHVER;
        ribaLaius = laius - 4;
        heledaAarePikkus = Math.round(ribaLaius * (float) HELEDA_AARE_PROTSENT / 100);

        ribaKaugus = Math.min(laius, Math.round(protsent * ribaLaius / 100));

        StringBuilder edenemisRiba = new StringBuilder();

        edenemisRiba.append(EscKoodid.muudaKursoriAsukohta(yKoordinaat, xKoordinaat));
        edenemisRiba.append(CLITeema.SISESTUSE_AKNA_VARV);
        edenemisRiba.append(CLITeema.NURK_1 + CLITeema.HORISONTAALNE_RIBA.repeat(laius - 2) + CLITeema.NURK_2)
                .append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + 1, xKoordinaat))
                .append(CLITeema.VERTIKAALNE_RIBA + " ".repeat(laius - 2) + CLITeema.VERTIKAALNE_RIBA)
                .append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + 2, xKoordinaat))
                .append(CLITeema.NURK_3 + CLITeema.HORISONTAALNE_RIBA.repeat(laius - 2) + CLITeema.NURK_4)
                .append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + 1, xKoordinaat + 1));

        edenemisRiba.append(joonistaRiba());

        return edenemisRiba.toString();
    }

    public String joonistaRiba() {
        StringBuilder edenemisRiba = new StringBuilder();
        int iteratsioon = hetk;

        edenemisRiba.append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + 1, xKoordinaat + 2));
        for (int asukoht = 0; asukoht <= ribaKaugus - 1 - heledaAarePikkus; asukoht++) {
            while (iteratsioon < 0) {
                iteratsioon += 1530;
            }
            while (iteratsioon >= 1530) {
                iteratsioon -= 1530;
            }
            edenemisRiba.append(EscKoodid.tagastaRGBTaust(" ",
                    KonsooliFunktsioonid.punane[iteratsioon], KonsooliFunktsioonid.roheline[iteratsioon], KonsooliFunktsioonid.sinine[iteratsioon]));
            iteratsioon += samm;
        }
        for (int asukoht = ribaKaugus - heledaAarePikkus; asukoht <= ribaKaugus - 1; asukoht++) {
            if (asukoht < 0) { continue; }
            while (iteratsioon < 0) {
                iteratsioon += 1530;
            }
            while (iteratsioon >= 1530) {
                iteratsioon -= 1530;
            }

            int r = KonsooliFunktsioonid.punane[iteratsioon] +
                    Math.round(((float) (255 - KonsooliFunktsioonid.punane[iteratsioon]) / heledaAarePikkus) *
                            (heledaAarePikkus - (ribaKaugus - asukoht)));
            int g = KonsooliFunktsioonid.roheline[iteratsioon] +
                    Math.round(((float) (255 - KonsooliFunktsioonid.roheline[iteratsioon]) / heledaAarePikkus) *
                            (heledaAarePikkus - (ribaKaugus - asukoht)));
            int b = KonsooliFunktsioonid.sinine[iteratsioon] +
                    Math.round(((float) (255 - KonsooliFunktsioonid.sinine[iteratsioon]) / heledaAarePikkus) *
                            (heledaAarePikkus - (ribaKaugus - asukoht)));

            edenemisRiba.append(EscKoodid.tagastaRGBTaust(" ", r, g, b));
            iteratsioon += samm;
        }
        edenemisRiba.append(EscKoodid.tavalineTaust());
        hetk += 1;
        return edenemisRiba.toString();
    }

    @Override
    public String uuendaCLIElement() {
        vajabUuendamist = false;
        return joonistaRiba();
    }

    @Override
    public int valjastaElemendiPikkus() {
        return 4;
    }

    public void setProtsent(float protsent) {
        this.protsent = protsent;
        int uusRibaKaugus = Math.min(Math.round(protsent * ribaLaius / 100), ribaLaius);

        if (uusRibaKaugus == ribaKaugus) return;
        ribaKaugus = uusRibaKaugus;
        vajabUuendamist = true;
    }
}
