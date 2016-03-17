package me.zjc.swfu.common;

/**
 * Created by ChuanZhangjiang on 2016/1/9.
 * 常量类，用于存储应用所需要的一些常量
 */
public class Constants {
    //bmob中的APP_ID
    public static final String APP_ID = "db0e2b66462e5bafe9150538c9bf26d4";
    public static final String REST_API_KEY = "fcbf78a1e0bfecefe51059fec2d931a4";

    //性别
    public static final Boolean MALE = true;
    public static final Boolean FEMALE = false;

    //SharedPreferences的名称
    public static class sharedPreferencesNameManager {
        public static final String CURRENT_USER = "currentUser";
    }

    //程序结束标志
    public static final String CLOSE_FLAG = "closeFlag";
    public static final String CLOSE_FLAG_CLOSE = "close";
    //handler消息标志
    public static final int LOGIN_GET_USER_SUCCESS = 0x00000001;//登录成功
    public static final int LOGIN_GET_USER_FAIL = 0x00000002;//登录失败
    //bmobUrl
    public static final String fileBaseUrl = "http://file.bmob.cn/";//
    public static final String BMOB_BASE_URL = "https://api.bmob.cn";//

    public static class UserUrlManager {
        public static final String REGISTER_URL = "/1/users";
        public static final int REGISTER_SUCCESS = 201;
        public static final String LOGIN_URL = "/1/login";
        public static final int LOGIN_SUCCESS = 200;
        public static final String GET_USER_BY_OBJECTID_URL = "/1/users/{objectID}";
        public static final int GET_USER_BY_OBJECTID_SUCCESS = 200;
        public static final String UPDATE_USER_BY_OBJECTID_URL   = "/1/users/{objectId}";
        public static final int UPDATE_USER_BY_OBJECTID_SUCCESS = 200;
        public static final String DELETE_USER_BY_OBJECTiD_URL = "/1/users/{objectId}";
        public static final int DELETE_USER_BY_OBJECTID_SUCCESS = 200;
        public static final String GET_ALL_USER_URL = REGISTER_URL;
        public static final int GET_ALL_USER_SUCCESS = 200;
        public static final String UPDATE_PASSWORD_URL = "/1/updateUserPassword/{objectId}";
        public static final int UPDATE_PASSWORD_SUCCESS = 200;
    }

    public static class UserTableField {
        public static final String OBJECT_ID = "objectId";
        public static final String USER_NAME = "username";
        public static final String PASSWORD = "password";
        public static final String PROFESSIONAL = "professional";
        public static final String SEX = "sex";
        public static final String AGE = "age";
        public static final String CLASSNAME = "className";
        public static final String LEVEL = "level";
        public static final String NAME = "name";
        public static final String HEADER_URL = "header_url";
        public static final String DEC = "dec";
        public static final String SESSIONTOKEN = "sessionToken";
    }
}
