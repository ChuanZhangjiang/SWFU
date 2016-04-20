package me.zjc.swfu.tableBean;


/**
 * Created by ChuanZhangjiang on 2016/1/11.
 */
public class User {

    public static final String TAB_NAME = "_User";

    private String objectId;
    private String username;//学号
    private String password;
    private String email;
    private String professional;
    private Boolean sex;
    private Integer age;
    private String className;
    private Integer level;
    private String name; //姓名
    private String dec;
    private Boolean isAdmin = false;

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    private String header_url;
    private String sessionToken;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeader_url() {
        return header_url;
    }

    public void setHeader_url(String header_url) {
        this.header_url = header_url;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "objectId='" + objectId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", professional='" + professional + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", className='" + className + '\'' +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", dec='" + dec + '\'' +
                ", header_url='" + header_url + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                '}';
    }
}
