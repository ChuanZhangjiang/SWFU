package me.zjc.swfu.netWork.server;

import me.zjc.swfu.common.Constants;
import me.zjc.swfu.netWork.response.NotifyDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ChuanZhangjiang on 2016/4/17.
 */
public interface PublicNotifyServer {

    /**
     * 查询公告明细
     * @param notifyId
     * @return
     */
    @GET(Constants.SECRET_KEY+"/queryNotifyDetail")
    Call<NotifyDetail> queryNotifyDetail(@Query("notifyId") String notifyId);

}
