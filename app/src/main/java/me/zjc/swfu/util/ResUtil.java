package me.zjc.swfu.util;

import android.content.Context;

/**
 * Created by ChuanZhangjiang on 2016/4/18.
 */
public class ResUtil {
    public static final String getResString(Context context, int resId) {
        return context.getResources().getString(resId);
    }
}
