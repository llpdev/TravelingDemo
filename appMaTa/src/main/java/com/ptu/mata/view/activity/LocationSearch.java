package com.ptu.mata.view.activity;

import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.ptu.mata.R;
import com.ptu.mata.adapter.BaseRecyclerViewAdapter;
import com.ptu.mata.adapter.RecyclerViewAdapterSearch;
import com.ptu.mata.bean.SearchBean;
import com.ptu.mata.presenter.SearchPresenter;
import com.ptu.mata.view.MySearchView;
import com.ptu.mata.widget.MyReFreshRecycleView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LocationSearch extends BaseActivity<SearchPresenter> implements MySearchView {
    //帅选，排序
    LinearLayout layout_shaixuan, layout_paixu;

    MyReFreshRecycleView myReFreshRecycleView;

    List<SearchBean.ItemsBean> mData = new ArrayList<>();

    //格式化时间
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    //适配器
    RecyclerViewAdapterSearch mAdapter;

    RecyclerView recyclerView;

    //查询异常提醒
    LinearLayout layout;

    //动画背景
    RelativeLayout background;

    //动画图片
    ImageView iv_animation;

    //动画对象
    ObjectAnimator animator;

    //对话框对象
    PopupWindow popupWindow;

    //弹出对话框时，遮罩层
    ImageView iv_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.ptu.mata.R.layout.activity_location_search);
        //初始化布局
        initView();
        //定义动画对象
        animator = ObjectAnimator.ofFloat(iv_animation, "Rotation", 0, 360);
        //循环播放
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        //设置成反转动画
        //animator.setRepeatMode(ObjectAnimator.REVERSE);
        //设置播放时间
        animator.setDuration(500);
        //播放动画
        animator.start();
        //先获得从其他活动传过来的参数
        String search = getIntent().getStringExtra("search");
        //获取当前时间,2016-10-28
        Date date = new Date();
        //当天的日期
        String beginDate = format.format(date.getTime());
        //明天
        String endDate = format.format(date.getTime() + 1000 * 60 * 60 * 24);
        Log.e("tag", "当前日期：" + beginDate + "," + "第二天：" + endDate);
        //开始下载
        mPresenter.LoadData(beginDate, endDate, search);


    }

    private void initView() {
        layout_paixu = (LinearLayout) findViewById(R.id.layout_paixu);
        layout_shaixuan = (LinearLayout) findViewById(R.id.layout_paixu);
        myReFreshRecycleView = (MyReFreshRecycleView) findViewById(R.id.recyler);
        background = (RelativeLayout) findViewById(R.id.background);
        layout = (LinearLayout) findViewById(R.id.layout1);
        iv_animation = (ImageView) findViewById(R.id.animation);
        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        //设置刷新模式
        myReFreshRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
        //获得RecyclerView对象
        recyclerView = myReFreshRecycleView.getRefreshableView();
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        mAdapter = new RecyclerViewAdapterSearch();
        //设置适配器
        recyclerView.setAdapter(mAdapter);
        //设置条目点击事件
        setItemClickListener();
        //点击排序弹出对话框
        layout_paixu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopupWindow();
            }
        });

    }

    private void setItemClickListener() {
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.myItemClickListener() {
            @Override
            public void myItemClick(View view, int position) {
                Log.e("tag", "点击了条目");
            }
        });
    }

    @Override
    public SearchPresenter creatPresenter() {
        return new SearchPresenter();
    }

    @Override
    public void showData(SearchBean bean) {
        ///Log.e("tag", "下载数据的长度" + bean.getItems().size());
        animator.cancel();
        //设置动画背景不可见
        background.setVisibility(View.GONE);
        mData.addAll(bean.getItems());
        //给适配器设置值
        mAdapter.setData(mData);
        if (mData.size() != 0) {
            layout.setVisibility(View.GONE);
        } else {

            layout.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            //透明度设为原来定的值
            backgroundAlpha(1f);
            //设置遮罩层隐藏的动画
            iv_bg.startAnimation(AnimationUtils.loadAnimation(LocationSearch.this, R.anim.bg_out));
            //隐藏遮罩层
            iv_bg.setVisibility(View.GONE);


        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);

    }

    protected void initPopupWindow() {
        View popupWindowView = getLayoutInflater().inflate(R.layout.pop, null);
        //内容，高度，宽度
        popupWindow = new PopupWindow(popupWindowView, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        //动画效果
        popupWindow.setAnimationStyle(R.style.AnimationBottomFade);

        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);
        //遮罩层显示
        iv_bg.setVisibility(View.VISIBLE);
        //设置遮罩层显示的动画
        iv_bg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bg_enter));
        //宽度
        //popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
        //高度
        //popupWindow.setHeight(LayoutParams.FILL_PARENT);
        //显示位置
        popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_main, null), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        //设置背景半透明
        //backgroundAlpha(0.9f);
        //设置背景亮度
        //backgroundBrightness(0.0f);
        //关闭事件
        popupWindow.setOnDismissListener(new popupDismissListener());

        popupWindowView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*if( popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow=null;
                }*/
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });

        Button bt1 = (Button) popupWindowView.findViewById(R.id.bt1);
        Button bt2 = (Button) popupWindowView.findViewById(R.id.bt2);
        Button bt3 = (Button) popupWindowView.findViewById(R.id.bt3);
        Button bt4 = (Button) popupWindowView.findViewById(R.id.bt4);

        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(LocationSearch.this, "默认排序", Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(LocationSearch.this, "销量最高", Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(LocationSearch.this, "价格从高到低", Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LocationSearch.this, "价格从低到高", Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
            }
        });
    }
}
