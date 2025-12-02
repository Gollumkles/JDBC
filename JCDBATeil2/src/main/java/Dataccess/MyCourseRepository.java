package Dataccess;

import domain.Course;
import domain.CourseTyp;

import java.util.Date;
import java.util.List;

public interface MyCourseRepository extends BaseRepository<Course,Long>{
    List<Course> findAllCoursesByName(String name);
    List<Course> findAllCoursesByDescription(String description);
    List<Course> findAllCoursesByNameOrDescription(String searchText);
    List<Course> findAllCoursesByCourseId(CourseTyp courseTyp);
    List<Course> findAllCoursesByStartDate(Date startDate);
    List<Course> findAllCourses();
}
