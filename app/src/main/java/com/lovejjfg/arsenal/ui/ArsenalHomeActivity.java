package com.lovejjfg.arsenal.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.base.SupportActivity;
import com.lovejjfg.arsenal.utils.JumpUtils;
import com.lovejjfg.arsenal.utils.TagUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.HashMap;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArsenalHomeActivity extends SupportActivity {
    private static final String TAG = ArsenalHomeActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    private ArsenalListInfoFragment listInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (savedInstanceState == null) {
            listInfoFragment = new ArsenalListInfoFragment();
            loadRoot(R.id.main_container, listInfoFragment);
        } else {
            listInfoFragment = findFragment(ArsenalListInfoFragment.class);
        }
        searchView.setVoiceSearch(false);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                String[] strings = TagUtils.getTagArray();
                if (strings !=null) {
//                String[] stringArray = MainActivity.this.getResources().getStringArray(R.array.query_suggestions);
                    searchView.setSuggestions(strings, new MaterialSearchView.SuggestionsListCallBack() {
                        @Override
                        public void onItemClick(String title) {
                            searchView.closeSearch();
                            String s = TagUtils.getTagValue(title);
                            JumpUtils.jumpToSearchList(searchView.getContext(),title, "/tag/" + s, ArsenalListInfoFragment.TYPE_SEARCH_TAG);
                        }
                    });
                }

            }

            @Override
            public void onSearchViewClosed() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition((ViewGroup) mToolbar.getParent());
                }
                //Do some magic
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
                JumpUtils.jumpToSearchList(ArsenalHomeActivity.this, query, ArsenalListInfoFragment.TYPE_SEARCH);
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
        return R.layout.activity_main;
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
}
