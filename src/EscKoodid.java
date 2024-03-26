public class EscKoodid {
    /**
     * Väljastab rgb värvi teksti
     * @param tekst väljastatav tekst
     * @param r punase värvi väärtus
     * @param g rohelise värvi väärtus
     * @param b sinise värvi väärtus
     */
    public static void rgbValjund(String tekst, int r, int g, int b) {
        System.out.printf("\033[38;2;%d;%d;%dm", r, g, b);
        System.out.print(tekst);
        System.out.println(tagastaTavaline());
    }

    public static String tagastaRGBTaust(String tekst, int r, int g, int b) {
        return String.format("\033[48;2;%d;%d;%dm%s", r, g, b, tekst);
    }

    /**
     * Muudab väljundi värvi tagasi normaalseks (default) väärtuseks
     */
    public static String tagastaTavaline() {
        return "\033[0m";
    }

    /**
     * Väljastab helerohelist värvi teksti
     * @param tekst väljastatav (heleroheline) tekst
     */
    public static void heleRohelineValjund(String tekst) {
        System.out.print("\033[92m");
        System.out.print(tekst);
        System.out.println(tagastaTavaline());
    }

    /**
     * Väljastab helepunast värvi teksti
     * @param tekst väljastatav (helepunane) tekst
     */
    public static void helePunaneValjund(String tekst) {
        System.out.print("\033[91m");
        System.out.print(tekst);
        System.out.println(tagastaTavaline());
    }

    /**
     * Väljastab helelillat värvi teksti
     * @param tekst väljastatav (helelilla) tekst
     */
    public static void heleLillaValjund(String tekst) {
        System.out.print("\033[95m");
        System.out.print(tekst);
        System.out.println(tagastaTavaline());
    }

    /**
     * Väljastab helekollast värvi teksti
     * @param tekst väljastatav (helekollane) tekst
     */
    public static void heleKollaneValjund(String tekst) {
        System.out.print("\033[93m");
        System.out.print(tekst);
        System.out.println(tagastaTavaline());
    }

    public static String peidaKursor() {
        return "\033[?25l";
    }

    public static String naitaKursor() {
        return "\033[?25h";
    }

}
