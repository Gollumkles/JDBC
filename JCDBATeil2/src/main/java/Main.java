import Dataccess.MySqlCourseRepository;
import Dataccess.MySqlStudentRepository;
import UI.CLI;

public class Main {
    public static void main(String[] args) {
        CLI mycli = new CLI(new MySqlCourseRepository(), new MySqlStudentRepository());
        mycli.start();
    }
}


