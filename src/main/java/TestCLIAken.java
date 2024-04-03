import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;

public class TestCLIAken {
    public static void main(String[] args) {
        KonsooliFunktsioonid.looRGB();

        CLITekst konsooliTekst = new CLITekst("See on testaken, proovi lahedaid asju!", CLITeema.TEKSTI_VARV,
                true);

        KonsooliFunktsioonid.konsooliSuurus("Programmi käivitamiseks vajuta enter");

        ArrayList<CLINimekirjaElement> nimekirjaElemendid = new ArrayList<CLINimekirjaElement>();
        for (int i = 0; i < 60; i++) {
            nimekirjaElemendid.add(new CLINimekirjaElement(i, String.format("Element %d", i + 1),
                    CLINimekirjaElement.Tuup.OBJEKT));
        }
//        for (int i = 15; i < 30; i++) {
//            nimekirjaElemendid.add(new CLINimekirjaElement(i, String.format("Element %d", i + 1),
//                    CLINimekirjaElement.Tuup.KASK));
//        }

        CLINimekiri nimekiri = new CLINimekiri(nimekirjaElemendid,
                CLITeema.TEKSTI_VARV_NIMEKIRJAS, CLITeema.KASU_VARV_NIMEKIRJAS);

        CLISisend konsooliSisend = new CLISisend("Sisestus: ", EscKoodid.heleRohelineTekst(), 0);
        CLIAknaElement[] sisestusAknaElemendid = {konsooliSisend};
        CLIAken sisestusAken = new CLIAken("", sisestusAknaElemendid, 2, 1);

        CLIAknaElement[] CLIElemendid = {konsooliTekst, nimekiri, sisestusAken};

        CLIAken konsooliAken = new CLIAken("Aken testimiseks", CLIElemendid, 4, 2);
        int minLaius = 100;
        int x = (KonsooliFunktsioonid.getKonsooliLaius() - minLaius) / 2;
        int y = (KonsooliFunktsioonid.getKonsooliPikkus() - konsooliAken.valjastaElemendiPikkus()) / 2;
//        System.out.print(konsooliAken.looCLIElement(x, y, minLaius));

        Taimer taimer = new Taimer(Duration.ofSeconds(25));
        CLIEdenemisRiba edenemisRiba = new CLIEdenemisRiba(1, 0);
        CLIAknaElement[] elemendid = {edenemisRiba};
        CLIAken uusKonsooliAken = new CLIAken("", elemendid, 2, 1);

        x = (KonsooliFunktsioonid.getKonsooliLaius() - minLaius) / 2;
        y = (KonsooliFunktsioonid.getKonsooliPikkus() - uusKonsooliAken.valjastaElemendiPikkus()) / 2;
        System.out.print(uusKonsooliAken.looCLIElement(x, y, minLaius));
        Scanner luger = new Scanner(System.in);
        taimer.alustaLoendust();
        while (true) {
            if (luger.hasNextLine()) {
                taimer.lopetaLoendus();
                break;
            }
            edenemisRiba.setProtsent(taimer.getProtsent());
            if (uusKonsooliAken.kasVajabUuendamist()) {
                System.out.print(uusKonsooliAken.uuendaCLIElement());
                System.out.flush();
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException viga) {
                System.out.println("Katkestus: " + viga.getMessage());
            }
        }
    }
}
