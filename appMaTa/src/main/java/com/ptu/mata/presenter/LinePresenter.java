package com.ptu.mata.presenter;

import com.ptu.mata.bean.LineBean;
import com.ptu.mata.model.LineModel;
import com.ptu.mata.view.LineView;

import rx.functions.Action1;

/**
 * Created by llpAndroid on 2016/10/31  15:41.
 */

public class LinePresenter extends MVPPresenter<LineView,LineModel> {
    @Override
    protected LineModel createModel() {
        return new LineModel();
    }

    //下载数据
    public void loadData(int id) {
        mModer.getData(new Action1<LineBean>() {
            @Override
            public void call(LineBean lineBean) {
                // Log.e("自定义标签", " 111："+mainBean.getFromMark());
                getView().showData(lineBean);
            }
        }, id);
    }
}
