package me.zjc.swfu.presenter;

import android.content.Context;

import com.google.gson.JsonObject;

import java.util.List;

import me.zjc.swfu.model.ICourseModel;
import me.zjc.swfu.model.IUserModel;
import me.zjc.swfu.model.impl.CourseModel;
import me.zjc.swfu.model.impl.UserModel;
import me.zjc.swfu.tableBean.Course;
import me.zjc.swfu.tableBean.User;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.view.ISelectCourseView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ChuanZhangjiang on 2016/4/5.
 */
public class SCPresent {

    public static final String TAG = SCPresent.class.getSimpleName();

    private ISelectCourseView mSCView;
    private ICourseModel mCourseModel;
    private IUserModel mUserModel;
    private Context mContext;
    private User mCurrentUser;

    public SCPresent(ISelectCourseView selectCourseView) {
        this.mSCView = selectCourseView;
        this.mCourseModel = new CourseModel();
        this.mUserModel = new UserModel();
        this.mContext = mSCView.getActivityContext();
        mUserModel.getCurrentUser(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mCurrentUser = user;
                    }
                });
    }

    public void refreshCourseList() {
        mCourseModel.queryCourseICanChoose("Course", new Subscriber<List<Course>>() {
            @Override
            public void onCompleted() {
                LogUtil.e(TAG, "get course I can select is OK !");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG, "get course I can select is failure ! message: " + e.getMessage());
            }

            @Override
            public void onNext(List<Course> courses) {
                mSCView.setCourseData(courses);
                setSelectedCourseData();
            }
        });
    }

    private void setSelectedCourseData() {
        mCourseModel.queryMyCourse(mCurrentUser.getObjectId(), new Subscriber<List<Course>>() {
            @Override
            public void onCompleted() {
                LogUtil.e(TAG, "query my course success !");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG, "query my course failure !" + e.getMessage());
            }

            @Override
            public void onNext(List<Course> courses) {
                if (courses != null) {
                    LogUtil.e(TAG, "course I has selected count is:" + courses.size());
                } else {
                    LogUtil.e(TAG, "course I has selected count is: null" );
                }
                mSCView.initSelectedCourse(courses);
            }
        });
    }

    //提交选课信息
    public void commitCourse() {
        List<String> selectCourseIds = mSCView.getSelectedCourse();
        if (selectCourseIds == null || selectCourseIds.size() < 1) {
            return;
        }

        if (mCurrentUser == null || mCurrentUser.getObjectId() == null) {
            mSCView.showAtyToast("数据出错，请稍后再试");
        }

        mCourseModel.chooseCourse(mCurrentUser.getObjectId(), mSCView.getSelectedCourse()
                , new Subscriber<JsonObject>() {
            @Override
            public void onCompleted() {
                LogUtil.e(TAG, "select course ok");
                mSCView.showAtyToast("选课成功");
                refreshCourseList();
            }

            @Override
            public void onError(Throwable e) {
                mSCView.showAtyToast("选课失败！");
                LogUtil.e(TAG, "select course failure: " + e.getMessage());
            }

            @Override
            public void onNext(JsonObject jsonObject) {

            }
        });
    }

    //退选
    public void unChooseCourse(List<String> courses) {
        mCourseModel.unChooseCourse(mCurrentUser.getObjectId(), courses, new Subscriber<JsonObject>() {
            @Override
            public void onCompleted() {
                mSCView.showAtyToast("退选成功");
                refreshCourseList();
                LogUtil.e(TAG, "unChoose success !");
            }

            @Override
            public void onError(Throwable e) {
                mSCView.showAtyToast("退选失败");
                LogUtil.e(TAG, "unChoose failure !" + e.getMessage());
            }

            @Override
            public void onNext(JsonObject jsonObject) {

            }
        });
    }

}
