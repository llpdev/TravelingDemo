package com.ptu.mata.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.ptu.mata.R;
import com.ptu.mata.bean.UserBean;
import com.ptu.mata.fragment.LoginButtonFragment;
import com.ptu.mata.fragment.LoginZhuceFragment;
import com.ptu.mata.widget.UserDataBaseOpenHelper;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by SinMin on 2016/10/28
 */

//佛曰七苦：生、老、病、死、爱别离、怨憎会、求不得。

//故事的开头总是这样，适逢其会，猝不及防。
//故事的结局总是这样，花开两朵，天各一方。

//世事茫茫，光阴有限，算来何必奔忙！
//人生碌碌，竞短论长，却不道荣枯有数，得失难量。

public class LoginActivity extends AppCompatActivity {
    private EditText loginnum;
    private EditText loginpwd;
    private Button loginbutton;
    private TextView loginzhuce;
    private TextView qqlogin;
    private Tencent mTencent;
    int action = QQ_LOGIN;
    private static final int QQ_LOGIN = 254;
    private static final int QQ_USERINFO = 255;
    private static final int QQ_SHARE = 256;
    public static String phonenumber;
    boolean isBecome=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMSSDK.initSDK(this, "1807f0ded7426", "456c6a6787073fba4a5d8150db3c36a1");
        setContentView(R.layout.login_edit_layout);
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.login_button_layout,new LoginButtonFragment()).commit();
        inttview();
    }

    private void inttview() {
        datas=new ArrayList<>();
        mOpenHelper=UserDataBaseOpenHelper.getInstance(this);
        try {
            userDao=mOpenHelper.getDao(UserBean.class);//获取数据库操作类
            List<UserBean> users = userDao.queryForAll();
            datas.addAll(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loginnum = (EditText)findViewById(R.id.login_edit_phonenum);
        loginpwd = (EditText)findViewById(R.id.login_edit_password);
        loginbutton = (Button)findViewById(R.id.login_button_login);
        qqlogin = (TextView) findViewById(R.id.login_textview_qq);
        loginzhuce = (TextView) findViewById(R.id.login_zhuce);
        mTencent=Tencent.createInstance("1105761154",this);//app id

        addListener();
    }

    IUiListener mIUiListener;//定义QQ登陆的监听器
    private void addListener() {
        loginzhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"注册",Toast.LENGTH_SHORT).show();
                //getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.login_button_layout,new LoginButtonFragment()).commit();

                mobLayout();
            }
        });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save();

            }
        });
        qqlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qqLogin();
            }
        });
    }

    private void mobLayout() {
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    phonenumber=phone;
                    isBecome=true;
                }
            }
        });
        registerPage.show(this);
    }
    public void save() {
        String name = loginnum.getText().toString();
        String pwd = loginpwd.getText().toString();
        if (isHave(name)){
            UserBean userEntity = new UserBean();
            userEntity.setName(name);
            userEntity.setPwd(pwd);
            userEntity.setIslogin(true);
            try {
                userDao.update(userEntity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this,"用户不存在",Toast.LENGTH_SHORT).show();
        }

    }
    public void qqLogin(){
        mIUiListener=new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject jsonObject= (JSONObject) o;
                switch (action) {
                    case QQ_LOGIN:
                        try {
                            String openId=jsonObject.getString("openid");
                            String expirisin=jsonObject.getString("expires_in");//有效期
                            String access_token=jsonObject.getString("access_token");//token内容
                            mTencent.setOpenId(openId);
                            mTencent.setAccessToken(access_token,expirisin);
                            UserInfo userInfo=new UserInfo(LoginActivity.this,mTencent.getQQToken());
                            action=QQ_USERINFO;
                            userInfo.getUserInfo(mIUiListener);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case QQ_USERINFO:
                        try {
                            String nickName=jsonObject.getString("nickname");//昵称信息
                            String imgurl=jsonObject.getString("figureurl_qq_2");//头像信息
                            addUser(nickName,imgurl);
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                }
            }
            @Override
            public void onError(UiError uiError) {
            }
            @Override
            public void onCancel() {
            }
        };
        mTencent.login(this,"all",mIUiListener);//调用登录方法登录

    }

    private void addUser(String name,String url) {
        UserBean userEntity = new UserBean();
        userEntity.setName(name);
        userEntity.setUrl(url);
        userEntity.setIslogin(true);
        Log.e("自定义", "addUser: "+userEntity.toString() );

        try {
            if (isHave(name)) {
                userDao.update(userEntity);
            }else {
                userDao.create(userEntity);//增加用户
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isHave(String name) {
        boolean isHave=false;
        for (int i = 0; i < datas.size(); i++) {
            if (name.equals(datas.get(i).getName())) {
                isHave=true;
            }else {
                isHave=false;
            }
        }
        return isHave;
    }

    Dao<UserBean,Integer> userDao;//数据库操作类
    UserDataBaseOpenHelper mOpenHelper;
    List<UserBean> datas;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //必须调用腾讯实例的回调方法
        if(requestCode== Constants.REQUEST_LOGIN){
            mTencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"onResume",Toast.LENGTH_SHORT).show();
        if (isBecome){
            addUser(phonenumber,"aaa");
            getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.login_button_layout,new LoginZhuceFragment()).commitAllowingStateLoss();
        }
    }




}
