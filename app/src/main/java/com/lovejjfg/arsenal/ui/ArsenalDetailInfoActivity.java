package com.lovejjfg.arsenal.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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
import com.lovejjfg.arsenal.utils.glide.CircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArsenalDetailInfoActivity extends SupportActivity implements View.OnClickListener {
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
    private CircleTransform mCircleTransform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ArsenalDetailInfo detailInfo = getIntent().getParcelableExtra(INFO);
        mCircleTransform = new CircleTransform(this);
        mWeb.setVerticalScrollBarEnabled(false);
        mWeb.setHorizontalScrollBarEnabled(false);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WebView.setWebContentsDebuggingEnabled(true);
//        }
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
        mWeb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        mWeb.setWebChromeClient(new WebChromeClient());
        mToolBar.setNavigationOnClickListener(this);

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
        Glide.with(this)
                .load(detailInfo.getPortraitUrl())
                .transform(mCircleTransform)
                .into(mIv);
        mWeb.loadDataWithBaseURL(Constants.BASE_URL, detailInfo.getDesc(),
                Constants.MIME_TYPE, Constants.ENCODING, Constants.FAIL_URL);

        mTvSite.setOnClickListener(this);

    }

    @Override
    public int initLayoutRes() {
        return R.layout.activity_web_view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_site:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String uriString = mTvSite.getText().toString();
                Uri uri = Uri.parse(uriString);
                intent.setData(uri);
                startActivity(Intent.createChooser(intent, uriString));
                return;
        }
        onBackPressed();
    }

    @Override
    protected void onDestroy() {

        if (mWeb != null) {
            ViewParent parent = mWeb.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWeb);
            }

            mWeb.stopLoading();
            mWeb.getSettings().setJavaScriptEnabled(false);
            mWeb.clearHistory();
            mWeb.clearView();
            mWeb.removeAllViews();
            mWeb.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mWeb.canGoBack()) {
            mWeb.goBack();
        } else {
            super.onBackPressed();
        }
    }
}