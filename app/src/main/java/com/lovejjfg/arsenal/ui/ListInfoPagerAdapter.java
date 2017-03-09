package com.lovejjfg.arsenal.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lovejjfg.arsenal.base.SupportFragment;

import java.util.ArrayList;

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

public class ListInfoPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<SupportFragment> mFragments;
    private final String[] names = new String[]{"own","contribution"};

    public ListInfoPagerAdapter(FragmentManager fm, ArrayList<SupportFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return names[position];
    }
}
