import java.util.ArrayList;
import java.util.Scanner;

public class TestProgramm {
    public static void main(String[] args) {
        try (Andmebaas andmebaas = new Andmebaas()) {
            try (Scanner luger = new Scanner(System.in)) {
                KonsooliFunktsioonid.konsooliSuurus(luger, "Programmi käivitamiseks vajuta enter");
                Programm.loeAndmedMallu(andmebaas);
                KonsooliFunktsioonid.looRGB();
                Programm.kasutajaValik(andmebaas, luger);
            }
        }
    }
}
