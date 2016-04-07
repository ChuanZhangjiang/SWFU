package me.zjc.swfu.netWork.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import me.zjc.swfu.base.BaseRtrftOnSubscribe;
import me.zjc.swfu.common.Constants;
import me.zjc.swfu.netWork.requestBody.ChuCoRequestBody;
import me.zjc.swfu.netWork.requestBody.ConditionQueryByObjectIdOr;
import me.zjc.swfu.netWork.requestBody.GetUCMByUserParam;
import me.zjc.swfu.netWork.requestBody.UnChuCoRequestBody;
import me.zjc.swfu.netWork.server.ArrayServer;
import me.zjc.swfu.netWork.server.QueryServer;
import me.zjc.swfu.netWork.server.ServerFactory;
import me.zjc.swfu.tableBean.Course;
import me.zjc.swfu.tableBean.UserCourseMapping;
import me.zjc.swfu.util.LogUtil;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ChuanZhangjiang on 2016/3/23.
 */
public class CourseClient {

    public static final String TAG = CourseClient.class.getSimpleName();

    private ServerFactory factory = null;
    private static CourseClient instance = null;

    private CourseClient() {
        factory = ServerFactory.getInstance();
    }

    public static CourseClient getInstance() {
        if (instance == null) {
            instance = new CourseClient();
        }
        return instance;
    }

    /**
     *选课的方法
     * @param objectId 学生选课表的对象Id
     * @param selectedCourse
     * @return
     */
    private Observable<JsonObject> selectCourse(final String objectId, final List<String> selectedCourse) {

        return Observable.create(new BaseRtrftOnSubscribe<JsonObject>() {
            @Override
            protected Call<JsonObject> getCall() {
                ArrayServer arrayServer =
                        factory.createServer(Constants.BMOB_BASE_URL, ArrayServer.class);
                ChuCoRequestBody requestBody = new ChuCoRequestBody();
                requestBody.setCourse(selectedCourse);
                return arrayServer.appendElement(UserCourseMapping.TABLE_NAME, objectId, requestBody);
            }

            @Override
            protected int getSuccessCode() {
                return 200;
            }
        });
    }

    /**
     * 退课
     * @param objectId
     * @param unSelectCourse
     * @return
     */
    private Observable<JsonObject> unSelectCourse(final String objectId, final List<String> unSelectCourse) {
        return Observable.create(new BaseRtrftOnSubscribe<JsonObject>() {
            @Override
            protected Call<JsonObject> getCall() {
                ArrayServer arrayServer = factory.createServer(Constants.BMOB_BASE_URL, ArrayServer.class);
                UnChuCoRequestBody requestBody = new UnChuCoRequestBody();
                requestBody.setCourse(unSelectCourse);
                return arrayServer.deleteElement(UserCourseMapping.TABLE_NAME, objectId, requestBody);
            }

            @Override
            protected int getSuccessCode() {
                return 200;
            }
        });
    }

