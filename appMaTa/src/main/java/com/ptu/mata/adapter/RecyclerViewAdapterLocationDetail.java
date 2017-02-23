package com.ptu.mata.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ptu.mata.R;
import com.ptu.mata.bean.LocationDetailBean;

/**
 * Created by Mr-Hei on 2016/10/31.
 */

public class RecyclerViewAdapterLocationDetail extends BaseRecyclerViewAdapter<LocationDetailBean.ItemsBean.ComResBean> {
    Context mContext;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (getHeaderView() != null && viewType == TYPE_HEADER) {
            return new MyViewHolderLocation(getHeaderView());
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new MyViewHolderLocation(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            //设置头布局的点击事件
            holder.itemView.setTag(holder);
            Log.e("tag", "头布局的位置" + position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myItemClickListener.myItemClick(v, ((RecyclerView.ViewHolder) v.getTag()).getLayoutPosition());
                }
            });
            return;
        }
        //获得除头布局以外的其他条目
        int realPosition = getRealPosition(holder);
        if (holder instanceof MyViewHolderLocation) {
            {
                MyViewHolderLocation loacal = (MyViewHolderLocation) holder;
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
                        myItemClickListener.myItemClick(v, ((MyViewHolderLocation) v.getTag()).getLayoutPosition());
                    }
                });
            }
        }

    }


    @Override
    public int getItemCount() {
        return mHeaderView == null ? list.size() : list.size() + 1;
    }


    class MyViewHolderLocation extends RecyclerView.ViewHolder {
        //条目对象
        TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
        ImageView iv;

        public MyViewHolderLocation(View itemView) {
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
