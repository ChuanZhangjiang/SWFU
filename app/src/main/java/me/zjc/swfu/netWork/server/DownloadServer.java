package me.zjc.swfu.netWork.server;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ChuanZhangjiang on 2016/4/18.
 */
public interface DownloadServer {

    @GET("{endUrl}")
    Call<ResponseBody> downloadFile(@Path("endUrl") String endUrl);

}
