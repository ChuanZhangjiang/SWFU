package me.zjc.swfu.model.impl;

import java.util.List;

import me.zjc.swfu.model.IScoreModel;
import me.zjc.swfu.netWork.client.ScoreClient;
import me.zjc.swfu.netWork.response.QueryScoreResponse;
import rx.Observable;

/**
 * Created by ChuanZhangjiang on 2016/4/13.
 */
public class ScoreModel implements IScoreModel {

    private ScoreClient mScoreClient = null;

    public ScoreModel(){
        mScoreClient = ScoreClient.getInstance();
    }

    @Override
    public Observable<List<QueryScoreResponse>> queryMyScore(String studentObjectId) {
        return mScoreClient.queryScore(studentObjectId);
    }
}
