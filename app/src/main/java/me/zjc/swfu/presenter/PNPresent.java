package me.zjc.swfu.presenter;

import java.util.List;

import me.zjc.swfu.model.IPublicNotifyModel;
import me.zjc.swfu.model.impl.PublicNotifyModel;
import me.zjc.swfu.tableBean.PublicNotify;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.view.IPublicNotifyView;
import rx.Subscriber;

/**
 * Created by ChuanZhangjiang on 2016/4/4.
 */
public class PNPresent {

    public static final String TAG = PNPresent.class.getSimpleName();

    private IPublicNotifyView mPNView = null;
    private IPublicNotifyModel mPNModel = null;


    public PNPresent(IPublicNotifyView pnView) {
        this.mPNView = pnView;
        this.mPNModel = new PublicNotifyModel();
    }

    public void refreshPNList() {
        mPNModel.queryAllPublicNotify(new Subscriber<List<PublicNotify>>() {
            @Override
            public void onCompleted() {
                LogUtil.e(TAG, "completed !");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG, e.getMessage());
            }

            @Override
            public void onNext(List<PublicNotify> publicNotifies) {
                mPNView.setPublicNotifyData(publicNotifies);
            }
        });
    }

}
