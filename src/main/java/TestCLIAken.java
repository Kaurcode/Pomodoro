import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;

public class TestCLIAken {
    public static void main(String[] args) {
        KonsooliFunktsioonid.looRGB();

        CLITekst konsooliTekst = new CLITekst("See on testaken, proovi lahedaid asju!");

        KonsooliFunktsioonid.konsooliSuurus("Programmi käivitamiseks vajuta enter");

        ArrayList<CLINimekirjaElement> nimekirjaElemendid = new ArrayList<CLINimekirjaElement>();
        for (int i = 0; i < 15; i++) {
            nimekirjaElemendid.add(new CLINimekirjaElement(String.format("Element %d", i + 1),
                    CLINimekirjaElement.Tuup.OBJEKT));
        }
        for (int i = 15; i < 30; i++) {
            nimekirjaElemendid.add(new CLINimekirjaElement(String.format("Element %d", i + 1),
                    CLINimekirjaElement.Tuup.KASK));
        }

        CLINimekiri nimekiri = new CLINimekiri(nimekirjaElemendid);
        CLISisestus sisestus = new CLISisestus();

        CLIAknaElement[] CLIElemendid = {konsooliTekst, nimekiri, sisestus};

        CLIAken konsooliAken = new CLIAken("Aken testimiseks", CLIElemendid, 75, 3);
        System.out.print(konsooliAken.looCLIAken());

        Taimer taimer = new Taimer(Duration.ofSeconds(25));
        CLIEdenemisRiba edenemisRiba = new CLIEdenemisRiba(1, 0);
        CLIAknaElement[] elemendid = {edenemisRiba};
        CLIAken uusKonsooliAken = new CLIAken("Taimer", elemendid, 75, 3);

        System.out.print(uusKonsooliAken.looCLIAken());
        Scanner luger = new Scanner(System.in);
        taimer.alustaLoendust();
        while (true) {
            if (luger.hasNextLine()) {
                taimer.lopetaLoendus();
                break;
            }
            edenemisRiba.setProtsent(taimer.getProtsent());
            System.out.print(edenemisRiba.uuendaCLIElement());
            System.out.flush();
            try {
                Thread.sleep(10);
            } catch (InterruptedException viga) {
                System.out.println("Katkestus: " + viga.getMessage());
            }
        }
    }
}
