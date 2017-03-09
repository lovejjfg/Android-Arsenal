package com.lovejjfg.arsenal.ui;

import android.os.Bundle;

import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.base.SupportActivity;

public class TagSearchActivity extends SupportActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListInfoFragment listInfoFragment = new ListInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ListInfoFragment.TYPE_NAME, ListInfoFragment.TYPE_SEARCH_TAG);
        bundle.putString(ListInfoFragment.KEY, getIntent().getStringExtra(ListInfoFragment.KEY));
        listInfoFragment.setArguments(bundle);
        loadRoot(R.id.activity_tag_search, listInfoFragment);
    }

    @Override
    public int initLayoutRes() {
        return R.layout.activity_tag_search;
    }

}
