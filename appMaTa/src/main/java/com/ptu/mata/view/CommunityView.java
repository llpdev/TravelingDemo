package com.ptu.mata.view;

import com.ptu.mata.bean.LocalBean;

/**
 * Created by Mr-Hei on 2016/10/28.
 */

public interface CommunityView extends MVPView {
    void showData(LocalBean localBean);

    void getMoreData(LocalBean localBean);

    void delError(Exception e);


}
