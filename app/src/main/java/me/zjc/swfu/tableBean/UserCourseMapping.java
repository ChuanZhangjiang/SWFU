package me.zjc.swfu.tableBean;

import java.util.List;

/**
 * Created by ChuanZhangjiang on 2016/3/23.
 */
public class UserCourseMapping {

    public static final String TABLE_NAME = "User-Course";

    private String objectId;
    private String userObjectId;
    private List<String> course;
    private Boolean canSelect;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public List<String> getCourse() {
        return course;
    }

    public void setCourse(List<String> course) {
        this.course = course;
    }

    public Boolean getCanSelect() {
        return canSelect;
    }

    public void setCanSelect(Boolean canSelect) {
        this.canSelect = canSelect;
    }
}
