package com.ptu.mata.presenter;

import com.ptu.mata.bean.SearchBean;
import com.ptu.mata.model.SearchModel;
import com.ptu.mata.view.MySearchView;

import rx.functions.Action1;

/**
 * Created by Mr-Hei on 2016/11/1.
 */

public class SearchPresenter extends MVPPresenter<MySearchView, SearchModel> {
    @Override
    protected SearchModel createModel() {
        return new SearchModel();
    }

    //获得查询的数据
    public void LoadData(String beginDate, String endDate, String search) {
        mModer.getSearchData(new Action1<SearchBean>() {
            @Override
            public void call(SearchBean searchBean) {
                if (getView() != null) {
                    getView().showData(searchBean);
                }
            }
        }, beginDate, endDate, search);
    }
}
