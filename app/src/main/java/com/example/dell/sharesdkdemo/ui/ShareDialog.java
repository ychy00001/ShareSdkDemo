package com.example.dell.sharesdkdemo.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import com.example.dell.sharesdkdemo.R;
import com.example.dell.sharesdkdemo.adapter.ShareListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShareDialog {

    private AlertDialog mDialog;
    private RecyclerView mRecycleView;
    private RelativeLayout cancelButton;
    private ShareListAdapter mShareAdapter;
    private List<HashMap<String, Object>> shareList;
    private int[] image = {R.mipmap.ssdk_logo_sinaweibo_checked,
            R.mipmap.ssdk_logo_wechat_checked,
            R.mipmap.ssdk_logo_wechatmoments_checked,
            R.mipmap.ssdk_logo_qq_checked,
            R.mipmap.ssdk_logo_qzone_checked};
    private String[] name = {"微博", "微信", "朋友圈", "QQ","QQ空间"};

    public ShareDialog(Context context) {
        initData();
        initDialog(context);
        fillData(context);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        shareList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < image.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", image[i]);//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            shareList.add(map);
        }
    }

    /**
     * 初始化对话框
     * @param context 上下文
     */
    private void initDialog(Context context) {
        mDialog = new AlertDialog.Builder(context).create();
        mDialog.show();
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 非常重要：设置对话框弹出的位置
        window.setContentView(R.layout.layout_ssdk_content);
        mRecycleView = (RecyclerView) window.findViewById(R.id.rlv_horizontal_list);
        cancelButton = (RelativeLayout) window.findViewById(R.id.rl_share_cancel);
    }

    /**
     * 填充数据至Dialog
     * @param context 上下文
     */
    private void fillData(Context context) {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleView.setLayoutManager(linearLayoutManager);

        mShareAdapter = new ShareListAdapter(context, shareList);
        mRecycleView.setAdapter(mShareAdapter);
    }



    public void setCancelButtonOnClickListener(OnClickListener Listener) {
        cancelButton.setOnClickListener(Listener);
    }

    public void setOnItemClickListener(ShareListAdapter.OnItemClickLitener listener) {
        mShareAdapter.setOnItemClickLitener(listener);
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        mDialog.dismiss();
    }
}