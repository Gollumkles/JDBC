import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; // <--- FEHLTE
import java.sql.ResultSet;        // <--- FEHLTE
import java.sql.SQLException;

public class JdbcDemo {

    public static void main(String[] args) {
        selectAllDemo();
        //InsertStudent();
        selectAllDemo();
        UpdateStudent();
        selectAllDemo();
        DeleteStudent(1);
        selectAllDemo();
        selectName("tom Zimmer");


    }

    public static void selectAllDemo(){
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

    public static void InsertStudent() {
        String user = "user";
        String pwd = "12345";
        System.out.println("Insert Demo JDBC");

        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung erfolgreich!");

            String sql = "INSERT INTO student (id, name, email) VALUES (NULL, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            try{
                preparedStatement.setString(1, "Peter Zeck");
                preparedStatement.setString(2, "Peter@gmail.com");

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println("Datensätze eingefügt: " + rowsAffected);
            }catch (SQLException e) {
                System.out.println("Fehler: " + e.getMessage());
            }


        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }


    public static void UpdateStudent() {
        String user = "user";
        String pwd = "12345";
        System.out.println("Update Demo JDBC");

        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung erfolgreich!");

            String sql = "UPDATE student SET name = ? WHERE id = 5";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            try {
                preparedStatement.setString(1, "tom Zimmer");
                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println("Anzahl der aktualisierten: " + rowsAffected);

            } catch (SQLException ex) {
                System.out.println("Fehler: " + ex.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    public static void DeleteStudent(int studentid) {
        String user = "user";
        String pwd = "12345";
        System.out.println("Delete Student Demo JDBC");

        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung erfolgreich!");

            String sql = "DELETE FROM student WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            try {
                preparedStatement.setInt(1, studentid);
                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println("Anzahl der gelöschten Datensätze: " + rowsAffected);

            } catch (SQLException ex) {
                System.out.println("Fehler: " + ex.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    public static void selectName(String searchName){
        String user = "user";
        String pwd = "12345";
        System.out.println("Find all by name Demo JDBC");

        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";

        try (Connection conn = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung erfolgreich!");

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM student WHERE name LIKE ?");
            preparedStatement.setString(1,"%"+searchName+"%");
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
