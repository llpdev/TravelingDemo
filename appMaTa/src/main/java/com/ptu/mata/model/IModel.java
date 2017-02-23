package com.ptu.mata.model;

import rx.functions.Action1;

/**
 * Created by Mr-Hei on 2016/10/28.
 */

//model接口，定义一些处理数据的基本方法，即抽象方法，和rxandroid联用
public interface IModel<T> extends MVPModel {
    //设缓存有效期为两个星期
    long CACHE_STALE_SEC = 60 * 60 * 24 * 14;
    //查询缓存的Cache-Control设置
    String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    String CACHE_CONTROL_NETWORK = "max-age=0";

    void getData(Action1<T> action1);
}
