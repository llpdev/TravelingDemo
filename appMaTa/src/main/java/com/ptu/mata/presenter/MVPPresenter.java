package com.ptu.mata.presenter;

import com.ptu.mata.model.MVPModel;
import com.ptu.mata.view.MVPView;

import java.lang.ref.WeakReference;

/**
 * Created by Mr-Hei on 2016/10/27.
 */
//基类presenter，弱引用view解决内存溢出问题，泛型
public abstract class MVPPresenter<V extends MVPView, M extends MVPModel> {
    //弱引用对象
    private WeakReference<V> weakReference;
    public M mModer;

    public MVPPresenter() {
        mModer = createModel();
    }

    //绑定view
    public void attach(V view) {
        weakReference = new WeakReference<V>(view);
    }

    //解除绑定
    public void delAttach() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
    }

    //获得实体模型对象，根据子类具体实体类型，返回具体实体类型
    protected abstract M createModel();

    //获得view对象
    public V getView() {
        if (weakReference == null) {
           // Log.e("tag", "当前的弱引用为空");
        }
        return weakReference == null ? null : weakReference.get();
    }
}
