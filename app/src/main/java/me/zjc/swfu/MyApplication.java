package me.zjc.swfu;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import me.zjc.swfu.bean.User;
import me.zjc.swfu.common.Constants;
import me.zjc.swfu.util.LogUtil;

/**
 * Created by ChuanZhangjiang on 2016/1/9.
 */
public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();
    public final static boolean DEBUG = BuildConfig.DEBUG;

    private static MyApplication myApplication;

    public List<Activity> activityList = new LinkedList<Activity>();//用于存储启动过的Activity

    private static int mVersionCode;
    private static String mVersionName;
    private static String mPackageName;
    private static PackageInfo mInfo;


    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        //初始化Bmob
//        Bmob.initialize(this, Constants.APP_ID);
        myApplication = this;

        initAppInfo();
    }


    //判断网络是否连接
    public Boolean isNetConnection() {
        ConnectivityManager conn = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = conn.getActiveNetworkInfo();
        if (net != null && net.isConnected()) {
            return true;
        }
        return false;
    }

    //没有继承BaseActivity的Activity一定要调用这个方法，不然程序可能不会正常退出
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //移除activityList中的activity，与上一个方法相对
    public void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    private void initAppInfo(){
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pageInfo=pm.getPackageInfo(getPackageName(),0);
            mPackageName=pageInfo.packageName;
            mVersionName=pageInfo.versionName;
            mVersionCode=pageInfo.versionCode;
            mInfo=pageInfo;
            LogUtil.d(TAG, "initAppInfo: versionName:" + mVersionName + " VersionCode:" + mVersionCode + " PackageName:" + mPackageName);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
