package me.zjc.swfu.view;

import android.app.Activity;

import me.zjc.swfu.netWork.requestBody.UpdatePasswordRequestBean;
import me.zjc.swfu.tableBean.User;

/**
 * Created by ChuanZhangjiang on 2016/3/8.
 */
public interface IMyCenterView {
    /**
     * 获取被修改之后的用户信息
     * @return
     */
    User getCurrentViewUser();

    /**
     * 从后台获取到当前用户之后将当前用户的信息填充到view中
     * 注意：请在方法开头调用MyCenterActivity的cancelModifyMode方法
     * @param user
     */
    void setCurrentViewUser(User user);

    /**
     * 从界面中获取到旧密码和新密码
     * @return
     */
    UpdatePasswordRequestBean getNewAndOldPassword();

    /**
     * 修改密码成功后退回登陆界面
     */
    void goLoginActivity();

    Activity getCurrentActivity();
    void showAtyProgress();
    void dismissAtyProgress();
    void showAtyToast(String content);
    void showAtyToast(String content, int duration);
}
