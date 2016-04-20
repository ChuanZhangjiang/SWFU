package me.zjc.swfu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.util.List;

import me.zjc.swfu.R;
import me.zjc.swfu.netWork.response.QueryScoreResponse;
import me.zjc.swfu.util.LogUtil;

/**
 * Created by ChuanZhangjiang on 2016/4/17.
 */
public class QSAdapter extends RecyclerView.Adapter<QSAdapter.ViewHolder> {

    public static final String TAG = QSAdapter.class.getSimpleName();

    private Context mContext = null;
    private List<QueryScoreResponse> mData = null;

    public QSAdapter(Context context, List<QueryScoreResponse> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_score, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QueryScoreResponse score = mData.get(position);

        holder.mTvCourseName.setText(score.getCourseName());
        LogUtil.e(TAG, score.getScore()+"");
        String scoreString = score.getScore()+"";
        holder.mTvCourseScore.setText(scoreString);
        holder.itemView.setTag(score);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvCourseName = null;
        private TextView mTvCourseScore = null;
        private View mItemView = null;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mItemView = itemView;
            this.mTvCourseName = (TextView) itemView.findViewById(R.id.tv_query_score_course_name);
            this.mTvCourseScore = (TextView) itemView.findViewById(R.id.tv_query_score_course_score);
        }
    }
}
