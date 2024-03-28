import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Failihaldur {
    private File fail;

    public Failihaldur(File fail) {
        this.fail = fail;
    }

    public Failihaldur(String failiNimi) {
        fail = new File(failiNimi);
    }

    public static File[] loeKaust(String kaustaNimi) {
        File kaust = new File(kaustaNimi);
        return kaust.listFiles();
    }

    public static void kontrolliKaust(String kaustaNimi) {
        File kaust = new File(kaustaNimi);
        if (kaust.isDirectory()) return;
        if (!kaust.mkdir()) {
            System.out.println("Viga kausta loomisel");
        }
    }

    public static Failihaldur looFail(String kaustaNimi, String failiNimi) {
        File kaust = new File(kaustaNimi);
        if (!kaust.isDirectory()) {
            System.out.println("Viga - kaustanimi");
            return null;
        }

        String failiTee = kaustaNimi + File.separator + failiNimi + ".txt";

        File fail = new File(failiTee);
        if (fail.isFile()) {
            System.out.println("Viga - fail juba olemas");
            return null;
        }

        try {
            if (!fail.createNewFile()) {
                System.out.println("Viga - fail");
                return null;
            }
        } catch (IOException viga) {
            System.out.println("Viga: " + viga.getMessage());
            return null;
        }

        return new Failihaldur(fail);
    }

    public void kirjutaRida(String rida) {
        try (PrintWriter failiKirjutaja = new PrintWriter(new FileWriter(fail, true), true)) {
            failiKirjutaja.println(rida);
        } catch (IOException viga) {
            System.out.println("Viga: " + viga.getMessage());
        }
    }
}
