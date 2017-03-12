package com.lovejjfg.arsenal.ui;

import android.os.Bundle;

import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.base.SupportActivity;

public class ArsenalSearchActivity extends SupportActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            ArsenalListInfoFragment listInfoFragment = new ArsenalListInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ArsenalListInfoFragment.TYPE_NAME, getIntent().getIntExtra(ArsenalListInfoFragment.TYPE_NAME, ArsenalListInfoFragment.TYPE_SEARCH_TAG));
            bundle.putString(ArsenalListInfoFragment.KEY, getIntent().getStringExtra(ArsenalListInfoFragment.KEY));
            listInfoFragment.setArguments(bundle);
            loadRoot(R.id.activity_tag_search, listInfoFragment);
        }

    }

    @Override
    public int initLayoutRes() {
        return R.layout.activity_tag_search;
    }

}
