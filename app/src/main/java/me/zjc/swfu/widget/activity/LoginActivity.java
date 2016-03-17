package me.zjc.swfu.widget.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import me.zjc.swfu.R;
import me.zjc.swfu.base.BaseActivity;
import me.zjc.swfu.presenter.LoginPresenter;
import me.zjc.swfu.view.ILoginView;

/**
 * Created by ChuanZhangjiang on 2016/1/12.
 */
public class LoginActivity extends BaseActivity implements ILoginView {

    private EditText mEtStudentId;
    private EditText mEtStudentPass;
    private Button mBtnLogin;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(LoginActivity.this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void findView() {
        mEtStudentId = (EditText) findViewById(R.id.et_studentId);
        mEtStudentPass = (EditText) findViewById(R.id.et_studentPass);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
    }

    @Override
    protected void setEvent() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeInput(v);
                //判断EditText中的数据
                //执行登录
                if (TextUtils.isEmpty(mEtStudentId.getText().toString())) {
                    showToast("请输入学号");
                    return;
                }
                if (TextUtils.isEmpty(mEtStudentPass.getText().toString())){
                    showToast("请输入密码");
                    return;
                }
                presenter.login();
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
    }


    @Override
    public String getStudentId() {
        String studentId = mEtStudentId.getText().toString();
        return studentId;
    }

    @Override
    public String getPassword() {
        String password = mEtStudentPass.getText().toString();
        return password;
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        exitSystem();
    }

    @Override
    public void startProgress() {
        showProgressDialog();
    }

    @Override
    public void stopProgress() {
        dismissProgressDialog();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }
}
