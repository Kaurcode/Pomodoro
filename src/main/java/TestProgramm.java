public class TestProgramm {
    public static void main(String[] args) {
        Andmebaas andmebaas = Programm.alustaProgramm();
        Pomodoro pomodoro = Programm.kasutajaValik(andmebaas);
        System.out.println(pomodoro);
        //Pomodoro taimer
        Lõpusõnad.lõpusõnad();
    }
}
