package com.ptu.mata.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.j256.ormlite.dao.Dao;
import com.ptu.mata.R;
import com.ptu.mata.bean.UserBean;
import com.ptu.mata.widget.GlideCircleTransform;
import com.ptu.mata.widget.UserDataBaseOpenHelper;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SinMin on 2016/10/28
 */

//佛曰七苦：生、老、病、死、爱别离、怨憎会、求不得。

//故事的开头总是这样，适逢其会，猝不及防。
//故事的结局总是这样，花开两朵，天各一方。

//世事茫茫，光阴有限，算来何必奔忙！
//人生碌碌，竞短论长，却不道荣枯有数，得失难量。

public class MineFragment extends Fragment {
    private ImageView mine_header;
    private TextView mine_name;
    private LinearLayout mine_setting;
    List<UserBean> datas;
    Dao<UserBean, Integer> userDao;//数据库操作类
    UserDataBaseOpenHelper mOpenHelper;
    String name;
    String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.minefragmentlayout, container, false);
        mine_header = (ImageView) view.findViewById(R.id.mine_header);
        mine_name = (TextView) view.findViewById(R.id.mine_name);
        mine_setting = (LinearLayout) view.findViewById(R.id.mine_setting);
        addListener();
        initState(view);//设置沉浸
        datas = new ArrayList<>();
        mOpenHelper = UserDataBaseOpenHelper.getInstance(getActivity());
        try {
            userDao = mOpenHelper.getDao(UserBean.class);//获取数据库操作类
            List<UserBean> users = userDao.queryForAll();
            datas.addAll(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setName();


        mine_name.setText(name);
        Glide.with(getActivity()).load(url).transform(new GlideCircleTransform(getContext())).into(mine_header);

        return view;
    }

    private void setName() {
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).islogin() == true) {
                name = datas.get(i).getName();
                url = datas.get(i).getUrl();
                return;
            }
        }
    }

    private void addListener() {
        mine_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.activity_main, new MineSettingFragment()).commit();
            }
        });
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
