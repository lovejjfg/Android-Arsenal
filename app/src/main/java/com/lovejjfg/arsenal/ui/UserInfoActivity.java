package com.lovejjfg.arsenal.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class UserInfoActivity extends SupportActivity {
    private static final String TAG = UserInfoActivity.class.getSimpleName();
    public static final String USER_INFO = "UserInfo";
    @Bind(R.id.iv_img)
    ImageView mIvPortrait;
    @Bind(R.id.tv_location)
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        circleTransform = new CircleTransform(this);
        setSupportActionBar(toolbar);
        ArsenalUserInfo info = getIntent().getParcelableExtra(USER_INFO);
        if (info == null) {
            finish();
        } else {
            refreshUI(info);
        }

    }

    private void refreshUI(ArsenalUserInfo info) {
        Log.e(TAG, "refreshUI: " + info.getPortraitUrl());
        Glide.with(UserInfoActivity.this).load(info.getPortraitUrl()).transform(circleTransform).into(mIvPortrait);
        getSupportActionBar().setTitle(info.getUserName());
        mTvFlowers.setText(info.getFollowers());
        mTvFlowing.setText(info.getFollowing());
        mTvRepo.setText(info.getPublicRepo());
        mTvLocation.setText(info.getLocation());
        mTvSite.setText(info.getSite());

        ArrayList<ArsenalListInfo.ListInfo> contributions = info.getContributions();
        ArrayList<ArsenalListInfo.ListInfo> ownProjects = info.getOwnProjects();
        Bundle contribution = new Bundle();
        contribution.putParcelableArrayList(UserDetailListInfoFragment.ARSENAL_LIST_INFO, contributions);
        Bundle ownProject = new Bundle();
        ownProject.putParcelableArrayList(UserDetailListInfoFragment.ARSENAL_LIST_INFO, ownProjects);
        ArrayList<SupportFragment> fragments = new ArrayList<>();
        UserDetailListInfoFragment ownProFragment = new UserDetailListInfoFragment();
        ownProFragment.setArguments(ownProject);
        UserDetailListInfoFragment contributionFragment = new UserDetailListInfoFragment();
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
}
