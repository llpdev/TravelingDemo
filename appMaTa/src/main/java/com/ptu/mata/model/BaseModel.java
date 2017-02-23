package com.ptu.mata.model;

import com.ptu.mata.bean.LocalBean;
import com.ptu.mata.bean.TestBean;
import com.ptu.mata.myinterface.Api_Server;
import com.ptu.mata.server.MyServer;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Mr-Hei on 2016/10/28.
 */

public class BaseModel implements IModel {
    @Override
    public void getData(Action1 action1) {

    }
//    @Override
//    public void getData(Action1<TestBean> action1) {
//        //调用服务的方法，联网下载数据
//        Observable<LocalBean> localBean = MyServer.getApi(Api_Server.class).getLocalBen(1, 10);
//        localBean.observeOn(AndroidSchedulers.mainThread())//
//                .subscribeOn(Schedulers.newThread())//
//                .subscribe();
//    }
//    @Override
//    public void getData(Action1<TestBean> action1) {
//        //调用服务的方法，联网下载数据
//        Observable<TestBean> test = MyServer.getApi(Api_Server.class).getTestBean("cook", 1, 19, 1);
//        test.observeOn(AndroidSchedulers.mainThread())//
//                .subscribeOn(Schedulers.newThread())//
//                .subscribe(action1);
//
//    }
}
