

public class Lõpusõnad {
    /**
     * Meetod randomi kasutamiseks, valib nimekirjast suvaliselt õnnitleva lause taimeri lõpu puhul.
     */
    public static String lõpusõnad() {
        int arv = (int) (Math.random() * 10);
        String[] laused = {"Tubli töö, pomodoro läbitud!",
        "Suurepärane töö, said hakkama!",
        "Võimas produktiivsus, võid uhke olla!",
        "Akadeemiline relv oled, väga tubli!",
        "Vinge, pomodoro saigi läbi!",
        "Tõsiselt produktiivne oled, keep it up!",
        "Tänast päeva võib lugeda edukaks!",
        "Ülesanded, hoidke alt! Siit tuleb produktiivsus ise!",
        "Haridusjumalad imetlevad sind kõrgelt!",
        "Oh sa poiss, milline produktiivne inimene!"};
        return laused[arv];
    }
}
