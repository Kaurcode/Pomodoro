public class CLIAken {
    public static int[] CLIAknaSuurus() {
        System.out.println(EscKoodid.salvestaKursoriAsukoht());
        System.out.println(EscKoodid.getCursorPosition());
        return null;
    }

    public static void main(String[] args) {
        CLIAknaSuurus();
    }
}
