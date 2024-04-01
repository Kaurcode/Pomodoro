public class CLIValjund {
    StringBuilder valjund;

    public CLIValjund() {
        this.valjund = new StringBuilder();
    }

    public void lisaValjundisse(String sone) {
        valjund.append(sone);
    }

    public void valjastaValjund() {
        System.out.print(valjund);
        System.out.flush();

        valjund.delete(0, valjund.length());
    }
}
