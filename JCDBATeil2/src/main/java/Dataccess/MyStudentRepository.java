package Dataccess;

import domain.Student;
import java.util.List;
import java.sql.Date;
import java.util.Optional;

public interface MyStudentRepository extends BaseRepository<Student,Long> {
    List<Student> searchByStudentVorname(String vorname);
    List<Student> searchByStudentBirthday(Date birthday);
    Optional<Student> searchByStudentID(Long id);
    int deleteByName(String vorname, String nachname);
}

