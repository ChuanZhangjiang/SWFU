package me.zjc.swfu.widget.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import me.zjc.swfu.R;
import me.zjc.swfu.base.BaseActivity;
import me.zjc.swfu.bean.UpdatePasswordRequestBean;
import me.zjc.swfu.bean.User;
import me.zjc.swfu.common.Constants;
import me.zjc.swfu.presenter.MyCenterPresenter;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.util.MD5Util;
import me.zjc.swfu.view.IMyCenterView;

/**
 * Created by ChuanZhangjiang on 2016/2/25.
 */
public class MyCenterActivity extends BaseActivity implements IMyCenterView {

    public static final String TAG = MyCenterPresenter.class.getSimpleName();

    private ViewHolder mViewHolder = null;
    private UpdatePassDialogViewHolder mUpdatePassViewHolder = null;
    private MyCenterPresenter mPresenter = null;

    private Boolean isModifyMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mycenter_main;
    }

    @Override
    protected void findView() {
        mViewHolder.mIvCardHeaderBackground = (ImageView) findViewById(R.id.iv_background);
        mViewHolder.mIvHeader = (CircleImageView) findViewById(R.id.iv_header);
        mViewHolder.mTvStudentId = (TextView) findViewById(R.id.tv_studentId);
        mViewHolder.mTvName = (TextView) findViewById(R.id.tv_name);
        mViewHolder.mTvAge = (TextView) findViewById(R.id.tv_age);
        mViewHolder.mTvLevel = (TextView) findViewById(R.id.tv_level);
        mViewHolder.mTvProfessional = (TextView) findViewById(R.id.tv_professional);
        mViewHolder.mTvSex = (TextView) findViewById(R.id.tv_sex);
        mViewHolder.mEtDec = (EditText) findViewById(R.id.et_dec);
        mViewHolder.mBtnResetPassword = (Button) findViewById(R.id.btn_resetPassword);
        mViewHolder.mBtnModifyInfo = (Button) findViewById(R.id.btn_modify_information);
    }

    @Override
    protected void setEvent() {
        mViewHolder.mBtnModifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isModifyMode) {
                    //提交更改
                    mPresenter.updateUserInfo();
                } else {
                    //进入编辑模式
                    modifyMode();
                }
            }
        });

        mViewHolder.mBtnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdatePasswordDialog();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new MyCenterPresenter(this);
        mViewHolder = new ViewHolder();
    }

    @Override
    protected void initView() {
        setTitle(getResString(R.string.myCenter_title));
        mPresenter.setCurrentUserView();
    }

    @Override
    public User getCurrentViewUser() {
        User user = new User();
        user.setDec(mViewHolder.mEtDec.getText().toString());
        return user;
    }

    @Override
    public void setCurrentViewUser(User user) {
        if (isModifyMode) {
            cancelModifyMode();
        }
        Picasso.with(this)
                .load(user.getHeader_url())
                .error(R.mipmap.header_default)
                .into(mViewHolder.mIvHeader);
        mViewHolder.mTvStudentId
                .setText(getResString(R.string.myCenter_studentId) + user.getUsername());
        mViewHolder.mTvName
                .setText(getResString(R.string.myCenter_name) + user.getName());
        if (user.getSex() == Constants.MALE) {
            mViewHolder.mTvSex .setText(getResString(R.string.myCenter_sex) + "男");
        } else {
            mViewHolder.mTvSex .setText(getResString(R.string.myCenter_sex) + "女");
        }
        mViewHolder.mTvAge.setText(getResString(R.string.myCenter_age) + user.getAge());
        mViewHolder.mTvProfessional
                .setText(getResString(R.string.myCenter_professional) + user.getProfessional());
        mViewHolder.mTvLevel.setText(getResString(R.string.myCenter_level) + user.getLevel());
        mViewHolder.mEtDec.setText(user.getDec());
    }

    @Override
    public UpdatePasswordRequestBean getNewAndOldPassword() {
        if (mUpdatePassViewHolder == null) {
            LogUtil.e(TAG, "return null updatePasswordRequestBean !");
            return null;
        }
        UpdatePasswordRequestBean requestBody = new UpdatePasswordRequestBean();
        String newPass = mUpdatePassViewHolder.mEtNewPassword.getText().toString();
        String secretNewPass = MD5Util.getMD5Str(newPass);
        String oldPass = mUpdatePassViewHolder.mEtOldPassword.getText().toString();
        String secretOldPass = MD5Util.getMD5Str(oldPass);
        requestBody.setNewPassword(secretNewPass);
        requestBody.setOldPassword(secretOldPass);
        mUpdatePassViewHolder = null;
        return requestBody;
    }

    @Override
    public void goLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

    @Override
    public void showAtyProgress() {
        showProgressDialog(getResString(R.string.loading));
    }

    @Override
    public void dismissAtyProgress() {
        dismissProgressDialog();
    }

    @Override
    public void showAtyToast(String content) {
        showToast(content);
    }

    @Override
    public void showAtyToast(String content, int duration) {
        showToast(content, duration);
    }

    /**
     * 进入编辑模式
     */
    private void modifyMode() {
        mViewHolder.mBtnModifyInfo.setText(getResString(R.string.OK));
        mViewHolder.mEtDec.setEnabled(true);
        isModifyMode = true;
    }

    /**
     * 取消编辑模式
     */
    private void cancelModifyMode() {
        mViewHolder.mBtnModifyInfo.setText(getResString(R.string.myCenter_modify_information));
        mViewHolder.mEtDec.setEnabled(false);
        mPresenter.setCurrentUserView();
        isModifyMode = false;
    }

    private void showUpdatePasswordDialog() {
        mUpdatePassViewHolder = new UpdatePassDialogViewHolder();

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_update_pass, null);

        mUpdatePassViewHolder.mEtOldPassword =
                (EditText) view.findViewById(R.id.et_old_pass);
        mUpdatePassViewHolder.mEtNewPassword =
                (EditText) view.findViewById(R.id.et_new_pass);
        mUpdatePassViewHolder.mEtNewPasswordReInput =
                (EditText) view.findViewById(R.id.et_new_pass_reInput);

        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle(getResString(R.string.myCenter_updatePass_dialog_title))
                .setView(view)
                .setPositiveButton(getResString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldPass = mUpdatePassViewHolder.mEtOldPassword.getText().toString();
                        String newPass = mUpdatePassViewHolder.mEtNewPassword.getText().toString();
                        String newPassReInput =
                                mUpdatePassViewHolder.mEtNewPasswordReInput.getText().toString();
                        if ("".equals(oldPass)) {
                            showToast(getResString(R.string.myCenter_toast_old_pass_isNull));
                            return;
                        }

                        if ("".equals(newPass)) {
                            showToast(getResString(R.string.myCenter_toast_new_pass_isNull));
                            return;
                        }

                        if ("".equals(newPassReInput)) {
                            showToast(getResString(R.string.myCenter_toast_new_pass_reInput_isNull));
                            return;
                        }

                        if (!newPass.equals(newPassReInput)) {
                            showAtyToast(getResString(R.string.myCenter_toast_pass_check_noPass));
                            mUpdatePassViewHolder.mEtNewPassword.setText("");
                            mUpdatePassViewHolder.mEtNewPasswordReInput.setText("");
                            return;
                        }

                        mPresenter.updatePassword();
                    }
                })
                .setNegativeButton(getResString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mUpdatePassViewHolder = null;
                    }
                })
                .create();

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (isModifyMode) {
            cancelModifyMode();
        } else {
            setResult(RESULT_OK);
            super.onBackPressed();
        }
    }

    private class ViewHolder{
        private CardView mCardHeader;
        private ImageView mIvCardHeaderBackground;
        private ImageView mIvHeader;
        private TextView mTvStudentId;//格式 "学号：xxxxxxxx"
        private TextView mTvName;//格式"姓名：xxxxxx"
        private TextView mTvSex;//格式"性别：x"
        private TextView mTvAge;//格式"年龄：xx"
        private TextView mTvProfessional;//格式"专业：xxxxxx"
        private TextView mTvLevel;//格式"年级：20xx"
        private EditText mEtDec; //个人简介
        private Button mBtnResetPassword; //重置密码按钮
        private Button mBtnModifyInfo; //编辑个人信息按钮,点击之后进入编辑模式，然后将“编辑个人信息”改为“完成编辑”
        //点返回推出编辑
    }

    private class UpdatePassDialogViewHolder {
        private EditText mEtOldPassword;
        private EditText mEtNewPassword;
        private EditText mEtNewPasswordReInput;
    }
}
