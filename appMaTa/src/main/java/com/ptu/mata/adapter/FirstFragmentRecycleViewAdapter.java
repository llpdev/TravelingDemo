package com.ptu.mata.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptu.mata.R;
import com.ptu.mata.widget.MyReFreshRecycleView;

import java.util.List;

/**
 * Created by SinMin on 2016/10/31
 */

//佛曰七苦：生、老、病、死、爱别离、怨憎会、求不得。

//故事的开头总是这样，适逢其会，猝不及防。
//故事的结局总是这样，花开两朵，天各一方。

//世事茫茫，光阴有限，算来何必奔忙！
//人生碌碌，竞短论长，却不道荣枯有数，得失难量。

public class FirstFragmentRecycleViewAdapter extends RecyclerView.Adapter<FirstFragmentRecycleViewAdapter.MyHolder> implements View.OnClickListener {
    private List<String> list;
    private onItemClickListener onItemClickListener;

    public FirstFragmentRecycleViewAdapter(List<String> list) {
        this.list = list;
    }
    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = View.inflate(parent.getContext(),R.layout.firstfragment_imageview_item_cardview,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(list.get(position));
        holder.itemView.setTag(holder);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener!=null) {
            onItemClickListener.onItemClick(v,((MyHolder)v.getTag()).getLayoutPosition());
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.cardview_textview);
        }
    }
    public interface onItemClickListener {
        void onItemClick(View view, int postion);
    }
}
