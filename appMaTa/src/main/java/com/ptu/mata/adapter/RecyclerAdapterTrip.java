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
import com.ptu.mata.bean.MainBean;
import com.ptu.mata.widget.GlideCircleTransform;


/**
 * Created by llpAndroid on 2016/10/29  17:05.
 */

public class RecyclerAdapterTrip extends BaseRecyclerAdapter<MainBean.ItemsBean> implements View.OnClickListener {

    Context mContex;
    int size;
    int position;//设置记录点击的位置
    //设置接口回调
    private onClickCallBack onclickCallBack;
    public void setOnclickCallBack(onClickCallBack onclickCallBack) {
        this.onclickCallBack = onclickCallBack;
    }



    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        mContex=parent.getContext();
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_souye, parent, false);
        return new MyHolder(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, final int RealPosition, MainBean.ItemsBean data) {
        //Log.e("自定义标签", "类名:RecyclerAdapterTrip" + "方法名：onBind: "+data.getDestination());
        if(viewHolder instanceof MyHolder) {
        MyHolder holder = (MyHolder) viewHolder;
        holder.name.setText(data.getNickname());
            if (data.getGender().equals("女")) {
                holder.age2.setText(data.getAge()+"岁");
                holder.age.setVisibility(View.GONE);
                holder.age2.setVisibility(View.VISIBLE);
            }else {
                holder.age.setText(data.getAge()+"岁");
                holder.age2.setVisibility(View.GONE);
                holder.age.setVisibility(View.VISIBLE);
            }

        holder.time.setText(data.getCreated());
        holder.context.setText(data.getRemark());
        holder.Destination.setText(". 出发去:"+data.getDestination());
        holder.Require.setText(". 相伴要求:"+data.getRequire());
        holder.MoneyType.setText(". 费用:"+data.getMoneyType());
        holder.like.setText(data.getYueyouLikeCount()+"");
        holder.msg.setText(data.getYueyouReplyCount()+"");

           /*
           item点击事件在BaseAdapter中已经有了
           holder.itemView.setTag(RealPosition);
            holder.itemView.setOnClickListener(this);*/

            /*setTag有2个参数的，第一个参数写资源id,  */

            holder.ivHead.setTag(R.id.iv_head,RealPosition);
            holder.ivHead.setOnClickListener(this);

            holder.like.setTag(holder.like.getId(),RealPosition);
            holder.like.setOnClickListener(this);

            holder.msg.setTag(holder.msg.getId(),RealPosition);
            holder.msg.setOnClickListener(this);

            holder.join.setTag(holder.join.getId(),RealPosition);
            holder.join.setOnClickListener(this);


            holder.iv1.setTag(holder.iv1.getId(),RealPosition);
            holder.iv1.setOnClickListener(this);

            holder.iv2.setTag(holder.iv2.getId(),RealPosition);
            holder.iv2.setOnClickListener(this);

            holder.iv3.setTag(holder.iv3.getId(),RealPosition);
            holder.iv3.setOnClickListener(this);



        //加载图片
        //Picasso.with(mContex).load(data.getHeadUrl()).transform(new CircleTransform()).into(holder.ivHead);
        Glide.with(mContex).load(data.getHeadUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(mContex)).into(holder.ivHead);
            if (data.getPicList()==null) {
                holder.iv1.setVisibility(View.GONE);
                holder.iv2.setVisibility(View.GONE);
                holder.iv3.setVisibility(View.GONE);
                return;

            }
        size = data.getPicList().size();
        if (size==1) {
            //Picasso.with(mContex).load(data.getPicList().get(0).getUrl()).into(holder.iv1);
            Glide.with(mContex).load(data.getPicList().get(0).getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv1);
            holder.iv2.setVisibility(View.GONE);
            holder.iv3.setVisibility(View.GONE);
        }else if(size==2){
            Glide.with(mContex).load(data.getPicList().get(0).getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv1);
            Glide.with(mContex).load(data.getPicList().get(1).getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv2);
            holder.iv2.setVisibility(View.VISIBLE);
            holder.iv3.setVisibility(View.GONE);
        }else if(size>2){
            holder.iv2.setVisibility(View.VISIBLE);
            holder.iv3.setVisibility(View.VISIBLE);
            Glide.with(mContex).load(data.getPicList().get(0).getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv1);
            Glide.with(mContex).load(data.getPicList().get(1).getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv2);
            Glide.with(mContex).load(data.getPicList().get(2).getUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv3);
        }

        }
    }
