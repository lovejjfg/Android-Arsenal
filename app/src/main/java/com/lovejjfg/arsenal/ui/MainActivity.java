package com.lovejjfg.arsenal.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.lovejjfg.arsenal.R;
import com.lovejjfg.arsenal.base.SupportActivity;
import com.lovejjfg.arsenal.utils.JumpUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends SupportActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    private String[] stringArray;
    private ListInfoFragment listInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        if (savedInstanceState == null) {
            listInfoFragment = new ListInfoFragment();
            loadRoot(R.id.main_container, listInfoFragment);
        } else {
            listInfoFragment = findFragment(ListInfoFragment.class);
        }
        searchView.setVoiceSearch(false);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                final HashMap<String, String> tags = listInfoFragment.getArsenalInfo().getTags();
                Set<String> strings = tags.keySet();
                stringArray = strings.toArray(new String[50]);
//                String[] stringArray = MainActivity.this.getResources().getStringArray(R.array.query_suggestions);
                searchView.setSuggestions(stringArray, new MaterialSearchView.SuggestionsListCallBack() {
                    @Override
                    public void onItemClick(String title) {
                        String s = tags.get(title);
                        JumpUtils.jumpToTagList(searchView.getContext(), "/tag/" + s, ListInfoFragment.TYPE_SEARCH_TAG);
                    }
                });
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        searchView.setEllipsize(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e(TAG, "onQueryTextSubmit: " + query);
                JumpUtils.jumpToTagList(MainActivity.this, query, ListInfoFragment.TYPE_SEARCH);
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
}
