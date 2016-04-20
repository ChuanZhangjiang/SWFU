package me.zjc.swfu.view;

import android.content.Context;

import java.util.List;

import me.zjc.swfu.netWork.response.QueryScoreResponse;

/**
 * Created by ChuanZhangjiang on 2016/4/16.
 */
public interface IQSView {

    void showScore(List<QueryScoreResponse> data);

    Context getCurrentActivity();

}
