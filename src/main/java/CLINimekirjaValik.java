public class CLINimekirjaValik {
    private String tekst;
    private boolean kasValitud;

    private boolean kasVajabMuudatust;

    private int joonePikkus;

    public CLINimekirjaValik(String tekst) {
        this.tekst = tekst;
        this.kasValitud = false;
        this.kasVajabMuudatust = true;
        this.joonePikkus = 0;
    }

    @Override
    public String toString() {
        return tekst;
    }
}
