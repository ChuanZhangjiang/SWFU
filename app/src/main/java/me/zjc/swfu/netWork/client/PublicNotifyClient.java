package me.zjc.swfu.netWork.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import me.zjc.swfu.base.BaseRtrftOnSubscribe;
import me.zjc.swfu.netWork.server.QueryServer;
import me.zjc.swfu.netWork.server.ServerFactory;
import me.zjc.swfu.tableBean.PublicNotify;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ChuanZhangjiang on 2016/3/28.
 */
public class PublicNotifyClient {
    private static PublicNotifyClient instance = null;
    private ServerFactory factory = null;
    private PublicNotifyClient() {
        factory = ServerFactory.getInstance();
    }
    public static PublicNotifyClient getInstance() {
        if (instance == null) {
            instance = new PublicNotifyClient();
        }
        return instance;
    }

    private Observable<List<PublicNotify>> allPublicNotify() {
        return Observable.create(new BaseRtrftOnSubscribe<JsonObject>() {
            @Override
            protected Call<JsonObject> getCall() {
                QueryServer server = factory.createServer(QueryServer.class);
                return server.getAllObject("PublicNotify");
            }

            @Override
            protected int getSuccessCode() {
                return 200;
            }
        }).onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .map(new Func1<JsonObject, List<PublicNotify>>() {
                    @Override
                    public List<PublicNotify> call(JsonObject jsonObject) {
                        JsonArray publicNotifyJsonArray = jsonObject.getAsJsonArray("results");
                        Gson gson = new Gson();
                        List<PublicNotify> publicNotifyList = new ArrayList<PublicNotify>();
                        for (JsonElement publicNotifyJson : publicNotifyJsonArray) {
                            PublicNotify publicNotify = gson.fromJson(publicNotifyJson, PublicNotify.class);
                            publicNotifyList.add(publicNotify);
                        }
                        return publicNotifyList;
                    }
                });
    }

    public void queryAllPublicNotify(Subscriber<List<PublicNotify>> subscriber) {
        allPublicNotify().observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
