package domain;

import javax.xml.crypto.Data;
import java.sql.Date;

public class Course extends BaseEntity{
    private String name;
    private String descripection;
    private int hours;
    private Date beginDate;
    private Date endDate;
    private CourseTyp courseTyp;


    public Course(Long id, String name, String descripection, int hours, Date beginDate, Date endDate, CourseTyp courseTyp) {
        super(id);
        this.setName(name);
        this.setDescripection(descripection);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setCourseTyp(courseTyp);
    }

    public Course(String name, String descripection, int hours, Date beginDate, Date endDate, CourseTyp courseTyp) {
        super(null);
        this.setName(name);
        this.setDescripection(descripection);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setCourseTyp(courseTyp);
    }



    public String getName() {
        return name;
    }

    public String getDescripection() {
        return descripection;
    }

    public int getHours() {
        return hours;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public CourseTyp getCourseTyp() {
        return courseTyp;
    }

    public void setName(String name) {
        if(name!=null && name.length()>1){
            this.name = name;
        } else {
            throw new InvalidValueException("Kurs muss mindestens 2 Zeichen lang sein");
        }
    }

    public void setDescripection(String descripection) {
        this.descripection = descripection;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setCourseTyp(CourseTyp courseTyp) {
        this.courseTyp = courseTyp;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", descripection='" + descripection + '\'' +
                ", hours=" + hours +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", courseTyp=" + courseTyp +
                '}';
    }
}
