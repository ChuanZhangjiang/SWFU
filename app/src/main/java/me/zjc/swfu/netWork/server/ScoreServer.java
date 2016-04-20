package me.zjc.swfu.netWork.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import me.zjc.swfu.common.Constants;
import me.zjc.swfu.netWork.response.QueryScoreResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ChuanZhangjiang on 2016/4/13.
 */
public interface ScoreServer {

    /**
     * 查成绩
     * @param studentObjectId
     * @return
     */
    @GET(Constants.SECRET_KEY+"/queryMyScore")
    Call<List<QueryScoreResponse>> queryScore(@Query("studentObjectId") String studentObjectId);

}
