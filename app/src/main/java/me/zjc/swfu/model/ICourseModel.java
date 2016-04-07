package me.zjc.swfu.model;

import com.google.gson.JsonObject;

import java.util.List;

import me.zjc.swfu.tableBean.Course;
import rx.Subscriber;

/**
 * Created by ChuanZhangjiang on 2016/3/25.
 */
public interface ICourseModel {
    /**
     * 学生选课
     * @param userObjectId 学生的objectId，UserModel的getCurrentUser方法可以拿到
     * @param courseIds 要选课的objectId
     * @param subscriber 选课回调
     */
    public void chooseCourse(String userObjectId, List<String> courseIds
            , Subscriber<JsonObject> subscriber);

    /**
     * 学生退课
     * @param userObjectId
     * @param courseIds
     * @param subscriber 退选回掉
     */
    public void unChooseCourse(String userObjectId, List<String> courseIds
            , Subscriber<JsonObject> subscriber);

    /**
     * 学生查询自己所选的课程
     * @param userObjectId
     * @param subscriber
     */
    public void queryMyCourse(String userObjectId, Subscriber<List<Course>> subscriber);

    public void queryCourseICanChoose(String tableName, Subscriber<List<Course>> subscriber);
}
