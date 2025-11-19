import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; // <--- FEHLTE
import java.sql.ResultSet;        // <--- FEHLTE
import java.sql.SQLException;

public class JdbcDemo {

    public static void main(String[] args) {
        selectAllDemo();
    }

    public static void selectAllDemo() {
        String user = "user";
        String pwd = "12345";
        System.out.println("Select all Demo JDBC");

        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung erfolgreich!");

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM student");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println(id + " | " + name + " | " + email);
            }

        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau: " + e.getMessage());
        }
    }
}
