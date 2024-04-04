import java.time.Duration;

public class CLIKuvaAeg implements CLIAknaElement{
    long aegS;
    int laius;
    int pikkus;

    int xKoordinaat;
    int yKoordinaat;

    public CLIKuvaAeg(long aegMs, int pikkus) {
        this.laius = 6;
        setAegMs(aegMs);
        this.pikkus = pikkus;
    }

    public void setAegMs(long aegMs) {
        this.aegS = (long) Math.ceil((double)aegMs / 1000);
    }

    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, int laius) {
        this.xKoordinaat = xKoordinaat;
        this.yKoordinaat = yKoordinaat;
        this.laius = laius;

        return kuvaAeg();
    }

    public String kuvaAeg() {

        // Kas taimer on üle aja läinud?
        char mark = switch(Long.signum(aegS)) {
            case 1 -> '-';
            case -1 -> '+';
            default -> ' ';
        };

        long aegMin = Math.abs(aegS) / 60;
        long aegSek = Math.abs(aegS) % 60;

        String aeg = String.format("%s%c%02d:%02d", CLITeema.TEKSTI_VARV, mark, aegMin, aegSek);
        int x = xKoordinaat + ((laius - aeg.length()) / 2);
        int y = yKoordinaat;

        return EscKoodid.muudaKursoriAsukohta(yKoordinaat, xKoordinaat) + " ".repeat(laius)
                + EscKoodid.muudaKursoriAsukohta(y, x) + aeg;
    }

    @Override
    public String uuendaCLIElement() {
        return kuvaAeg();
    }

    @Override
    public int valjastaElemendiPikkus() {
        return pikkus;
    }

    @Override
    public int valjastaElemendiLaius() {
        return laius;
    }

    @Override
    public boolean kasVajabUuendamist() {
        return true;
    }
}
