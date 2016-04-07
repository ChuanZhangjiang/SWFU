package me.zjc.swfu.presenter;

import android.content.Context;
import android.content.Intent;

import me.zjc.swfu.tableBean.User;
import me.zjc.swfu.model.IUserModel;
import me.zjc.swfu.model.impl.UserModel;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.view.IMainView;
import me.zjc.swfu.widget.activity.LoginActivity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ChuanZhangjiang on 2016/1/17.
 */
public class MainPresenter {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private Context mContext;
    private IUserModel mUserModel;
    private IMainView mMainView;
    public MainPresenter(IMainView mainView) {
        this.mContext = mainView.getActivityContext();
        this.mUserModel = new UserModel();
        this.mMainView = mainView;
    }

    public void logout () {
        mUserModel.clearCurrentUser(mContext);
        Intent i = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(i);
    }

    public void initNavView() {
        mUserModel.getCurrentUser(mContext)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "获取当前用户失败：" + e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        String headerUrl = user.getHeader_url();
                        String name = user.getName();
                        String dec = user.getDec();
                        mMainView.initNavView(headerUrl, name, dec);
                    }
                });
    }
}
