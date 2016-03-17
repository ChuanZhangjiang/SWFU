package me.zjc.swfu.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonObject;

import java.util.List;

import me.zjc.swfu.base.BaseRtrftOnSubscribe;
import me.zjc.swfu.bean.UpdatePasswordRequestBean;
import me.zjc.swfu.bean.User;
import me.zjc.swfu.common.Constants;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ChuanZhangjiang on 2016/2/9.
 */
public class UserManager {
    private static final String TAG = Constants.UserUrlManager.class.getSimpleName();

    private Retrofit retrofit = null;
    private UserServer userServer = null;

    public Observable<User> register (final User user) {
        init();


        return Observable.create(new BaseRtrftOnSubscribe<User>() {
            @Override
            protected Call<User> getCall() {
                return userServer.register(user);
            }

            @Override
            protected int getSuccessCode() {
                return Constants.UserUrlManager.REGISTER_SUCCESS;
            }
        });
    }

    public Observable<User> login(final String username, final String password) {
        init();

        return Observable.create(new BaseRtrftOnSubscribe<User>() {
            @Override
            protected Call<User> getCall() {
                return userServer.login(username, password);
            }

            @Override
            protected int getSuccessCode() {
                return Constants.UserUrlManager.LOGIN_SUCCESS;
            }
        });
    }

    public Observable<User> getUserByObjectId(final String objectId) {
        init();

        return Observable.create(new BaseRtrftOnSubscribe<User>() {
            @Override
            protected Call<User> getCall() {
                return userServer.getUserByObjectId(objectId);
            }

            @Override
            protected int getSuccessCode() {
                return Constants.UserUrlManager.GET_USER_BY_OBJECTID_SUCCESS;
            }
        });
    }

    public Observable<JsonObject> updateUserByObjectId(final String objectId,
                                                       final User user,
                                                       final String sessionToken) {
        init();

        return Observable.create(new BaseRtrftOnSubscribe<JsonObject>() {
            @Override
            protected Call<JsonObject> getCall() {
                return userServer.updateUserByObjectId(objectId, user, sessionToken);
            }

            @Override
            protected int getSuccessCode() {
                return Constants.UserUrlManager.UPDATE_USER_BY_OBJECTID_SUCCESS;
            }
        });
    }

    public Observable<JsonObject> deleteUserByObjectId(final String objectId,
                                                       final String sessionToken) {

        init();

        return Observable.create(new BaseRtrftOnSubscribe<JsonObject>() {
            @Override
            protected Call<JsonObject> getCall() {
                return userServer.deleteUserByObjectId(objectId, sessionToken);
            }

            @Override
            protected int getSuccessCode() {
                return Constants.UserUrlManager.DELETE_USER_BY_OBJECTID_SUCCESS;
            }
        });
    }

    public Observable<List<User>> getAllUser() {
        init();

        return Observable.create(new BaseRtrftOnSubscribe<List<User>>() {
            @Override
            protected Call<List<User>> getCall() {
                return userServer.getAllUser();
            }

            @Override
            protected int getSuccessCode() {
                return Constants.UserUrlManager.GET_ALL_USER_SUCCESS;
            }
        });
    }

    public Observable<JsonObject> updateUserPassword(final String objectId,
                                                     final UpdatePasswordRequestBean requestBody,
                                                     final String sessionToken) {

        init();

        return Observable.create(new BaseRtrftOnSubscribe<JsonObject>() {
            @Override
            protected Call<JsonObject> getCall() {
                return userServer.updateUserPassword(objectId, requestBody, sessionToken);
            }

            @Override
            protected int getSuccessCode() {
                return Constants.UserUrlManager.UPDATE_PASSWORD_SUCCESS;
            }
        });

    }

