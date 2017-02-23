package com.ptu.mata.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.ptu.mata.R;
import com.ptu.mata.adapter.BaseRecyclerViewAdapter;
import com.ptu.mata.adapter.RecyclerViewAdapterLocationDetail;
import com.ptu.mata.bean.LocationDetailBean;
import com.ptu.mata.presenter.LocationDetailPresenter;
import com.ptu.mata.view.LocationView;
import com.ptu.mata.widget.MyReFreshRecycleView;

import java.util.ArrayList;
import java.util.List;

public class LocationDetail extends BaseActivity<LocationDetailPresenter> implements LocationView {
    List<LocationDetailBean.ItemsBean.ComResBean> mData = new ArrayList<>();

    //recyclerview的适配器
    RecyclerViewAdapterLocationDetail mAdapter;

    //recyclerview
    MyReFreshRecycleView reFreshRecycleView;

    //Recycler
    RecyclerView recyclerView;

    //退出键
    ImageView iv_back;

    //头布局里面的控件
    ImageView iv;
    TextView tv, tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);
        //初始化控件
        reFreshRecycleView = (MyReFreshRecycleView) findViewById(R.id.recycler);
        //回退按钮
        iv_back = (ImageView) findViewById(R.id.iv_back);
        //设置点击监听
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置即可上拉也可下拉
        reFreshRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
        recyclerView = reFreshRecycleView.getRefreshableView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        //初始化适配器
        mAdapter = new RecyclerViewAdapterLocationDetail();
        //设置适配器
        recyclerView.setAdapter(mAdapter);
        View headView = LayoutInflater.from(this).inflate(R.layout.item_head_location, recyclerView, false);
        mAdapter.setHeaderView(headView);
        //初始化头布局控件
        initHeadView(headView);
        //首先获得从其他活动传过来的参数
        Intent intent = getIntent();
        int area_id = intent.getIntExtra("area_id", 0);
        String area_name = intent.getStringExtra("area_name");
        String image = intent.getStringExtra("image");
        int yueyouCount = intent.getIntExtra("yueyouCount", 0);
        //给头布局控件设置值
        tv.setText(yueyouCount + "人在此结伴");
        tv_name.setText(area_name);
        Glide.with(this).load(image).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);
        //调用presenter的方法下载数据
        mPresenter.LoadData(area_id, area_name, 10);
        //给条目设置监听
        setItemClickListener();
    }

    private void setItemClickListener() {
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.myItemClickListener() {
            @Override
            public void myItemClick(View view, int position) {
                Log.e("tag", "跳转到详细内容里面");
            }
        });
    }

    private void initHeadView(View headView) {
        iv = (ImageView) headView.findViewById(R.id.iv_head);
        tv = (TextView) headView.findViewById(R.id.tv);
        tv_name = (TextView) headView.findViewById(R.id.tv_name);
    }

    @Override
    public LocationDetailPresenter creatPresenter() {
        return new LocationDetailPresenter();
    }

    @Override
    public void showData(LocationDetailBean bean) {
        mData.addAll(bean.getItems().getComRes());
        // Log.e("tag", "数据下载成功" + bean.getItems().getComRes().size());
        Log.e("tag", "数据的长度" + mData.size());
        mAdapter.setData(mData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄露
        //Log.e("tag", "销毁关联:weakReference=null");
        mPresenter.delAttach();
    }
}
