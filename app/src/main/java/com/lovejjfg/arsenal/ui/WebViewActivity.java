package com.lovejjfg.arsenal.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.DataManager;
import com.lovejjfg.arsenal.base.SupportActivity;
import com.lovejjfg.arsenal.utils.Constants;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class WebViewActivity extends SupportActivity {
    public static String URL = "url";
    @Bind(R.id.web_view)
    WebView mWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        String data = getIntent().getStringExtra(URL);
        mWeb.setVerticalScrollBarEnabled(false);
        mWeb.setHorizontalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        WebSettings webSettings = mWeb.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWeb.setClickable(true);
        mWeb.setFocusableInTouchMode(true);
        mWeb.setWebViewClient(new WebViewClient());
        mWeb.setWebChromeClient(new WebChromeClient());
        mWeb.loadDataWithBaseURL(Constants.BASE_URL, data, Constants.MIME_TYPE, Constants.ENCODING, Constants.FAIL_URL);
//        mWeb.post(new Runnable() {
//            @Override
//            public void run() {
//                mWeb.loadUrl("https://android-arsenal.com/details/1/5430");
//            }
//        });

        Observable<String> stringObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {


            }
        });

        DataManager.handleNormalService(stringObservable, new Action1<String>() {
            @Override
            public void call(String data) {
                Log.e("TAG", "call: " + data);
                mWeb.loadDataWithBaseURL(Constants.BASE_URL, data, Constants.MIME_TYPE, Constants.ENCODING, Constants.FAIL_URL);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("TAG", "call: ", throwable);
            }
        });


    }

    @Override
    public int initLayoutRes() {
        return R.layout.activity_web_view;
    }
}
