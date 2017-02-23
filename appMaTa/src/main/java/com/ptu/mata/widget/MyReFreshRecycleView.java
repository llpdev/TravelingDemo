package com.ptu.mata.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by llpAndroid on 2016/10/16  18:41.
 */
public class MyReFreshRecycleView extends PullToRefreshBase<RecyclerView> {
   // private RecyclerView.Adapter adapter;

    public MyReFreshRecycleView(Context context) {
        super(context);
    }

    public MyReFreshRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyReFreshRecycleView(Context context, Mode mode) {
        super(context, mode);
    }

    public MyReFreshRecycleView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    /**
     * 返回刷新的方向
     * @return
     */
    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    /**
     * 返回内部刷新 view 的对象
     * @param context Context to create view with
     * @param attrs AttributeSet from wrapped class. Means that anything you
     *            include in the XML layout declaration will be routed to the
     *            created View
     * @return
     */
    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        return new RecyclerView(context,attrs);
    }

    /**
     * 是否上拉
     * @return
     */
    @Override
    protected boolean isReadyForPullEnd() {
     //return  true;
        return isLastItemVisible();
    }

    /**
     * 是否是下拉
     * @return 返回 true 代表可以下拉刷新
     */
    @Override
    protected boolean isReadyForPullStart() {
        //写逻辑
        //判断第一条是不是完全可见就行
        return isFirstItemVisable();
    }

    public boolean isFirstItemVisable() {
        RecyclerView.Adapter adapter = getRefreshableView().getAdapter();
        if (adapter == null || adapter.getItemCount() == 0) {//没有数据 可以刷新
            return true;
        } else{//有数据
            int fistItemPotion = getFistItemPotion();//获取到当前显示的是第几条数据
            if (fistItemPotion == 0) {
                return getRefreshableView().getChildAt(0).getTop() >= getRefreshableView().getTop();
            }
        }
        return false;
    }
    private int getFistItemPotion() {
        View childAt = getRefreshableView().getChildAt(0);
//        int childAdapterPosition = getRefreshableView().getChildAdapterPosition(childAt);
        // //如果为 null 返回-1  否者调用方法查询出当前条目实际是第几条
        return childAt==null?-1:getRefreshableView().getChildAdapterPosition(childAt);
    }


    public boolean isLastItemVisible(){
        RecyclerView.Adapter adapter = getRefreshableView().getAdapter();
        if (adapter == null || adapter.getItemCount() == 0) {//没有数据 可以刷新
            return true;
        } else{//有数据
            int lastItemPosition = getRefreshableView().getChildCount();//得到当前显示条目的总条
            int lastItemPotion = getLastItemPotion();//获取到当前页面最后显示的是具体是第几条数据
            if (lastItemPotion == getRefreshableView().getAdapter().getItemCount()-1) {
                return getRefreshableView().getChildAt(lastItemPosition - 1).getBottom() <= getRefreshableView().getBottom();
            }
        }
        return false;
    }
    //获取到当前页面最后显示的是具体是第几条数据
    private int getLastItemPotion() {
        //int itemCount = getRefreshableView().getAdapter().getItemCount();//得到总共条目数
        int lastItemPosition = getRefreshableView().getChildCount();//得到当前显示条目的总条数
        View childAt = getRefreshableView().getChildAt(lastItemPosition - 1);//item对象
        //如果为 null 返回-1  否者调用方法查询出当前最后条目实际是第几条
        return childAt==null?-1:getRefreshableView().getChildAdapterPosition(childAt);
    }

}
