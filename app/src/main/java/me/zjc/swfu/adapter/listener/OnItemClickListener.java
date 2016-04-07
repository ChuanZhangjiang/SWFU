package me.zjc.swfu.adapter.listener;

import android.view.View;

/**
 * Created by ChuanZhangjiang on 2016/4/4.
 */
public interface OnItemClickListener<T> {
    void onItemClick(int position, T itemObject);
}
