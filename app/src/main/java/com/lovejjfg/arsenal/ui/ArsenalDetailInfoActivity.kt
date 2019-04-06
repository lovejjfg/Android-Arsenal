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

package com.lovejjfg.arsenal.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.lovejjfg.arsenal.R
import com.lovejjfg.arsenal.api.DataManager
import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo
import com.lovejjfg.arsenal.base.SupportActivity
import com.lovejjfg.arsenal.utils.Constants
import com.lovejjfg.arsenal.utils.JumpUtils
import com.lovejjfg.arsenal.utils.glide.CircleTransform
import com.lovejjfg.arsenal.utils.glide.GlideApp
import kotlinx.android.synthetic.main.activity_web_view.iv_img
import kotlinx.android.synthetic.main.activity_web_view.tv_language
import kotlinx.android.synthetic.main.activity_web_view.tv_name
import kotlinx.android.synthetic.main.activity_web_view.tv_site
import kotlinx.android.synthetic.main.activity_web_view.tv_updated
import kotlinx.android.synthetic.main.activity_web_view.web_view

class ArsenalDetailInfoActivity : SupportActivity(), View.OnClickListener {
    lateinit var mWeb: WebView
    lateinit var mIv: ImageView
    lateinit var mTvName: TextView
    lateinit var mTvSite: TextView
    lateinit var mTvLanguage: TextView
    lateinit var mTvUpdated: TextView
    private lateinit var mCircleTransform: CircleTransform
    private lateinit var mDetailInfo: ArsenalDetailInfo

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        initView()
        mDetailInfo = if (savedInstanceState == null) {
            intent.getParcelableExtra(INFO)
        } else {
            savedInstanceState.getParcelable(INFO)
        }
        mCircleTransform = CircleTransform()
        mWeb.isVerticalScrollBarEnabled = false
        mWeb.isHorizontalScrollBarEnabled = false
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //            WebView.setWebContentsDebuggingEnabled(true);
        //        }
        val webSettings = mWeb.settings
        webSettings.javaScriptEnabled = true
        //        webSettings.setUseWideViewPort(true);
        //        webSettings.setLoadWithOverviewMode(true);
        mWeb.isClickable = true
        webSettings.domStorageEnabled = true
        webSettings.loadsImagesAutomatically = true
        webSettings.builtInZoomControls = true
        webSettings.blockNetworkImage = false
        webSettings.displayZoomControls = false
        //        mWeb.setWebViewClient(new WebViewClient());
        mWeb.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress != 100) {
                    showLoadingDialog("")
                } else {
                    closeLoadingDialog()
                }
            }
        }
        toolbar?.setNavigationOnClickListener(this)

        initViews(mDetailInfo)
    }

    private fun initView() {
        mWeb = web_view
        mIv = iv_img
        mTvName = tv_name
        mTvSite = tv_site
        mTvLanguage = tv_language
        mTvUpdated = tv_updated
    }

    private fun initViews(detailInfo: ArsenalDetailInfo?) {
        if (detailInfo == null) {
            onBackPressed()
            return
        }
        toolbar?.title = detailInfo.title
        mTvLanguage.text = detailInfo.language
        mTvName.text = detailInfo.owner
        mTvUpdated.text = detailInfo.updatedDate
        mTvSite.text = detailInfo.link
        val portraitUrl = detailInfo.portraitUrl
        if (!TextUtils.isEmpty(portraitUrl)) {
            GlideApp.with(this)
                .load(portraitUrl)
                .error(R.mipmap.ic_launcher)
                .transform(mCircleTransform)
                .into(mIv)
            mIv.setOnClickListener { v ->
                DataManager.handleNormalService<ArsenalUserInfo>(
                    DataManager.getArsenalApi().getArsenalUserInfo(detailInfo.ownerurl),
                    { info -> JumpUtils.jumpToUserDetail(this, info, v) },
                    { throwable -> showToast(throwable?.message) })
            }
        } else {
            mIv.setOnClickListener(null)
        }
        mWeb.loadDataWithBaseURL(
            Constants.BASE_URL, detailInfo.desc,
            Constants.MIME_TYPE, Constants.ENCODING, Constants.FAIL_URL
        )
        val text = mTvSite.text
        if (!TextUtils.equals("N/A", text)) {
            mTvSite.setOnClickListener(this)
        } else {
            mTvSite.setOnClickListener(null)
        }
    }

    override fun initLayoutRes(): Int {
        return R.layout.activity_web_view
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_site -> {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                val uriString = mTvSite.text.toString()
                val uri = Uri.parse(uriString)
                intent.data = uri
                startActivity(Intent.createChooser(intent, uriString))
                return
            }
        }
        onBackPressed()
    }

    override fun onDestroy() {

        if (mWeb != null) {
            val parent = mWeb.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(mWeb)
            }

            mWeb.stopLoading()
            mWeb.clearCache(true)
            mWeb.settings.javaScriptEnabled = false
            mWeb.clearHistory()
            mWeb.clearView()
            mWeb.removeAllViews()
            mWeb.destroy()
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (mWeb.canGoBack()) {
            mWeb.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onNewIntent(intent: Intent) {
        val detailInfo = intent.getParcelableExtra<ArsenalDetailInfo>(INFO)
        if (detailInfo == mDetailInfo) {
            return
        }
        mDetailInfo = detailInfo
        initViews(mDetailInfo)
        super.onNewIntent(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(INFO, mDetailInfo)
        super.onSaveInstanceState(outState)
    }

    companion object {
        val INFO = "info"
    }
}