//MainActivity实现的监听
    @Override
    public void onClick(View v) {

       switch (v.getId()){
           case R.id.iv_head:{
               if(onclickCallBack!=null) {
                   position = (int) v.getTag(v.getId());
                   onclickCallBack.onHeadClick(v, position);
                   //Log.e("自定义标签", "类名:RecyclerAdapterTrip" + "方法名：onClick头像点击: "+position);
               }
           }break;
           case R.id.iv1:{
               if(onclickCallBack!=null) {
                   position = (int) v.getTag(v.getId());
                   onclickCallBack.onImgClick(v, position);
                   // Log.e("自定义标签", "类名:RecyclerAdapterTrip" + "方法名：onClick: 图1"+position);
               }
           }
           break;
           case R.id.iv2:{
               if(onclickCallBack!=null) {
               position= (int) v.getTag(v.getId());
               onclickCallBack.onImgClick(v,position);
               //Log.e("自定义标签", "类名:RecyclerAdapterTrip" + "方法名：onClick: 图1"+position);
                   }
           }
           break;
           case R.id.iv3:{
               if(onclickCallBack!=null) {
                   position = (int) v.getTag(v.getId());
                   onclickCallBack.onImgClick(v, position);
                   //Log.e("自定义标签", "类名:RecyclerAdapterTrip" + "方法名：onClick: 图1"+position);
               }
           }
           break;
           case R.id.tv_like:{
               if(onclickCallBack!=null) {
                   position = (int) v.getTag(v.getId());
                   onclickCallBack.onLikeClick(v, position);
               }
           }break;
           case R.id.tv_comment:{
               if(onclickCallBack!=null) {
                   position = (int) v.getTag(v.getId());
                   onclickCallBack.onCommentClick(v, position);
               }
           }break;
           case R.id.tv_join:{
               if(onclickCallBack!=null) {
                   position = (int) v.getTag(v.getId());
                   onclickCallBack.onJoinClick(v, position);
               }
           }break;

       }

      /*  position= (int) v.getTag(v.getId());
        Log.e("自定义标签", "类名:RecyclerAdapterTrip" + "方法名：onClick: "+position);
        onclickCallBack.onItemClick(v,position);*/
    }

    class MyHolder extends BaseRecyclerAdapter.Holder {
        ImageView ivHead,iv1,iv2,iv3;
        TextView name,age,age2,time,context,Destination,Require,MoneyType,like,msg,join;
        public MyHolder(View itemView) {
            super(itemView);

            ivHead= (ImageView) itemView.findViewById(R.id.iv_head);
            iv1= (ImageView) itemView.findViewById(R.id.iv1);
            iv2= (ImageView) itemView.findViewById(R.id.iv2);
            iv3= (ImageView) itemView.findViewById(R.id.iv3);
            name= (TextView) itemView.findViewById(R.id.name);
            age= (TextView) itemView.findViewById(R.id.tv_man);
            age2= (TextView) itemView.findViewById(R.id.tv_woman);
            time= (TextView) itemView.findViewById(R.id.tv_time);
            context= (TextView) itemView.findViewById(R.id.tv_context);
            Destination= (TextView) itemView.findViewById(R.id.Destination);
            Require= (TextView) itemView.findViewById(R.id.Require);
            MoneyType= (TextView) itemView.findViewById(R.id.MoneyType);
            like= (TextView) itemView.findViewById(R.id.tv_like);
            msg= (TextView) itemView.findViewById(R.id.tv_comment);
            join= (TextView) itemView.findViewById(R.id.tv_join);

        }
    }
    //设置接口回调接口
    public interface onClickCallBack{
        void onHeadClick(View v,int position);//头部点击事件
        //void onItemClick(View v,int position);//整个Item的点击事件
        void onImgClick(View v,int position);
        void onLikeClick(View v,int position);  //喜欢
        void onCommentClick(View v,int position);  //评论
        void onJoinClick(View v,int position);  //加入

    }

}
