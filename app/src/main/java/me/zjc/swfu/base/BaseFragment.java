package me.zjc.swfu.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import me.zjc.swfu.MyApplication;

/**
 * Created by ChuanZhangjiang on 2016/1/17.
 */
public abstract class BaseFragment extends Fragment {

    private View rootView = null;
    private Toast toast = null;
    private ProgressDialog progressDialog = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getResLayoutId(), null);
        }
        findView(rootView);
        initData();
        initView();
        setEvent();
        return rootView;
    }

    protected void showToast(String msg, int duration) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(MyApplication.getInstance(), msg, duration);
        toast.show();
    }

    protected void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    protected void showProcessDialog(String hint) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(MyApplication.getInstance());
        }
        if (!TextUtils.isEmpty(hint)) {
            progressDialog.setTitle(hint);
        }
        progressDialog.show();
    }

    protected void showProcessDialog() {
        showProcessDialog("");
    }

    protected String getResString(int id) {
        String str = getResources().getString(id);
        return str;
    }

    /**
     * 关闭输入法
     * @param view 输入法对焦的布局，或该布局中的某个view
     */
    protected void closeInput(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(BaseActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected abstract int getResLayoutId ();
    protected abstract void findView(View rootView);
    protected abstract void initData();
    protected abstract void initView();
    protected abstract void setEvent();
}
