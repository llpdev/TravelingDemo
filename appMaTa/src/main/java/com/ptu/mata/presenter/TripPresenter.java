package com.ptu.mata.presenter;

import android.util.Log;

import com.ptu.mata.bean.MainBean;
import com.ptu.mata.model.TripModel;
import com.ptu.mata.view.TripView;

import rx.functions.Action1;

/**
 * Created by llpAndroid on 2016/10/28  19:02.
 */

public class TripPresenter extends MVPPresenter<TripView,TripModel> {
    @Override
    protected TripModel createModel() {
        return new TripModel();
    }

    //下载数据
    public void loadData(int page) {
        Log.e("自定义标签", "类名:TripPre "+page);
        mModer.getData(new Action1<MainBean>() {
            @Override
            public void call(MainBean mainBean) {
               // Log.e("自定义标签", " 111："+mainBean.getFromMark());
                getView().showData(mainBean);
            }
        },page);

    }

    //从缓存里取数据
    //从缓冲中读取数据
    public void getDateFromCache(int page) {
        mModer.getCacheData(new Action1<MainBean>() {
            @Override
            public void call(MainBean mainBean) {
                //  Log.e("tag", "从缓存中得到图片的地址为" + localBean.getItems().get(0).getImage());
                getView().showData(mainBean);
            }
        },page);
    }
}
