package Dataccess;

import domain.Student;

import javax.xml.crypto.Data;
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
    public Optional<Student> searchByStudentID(Long id) {
        String sql = "SELECT * FROM student WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student student = new Student(
                        rs.getLong("id"),
                        rs.getString("vorname"),
                        rs.getString("nachname"),
                        rs.getDate("birthday")
                );
                return Optional.of(student);
            } else {
                return Optional.empty();
            }

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
        String sql = "SELECT * FROM student WHERE vorname=?";
        try(PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1, vorname);
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

        } catch (DatabaseExeption e) {
            throw new DatabaseExeption(e.getMessage());
        }catch (RuntimeException e){
            throw new RuntimeException((e));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        String sql = "INSERT INTO student (vorname, nachname, birthday) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getVorname());
            stmt.setString(2, entity.getNachname());
            stmt.setDate(3, entity.getBirthday());

            int rows = stmt.executeUpdate();

            if (rows == 0) {
                return Optional.empty();
            }

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                Long id = keys.getLong(1);
                return searchByStudentID(id);
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new DatabaseExeption("Insert Fehler: " + e.getMessage());
        }
    }

}
