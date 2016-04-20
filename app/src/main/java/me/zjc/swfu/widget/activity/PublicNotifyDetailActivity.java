package me.zjc.swfu.widget.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.util.List;

import me.zjc.swfu.R;
import me.zjc.swfu.base.BaseActivity;
import me.zjc.swfu.netWork.response.BmobFile;
import me.zjc.swfu.netWork.response.NotifyDetail;
import me.zjc.swfu.presenter.NDPresent;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.view.imp.INDView;
import me.zjc.swfu.widget.dialog.FileDialogMaker;

public class PublicNotifyDetailActivity extends BaseActivity implements INDView {

    public static final String TAG = PublicNotifyDetailActivity.class.getSimpleName();

    private ViewHolder mViewHolder = null;
    private NDPresent mPresenter = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_public_notify_detail;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String notifyId = intent.getStringExtra("notifyId");
        if (notifyId == null) {
            LogUtil.e(TAG, "接收到的notifyId为空");
            return;
        }
        mPresenter = new NDPresent(this, notifyId);
    }

    @Override
    protected void findView() {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder();
            mViewHolder.mTvTitle = (TextView) findViewById(R.id.tv_notify_title);
            mViewHolder.mTvContent = (TextView) findViewById(R.id.tv_notify_content);
            mViewHolder.mFileLayout = findViewById(R.id.cv_file_layout);
            mViewHolder.mTvFileName = (TextView) findViewById(R.id.tv_file_name);
            mViewHolder.mMainContent = findViewById(R.id.content_main);
            mViewHolder.mProgressBar = findViewById(R.id.progressBar);
        }
    }

    @Override
    protected void initView() {
        if (mPresenter == null) {
            dismissContent();
            dismissProgressBar();
            return;
        }

        mPresenter.initView();
    }

    @Override
    protected void setEvent() {
        mViewHolder.mFileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BmobFile> fileList = (List<BmobFile>) v.getTag();
                FileDialogMaker maker = new FileDialogMaker(getCurrentContext());
                maker.create(fileList);
                maker.show();
            }
        });
    }

    @Override
    public Context getCurrentContext() {
        return this;
    }

    @Override
    public void showProgressBar() {
        mViewHolder.mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressBar() {
        mViewHolder.mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setContent(NotifyDetail data) {
        mViewHolder.mTvTitle.setText(data.getTitle());
        mViewHolder.mTvContent.setText(data.getContent());

        List<BmobFile> fileList = data.getFile();
        if (fileList == null || fileList.size() <= 0) {
            mViewHolder.mFileLayout.setVisibility(View.GONE);
        } else if (fileList.size() == 1) {
            BmobFile file = fileList.get(0);
            mViewHolder.mTvFileName.setText(file.getFilename());
            mViewHolder.mFileLayout.setVisibility(View.VISIBLE);
        } else {
            String fileName =
                    getResString(R.string.activity_notify_detail_file)
                            +fileList.size()
                            +getResString(R.string.ge);
            mViewHolder.mTvFileName.setText(fileName);
            mViewHolder.mFileLayout.setVisibility(View.VISIBLE);
        }
        mViewHolder.mFileLayout.setTag(fileList);
    }

    @Override
    public void showContent() {
        mViewHolder.mMainContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissContent() {
        mViewHolder.mMainContent.setVisibility(View.GONE);
    }

    @Override
    public void onGetDataError() {
        showToast(getResString(R.string.getDataFail));
    }

    private class ViewHolder{
        private TextView mTvTitle;
        private TextView mTvContent;
        private TextView mTvFileName;
        private View mFileLayout;
        private View mMainContent;
        private View mProgressBar;
    }
}
