package me.zjc.swfu.netWork.response;

import java.util.List;

/**
 * Created by ChuanZhangjiang on 2016/4/17.
 */
public class NotifyDetail {

    private String title;
    private String content;
    private List<BmobFile> file;

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

    public List<BmobFile> getFile() {
        return file;
    }

    public void setFile(List<BmobFile> file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "NotifyDetail{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", file=" + file +
                '}';
    }
}
