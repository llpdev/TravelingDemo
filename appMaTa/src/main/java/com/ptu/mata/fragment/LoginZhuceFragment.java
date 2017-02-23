package com.ptu.mata.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ptu.mata.R;
import com.ptu.mata.view.activity.LoginActivity;
import com.ptu.mata.view.activity.MainActivity;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by SinMin on 2016/10/29
 */

//佛曰七苦：生、老、病、死、爱别离、怨憎会、求不得。

//故事的开头总是这样，适逢其会，猝不及防。
//故事的结局总是这样，花开两朵，天各一方。

//世事茫茫，光阴有限，算来何必奔忙！
//人生碌碌，竞短论长，却不道荣枯有数，得失难量。

public class LoginZhuceFragment extends Fragment {
    private ImageView zhuce_back;
    private TextView zhuce_next;
    private EditText zhuce_pwd;
    private EditText zhuce_pwd_s;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_zhuce, container, false);
        zhuce_back = (ImageView) view.findViewById(R.id.zhuce_back);
        zhuce_next = (TextView) view.findViewById(R.id.zhuce_next);
        zhuce_pwd = (EditText) view.findViewById(R.id.zhuce_pwd);
        zhuce_pwd_s = (EditText) view.findViewById(R.id.zhuce_pwd_s);
        addListener();
        return view;
    }

    private void addListener() {
        zhuce_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        zhuce_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (zhuce_pwd.getText().length() < 6 && zhuce_pwd_s.getText().length() < 6) {
                    Toast.makeText(getContext(), "密码应为6位以上", Toast.LENGTH_SHORT).show();
                } else if (!zhuce_pwd.getText().toString().equals(zhuce_pwd_s.getText().toString())) {
                    Toast.makeText(getContext(), "两次密码不同", Toast.LENGTH_SHORT).show();
                    zhuce_pwd.setText("");
                    zhuce_pwd_s.setText("");
                } else {
                   Intent intent = new Intent(getActivity(), MainActivity.class);
                   startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }
}
