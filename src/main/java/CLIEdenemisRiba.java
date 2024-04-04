public class CLIEdenemisRiba implements CLIAknaElement {
    int xKoordinaat;
    int yKoordinaat;
    public boolean kasVajabUuendamist;
    private final static int HELEDA_AARE_PROTSENT = 50;
    private int heledaAarePikkus;  // Kui palju on heledat äärt paremas otsas
    private float protsent;
    private int ribaKaugus;
    private int laius;
    private int animatsiooniJark;  // Mis järgus animatsioon on? -> et rgb värv saaks muutuda
    private int samm;


    public CLIEdenemisRiba(int samm, float protsent) {
        this.samm = samm;
        this.protsent = protsent;
        this.animatsiooniJark = 0;
        this.laius = 0;
        kasVajabUuendamist = true;
    }

    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, int laius) {
        this.xKoordinaat = xKoordinaat;
        this.yKoordinaat = yKoordinaat;
        this.laius = laius;

        heledaAarePikkus = Math.round(laius * (float) HELEDA_AARE_PROTSENT / 100);
        ribaKaugus = Math.min(laius, Math.round(protsent * laius / 100));

        StringBuilder edenemisRiba = new StringBuilder();

//        edenemisRiba.append(EscKoodid.muudaKursoriAsukohta(yKoordinaat, xKoordinaat));
//        edenemisRiba.append(CLITeema.SISESTUSE_AKNA_VARV);
//        edenemisRiba.append(CLITeema.NURK_1 + CLITeema.HORISONTAALNE_RIBA.repeat(laius - 2) + CLITeema.NURK_2)
//                .append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + 1, xKoordinaat))
//                .append(CLITeema.VERTIKAALNE_RIBA + " ".repeat(laius - 2) + CLITeema.VERTIKAALNE_RIBA)
//                .append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + 2, xKoordinaat))
//                .append(CLITeema.NURK_3 + CLITeema.HORISONTAALNE_RIBA.repeat(laius - 2) + CLITeema.NURK_4)
//                .append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + 1, xKoordinaat + 1));

        edenemisRiba.append(joonistaRiba());

        return edenemisRiba.toString();
    }

    /**
     * Joonistab edenemisrea vastavalt määratud protsendile ja animatsioonijärgule
     * @return edenemisrea loomiseks/uuendamiseks vajalik sõne koos Escape koodidega
     */
    public String joonistaRiba() {
        StringBuilder edenemisRiba = new StringBuilder();
        int iteratsioon = animatsiooniJark;

        // RGB osa loomine
        edenemisRiba.append(EscKoodid.muudaKursoriAsukohta(yKoordinaat, xKoordinaat));
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

        // Heleda osa loomine
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
        animatsiooniJark += 1;
        return edenemisRiba.toString();
    }

    @Override
    public String uuendaCLIElement() {
        return joonistaRiba();
    }

    @Override
    public int valjastaElemendiPikkus() {
        return 0;
    }

    public void setProtsent(float protsent) {
        this.protsent = protsent;
        ribaKaugus = Math.min(Math.round(protsent * laius / 100), laius);
    }

    @Override
    public boolean kasVajabUuendamist() {
        return kasVajabUuendamist;
    }

    @Override
    public int valjastaElemendiLaius() {
        return this.laius;
    }
}
