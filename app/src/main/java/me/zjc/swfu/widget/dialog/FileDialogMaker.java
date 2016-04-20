package me.zjc.swfu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

import me.zjc.swfu.R;
import me.zjc.swfu.adapter.FileListAdapter;
import me.zjc.swfu.netWork.response.BmobFile;
import me.zjc.swfu.util.LogUtil;
import me.zjc.swfu.util.ResUtil;

/**
 * Created by ChuanZhangjiang on 2016/4/18.
 */
public class FileDialogMaker {

    public static final String TAG = FileDialogMaker.class.getSimpleName();

    private Dialog mDialog = null;
    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private RecyclerView mRlvFiles = null;

    public FileDialogMaker(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        mRlvFiles = new RecyclerView(mContext);
    }

    public void create(List<BmobFile> fileList) {
        FileListAdapter adapter = new FileListAdapter(mContext, fileList);
        mRlvFiles.setAdapter(adapter);
        mRlvFiles.setLayoutManager(new LinearLayoutManager(mContext));
        mDialog = new AlertDialog.Builder(mContext)
                .setTitle(ResUtil.getResString(mContext, R.string.activity_notify_detail_file))
                .setView(mRlvFiles)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }

    public void show(){
        if (mDialog == null) {
            LogUtil.e(TAG, "请先调用FileDialogMaker.create()方法");
            return;
        }
        mDialog.show();
    }

}
