package me.zjc.swfu.view;

import java.util.List;

import me.zjc.swfu.tableBean.PublicNotify;

/**
 * Created by ChuanZhangjiang on 2016/4/3.
 */
public interface IPublicNotifyView {
    /**
     * 设置公文公告的数据
     * @param publicNotifies 公文公告的数据
     */
    void setPublicNotifyData(List<PublicNotify> publicNotifies);

}
