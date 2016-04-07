package me.zjc.swfu.netWork.server;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ChuanZhangjiang on 2016/2/24.
 */
public class ServerFactory {
    private static ServerFactory instance = null;
    private ServerFactory() {}

    public static ServerFactory getInstance() {
        if (instance == null) {
            instance = new ServerFactory();
        }
        return instance;
    }

    public <T> T createServer (String baseUrl, Class<? extends T> clazz) {
        if (baseUrl == null) {
            baseUrl = "https://api.bmob.cn";
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

         T server =  retrofit.create(clazz);

        return server;
    }

    public <T> T createServer (Class<? extends T> clazz) {
        return createServer(null, clazz);
    }

}
