import java.util.Arrays;
public class CLIElemendid {
    public static int[] punane = new int[1530];
    public static int[] roheline = new int[1530];
    public static int[] sinine = new int[1530];
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

    public static void looRGB() {
        // 1. samm: Punane -> Kollane
        for (int i = 0; i < 255; i++) {
            punane[i] = 255;
            roheline[i] = i;
            sinine[i] = 0;
        }
        // 2. samm: Kollane -> Roheline
        for (int i = 255; i < 510; i++) {
            punane[i] = 255 - (i - 255);
            roheline[i] = 255;
            sinine[i] = 0;
        }
        // 3. samm: Roheline -> Tsüaan
        for (int i = 510; i < 765; i++) {
            punane[i] = 0;
            roheline[i] = 255;
            sinine[i] = i - 510;
        }
        // 4. samm: Tsüaan -> Sinine
        for (int i = 765; i < 1020; i++) {
            punane[i] = 0;
            roheline[i] = 255 - (i - 765);
            sinine[i] = 255;
        }
        // 5. samm: Sinine -> Purpurpunane
        for (int i = 1020; i < 1275; i++) {
            punane[i] = i - 1020;
            roheline[i] = 0;
            sinine[i] = 255;
        }
        // 6. samm: Purpurpunane -> Punane
        for (int i = 1275; i < 1530; i++) {
            punane[i] = 255;
            roheline[i] = 0;
            sinine[i] = 255 - (i - 1275);
        }
    }

    public static void main(String[] args) {
        looRGB();
    }
}
