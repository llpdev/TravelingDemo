package com.ptu.mata.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.ptu.mata.R;
import com.ptu.mata.adapter.BaseRecyclerAdapter;
import com.ptu.mata.adapter.FirstFragmentRecycleViewAdapter;
import com.ptu.mata.adapter.MyAdapter;
import com.ptu.mata.adapter.RecyclerAdapterTrip;
import com.ptu.mata.bean.MainBean;
import com.ptu.mata.myapplition.MyApplication;
import com.ptu.mata.presenter.TripPresenter;
import com.ptu.mata.view.TripView;
import com.ptu.mata.view.activity.DetailActivity;
import com.ptu.mata.view.activity.ImgFragmentActivity;
import com.ptu.mata.view.activity.LineDetailActivity;
import com.ptu.mata.view.activity.SearchActivity;
import com.ptu.mata.view.activity.WebActivity;
import com.ptu.mata.widget.MyReFreshRecycleView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * Created by SinMin on 2016/10/28
 */

//佛曰七苦：生、老、病、死、爱别离、怨憎会、求不得。

//故事的开头总是这样，适逢其会，猝不及防。
//故事的结局总是这样，花开两朵，天各一方。

//世事茫茫，光阴有限，算来何必奔忙！
//人生碌碌，竞短论长，却不道荣枯有数，得失难量。

public class FirstFragment extends MySuperFragment<TripPresenter> implements TripView, FirstFragmentRecycleViewAdapter.onItemClickListener {
    private MyReFreshRecycleView myReFreshRecycleView;
    private RecyclerView mRecyclerView;
    List<MainBean.ItemsBean> list;
    RecyclerAdapterTrip adapterTrip;
    private List<String> recycle_list;
    int page = 1;


    List<View> views;
    MyAdapter loopAdapter;

    // 左中右三个弹出窗口
    private PopupWindow popup;
    // 左中右三个layout
    private View layoutLeft;
    // 左中右三个ListView控件（弹出窗口里）
    private ListView menulistLeft;
    // 菜单数据项
    private List<String> listLeft;

    MainBean bean;
    //标记是否有网络
    boolean netState = true;
    //application
    MyApplication myApplication;

    private ImageView popuwindow_buton;
    private View popuwindow_view;
    private PopupWindow popupWindow;
    private int popu = 0;
    private RecyclerView recyclerView;
    private TextView showtextview;
    FirstFragmentRecycleViewAdapter cardviewadapter;
    private List<String> picdata;
    ArrayAdapter<String> adapter;

    MainBean mainBean1;

    //头顶的搜索布局
    LinearLayout layout_search;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化数据项
        listLeft = new ArrayList<String>();

