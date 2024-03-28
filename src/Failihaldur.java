import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Failihaldur {
    private File fail;

    public Failihaldur(File fail) {
        this.fail = fail;
    }

    public Failihaldur(String failiNimi) {
        this(new File(failiNimi));
    }

    public static File[] loeKaust(String kaustaNimi) {
        File kaust = new File(kaustaNimi);
        return kaust.listFiles();
    }

    public static Failihaldur[] looKaustastFailihaldurid(File[] failid) {
        Failihaldur[] failihaldurid = new Failihaldur[failid.length];
        for (int failiIndeks = 0; failiIndeks < failid.length; failiIndeks++) {
            failihaldurid[failiIndeks] = new Failihaldur(failid[failiIndeks]);
        }
        return failihaldurid;
    }

    public static Failihaldur[] looKaustastFailihaldurid(String kaustaNimi) {
        File[] failid = loeKaust(kaustaNimi);
        return looKaustastFailihaldurid(failid);
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

    public ArrayList<String> loeKoikReadFailist() {
        ArrayList<String> read = new ArrayList<String>();

        try (Scanner luger = new Scanner(fail)) {
            while (luger.hasNextLine()) {
                read.add(luger.nextLine());
            }
        } catch (FileNotFoundException viga) {
            System.out.printf("Viga: %s\n", viga.getMessage());
        }

        return read;
    }
}
