package UI;

import java.util.Scanner;

public class CLI {
    Scanner scan;

    public CLI() { // Konstruktor
        this.scan = new Scanner(System.in);
    }

    public void start() {
        String input = "-";
        while (!input.equals("x")) {   // richtige Schleifenbedingung
            showMenue();
            input = scan.nextLine();
            switch (input) {
                case "1":
                    System.out.println("Kurs eingabe");
                    break;
                case "2":
                    System.out.println("Alle Kurse anzeigen");
                    break;
                case "x":
                    System.out.println("Tschau");
                    break;
                default:
                    inputError();
                    break;
            }
        }
    }

    private void showMenue() {
        System.out.println("------------Kurs Management-----------------------");
        System.out.println("(1) Kurs Eingeben \t (2) Alle Kurse anzeigen");
        System.out.println("(x) ENDE");
    }

    private void inputError(){
        System.out.println("Bitte nur die Zahlen der Menüauswahl eingeben");
    }
}
