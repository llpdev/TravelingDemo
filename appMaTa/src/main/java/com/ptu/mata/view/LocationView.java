package com.ptu.mata.view;

import com.ptu.mata.bean.LocationDetailBean;

/**
 * Created by Mr-Hei on 2016/10/31.
 */

public interface LocationView extends MVPView {
    void showData(LocationDetailBean bean);
}
