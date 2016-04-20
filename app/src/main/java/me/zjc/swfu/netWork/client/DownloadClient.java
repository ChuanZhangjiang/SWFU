package me.zjc.swfu.netWork.client;


import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import me.zjc.swfu.netWork.server.DownloadServer;
import me.zjc.swfu.netWork.server.ServerFactory;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by ChuanZhangjiang on 2016/4/18.
 */
public class DownloadClient {

    public static final String TAG = DownloadClient.class.getSimpleName();

    private ServerFactory mFactory = null;
    private static final DownloadClient instance = new DownloadClient();
    private DownloadResListener downloadResListener = null;

    private DownloadClient() {
        mFactory = ServerFactory.getInstance();
    }

    public static DownloadClient getInstance() {
        return instance;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public Observable<ResponseBody> downloadFile(final String url) {
        return Observable.create(new Observable.OnSubscribe<ResponseBody>() {
            @Override
            public void call(Subscriber<? super ResponseBody> subscriber) {
                String pre = url.substring(url.lastIndexOf("/")+1);
                String baseUrl = url.substring(0, url.lastIndexOf("/") + 1);
                DownloadServer server = mFactory.createServer(baseUrl, DownloadServer.class);
                Call<ResponseBody> call = server.downloadFile(pre);

                try {
                    final Response<ResponseBody> response = call.execute();

                    if (response.isSuccessful()) {
                        if (downloadResListener != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    downloadResListener.onGetResSuccess(response.body().contentLength());
                                }
                            });
                        }
                        subscriber.onNext(response.body());
                    } else {
                        if (downloadResListener != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        downloadResListener.onGetResFailure
                                                (new Throwable(response.errorBody().string()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        subscriber.onError(new Throwable(response.errorBody().string()));
                    }

                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onCompleted();
                        call.cancel();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }

    public void setDownloadResListener(DownloadResListener listener) {
        this.downloadResListener = listener;
    }

    public interface DownloadResListener {
        void onGetResSuccess(long resSize);
        void onGetResFailure(Throwable error);
    }
}
