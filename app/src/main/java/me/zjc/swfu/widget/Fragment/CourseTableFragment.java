package me.zjc.swfu.widget.Fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.zjc.swfu.R;
import me.zjc.swfu.base.BaseFragment;
import me.zjc.swfu.presenter.CTPresenter;
import me.zjc.swfu.view.ICourseTableView;

/**
 * Created by ChuanZhangjiang on 2016/3/28.
 */
public class CourseTableFragment extends BaseFragment implements ICourseTableView {

    private ViewHolder mViewHolder = null;
    private CTPresenter mPresenter = null;

    @Override
    protected int getResLayoutId() {
        return R.layout.fragment_course_table;
    }

    @Override
    protected void findView(View rootView) {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder();

            mViewHolder.mCvMondayTitle = (CardView) rootView.findViewById(R.id.cv_monday_title);
            mViewHolder.mCvTuesdayTitle = (CardView) rootView.findViewById(R.id.cv_tuesday_title);
            mViewHolder.mCvWednesdayTitle = (CardView) rootView.findViewById(R.id.cv_wednesday_title);
            mViewHolder.mCvThursdayTitle = (CardView) rootView.findViewById(R.id.cv_thursday_title);
            mViewHolder.mCvFridayTitle = (CardView) rootView.findViewById(R.id.cv_friday_title);
            mViewHolder.mCvSaturdayTitle = (CardView) rootView.findViewById(R.id.cv_saturday_title);
            mViewHolder.mCvSundayTitle = (CardView) rootView.findViewById(R.id.cv_sunday_title);

            mViewHolder.mTvMondayTitle = (TextView) rootView.findViewById(R.id.tv_monday_title);
            mViewHolder.mTvTuesdayTitle = (TextView) rootView.findViewById(R.id.tv_tuesday_title);
            mViewHolder.mTvWednesdayTitle = (TextView) rootView.findViewById(R.id.tv_wednesday_title);
            mViewHolder.mTvThursdayTitle = (TextView) rootView.findViewById(R.id.tv_thursday_title);
            mViewHolder.mTvFridayTitle = (TextView) rootView.findViewById(R.id.tv_friday_title);
            mViewHolder.mTvSaturdayTitle = (TextView) rootView.findViewById(R.id.tv_saturday_title);
            mViewHolder.mTvSundayTitle = (TextView) rootView.findViewById(R.id.tv_sunday_title);

            mViewHolder.mRlvCTMonday = (RecyclerView) rootView.findViewById(R.id.rlv_course_table_monday);
            mViewHolder.mRlvCTTuesday = (RecyclerView) rootView.findViewById(R.id.rlv_course_table_tuesday);
            mViewHolder.mRlvCTWednesday = (RecyclerView) rootView.findViewById(R.id.rlv_course_table_wednesday);
            mViewHolder.mRlvCTThursday = (RecyclerView) rootView.findViewById(R.id.rlv_course_table_thursday);
            mViewHolder.mRlvCTFriday = (RecyclerView) rootView.findViewById(R.id.rlv_course_table_friday);
            mViewHolder.mRlvCTSaturday = (RecyclerView) rootView.findViewById(R.id.rlv_course_table_saturday);
            mViewHolder.mRlvCTSunday = (RecyclerView) rootView.findViewById(R.id.rlv_course_table_sunday);

            mViewHolder.mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeLayout);
        }
    }

    @Override
    protected void initData() {
        if (mPresenter == null) {
            mPresenter = new CTPresenter(this);
            mPresenter.refreshCT(mViewHolder.mSwipeRefreshLayout);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setEvent() {
        mViewHolder.mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewHolder.mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.refreshCT(mViewHolder.mSwipeRefreshLayout);
            }
        });
    }

    @Override
    public Context getActivityContext() {
        return getActivity();
    }

    @Override
    public void showProgress(String hint) {
        showProcessDialog(hint);
    }

    @Override
    public void showFragmentToast(String hint) {
        showToast(hint);
    }

    private class ViewHolder{
        private CardView mCvMondayTitle, mCvTuesdayTitle, mCvWednesdayTitle, mCvThursdayTitle
                , mCvFridayTitle, mCvSaturdayTitle, mCvSundayTitle;

        private TextView mTvMondayTitle, mTvTuesdayTitle, mTvWednesdayTitle, mTvThursdayTitle
                , mTvFridayTitle, mTvSaturdayTitle, mTvSundayTitle;

        private RecyclerView mRlvCTMonday, mRlvCTTuesday, mRlvCTWednesday, mRlvCTThursday, mRlvCTFriday
                , mRlvCTSaturday, mRlvCTSunday;

        private SwipeRefreshLayout mSwipeRefreshLayout;
    }
}
