import Dataccess.MysqlDatabaseConnection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            var myConnection = MysqlDatabaseConnection.getConnection(
                    "jdbc:mysql://localhost:3306/imstkurssystem",
                    "user",
                    "12345"
            );
            System.out.println("Verbindung erfolgreich!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
