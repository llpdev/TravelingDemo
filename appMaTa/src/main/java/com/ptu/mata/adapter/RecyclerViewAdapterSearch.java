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
import com.ptu.mata.bean.SearchBean;

/**
 * Created by Mr-Hei on 2016/11/1.
 */

public class RecyclerViewAdapterSearch extends BaseRecyclerViewAdapter<SearchBean.ItemsBean> {
    Context mContext;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (getHeaderView() != null && TYPE_HEADER == viewType) {
            return new MyViewHolderSearch(getHeaderView());
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_serach, parent, false);
        return new MyViewHolderSearch(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        int realPosition = getRealPosition(holder);
        if (holder instanceof MyViewHolderSearch) {
            {
                MyViewHolderSearch loacal = (MyViewHolderSearch) holder;
                //在这里绑定数据，和设置条目点击事件
                loacal.tv1.setText(list.get(realPosition).getCityName());
                //loacal.tv2.setText(list.get(realPosition).getYueyouCount());
                loacal.tv3.setText(list.get(realPosition).getResName());
                loacal.tv4.setText(list.get(realPosition).getResGrade());
                loacal.tv5.setText(list.get(realPosition).getAddress());
                loacal.tv6.setText(list.get(realPosition).getMinPrice() + "");
                loacal.tv7.setText(list.get(realPosition).getOriginalPrice() + "");
                //设置图片
                Glide.with(mContext).load(list.get(realPosition).getDefaultImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(loacal.iv);
                //标记被点击条目
                loacal.itemView.setTag(loacal);
                loacal.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myItemClickListener.myItemClick(v, ((MyViewHolderSearch) v.getTag()).getLayoutPosition());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderView() == null ? list.size() : list.size() + 1;
    }

    class MyViewHolderSearch extends RecyclerView.ViewHolder {
        //条目对象
        TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
        ImageView iv;

        public MyViewHolderSearch(View itemView) {
            super(itemView);
            if (itemView == getHeaderView()) {
                return;
            }
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
            tv3 = (TextView) itemView.findViewById(R.id.tv3);
            tv4 = (TextView) itemView.findViewById(R.id.tv4);
            tv5 = (TextView) itemView.findViewById(R.id.tv5);
            tv6 = (TextView) itemView.findViewById(R.id.tv6);
            tv7 = (TextView) itemView.findViewById(R.id.tv7);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
