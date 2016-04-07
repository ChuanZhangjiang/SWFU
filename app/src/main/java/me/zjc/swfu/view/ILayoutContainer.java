package me.zjc.swfu.view;

import android.content.Context;
import android.view.View;

/**
 * Created by ChuanZhangjiang on 2016/3/26.
 */
public interface ILayoutContainer {

    public void inti(Context context);

    public View getView();

    public Boolean onBackPress();

}
