package com.ptu.mata.model;

import com.ptu.mata.bean.MainBean;
import com.ptu.mata.myinterface.Api_Server;
import com.ptu.mata.server.MyServer;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by llpAndroid on 2016/10/29  10:12.
 */

public class DetailModel implements IModel<MainBean> {

    public void getData(Action1<MainBean> action1, String key, int page) {
        Observable<MainBean> mainBean = MyServer.getApi(Api_Server.class).getDetail(key,22.579958,113.961732,page,10);
        mainBean.observeOn(AndroidSchedulers.mainThread())//观察在主线程中
                .subscribeOn(Schedulers.newThread())//订阅在子线程，
                .subscribe(action1);//把订阅的事件响应出去

    }

    @Override
    public void getData(Action1<MainBean> action1) {

    }
}
