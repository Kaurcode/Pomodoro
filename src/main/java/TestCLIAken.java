public class TestCLIAken {
    public static void main(String[] args) {
        int[] koordinaadid = CLIAken.CLIAknaSuurus("Programmi käivitamiseks vajuta enter");
        for (int i : koordinaadid) {
            System.out.println(i);
        }
    }
}
