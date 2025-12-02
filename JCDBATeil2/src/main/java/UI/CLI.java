package UI;

import Dataccess.MyCourseRepository;
import domain.Course;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {
    Scanner scan;
    MyCourseRepository repo;

    public CLI(MyCourseRepository repo) { // Konstruktor
        this.scan = new Scanner(System.in);
        this.repo = repo;
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
                    showAllCourses()
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

    private void showAllCourses() {
        List <Course> list = null;
        list = repo.getAll();
        if(list.size() > 0)
        {
            for(Course course : list){
                System.out.println(course);
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
