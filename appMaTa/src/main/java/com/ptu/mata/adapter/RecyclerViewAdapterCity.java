package com.ptu.mata.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptu.mata.R;

import java.util.List;

/**
 * Created by Mr-Hei on 2016/11/5.
 */

public class RecyclerViewAdapterCity extends RecyclerView.Adapter<RecyclerViewAdapterCity.MyViewHolderCity> {

    List<String> data;

    @Override
    public MyViewHolderCity onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new MyViewHolderCity(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderCity holder, int position) {
        //赋值并设置点击监听
        holder.tv.setText(data.get(position));
        holder.itemView.setTag(holder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClcikListener.OnItemClickListener(v, ((MyViewHolderCity) v.getTag()).getLayoutPosition());
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    //设置集合的值
    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class MyViewHolderCity extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolderCity(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_cityname);
        }
    }

    private ItemClcikListener itemClcikListener;

    //接口回调
    public interface ItemClcikListener {
        void OnItemClickListener(View v, int position);
    }

    //调用方法
    public void setOnItemClickListener(ItemClcikListener itemClcikListener) {
        this.itemClcikListener = itemClcikListener;
    }
}
