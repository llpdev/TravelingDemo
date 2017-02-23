package com.ptu.mata.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ptu.mata.R;
import com.ptu.mata.adapter.RecyclerViewAdapterCity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button bt_cancle;
    //装载数据的集合
    List<String> mData;
    //适配器
    RecyclerViewAdapterCity mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        //初始化集合
        initData();
        //初始化recycler
        initRecycler();

    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        //适配器
        mAdapter = new RecyclerViewAdapterCity();
        mAdapter.setData(mData);
        //设置适配器
        recyclerView.setAdapter(mAdapter);
        //对条目设置点击监听
        mAdapter.setOnItemClickListener(new RecyclerViewAdapterCity.ItemClcikListener() {
            @Override
            public void OnItemClickListener(View v, int position) {
                Log.e("tag", "点击了条目");
            }
        });
    }

    private void initData() {
        mData = new ArrayList<>();
        mData.add("丽江");
        mData.add("大理");
        mData.add("拉萨");
        mData.add("青海");
        mData.add("成都");
        mData.add("九寨沟");
        mData.add("三亚");
        mData.add("泸沽湖");
        mData.add("香格里...");
        mData.add("厦门");
        mData.add("杭州");
        mData.add("西塘");
        mData.add("凤凰");
        mData.add("泰国");
        mData.add("普吉");
        mData.add("巴厘岛");
        mData.add("韩国");
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        bt_cancle = (Button) findViewById(R.id.bt_cacle);
    }
}
