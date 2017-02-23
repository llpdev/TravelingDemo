package com.ptu.mata.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.ptu.mata.R;
import com.ptu.mata.adapter.BaseRecyclerAdapter;
import com.ptu.mata.adapter.RecyclerAdapterTrip;
import com.ptu.mata.bean.MainBean;
import com.ptu.mata.presenter.DetailPresenter;
import com.ptu.mata.view.DetailView;
import com.ptu.mata.widget.MyReFreshRecycleView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity  extends BaseActivity<DetailPresenter> implements DetailView {
    private MyReFreshRecycleView myReFreshRecycleView;
    private RecyclerView mRecyclerView;
    List<MainBean.ItemsBean> list;
    RecyclerAdapterTrip adapterTrip;
    int page=1;
    private String value;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        value = intent.getStringExtra("key");



        //调用presenter对象里面加载数据的方法

        mPresenter.loadData("丽江",1);
        mPresenter.loadData(value,page);
        initView();//初始化
        tv.setText(value);
        //设置适配器
        adapterTrip=new RecyclerAdapterTrip();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        //设置适配器
        mRecyclerView.setAdapter(adapterTrip);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置动画效果


        initEvent();//设置事件

    }

    private void initEvent() {
        //接口回调设置点击事件
        adapterTrip.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Log.e("自定义标签", "类名:FirstFragment" + "方法名：onItemClick: "+position);
            }
        });

        //在这个方法里加载数据
        myReFreshRecycleView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                //下拉刷新
                mPresenter.loadData(value,1);
                myReFreshRecycleView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                //加载更多
                mPresenter.loadData(value,++page);
                myReFreshRecycleView.onRefreshComplete();

            }
        });
    }

    private void initView() {
        list=new ArrayList<>();
        myReFreshRecycleView= (MyReFreshRecycleView) findViewById(R.id.myRecycleView2);
        myReFreshRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
        mRecyclerView = myReFreshRecycleView.getRefreshableView();
        tv = (TextView) findViewById(R.id.text);
    }

    @Override
    public DetailPresenter creatPresenter() {
        return new DetailPresenter();
    }

    @Override
    public void showData(MainBean mainBean) {
        Log.e("自定义标签", "1111：" + mainBean.getItems().get(0).getDestination());
        list.addAll(mainBean.getItems());
        adapterTrip.addDatas((ArrayList<MainBean.ItemsBean>) list);
        adapterTrip.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
