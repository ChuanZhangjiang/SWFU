package me.zjc.swfu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.zjc.swfu.R;
import me.zjc.swfu.adapter.listener.OnItemClickListener;
import me.zjc.swfu.adapter.listener.OnItemLongClickListener;
import me.zjc.swfu.tableBean.Course;

/**
 * Created by ChuanZhangjiang on 2016/4/8.
 */
public class CTAdapter extends RecyclerView.Adapter<CTAdapter.CTAdtViewHolder> {

    private OnItemClickListener<Course> mItemClickListener = null;
    private OnItemLongClickListener<Course> mItemLongClickListener = null;

    private List<Course> mCourse = null;
    private Context mContext = null;

    public CTAdapter (Context context, List<Course> courses) {
        this.mContext = context;
        this.mCourse = courses;
    }

    @Override
    public CTAdtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_course_table, parent, false);
        CTAdtViewHolder viewHolder = new CTAdtViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CTAdtViewHolder holder, final int position) {
        Course course = mCourse.get(position);
        holder.mTvCourseName.setText(course.getCourseName());
        holder.mTvCourseAddress.setText(course.getCoursAdress());
        List<Integer> sections = course.getCourseTimeObject().getSection();
        holder.mTvCourseSection.setText(transCourseSection(sections));
        holder.mItemView.setTag(course);

        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener == null) {
                    return;
                }
                Course course = (Course) v.getTag();
                mItemClickListener.onItemClick(position, course);
            }
        });

        holder.mItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemLongClickListener == null) {
                    return false;
                }
                Course course = (Course) v.getTag();
                mItemLongClickListener.onItemLongClick(position, course);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourse.size();
    }

    public void setData(List<Course> courses) {
        this.mCourse = courses;
    }

    public void setOnItemClickListener(OnItemClickListener<Course> clickListener) {
        this.mItemClickListener = clickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<Course> clickListener) {
        this.mItemLongClickListener = clickListener;
    }

    private String transCourseSection(List<Integer> sections) {
        StringBuilder courseSection = new StringBuilder("、节");
        for (int section: sections) {
            char firstChar = courseSection.charAt(0);
            int indexOfPause = courseSection.indexOf("、");
            if (firstChar == '、') {
                courseSection.insert(0, section);
            } else{
                courseSection.insert(indexOfPause + 1, section);
            }
        }

        return courseSection.toString();
    }

    public static class CTAdtViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvCourseName, mTvCourseAddress, mTvCourseSection;
        private View mItemView;

        public CTAdtViewHolder(View itemView) {
            super(itemView);
            mTvCourseName = (TextView) itemView.findViewById(R.id.tv_course_name);
            mTvCourseAddress = (TextView) itemView.findViewById(R.id.tv_course_address);
            mTvCourseSection = (TextView) itemView.findViewById(R.id.tv_course_section);
            mItemView = itemView;
        }
    }
}
