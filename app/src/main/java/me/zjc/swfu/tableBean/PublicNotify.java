package me.zjc.swfu.tableBean;

import java.util.List;

/**
 * Created by ChuanZhangjiang on 2016/3/28.
 */
public class PublicNotify {
    private String objectId;
    private String title;
    private String content;
    private String to;
    private String type;//公告类型
    private List<String> file; //附件id集合

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class PublicNotifyType {
        public static final String NORMAL = "0";//普通公告
        public static final String SELECT_COURSE = "1";//选课类型的公告
    }
}
