package com.ptu.mata.model;

import android.util.Log;

import com.ptu.mata.bean.LocalBean;
import com.ptu.mata.myinterface.Api_Server;
import com.ptu.mata.server.MyServer;
import com.ptu.mata.server.RetrofitUseCache;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Mr-Hei on 2016/10/28.
 */

public class CommunityModel implements IModel<LocalBean> {
    //查询网络数据的方法
    @Override
    public void getData(Action1<LocalBean> action1) {
        //调用服务的方法，联网下载数据
        Observable<LocalBean> localBean;
        try {
            localBean = RetrofitUseCache.getService(Api_Server.class).getLocalBen(1, 9);
            localBean.observeOn(AndroidSchedulers.mainThread())//观察在主线程中
                    .subscribeOn(Schedulers.newThread())//订阅在子线程，
                    .subscribe(action1);//把订阅的事件响应出去

        } catch (Exception e) {
            Log.e("CWW", "出现异常");
            e.printStackTrace();
        }
    }

    //查询缓存数据的方法
    public void getCacheData(Action1<LocalBean> action1) {
        Observable<LocalBean> localBean;
        try {
            localBean = RetrofitUseCache.getService(Api_Server.class).getLocalBenDepend(CACHE_CONTROL_CACHE, 1, 9);
            localBean.observeOn(AndroidSchedulers.mainThread())//观察在主线程
                    .subscribeOn(Schedulers.newThread())//订阅在子线程
                    .subscribe(action1);//把订阅事件发射出去
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //从网络获取数据
    public void getNetData(Action1<LocalBean> action1) {
        Observable<LocalBean> localBean;
        try {
            Log.e("tag", "调用从网络获取数据的方法");
            localBean = RetrofitUseCache.getService(Api_Server.class).getLocalBenDepend(CACHE_CONTROL_NETWORK, 1, 9);
            localBean.observeOn(AndroidSchedulers.mainThread())//观察在主线程
                    .subscribeOn(Schedulers.newThread())//订阅在子线程
                    .subscribe(action1);//把订阅事件发射出去
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //page页数，num最大值19
    public void refreshData(Action1<LocalBean> action1, int num) {
        Observable<LocalBean> localBean = MyServer.getApi(Api_Server.class).getLocalBen(1, num);
        localBean.observeOn(AndroidSchedulers.mainThread())//
                .subscribeOn(Schedulers.newThread())//
                .subscribe(action1);

    }


}
