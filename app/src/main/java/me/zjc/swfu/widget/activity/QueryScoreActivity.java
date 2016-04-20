package me.zjc.swfu.widget.activity;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import me.zjc.swfu.R;
import me.zjc.swfu.adapter.QSAdapter;
import me.zjc.swfu.base.BaseActivity;
import me.zjc.swfu.common.Constants;
import me.zjc.swfu.netWork.response.QueryScoreResponse;
import me.zjc.swfu.netWork.server.ScoreServer;
import me.zjc.swfu.netWork.server.ServerFactory;
import me.zjc.swfu.presenter.QSPresenter;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.view.IQSView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ChuanZhangjiang on 2016/4/13.
 */
public class QueryScoreActivity extends BaseActivity implements IQSView {

    public static final String TAG = QueryScoreActivity.class.getSimpleName();

    private ViewHolder mViewHolder = null;
    private QSPresenter mPresenter = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_query_score;
    }

    @Override
    protected void initData() {
        if (mPresenter == null) {
            mPresenter = new QSPresenter(this);
        }
    }

    @Override
    protected void findView() {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder();
            mViewHolder.mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
            mViewHolder.mRlvScore = (RecyclerView) findViewById(R.id.rlv_score);
        }
    }

    @Override
    protected void initView() {
        mPresenter.refresh(mViewHolder.mSwipe);
    }

    @Override
    protected void setEvent() {
        mViewHolder.mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh(mViewHolder.mSwipe);
            }
        });
    }

    @Override
    public void showScore(List<QueryScoreResponse> data) {
        QSAdapter adapter = new QSAdapter(this, data);
        mViewHolder.mRlvScore.setAdapter(adapter);
        mViewHolder.mRlvScore.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public Context getCurrentActivity() {
        return this;
    }

    private class ViewHolder{
        private SwipeRefreshLayout mSwipe;
        private RecyclerView mRlvScore;
    }
}
