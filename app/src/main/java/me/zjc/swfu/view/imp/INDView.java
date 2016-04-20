package me.zjc.swfu.view.imp;

import android.content.Context;

import me.zjc.swfu.netWork.response.NotifyDetail;

/**
 * Created by ChuanZhangjiang on 2016/4/18.
 */
public interface INDView {

    Context getCurrentContext();

    void showProgressBar();

    void dismissProgressBar();

    void setContent(NotifyDetail data);

    void showContent();

    void dismissContent();

    void onGetDataError();
}
