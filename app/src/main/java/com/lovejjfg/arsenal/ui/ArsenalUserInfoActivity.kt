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
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lovejjfg.arsenal.R
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo
import com.lovejjfg.arsenal.base.SupportActivity
import com.lovejjfg.arsenal.base.SupportFragment
import com.lovejjfg.arsenal.utils.JumpUtils
import com.lovejjfg.arsenal.utils.glide.CircleTransform
import com.lovejjfg.arsenal.utils.glide.GlideApp
import kotlinx.android.synthetic.main.activity_user_info.iv_img
import kotlinx.android.synthetic.main.activity_user_info.tabs
import kotlinx.android.synthetic.main.activity_user_info.tv_flowers
import kotlinx.android.synthetic.main.activity_user_info.tv_flowing
import kotlinx.android.synthetic.main.activity_user_info.tv_name
import kotlinx.android.synthetic.main.activity_user_info.tv_repo
import kotlinx.android.synthetic.main.activity_user_info.tv_site
import kotlinx.android.synthetic.main.activity_user_info.viewpager
import java.util.ArrayList

class ArsenalUserInfoActivity : SupportActivity(), View.OnClickListener {
    private lateinit var mIvPortrait: ImageView
    private lateinit var mTvLocation: TextView
    private lateinit var mTvFlowers: TextView
    private lateinit var mTvFlowing: TextView
    private lateinit var mTvSite: TextView
    private lateinit var mTvRepo: TextView
    private lateinit var mTabs: TabLayout
    private lateinit var mViewpager: ViewPager
    private var circleTransform: CircleTransform? = null
    private var mInfo: ArsenalUserInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        toolbar?.setNavigationOnClickListener(this)
        circleTransform = CircleTransform()
        val info = if (savedInstanceState == null) {
            intent.getParcelableExtra(USER_INFO) as ArsenalUserInfo
        } else {
            savedInstanceState.getParcelable(USER_INFO) as ArsenalUserInfo
        }
        mInfo = info
        if (mInfo == null) {
            finish()
        } else {
            refreshUI(info)
        }
    }

    private fun initView() {
        mIvPortrait = iv_img
        mTvLocation = tv_name
        mTvFlowers = tv_flowers
        mTvFlowing = tv_flowing
        mTvSite = tv_site
        mTvRepo = tv_repo
        mTabs = tabs
        mViewpager = viewpager
    }

    private fun refreshUI(info: ArsenalUserInfo) {
        GlideApp.with(this@ArsenalUserInfoActivity)
            .load(info.portraitUrl)
            .error(R.mipmap.ic_launcher)
            .transform(circleTransform!!)
            .into(mIvPortrait)
        toolbar?.title = info.userName
        mTvFlowers.text = info.followers
        mTvFlowing.text = info.following
        mTvRepo.text = info.publicRepo
        mTvLocation.text = info.location

        val text = info.site
        mTvSite.text = text
        if (!TextUtils.equals("N/A", text)) {
            mTvSite.setOnClickListener(this)
        } else {
            mTvSite.setOnClickListener(null)
        }
        val contributions = info.contributions
        val ownProjects = info.ownProjects
        val contribution = Bundle()
        contribution.putParcelableArrayList(ArsenalListInfoFragment.ARSENAL_LIST_INFO, contributions)
        contribution.putInt(
            ArsenalListInfoFragment.TYPE_NAME,
            ArsenalListInfoFragment.TYPE_USER_DETAIL
        )
        val ownProject = Bundle()
        ownProject.putParcelableArrayList(ArsenalListInfoFragment.ARSENAL_LIST_INFO, ownProjects)
        ownProject.putInt(
            ArsenalListInfoFragment.TYPE_NAME,
            ArsenalListInfoFragment.TYPE_USER_DETAIL
        )
        val fragments = ArrayList<SupportFragment>()
        val ownProFragment = ArsenalListInfoFragment()
        ownProFragment.arguments = ownProject
        val contributionFragment = ArsenalListInfoFragment()
        contributionFragment.arguments = contribution

        fragments.add(ownProFragment)
        fragments.add(contributionFragment)

        val adapter = ListInfoPagerAdapter(supportFragmentManager, fragments)

        mViewpager.adapter = adapter

        mTabs.setupWithViewPager(mViewpager)
    }

    override fun initLayoutRes(): Int {
        return R.layout.activity_user_info
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_site -> {
                JumpUtils.jumpToWeb(this, mTvSite.text.toString())
                return
            }
        }
        onBackPressed()
    }

    override fun onNewIntent(intent: Intent) {
        val info = intent.getParcelableExtra<ArsenalUserInfo>(USER_INFO)
        if (mInfo == info) {
            return
        }
        mInfo = info
        refreshUI(info)
        super.onNewIntent(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(USER_INFO, mInfo)
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val USER_INFO = "UserInfo"
    }
}
