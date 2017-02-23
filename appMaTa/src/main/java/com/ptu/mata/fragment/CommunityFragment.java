package com.ptu.mata.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.ptu.mata.R;
import com.ptu.mata.adapter.BaseRecyclerViewAdapter;
import com.ptu.mata.adapter.RecyclerViewAdapterLocal;
import com.ptu.mata.bean.LocalBean;
import com.ptu.mata.myapplition.MyApplication;
import com.ptu.mata.presenter.CommunityPresenter;
import com.ptu.mata.view.CommunityView;
import com.ptu.mata.view.activity.CalendarActivity;
import com.ptu.mata.view.activity.CityActivity;
import com.ptu.mata.view.activity.LocationDetail;
import com.ptu.mata.view.activity.LocationSearch;
import com.ptu.mata.widget.MyReFreshRecycleView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SinMin on 2016/10/28
 */

//佛曰七苦：生、老、病、死、爱别离、怨憎会、求不得。

//故事的开头总是这样，适逢其会，猝不及防。
//故事的结局总是这样，花开两朵，天各一方。

//世事茫茫，光阴有限，算来何必奔忙！
//人生碌碌，竞短论长，却不道荣枯有数，得失难量。

public class CommunityFragment extends MySuperFragment<CommunityPresenter> implements CommunityView {


    RecyclerView mRecyclerView;

    //上拉加载更多，下拉刷新控件
    MyReFreshRecycleView mReFreshRecycleView;
    //装载数据的集合
    List<LocalBean.ItemsBean> mData = new ArrayList<>();

    //适配器
    RecyclerViewAdapterLocal mAdapter;

    //数据加载时，动画的背景
    RelativeLayout background;

    //属性动画的图片
    ImageView animation;

    //动画对象
    ObjectAnimator animator;

    //application
    MyApplication myApplication;

    //标记是否有网络
    boolean netState = true;

    //头布局里面所用控件对象
    //搜索编辑框
    EditText et_search;

    //查看位置按钮
    Button bt_location;

    //查看日历的布局
    RelativeLayout layout_date;

