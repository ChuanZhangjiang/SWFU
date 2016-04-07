package me.zjc.swfu.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import me.zjc.swfu.tableBean.User;
import me.zjc.swfu.model.IUserModel;
import me.zjc.swfu.model.impl.UserModel;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.util.MD5Util;
import me.zjc.swfu.view.ILoginView;
import me.zjc.swfu.widget.activity.MainActivity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ChuanZhangjiang on 2016/1/12.
 */
public class LoginPresenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private IUserModel mUserModel;
    private ILoginView mLoginView;
    private Context mContext;

    public LoginPresenter(ILoginView loginView) {
        this.mUserModel = new UserModel();
        this.mLoginView = loginView;
        this.mContext = mLoginView.getActivityContext();
    }

    public void login() {
        mLoginView.startProgress();
        String studentId = mLoginView.getStudentId();
        String studentPass = mLoginView.getPassword();
        mUserModel.login(studentId, MD5Util.getMD5Str(studentPass))
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .timeout(60, TimeUnit.SECONDS)
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        mLoginView.stopProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoginView.stopProgress();
                        LogUtil.e(TAG, "登陆失败： " + e.getMessage());
                        Toast.makeText(mContext, "登录失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(User user) {

                        mUserModel.saveCurrentUser(user, mContext);

                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mContext.startActivity(intent);
                        Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
