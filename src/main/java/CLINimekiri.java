import java.util.ArrayList;

public class CLINimekiri implements CLIAknaElement {
    ArrayList<CLINimekirjaElement> elemendid;
    public static final int XPUHVER = 2;
    int veerge;

    public CLINimekiri(ArrayList<CLINimekirjaElement> elemendid) {
        this.elemendid = elemendid;
        this.veerge = elemendid.size() / (KonsooliFunktsioonid.getKonsooliPikkus() / 4) + 1;
    }

    @Override
    public String uuendaCLIElement() {
        return null;
    }

    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, CLIAken aken) {
        StringBuilder menuuElement = new StringBuilder();
        int elementeVeerus = elemendid.size() / veerge;
        elementeVeerus += (elementeVeerus * veerge) < elemendid.size() ? 1 : 0;

        for (int elemendiIndeks = 0; elemendiIndeks < elemendid.size(); elemendiIndeks++) {
            int x = xKoordinaat + (elemendiIndeks / elementeVeerus) *
                    ((aken.getLaius() - 2 * CLIAken.XPUHVER) / veerge) + XPUHVER;
            int y = yKoordinaat + 2 * (elemendiIndeks % elementeVeerus);

            menuuElement.append(EscKoodid.muudaKursoriAsukohta(y, x));
            menuuElement.append(CLITeema.TEKSTI_VARV);
            String number = String.format("%d. ", elemendiIndeks + 1);
            menuuElement.append(number);

            menuuElement.append(elemendid.get(elemendiIndeks).looCLIElement(x + number.length(), y, aken));

        }

        return menuuElement.toString();
    }

    @Override
    public int valjastaElemendiPikkus() {
        int elementeVeerus = elemendid.size() / veerge;
        elementeVeerus += elementeVeerus * veerge < elemendid.size() ? 1 : 0;
        return elementeVeerus * 2;
    }
}