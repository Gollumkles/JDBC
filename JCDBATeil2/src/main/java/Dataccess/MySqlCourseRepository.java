package Dataccess;

import domain.Course;
import domain.CourseTyp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MySqlCourseRepository implements MyCourseRepository{
    
    private Connection con;

    public MySqlCourseRepository(Connection con) {

        try {
            this.con = MysqlDatabaseConnection.getConnection(
                    "jdbc:mysql://localhost:3306/imstkurssystem","user","12345");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
        return List.of();
    }

    @Override
    public Optional<Course> insert(Course entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Course> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT * FROM 'course";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Course> courseList = new ArrayList<>();
            while (resultSet.next())
            {
                courseList.add(new Course(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("hours"),
                        resultSet.getDate("begindate"),
                        resultSet.getDate("enddate"),
                        CourseTyp.valueOf(resultSet.getString("coursetype"))
                        )

                );
                return courseList;
            }
        } catch (SQLException e) {
            throw new DatabaseExeption("Database error occured!");
        }
        return null;
    }

    @Override
    public Optional<Course> update(Course entity) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
