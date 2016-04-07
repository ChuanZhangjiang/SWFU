package me.zjc.swfu.netWork.requestBody;

import java.util.List;

/**
 * 学生退课请求体
 * Created by ChuanZhangjiang on 2016/3/24.
 */
public class UnChuCoRequestBody {
    private OprFiled course;

    private class OprFiled {//要操作的字段
        private final String __op = "Remove";
        private List<String> objects;
    }

    public void setCourse(List<String> objectIds) {
        OprFiled oprFiled = new OprFiled();
        oprFiled.objects = objectIds;
        course = oprFiled;
    }
}
