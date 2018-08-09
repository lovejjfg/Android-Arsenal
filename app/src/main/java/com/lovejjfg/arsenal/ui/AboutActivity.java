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

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.mode.Library;
import com.lovejjfg.arsenal.base.SupportActivity;
import com.lovejjfg.arsenal.utils.JumpUtils;
import com.lovejjfg.arsenal.utils.glide.GlideApp;
import com.lovejjfg.powerrecycle.AdapterLoader;
import com.lovejjfg.powerrecycle.PowerAdapter;
import java.util.ArrayList;

public class AboutActivity extends SupportActivity implements AdapterLoader.OnItemClickListener {

    @BindView(R.id.tv_site)
    TextView mTvSite;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.iv_img)
    ImageView mIv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private AboutAdapter aboutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        aboutAdapter = new AboutAdapter();
        initData();
        mToolBar.setNavigationOnClickListener(v -> onBackPressed());
        mRecyclerView.setAdapter(aboutAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aboutAdapter.setOnItemClickListener(this);
        GlideApp.with(this)
            .load(R.mipmap.ic_launcher)
            .centerCrop()
            .into(mIv);

//        String s = "<h2>Android Arsenal</h2><br><p>It get data from <a href=www.baidu.com>Android Arsenal</a>It is
// in no way an  official client for these services nor endorsed by them.</p>";
//        mTvSite.setText(Html.fromHtml(s));
    }

    @OnClick({ R.id.tv_site, R.id.iv_img, R.id.tv_about })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_site:
            case R.id.iv_img:
                JumpUtils.jumpToWeb(this, "https://github.com/lovejjfg");
                break;
            case R.id.tv_about:
                JumpUtils.jumpToWeb(this, "https://android-arsenal.com/");
                break;
        }
    }

    private void initData() {
        ArrayList<Library> libraries = new ArrayList<>();
        libraries.add(new Library("AndResGuard",
            "proguard resource for Android by wechat team.",
            "https://github.com/shwenzhang/AndResGuard"));
        libraries.add(new Library("Android support libraries",
            "The Android support libraries offer a number of features that are not built into the framework.",
            "https://developer.android.com/topic/libraries/support-library"));
        libraries.add(new Library("ButterKnife",
            "Bind Android views and callbacks to fields and methods.",
            "http://jakewharton.github.io/butterknife/"));
        libraries.add(new Library("Glide",
            "An image loading and caching library for Android focused on smooth scrolling.",
            "https://github.com/bumptech/glide"));
        libraries.add(new Library("gradle-retrolambda",
            "A gradle plugin for getting java lambda support in java 6, 7 and android.",
            "https://github.com/evant/gradle-retrolambda"));
        libraries.add(new Library("JSoup",
            "Java HTML Parser, with best of DOM, CSS, and jquery.",
            "https://github.com/jhy/jsoup/"));
        libraries.add(new Library("leakcanary",
            "A memory leak detection library for Android and Java.",
            "https://github.com/square/leakcanary"));
        libraries.add(new Library("MaterialSearchView",
            "Cute library to implement SearchView in a Material Design Approach .",
            "http://miguelcatalan.info/2015/09/23/MaterialSearchView/"));
        libraries.add(new Library("OkHttp",
            "An HTTP & HTTP/2 client for Android and Java applications.",
            "http://square.github.io/okhttp/"));
        libraries.add(new Library("PowerRecyclerView",
            "Easy for RecyclerView to pull refresh and load more.",
            "https://github.com/lovejjfg/PowerRecyclerView"));
        libraries.add(new Library("Retrofit",
            "A type-safe HTTP client for Android and Java.",
            "http://square.github.io/retrofit/"));
        libraries.add(new Library("RxAndroid",
            "RxJava bindings for Android.",
            "https://github.com/ReactiveX/RxAndroid"));
        libraries.add(new Library("RxJava",
            "RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs"
                + " using observable sequences for the Java VM.",
            "https://github.com/ReactiveX/RxJava"));

        aboutAdapter.setList(libraries);
    }

    @Override
    public int initLayoutRes() {
        return R.layout.activity_about;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        JumpUtils.jumpToWeb(this, aboutAdapter.getList().get(position).getJumpUrl());
    }

    static class AboutAdapter extends PowerAdapter<Library> {

        @Override
        public RecyclerView.ViewHolder onViewHolderCreate(ViewGroup parent, int viewType) {
            return new AboutHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_about_info, parent, false));
        }

        @Override
        public void onViewHolderBind(RecyclerView.ViewHolder holder, int position) {
            ((AboutHolder) holder).onBind(list.get(position));
        }
    }

    static class AboutHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTitle;
        @BindView(R.id.tv_des)
        TextView mDes;

        public AboutHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(Library library) {
            mTitle.setText(library.getName());
            mDes.setText(library.getDes());
        }
    }
}
