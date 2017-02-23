package com.ptu.mata.view;

import com.ptu.mata.bean.TestBean;

/**
 * Created by Mr-Hei on 2016/10/28.
 */
//把view定义成接口，在里面定义一些数据交互的方法
public interface BaseView extends MVPView {
    void showData(TestBean testBean);
}
