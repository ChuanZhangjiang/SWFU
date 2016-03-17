package me.zjc.swfu.view;

import android.content.Context;

/**
 * Created by ChuanZhangjiang on 2016/1/17.
 */
public interface IMainView {
    public Context getActivityContext();
    public void showProgressbar();
    public void dismissProgressbar();
    public void initNavView(String headerUrl, String name, String dec);
}
