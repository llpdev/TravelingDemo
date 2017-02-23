package com.ptu.mata.server;

import android.app.Application;
import android.util.Log;

import com.ptu.mata.widget.AppUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.CacheControl.FORCE_CACHE;

public class RetrofitUseCache {
    //请求当地,用@Header设置Cache-Control
    private static Application mApplication;
    private static final String BASEURL = "http://api.miaotu.net";
    //缓存路径
    private static File cacheDir;
    //保存缓存数据的地址
    private static String cacheUrl;

    public static void init(Application application) {
        mApplication = application;
        //保存在手机内存上的地址
        cacheDir = new File(mApplication.getExternalCacheDir(), "MaTa");


        cacheUrl = cacheDir.getAbsolutePath();

    }


    /**
     * 拦截器
     */
    /**
     * Cache获取与网络获取是异步进行的.
     * 若网络获取比Cache获取速度快,Cache不会发射出去.
     * 网络获取完毕后,异步进行保存Cache操作,不阻塞结果的发射.
     */
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            /**noCache 不使用缓存，全部走网络
             noStore 不使用缓存，也不存储缓存
             onlyIfCached 只使用缓存
             maxAge 设置最大失效时间，失效则不使用 需要服务器配合
             maxStale 设置最大失效时间，失效则不使用 需要服务器配合 感觉这两个类似 还没怎么弄清楚，清楚的同学欢迎留言
             minFresh 设置有效时间，依旧如上
             FORCE_NETWORK 只走网络
             FORCE_CACHE 只走缓存*/

            //设置请求的类型
            Request request = chain.request();

            if (!AppUtils.netState(mApplication)) {
                request = request.newBuilder()
                        .cacheControl(FORCE_CACHE)//只走缓存
                        .url(cacheUrl)//没有网络时从保存缓存地方取数据
                        .build();
                // Toast.makeText(mApplication, "没有网络", Toast.LENGTH_SHORT).show();
                Log.e("tag", "当前没有网络");
            }

            //设置回复的类型
            Response response = chain.proceed(request);
            //有网络时
            if (AppUtils.netState(mApplication)) {
                Log.e("tag", "有网络时，请求网络数据");
                int maxAge = 60 * 60; // read from cache for 60 minute
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                //没网络时
                Log.e("tag", "没有网络时，从本地取数据");
                int maxStale = 60 * 60 * 24 * 7; // tolerate 1-weeks stale
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;

        }
    };

    /**
     * @return
     */
    private static Retrofit getRetrofit() {
        //缓存大小
        int cacheSize = 50 * 1024 * 1024;
        OkHttpClient.Builder builder1 = new OkHttpClient.Builder()
                .cache(new Cache(cacheDir.getAbsoluteFile(), cacheSize));
        builder1.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        OkHttpClient okHttpClient = builder1.build();
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())//json解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//RxJava适配器，和RxAndroid联用
                .client(okHttpClient)//添加okHttp，增加缓存效果
                .build();

        return builder;
    }

    public static <T> T getService(Class<T> tClass) throws Exception {
        return getRetrofit().create(tClass);
    }
}
