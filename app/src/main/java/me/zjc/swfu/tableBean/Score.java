package me.zjc.swfu.tableBean;

/**
 * Created by ChuanZhangjiang on 2016/4/11.
 */
public class Score {

    public static final String TAB_NAME = "Score";

    private String objectId;
    private String StudentObjectId;
    private String CourseObjectId;
    private Integer Score;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getStudentObjectId() {
        return StudentObjectId;
    }

    public void setStudentObjectId(String studentObjectId) {
        StudentObjectId = studentObjectId;
    }

    public String getCourseObjectId() {
        return CourseObjectId;
    }

    public void setCourseObjectId(String courseObjectId) {
        CourseObjectId = courseObjectId;
    }

    public Integer getScore() {
        return Score;
    }

    public void setScore(Integer score) {
        Score = score;
    }
}
