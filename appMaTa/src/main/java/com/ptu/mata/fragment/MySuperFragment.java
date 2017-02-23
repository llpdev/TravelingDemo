package com.ptu.mata.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.ptu.mata.presenter.MVPPresenter;
import com.ptu.mata.view.MVPView;

/**
 * Created by Mr-Hei on 2016/10/28.
 */

public abstract class MySuperFragment<T extends MVPPresenter> extends Fragment {
    public T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attach((MVPView) this);
    }

    public abstract T createPresenter();


    /**
     * 和 activity 剥离了
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.delAttach();

    }
}
