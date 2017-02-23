package com.ptu.mata.server;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 联网请求服务,利用RxAndroid框架提醒数据更改
 */

public class MyServer {
    //联网请求服务器数据的基网址
    // private static final String BASEURL = "http://www.tngou.net";
    private static final String BASEURL = "http://api.miaotu.net";
    //联网请求数据的对象,即请求数据的事件，观察者模式中被观察的事件
    //构建者模式
    private static Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEURL)//
            .addConverterFactory(GsonConverterFactory.create())//gson解析
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//观察者模式，和rxandroid联用
            .build();

    /**
     * 返回具体请求类的方法
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getApi(Class<T> tClass) {
        return retrofit.create(tClass);
    }


}


