package domain;

import java.time.LocalDate;

public class Student {

    private Long studentId;
    private String vorname;
    private String nachname;
    private LocalDate birthday;

    public Student(Long studentId, String vorname, String nachname, LocalDate birthday) {
        setStudentId(studentId);
        setVorname(vorname);
        setNachname(nachname);
        setBirthday(birthday);
    }

    public Student(String vorname, String nachname, LocalDate birthday) {
        setVorname(vorname);
        setNachname(nachname);
        setBirthday(birthday);
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        if (studentId != null && studentId <= 0) {
            throw new InvalidStudentDataException("Student-ID muss positiv sein.");
        }
        this.studentId = studentId;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        if (vorname == null || vorname.trim().isEmpty()) {
            throw new InvalidStudentDataException("Vorname darf nicht leer sein.");
        }
        this.vorname = vorname.trim();
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        if (nachname == null || nachname.trim().isEmpty()) {
            throw new InvalidStudentDataException("Nachname darf nicht leer sein.");
        }
        this.nachname = nachname.trim();
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        if (birthday == null) {
            throw new InvalidStudentDataException("Geburtsdatum darf nicht null sein.");
        }
        if (birthday.isAfter(LocalDate.now())) {
            throw new InvalidStudentDataException("Geburtsdatum darf nicht in der Zukunft liegen.");
        }
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
