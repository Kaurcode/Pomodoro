import java.util.Arrays;
public class CLIElemendid {

    public static StringBuilder edenemisRiba(float protsent, int laius, int pikkus, int iteratsioon, int samm) {
        StringBuilder edenemisRiba = new StringBuilder();
        int ribaKaugus = Math.min(laius, Math.round(protsent * laius / 100));
        int heledaAareProtsent = 50;
        int heledaAarePikkus = Math.round(laius * (float) heledaAareProtsent / 100);
        for (int asukoht = 0; asukoht <= ribaKaugus - 1 - heledaAarePikkus; asukoht++) {
            while (iteratsioon < 0) {
                iteratsioon += 1530;
            }
            while (iteratsioon >= 1530) {
                iteratsioon -= 1530;
            }
            edenemisRiba.append(EscKoodid.tagastaRGBTaust(" ",
                    punane[iteratsioon], roheline[iteratsioon], sinine[iteratsioon]));
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
            int r = punane[iteratsioon] + Math.round(((float) (255 - punane[iteratsioon]) / heledaAarePikkus) * (heledaAarePikkus - (ribaKaugus - asukoht)));
            int g = roheline[iteratsioon] + Math.round(((float) (255 - roheline[iteratsioon]) / heledaAarePikkus) *  (heledaAarePikkus - (ribaKaugus - asukoht)));
            int b = sinine[iteratsioon] + Math.round(((float) (255 - sinine[iteratsioon]) / heledaAarePikkus) * (heledaAarePikkus - (ribaKaugus - asukoht)));
            edenemisRiba.append(EscKoodid.tagastaRGBTaust(" ", r, g, b));
            iteratsioon += samm;
        }
        edenemisRiba.append(EscKoodid.tagastaTavaline());
        edenemisRiba.append(" ".repeat(laius - ribaKaugus));

        for (int i = 0; i < pikkus - 1; i++) {
            edenemisRiba.append('\n');
            edenemisRiba.append(edenemisRiba);
        }
        return edenemisRiba;
    }

    public static StringBuilder edenemisRiba(float protsent, int laius, int pikkus, int iteratsioon) {
        return edenemisRiba(protsent, laius, pikkus, iteratsioon, 1);
    }

    public static void main(String[] args) {
        looRGB();
    }
}
