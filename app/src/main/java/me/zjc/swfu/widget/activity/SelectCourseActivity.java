package me.zjc.swfu.widget.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.List;

import me.zjc.swfu.R;
import me.zjc.swfu.adapter.SCAdapter;
import me.zjc.swfu.base.BaseActivity;
import me.zjc.swfu.presenter.SCPresent;
import me.zjc.swfu.tableBean.Course;
import me.zjc.swfu.view.ISelectCourseView;

/**
 * Created by ChuanZhangjiang on 2016/4/5.
 */
public class SelectCourseActivity extends BaseActivity implements ISelectCourseView {

    public static final String TAG = SelectCourseActivity.class.getSimpleName();

    private ViewHolder mViewHolder = null;

    private List<Course> mCourseList = null;
    private List<Course> mSelectedCourses = null;
    private SCPresent mPresenter = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_course;
    }

    @Override
    protected void initData() {
        if (mPresenter == null) {
            mPresenter = new SCPresent(this);
        }
    }

    @Override
    protected void findView() {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder();
            mViewHolder.mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
            mViewHolder.mRlvCourse = (RecyclerView) findViewById(R.id.rlv_course);
            mViewHolder.mFabMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
            mViewHolder.mFabCommit = (FloatingActionButton) findViewById(R.id.fab_commit);
            mViewHolder.mFabClear = (FloatingActionButton) findViewById(R.id.fab_clear);
            mViewHolder.mFabLook = (FloatingActionButton) findViewById(R.id.fab_look);
        }
    }

    @Override
    protected void initView() {
        setTitle(getResString(R.string.select_course_title));
        mViewHolder.mSwipe.setRefreshing(true);
        mPresenter.refreshCourseList();
    }

    @Override
    protected void setEvent() {
        mViewHolder.mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewHolder.mSwipe.setRefreshing(true);
                mPresenter.refreshCourseList();
            }
        });

        mViewHolder.mFabCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.mFabMenu.toggle();
                mPresenter.commitCourse();
            }
        });

        mViewHolder.mFabClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.mFabMenu.toggle();
                clearSelectedCourse();
            }
        });

        mViewHolder.mFabLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.mFabMenu.toggle();
                showLookCourseISelectedDialog(mSelectedCourses);
            }
        });
    }

    private void clearSelectedCourse() {
        SCAdapter adapter = (SCAdapter) mViewHolder.mRlvCourse.getAdapter();
        if (adapter != null) {
            adapter.clearSelectedCourse();
//            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setCourseData(List<Course> courses) {
        mCourseList = courses;
    }

    @Override
    public void initSelectedCourse(List<Course> selectedCourse) {
        mSelectedCourses = selectedCourse;
        SCAdapter adapter = new SCAdapter(this, mCourseList, selectedCourse);
        mViewHolder.mRlvCourse.setHasFixedSize(true);
        mViewHolder.mRlvCourse.setAdapter(adapter);
        mViewHolder.mRlvCourse.setLayoutManager(new LinearLayoutManager(this));
        mViewHolder.mRlvCourse.setItemAnimator(new DefaultItemAnimator());
        mViewHolder.mSwipe.setRefreshing(false);
    }

    @Override
    public List<String> getSelectedCourse() {
        SCAdapter adapter = (SCAdapter) mViewHolder.mRlvCourse.getAdapter();
        if (adapter == null) {
            return null;
        }
        List<String> selectedCourseIds = adapter.getSelectedCourse();
        if (selectedCourseIds == null || selectedCourseIds.size() < 1) {
            showToast(getResString(R.string.select_course_no_select_course));
            return null;
        }
        return selectedCourseIds;
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void showAtyToast(String hint) {
        showToast(hint);
    }

    private void showLookCourseISelectedDialog(List<Course> courses) {

        if (courses == null || courses.size() < 1) {
            showAtyToast(getResString(R.string.select_course_no_selected_course));
            return;
        }

        RecyclerView rlv;

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_look_selected_course, null);
        rlv = (RecyclerView) view.findViewById(R.id.rlv_course_ISelected);

        final SCAdapter adapter = new SCAdapter(this, courses, null);
        rlv.setAdapter(adapter);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        rlv.setItemAnimator(new DefaultItemAnimator());

        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.select_course_look_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton(R.string.select_course_unChoose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.unChooseCourse(adapter.getSelectedCourse());
                    }
                })
                .create();

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if (mViewHolder == null ) {
            super.onBackPressed();
        }

        if (mViewHolder.mFabMenu.isExpanded()) {
            mViewHolder.mFabMenu.toggle();
        } else {
            super.onBackPressed();
        }

    }

    private class ViewHolder{
        private SwipeRefreshLayout mSwipe;
        private RecyclerView mRlvCourse;
        private FloatingActionsMenu mFabMenu;
        private FloatingActionButton mFabCommit;
        private FloatingActionButton mFabClear;
        private FloatingActionButton mFabLook;

    }

}
