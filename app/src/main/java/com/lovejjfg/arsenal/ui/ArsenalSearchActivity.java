package com.lovejjfg.arsenal.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.base.SupportActivity;
import com.lovejjfg.arsenal.utils.JumpUtils;
import com.lovejjfg.arsenal.utils.TagUtils;
import com.lovejjfg.arsenal.utils.rxbus.RxBus;
import com.lovejjfg.arsenal.utils.rxbus.SearchEvent;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArsenalSearchActivity extends SupportActivity implements View.OnClickListener {
    private static final String TAG = ArsenalSearchActivity.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        String currentTag = getIntent().getStringExtra(ArsenalListInfoFragment.KEY);
        String currentTagName = getIntent().getStringExtra(ArsenalListInfoFragment.TAG_NAME);
        getSupportActionBar().setTitle(TextUtils.isEmpty(currentTagName) ? currentTag : currentTagName);
        mToolbar.setNavigationOnClickListener(this);

        if (savedInstanceState == null) {
            ArsenalListInfoFragment listInfoFragment = new ArsenalListInfoFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ArsenalListInfoFragment.TYPE_NAME, getIntent().getIntExtra(ArsenalListInfoFragment.TYPE_NAME, ArsenalListInfoFragment.TYPE_SEARCH_TAG));
            bundle.putString(ArsenalListInfoFragment.KEY, currentTag);
            listInfoFragment.setArguments(bundle);
            loadRoot(R.id.activity_tag_search, listInfoFragment);
        }
//        setSupportActionBar(mToolbar);

        searchView.setVoiceSearch(false);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                String[] strings = TagUtils.getTagArray();
                if (strings != null) {
                    searchView.setSuggestions(strings, new MaterialSearchView.SuggestionsListCallBack() {
                        @Override
                        public void onItemClick(String title) {
                            String s = TagUtils.getTagValue(title);
                            JumpUtils.jumpToSearchList(searchView.getContext(), title, "/tag/" + s, ArsenalListInfoFragment.TYPE_SEARCH_TAG);
                            searchView.closeSearch();
                        }
                    });
                }
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition((ViewGroup) mToolbar.getParent());
                }
                mToolbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewAnimationStart() {
                mToolbar.setVisibility(View.GONE);
            }
        });

        searchView.setEllipsize(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e(TAG, "onQueryTextSubmit: " + query);
                searchView.closeSearch();
                JumpUtils.jumpToSearchList(ArsenalSearchActivity.this, query, ArsenalListInfoFragment.TYPE_SEARCH);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });


    }

    @Override
    public int initLayoutRes() {
        return R.layout.activity_tag_search;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView.setMenuItem(myActionMenuItem);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String currentTag = intent.getStringExtra(ArsenalListInfoFragment.KEY);
        String currentTagName = intent.getStringExtra(ArsenalListInfoFragment.TAG_NAME);
        int type = intent.getIntExtra(ArsenalListInfoFragment.TYPE_NAME, ArsenalListInfoFragment.TYPE_SEARCH_TAG);
        getSupportActionBar().setTitle(TextUtils.isEmpty(currentTagName) ? currentTag : currentTagName);
        RxBus.getInstance().post(new SearchEvent(currentTag, currentTagName, type));
        super.onNewIntent(intent);
    }
}
