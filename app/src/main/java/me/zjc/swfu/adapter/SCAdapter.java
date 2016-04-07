package me.zjc.swfu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.zjc.swfu.R;
import me.zjc.swfu.tableBean.Course;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by ChuanZhangjiang on 2016/4/5.
 */
public class SCAdapter extends RecyclerView.Adapter<SCAdapter.SCAdapterViewHolder> {

    public static final String TAG = SCAdapter.class.getSimpleName();

    private List<Course> mCourses = null;
    private Context mContext = null;
    private List<String> mSelectedCourse = null; //已经被选过的课
    private List<String> mCurrentSelectCourse = null;
    private List<CheckBox> mSelectedCB = null;

    public SCAdapter(Context context, List<Course> courses, List<Course> selectedCourse) {
        this.mCourses = courses;
        this.mContext = context;
        this.mCurrentSelectCourse = new ArrayList<>();
        this.mSelectedCB = new ArrayList<>();
        if (selectedCourse != null) {
            Observable.from(selectedCourse)
                    .map(new Func1<Course, String>() {
                        @Override
                        public String call(Course course) {
                            return course.getObjectId();
                        }
                    })
                    .buffer(selectedCourse.size())
                    .subscribe(new Action1<List<String>>() {
                        @Override
                        public void call(List<String> strings) {
                            mSelectedCourse = strings;
                        }
                    });
        }
        if (mCourses != null && mSelectedCourse != null) {
            Iterator<Course> iterator = mCourses.iterator();
            while (iterator.hasNext()) {
                Course course = iterator.next();
                if (mSelectedCourse.contains(course.getObjectId())) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public SCAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.item_select_course, parent, false);
        return new SCAdapterViewHolder(item);
    }

    @Override
    public void onBindViewHolder(SCAdapterViewHolder holder, int position) {
        Course course = mCourses.get(position);

        holder.mTvCourseName.setText(course.getCourseName());
        holder.mTvCourseTeacher.setText(course.getTeacher());
        holder.mCbCheckCourse.setTag(course);
        mSelectedCB.add(holder.mCbCheckCourse);

        if (mCurrentSelectCourse.contains(course.getObjectId())) {
            holder.mCbCheckCourse.setChecked(true);
        }

        holder.mCbCheckCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                Course course = (Course) checkBox.getTag();
                boolean isChecked = checkBox.isChecked();
                if (isChecked) {
                    if (!mCurrentSelectCourse.contains(course.getObjectId())) {
                        mCurrentSelectCourse.add(course.getObjectId());
                    }
                } else {
                    mCurrentSelectCourse.remove(course.getObjectId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public List<String> getSelectedCourse() {
        return mCurrentSelectCourse;
    }

    public void clearSelectedCourse() {
        mCurrentSelectCourse.clear();
        Observable.from(mSelectedCB)
                .subscribe(new Action1<CheckBox>() {
                    @Override
                    public void call(CheckBox checkBox) {
                        checkBox.setChecked(false);
                    }
                });
    }

    public static class SCAdapterViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mCbCheckCourse;
        private TextView mTvCourseName;
        private TextView mTvCourseTeacher;

        public SCAdapterViewHolder(View itemView) {
            super(itemView);
            mCbCheckCourse = (CheckBox) itemView.findViewById(R.id.cb_check_course);
            mTvCourseName = (TextView) itemView.findViewById(R.id.tv_course_name);
            mTvCourseTeacher = (TextView) itemView.findViewById(R.id.tv_course_teacher);
        }
    }
}
