public class TestCLIAken {
    public static void main(String[] args) {
        KonsooliFunktsioonid.konsooliSuurus("Programmi käivitamiseks vajuta enter");
        CLINimekirjaValik valik1 = new CLINimekirjaValik("Valik1");
        CLINimekirjaValik valik2 = new CLINimekirjaValik("Valik2");
        CLINimekirjaValik valik3 = new CLINimekirjaValik("Valik3");
        CLINimekirjaValik valik4 = new CLINimekirjaValik("Valik4");
        CLINimekirjaValik valik5 = new CLINimekirjaValik("Valik5");
        CLINimekirjaValik[] valikud = {valik1, valik2, valik3, valik4, valik5};

        CLINimekiri nimekiri = new CLINimekiri(valikud);
        CLIElement[] CLIElemendid = {nimekiri};

        CLIAken konsooliAken = new CLIAken("Aken testimiseks", CLIElemendid, EscKoodid.heleLillaTekst(),
                EscKoodid.mustTaust(), 100, 15);
        System.out.print(konsooliAken.looCLIAken());
        System.out.print(EscKoodid.muudaKursoriAsukohta(KonsooliFunktsioonid.getKonsooliPikkus(),
                KonsooliFunktsioonid.getKonsooliLaius()));
    }
}
