import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class EscKoodid {
    public static String tagastaRGBTaust(String tekst, int r, int g, int b) {
        return String.format("\033[48;2;%d;%d;%dm%s", r, g, b, tekst);
    }

    public static String hallTaust() {
        return "\033[100m";
    }

    public static String mustTaust() {
        return "\033[40m";
    }

    public static String heleRohelineTekst() {
        return "\033[92m";
    }
    public static String heleLillaTekst() {
        return "\033[95m";
    }

    public static String lillaTekst() {
        return "\033[35m";
    }

    /**
     * Muudab väljundi värvi tagasi normaalseks (default) väärtuseks
     */
    public static String tagastaTavaline() {
        return "\033[0m";
    }
    public static String tavalineTekst() {
        return "\033[39m";
    }
    public static String tavalineTaust() {
        return "\033[49m";
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

    public static String muudaKursoriAsukohta(int yKoordinaat, int xKoordinaat) {
        return String.format("\033[%d;%dH", yKoordinaat, xKoordinaat);
    }

    public static String tagastaKursoriAsukoht() {
        return "\033[6n";
    }

    public static String kustutaValjundAlatesKursorist() {
        return "\033[0J";
    }

    public static String liigutaKursorAllaReaAlgusesse(int ridadeArv) {
        return String.format("\033[%dE", ridadeArv);
    }
}
