package domain;
import java.sql.Date;

public class Student {
    Long studentId;
    String vorname;
    String nachname;
    Date Birthday;

    public Student(Long studentId, String vorname, String nachname, Date birthday) {
        this.studentId = studentId;
        this.vorname = vorname;
        this.nachname = nachname;
        Birthday = birthday;
    }

    public Student(String vorname, String nachname, Date birthday) {
        this.vorname = vorname;
        this.nachname = nachname;
        Birthday = birthday;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(Date birthday) {
        Birthday = birthday;
    }
}
