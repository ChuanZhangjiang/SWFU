package me.zjc.swfu.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.zjc.swfu.model.ICourseModel;
import me.zjc.swfu.model.IUserModel;
import me.zjc.swfu.model.impl.CourseModel;
import me.zjc.swfu.model.impl.UserModel;
import me.zjc.swfu.tableBean.Course;
import me.zjc.swfu.tableBean.CourseTime;
import me.zjc.swfu.tableBean.User;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.view.ICourseTableView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ChuanZhangjiang on 2016/4/7.
 */
public class CTPresenter {

    public static final String TAG = CTPresenter.class.getSimpleName();

    private ICourseModel mCourseModel = null;
    private ICourseTableView mCourseTableView = null;
    private User mCurrentUser = null;
    private Context mContext = null;
    private Map<Integer, List<Course>> mWeekDayCourseMap = null;

    public CTPresenter(ICourseTableView courseTableView) {
        this.mCourseTableView = courseTableView;
        this.mContext = mCourseTableView.getActivityContext();
        this.mCourseModel = new CourseModel();
        IUserModel userModel = new UserModel();
        userModel.getCurrentUser(mContext)
                .onBackpressureBuffer()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mCurrentUser = user;
                    }
                });
    }

    //刷新课表
    public void refreshCT(final SwipeRefreshLayout swipeRefreshLayout) {
        mCourseModel.queryMyCourse(mCurrentUser.getObjectId(), new Subscriber<List<Course>>() {
            @Override
            public void onCompleted() {
                LogUtil.e(TAG, "get course I has chosen is completed !");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG, "get course I has chose is failure !" + e.getMessage());
            }

            @Override
            public void onNext(List<Course> courses) {
                if (courses == null) {
                    courses = new ArrayList<Course>();
                }
                classifyCourse(courses);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void classifyCourse(List<Course> courses) {
        List<Course> mondayCourse = new ArrayList<>();
        List<Course> tuesdayCourse = new ArrayList<>();
        List<Course> wednesdayCourse = new ArrayList<>();
        List<Course> thursdayCourse = new ArrayList<>();
        List<Course> fridayCourse = new ArrayList<>();
        List<Course> saturdayCourse = new ArrayList<>();
        List<Course> sundayCourse = new ArrayList<>();

        Gson gson = new Gson();

        for (Course course: courses) {

            String courseTimeJsonArrayStr = course.getCourseTime();
            List<CourseTime> courseTimeList = gson.fromJson(courseTimeJsonArrayStr
                    , new TypeToken<List<CourseTime>>(){}.getType());

            for (CourseTime courseTime: courseTimeList) {
                int weekDay = courseTime.getWhatDay();
                course.setCourseTimeObject(courseTime);
                switch (weekDay) {
                    case 1://星期一
                        mondayCourse.add(course);
                        break;
                    case 2://星期二
                        tuesdayCourse.add(course);
                        break;
                    case 3:
                        wednesdayCourse.add(course);
                        break;
                    case 4:
                        thursdayCourse.add(course);
                        break;
                    case 5:
                        fridayCourse.add(course);
                        break;
                    case 6:
                        saturdayCourse.add(course);
                        break;
                    case 7:
                        sundayCourse.add(course);
                        break;
                }
            }

        }

        if (mWeekDayCourseMap == null) {
            mWeekDayCourseMap = new HashMap<>();
        }

        mWeekDayCourseMap.put(1, mondayCourse);
        mWeekDayCourseMap.put(2, tuesdayCourse);
        mWeekDayCourseMap.put(3, wednesdayCourse);
        mWeekDayCourseMap.put(4, thursdayCourse);
        mWeekDayCourseMap.put(5, fridayCourse);
        mWeekDayCourseMap.put(6, saturdayCourse);
        mWeekDayCourseMap.put(7, sundayCourse);
    }


}
