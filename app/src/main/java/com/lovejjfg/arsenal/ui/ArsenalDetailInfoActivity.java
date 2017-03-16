/*
 *  Copyright (c) 2017.  Joe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.lovejjfg.arsenal.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.DataManager;
import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo;
import com.lovejjfg.arsenal.base.SupportActivity;
import com.lovejjfg.arsenal.utils.Constants;
import com.lovejjfg.arsenal.utils.JumpUtils;
import com.lovejjfg.arsenal.utils.glide.CircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArsenalDetailInfoActivity extends SupportActivity implements View.OnClickListener {
    public static final String INFO = "info";
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
    private ArsenalDetailInfo mDetailInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mDetailInfo = getIntent().getParcelableExtra(INFO);
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
//        mWeb.setWebViewClient(new WebViewClient());
        mWeb.setWebChromeClient(new WebChromeClient());
        mToolBar.setNavigationOnClickListener(this);

        initViews(mDetailInfo);
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
        String portraitUrl = detailInfo.getPortraitUrl();
        if (!TextUtils.isEmpty(portraitUrl)) {
            Glide.with(this)
                    .load(portraitUrl)
                    .error(R.mipmap.ic_launcher)
                    .transform(mCircleTransform)
                    .into(mIv);
            mIv.setOnClickListener(v ->
                    DataManager.handleNormalService(DataManager.getArsenalApi().getArsenalUserInfo(detailInfo.getOwnerurl()),
                            info -> JumpUtils.jumpToUserDetail(this, info), throwable -> showToast(throwable.getMessage())));
        } else {
            mIv.setOnClickListener(null);
        }
        mWeb.loadDataWithBaseURL(Constants.BASE_URL, detailInfo.getDesc(),
                Constants.MIME_TYPE, Constants.ENCODING, Constants.FAIL_URL);
        CharSequence text = mTvSite.getText();
        if (!TextUtils.equals("N/A", text)) {
            mTvSite.setOnClickListener(this);
        } else {
            mTvSite.setOnClickListener(null);
        }


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
            mWeb.clearCache(true);
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

    @Override
    protected void onNewIntent(Intent intent) {
        ArsenalDetailInfo detailInfo = intent.getParcelableExtra(INFO);
        if (detailInfo.equals(mDetailInfo)) {
            return;
        }
        mDetailInfo = detailInfo;
        initViews(mDetailInfo);
        super.onNewIntent(intent);
    }
}
