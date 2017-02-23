package com.ptu.mata.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.ptu.mata.R;
import com.ptu.mata.adapter.MyFragmentPagerAdapter;
import com.ptu.mata.bean.MainBean;
import com.ptu.mata.fragment.ImagBaseFragement;

import java.util.ArrayList;
import java.util.List;

public class ImgFragmentActivity extends FragmentActivity {

    List<String> picUrl;//图片地址集合
    //数据声明
    List<Fragment> fragments;
    MyFragmentPagerAdapter adapter;
    ViewPager viewPager;
    private TextView tv;
    /*ArrayList<MainBean.ItemsBean.PicListBean> picsList;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
       // picsList=new ArrayList<>();
        tv = (TextView) findViewById(R.id.tv);
        Intent intent = getIntent();

        int type = intent.getIntExtra("type", -1);
        Log.e("自定义标签", "类名:ImgFragmentActivity" + "方法名：onCreate: 类型"+type);


        //ArrayList<MainBean.ItemsBean> picsList = intent.getBundleExtra("bundle").getParcelable("pics");
        final MainBean.ItemsBean itemsBean = intent.getBundleExtra("bundle").getParcelable("pics");
        Log.e("自定义标签", "类名:ImgFragmentActivity" + "方法名：onCreate: "+ itemsBean.getPicList().size());

        tv.setText((type+1)+"/"+itemsBean.getPicList().size());
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        picUrl=new ArrayList<>();
        for (int i = 0; i < itemsBean.getPicList().size(); i++) {
            //picUrl.add(picsList.get(i).getUrl());
            picUrl.add(itemsBean.getPicList().get(i).getUrl());
        }

        fragments=new ArrayList<>();
        //根据图片的数量创建对应的Fragment
        for (int i = 0; i < itemsBean.getPicList().size(); i++) {
            //创建一个新的Fragment
            ImagBaseFragement ibf = new ImagBaseFragement();
            //传入参数，以便构造不同的数据
            Bundle bundle = new Bundle();
            bundle.putString("key", picUrl.get(i));
            ibf.setArguments(bundle);
            //添加到Fragment集合中
            fragments.add(ibf);
        }
        //创建适配器
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(type);
        //对viewPager做监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                Log.e("自定义标签", "类名:ImgFragmentActivity" + "方法名：onPageSelected: 333:"+position);
                //int currentItem = viewPager.getCurrentItem();
                tv.setText((position+1)+"/"+itemsBean.getPicList().size());
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

}
