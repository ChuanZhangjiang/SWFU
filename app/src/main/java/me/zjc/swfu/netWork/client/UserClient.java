package me.zjc.swfu.netWork.client;


import me.zjc.swfu.netWork.server.ServerFactory;

/**
 * Created by ChuanZhangjiang on 2016/3/25.
 */
public class UserClient {

    public static final String TAG = UserClient.class.getSimpleName();

    private ServerFactory factory = null;
    private static UserClient instance = null;

    private UserClient() {
        factory = ServerFactory.getInstance();
    }

    public static UserClient getInstance() {
        if (instance == null) {
            instance = new UserClient();
        }
        return instance;
    }


}
