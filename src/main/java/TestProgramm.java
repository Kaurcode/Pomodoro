import java.util.ArrayList;
import java.util.Scanner;

public class TestProgramm {
    public static void main(String[] args) {

        try (Scanner luger = new Scanner(System.in)) {
            KonsooliFunktsioonid.looRGB();
            KonsooliFunktsioonid.konsooliSuurus(luger, "Programmi käivitamiseks vajuta enter");
            luger.nextLine();
            String[] andmed = Programm.kusiAndmebaasiAndmed(luger);
            try (Andmebaas andmebaas = new Andmebaas(andmed[0], andmed[1])) {
                Programm.loeAndmedMallu(andmebaas);
                Programm.kasutajaValik(andmebaas, luger);
            }
        }
    }
}
