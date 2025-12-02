import Dataccess.MySqlCourseRepository;
import UI.CLI;

public class Main {
    public static void main(String[] args) {
        CLI mycli = new CLI(new MySqlCourseRepository());
        mycli.start();
    }
}


