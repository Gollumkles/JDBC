package Dataccess;

import domain.Course;
import domain.CourseTyp;
import util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MySqlCourseRepository implements MyCourseRepository {

    private Connection con;

    public MySqlCourseRepository() {
        try {
            this.con = MysqlDatabaseConnection.getConnection(
                    "jdbc:mysql://localhost:3306/imstkurssystem", "user", "12345");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------------- INSERT -------------------------

    @Override
    public Optional<Course> insert(Course entity) {
        Assert.notNull(entity);

        String sql = "INSERT INTO `course` (`name`, `description`, `hours`, `begindate`, `enddate`, `coursetype`) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescripection());
            stmt.setInt(3, entity.getHours());
            stmt.setDate(4, entity.getBeginDate());
            stmt.setDate(5, entity.getEndDate());
            stmt.setString(6, entity.getCourseTyp().toString());

            int rows = stmt.executeUpdate();

            if (rows == 0) {
                return Optional.empty();
            }

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return getById(keys.getLong(1));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new DatabaseExeption("Insert Fehler: " + e.getMessage());
        }
    }

    // ------------------------- GET BY ID -------------------------

    @Override
    public Optional<Course> getById(Long id) {
        Assert.notNull(id);

        String sql = "SELECT * FROM `course` WHERE `id` = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            Course c = new Course(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("hours"),
                    rs.getDate("begindate"),
                    rs.getDate("enddate"),
                    CourseTyp.valueOf(rs.getString("coursetype"))
            );

            return Optional.of(c);

        } catch (SQLException e) {
            throw new DatabaseExeption("getById Fehler: " + e.getMessage());
        }
    }

    // ------------------------- COUNT -------------------------

    private int countCoursesInDbWithId(Long id) {
        String sql = "SELECT COUNT(*) FROM `course` WHERE `id` = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            return 0;
        }
    }

    // ------------------------- GET ALL -------------------------

    @Override
    public List<Course> getAll() {
        String sql = "SELECT * FROM `course`";

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Course> list = new ArrayList<>();

            while (rs.next()) {
                list.add(new Course(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("hours"),
                        rs.getDate("begindate"),
                        rs.getDate("enddate"),
                        CourseTyp.valueOf(rs.getString("coursetype"))
                ));
            }
            return list;

        } catch (SQLException e) {
            throw new DatabaseExeption("getAll Fehler: " + e.getMessage());
        }
    }

    // ------------------------- UPDATE -------------------------

    @Override
    public Optional<Course> update(Course entity) {
        Assert.notNull(entity);

        if (countCoursesInDbWithId(entity.getID()) == 0) {
            return Optional.empty();
        }

        String sql = "UPDATE `course` SET `name` = ?, `description` = ?, `hours` = ?, `begindate` = ?, `enddate` = ?, `coursetype` = ? WHERE `id` = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescripection());
            stmt.setInt(3, entity.getHours());
            stmt.setDate(4, entity.getBeginDate());
            stmt.setDate(5, entity.getEndDate());
            stmt.setString(6, entity.getCourseTyp().toString());
            stmt.setLong(7, entity.getID());

            int rows = stmt.executeUpdate();

            if (rows == 0) {
                return Optional.empty();
            }
            return getById(entity.getID());

        } catch (SQLException e) {
            throw new DatabaseExeption("Update Fehler: " + e.getMessage());
        }
    }

    // ------------------------- DELETE -------------------------

    @Override
    public void deleteById(Long id) {
        Assert.notNull(id);
        String sql ="DELETE FROM `course` WHERE  `id` = ?";
        try{
            if(countCoursesInDbWithId(id) == 0){
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();

            }
        }catch (SQLException sqlException){
            throw new DatabaseExeption(sqlException.getMessage());
        }

    }

    // ------------------------- SEARCH (noch leer) -------------------------

    @Override
    public List<Course> findAllCoursesByName(String name) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoursesByDescription(String description) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoursesByNameOrDescription(String searchText) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoursesByCourseId(CourseTyp courseTyp) {
        return List.of();
    }

    @Override
    public List<Course> findAllCoursesByStartDate(Date startDate) {
        return List.of();
    }

    @Override
    public List<Course> findAllCourses() {
        return getAll(); // Delegiert sauber
    }
}
