package me.zjc.swfu.model;

import java.util.List;

import me.zjc.swfu.netWork.response.QueryScoreResponse;
import rx.Observable;

/**
 * Created by ChuanZhangjiang on 2016/4/13.
 */
public interface IScoreModel {

    /**
     * 查询学生成绩
     * @param studentObjectId
     * @return
     */
    Observable<List<QueryScoreResponse>> queryMyScore(String studentObjectId);

}
