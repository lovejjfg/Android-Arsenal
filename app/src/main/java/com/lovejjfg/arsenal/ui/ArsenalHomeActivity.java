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

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.api.mode.SearchInfo;
import com.lovejjfg.arsenal.base.SupportActivity;
import com.lovejjfg.arsenal.utils.JumpUtils;
import com.lovejjfg.arsenal.utils.TagUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArsenalHomeActivity extends SupportActivity {
    private static final String TAG = ArsenalHomeActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ArsenalListInfoFragment listInfoFragment;
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
                if (strings != null && strings.length > 0) {
//                String[] stringArray = MainActivity.this.getResources().getStringArray(R.array.query_suggestions);
                    searchView.setSuggestions(strings, title -> {
                        searchView.closeSearch();
                        String s = TagUtils.getTagValue(title);
                        if (TextUtils.isEmpty(s)) {
                            JumpUtils.jumpToSearchList(searchView.getContext(), title);
                        } else {
                            JumpUtils.jumpToSearchList(searchView.getContext(), title, "/tag/" + s);
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
//                mToolbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewAnimationStart() {
//                mToolbar.setVisibility(View.GONE);
            }
        });

        searchView.setEllipsize(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchInfo searchInfo = new SearchInfo(query, ArsenalListInfoFragment.TYPE_SEARCH, null);
                TagUtils.save(searchInfo);
                searchView.closeSearch();
                JumpUtils.jumpToSearchList(ArsenalHomeActivity.this, query);
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
    protected void onResume() {
        super.onResume();
        // Set screen name.
        mTracker.setScreenName("HomeActivity");

    }

    @Override
    public int initLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
//        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                // [START custom_event]
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("About")
                        .build());
                // [END custom_event]
                JumpUtils.jumpToAbout(this);
                break;
            case R.id.action_search:
                if (searchView.getMenuItem() == null) {
                    View searchMenuView = mToolbar.findViewById(R.id.action_search);
                    searchView.setMenuItem(searchMenuView);
                }
                if (!searchView.isSearchOpen()) {
                    // [START custom_event]
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Action")
                            .setAction("Search")
                            .build());
                    // [END custom_event]
                    searchView.showSearch();
                }
                break;
        }
        return super.onOptionsItemSelected(item);

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
