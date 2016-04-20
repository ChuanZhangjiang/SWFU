package me.zjc.swfu.admin.widget.activity;

import me.zjc.swfu.R;
import me.zjc.swfu.base.BaseActivity;

/**
 * Created by ChuanZhangjiang on 2016/4/19.
 */
public class MainAdminActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.layout_container;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void findView() {

    }

    @Override
    protected void initView() {
        setTitle(R.string.draw_item_home);
    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onBackPressed() {
        exitSystem();
    }
}
