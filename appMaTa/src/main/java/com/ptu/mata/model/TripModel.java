package com.ptu.mata.model;

import android.util.Log;

import com.ptu.mata.bean.MainBean;
import com.ptu.mata.myinterface.Api_Server;
import com.ptu.mata.server.RetrofitUseCache;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by llpAndroid on 2016/10/28  19:09.
 */

public class TripModel implements IModel<MainBean> {
    public void getData(Action1<MainBean> action1,int page) {
        Log.e("自定义标签", "类名:TripModel" + "方法名：getDa "+page);

        Observable<MainBean> mainBean = null;
        try {
            mainBean = RetrofitUseCache.getService(Api_Server.class).getTripBean(CACHE_CONTROL_NETWORK,page, 10,22.579958,113.961732);
            Log.e("自定义标签", "类名:TripModel" + "方法名：getData111111111111: "+page);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainBean.observeOn(AndroidSchedulers.mainThread())//观察在主线程中
                .subscribeOn(Schedulers.newThread())//订阅在子线程，
                .subscribe(action1);//把订阅的事件响应出去
    }
    @Override
    public void getData(Action1<MainBean> action1) {

    }

    //查询缓存数据的方法
    public void getCacheData(Action1<MainBean> action1,int page) {
        Observable<MainBean> mainBean;
        try {
            mainBean = RetrofitUseCache.getService(Api_Server.class).getTripBean(CACHE_CONTROL_CACHE,page, 10,22.579958,113.961732);
            mainBean.observeOn(AndroidSchedulers.mainThread())//观察在主线程
                    .subscribeOn(Schedulers.newThread())//订阅在子线程
                    .subscribe(action1);//把订阅事件发射出去
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
