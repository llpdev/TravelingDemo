package com.ptu.mata.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr-Hei on 2016/10/28.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    //数据集合,初始化，防止基类报集合获得长度异常
    public List<T> list = new ArrayList<>();

    //提供点击条目的接口
    public interface myItemClickListener {
        void myItemClick(View view, int position);
    }

    //接口对象
    public myItemClickListener myItemClickListener;

    //提供方法设置条目点
    public void setOnItemClickListener(myItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }


    //为集合设置数据
    public void setData(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //条目的类型
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;


    public View mHeaderView;


    //添加头部的方法
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    //删除头布局
    public void removeHeaderView() {
        mHeaderView = null;
        notifyItemRemoved(0);
    }


    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    //得到条目的位置
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }


}
