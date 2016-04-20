package me.zjc.swfu.netWork.response;

/**
 * Created by ChuanZhangjiang on 2016/4/17.
 */
public class BmobFile {

    private String __type;
    private String cdn;
    private String filename;
    private String url;

    public String get__type() {
        return __type;
    }

    public void set__type(String __type) {
        this.__type = __type;
    }

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BmobFile{" +
                "__type='" + __type + '\'' +
                ", cdn='" + cdn + '\'' +
                ", filename='" + filename + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
