package me.zjc.swfu.model;


import java.util.List;

import me.zjc.swfu.tableBean.PublicNotify;
import rx.Subscriber;

/**
 * Created by ChuanZhangjiang on 2016/3/28.
 */
public interface IPublicNotifyModel {

    public void queryAllPublicNotify(Subscriber<List<PublicNotify>> subscriber);

}
