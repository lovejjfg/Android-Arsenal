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

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lovejjfg.arsenal.R
import com.lovejjfg.arsenal.api.mode.Library
import com.lovejjfg.arsenal.base.SupportActivity
import com.lovejjfg.arsenal.utils.EggsHelper
import com.lovejjfg.arsenal.utils.FirebaseUtils
import com.lovejjfg.arsenal.utils.JumpUtils
import com.lovejjfg.arsenal.utils.glide.GlideApp
import com.lovejjfg.powerrecycle.AdapterLoader
import com.lovejjfg.powerrecycle.PowerAdapter
import com.lovejjfg.powerrecycle.holder.PowerHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_about.iv_img
import kotlinx.android.synthetic.main.activity_about.recycler_view
import kotlinx.android.synthetic.main.activity_about.tv_about
import kotlinx.android.synthetic.main.activity_about.tv_site
import kotlinx.android.synthetic.main.layout_about_info.tv_des
import kotlinx.android.synthetic.main.layout_about_info.tv_name
import java.util.ArrayList

class AboutActivity : SupportActivity(), AdapterLoader.OnItemClickListener<Library>, OnClickListener {
    lateinit var mTvSite: TextView
    lateinit var mIv: ImageView
    lateinit var mRecyclerView: RecyclerView
    private lateinit var aboutAdapter: AboutAdapter
    private var takeRestCount = 0
    private var maxRestCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTvSite = tv_site
        mIv = iv_img
        mRecyclerView = recycler_view
        aboutAdapter = AboutAdapter()
        initData()
        toolbar?.setNavigationOnClickListener { v -> onBackPressed() }
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = aboutAdapter
        aboutAdapter.setOnItemClickListener(this)
        GlideApp.with(this)
            .load(R.mipmap.ic_launcher_round)
            .centerCrop()
            .into(mIv)
        mIv.setOnClickListener {
            if (mIv.rotation != 3600f * maxRestCount) {
                mIv.animate()
                    .rotation(mIv.rotation + 3600f)
                    .setDuration(2000)
                    .setInterpolator(FastOutSlowInInterpolator())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            mIv.isEnabled = true
                        }

                        override fun onAnimationStart(animation: Animator?) {
                            mIv.isEnabled = false
                        }
                    })
                    .start()
                FirebaseUtils.logEggAbout(this@AboutActivity)
            } else {
                if (!EggsHelper.showCenterScaleView(this)) {
                    return@setOnClickListener
                }
                takeRestCount++
                if (takeRestCount >= maxRestCount) {
                    maxRestCount++
                    takeRestCount = 0
                    mIv.rotation = 0f
                }
                FirebaseUtils.logEggAbout(this@AboutActivity)
            }
        }
        tv_site.setOnClickListener(this)
        iv_img.setOnClickListener(this)
        tv_about.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_site, R.id.iv_img -> JumpUtils.jumpToWeb(this, "https://github.com/lovejjfg")
            R.id.tv_about -> JumpUtils.jumpToWeb(this, "https://android-arsenal.com/")
        }
    }

    override fun onItemClick(holder: PowerHolder<Library>, position: Int, item: Library) {
        JumpUtils.jumpToWeb(this, item.jumpUrl)
    }

    private fun initData() {
        val libraries = ArrayList<Library>()
        libraries.add(
            Library(
                "AndResGuard",
                "proguard resource for Android by wechat team.",
                "https://github.com/shwenzhang/AndResGuard"
            )
        )
        libraries.add(
            Library(
                "Android support libraries",
                "The Android support libraries offer a number of features that are not built into the framework.",
                "https://developer.android.com/topic/libraries/support-library"
            )
        )
        libraries.add(
            Library(
                "ButterKnife",
                "Bind Android views and callbacks to fields and methods.",
                "http://jakewharton.github.io/butterknife/"
            )
        )
        libraries.add(
            Library(
                "Glide",
                "An image loading and caching library for Android focused on smooth scrolling.",
                "https://github.com/bumptech/glide"
            )
        )
        libraries.add(
            Library(
                "gradle-retrolambda",
                "A gradle plugin for getting java lambda support in java 6, 7 and android.",
                "https://github.com/evant/gradle-retrolambda"
            )
        )
        libraries.add(
            Library(
                "JSoup",
                "Java HTML Parser, with best of DOM, CSS, and jquery.",
                "https://github.com/jhy/jsoup/"
            )
        )
        libraries.add(
            Library(
                "leakcanary",
                "A memory leak detection library for Android and Java.",
                "https://github.com/square/leakcanary"
            )
        )
        libraries.add(
            Library(
                "MaterialSearchView",
                "Cute library to implement SearchView in a Material Design Approach .",
                "http://miguelcatalan.info/2015/09/23/MaterialSearchView/"
            )
        )
        libraries.add(
            Library(
                "OkHttp",
                "An HTTP & HTTP/2 client for Android and Java applications.",
                "http://square.github.io/okhttp/"
            )
        )
        libraries.add(
            Library(
                "PowerRecyclerView",
                "Easy for RecyclerView to pull refresh and load more.",
                "https://github.com/lovejjfg/PowerRecyclerView"
            )
        )
        libraries.add(
            Library(
                "Retrofit",
                "A type-safe HTTP client for Android and Java.",
                "http://square.github.io/retrofit/"
            )
        )
        libraries.add(
            Library(
                "RxAndroid",
                "RxJava bindings for Android.",
                "https://github.com/ReactiveX/RxAndroid"
            )
        )
        libraries.add(
            Library(
                "RxJava",
                "RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs" + " using observable sequences for the Java VM.",
                "https://github.com/ReactiveX/RxJava"
            )
        )

        aboutAdapter.setList(libraries)
    }

    override fun initLayoutRes(): Int {
        return R.layout.activity_about
    }

    class AboutAdapter : PowerAdapter<Library>() {
        override fun onViewHolderBind(holder: PowerHolder<Library>, position: Int) {
            holder.onBind(list[position])
        }

        override fun onViewHolderCreate(parent: ViewGroup, viewType: Int): PowerHolder<Library> {
            return AboutHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.layout_about_info, parent, false)
            )
        }
    }

    internal class AboutHolder(override val containerView: View) : PowerHolder<Library>(containerView),
        LayoutContainer {

        override fun onBind(library: Library) {
            tv_name.text = library.name
            tv_des.text = library.des
        }
    }
}
