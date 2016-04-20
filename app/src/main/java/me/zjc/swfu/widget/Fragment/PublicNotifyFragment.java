package me.zjc.swfu.widget.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import me.zjc.swfu.R;
import me.zjc.swfu.adapter.PublicNotifyAdapter;
import me.zjc.swfu.adapter.listener.OnItemClickListener;
import me.zjc.swfu.base.BaseFragment;
import me.zjc.swfu.common.Constants;
import me.zjc.swfu.presenter.PNPresent;
import me.zjc.swfu.tableBean.PublicNotify;
import me.zjc.swfu.view.IPublicNotifyView;
import me.zjc.swfu.widget.activity.MyCenterActivity;
import me.zjc.swfu.widget.activity.PublicNotifyDetailActivity;
import me.zjc.swfu.widget.activity.SelectCourseActivity;

/**
 * Created by ChuanZhangjiang on 2016/3/28.
 */
public class PublicNotifyFragment extends BaseFragment implements IPublicNotifyView {

    public static final String TAG = PublicNotifyFragment.class.getSimpleName();

    private ViewHolder mHolder = null;
    private PNPresent mPresent = null;

    @Override
    protected int getResLayoutId() {
        return R.layout.fragment_notify;
    }

    @Override
    protected void findView(View rootView) {
        if (mHolder == null) {
            mHolder = new ViewHolder();
            mHolder.mNotifyListView = (RecyclerView) rootView.findViewById(R.id.rlv_notify);
            mHolder.mSwipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeLayout);
        }
    }

    @Override
    protected void initData() {
        if (mPresent == null) {
            mPresent = new PNPresent(this);
        }
    }

    @Override
    protected void initView() {
        mHolder.mSwipe.setRefreshing(true);
        mPresent.refreshPNList();
    }

    @Override
    protected void setEvent() {
        mHolder.mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHolder.mSwipe.setRefreshing(true);
                mPresent.refreshPNList();
            }
        });
    }

    @Override
    public void setPublicNotifyData(List<PublicNotify> publicNotifies) {
        PublicNotifyAdapter adapter = new PublicNotifyAdapter(publicNotifies, getActivity());
        mHolder.mNotifyListView.setHasFixedSize(true);
        mHolder.mNotifyListView.setAdapter(adapter);
        mHolder.mNotifyListView.setLayoutManager(
                new LinearLayoutManager(getContext()));
        mHolder.mNotifyListView.setItemAnimator(new DefaultItemAnimator());
        mHolder.mSwipe.setRefreshing(false);

        adapter.setOnItemClickListener(new OnItemClickListener<PublicNotify>() {
            @Override
            public void onItemClick(int position, PublicNotify itemObject) {
                onNotifyClick(position, itemObject);
            }
        });
    }

    private void onNotifyClick(int position, PublicNotify publicNotify) {
        String notifyType = publicNotify.getType();
        Activity activity = getActivity();
        if (PublicNotify.PublicNotifyType.NORMAL.equals(notifyType)) { //常规公告
            Intent intent = new Intent(activity, PublicNotifyDetailActivity.class);
            intent.putExtra("notifyId", publicNotify.getObjectId());
            startActivity(intent);
        } else if (PublicNotify.PublicNotifyType.SELECT_COURSE.equals(notifyType)) { //选课公告
            Intent intent = new Intent(activity, SelectCourseActivity.class);
            startActivity(intent);
        }
    }

    private class ViewHolder{
        private SwipeRefreshLayout mSwipe;
        private RecyclerView mNotifyListView;
    }
}
