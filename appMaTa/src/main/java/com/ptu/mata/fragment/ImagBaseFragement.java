package com.ptu.mata.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ptu.mata.R;

/**
 * Created by llpAndroid on 2016/11/5  11:24.
 */

public class ImagBaseFragement extends Fragment {

    ImageView iv_PhotoView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imgfragment, container, false);
        iv_PhotoView= (ImageView) view.findViewById(R.id.iv_PhotoView);

        //获取传入的参数，根据参数构造不同的数据
        Bundle bundle = getArguments();
        String values = bundle.getString("key");

        Glide.with(getContext()).load(values).into(iv_PhotoView);

        return view;
    }
}
