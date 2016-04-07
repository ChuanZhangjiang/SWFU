package me.zjc.swfu.model.impl;

import android.content.Context;

import com.google.gson.JsonObject;

import java.util.List;

import me.zjc.swfu.netWork.requestBody.UpdatePasswordRequestBean;
import me.zjc.swfu.tableBean.User;
import me.zjc.swfu.model.IUserModel;
import me.zjc.swfu.util.UserManager;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by ChuanZhangjiang on 2016/3/8.
 */
public class UserModel implements IUserModel {

    private UserManager manager;

    public UserModel() {
        manager = new UserManager();
    }

    @Override
    public Observable<User> register(User user) {
        return manager.register(user);
    }

    @Override
    public Observable<User> login(String userName, String password) {
        return manager.login(userName, password);
    }

    @Override
    public Observable<User> getUserByObjectId(String objectId) {
        return manager.getUserByObjectId(objectId);
    }

    @Override
    public Observable<JsonObject> updateUserByObjectId(String objectId, User user, String sessionToken) {
        return manager.updateUserByObjectId(objectId, user, sessionToken);
    }

    @Override
    public Observable<JsonObject> deleteUserByObjectId(String objectId, String sessionToken) {
        return manager.deleteUserByObjectId(objectId, sessionToken);
    }

    @Override
    public Observable<List<User>> getAllUser() {
        return manager.getAllUser();
    }

    @Override
    public Observable<JsonObject> updateUserPassword(String objectId, UpdatePasswordRequestBean requestBody, String sessionToken) {
        return manager.updateUserPassword(objectId, requestBody, sessionToken);
    }

    @Override
    public Observable<User> getCurrentUser(Context context) {
        return manager.getCurrentUser(context);
    }

    @Override
    public void saveCurrentUser(final User user, Context context) {
        Observable<User> userObservable = Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                if ("".equals(user.getObjectId())){
                    subscriber.onError(new Throwable("objectId is null"));
                } else {
                    subscriber.onNext(user);
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
        manager.saveCurrentUser(userObservable, context);
    }

    @Override
    public void clearCurrentUser(Context context) {
        manager.clearCurrentUser(context);
    }

}
