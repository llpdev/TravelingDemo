package com.ptu.mata.model;

import com.ptu.mata.bean.LocationDetailBean;
import com.ptu.mata.myinterface.Api_Server;
import com.ptu.mata.server.RetrofitUseCache;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Mr-Hei on 2016/10/31.
 */

public class LocationDetailModel implements IModel<LocationDetailBean> {
    @Override
    public void getData(Action1<LocationDetailBean> action1) {

    }

    public void getDataByName(Action1<LocationDetailBean> action1, int area_id, String name, int num) {
        try {
            Observable<LocationDetailBean> observable = RetrofitUseCache.getService(Api_Server.class).getLocationDetail(area_id, name, 1, num);
            observable.observeOn(AndroidSchedulers.mainThread())//观察在主活动
                    .subscribeOn(Schedulers.newThread())//订阅在子线程
                    .subscribe(action1);//将订阅发射出去
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
