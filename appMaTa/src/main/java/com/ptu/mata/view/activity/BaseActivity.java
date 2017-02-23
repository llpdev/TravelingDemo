package com.ptu.mata.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.ptu.mata.presenter.MVPPresenter;
import com.ptu.mata.view.MVPView;

import me.yokeyword.swipebackfragment.SwipeBackActivity;

/**
 * Created by Mr-Hei on 2016/10/28.
 */

public abstract class BaseActivity<P extends MVPPresenter> extends SwipeBackActivity {

    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = creatPresenter();
        //和view建立联系
        mPresenter.attach((MVPView) this);
        initState();
    }
    public abstract P creatPresenter();
    //沉浸模式
    public  void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
           getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.delAttach();
    }

}
