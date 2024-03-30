import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class EscKoodid {
    public static String tagastaRGBTaust(String tekst, int r, int g, int b) {
        return String.format("\033[48;2;%d;%d;%dm%s", r, g, b, tekst);
    }

    public static String heleRohelineTekst() {
        return "\033[92m";
    }

    /**
     * Muudab väljundi värvi tagasi normaalseks (default) väärtuseks
     */
    public static String tagastaTavaline() {
        return "\033[0m";
    }

    public static String peidaKursor() {
        return "\033[?25l";
    }

    public static String naitaKursor() {
        return "\033[?25h";
    }

    public static String salvestaKursoriAsukoht() {
        return "\033[s";
    }

    public static String taastaKursoriAsukoht() {
        return "\033[u";
    }

    public static String muudaKursoriAsukohta(int xKoordinaat, int yKoordinaat) {
        return String.format("\033[%d;%dH", xKoordinaat, yKoordinaat);
    }

    public static String tagastaKursoriAsukoht() {
        System.out.print("\033[6n");
        System.out.flush();
        StringBuilder sisend = new StringBuilder();
        int tahtInt;
        char taht;
        try {
            while (true) {
                tahtInt = System.in.read();
                taht = (char) tahtInt;
                if (taht == 'R') break;
                sisend.append(taht);
            }
        } catch (IOException viga) {
            System.out.printf("Sisendi lugemisel tekkis viga: %s", viga);
        }

        return sisend.toString();
    }

    public static String getCursorPosition() {
        System.out.print("\033[6n");
        System.out.flush();

        StringBuilder inputBuffer = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int character;

            // Read characters until 'R' is encountered
            while ((character = reader.read()) != 'R') {
                if (character == -1) {
                    // End of stream reached before 'R'
                    return null;
                }
                inputBuffer.append((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Extract position from input buffer
        String response = inputBuffer.toString();
        return response;
    }

    public static String kustutaValjundAlatesKursorist() {
        return "\033[0J";
    }

    public static String liigutaKursorAllaReaAlgusesse(int ridadeArv) {
        return String.format("\033[%dE", ridadeArv);
    }
}
