
public class CLINimekiri implements CLIElement{
    CLINimekirjaValik[] valikud;
    int valitudElement;

    String tekstiVarv;
    String taustaVarv;

    public CLINimekiri(CLINimekirjaValik[] valikud) {
        this.valikud = valikud;

        this.tekstiVarv = tekstiVarv;
        this.taustaVarv = taustaVarv;

        this.valitudElement = 0;
    }

    @Override
    public String looCLIElement(int xKoordinaat, int yKoordinaat, String tekstiVarv, String taustaVarv) {
        StringBuilder nimekiri = new StringBuilder();
        nimekiri.append(tekstiVarv)
                .append(taustaVarv);

        for (int valik = 0; valik < valikud.length; valik ++) {
            nimekiri.append(EscKoodid.muudaKursoriAsukohta(yKoordinaat + valik * 2, xKoordinaat));
            nimekiri.append(valikud[valik]);
        }

        return nimekiri.toString();
    }


}
