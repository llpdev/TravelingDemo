package com.ptu.mata.presenter;

import com.ptu.mata.bean.MainBean;
import com.ptu.mata.model.DetailModel;
import com.ptu.mata.view.DetailView;

import rx.functions.Action1;

/**
 * Created by llpAndroid on 2016/10/29  10:05.
 */

public class DetailPresenter extends MVPPresenter <DetailView,DetailModel> {
    @Override
    protected DetailModel createModel() {
        return new DetailModel();
    }

    //下载数据
    public void loadData(String key,int page) {
        mModer.getData(new Action1<MainBean>() {
            @Override
            public void call(MainBean mainBean) {
                // Log.e("自定义标签", " 111："+mainBean.getFromMark());
                getView().showData(mainBean);
            }
        }, key, page);
    }
}
