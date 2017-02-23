package com.ptu.mata.presenter;

import com.ptu.mata.bean.TestBean;
import com.ptu.mata.model.BaseModel;
import com.ptu.mata.view.BaseView;

import rx.functions.Action1;

/**
 * Created by Mr-Hei on 2016/10/28.
 */

public class BasePresenter extends MVPPresenter<BaseView, BaseModel> {
    @Override
    protected BaseModel createModel() {
        return new BaseModel();
    }

    //将从网络上下载下来的数据，在此处让model和view进行交互
    public void loadData() {
        mModer.getData(new Action1<TestBean>() {
            @Override
            public void call(TestBean testBean) {
                //交给view，到用户界面进行处理
                getView().showData(testBean);
            }
        });
    }


}
