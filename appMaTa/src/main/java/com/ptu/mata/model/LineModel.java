package com.ptu.mata.model;

import com.ptu.mata.bean.LineBean;
import com.ptu.mata.myinterface.Api_Server;
import com.ptu.mata.server.MyServer;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by llpAndroid on 2016/10/31  15:42.
 */

public class LineModel implements IModel<LineBean> {

    public void getData(Action1<LineBean>action1,int id){
        Observable<LineBean>lineBean= MyServer.getApi(Api_Server.class).getLine(id);
        lineBean.observeOn(AndroidSchedulers.mainThread())//
        .subscribeOn(Schedulers.newThread()).subscribe(action1);

    }

    @Override
    public void getData(Action1<LineBean> action1) {

    }
}