    public void saveCurrentUser(Observable<User> user, final Context context) {

        user.onBackpressureBuffer()
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .map(new Func1<User, SharedPreferences.Editor>() {
                    @Override
                    public SharedPreferences.Editor call(User user) {
                        SharedPreferences preferences = context
                                .getSharedPreferences(Constants.sharedPreferencesNameManager.CURRENT_USER
                                        , Activity.MODE_PRIVATE);

                        SharedPreferences.Editor editor = preferences.edit();
                        if (user.getObjectId() != null) {
                            editor.putString(Constants.UserTableField.OBJECT_ID, user.getObjectId());
                        }
                        if (user.getUsername() != null) {
                            editor.putString(Constants.UserTableField.USER_NAME, user.getUsername());
                        }
                        if (user.getName() != null) {
                            editor.putString(Constants.UserTableField.NAME, user.getName());
                        }
                        if (user.getProfessional() != null) {
                            editor.putString(Constants.UserTableField.PROFESSIONAL, user.getProfessional());
                        }
                        if (user.getSex() != null) {
                            editor.putBoolean(Constants.UserTableField.SEX, user.getSex());
                        }
                        if (user.getAge() != null) {
                            editor.putInt(Constants.UserTableField.AGE, user.getAge());
                        }
                        if (user.getClassName() != null) {
                            editor.putString(Constants.UserTableField.CLASSNAME, user.getClassName());
                        }
                        if (user.getLevel() != null) {
                            editor.putInt(Constants.UserTableField.LEVEL, user.getLevel());
                        }
                        if (user.getHeader_url() != null) {
                            editor.putString(Constants.UserTableField.HEADER_URL, user.getHeader_url());
                        }
                        if (user.getDec() != null) {
                            editor.putString(Constants.UserTableField.DEC, user.getDec());
                        }
                        if (user.getSessionToken() != null) {
                            editor.putString(Constants.UserTableField.SESSIONTOKEN, user.getSessionToken());
                        }
                        return editor;
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Action1<SharedPreferences.Editor>() {
                    @Override
                    public void call(SharedPreferences.Editor editor) {
                        editor.commit();
                    }
                });
    }

    public Observable<User> getCurrentUser(final Context context) {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                User user = new User();
                SharedPreferences preferences = context.getSharedPreferences
                        (Constants.sharedPreferencesNameManager.CURRENT_USER, Activity.MODE_PRIVATE);
                user.setObjectId(preferences.getString(Constants.UserTableField.OBJECT_ID, ""));
                user.setUsername(preferences.getString(Constants.UserTableField.USER_NAME, ""));
                user.setName(preferences.getString(Constants.UserTableField.NAME, ""));
                user.setProfessional(preferences.getString(Constants.UserTableField.PROFESSIONAL, ""));
                user.setSex(preferences.getBoolean(Constants.UserTableField.SEX, true));
                user.setAge(preferences.getInt(Constants.UserTableField.AGE, 0));
                user.setClassName(preferences.getString(Constants.UserTableField.CLASSNAME, ""));
                user.setLevel(preferences.getInt(Constants.UserTableField.LEVEL, 0));
                user.setHeader_url(preferences.getString(Constants.UserTableField.HEADER_URL, ""));
                user.setDec(preferences.getString(Constants.UserTableField.DEC, ""));
                user.setSessionToken(preferences.getString(Constants.UserTableField.SESSIONTOKEN, ""));
                if ("".equals(user.getObjectId())) {
                    subscriber.onError(new Throwable("null current user"));
                } else {
                    subscriber.onNext(user);
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public void clearCurrentUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences
                (Constants.sharedPreferencesNameManager.CURRENT_USER, Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
    }

    private interface UserServer {
        /**
         * 注册
         * @param user
         * @return
         */
        @Headers({
                "X-Bmob-Application-Id:" + Constants.APP_ID,
                "X-Bmob-REST-API-Key:" + Constants.REST_API_KEY,
                "Content-Type: application/json"
        })
        @POST(Constants.UserUrlManager.REGISTER_URL)
        Call<User> register(@Body User user);

        /**
         * 用户登录
         * @param username
         * @param password
         * @return
         */
        @Headers({
                "X-Bmob-Application-Id:" + Constants.APP_ID,
                "X-Bmob-REST-API-Key:" + Constants.REST_API_KEY,
                "Content-Type: application/json"
        })
        @GET(Constants.UserUrlManager.LOGIN_URL)
        Call<User> login(@Query("username") String username, @Query("password") String password);

        /**
         * 根据用户的objectId获取用户
         * @param objectId
         * @return
         */
        @Headers({
                "X-Bmob-Application-Id:" + Constants.APP_ID,
                "X-Bmob-REST-API-Key:" + Constants.REST_API_KEY,
                "Content-Type: application/json"
        })
        @GET(Constants.UserUrlManager.GET_USER_BY_OBJECTID_URL)
        Call<User> getUserByObjectId(@Path("objectID") String objectId);

        /**
         * 根据用户的objectId更新用户信息
         * @param objectId
         * @param user 创建一个user对象用来跟新以前的user
         * @param sessionToken 要跟新的用户的sessionToken
         * @return
         */
        @Headers({
                "X-Bmob-Application-Id:" + Constants.APP_ID,
                "X-Bmob-REST-API-Key:" + Constants.REST_API_KEY,
                "Content-Type: application/json"
        })
        @PUT(Constants.UserUrlManager.UPDATE_USER_BY_OBJECTID_URL)
        Call<JsonObject> updateUserByObjectId(@Path("objectId") String objectId,
                                        @Body User user,
                                        @Header("X-Bmob-Session-Token") String sessionToken);

        /**
         * 通过用户的ObjectId删除用户
         * @param objectId
         * @param sessionToken
         * @return
         */
        @Headers({
                "X-Bmob-Application-Id:" + Constants.APP_ID,
                "X-Bmob-REST-API-Key:" + Constants.REST_API_KEY,
                "Content-Type: application/json"
        })
        @DELETE(Constants.UserUrlManager.DELETE_USER_BY_OBJECTiD_URL)
        Call<JsonObject> deleteUserByObjectId(@Path("objectId") String objectId,
                                              @Header("X-Bmob-Session-Token") String sessionToken);

        /**
         * 获取_User表中的所有用户
         * @return
         */
        @Headers({
                "X-Bmob-Application-Id:" + Constants.APP_ID,
                "X-Bmob-REST-API-Key:" + Constants.REST_API_KEY,
        })
        @GET(Constants.UserUrlManager.GET_ALL_USER_URL)
        Call<List<User>> getAllUser();

        /**
         * 用户密码重置,通过旧密码修改方式
         * @param objectId
         * @param requestBody
         * @param sessionToken
         * @return
         */
        @Headers({
                "X-Bmob-Application-Id:" + Constants.APP_ID,
                "X-Bmob-REST-API-Key:" + Constants.REST_API_KEY,
                "Content-Type: application/json"
        })
        @PUT(Constants.UserUrlManager.UPDATE_PASSWORD_URL)
        Call<JsonObject> updateUserPassword(@Path("objectId") String objectId,
                                            @Body UpdatePasswordRequestBean requestBody,
                                            @Header("X-Bmob-Session-Token") String sessionToken);
    }

    private void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BMOB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userServer = retrofit.create(UserServer.class);
    }
}