    //查询酒店的按钮
    Button bt_hotel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从父类继承过来的Presenter可以直接使用
        //调用presenter对象里面加载数据的方法
        //首先判断是否连接网络，如果没有就不去下载数据，并提示网络状态
        myApplication = (MyApplication) getActivity().getApplication();
        netState = myApplication.netState();
        //如果有联网才下载数据
        if (netState) {
            // Log.e("tag", "执行该方法2");
            mPresenter.loadData();
        } else {
            //  Log.e("tag", "执行该方法3");
            //Log.e("tag", "当前没有网络");
            mPresenter.getDateFromCache();
        }
    }

    @Override
    public CommunityPresenter createPresenter() {
        return new CommunityPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.communityfragmentlayout, container, false);
       initState(view);//设置沉浸
        //动画背景
        background = (RelativeLayout) view.findViewById(R.id.background);
        //动画图片
        animation = (ImageView) view.findViewById(R.id.animation);
        //定义动画对象
        animator = ObjectAnimator.ofFloat(animation, "Rotation", 0, 360);
        //循环播放
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        //设置成反转动画
        //animator.setRepeatMode(ObjectAnimator.REVERSE);
        //设置播放时间
        animator.setDuration(500);
        //开启动画
        if (netState) {
            animator.start();
        } else {
            background.setVisibility(View.GONE);
        }

        mReFreshRecycleView = (MyReFreshRecycleView) view.findViewById(R.id.myRefreashView);
        //设置上下都可以拉动
        mReFreshRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
        //recyclerview为刷新控件
        mRecyclerView = mReFreshRecycleView.getRefreshableView();
        //recyclerviewhead
        // mRecyclerViewHeader = (RecyclerViewHeader) view.findViewById(R.id.recycler_head);
        //先给recyclerview设置布局
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        mAdapter = new RecyclerViewAdapterLocal();
        //添加头布局
        View viewHead = LayoutInflater.from(getContext()).inflate(R.layout.item_head_community, mRecyclerView, false);
        //获得头布局里面所有控件对象
        initHeadView(viewHead);
        mAdapter.setHeaderView(viewHead);
        //设置适配器
        mRecyclerView.setAdapter(mAdapter);
        //设置recyclerview条目变化的动画效果
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置条目的点击事件
        setItemClickEvent();
        //对刷新控件的监听
        setRefreshListener();
        return view;
    }

    static final int REQUESTCITYCODE = 1;

    private void initHeadView(View viewHead) {
        et_search = (EditText) viewHead.findViewById(R.id.et_search);
        bt_location = (Button) viewHead.findViewById(R.id.bt_location);
        layout_date = (RelativeLayout) viewHead.findViewById(R.id.layout_date);
        bt_hotel = (Button) viewHead.findViewById(R.id.bt_hotel);

        //先对位置按钮设置监听
        bt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CityActivity.class);
                startActivityForResult(intent, REQUESTCITYCODE);
            }
        });

        //点击日历布局
        layout_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CalendarActivity.class);
                startActivityForResult(intent, REQUESTCITYCODE);
            }
        });

        //点击查询按钮
        bt_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = et_search.getText().toString().trim();
                Intent intent = new Intent(getContext(), LocationSearch.class);
                intent.putExtra("search", searchText);
                startActivity(intent);
            }
        });
    }


    //解决意图
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCITYCODE && resultCode == 1) {
            String cityName = data.getStringExtra("cityname");
            et_search.setText(cityName);
        }
    }

    private void setRefreshListener() {
        mReFreshRecycleView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            //向下拉，刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                //开启动画
                //animator.start();
                // Log.e("tag", "down下拉");
                if (!netState) {
                    mReFreshRecycleView.onRefreshComplete();
                    return;
                }
                mPresenter.loadData();
            }

            //向上拉，加载更多
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                // Log.e("tag", "up上拉");
                //开启动画
                //animator.start();
                if (!netState) {
                    mReFreshRecycleView.onRefreshComplete();
                    return;
                }
                int num = 19;
                mPresenter.getMoreData(num);
            }
        });
    }

    private void setItemClickEvent() {
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.myItemClickListener() {
            @Override
            public void myItemClick(View view, int position) {

                //获得名字和id
                if (mData.size() > 0) {
                    int area_id = mData.get(position - 1).getId();
                    String area_name = mData.get(position - 1).getName();
                    String image = mData.get(position - 1).getImage();
                    int yueyouCount = mData.get(position - 1).getYueyouCount();
                    Intent intent = new Intent(getContext(), LocationDetail.class);
                    intent.putExtra("area_id", area_id);
                    intent.putExtra("area_name", area_name);
                    intent.putExtra("image", image);
                    intent.putExtra("yueyouCount", yueyouCount);
                    getContext().startActivity(intent);
                }
            }
        });

    }


    @Override
    public void showData(LocalBean localBean) {
        //Log.e("cww", "下载数据的长度" + localBean.getItems().size());
        //获得网络下载的数据
        if (localBean.getItems() != null) {
            mData.clear();
            mData.addAll(localBean.getItems());
            mAdapter.setData(mData);
            //停止刷新
            mReFreshRecycleView.onRefreshComplete();
            //把动画停止掉，同时设置不可见
            animator.cancel();
            background.setVisibility(View.GONE);
        }
    }

    @Override
    public void getMoreData(LocalBean localBean) {
        if (localBean != null) {
            mData.clear();
            mData.addAll(localBean.getItems());
            mAdapter.setData(mData);
            //停止加载更多
            mReFreshRecycleView.onRefreshComplete();
            //把动画停止掉，同时设置不可见
            animator.cancel();
            background.setVisibility(View.INVISIBLE);
        }
    }

    //下载数据时出现异常，在应用界面上提醒用户
    @Override
    public void delError(Exception e) {
        Toast.makeText(getContext(), "您的网络出现异常", Toast.LENGTH_SHORT).show();
    }


    /**
     * 动态的设置状态栏  实现沉浸式状态栏
     *
     */
    private void initState(View view) {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            LinearLayout linear_bar = (LinearLayout) view.findViewById(R.id.ll_bar);
            linear_bar.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = getStatusBarHeight();
            //动态的设置隐藏布局的高度
           LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
          /* //RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) linear_bar.getLayoutParams();
           FrameLayout.LayoutParams params= (FrameLayout.LayoutParams) linear_bar.getLayoutParams();*/
            params.height = statusHeight;
            linear_bar.setLayoutParams(params);
        }
    }
    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
