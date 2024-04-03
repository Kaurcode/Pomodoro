import java.util.ArrayList;

public class CLINimekiri implements CLIAknaElement {
    ArrayList<CLINimekirjaElement> elemendid;
    int veerge;
    int laius;

    final int ELEMENTIDE_VAHELINE_LAIUS = 3;
    final int RIDU_ELEMENTIDE_VAHEL = 1;
    final int MARGI_PIKKUSE_LAIUSE_SUHE = 2;  // Konsooli märgi pikkuse-laiuse suhe 1:2
    final int RISTKULIKU_KULGEDE_SUHE = 2;  // Nimekirjast mooodustub ristkülik

    String valikuVarv;
    String kasuVarv;

    public CLINimekiri(ArrayList<CLINimekirjaElement> elemendid, String valikuVarv, String kasuVarv) {
        this.elemendid = elemendid;

        this.valikuVarv = valikuVarv;
        this.kasuVarv = kasuVarv;

        arvutaVeergudeArv();

    }

    @Override
    public String uuendaCLIElement() {
        return null;
    }

    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, int laius) {
        this.laius = Math.min(laius, this.laius);
        StringBuilder menuuElement = new StringBuilder();

        int elementeVeerus = (int) Math.ceil((double) elemendid.size() / veerge);

        for (int elemendiIndeks = 0; elemendiIndeks < elemendid.size(); elemendiIndeks++) {
            int x = xKoordinaat + (elemendiIndeks / elementeVeerus) * (this.laius / veerge) + ELEMENTIDE_VAHELINE_LAIUS;
            int y = yKoordinaat + 2 * (elemendiIndeks % elementeVeerus);

            menuuElement.append(EscKoodid.muudaKursoriAsukohta(y, x));
            menuuElement.append(switch (elemendid.get(elemendiIndeks).getTuup()) {
                case OBJEKT -> valikuVarv;
                case KASK -> kasuVarv;
            });

            menuuElement.append(elemendid.get(elemendiIndeks).looCLIElement(x, y, suurimaElemendiLaius()));

        }

        return menuuElement.toString();
    }

    @Override
    public int valjastaElemendiPikkus() {
        int elementeVeerus = (int) Math.ceil((double) elemendid.size() / veerge);
        return elementeVeerus * 2;
    }

    @Override
    public int valjastaElemendiLaius() {
        return this.laius;
    }

    public int suurimaElemendiLaius() {
        int suurimaElemendiLaius = 0;
        for (CLINimekirjaElement element : elemendid) {
            int elemendiLaius = element.valjastaElemendiLaius();
            suurimaElemendiLaius = Math.max(suurimaElemendiLaius, elemendiLaius);
        }
        return suurimaElemendiLaius;
    }

    private void arvutaVeergudeArv() {
        double nimekirjaPikkus = elemendid.size() * (1 + RIDU_ELEMENTIDE_VAHEL)
                * MARGI_PIKKUSE_LAIUSE_SUHE * RISTKULIKU_KULGEDE_SUHE;
        double nimekirjaLaius = (suurimaElemendiLaius() + ELEMENTIDE_VAHELINE_LAIUS);
        int maksimaalneVeergudeArv = (int) (KonsooliFunktsioonid.getKonsooliLaius() / nimekirjaLaius);
        // this.veerge = Math.min((int) Math.ceil(nimekirjaPikkus / nimekirjaLaius), maksimaalneVeergudeArv);
        this.veerge = (int) Math.min(Math.ceil(Math.sqrt(nimekirjaPikkus / nimekirjaLaius)), maksimaalneVeergudeArv);
        this.laius = (int) (nimekirjaLaius * veerge) + ELEMENTIDE_VAHELINE_LAIUS;
    }

    @Override
    public boolean kasVajabUuendamist() {
        return false;
    }
}