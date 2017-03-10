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
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class WebViewActivity extends SupportActivity {
    @Bind(R.id.web_view)
    WebView mWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

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
//        mWeb.post(new Runnable() {
//            @Override
//            public void run() {
//                mWeb.loadUrl("https://android-arsenal.com/details/1/5430");
//            }
//        });

        Observable<String> stringObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                StringBuilder sb = new StringBuilder();
                sb.append("<link href=\"/css/app.3d329cbe.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                Document document = null;
                subscriber.onStart();
                try {
                    document = Jsoup.connect("https://android-arsenal.com/details/1/5430").get();
                    //Elements divs = doc.select("div").not("#logo");
                    Elements select1 = document.select("body").not("div:matches(/(?=ads)/)");

                    if (select1.isEmpty()) {
                        System.out.println("没有更多了！");
                    } else {
                        sb.append(select1.first().toString());
                        subscriber.onNext(sb.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onCompleted();

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