        listLeft.add("默认排序");
        listLeft.add("只看男生");
        listLeft.add("只看女生");
        listLeft.add("按距离");
        listLeft.add("按人气");
        //适配器
        adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                listLeft);

        //调用presenter对象里面加载数据的方法
        // mPresenter.loadData(1);

        //首先判断是否连接网络，如果没有就不去下载数据，并提示网络状态
        myApplication = (MyApplication) getActivity().getApplication();
        netState = myApplication.netState();
        //如果有联网才下载数据
        if (netState) {
            // Log.e("tag", "执行该方法2");
            mPresenter.loadData(page);
        } else {
            //  Log.e("tag", "执行该方法3");
            //  Log.e("tag", "当前没有网络");
            mPresenter.getDateFromCache(page);
        }

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.trip_fragment, container, false);
        initView(view);
        initState(view);//设置沉浸
        initPopuWindow(inflater);
        initPopup(inflater);

        //查找到搜索布局
        layout_search = (LinearLayout) view.findViewById(R.id.layout_search);
        //设置点击监听事件
        layout_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        //设置适配器
        adapterTrip = new RecyclerAdapterTrip();
        //adapterTrip.addDatas((ArrayList<MainBean.ItemsBean>) list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));


        View viewHead = LayoutInflater.from(getContext()).inflate(R.layout.trip_head, mRecyclerView, false);
        adapterTrip.setHeaderView(viewHead);


        final TextView tv_lj = (TextView) viewHead.findViewById(R.id.tv_lj);
        tv_lj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("key", tv_lj.getText().toString());
                startActivity(intent);
            }
        });

        final TextView tv_dl = (TextView) viewHead.findViewById(R.id.tv_dl);
        tv_dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("key", tv_dl.getText().toString());
                startActivity(intent);
            }
        });

        final TextView tv_ls = (TextView) viewHead.findViewById(R.id.tv_ls);
        tv_dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("key", tv_ls.getText().toString());
                startActivity(intent);
            }
        });
        //点击图片，跳转web
        final ViewPager viewPager = (ViewPager)viewHead.findViewById(R.id.loopViewPager);
        views = new ArrayList<>();
        ImageView iv1 = new ImageView(getContext());
        iv1.setScaleType(ImageView.ScaleType.FIT_XY);
        iv1.setImageResource(R.drawable.a);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("key",7);
                startActivity(intent);
            }
        });
        views.add(iv1);
        // ----------------
        ImageView iv2 = new ImageView(getContext());
        iv2.setScaleType(ImageView.ScaleType.FIT_XY);
        iv2.setImageResource(R.drawable.b);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("key",5);
                startActivity(intent);
            }
        });
        views.add(iv2);
        // ----------------
        ImageView iv3 = new ImageView(getContext());
        iv3.setScaleType(ImageView.ScaleType.FIT_XY);
        iv3.setImageResource(R.drawable.c);
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("key",3);
                startActivity(intent);
            }
        });
        views.add(iv3);

        loopAdapter = new MyAdapter(views);
        viewPager.setAdapter(loopAdapter);

        // 启动一个线程，死循环
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Log.d("tag", "子线程在运行~~~~~~~~~~~~");
                        SystemClock.sleep(4000);
                        // 再设置下一张图片
                        // 1.先获取当前显示的位置
                        // 解决子线程不能更新UI问题
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 在UI线程中运行
                                int current = viewPager.getCurrentItem();
                                // 2.在当前显示的位置基础上，再加1
                                current++;
                                // 3.再重新设置一遍显示的位置
                                viewPager.setCurrentItem(current % views.size());
                            }
                        });

                       /* getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 在UI线程中运行
                                int current = viewPager.getCurrentItem();
                                // 2.在当前显示的位置基础上，再加1
                                current++;
                                // 3.再重新设置一遍显示的位置
                                viewPager.setCurrentItem(current % views.size());
                            }
                        });*/
                    }
                }
            }).start();


        popuwindow_buton = (ImageView) viewHead.findViewById(R.id.down);
        popuwindow_buton.setOnClickListener(new PopuOnClickListener());
        showtextview = (TextView) viewHead.findViewById(R.id.head_textview_show);

        //设置适配器
        mRecyclerView.setAdapter(adapterTrip);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置动画效果

        initEvent();//设置事件

        return view;
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


    private void initPopup(LayoutInflater inflater) {
        layoutLeft = inflater.inflate(R.layout.pop_menulist, null);
    }


    private void initPopuWindow(LayoutInflater inflater) {
        popuwindow_view = inflater.inflate(R.layout.firstfragment_popuwindow, null);
        popupWindow = new PopupWindow(popuwindow_view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);//不能在没有焦点的时候使用
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.e("自定义", "size------xiaoshi---------: ");
                showtextview.setVisibility(View.INVISIBLE);
                ObjectAnimator//
                        .ofFloat(popuwindow_buton, "rotation", 180.0F, 360.0F)//
                        .setDuration(500)//
                        .start();
            }
        });
        recyclerView = (RecyclerView) popuwindow_view.findViewById(R.id.popuwindow_recycleview);
        recyclerView.getBackground().setAlpha(245);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(cardviewadapter);
        cardviewadapter.setOnItemClickListener(this);
    }


    private void initEvent() {
        //接口回调设置点击事件
        //item的点击事件
        adapterTrip.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Log.e("自定义标签", "类名:FirstFragment" + "方法名：onItemClick: " + position);
                int yid = list.get(position).getYid();
                Intent intent = new Intent(getContext(), LineDetailActivity.class);
                intent.putExtra("key", yid);
                startActivity(intent);
            }
        });


        adapterTrip.setOnclickCallBack(new RecyclerAdapterTrip.onClickCallBack() {
            private List<MainBean.ItemsBean.PicListBean> picList;

            @Override
            public void onHeadClick(View v, int position) {
                Log.e("自定义标签", "类名:FirstFragment" + "方法名：head: " + position);
            }

            @Override
            public void onImgClick(View v, int position) {
                Intent intent2 = new Intent(getContext(), ImgFragmentActivity.class);
                Bundle bundle = new Bundle();
                MainBean.ItemsBean itemsBean = mainBean1.getItems().get(position);
                bundle.putParcelable("pics", itemsBean);
                intent2.putExtra("bundle", bundle);
                switch (v.getId()) {
                    case R.id.iv1:
                       /* ImgFragmentActivity imgFragmentActivity=new ImgFragmentActivity();
                        imgFragmentActivity.register();*/
                        Log.e("自定义标签", "类名:FirstFragment" + "方法名：onImgClick: 图片11111=" + position);
                        intent2.putExtra("type", 0);
                        startActivity(intent2);
                        break;
                    case R.id.iv2:
                        //Toast.makeText(myApplication, "点击", Toast.LENGTH_SHORT).show();
                        intent2.putExtra("type", 1);
                        startActivity(intent2);
                        break;
                    case R.id.iv3:
                        intent2.putExtra("type", 2);
                        startActivity(intent2);
                        break;
                }
            }
            //喜欢
            @Override
            public void onLikeClick(View v, int position) {
                 boolean LikeStatus =false;  //默认的状态
               // Toast.makeText(myApplication, " "+position, Toast.LENGTH_SHORT).show();
                if(!LikeStatus){
                    //Log.e("自定义标签：", "onLikeClick: +1");
                    TextView tvLike= (TextView) v.findViewById(R.id.tv_like);
                    tvLike.setText(Integer.parseInt(tvLike.getText().toString())+1+"");
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_detail_like_solid);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                    tvLike.setCompoundDrawables(drawable, null, null, null);//画在左边
                    LikeStatus=true;
                }else {
                    Toast.makeText(myApplication, "已经点赞过了", Toast.LENGTH_SHORT).show();
                }
            }
            //评论
            @Override
            public void onCommentClick(View v, int position) {
                boolean CommentStatus=false;
                if(!CommentStatus){
                    TextView tvComment= (TextView) v.findViewById(R.id.tv_comment);
                    tvComment.setText(Integer.parseInt(tvComment.getText().toString())+1+"");
                    CommentStatus=true;
                }else {
                    Toast.makeText(myApplication, "已经评论过了", Toast.LENGTH_SHORT).show();
                }
            }
            //分享功能
            @Override
            public void onJoinClick(View v, int position) {
                //Toast.makeText(myApplication, " "+position, Toast.LENGTH_SHORT).show();

                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();
                // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
                oks.setTitle("标题");
                // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
                //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
                oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite("ShareSDK");
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("http://sharesdk.cn");

                // 启动分享GUI
                oks.show(getActivity());


            }
        });


        //在这个方法里加载数据
        myReFreshRecycleView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                //下拉刷新
                //设置刷新时间
                String label = DateUtils.formatDateTime(
                        getContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 接收两个参数，为true,false返回设置下拉的ILoadingLayout
                ILoadingLayout startLabels = refreshView.getLoadingLayoutProxy(true, false);
                startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
                startLabels.setRefreshingLabel("正在载入中...");// 刷新时放开以刷新
                startLabels.setReleaseLabel("放开以刷新...");// 下来达到一定距离时，显示的提示
                startLabels.setLastUpdatedLabel(label);
                //设置图片
               /* Drawable btnDrawable = getContext().getResources().getDrawable(R.drawable.indicator_arrow);
                startLabels.setLoadingDrawable(btnDrawable);*/
                if (!netState) {
                    myReFreshRecycleView.onRefreshComplete();
                    return;
                }
                mPresenter.loadData(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                //加载更多
                // 接收两个参数，为false,true返回设置下拉的ILoadingLayout
                ILoadingLayout endLabels = refreshView.getLoadingLayoutProxy(false, true);
                endLabels.setPullLabel("你可劲拉，加载...");// 刚下拉时，显示的提示
                endLabels.setRefreshingLabel("好嘞，正在加载...");// 刷新时
                endLabels.setReleaseLabel("你敢放，我就敢加载...");// 下来达到一定距离时，显示的提示
                Drawable btnDrawable = getContext().getResources().getDrawable(R.mipmap.euc);
                endLabels.setLoadingDrawable(btnDrawable);
                if (!netState) {
                    myReFreshRecycleView.onRefreshComplete();
                    return;
                }
                mPresenter.loadData(++page);
                Log.e("自定义标签", "类名:FirstFragment" + "方法名：onPullUpToRefresh: 页数" + page);

            }
        });
    }


    private void initView(View view) {
        list = new ArrayList<>();
        recycle_list = new ArrayList<>();
        String[] carditem = {"丽江", "大理", "拉萨", "青海", "成都", "九寨沟", "三亚", "泸沽湖", "香格里拉", "厦门", "杭州", "西塘", "凤凰", "泰国", "普吉", "巴厘岛", "韩国"};
        for (int i = 0; i < carditem.length; i++) {
            recycle_list.add(carditem[i]);
        }
        cardviewadapter = new FirstFragmentRecycleViewAdapter(recycle_list);
        myReFreshRecycleView = (MyReFreshRecycleView) view.findViewById(R.id.myRecycleView);
        // myReFreshRecycleView.setMode(PullToRefreshBase.Mode.BOTH);
        mRecyclerView = myReFreshRecycleView.getRefreshableView();

        final ImageView iv_add = (ImageView) view.findViewById(R.id.addmore);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出popup
                if (popup != null && popup.isShowing()) {
                    popup.dismiss();
                } else {
                    menulistLeft = (ListView) layoutLeft
                            .findViewById(R.id.menulist);
                    menulistLeft.setAdapter(adapter);
                    // 点击listview中item的处理
                    menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getContext(), "选择" + position, Toast.LENGTH_SHORT).show();
                            //mPresenter.loadData(++position);

                            // 隐藏弹出窗口
                            if (popup != null && popup.isShowing()) {
                                popup.dismiss();
                            }
                        }
                    });
                    // 创建弹出窗口
                    // 窗口内容为layoutLeft，里面包含一个ListView
                    // 窗口宽度跟tvLeft一样
                    popup = new PopupWindow(layoutLeft, 200,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

                    ColorDrawable cd = new ColorDrawable(0000);
                    popup.setBackgroundDrawable(cd);
                    popup.setAnimationStyle(R.style.PopupAnimation);
                    popup.update();
                    popup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                    popup.setTouchable(true); // 设置popupwindow可点击
                    popup.setOutsideTouchable(true); // 设置popupwindow外部可点击
                    popup.setFocusable(true); // 获取焦点

                    // 设置popupwindow的位置（相对tvLeft的位置）
                    //x,y关于原点的偏移量（横坐标，纵坐标）
                    popup.showAsDropDown(iv_add, 0, 0);

                    popup.setTouchInterceptor(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            // 如果点击了popupwindow的外部，popupwindow也会消失
                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                popup.dismiss();
                                return true;
                            }
                            return false;
                        }
                    });


                }


            }
        });

    }

    @Override
    public TripPresenter createPresenter() {
        return new TripPresenter();
    }

    @Override
    public void showData(MainBean mainBean) {
        mainBean1 = mainBean;
        Log.e("自定义标签", "1111：" + mainBean.getItems().get(0).getNickname());
        list.addAll(mainBean.getItems());
        Log.e("自定义标签", "类名:FirstFragment" + "方法名：showData: " + list.size());
        adapterTrip.addDatas((ArrayList<MainBean.ItemsBean>) list);
        myReFreshRecycleView.onRefreshComplete();//数据加载完，停止刷新
        //adapterTrip.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int postion) {
        //Toast.makeText(getActivity(), recycle_list.get(postion), Toast.LENGTH_SHORT).show();
        String city = recycle_list.get(postion).toString();
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("key", city);
        startActivity(intent);


    }

    /**
     * 这个类主要显示PopuWindow，并显示之后对里面的按钮添加监听事件。
     */
    private class PopuOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.down:
                    // 加上下面两行可以用back键关闭popupwindow，否则必须调用dismiss();
                    // 需要顺利让PopUpWindow dimiss；PopUpWindow的背景不能为空。
                    // 当有popuWindow.setFocusable(false);的时候，说明PopuWindow不能获得焦点，并不能点击外面消失，只能由dismiss()消失。
                    // 当设置为popuWindow.setFocusable(true);的时候，加上下面两行代码才会消失
                    // 注意这里添加背景并不会覆盖原来的背景。
                    popupWindow.setFocusable(true);
                    ColorDrawable cd = new ColorDrawable(0xfff000);
                    popupWindow.setBackgroundDrawable(cd);
                    popupWindow.showAsDropDown(v);
                    showtextview.setVisibility(View.VISIBLE);
                    ObjectAnimator//
                            .ofFloat(popuwindow_buton, "rotation", 0.0F, 180.0F)//
                            .setDuration(500)//
                            .start();

                    break;
                default:
                    break;
            }
        }
    }
}
