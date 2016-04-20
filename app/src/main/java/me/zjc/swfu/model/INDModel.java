package me.zjc.swfu.model;

import me.zjc.swfu.netWork.response.NotifyDetail;
import rx.Observable;

/**
 * Created by ChuanZhangjiang on 2016/4/18.
 */
public interface INDModel {

    Observable<NotifyDetail> getNotifyDetail(String notifyId);

}
