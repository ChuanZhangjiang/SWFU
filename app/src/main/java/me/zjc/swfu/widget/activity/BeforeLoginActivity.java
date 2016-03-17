package me.zjc.swfu.widget.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import me.zjc.swfu.MyApplication;
import me.zjc.swfu.R;
import me.zjc.swfu.bean.User;
import me.zjc.swfu.model.IUserModel;
import me.zjc.swfu.model.impl.UserModel;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ChuanZhangjiang on 2016/1/11.
 */
public class BeforeLoginActivity extends Activity {
    private static final String TAG = BeforeLoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_login);
        MyApplication.getInstance().addActivity(this);

        IUserModel userModel = new UserModel();

        userModel.getCurrentUser(this)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        //进入登录界面
                        Intent i = new Intent(BeforeLoginActivity.this, LoginActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onNext(User user) {
                        //直接进入MainActivity
                        Intent i = new Intent(BeforeLoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
