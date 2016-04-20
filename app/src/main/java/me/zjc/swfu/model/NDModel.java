package me.zjc.swfu.model;

import me.zjc.swfu.netWork.client.PublicNotifyClient;
import me.zjc.swfu.netWork.response.NotifyDetail;
import rx.Observable;

/**
 * Created by ChuanZhangjiang on 2016/4/18.
 */
public class NDModel implements INDModel {

    private PublicNotifyClient mClient = null;

    public NDModel () {
        mClient = PublicNotifyClient.getInstance();
    }

    @Override
    public Observable<NotifyDetail> getNotifyDetail(String notifyId) {
        return mClient.queryNotifyDetail(notifyId);
    }
}
