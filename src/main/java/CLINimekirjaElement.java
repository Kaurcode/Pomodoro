public class CLINimekirjaElement implements CLIAknaElement {
    private String tekst;
    private int indeks;
    private int laius;

    public enum Tuup {
        OBJEKT,
        KASK
    }
    private Tuup tuup;

    public CLINimekirjaElement(int indeks, String tekst, Tuup tuup) {
        this.indeks = indeks;
        this.tekst = tekst;
        this.tuup = tuup;

        this.laius = tekst.length() + (int) (Math.log10(indeks) + 1) + 2;
    }

    public Tuup getTuup() {
        return tuup;
    }

    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, int laius) {
        this.laius = laius;
        return EscKoodid.muudaKursoriAsukohta(yKoordinaat, xKoordinaat) +
                String.format("%d. %s", this.indeks + 1, this.tekst);
    }

    @Override
    public String uuendaCLIElement() {
        return null;
    }

    @Override
    public int valjastaElemendiPikkus() {
        return 2;
    }

    @Override
    public int valjastaElemendiLaius() {
        return this.laius;
    }

    @Override
    public boolean kasVajabUuendamist() {
        return false;
    }
}
