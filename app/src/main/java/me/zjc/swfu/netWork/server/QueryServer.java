package me.zjc.swfu.netWork.server;

import com.google.gson.JsonObject;

import me.zjc.swfu.common.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ChuanZhangjiang on 2016/3/23.
 */
public interface QueryServer {

    /**
     * 根据条件查询对象
     * @param tableName 要查询的表
     * @param where 查询条件，参考（http://docs.bmob.cn/restful/developdoc/index.html?menukey=develop_doc&key=develop_restful#index_条件查询）
     * @return
     */
    @Headers({
            "X-Bmob-Application-Id:" + Constants.APP_ID,
            "X-Bmob-REST-API-Key:" + Constants.REST_API_KEY,
            "Content-Type: application/json"
    })
    @GET("/1/classes/{tableName}")
    Call<JsonObject> getObjectByCondition(@Path("tableName") String tableName
            , @Query("where") String where);


    /**
     * 查询数据表中的所有对象
     * @param tableName
     * @return
     */
    @Headers({
            "X-Bmob-Application-Id:" + Constants.APP_ID,
            "X-Bmob-REST-API-Key:" + Constants.REST_API_KEY,
            "Content-Type: application/json"
    })
    @GET("/1/classes/{TableName}")
    Call<JsonObject> getAllObject(@Path("TableName")String tableName);

}
