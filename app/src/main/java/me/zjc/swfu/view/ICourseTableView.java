package me.zjc.swfu.view;

import android.content.Context;

import java.util.Map;

import me.zjc.swfu.adapter.CTAdapter;

/**
 * Created by ChuanZhangjiang on 2016/4/7.
 */
public interface ICourseTableView {

    Context getActivityContext();

    void showProgress(String hint);

    void showFragmentToast(String hint);

    void setCourseTable(Map<Integer, CTAdapter> courseTable);

}
