package me.zjc.swfu.view.imp;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;

import java.sql.ResultSet;

import me.zjc.swfu.R;
import me.zjc.swfu.view.ILayoutContainer;

/**
 * Created by ChuanZhangjiang on 2016/3/26.
 */
public class LayoutContainer extends CoordinatorLayout implements ILayoutContainer {

    private View mView;

    public LayoutContainer(Context context) {
        super(context);
    }


    @Override
    public void inti(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mView = inflater.inflate(R.layout.layout_container, null);
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public Boolean onBackPress() {
        return null;
    }
}