    /**
     * 根据用户的Id查询出User-Course表中的数据
     * @param userObjectId 用户的objectId
     * @return
     */
    private Observable<UserCourseMapping> getUCMappingByUserId(final String userObjectId) {
        return Observable.create(new BaseRtrftOnSubscribe<JsonObject>() {
            @Override
            protected Call<JsonObject> getCall() {
                QueryServer queryServer =
                        factory.createServer(Constants.BMOB_BASE_URL, QueryServer.class);
                final GetUCMByUserParam param = new GetUCMByUserParam();
                param.setUserObjectId(userObjectId);
                Gson gson = new Gson();
                return queryServer.getObjectByCondition(UserCourseMapping.TABLE_NAME, gson.toJson(param));
            }

            @Override
            protected int getSuccessCode() {
                return 200;
            }
        }).onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<JsonObject, UserCourseMapping>() {
                    @Override
                    public UserCourseMapping call(JsonObject jsonObject) {
                        JsonArray results = jsonObject.getAsJsonArray("results");
                        JsonElement UCMappingJson = results.get(0);
                        Gson gson = new Gson();
                        UserCourseMapping UCMapping =
                                gson.fromJson(UCMappingJson, UserCourseMapping.class);
                        return UCMapping;
                    }
                });
    }


    /**
     * 对外暴露的选课方法
     * @param userObjectId
     * @param selectedCourse
     * @param subscriber
     */
    public void chooseCourse(String userObjectId, final List<String> selectedCourse
            , Subscriber<JsonObject> subscriber ) {

        getUCMappingByUserId(userObjectId)
                .flatMap(new Func1<UserCourseMapping, Observable<JsonObject>>() {
                    @Override
                    public Observable<JsonObject> call(UserCourseMapping userCourseMapping) {
                        String objectId = userCourseMapping.getObjectId();
                        return selectCourse(objectId, selectedCourse);
                    }
                })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 对外暴露的退选方法
     * @param userObjectId
     * @param unSelectCourse
     * @param subscriber
     */
    public void unChooseCourse(String userObjectId, final List<String> unSelectCourse
            , Subscriber<JsonObject> subscriber) {

        getUCMappingByUserId(userObjectId)
                .flatMap(new Func1<UserCourseMapping, Observable<JsonObject>>() {
                    @Override
                    public Observable<JsonObject> call(UserCourseMapping userCourseMapping) {
                        String objectId = userCourseMapping.getObjectId();
                        return unSelectCourse(objectId, unSelectCourse);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 根据查询条件查询课程
     * @param where
     * @return
     */
    private Observable<List<Course>> queryCourseByCondition(final String where) {
        return Observable.create(new BaseRtrftOnSubscribe<JsonObject>() {
            @Override
            protected Call<JsonObject> getCall() {
                QueryServer server = ServerFactory.getInstance().createServer(QueryServer.class);
                return server.getObjectByCondition(Course.TABLE_NAME, where);
            }

            @Override
            protected int getSuccessCode() {
                return 200;
            }
        }).onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<JsonObject, List<Course>>() {
                    @Override
                    public List<Course> call(JsonObject jsonObject) {
                        JsonArray results = jsonObject.getAsJsonArray("results");
                        Gson gson = new Gson();
                        List<Course> courses = new ArrayList<Course>();
                        for (JsonElement courseJson: results) {
                            Course course = gson.fromJson(courseJson, Course.class);
                            courses.add(course);
                        }
                        return courses;
                    }
                });
    }

    /**
     * 对外暴露的查询我选了的课的方法
     * @param userObjectId
     * @param subscriber
     */
    public void queryMyCourse(String userObjectId, Subscriber<List<Course>> subscriber) {
        getUCMappingByUserId(userObjectId)
                .flatMap(new Func1<UserCourseMapping, Observable<List<Course>>>() {
                    @Override
                    public Observable<List<Course>> call(UserCourseMapping userCourseMapping) {
                        List<String> courseIds = userCourseMapping.getCourse();
                        if (courseIds == null || courseIds.size() <= 0) {
                            LogUtil.e(TAG, "no course has selected!");
                            return Observable.create(new Observable.OnSubscribe<List<Course>>() {
                                @Override
                                public void call(Subscriber<? super List<Course>> subscriber) {
                                    List<Course> courseList = null;
                                    subscriber.onNext(courseList);
                                }
                            });
                        }
                        ConditionQueryByObjectIdOr where = new ConditionQueryByObjectIdOr();
                        where.setSubBody(courseIds);
                        Gson gson = new Gson();
                        String whereStr = gson.toJson(where);
                        return queryCourseByCondition(whereStr);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 查询我能选的课，内部方法
     * @param tableName
     * @return
     */
    private Observable<List<Course>> queryCourseICanChoose(final String tableName) {
        return Observable.create(new BaseRtrftOnSubscribe<JsonObject>() {
            @Override
            protected Call<JsonObject> getCall() {
                QueryServer server = factory.createServer(QueryServer.class);
                return server.getAllObject(tableName);
            }

            @Override
            protected int getSuccessCode() {
                return 200;
            }
        }).onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .map(new Func1<JsonObject, List<Course>>() {
                    @Override
                    public List<Course> call(JsonObject jsonObject) {
                        JsonArray coursesJson = jsonObject.getAsJsonArray("results");
                        List<Course> courseList = new ArrayList<Course>();
                        Gson gson = new Gson();
                        for (JsonElement courseJson: coursesJson) {
                            Course course = gson.fromJson(courseJson, Course.class);
                            courseList.add(course);
                        }
                        return courseList;
                    }
                });
    }

    /**
     * 对外暴露的查询自己可选课的方法
     * @param tableName
     * @param subscriber
     */
    public void queryCourseICanChoose(String tableName, Subscriber<List<Course>> subscriber) {
        queryCourseICanChoose(tableName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
