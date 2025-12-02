import Dataccess.MySqlCourseRepository;
import Dataccess.MysqlDatabaseConnection;
import UI.CLI;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            CLI mycli = new CLI(new MySqlCourseRepository());
            mycli.start();
        }catch (SQLException e) {
            System.out.println("Datenbankfehler" + e.getMessage() + "SQL State" + e.getSQLState());
        } catch (ClassNotFoundException e) {
            System.out.println("Datenbankfehler " + e.getMessage());
        }


    }
}
