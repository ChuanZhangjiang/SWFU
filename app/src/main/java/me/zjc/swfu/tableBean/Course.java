package me.zjc.swfu.tableBean;

/**
 * Created by ChuanZhangjiang on 2016/3/23.
 */
public class Course {

    public static final String TABLE_NAME = "Course";

    private String objectId;
    private String courseName;
    private String teacher;
    private String coursAdress;
    private String courseTime;//JsonArray
    private CourseTime courseTimeObject;//上课时间对象

    public CourseTime getCourseTimeObject() {
        return courseTimeObject;
    }

    public void setCourseTimeObject(CourseTime courseTimeObject) {
        this.courseTimeObject = courseTimeObject;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCoursAdress() {
        return coursAdress;
    }

    public void setCoursAdress(String coursAdress) {
        this.coursAdress = coursAdress;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }
}
