package com.ptu.mata.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ptu.mata.R;
import com.ptu.mata.bean.LineBean;
import com.ptu.mata.presenter.LinePresenter;
import com.ptu.mata.view.LineView;
import com.ptu.mata.widget.GlideCircleTransform;

import cn.sharesdk.onekeyshare.OnekeyShare;


public class LineDetailActivity extends BaseActivity<LinePresenter> implements LineView {

    private TextView name;
    private TextView age;
    private TextView groupname;
    private TextView context;
    private TextView starTime;
    private TextView endTime;
    private TextView gointo;
    private TextView frominto;
    private TextView money;
    private TextView require;
    private TextView yueren;
    private ImageView head;
    private ImageView back;
    private ImageView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  ShareSDK.initSDK(this);
        setContentView(R.layout.activity_line_detail);

        Intent intent = getIntent();
        int id = intent.getIntExtra("key", -1);
        Log.e("自定义标签", "类名:LineDetailActivity"+id);
        mPresenter.loadData(id);
        initView();//绑定控件
        initEvent();

    }

    private void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //点击一键分享
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showShare();   //分享

            }
        });

    }

    private void initView() {
        head = (ImageView) findViewById(R.id.iv_head);
        back = (ImageView) findViewById(R.id.iv_back);
        share = (ImageView) findViewById(R.id.share);

        name = (TextView) findViewById(R.id.name);
        age = (TextView) findViewById(R.id.tv_man);
        groupname = (TextView) findViewById(R.id.tv_groupname);
        context = (TextView) findViewById(R.id.tv_context);
        starTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);
        gointo = (TextView) findViewById(R.id.gointo);
        frominto = (TextView) findViewById(R.id.fromeinto);
        money = (TextView) findViewById(R.id.money);
        yueren = (TextView) findViewById(R.id.yueren);
        require = (TextView) findViewById(R.id.require);
        
        

    }

    @Override
    public LinePresenter creatPresenter() {
        return new LinePresenter();
    }

    @Override
    public void showData(LineBean lineBean) {
        Log.e("自定义标签", " 11221"+lineBean.getItems().getNickname());

        name.setText(lineBean.getItems().getNickname());
        age.setText(lineBean.getItems().getAge()+"岁");
        groupname.setText(lineBean.getItems().getGroupName());
        context.setText("   "+lineBean.getItems().getRemark());
        starTime.setText("开始时间"+lineBean.getItems().getStartDate());
        endTime.setText("结束时间"+lineBean.getItems().getEndDate());
        gointo.setText("出 发 地："+lineBean.getItems().getFrom());
        frominto.setText("目 的 地："+lineBean.getItems().getDestination());
        money.setText("费用说明:"+lineBean.getItems().getMoneyType());
        yueren.setText("求 约 伴:"+lineBean.getItems().getNumber()+"人");
        require.setText("约伴要求："+lineBean.getItems().getRequire());
        Glide.with(this).load(lineBean.getItems().getHeadUrl()).transform(new GlideCircleTransform(this)).into(head);

    }


    private void showShare() {
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
        oks.show(this);
    }

}
