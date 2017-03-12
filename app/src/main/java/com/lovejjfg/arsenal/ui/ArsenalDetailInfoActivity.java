package com.lovejjfg.arsenal.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo;
import com.lovejjfg.arsenal.base.SupportActivity;
import com.lovejjfg.arsenal.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArsenalDetailInfoActivity extends SupportActivity {
    public static String INFO = "info";
    @Bind(R.id.web_view)
    WebView mWeb;
    @Bind(R.id.iv_img)
    ImageView mIv;
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_site)
    TextView mTvSite;
    @Bind(R.id.tv_language)
    TextView mTvLanguage;
    @Bind(R.id.tv_updated)
    TextView mTvUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ArsenalDetailInfo detailInfo = getIntent().getParcelableExtra(INFO);
        mWeb.setVerticalScrollBarEnabled(false);
        mWeb.setHorizontalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        WebSettings webSettings = mWeb.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
        mWeb.setClickable(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setDisplayZoomControls(false);
        mWeb.setWebViewClient(new WebViewClient());
        mWeb.setWebChromeClient(new WebChromeClient());

        initViews(detailInfo);
    }

    private void initViews(ArsenalDetailInfo detailInfo) {
        if (detailInfo == null) {
            onBackPressed();
            return;
        }
        mToolBar.setTitle(detailInfo.getTitle());
        mTvLanguage.setText(detailInfo.getLanguage());
        mTvName.setText(detailInfo.getOwner());
        mTvUpdated.setText(detailInfo.getUpdatedDate());
        mTvSite.setText(detailInfo.getLink());
        Glide.with(this).load(detailInfo.getPortraitUrl()).into(mIv);
        mWeb.loadDataWithBaseURL(Constants.BASE_URL, detailInfo.getDesc(),
                Constants.MIME_TYPE, Constants.ENCODING, Constants.FAIL_URL);

    }

    @Override
    public int initLayoutRes() {
        return R.layout.activity_web_view;
    }

}
