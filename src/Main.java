import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner klaviatuuriSisend = new Scanner(System.in);

        System.out.print("Sisesta tööaeg: ");
        int produktiivneAeg = klaviatuuriSisend.nextInt();
        System.out.print("Sisesta puhkeaeg: ");
        int puhkeAeg = klaviatuuriSisend.nextInt();

        Pomodoro pomodoro = new Pomodoro(produktiivneAeg, puhkeAeg);

        System.out.print("Alusta produktiivset aega? ");
        klaviatuuriSisend.nextLine();  // Mul pole õrna aimugi, miks neid on kaks vaja :)
        klaviatuuriSisend.nextLine();

        Taimer produktiivsusTaimer = new Taimer(pomodoro.getProduktiivsusAegSekundites());
        produktiivsusTaimer.alustaLoendust();
        pomodoro.tsukkelTehtud();

        System.out.println("Pomodoro tehtud!");
        System.out.print("Alusta puhkeaega? ");
        klaviatuuriSisend.nextLine();

        Taimer puhkeTaimer = new Taimer(pomodoro.getPuhkeAegSekundites());
        puhkeTaimer.alustaLoendust();
        System.out.println("Puhkus tehtud!");
    }
}
