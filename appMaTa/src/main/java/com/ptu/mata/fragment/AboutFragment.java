package com.ptu.mata.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ptu.mata.R;
import com.ptu.mata.view.activity.Web2Activity;

import java.lang.reflect.Field;

/**
 * Created by SinMin on 2016/10/28
 */

//佛曰七苦：生、老、病、死、爱别离、怨憎会、求不得。

//故事的开头总是这样，适逢其会，猝不及防。
//故事的结局总是这样，花开两朵，天各一方。

//世事茫茫，光阴有限，算来何必奔忙！
//人生碌碌，竞短论长，却不道荣枯有数，得失难量。

public class AboutFragment extends Fragment {
    private LinearLayout ll_git;
    private LinearLayout ll_weibo;
    private LinearLayout ll_git_xmdz;
    private LinearLayout ll_csdn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messageframentlayout,container,false);
        ll_git = (LinearLayout)view.findViewById(R.id.ll_git);
        ll_weibo = (LinearLayout)view.findViewById(R.id.ll_weibo);
        ll_git_xmdz = (LinearLayout)view.findViewById(R.id.ll_git_xmdz);
        ll_csdn = (LinearLayout)view.findViewById(R.id.ll_csdn);
        initState(view);//设置沉浸
        addListener();
        return  view;
    }

    private void addListener() {
        ll_git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               jumpActivity("https://github.com/llpAndroid");
            }
        });
        ll_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity("http://weibo.com/p/1005053816716541");
            }
        });
        ll_git_xmdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity("https://github.com/sfsheng0322/StickyHeaderListView");
            }
        });
        ll_csdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity("http://my.csdn.net/liliangpin");
            }
        });
    }

    private void jumpActivity(String url) {
        Intent intent = new Intent(getActivity(), Web2Activity.class);
        intent.putExtra("jump",url);
        startActivity(intent);
    }

    /**
     * 动态的设置状态栏  实现沉浸式状态栏
     *
     */
    private void initState(View view) {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            LinearLayout linear_bar = (LinearLayout) view.findViewById(R.id.ll_bar);
            linear_bar.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = getStatusBarHeight();
            //动态的设置隐藏布局的高度
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
            params.height = statusHeight;
            linear_bar.setLayoutParams(params);
        }
    }
    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
