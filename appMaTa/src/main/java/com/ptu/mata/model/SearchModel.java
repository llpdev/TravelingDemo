package com.ptu.mata.model;

import com.ptu.mata.bean.SearchBean;
import com.ptu.mata.myinterface.Api_Server;
import com.ptu.mata.server.RetrofitUseCache;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Mr-Hei on 2016/11/1.
 */

public class SearchModel implements IModel<SearchBean> {
    @Override
    public void getData(Action1<SearchBean> action1) {

    }

    //通过指定日期和内容来查询
    public void getSearchData(Action1<SearchBean> action1, String beginDate, String endDate, String search) {
        try {
            Observable<SearchBean> bean = RetrofitUseCache.getService(Api_Server.class).getSearchData(beginDate, endDate, search);
            bean.observeOn(AndroidSchedulers.mainThread())//
                    .subscribeOn(Schedulers.newThread())//
                    .subscribe(action1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
