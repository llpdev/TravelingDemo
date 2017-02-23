package com.ptu.mata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ptu.mata.R;
import com.ptu.mata.bean.LocalBean;

/**
 * Created by Mr-Hei on 2016/10/28.
 */

public class RecyclerViewAdapterLocal extends BaseRecyclerViewAdapter<LocalBean.ItemsBean> {
    Context mContext;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (getHeaderView() != null && viewType == TYPE_HEADER) {
            return new MyViewHolderLoacal(getHeaderView());
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_community, parent, false);
        return new MyViewHolderLoacal(view);

    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? list.size() : list.size() + 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        //获得除头布局以外的其他条目
        int realPosition = getRealPosition(holder);
        if (holder instanceof MyViewHolderLoacal) {
            {
                final MyViewHolderLoacal loacal = (MyViewHolderLoacal) holder;
                //在这里绑定数据，和设置条目点击事件
                loacal.tv_local_name.setText(list.get(realPosition).getName());
                loacal.tv_yueyoucount.setText(list.get(realPosition).getYueyouCount() + "人在此结伴");
                loacal.tv_ComResCount.setText(list.get(realPosition).getComResCount() + "家特惠酒店");
                //设置图片
                Glide.with(mContext).load(list.get(realPosition).getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(loacal.iv_background);
                //标记被点击条目
                loacal.itemView.setTag(loacal);
                loacal.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myItemClickListener.myItemClick(v, ((MyViewHolderLoacal) v.getTag()).getLayoutPosition());
                    }
                });
            }
        }
    }

    class MyViewHolderLoacal extends RecyclerView.ViewHolder {
        ImageView iv_background;
        TextView tv_local_name, tv_yueyoucount, tv_ComResCount;
        //在这里定义头布局里面的控件

        public MyViewHolderLoacal(View itemView) {
            super(itemView);
            if (itemView == getHeaderView()) {
                //如果是头布局直接返回
                return;
            }
            iv_background = (ImageView) itemView.findViewById(R.id.iv_background);
            tv_local_name = (TextView) itemView.findViewById(R.id.tv_local_name);
            tv_yueyoucount = (TextView) itemView.findViewById(R.id.tv_yueyoucount);
            tv_ComResCount = (TextView) itemView.findViewById(R.id.tv_ComResCount);
        }
    }
}


