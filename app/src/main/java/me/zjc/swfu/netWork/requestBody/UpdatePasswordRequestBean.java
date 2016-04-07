package me.zjc.swfu.netWork.requestBody;

/**
 * Created by ChuanZhangjiang on 2016/2/14.
 */
public class UpdatePasswordRequestBean {
    private String oldPassword;
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
