package me.zjc.swfu.presenter;

import android.content.Context;

import me.zjc.swfu.model.INDModel;
import me.zjc.swfu.model.NDModel;
import me.zjc.swfu.netWork.response.NotifyDetail;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.view.imp.INDView;
import me.zjc.swfu.widget.dialog.FileDialogMaker;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 公告详情的presenter全名NotifyDetailPresenter
 * Created by ChuanZhangjiang on 2016/4/18.
 */
public class NDPresent  {

    public static final String TAG = NDPresent.class.getSimpleName();

    private Context mContext = null;
    private INDView mNDView = null;
    private INDModel mNDModel = null;
    private String mNotifyId = null;


    public NDPresent (INDView NDView, String notifyId) {
        this.mNDView = NDView;
        this.mContext = mNDView.getCurrentContext();
        this.mNotifyId = notifyId;
        this.mNDModel = new NDModel();
    }

    public void initView() {
        mNDView.showProgressBar();
        mNDView.dismissContent();
        mNDModel.getNotifyDetail(mNotifyId)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NotifyDetail>() {
                    @Override
                    public void onCompleted() {
                        mNDView.dismissProgressBar();
                        mNDView.showContent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mNDView.dismissContent();
                        mNDView.dismissProgressBar();
                        mNDView.onGetDataError();
                        LogUtil.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(NotifyDetail notifyDetail) {
                        mNDView.setContent(notifyDetail);
                    }
                });
    }

}
