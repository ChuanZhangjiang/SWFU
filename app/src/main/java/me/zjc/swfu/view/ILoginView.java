package me.zjc.swfu.view;

import android.content.Context;

import me.zjc.swfu.common.Constants;

/**
 * Created by ChuanZhangjiang on 2016/1/12.
 */
public interface ILoginView {
    public String getStudentId();
    public String getPassword();
    public void startProgress();
    public void stopProgress();
    public Context getActivityContext();
}
