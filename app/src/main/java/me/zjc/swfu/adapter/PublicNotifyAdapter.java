package me.zjc.swfu.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.zjc.swfu.R;
import me.zjc.swfu.adapter.listener.OnItemClickListener;
import me.zjc.swfu.adapter.listener.OnItemLongClickListener;
import me.zjc.swfu.tableBean.PublicNotify;

/**
 * Created by ChuanZhangjiang on 2016/3/28.
 */
public class PublicNotifyAdapter extends RecyclerView.Adapter<PublicNotifyAdapter.PublicNotifyViewHolder> {

    public static final String TAG = PublicNotifyAdapter.class.getSimpleName();

    private List<PublicNotify> mPublicNotifies = null;
    private Context mContext = null;

    private OnItemClickListener<PublicNotify> mItemClickListener = null;
    private OnItemLongClickListener<PublicNotify> mItemLongClickListener = null;

    public PublicNotifyAdapter(List<PublicNotify> publicNotifies, Context context) {
        this.mPublicNotifies = publicNotifies;
        this.mContext = context;
    }

    @Override
    public PublicNotifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.item_public_notify, parent, false);
        return new PublicNotifyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(PublicNotifyViewHolder holder, final int position) {
        PublicNotify notify = mPublicNotifies.get(position);
        holder.mTvTitle.setText(notify.getTitle());
        holder.mTvContent.setText(notify.getContent());
        holder.itemView.setTag(notify);

        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position, mPublicNotifies.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPublicNotifies.size();
    }

    public void setOnItemClickListener(OnItemClickListener<PublicNotify> listener) {
        mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<PublicNotify> listener) {
        mItemLongClickListener = listener;
    }

    public static class PublicNotifyViewHolder extends RecyclerView.ViewHolder {

        private View mItemView;
        private ImageView mIvIc;
        private TextView mTvTitle;
        private TextView mTvContent;

        public PublicNotifyViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mIvIc = (ImageView) itemView.findViewById(R.id.iv_public_notify_item);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_public_notify_title);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_public_notify_content);
        }
    }
}
