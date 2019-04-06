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

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.transition.TransitionManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import com.lovejjfg.arsenal.R
import com.lovejjfg.arsenal.api.mode.SearchInfo
import com.lovejjfg.arsenal.base.SupportActivity
import com.lovejjfg.arsenal.utils.EggsHelper
import com.lovejjfg.arsenal.utils.JumpUtils
import com.lovejjfg.arsenal.utils.TagUtils
import com.lovejjfg.arsenal.utils.UIUtils
import com.lovejjfg.arsenal.utils.rxbus.RxBus
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.main_container
import kotlinx.android.synthetic.main.activity_main.search_view

class ArsenalHomeActivity : SupportActivity() {

    lateinit var searchView: MaterialSearchView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        searchView = search_view
        setSupportActionBar(toolbar)
        toolbar?.setOnClickListener {
            if (UIUtils.thirdClick()) {
                EggsHelper.showRandomEgg(this)
            } else if (UIUtils.doubleClick()) {
                try {
                    ((main_container[0] as ViewGroup)[0] as RecyclerView).layoutManager?.scrollToPosition(0)
                } catch (e: Exception) {
                }
            }
        }

        if (savedInstanceState == null) {
            val listInfoFragment = ArsenalListInfoFragment()
            loadRoot(R.id.main_container, listInfoFragment)
        }
        searchView.setVoiceSearch(false)
        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                val strings = TagUtils.tagArray
                if (strings != null && strings.isNotEmpty()) {
                    searchView.setSuggestions(strings) { title ->
                        searchView.closeSearch()
                        val s = TagUtils.getTagValue(title)
                        if (TextUtils.isEmpty(s)) {
                            JumpUtils.jumpToSearchList(searchView.context, title)
                        } else {
                            JumpUtils.jumpToSearchList(searchView.context, title, "/tag/" + s)
                        }
                    }
                }
            }

            override fun onSearchViewClosed() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(toolbar?.parent as ViewGroup)
                }
            }

            override fun onSearchViewAnimationStart() {
            }
        })

        searchView.setEllipsize(true)

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val searchInfo = SearchInfo(query, ArsenalListInfoFragment.TYPE_SEARCH, null)
                TagUtils.save(searchInfo)
                searchView.closeSearch()
                JumpUtils.jumpToSearchList(this@ArsenalHomeActivity, query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //Do some magic
                return false
            }
        })
    }

    override fun initLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                JumpUtils.jumpToAbout(this)
            }
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

    companion object {
        private val TAG = ArsenalHomeActivity::class.java.simpleName
    }
}
