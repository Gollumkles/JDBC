package Dataccess;

import domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlStudentRepository implements MyStudentRepository {

    private Connection con;

    public MySqlStudentRepository() {
        try {
            this.con = MysqlDatabaseConnection.getConnection(
                    "jdbc:mysql://localhost:3306/imstkurssystem", "root", "12345");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Student> searchByStudentID(Long id) {
        return getById(id);
    }

    @Override
    public List<Student> searchByStudentBirthday(Date birthday) {
        String sql = "SELECT * FROM student WHERE birthday = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, birthday);
            ResultSet rs = stmt.executeQuery();
            List<Student> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapResultSetToStudent(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseExeption(e.getMessage());
        }
    }

    @Override
    public List<Student> searchByStudentVorname(String vorname) {
        String sql = "SELECT * FROM student WHERE vorname LIKE ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + vorname + "%");
            ResultSet rs = stmt.executeQuery();
            List<Student> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapResultSetToStudent(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseExeption(e.getMessage());
        }
    }


    @Override
    public Optional<Student> insert(Student entity) {

        String sql = "INSERT INTO student (vorname, nachname, birthday) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getVorname());
            stmt.setString(2, entity.getNachname());
            stmt.setDate(3, entity.getBirthday());

            int rows = stmt.executeUpdate();
            if (rows == 0) return Optional.empty();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return getById(keys.getLong(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseExeption("Insert Fehler: " + e.getMessage());
        }
    }

    @Override
    public Optional<Student> getById(Long id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToStudent(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseExeption("GetById Fehler: " + e.getMessage());
        }
    }

    @Override
    public List<Student> getAll() {
        String sql = "SELECT * FROM student";
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            List<Student> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapResultSetToStudent(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseExeption("GetAll Fehler: " + e.getMessage());
        }
    }

    @Override
    public Optional<Student> update(Student entity) {
        String sql = "UPDATE student SET vorname = ?, nachname = ?, birthday = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, entity.getVorname());
            stmt.setString(2, entity.getNachname());
            stmt.setDate(3, entity.getBirthday());
            stmt.setLong(4, entity.getStudentId());

            int rows = stmt.executeUpdate();
            if (rows > 0) return getById(entity.getStudentId());
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseExeption("Update Fehler: " + e.getMessage());
        }
    }


    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM student WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseExeption("Delete Fehler: " + e.getMessage());
        }
    }

    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        return new Student(
                rs.getLong("id"),
                rs.getString("vorname"),
                rs.getString("nachname"),
                rs.getDate("birthday")
        );
    }
}