package UI;

import Dataccess.DatabaseExeption;
import Dataccess.MyCourseRepository;
import Dataccess.MySqlStudentRepository;
import domain.Course;
import domain.CourseTyp;
import domain.InvalidStudentDataException;
import domain.InvalidValueException;
import domain.Student;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CLI {
    private final Scanner scan;
    private final MyCourseRepository repo;
    private final MySqlStudentRepository studi;

    public CLI(MyCourseRepository repo, MySqlStudentRepository studi) {
        this.scan = new Scanner(System.in);
        this.repo = repo;
        this.studi = studi;
    }

    public void start() {
        String input = "-";
        while (!input.equals("x")) {
            showMenue();
            input = scan.nextLine();

            switch (input) {
                case "1":
                    showAllCourses();
                    break;
                case "2":
                    courseSearch();
                    break;
                case "3":
                    addCourse();
                    break;
                case "4":
                    updateCourseDetails();
                    break;
                case "5":
                    deleteCourse();
                    break;
                case "6":
                    showAllStudents();
                    break;
                case "7":
                    findStudentByName();
                    break;
                case "8":
                    addStudent();
                    break;
                case "9":
                    updateStudent();
                    break;
                case "10":
                    deleteStudent();
                    break;
                case "11":
                    deleteStudentByName();
                    break;

                case "x":
                    System.out.println("Auf Wiedersehen!");
                    break;
                default:
                    System.out.println("Ungültige Eingabe!");
            }
        }
    }

    // ---------------- KURSE ----------------

    private void courseSearch() {
        System.out.println("Geben Sie einen Suchbegriff an:");
        String searchString = scan.nextLine();
        try {
            List<Course> courseList = repo.findAllCoursesByNameOrDescription(searchString);
            if (courseList.isEmpty()) {
                System.out.println("Keine Kurse gefunden.");
            } else {
                for (Course course : courseList) {
                    System.out.println(course);
                }
            }
        } catch (DatabaseExeption e) {
            System.out.println("Datenbankfehler bei der Kurssuche: " + e.getMessage());
        }
    }

    private void deleteCourse() {
        System.out.println("Welchen Kurs möchten Sie löschen? (ID)");
        Long courseIdToDelete = Long.parseLong(scan.nextLine());
        try {
            repo.deleteById(courseIdToDelete);
            System.out.println("Kurs gelöscht (wenn vorhanden).");
        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private void updateCourseDetails() {
        System.out.println("Für welchen Kurs möchten Sie die Details ändern? (ID)");
        Long courseId = Long.parseLong(scan.nextLine());

        try {
            Optional<Course> courseOptional = repo.getById(courseId);
            if (courseOptional.isEmpty()) {
                System.out.println("Kurs mit der ID ist nicht in der DB.");
                return;
            }

            Course course = courseOptional.get();
            System.out.println("Aktuell: " + course);

            System.out.println("Bitte neue Kursdaten angeben (Enter = keine Änderung)");
            System.out.print("Name: ");
            String name = scan.nextLine();

            System.out.print("Beschreibung: ");
            String description = scan.nextLine();

            System.out.print("Hours: ");
            String hours = scan.nextLine();

            System.out.print("Start Datum (YYYY-MM-DD): ");
            String dateFrom = scan.nextLine();

            System.out.print("End Datum (YYYY-MM-DD): ");
            String dateTo = scan.nextLine();

            System.out.print("Kurstyp (ZA/BF/FF/OE): ");
            String courseType = scan.nextLine();

            Course updated = new Course(
                    course.getID(),
                    name.isEmpty() ? course.getName() : name,
                    description.isEmpty() ? course.getDescripection() : description,
                    hours.isEmpty() ? course.getHours() : Integer.parseInt(hours),
                    dateFrom.isEmpty() ? course.getBeginDate() : Date.valueOf(dateFrom),
                    dateTo.isEmpty() ? course.getEndDate() : Date.valueOf(dateTo),
                    courseType.isEmpty() ? course.getCourseTyp() : CourseTyp.valueOf(courseType)
            );

            Optional<Course> optionalCourseUpdated = repo.update(updated);
            optionalCourseUpdated.ifPresentOrElse(
                    c -> System.out.println("Kurs aktualisiert: " + c),
                    () -> System.out.println("Kurs konnte nicht aktualisiert werden.")
            );

        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private void addCourse() {
        try {
            System.out.println("Bitte alle Kursdaten angeben");
            System.out.print("Name: ");
            String name = scan.nextLine();
            if (name.isEmpty()) throw new IllegalArgumentException("Name darf nicht leer sein");

            System.out.print("Beschreibung: ");
            String description = scan.nextLine();
            if (description.isEmpty()) throw new IllegalArgumentException("Beschreibung darf nicht leer sein");

            System.out.print("Hours: ");
            int hours = Integer.parseInt(scan.nextLine());

            System.out.print("Start Datum (YYYY-MM-DD): ");
            Date dateFrom = Date.valueOf(scan.nextLine());

            System.out.print("End Datum (YYYY-MM-DD): ");
            Date dateTo = Date.valueOf(scan.nextLine());

            System.out.print("Kurstyp (ZA/BF/FF/OE): ");
            CourseTyp courseTyp = CourseTyp.valueOf(scan.nextLine());

            Optional<Course> optionalCourse = repo.insert(new Course(name, description, hours, dateFrom, dateTo, courseTyp));

            optionalCourse.ifPresentOrElse(
                    c -> System.out.println("Kurs angelegt: " + c),
                    () -> System.out.println("Kurs konnte nicht angelegt werden.")
            );

        } catch (IllegalArgumentException e) {
            System.out.println("Eingabefehler: " + e.getMessage());
        } catch (InvalidValueException e) {
            System.out.println("Kursdaten nicht korrekt: " + e.getMessage());
        } catch (DatabaseExeption e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        }
    }

    private void showAllCourses() {
        try {
            List<Course> list = repo.getAll();
            if (list.isEmpty()) {
                System.out.println("Keine Kurse vorhanden.");
            } else {
                list.forEach(System.out::println);
            }
        } catch (DatabaseExeption e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        }
    }

    // ---------------- STUDENTEN ----------------

    private void showAllStudents() {
        List<Student> list = studi.getAll();
        if (list.isEmpty()) {
            System.out.println("Keine Studenten vorhanden.");
        } else {
            list.forEach(System.out::println);
        }
    }

    private void findStudentByName() {
        System.out.print("Suchbegriff (Vorname): ");
        String search = scan.nextLine();
        List<Student> list = studi.searchByStudentVorname(search);
        if (list.isEmpty()) System.out.println("Keine Studenten gefunden.");
        else list.forEach(System.out::println);
    }

    private void addStudent() {
        try {
            System.out.print("Vorname: ");
            String vn = scan.nextLine();
            System.out.print("Nachname: ");
            String nn = scan.nextLine();
            System.out.print("Geburtstag (YYYY-MM-DD): ");
            Date geb = Date.valueOf(scan.nextLine());

            Student s = new Student(null, vn, nn, geb);
            Optional<Student> optionalStudent = studi.insert(s);

            optionalStudent.ifPresentOrElse(
                    st -> System.out.println("Student angelegt: " + st),
                    () -> System.out.println("Fehler beim Anlegen.")
            );

        } catch (IllegalArgumentException e) {
            System.out.println("Ungültiges Format: " + e.getMessage());
        } catch (InvalidStudentDataException e) {
            System.out.println("Studentendaten nicht korrekt: " + e.getMessage());
        } catch (DatabaseExeption e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
        }
    }

    private void updateStudent() {
        try {
            System.out.print("ID des Studenten zum Bearbeiten: ");
            Long id = Long.parseLong(scan.nextLine());

            Optional<Student> sOpt = studi.getById(id);
            if (sOpt.isEmpty()) {
                System.out.println("Student nicht gefunden!");
                return;
            }

            Student s = sOpt.get();
            System.out.println("Aktuell: " + s);

            System.out.print("Neuer Vorname (Enter = behalten): ");
            String vn = scan.nextLine();
            if (!vn.isEmpty()) s.setVorname(vn);

            System.out.print("Neuer Nachname (Enter = behalten): ");
            String nn = scan.nextLine();
            if (!nn.isEmpty()) s.setNachname(nn);

            System.out.print("Neuer Geburtstag (YYYY-MM-DD) (Enter = behalten): ");
            String dateStr = scan.nextLine();
            if (!dateStr.isEmpty()) s.setBirthday(Date.valueOf(dateStr));

            // Wichtig: id bleibt gleich
            s.setStudentId(id);

            Optional<Student> updated = studi.update(s);
            updated.ifPresentOrElse(
                    st -> System.out.println("Student aktualisiert: " + st),
                    () -> System.out.println("Student konnte nicht aktualisiert werden.")
            );

        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        try {
            System.out.print("ID des zu löschenden Studenten: ");
            Long id = Long.parseLong(scan.nextLine());
            studi.deleteById(id);
            System.out.println("Löschvorgang durchgeführt.");
        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private void deleteStudentByName() {
        try {
            System.out.print("Vorname: ");
            String vn = scan.nextLine().trim();
            System.out.print("Nachname: ");
            String nn = scan.nextLine().trim();

            int deleted = studi.deleteByName(vn, nn);
            if (deleted == 0) {
                System.out.println("Kein Student mit diesem Namen gefunden.");
            } else {
                System.out.println("Gelöscht: " + deleted + " Datensatz/Datensätze.");
            }
        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }


    private void showMenue() {
        System.out.println("---------------- KURSMANAGEMENT ----------------");
        System.out.println("(1) Alle Kurse anzeigen");
        System.out.println("(2) Kurs suchen (nach Name/Beschreibung)");
        System.out.println("(3) Neuen Kurs anlegen");
        System.out.println("(4) Kurs bearbeiten");
        System.out.println("(5) Kurs löschen");
        System.out.println("---------------- STUDENTEN ----------------");
        System.out.println("(6) Alle Studenten anzeigen");
        System.out.println("(7) Student suchen (Vorname)");
        System.out.println("(8) Neuen Student anlegen");
        System.out.println("(9) Student bearbeiten");
        System.out.println("(10) Student löschen");
        System.out.println("(11) Student löschen (Vorname+Nachname)");
        System.out.println("x.  Beenden");
        System.out.print("Auswahl: ");
    }
}
