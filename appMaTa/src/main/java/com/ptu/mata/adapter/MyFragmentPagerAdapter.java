package com.ptu.mata.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by llpAndroid on 2016/11/5  11:39.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {


    //需要的数据
    List<Fragment> data;
    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        data=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
    }
}
