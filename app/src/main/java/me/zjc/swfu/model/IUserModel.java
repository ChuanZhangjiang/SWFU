package me.zjc.swfu.model;

import android.content.Context;

import com.google.gson.JsonObject;

import java.util.List;

import me.zjc.swfu.netWork.requestBody.UpdatePasswordRequestBean;
import me.zjc.swfu.tableBean.User;
import rx.Observable;

/**
 * Created by ChuanZhangjiang on 2016/3/8.
 */
public interface IUserModel {

    Observable<User> register(User user);

    Observable<User> login(String userName, String password);

    Observable<User> getUserByObjectId(String objectId);

    Observable<JsonObject> updateUserByObjectId(String objectId, User user, String sessionToken);

    Observable<JsonObject> deleteUserByObjectId(String objectId, String sessionToken);

    Observable<List<User>> getAllUser();

    Observable<JsonObject> updateUserPassword(String objectId
            , UpdatePasswordRequestBean requestBody, String sessionToken);

    Observable<User> getCurrentUser(Context context);

    void saveCurrentUser(User user, Context context);

    void clearCurrentUser(Context context);

}
