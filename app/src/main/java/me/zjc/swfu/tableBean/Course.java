package me.zjc.swfu.tableBean;

import java.util.Comparator;

/**
 * Created by ChuanZhangjiang on 2016/3/23.
 */
public class Course implements Comparator<Course> {

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

    public Course clone() {
        Course course = new Course();
        course.objectId = objectId;
        course.teacher = teacher;
        course.coursAdress = coursAdress;
        course.courseName = courseName;
        course.courseTime = courseTime;
        course.courseTimeObject = courseTimeObject;
        return course;
    }

    @Override
    public int compare(Course lhs, Course rhs) {
        Integer course01Section = lhs.courseTimeObject.getSection().get(0);
        Integer course02Section = rhs.courseTimeObject.getSection().get(0);
        int flag = course01Section.compareTo(course02Section);
        if (flag == 0) {
            return lhs.getCourseName().compareTo(rhs.courseName);
        }
        return flag;
    }
}
