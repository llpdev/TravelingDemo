package com.ptu.mata.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.ptu.mata.R;
import com.ptu.mata.adapter.CityAdapter;
import com.ptu.mata.bean.CityBean;
import com.ptu.mata.myapplition.MyApplication;
import com.ptu.mata.view.CircleTextView;
import com.ptu.mata.view.MySlideView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mr-Hei on 2016/11/1.
 */

public class CityActivity extends Activity implements MySlideView.onTouchListener, CityAdapter.onItemClickListener {
    private List<CityBean> cityList = new ArrayList<>();
    private Set<String> firstPinYin = new LinkedHashSet<>();
    public static List<String> pinyinList = new ArrayList<>();
    private PinyinComparator pinyinComparator;

    private MySlideView mySlideView;
    private CircleTextView circleTxt;


    private RecyclerView recyclerView;
    private TextView tvStickyHeaderView;
    private CityAdapter adapter;
    private LinearLayoutManager layoutManager;
    private TextView tv_cityName;
    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();
    }

    private void initView() {
        tv_cityName = (TextView) findViewById(R.id.tv_cityname);
        //对textview设置点击监听事件
        setClickListenerOnTV();
        myApplication = (MyApplication) getApplication();
        //如果城市名称为空，重新定位
        tv_cityName.setText(myApplication.getCityName());
        cityList.clear();
        firstPinYin.clear();
        pinyinList.clear();

        mySlideView = (MySlideView) findViewById(R.id.my_slide_view);
        circleTxt = (CircleTextView) findViewById(R.id.my_circle_view);
        pinyinComparator = new PinyinComparator();
        tvStickyHeaderView = (TextView) findViewById(R.id.tv_sticky_header_view);
        for (int i = 0; i < CityBean.stringCitys.length; i++) {
            CityBean city = new CityBean();
            city.setCityName(CityBean.stringCitys[i]);
            city.setCityPinyin(transformPinYin(CityBean.stringCitys[i]));
            cityList.add(city);
        }
        Collections.sort(cityList, pinyinComparator);
        for (CityBean city : cityList) {
            firstPinYin.add(city.getCityPinyin().substring(0, 1));
        }
        for (String string : firstPinYin) {
            pinyinList.add(string);
        }

        mySlideView.setListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.rv_sticky_example);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CityAdapter(getApplicationContext(), cityList);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                View stickyInfoView = recyclerView.findChildViewUnder(
                        tvStickyHeaderView.getMeasuredWidth() / 2, 5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    tvStickyHeaderView.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                View transInfoView = recyclerView.findChildViewUnder(
                        tvStickyHeaderView.getMeasuredWidth() / 2, tvStickyHeaderView.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - tvStickyHeaderView.getMeasuredHeight();
                    if (transViewStatus == CityAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            tvStickyHeaderView.setTranslationY(dealtY);
                        } else {
                            tvStickyHeaderView.setTranslationY(0);
                        }
                    } else if (transViewStatus == CityAdapter.NONE_STICKY_VIEW) {
                        tvStickyHeaderView.setTranslationY(0);
                    }
                }


            }
        });


    }

    private void setClickListenerOnTV() {
        tv_cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("cityname", tv_cityName.getText().toString().trim());
                setResult(1, intent);
                //退出活动
                finish();
            }
        });
    }

    @Override
    public void itemClick(int position) {
        //Toast.makeText(getApplicationContext(), "你选择了:" + cityList.get(position).getCityName(), Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        intent.putExtra("cityname", cityList.get(position).getCityName());
        setResult(1, intent);
        //退出活动
        finish();
    }

    public String transformPinYin(String character) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < character.length(); i++) {
            buffer.append(Pinyin.toPinyin(character.charAt(i)));
        }
        return buffer.toString();
    }

    @Override
    public void showTextView(String textView, boolean dismiss) {

        if (dismiss) {
            circleTxt.setVisibility(View.GONE);
        } else {
            circleTxt.setVisibility(View.VISIBLE);
            circleTxt.setText(textView);
        }

        int selectPosition = 0;
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).getFirstPinYin().equals(textView)) {
                selectPosition = i;
                break;
            }
        }
//        recyclerView.scrollToPosition(selectPosition);
        scroll2Position(selectPosition);
    }


    public class PinyinComparator implements Comparator<CityBean> {
        @Override
        public int compare(CityBean cityFirst, CityBean citySecond) {
            return cityFirst.getCityPinyin().compareTo(citySecond.getCityPinyin());
        }
    }


    private void scroll2Position(int index) {
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        int lastPosition = layoutManager.findLastVisibleItemPosition();
        if (index <= firstPosition) {
            recyclerView.scrollToPosition(index);
        } else if (index <= lastPosition) {
            int top = recyclerView.getChildAt(index - firstPosition).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            recyclerView.scrollToPosition(index);
        }
    }
}
