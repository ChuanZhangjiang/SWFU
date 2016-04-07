package me.zjc.swfu.model.impl;

import java.util.List;

import me.zjc.swfu.model.IPublicNotifyModel;
import me.zjc.swfu.netWork.client.PublicNotifyClient;
import me.zjc.swfu.tableBean.PublicNotify;
import rx.Subscriber;

/**
 * Created by ChuanZhangjiang on 2016/3/28.
 */
public class PublicNotifyModel implements IPublicNotifyModel {

    private PublicNotifyClient client;

    public PublicNotifyModel() {
        client = PublicNotifyClient.getInstance();
    }

    @Override
    public void queryAllPublicNotify(Subscriber<List<PublicNotify>> subscriber) {
        client.queryAllPublicNotify(subscriber);
    }
}
