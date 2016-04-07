package me.zjc.swfu.view;

import android.content.Context;

import java.util.List;

import me.zjc.swfu.base.BaseActivity;
import me.zjc.swfu.tableBean.Course;

/**
 * 学生选课视图接口
 * Created by ChuanZhangjiang on 2016/3/26.
 */
public interface ISelectCourseView {

    //设置课程数据
    void setCourseData(List<Course> courses);

    //初始化已选择的课程
    void initSelectedCourse(List<Course> selectedCourse);

    //获取已选的课
    List<String> getSelectedCourse();

    Context getActivityContext();

    void showAtyToast(String hint);

}


