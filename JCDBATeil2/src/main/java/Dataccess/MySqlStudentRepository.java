package Dataccess;

import domain.Student;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class MySqlStudentRepository implements MyStudentRepository{

    private Connection con;

    public MySqlStudentRepository() {
        try {
            this.con = MysqlDatabaseConnection.getConnection(
                    "jdbc:mysql://localhost:3306/imstkurssystem", "user", "12345");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> searchByStudentID(Long id) {
        String sql = "SELECT * FROM student WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            List<Student> result = new java.util.ArrayList<>();

            while (rs.next()) {
                result.add(
                        new Student(
                                rs.getLong("id"),
                                rs.getString("vorname"),
                                rs.getString("nachname"),
                                rs.getDate("birthday")
                        )
                );
            }

            return result;

        } catch (SQLException e) {
            throw new DatabaseExeption("Suche Student Fehler: " + e.getMessage());
        }
    }


    @Override
    public List<Student> searchByStudentBirthday(Date birthday) {
        return List.of();
    }

    @Override
    public List<Student> searchByStudentVorname(String vorname) {
        return List.of();
    }



    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Student> update(Student entity) {
        return Optional.empty();
    }

    @Override
    public List<Student> getAll() {
        return List.of();
    }

    @Override
    public Optional<Student> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> insert(Student entity) {
        return Optional.empty();
    }
}
