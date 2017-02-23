package com.ptu.mata.presenter;

import android.util.Log;

import com.ptu.mata.bean.LocalBean;
import com.ptu.mata.model.CommunityModel;
import com.ptu.mata.view.CommunityView;

import rx.functions.Action1;

/**
 * Created by Mr-Hei on 2016/10/28.
 */

public class CommunityPresenter extends MVPPresenter<CommunityView, CommunityModel> {
    @Override
    protected CommunityModel createModel() {
        return new CommunityModel();
    }

    //下载数据
    public void loadData() {
        try {
            //Log.e("tag", "p异常1");
            mModer.getNetData(new Action1<LocalBean>() {
                @Override
                public void call(LocalBean localBean) {
                    getView().showData(localBean);
                    //Log.e("cww", "call数据加载？");
                }
            });
            // Log.e("tag", "p异常2");
        } catch (Exception e) {
            Log.e("tag", "p异常3");
            // e.printStackTrace();
            getView().delError(e);//出现下载异常提醒用户
        }
    }

    //从缓冲中读取数据
    public void getDateFromCache() {
        mModer.getCacheData(new Action1<LocalBean>() {
            @Override
            public void call(LocalBean localBean) {
                //  Log.e("tag", "从缓存中得到图片的地址为" + localBean.getItems().get(0).getImage());
                getView().showData(localBean);
            }
        });
    }


    //加载更多的数据
    public void getMoreData(int num) {

        mModer.refreshData(new Action1<LocalBean>() {
            @Override
            public void call(LocalBean localBean) {
                getView().getMoreData(localBean);
            }
        }, num);

    }


}
