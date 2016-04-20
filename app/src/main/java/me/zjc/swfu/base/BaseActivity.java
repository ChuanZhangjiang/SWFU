package me.zjc.swfu.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import me.zjc.swfu.MyApplication;
import me.zjc.swfu.R;
import me.zjc.swfu.widget.activity.BeforeLoginActivity;

/**
 * Created by ChuanZhangjiang on 2016/1/9.
 * activity的基类
 * 布局文件中必须包含toolbar这个布局文件
 * 不然报找不到toolbar的错误
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    protected Toolbar toolbar = null;
    protected ProgressDialog progressDialog = null;
    protected Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        MyApplication.getInstance().addActivity(this);
        if (MyApplication.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initData();
        findView();
        initView();
        setEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    /**
     * 获取布局文件的的ID，
     * 如果要传入View，无视这个方法直接掉用setContentView（View view）方法
     * 注意，如果调用setContentView（View view）方法，findView（）和setEvent（）将无效
     * @return 布局文件的ID
     */
    protected abstract int getLayoutId();

    /**
     *
     * 用于初始化数据，比如获取从上一个activity传递过来的数据
     * 初始化一些全局变量等
     * 请求网络数据
     */
    protected abstract void initData();

    /**
     * 绑定控件
     */
    protected abstract void findView();

    /**
     * 用于将初始化之后的数据填充到View中
     */
    protected abstract void initView();

    /**
     * 用于设置事件的监听，比如，按钮点击、View的触摸等
     */
    protected abstract void setEvent();



    protected void showProgressDialog(String hint) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        if (!TextUtils.isEmpty(hint)) {
            progressDialog.setTitle(hint);
        }
        progressDialog.show();
    }

    protected void showProgressDialog() {
        showProgressDialog("");
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void showToast(String hint, int duration) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, hint, duration);
        toast.show();
    }

    protected void showToast(String hint) {
        showToast(hint, Toast.LENGTH_SHORT);
    }

    protected String getResString(int resId) {
        String str = getResources().getString(resId);
        return str;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
//            finishAfterTransition();
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 关闭输入法
     * @param view 输入法对焦的视图
     */
    protected void closeInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(BaseActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void exitSystem() {
        Intent intent = new Intent(this, BeforeLoginActivity.class);
//        intent.putExtra(Constants.CLOSE_FLAG, Constants.CLOSE_FLAG_CLOSE);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
