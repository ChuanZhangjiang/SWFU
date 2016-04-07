package me.zjc.swfu.netWork.server;


import com.google.gson.JsonObject;

import me.zjc.swfu.common.Constants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ChuanZhangjiang on 2016/3/23.
 */
public interface ArrayServer {

    /**
     * 向一个Array中添加元素
     * @param tableName array所在的表
     * @param objectId 要被添加元素的array对应对象的objectId
     * @param body 对应一个Json对象格式参考（http://docs.bmob.cn/restful/developdoc/index.html?menukey=develop_doc&key=develop_restful#index_更新数组数据）
     * @return
     */
    @Headers({
            "X-Bmob-Application-Id:" + Constants.APP_ID,
            "X-Bmob-REST-API-Key:" + Constants.REST_API_KEY,
            "Content-Type: application/json"
    })
    @PUT("/1/classes/{tableName}/{objectId}")
    Call<JsonObject> appendElement(@Path("tableName") String tableName
            , @Path("objectId") String objectId
            , @Body Object body);

    /**
     * 删除数组中的元素
     * @param tableName 同上
     * @param objectId 同上
     * @param body 参考（http://docs.bmob.cn/restful/developdoc/index.html?menukey=develop_doc&key=develop_restful#index_删除数组数据）
     * @return
     */
    @Headers({
            "X-Bmob-Application-Id:" + Constants.APP_ID,
            "X-Bmob-REST-API-Key:" + Constants.REST_API_KEY,
            "Content-Type: application/json"
    })
    @PUT("/1/classes/{TableName}/{objectId}")
    Call<JsonObject> deleteElement(@Path("TableName") String tableName
            , @Path("objectId") String objectId, @Body Object body);
}
