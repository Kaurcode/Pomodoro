public class CLISisestus implements CLIAknaElement {
    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, CLIAken aken) {
        StringBuilder sisendiAken = new StringBuilder();
        sisendiAken.append(EscKoodid.muudaKursoriAsukohta(yKoordinaat, xKoordinaat));
        sisendiAken.append(CLITeema.SISESTUSE_AKNA_VARV);
        int laius = aken.getLaius() - 2 * CLIAken.XPUHVER;
        sisendiAken.append(CLITeema.NURK_1 + CLITeema.HORISONTAALNE_RIBA.repeat(laius - 2) + CLITeema.NURK_2)
                .append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + 1, xKoordinaat))
                .append(CLITeema.VERTIKAALNE_RIBA + " ".repeat(laius - 2) + CLITeema.VERTIKAALNE_RIBA)
                .append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + 2, xKoordinaat))
                .append(CLITeema.NURK_3 + CLITeema.HORISONTAALNE_RIBA.repeat(laius - 2) + CLITeema.NURK_4)
                .append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + 1, xKoordinaat + 1));
        return sisendiAken.toString();
    }

    @Override
    public String uuendaCLIElement() {
        return null;
    }

    @Override
    public int valjastaElemendiPikkus() {
        return 4;
    }
}
