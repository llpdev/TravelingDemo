package com.ptu.mata.myinterface;


import com.ptu.mata.bean.LineBean;
import com.ptu.mata.bean.LocalBean;
import com.ptu.mata.bean.LocationDetailBean;
import com.ptu.mata.bean.MainBean;
import com.ptu.mata.bean.SearchBean;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 在该类中进行api接口的拼接,采用retrofit框架请求网络数据
 */
public interface Api_Server {


    //采用get请求服务器的数据，地址拼接的要点
//    @GET("/api/{path}/list")
//    Observable<TestBean> getTestBean(@Path("path") String path, @Query("page") int page, @Query("rows") int rows, @Query("id") int id);

    //请求当地
    @GET("/v2/res/recommend")
    Observable<LocalBean> getLocalBen(@Query("page") int page, @Query("num") int num);



    //请求当地,用@Header设置Cache-Control
    @GET("/v2/res/recommend")
    Observable<LocalBean> getLocalBenDepend(@Header("Cache-Control") String cacheControl, @Query("page") int page, @Query("num") int num);


    //请求首页数据
    @GET("/v2/yueyou/list")
//Observable<MainBean> getTripBean(@Query("page") int page, @Query("num")int num,@Query("latitude")double latitude,@Query("longitude") double longitude,@Query("time") String time);
// Observable<MainBean> getTripBean(@Query("page") int page, @Query("num")int num,@Query("latitude")double latitude,@Query("longitude") double longitude,@Query("time") String time);
    Observable<MainBean> getTripBean(@Header("Cache-Control") String cacheControl, @Query("page") int page, @Query("num") int num, @Query("latitude") double latitude, @Query("longitude") double longitude);


    //地区数据
    @GET("/v2/yueyou/search")
    Observable<MainBean> getDetail(@Query("keywords") String keywords, @Query("latitude") double latitude, @Query("longitude") double longitude, @Query("page") int page, @Query("num") int num);


    //地区酒店详细信息
    //http://api.miaotu.net/v2/res/recommend/list?area_id=11&area_name=%E8%A5%BF%E5%A1%98&page=1&num=10
    @GET("/v2/res/recommend/list")
    Observable<LocationDetailBean> getLocationDetail(@Query("area_id") int area_id, @Query("area_name") String area_name, @Query("page") int page, @Query("num") int num);

    //得到酒店查询信息
    @GET("/v2/res/search")
    Observable<SearchBean> getSearchData(@Query("begin_date") String begin_date, @Query("end_date") String end_date, @Query("search") String search);


    //文章详情
    @GET("/v2/yueyou")
    Observable<LineBean>getLine(@Query("yid") int yid);


}
