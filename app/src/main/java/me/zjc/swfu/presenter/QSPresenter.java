package me.zjc.swfu.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import me.zjc.swfu.model.IScoreModel;
import me.zjc.swfu.model.IUserModel;
import me.zjc.swfu.model.impl.ScoreModel;
import me.zjc.swfu.model.impl.UserModel;
import me.zjc.swfu.netWork.response.QueryScoreResponse;
import me.zjc.swfu.tableBean.User;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.view.IQSView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ChuanZhangjiang on 2016/4/16.
 */
public class QSPresenter {

    public static final String TAG = QSPresenter.class.getSimpleName();

    private IScoreModel mScoreModel = null;
    private IUserModel mUserModel = null;
    private IQSView mQSView = null;
    private Context mContext = null;

    public QSPresenter(IQSView qsView) {
        this.mScoreModel = new ScoreModel();
        this.mUserModel = new UserModel();
        this.mQSView = qsView;
        this.mContext = mQSView.getCurrentActivity();
    }

    public void refresh(final SwipeRefreshLayout swipeRefreshLayout){
        swipeRefreshLayout.setRefreshing(true);
        mUserModel.getCurrentUser(mContext)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<User, Observable<List<QueryScoreResponse>>>() {
                    @Override
                    public Observable<List<QueryScoreResponse>> call(User user) {
                        return mScoreModel.queryMyScore(user.getObjectId());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<QueryScoreResponse>>() {
                    @Override
                    public void onCompleted() {
                        swipeRefreshLayout.setRefreshing(false);
                        LogUtil.e(TAG, "get my score completed!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, e.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<QueryScoreResponse> data) {
                        LogUtil.e(TAG, "score length: " + data.size());
                        mQSView.showScore(data);
                    }
                });


    }

}
