package com.ptu.mata.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.j256.ormlite.dao.Dao;
import com.ptu.mata.R;
import com.ptu.mata.bean.UserBean;
import com.ptu.mata.view.activity.LoginActivity;
import com.ptu.mata.view.activity.MainActivity;
import com.ptu.mata.widget.UserDataBaseOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SinMin on 2016/10/29
 */

//佛曰七苦：生、老、病、死、爱别离、怨憎会、求不得。

//故事的开头总是这样，适逢其会，猝不及防。
//故事的结局总是这样，花开两朵，天各一方。

//世事茫茫，光阴有限，算来何必奔忙！
//人生碌碌，竞短论长，却不道荣枯有数，得失难量。

public class MineSettingFragment  extends Fragment{
    private Button setting_outlogin;
    private ImageView setting_back;
    List<UserBean> datas;
    Dao<UserBean,Integer> userDao;//数据库操作类
    UserDataBaseOpenHelper mOpenHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_setting_layout,container,false);
        setting_outlogin = (Button)view.findViewById(R.id.setting_outlogin);
        setting_back = (ImageView)view.findViewById(R.id.setting_back);
        loadDate();
        addListener();
        return view;
    }

    private void loadDate() {
        datas=new ArrayList<>();
        mOpenHelper=UserDataBaseOpenHelper.getInstance(getActivity());
        try {
            userDao=mOpenHelper.getDao(UserBean.class);//获取数据库操作类
            List<UserBean> users = userDao.queryForAll();
            datas.addAll(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addListener() {
        setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        setting_outlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outlogin();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
    private void outlogin() {

        for (int i = 0; i < datas.size(); i++) {
            UserBean userBean = new UserBean();
            userBean=datas.get(i);

            try {
                userDao.deleteById((int) userBean.get_id());
                userDao.delete(userBean);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
