package com.ptu.mata.widget;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * @author ZhiCheng Guo
 * @version 2014年11月18日 下午12:44:59 精品界面多点触摸有bug,会导致pointerIndex out of range的异常,
 *          所以加了这个view
 */
public class MutipleTouchViewPager extends ViewPager {

    public MutipleTouchViewPager(Context context) {
        super(context);
    }

    public MutipleTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private boolean mIsDisallowIntercept = false;
    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // keep the info about if the innerViews do
        // requestDisallowInterceptTouchEvent
        mIsDisallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // the incorrect array size will only happen in the multi-touch
        // scenario.
        if (ev.getPointerCount() > 1 && mIsDisallowIntercept) {
            requestDisallowInterceptTouchEvent(false);
            boolean handled = super.dispatchTouchEvent(ev);
            requestDisallowInterceptTouchEvent(true);
            return handled;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }
}