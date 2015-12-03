package com.example.dell.sharesdkdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.example.dell.sharesdkdemo.adapter.ShareListAdapter;
import com.example.dell.sharesdkdemo.ui.ShareDialog;

import java.util.HashMap;

import static cn.sharesdk.framework.utils.ShareSDKR.getStringRes;

public class MainActivity extends AppCompatActivity implements PlatformActionListener {
    private static final int MSG_SHARE_SUCCESS = 0;
    private static final int MSG_SHARE_ERROR = 1;
    private static final int MSG_SHARE_CANCEL = 2;

    private ShareDialog mShareDialog;
    private Handler mHandler;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(this);
        setContentView(R.layout.activity_main);
        mContext = this;
        mHandler = new MyShareHandler();
    }

    /**
     * 点击弹出分享对话框（按钮属性）
     */
    public void showShareDialog(View view) {
        showDialog();
    }

    private void showDialog() {
        mShareDialog = new ShareDialog(this);
        mShareDialog.setCancelButtonOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mShareDialog.dismiss();
            }
        });
        addListener();

    }

    private void addListener() {
        mShareDialog.setOnItemClickListener(new ShareListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position, Object obj) {
                HashMap<String, Object> item = (HashMap<String, Object>) obj;
                //2、设置分享内容
                Platform.ShareParams sp = new Platform.ShareParams();
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                sp.setTitle("我是分享标题");
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                sp.setTitleUrl("http://www.baidu.com");
                // text是分享文本，所有平台都需要这个字段
                sp.setText("我是分享文本，啦啦啦~http://uestcbmi.com/"); //分享文本
                // imageUrl是网络图片的路径，Linked-In以外的平台都支持此参数
                sp.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul 新浪微博需要高级权限
                // url仅在微信（包括好友和朋友圈）中使用 点击后的链接网址
                sp.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                sp.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                sp.setSite("爱卡汽车");
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                sp.setSiteUrl("http://www.xcar.com.cn/");

                if (item.get("ItemText").equals("微博")) {
                    //非常重要：获取平台对象
                    Platform sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                    // 设置分享事件回调
                    sinaWeibo.setPlatformActionListener(MainActivity.this);
                    // 执行分享
                    sinaWeibo.share(sp);
                } else if (item.get("ItemText").equals("微信")) {
                    //非常重要：一定要设置分享属性
                    sp.setShareType(Platform.SHARE_WEBPAGE);

                    Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                    wechat.setPlatformActionListener(MainActivity.this);
                    wechat.share(sp);

                } else if (item.get("ItemText").equals("朋友圈")) {
                    //非常重要：一定要设置分享属性
                    sp.setShareType(Platform.SHARE_WEBPAGE);

                    Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
                    wechatMoments.setPlatformActionListener(MainActivity.this);
                    wechatMoments.share(sp);
                } else if (item.get("ItemText").equals("QQ")) {
                    Platform qq = ShareSDK.getPlatform(QQ.NAME);
                    qq.setPlatformActionListener(MainActivity.this); // 设置分享事件回调
                    qq.share(sp);

                } else if (item.get("ItemText").equals("QQ空间")) {
                    Platform qZone = ShareSDK.getPlatform(QZone.NAME);
                    qZone.setPlatformActionListener(MainActivity.this); // 设置分享事件回调
                    qZone.share(sp);
                }
                mShareDialog.dismiss();
            }
        });

    }

    /******************分割线：分享回调监听**************************/

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message msg = Message.obtain();
        msg.what = MSG_SHARE_SUCCESS;
        if (platform.getName().equals(SinaWeibo.NAME)) {// 判断成功的平台是不是新浪微博
            msg.obj = "微博分享成功";
        } else if (platform.getName().equals(Wechat.NAME)) {
            msg.obj = "微信分享成功";
        } else if (platform.getName().equals(WechatMoments.NAME)) {
            msg.obj = "朋友圈分享成功";
        } else if (platform.getName().equals(QQ.NAME)) {
            msg.obj = "QQ分享成功";
        }else if (platform.getName().equals(QZone.NAME)) {
            msg.obj = "QQ空间分享成功";
        }
        mHandler.sendMessage(msg);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        throwable.printStackTrace();
        Message msg = new Message();
        msg.what = MSG_SHARE_ERROR;
        msg.obj = throwable;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        mHandler.sendEmptyMessage(MSG_SHARE_CANCEL);
    }


    /***********************分割线：分享回调后消息处理**************/
    private class MyShareHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHARE_SUCCESS:
                    Toast.makeText(mContext, (String)msg.obj, Toast.LENGTH_LONG).show();
                    break;
                case MSG_SHARE_ERROR:
                    String expName = msg.obj.getClass().getSimpleName();
                    if ("WechatClientNotExistException".equals(expName)
                            || "WechatTimelineNotSupportedException".equals(expName)
                            || "WechatFavoriteNotSupportedException".equals(expName)) {
                        int resId = getStringRes(getApplicationContext(), "ssdk_wechat_client_inavailable");
                        if (resId > 0) {
                            showNotification(mContext.getString(resId));
                            return;
                        }
                    }else{
                        showNotification("分享失败");
                    }
                    break;
                case MSG_SHARE_CANCEL:
                    //分享取消的处理  目前未使用 等待后续添加
                    break;
                default:
                    break;
            }
        }
    }

    private void showNotification(String string) {
        Toast.makeText(mContext, string, Toast.LENGTH_LONG).show();
    }
}
