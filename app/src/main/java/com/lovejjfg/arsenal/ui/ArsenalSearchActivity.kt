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

package com.lovejjfg.arsenal.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.BounceInterpolator
import com.lovejjfg.arsenal.R
import com.lovejjfg.arsenal.api.mode.SearchInfo
import com.lovejjfg.arsenal.base.SupportActivity
import com.lovejjfg.arsenal.utils.FirebaseUtils
import com.lovejjfg.arsenal.utils.JumpUtils
import com.lovejjfg.arsenal.utils.TagUtils
import com.lovejjfg.arsenal.utils.rxbus.RxBus
import com.lovejjfg.arsenal.utils.rxbus.SearchEvent
import com.lovejjfg.arsenal.utils.transationHint
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_tag_search.activity_tag_search
import kotlinx.android.synthetic.main.activity_tag_search.search_view

class ArsenalSearchActivity : SupportActivity(), View.OnClickListener {
    lateinit var searchView: MaterialSearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchView = search_view
        setSupportActionBar(toolbar)
        val currentTag = intent.getStringExtra(ArsenalListInfoFragment.KEY)
        val currentTagName = intent.getStringExtra(ArsenalListInfoFragment.TAG_NAME)
        supportActionBar?.title = if (TextUtils.isEmpty(currentTagName)) currentTag else currentTagName
        toolbar?.setNavigationOnClickListener(this)

        if (savedInstanceState == null) {
            val listInfoFragment = ArsenalListInfoFragment()
            val bundle = Bundle()
            bundle.putInt(
                ArsenalListInfoFragment.TYPE_NAME, intent.getIntExtra(
                    ArsenalListInfoFragment.TYPE_NAME,
                    ArsenalListInfoFragment.TYPE_SEARCH_TAG
                )
            )
            bundle.putString(ArsenalListInfoFragment.KEY, currentTag)
            listInfoFragment.arguments = bundle
            loadRoot(R.id.activity_tag_search, listInfoFragment)
        }
        //        setSupportActionBar(mToolbar);

        searchView.setVoiceSearch(false)
        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                val strings = TagUtils.tagArray
                if (strings != null) {
                    searchView.setSuggestions(strings) { title ->
                        if (searchView.isSearchOpen) {
                            searchView.closeSearch()
                            val s = TagUtils.getTagValue(title)
                            if (TextUtils.isEmpty(s)) {
                                JumpUtils.jumpToSearchList(searchView.context, title)
                            } else {
                                JumpUtils.jumpToSearchList(searchView.context, title, "/tag/" + s!!)
                            }
                        }
                    }
                }
            }

            override fun onSearchViewClosed() {
                //Do some magic
                //                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //                    TransitionManager.beginDelayedTransition((ViewGroup) mToolbar.getParent(), new Fade(Fade.IN));
                //                }
                //                mToolbar.setVisibility(View.VISIBLE);
            }

            override fun onSearchViewAnimationStart() {
                //                mToolbar.setVisibility(View.INVISIBLE);
            }
        })

        searchView.setEllipsize(true)

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.closeSearch()
                val searchInfo = SearchInfo(query, ArsenalListInfoFragment.TYPE_SEARCH, null)
                TagUtils.save(searchInfo)
                JumpUtils.jumpToSearchList(this@ArsenalSearchActivity, query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //Do some magic
                return false
            }
        })
    }

    override fun initLayoutRes(): Int {
        return R.layout.activity_tag_search
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        //        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                if (searchView.menuItem == null) {
                    val searchMenuView = toolbar?.findViewById<View>(R.id.action_search)
                    searchView.menuItem = searchMenuView
                }
                if (!searchView.isSearchOpen) {
                    searchView.showSearch()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (searchView.isSearchOpen) {
            searchView.closeSearch()
            return
        }
        super.onBackPressed()
    }

    override fun onClick(v: View) {
        onBackPressed()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val currentTag = intent.getStringExtra(ArsenalListInfoFragment.KEY)
        val currentTagName = intent.getStringExtra(ArsenalListInfoFragment.TAG_NAME)
        val type = intent.getIntExtra(
            ArsenalListInfoFragment.TYPE_NAME,
            ArsenalListInfoFragment.TYPE_SEARCH_TAG
        )
        val title = supportActionBar?.title
        println("title:$title;;currentTag: $currentTag || currentTagName: $currentTagName")
        if (title == currentTag || title == currentTagName) {
            activity_tag_search.transationHint()
        } else {
            val t = if (TextUtils.isEmpty(currentTagName)) currentTag else currentTagName
            supportActionBar?.title = t
            RxBus.getInstance().post(SearchEvent(currentTag, currentTagName, type))
            FirebaseUtils.logSearchEvent(this@ArsenalSearchActivity,t)
        }
    }
}
