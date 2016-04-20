package me.zjc.swfu.netWork.response;

/**
 * Created by ChuanZhangjiang on 2016/4/13.
 */
public class QueryScoreResponse {

    private String courseId;
    private String courseName;
    private Integer score;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "QueryScoreResponse{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", score=" + score +
                '}';
    }
}
