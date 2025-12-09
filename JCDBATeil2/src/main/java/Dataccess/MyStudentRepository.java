package Dataccess;

import domain.Student;
import java.util.List;
import java.sql.Date;

public interface MyStudentRepository extends BaseRepository<Student,Long> {

    List<Student> searchByStudentVorname(String vorname);
    List<Student> searchByStudentBirthday(Date birthday);
    List<Student> searchByStudentID(Long id);
}
