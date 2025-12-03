package UI;

import Dataccess.DatabaseExeption;
import Dataccess.MyCourseRepository;
import domain.Course;
import domain.CourseTyp;
import domain.InvalidValueException;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                    addCourse();
                    break;
                case "2":
                    showAllCourses();
                    System.out.println("Alle Kurse anzeigen");
                    break;

                case "3":
                    showCourseDetails();
                    break;
                case "4":
                    updateCourseDetails();
                    break;
                case "5":
                    deleteCourse();
                    break;
                case "6":
                    courseSearch();
                    break;
                case "7":
                    runningCourses();
                    break;;
                case "x":
                    System.out.println("Tschau");
                    break;
                default:
                    inputError();
                    break;
            }
        }
    }

    private void runningCourses() {
        System.out.println("Laufende Kurse");
        List<Course> list;
        try{
            list = repo.findAll
        }catch (DatabaseExeption databaseExeption){
            System.out.println("Fehler beim zugriff auf db" + databaseExeption.getMessage());
        }catch (Exception exception){
            System.out.println("undefinierter fehler" + exception.getMessage());
        }

    }

    private void courseSearch() {
        System.out.println("Geben Sie einen Suchbegriff an");
        String searchString = scan.nextLine();
        List<Course> courseList;
        try{
            courseList = repo.findAllCoursesByNameOrDescription(searchString);
            for(Course course : courseList){
                System.out.println(courseList);
            }
        }catch (DatabaseExeption databaseExeption){
            System.out.println("Datenbankfehler bei der Kurssuche" + databaseExeption.getMessage());
        }
    }

    private void deleteCourse() {
        System.out.println("Welchen Kurs möchten sie löschen");
        Long courseIdToDelete = Long.parseLong((scan.nextLine()));

        try{
            repo.deleteById(courseIdToDelete);
        }catch (Exception e){
            System.out.println("Unbenkannter fehler" + e.getMessage());
        }
    }

    private void updateCourseDetails() {
        System.out.println("Für welchen Kurs möchten sie die Details ändern");
        Long courseId = Long.parseLong(scan.nextLine());
        try{

            Optional<Course> courseOptional = repo.getById(courseId);
            if(courseOptional.isEmpty()){
                System.out.println("Kurs mit der Id ist nicht in der db");
            }else{
                Course course = courseOptional.get();
                System.out.println(course);
                String name, description, hours, dateFrom, dateTo, courseType;

                System.out.println("Bitte neue Kursdaten angeben (Enter fallls keine änderung gewünscht ist)");
                System.out.println("Name: ");
                name = scan.nextLine();

                System.out.println("description: ");
                description = scan.nextLine();

                System.out.println("hours: ");
                hours = scan.nextLine();

                System.out.println("Start Datum: ");
                dateFrom = scan.nextLine();

                System.out.println("End Datum: ");
                dateTo = scan.nextLine();

                System.out.println("Kurstyp ZA/BF/FF/OE");
                courseType = scan.nextLine();

                Optional<Course>   optionalCourseUpdated = repo.update(
                        new Course(
                        course.getID(),
                        name.equals("") ? course.getName() : name,
                        description.equals("") ? course.getDescripection() : description,
                        hours.equals("") ? course.getHours() : Integer.parseInt(hours),
                        dateFrom.equals("") ? course.getBeginDate() : Date.valueOf(dateFrom),
                        dateTo.equals("") ? course.getBeginDate() : Date.valueOf(dateTo),
                        courseType.equals("")?course.getCourseTyp() : CourseTyp.valueOf(courseType)
                        )
                );
            optionalCourseUpdated.ifPresentOrElse(
                    (c) -> System.out.println("Kurs aktualiesiet" + c),
                    () -> System.out.println("Kurs konnte nicht aktualisiert werden")
            );
            }
        }catch (Exception exception){
            System.out.println("unbekannter fehler" + exception.getMessage());
        }
    }

    private void addCourse() {
        String name, description;
        int hours;
        Date dateFrom, dateTo;
        CourseTyp courseTyp;

        try{
            System.out.println("bitte alle kurs daten angeben");
            System.out.println("Name: ");
            name = scan.nextLine();
            if (name.equals("")) throw new IllegalArgumentException(("Eingabe darf nict leer sein"));

            System.out.println("description: ");
            description = scan.nextLine();
            if (description.equals("")) throw new IllegalArgumentException(("Eingabe darf nict leer sein"));

            System.out.println("hours: ");
            hours = Integer.parseInt(scan.nextLine());

            System.out.println("Start Datum: ");
            dateFrom = Date.valueOf(scan.nextLine());

            System.out.println("End Datum: ");
            dateTo = Date.valueOf(scan.nextLine());

            System.out.println("Kurstyp ZA/BF/FF/OE");
            courseTyp = CourseTyp.valueOf(scan.nextLine());

            Optional<Course> optionalCourse = repo.insert(
                    new Course(name,description,hours,dateFrom,dateTo,courseTyp)
            );

            if(optionalCourse.isPresent()){
                System.out.println("Kurs angelegt" + optionalCourse.get());
            }else{
                System.out.println("Kurs könnte nicht angelegt werden");
            }

        }catch (IllegalArgumentException illegalArgumentException){
            System.out.println("Eingabefehler" + illegalArgumentException);
        }catch (InvalidValueException invalidValueException){
            System.out.println("Kursdaten nicht korrejt" + invalidValueException.getMessage());
        }catch (DatabaseExeption databaseExeption){
            System.out.println("Datenbankfehler beim Einfügen" + databaseExeption.getMessage());
        }catch (Exception exception){
            System.out.println("Unbekannter fehler" + exception.getMessage());
        }
    }

    private void showAllCourses() {
        List <Course> list = null;
        try{

            list = repo.getAll();
            if(list.size() > 0)
            {
                for(Course course : list){
                    System.out.println(course);
                }
            }else {
                System.out.println("Kurs leer");
            }
        } catch(DatabaseExeption databaseException)
            {
                System.out.println("Datenbankkfehler " + databaseException.getMessage());
            } catch(Exception exception){
                System.out.println("unbekannterfehler" + exception.getMessage());
            }
    }


    private void showCourseDetails(){
        System.out.println("Für welchen Kurs möchten Siedie Kurs Details anzeigen");
        Long courseId = Long.parseLong((scan.nextLine()));

        try {
            Optional<Course> courseOptional = repo.getById(courseId);
            if(courseOptional.isPresent())
            {
                System.out.println(courseOptional.get());
            }else{
                System.out.println("Kurse mit der ID" + courseId + "nicht gefunden");
            }
        } catch (DatabaseExeption databaseExeption) {
            System.out.println("Datenbankfehler bei Kursanzeigen " + databaseExeption.getMessage());

        }catch (Exception exception){
            System.out.println("Unbekannterfehler" + exception.getMessage());
        }
    }

    private void showMenue() {
        System.out.println("------------Kurs Management-----------------------");
        System.out.println("(1) Kurs Eingeben \t (2) Alle Kurse anzeigen \t (3) nach id suchen \t (4) Update \t(5) Löschen \t (6)genaue suche \t (7) Laufende Kurse");
        System.out.println("(x) ENDE");
    }

    private void inputError(){
        System.out.println("Bitte nur die Zahlen der Menüauswahl eingeben");
    }


}
