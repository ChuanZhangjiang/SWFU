package me.zjc.swfu.widget.Fragment;

import android.content.Intent;
import android.view.View;

import me.zjc.swfu.R;
import me.zjc.swfu.base.BaseFragment;
import me.zjc.swfu.widget.activity.QueryScoreActivity;

/**
 * Created by ChuanZhangjiang on 2016/1/17.
 */
public class InfoQueryFragment extends BaseFragment {

    public static final String TAG = InfoQueryFragment.class.getSimpleName();
    private ViewHolder mViewHolder = null;

    @Override
    protected int getResLayoutId() {
        return R.layout.fragment_information_query;
    }

    @Override
    protected void findView(View rootView) {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder();
            mViewHolder.queryScoreLayout = rootView.findViewById(R.id.query_score_layout);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setEvent() {
        if (mViewHolder == null) {
            return;
        }

        mViewHolder.queryScoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动查询成绩Activity;
                Intent intent = new Intent(getActivity(), QueryScoreActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    private class ViewHolder{
        View queryScoreLayout = null;
    }
}
