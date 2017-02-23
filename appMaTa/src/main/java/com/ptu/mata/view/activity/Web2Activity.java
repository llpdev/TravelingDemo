package com.ptu.mata.view.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ptu.mata.R;

/*关于的页面跳转*/
public class Web2Activity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private WebView webView;

    //动画背景
    RelativeLayout background;
    //动画图片
    ImageView iv_animation;

    //动画对象
    ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);

        webView= (WebView) findViewById(R.id.webview);
        mProgressBar=(ProgressBar)findViewById(R.id.mProgressBar);

        background = (RelativeLayout) findViewById(R.id.background);
        iv_animation = (ImageView) findViewById(R.id.animation);


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


        Intent intent = getIntent();
        String url = intent.getStringExtra("jump"); //得到跳转的url


        webView.requestFocus();
        //对webView设置
        WebSettings settings = webView.getSettings();//设置的封装对象
        settings.setJavaScriptEnabled(true);  //支持js
        settings.setUseWideViewPort(false);  //将图片调整到适合webview的大小
        settings.supportMultipleWindows();  //多窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); //优先使用缓存
        settings.setSupportZoom(true); //支持缩放
        settings.setBuiltInZoomControls(true); //支持手势缩放
        settings.setDisplayZoomControls(false); //是否显示缩放按钮
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 取消滚动条白边效果
        settings.setDefaultTextEncodingName("utf-8");
        webView.loadUrl(url);  //加载页面

        //设置网页加载进度
        webView.setWebChromeClient(new WebChromeClient() {
            //网页加载进度回调
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //更新进度
                mProgressBar.setProgress(newProgress);
            }

        });


        //设置监听,监听网页加载开始和结束，当网页加载开始，进度条设置可见不可见
        webView.setWebViewClient(new WebViewClient(){
            //当网页开始加载回调
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }
            //当网页加载完成回调.
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //加载结束，隐藏进度条
                mProgressBar.setVisibility(View.GONE);
                //取消动画
                animator.cancel();
                //设置动画背景不可见
                background.setVisibility(View.GONE);
                iv_animation.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(Web2Activity.this, "网络连接失败，请重新连接", Toast.LENGTH_SHORT).show();
            }
        });

    }
    //监听系统返回按钮
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK&& webView!=null&&webView.canGoBack()) {//按下返回按钮
            //如果 webview 可以返回 应该执行返回操作,同时终止事件
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
