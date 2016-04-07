package me.zjc.swfu.model.impl;

import com.google.gson.JsonObject;

import java.util.List;

import me.zjc.swfu.model.ICourseModel;
import me.zjc.swfu.netWork.client.CourseClient;
import me.zjc.swfu.tableBean.Course;
import rx.Subscriber;

/**
 * Created by ChuanZhangjiang on 2016/3/25.
 */
public class CourseModel implements ICourseModel {
    private CourseClient mClient = null;

    public CourseModel() {
        mClient = CourseClient.getInstance();
    }

    @Override
    public void chooseCourse(String userObjectId, List<String> courseIds, Subscriber<JsonObject> subscriber) {
        mClient.chooseCourse(userObjectId, courseIds, subscriber);
    }

    @Override
    public void unChooseCourse(String userObjectId, List<String> courseIds, Subscriber<JsonObject> subscriber) {
        mClient.unChooseCourse(userObjectId, courseIds, subscriber);
    }

    @Override
    public void queryMyCourse(String userObjectId, Subscriber<List<Course>> subscriber) {
        mClient.queryMyCourse(userObjectId, subscriber);
    }

    @Override
    public void queryCourseICanChoose(String tableName, Subscriber<List<Course>> subscriber) {
        mClient.queryCourseICanChoose(tableName, subscriber);
    }
}
