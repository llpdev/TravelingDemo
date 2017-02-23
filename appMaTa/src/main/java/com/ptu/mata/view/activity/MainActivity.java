
package com.ptu.mata.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.ptu.mata.R;
import com.ptu.mata.bean.UserBean;
import com.ptu.mata.fragment.CommunityFragment;
import com.ptu.mata.fragment.FirstFragment;
import com.ptu.mata.fragment.AboutFragment;
import com.ptu.mata.fragment.MineFragment;
import com.ptu.mata.widget.UserDataBaseOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FragmentManager supportFragmentManager;
    private List<Fragment> fragments;
    private RadioButton radiobutton_first;
    private RadioButton radiobutton_community;
    private RadioButton radiobutton_message;
    private RadioButton radiobutton_mine;
    boolean isLogin = false;
    private long timer = 0;
    private int page = 0;


    List<UserBean> datas;
    Dao<UserBean, Integer> userDao;//数据库操作类
    UserDataBaseOpenHelper mOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
    }

    private void initview() {
        datas = new ArrayList<>();
        mOpenHelper = UserDataBaseOpenHelper.getInstance(this);
        try {
            userDao = mOpenHelper.getDao(UserBean.class);//获取数据库操作类
        } catch (SQLException e) {
            e.printStackTrace();
        }
        islogin();
        fragments = new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new AboutFragment());
        fragments.add(new MineFragment());
        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_framelayout, fragments.get(0));
        fragmentTransaction.add(R.id.main_framelayout, fragments.get(1));
        fragmentTransaction.add(R.id.main_framelayout, fragments.get(2));
        fragmentTransaction.add(R.id.main_framelayout, fragments.get(3));
        fragmentTransaction.commit();
        showOne(0);
        radiobutton_first = (RadioButton) findViewById(R.id.radiobutton_first);
        radiobutton_community = (RadioButton) findViewById(R.id.radiobutton_community);
        radiobutton_message = (RadioButton) findViewById(R.id.radiobutton_message);
        radiobutton_mine = (RadioButton) findViewById(R.id.radiobutton_mine);
        setListener();
    }


    private void setListener() {
        radiobutton_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page!=0){
                    page = 0;
                    showOne(page);
                }

            }
        });
        radiobutton_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page!=1){
                    page = 1;
                    showOne(page);
                }
            }
        });
        radiobutton_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page!=2){
                    page = 2;
                    showOne(page);
                }
            }
        });
        radiobutton_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (page!=3){
                        page = 3;
                        showOne(page);
                    }
                }
            }
        });

    }

    private void hodeAll() {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.hide(fragments.get(0));
        fragmentTransaction.hide(fragments.get(1));
        fragmentTransaction.hide(fragments.get(2));
        fragmentTransaction.hide(fragments.get(3));
        fragmentTransaction.commit();
    }

    private void showOne(int i) {
        hodeAll();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.show(fragments.get(i));
        fragmentTransaction.commit();
    }


    //双击退出


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - timer > 2000) {
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                timer = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void islogin() {
        try {
            List<UserBean> users = userDao.queryForAll();
            datas.clear();
            datas.addAll(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.e("自定义", "size------: "+datas.size() );
        Log.e("自定义", "islogin: "+datas.toString() );
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).islogin() == true) {

                isLogin = true;
                return;
            }


        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        islogin();
    }
}

