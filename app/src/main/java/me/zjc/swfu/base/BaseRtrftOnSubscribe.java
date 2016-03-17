package me.zjc.swfu.base;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import me.zjc.swfu.util.LogUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by ChuanZhangjiang on 2016/2/11.
 */
public abstract class BaseRtrftOnSubscribe<T> implements Observable.OnSubscribe<T> {

    private static final String TAG = BaseRtrftOnSubscribe.class.getSimpleName();

    @Override
    public void call(Subscriber<? super T> subscriber) {
        Call<T> call = getCall();

        Response<T> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            return;
        }
        if (response.isSuccess() && response.code() == getSuccessCode()) {
            subscriber.onNext(response.body());
        } else {
            try {
                subscriber.onError(new Throwable(getErrorMessage(response.errorBody())));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                LogUtil.e(TAG, e.getMessage());
            }
        }

        if (!subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
            subscriber.unsubscribe();
        }
    }

    abstract protected Call<T> getCall();
    abstract protected int getSuccessCode();

    private String getErrorMessage(ResponseBody errorBody) throws IOException, JSONException {
        JSONObject errorBodyJson = new JSONObject(errorBody.string());
        return errorBodyJson.getString("error");
    }
}

