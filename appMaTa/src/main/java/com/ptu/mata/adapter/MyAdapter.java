package com.ptu.mata.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by llpAndroid on 2017/2/18.
 */

public class MyAdapter extends PagerAdapter {
    List<View> list;
    public MyAdapter(List<View> list) {
        this.list = list;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (list.get(position).getParent() != null) {
            ((ViewGroup) list.get(position).getParent()).removeView(list.get(position));
        }
        container.addView(list.get(position));
        return list.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}