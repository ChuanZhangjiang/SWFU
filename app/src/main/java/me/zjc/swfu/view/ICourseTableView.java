package me.zjc.swfu.view;

import android.content.Context;

/**
 * Created by ChuanZhangjiang on 2016/4/7.
 */
public interface ICourseTableView {

    Context getActivityContext();

    void showProgress(String hint);

    void showFragmentToast(String hint);

}
