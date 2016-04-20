package me.zjc.swfu.netWork.client;

import com.google.gson.JsonObject;

import java.util.List;

import me.zjc.swfu.base.BaseRtrftOnSubscribe;
import me.zjc.swfu.common.Constants;
import me.zjc.swfu.netWork.response.QueryScoreResponse;
import me.zjc.swfu.netWork.server.ScoreServer;
import me.zjc.swfu.netWork.server.ServerFactory;
import retrofit2.Call;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ChuanZhangjiang on 2016/4/11.
 */
public class ScoreClient {

    public static final String TAG = ScoreClient.class.getSimpleName();

    private static final ScoreClient instance = new ScoreClient();
    private ServerFactory mFactory = null;

    private ScoreClient(){
        mFactory = ServerFactory.getInstance();
    }

    public static final ScoreClient getInstance() {
        return instance;
    }

    public Observable<List<QueryScoreResponse>> queryScore(final String studentObjectId){
        return Observable.create(new BaseRtrftOnSubscribe<List<QueryScoreResponse>>() {
            @Override
            protected Call<List<QueryScoreResponse>> getCall() {
                ScoreServer server =
                        mFactory.createServer(Constants.BMOB_BASE_CLOUD_URL, ScoreServer.class);
                return server.queryScore(studentObjectId);
            }

            @Override
            protected int getSuccessCode() {
                return 200;
            }
        });
    }
}
