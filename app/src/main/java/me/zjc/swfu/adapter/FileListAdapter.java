package me.zjc.swfu.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import me.zjc.swfu.R;
import me.zjc.swfu.netWork.client.DownloadClient;
import me.zjc.swfu.netWork.response.BmobFile;
import me.zjc.swfu.util.FileUtil;
import me.zjc.swfu.util.LogUtil;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ChuanZhangjiang on 2016/4/18.
 */
public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.FLHolder> {

    public static final String TAG = FileListAdapter.class.getSimpleName();

    private Context mContext = null;
    private List<BmobFile> mData = null;

    public FileListAdapter(Context context, List<BmobFile> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public FLHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_file_list, parent, false);
        FLHolder holder = new FLHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final FLHolder holder, int position) {
        BmobFile file = mData.get(position);
        FileUtil util = FileUtil.getInstance();

        holder.mTvFileName.setText(file.getFilename());
        holder.mItemView.setTag(file);

        boolean isFileDownload = util.checkFileIsDownload(file);
        if (isFileDownload) {
            holder.mIvOpenFile.setImageResource(R.mipmap.ic_open_file);
        } else {
            holder.mIvOpenFile.setImageResource(R.mipmap.ic_download);
        }
        holder.mIvOpenFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealFile(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class FLHolder extends RecyclerView.ViewHolder{

        private TextView mTvFileName = null;
        private ImageButton mIvOpenFile = null;
        private ProgressBar mPbDownload = null;
        private ProgressBar mPbWait = null;
        private View mItemView = null;

        public FLHolder(View itemView) {
            super(itemView);
            this.mTvFileName = (TextView) itemView.findViewById(R.id.tv_file_name);
            this.mIvOpenFile = (ImageButton) itemView.findViewById(R.id.iv_openFile);
            this.mPbDownload = (ProgressBar) itemView.findViewById(R.id.pb_download);
            this.mPbWait = (ProgressBar) itemView.findViewById(R.id.pb_wait);
            this.mItemView = itemView;
        }
    }

    /**
     * holder.mIvOpenFile的点击事件
     */
    private void dealFile(final FLHolder holder) {
        BmobFile file = (BmobFile) holder.mItemView.getTag();
        FileUtil util = FileUtil.getInstance();
        boolean isFileAlreadyDownload = util.checkFileIsDownload(file);
        if (isFileAlreadyDownload) {
            File localFile = new File(FileUtil.DOWNLOAD_FILE_PATH + file.getFilename());
            util.openFile(mContext, localFile);
        } else {
            holder.mIvOpenFile.setVisibility(View.GONE);
            holder.mPbWait.setVisibility(View.VISIBLE);
            downloadFile(file.getUrl(), file.getFilename(), new MyDownloadLister() {
                long maxSize = 0l;
                @Override
                public void onDownloadProgressUpdate(long total) {
                    holder.mPbDownload.setProgress((int) total);
                }

                @Override
                public void onDownloadFinish(File file) {
                    holder.mPbDownload.setVisibility(View.GONE);
                    if (maxSize == file.length()) {
                        holder.mIvOpenFile.setImageResource(R.mipmap.ic_open_file);
                        holder.mIvOpenFile.setVisibility(View.VISIBLE);
                        holder.mPbWait.setVisibility(View.GONE);
                        LogUtil.e(TAG, "下载校验完成");
                    } else {
                        holder.mIvOpenFile.setImageResource(R.mipmap.ic_open_file);
                        holder.mIvOpenFile.setVisibility(View.VISIBLE);
                        holder.mPbWait.setVisibility(View.GONE);
                        LogUtil.e(TAG, "下载成功，校验失败 " + maxSize + " " +file.length());
                    }
                }

                @Override
                public void onGetResSuccess(long resSize) {
                    maxSize = resSize;
                    holder.mPbDownload.setMax((int) resSize);
                    holder.mPbDownload.setVisibility(View.VISIBLE);
                }

                @Override
                public void onGetResFailure(Throwable error) {
                    LogUtil.e(TAG, "获取资源失败：" + error.getMessage());
                }
            });
        }
    }

    private void downloadFile(String url, final String filename, final MyDownloadLister listener) {
        DownloadClient client = DownloadClient.getInstance();
        client.setDownloadResListener(listener);
        client.downloadFile(url)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ResponseBody, File>() {
                    @Override
                    public File call(ResponseBody responseBody) {
                        FileUtil util = FileUtil.getInstance();
                        return util.downloadFile(responseBody.byteStream(), filename, listener);
                    }
                })
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.e(TAG, "下载完成！");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "下载失败： " + e.getMessage());
                    }

                    @Override
                    public void onNext(File file) {
                        LogUtil.e(TAG, "文件大小: " + file.length());
                    }
                });
    }

    public interface MyDownloadLister extends FileUtil.DownloadListener, DownloadClient.DownloadResListener{

    }

}
