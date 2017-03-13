package com.lovejjfg.arsenal.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;
import com.lovejjfg.arsenal.base.SupportActivity;
import com.lovejjfg.arsenal.base.SupportFragment;
import com.lovejjfg.arsenal.utils.glide.CircleTransform;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArsenalUserInfoActivity extends SupportActivity implements View.OnClickListener {
    private static final String TAG = ArsenalUserInfoActivity.class.getSimpleName();
    public static final String USER_INFO = "UserInfo";
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.iv_img)
    ImageView mIvPortrait;
    @Bind(R.id.tv_name)
    TextView mTvLocation;
    @Bind(R.id.tv_flowers)
    TextView mTvFlowers;
    @Bind(R.id.tv_flowing)
    TextView mTvFlowing;
    @Bind(R.id.tv_site)
    TextView mTvSite;
    @Bind(R.id.tv_repo)
    TextView mTvRepo;
    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    private CircleTransform circleTransform;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mToolBar.setNavigationOnClickListener(this);
        circleTransform = new CircleTransform(this);
        ArsenalUserInfo info = getIntent().getParcelableExtra(USER_INFO);
        if (savedInstanceState == null) {
            if (info == null) {
                finish();
            } else {
                refreshUI(info);
            }
        }

    }

    private void refreshUI(ArsenalUserInfo info) {
        Log.e(TAG, "refreshUI: " + info.getPortraitUrl());
        Glide.with(ArsenalUserInfoActivity.this).load(info.getPortraitUrl()).transform(circleTransform).into(mIvPortrait);
        mToolBar.setTitle(info.getUserName());
        mTvFlowers.setText(info.getFollowers());
        mTvFlowing.setText(info.getFollowing());
        mTvRepo.setText(info.getPublicRepo());
        mTvLocation.setText(info.getLocation());
        mTvSite.setText(info.getSite());
        mTvSite.setOnClickListener(this);

        ArrayList<ArsenalListInfo.ListInfo> contributions = info.getContributions();
        ArrayList<ArsenalListInfo.ListInfo> ownProjects = info.getOwnProjects();
        Bundle contribution = new Bundle();
        contribution.putParcelableArrayList(ArsenalListInfoFragment.ARSENAL_LIST_INFO, contributions);
        contribution.putInt(ArsenalListInfoFragment.TYPE_NAME, ArsenalListInfoFragment.TYPE_USER_DETAIL);
        Bundle ownProject = new Bundle();
        ownProject.putParcelableArrayList(ArsenalListInfoFragment.ARSENAL_LIST_INFO, ownProjects);
        ownProject.putInt(ArsenalListInfoFragment.TYPE_NAME, ArsenalListInfoFragment.TYPE_USER_DETAIL);
        ArrayList<SupportFragment> fragments = new ArrayList<>();
        ArsenalListInfoFragment ownProFragment = new ArsenalListInfoFragment();
        ownProFragment.setArguments(ownProject);
        ArsenalListInfoFragment contributionFragment = new ArsenalListInfoFragment();
        contributionFragment.setArguments(contribution);

        fragments.add(ownProFragment);
        fragments.add(contributionFragment);

        ListInfoPagerAdapter adapter = new ListInfoPagerAdapter(getSupportFragmentManager(), fragments);

        mViewpager.setAdapter(adapter);

        mTabs.setupWithViewPager(mViewpager);

    }

    @Override
    public int initLayoutRes() {
        return R.layout.activity_user_info;
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
}
