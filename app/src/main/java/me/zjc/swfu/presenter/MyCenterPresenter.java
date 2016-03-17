package me.zjc.swfu.presenter;

import android.content.Context;

import com.google.gson.JsonObject;

import me.zjc.swfu.MyApplication;
import me.zjc.swfu.bean.UpdatePasswordRequestBean;
import me.zjc.swfu.bean.User;
import me.zjc.swfu.model.IUserModel;
import me.zjc.swfu.model.impl.UserModel;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.view.IMyCenterView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ChuanZhangjiang on 2016/3/8.
 */
public class MyCenterPresenter {

    public static final String TAG = MyCenterPresenter.class.getSimpleName();

    private IUserModel mUserModel;
    private IMyCenterView mMyCenterView;
    private Context mContext;

    public MyCenterPresenter(IMyCenterView myCenterView) {
        this.mUserModel = new UserModel();
        this.mMyCenterView = myCenterView;
        this.mContext = mMyCenterView.getCurrentActivity();
    }

    /**
     * 设置当前用户的视图
     */
    public void setCurrentUserView() {
        mMyCenterView.showAtyProgress();
        Observable<User> user = mUserModel.getCurrentUser(mContext);
        user.onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        mMyCenterView.dismissAtyProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMyCenterView.dismissAtyProgress();
                        mMyCenterView.showAtyToast("获取当前用户失败，请稍后在试");
                        LogUtil.e(TAG, "获取当前用户失败：" + e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        LogUtil.e(TAG, "getUserByObjectId: " + user.toString());
                        mMyCenterView.setCurrentViewUser(user);
                        mUserModel.saveCurrentUser(user, mContext);
                    }
                });
    }

    public void updateUserInfo() {

        if (!MyApplication.getInstance().isNetConnection()) {
            mMyCenterView.showAtyToast("没有网络啊，太痛苦了！");
            return;
        }

        mMyCenterView.showAtyProgress();
        mUserModel.getCurrentUser(mContext)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<User, Observable<JsonObject>>() {
                    @Override
                    public Observable<JsonObject> call(User user) {
                        User currentViewUser = mMyCenterView.getCurrentViewUser();
                        return mUserModel.updateUserByObjectId(user.getObjectId()
                                , currentViewUser
                                , user.getSessionToken());
                    }
                })
                .flatMap(new Func1<JsonObject, Observable<User>>() {
                    @Override
                    public Observable<User> call(JsonObject jsonObject) {
                        return mUserModel.getCurrentUser(mContext);
                    }
                })
                .flatMap(new Func1<User, Observable<User>>() {
                    @Override
                    public Observable<User> call(User user) {
                        return mUserModel.getUserByObjectId(user.getObjectId());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        mMyCenterView.dismissAtyProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMyCenterView.dismissAtyProgress();
                        mMyCenterView.showAtyToast("更新失败，请重试");
                        LogUtil.e(TAG, "更新用户失败：" + e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        mMyCenterView.showAtyToast("恭喜你！修改成功");
                        mUserModel.saveCurrentUser(user, mContext);
                        setCurrentUserView();
                    }
                });


    }

    public void updatePassword() {

        if (!MyApplication.getInstance().isNetConnection()) {
            mMyCenterView.showAtyToast("没有网络啊，太痛苦了！");
            return;
        }

        mMyCenterView.showAtyProgress();
        mUserModel.getCurrentUser(mContext)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<User, Observable<JsonObject>>() {
                    @Override
                    public Observable<JsonObject> call(User user) {
                        UpdatePasswordRequestBean requestBody = mMyCenterView.getNewAndOldPassword();
                        return mUserModel.updateUserPassword(user.getObjectId()
                                , requestBody
                                , user.getSessionToken());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JsonObject>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMyCenterView.showAtyToast("修改密码失败：" + e.getMessage());
                        mMyCenterView.dismissAtyProgress();
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        mMyCenterView.dismissAtyProgress();
                        mMyCenterView.showAtyToast("修改成功");
                        mMyCenterView.goLoginActivity();
                    }
                });
    }

}
