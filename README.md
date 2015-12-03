###ShareSDK V2.6.5 的集成Demo 
1. 首先项目依赖 MOB官方的库文件 我命名为 sharesdklibs
2. 创建assets资源文件 放置ShareSDK.xml 这是分享的配置文件
	（注意文件中需要填写在MOB注册的AppKey 以及需要分享的各个平台的key keysecret）
3. 资源文件中有layout_share_content.xml 分享布局
4. layout_ssdk_content.xml 这是对话框采用的内容布局
5. values中ssdk_strings.xml 中定义了一下分享后的提示内容

###下面介绍主要类
1. ShareListAdapter 用于分享的列表适配
2. OneKeyShare 用于一键分享 会弹出对话框
	`OneKeyShare oks = new OneKeyShare();`
    `oks.setTitle("自定义快速分享");`
	`oks.show(this);`
3. ShareLayoutUtil 这个是用于绑定布局来为布局添加分享回调，如果你的应用程序要集成我这个分享，可以直接将layout_share_content.xml引导你要放置分享布局的位置。Activity中查找到该布局中的ReeycleListView，调用injectLayout即可。
	`RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rlv_share_list);`
    `ShareLayoutUtil shareUtil = new ShareLayoutUtil(this);`
    `shareUtil.setText("哈哈分享测试");`
    `shareUtil.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");`
    `shareUtil.injectLayout(recyclerView);`
    `shareUtil.setOnShareClickListener(new ShareLayoutUtil.OnShareClickListener() {
            @Override
            public void onShareClick(View view, int position, Object object) {
                Toast.makeText(MainActivity.this,"点击",Toast.LENGTH_SHORT).show();
            }
        });`
4. ShareDialog设计了一个Dialog用于分享弹出
5. WXEntryActivity是微信分享必须添加的一个回调页面
6. MainActivity程序入口