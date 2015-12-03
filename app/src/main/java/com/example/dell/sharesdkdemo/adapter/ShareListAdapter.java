package com.example.dell.sharesdkdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dell.sharesdkdemo.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ychy
 * 2015/12/2 17:52
 */
public class ShareListAdapter  extends RecyclerView.Adapter<ShareListAdapter.ViewHolder> {
    private LayoutInflater mInflater;
//    private int[] mImgIds;
//    private String[] mShareNames;
    private List<HashMap<String, Object>> mShareList;

    public ShareListAdapter(Context context, List<HashMap<String, Object>> shareList)
    {
        mInflater = LayoutInflater.from(context);
        mShareList = shareList;
    }

    @Override
    public int getItemCount()
    {
        return mShareList.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.item_ssdk_list,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.iv_item_share_image);
        viewHolder.mTxt = (TextView) view
                .findViewById(R.id.tv_item_share_name);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        viewHolder.mImg.setImageResource((int) mShareList.get(i).get("ItemImage"));
        viewHolder.mTxt.setText((String) mShareList.get(i).get("ItemText"));
        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, i,mShareList.get(i));
                }
            });

        }
    }


    /**
     * 创建适配器
     */
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View view)
        {
            super(view);
        }
        ImageView mImg;
        TextView mTxt;
    }

    private OnItemClickLitener mOnItemClickLitener;
    /**
     * ItemClick的回调接口
     * @author yangchunyu
     *
     */
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position,Object object);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
