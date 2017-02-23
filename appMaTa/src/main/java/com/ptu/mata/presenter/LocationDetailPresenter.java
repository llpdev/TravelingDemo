package com.ptu.mata.presenter;

import com.ptu.mata.bean.LocationDetailBean;
import com.ptu.mata.model.LocationDetailModel;
import com.ptu.mata.view.LocationView;

import rx.functions.Action1;

/**
 * Created by Mr-Hei on 2016/10/31.
 */

public class LocationDetailPresenter extends MVPPresenter<LocationView, LocationDetailModel> {
    @Override
    protected LocationDetailModel createModel() {
        return new LocationDetailModel();
    }

    //将model下载的数据传给View
    public void LoadData(int area_id, String name, int num) {
        mModer.getDataByName(new Action1<LocationDetailBean>() {
            @Override
            public void call(LocationDetailBean locationDetailBean) {
                //报空指针异常
                if (getView() != null) {
                    getView().showData(locationDetailBean);
                }
            }
        }, area_id, name, num);
    }
}
