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

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.lovejjfg.arsenal.R
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo.ListInfo
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo
import com.lovejjfg.arsenal.api.mode.SearchInfo
import com.lovejjfg.arsenal.base.BaseFragment
import com.lovejjfg.arsenal.ui.contract.HomeListInfoPresenter
import com.lovejjfg.arsenal.ui.contract.ListInfoContract
import com.lovejjfg.arsenal.ui.contract.SearchListInfoPresenter
import com.lovejjfg.arsenal.ui.contract.TagSearchListInfoPresenter
import com.lovejjfg.arsenal.ui.contract.UserDetailListInfoPresenter
import com.lovejjfg.arsenal.utils.JumpUtils
import com.lovejjfg.arsenal.utils.TagUtils
import com.lovejjfg.arsenal.utils.UIUtils
import com.lovejjfg.arsenal.utils.rxbus.RxBus
import com.lovejjfg.arsenal.utils.rxbus.SearchEvent
import com.lovejjfg.powerrecycle.AdapterLoader
import com.lovejjfg.powerrecycle.OnLoadMoreListener
import com.lovejjfg.powerrecycle.holder.PowerHolder
import kotlinx.android.synthetic.main.fragment_list_info.refreshHeader
import kotlinx.android.synthetic.main.fragment_list_info.refreshList
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.ArrayList

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

class ArsenalListInfoFragment : BaseFragment<ListInfoContract.Presenter>(),
    AdapterLoader.OnItemClickListener<ArsenalListInfo.ListInfo>,
    OnLoadMoreListener, ListInfoContract.View {
    private lateinit var listInfoAdapter: ArsenalListInfoAdapter
    private var mType: Int = 0

    override fun initLayoutRes(): Int {
        return R.layout.fragment_list_info
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val subscription = RxBus.getInstance()
            .toObservable(SearchEvent::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .distinctUntilChanged()
            .subscribe({ this.onSearchEvent(it) }, { throwable ->

            })
        RxBus.getInstance().addSubscription(this, subscription)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var beans: ArrayList<ArsenalListInfo.ListInfo>? = arguments!!.getParcelableArrayList(ARSENAL_LIST_INFO)
        if (savedInstanceState != null) {
            beans = savedInstanceState.getParcelableArrayList<ListInfo>(ARSENAL_LIST_INFO)
        }
        val key = arguments!!.getString(KEY)
        refreshList.layoutManager = LinearLayoutManager(context)
        listInfoAdapter = ArsenalListInfoAdapter(mPresenter)
        listInfoAdapter.totalCount = Integer.MAX_VALUE
        refreshList.adapter = listInfoAdapter
        listInfoAdapter.setOnItemClickListener(this)
        listInfoAdapter.setLoadMoreListener(this)
        refreshHeader.setOnRefreshListener {
            mPresenter.onRefresh()
        }

        mPresenter.onViewPrepared()
        if (beans != null && !beans.isEmpty()) {
            listInfoAdapter.setList(beans)
        } else if (!TextUtils.isEmpty(key)) {
            mPresenter.startSearch(key)
        } else {
            mPresenter.onRefresh()
        }
        if (mType != TYPE_USER_DETAIL) {
            val toolbar = toolbar
            toolbar?.setOnClickListener { v ->
                if (UIUtils.doubleClick()) {
                    refreshList.scrollToPosition(0)
                }
            }
        }
    }

    override fun onLoadMore() {
        mPresenter.onLoadMore()
    }

    override fun onItemClick(holder: PowerHolder<ListInfo>, position: Int, item: ListInfo) {
        mPresenter.onItemClick(holder.itemView, item)
    }

    override fun onRefresh(info: ArsenalListInfo) {
        listInfoAdapter.setList(info.infos)
        refreshList.scrollToPosition(0)
        if (!TagUtils.isSaveTag(info.tags.size)) {
            val entries = info.tags.entries
            val infos = ArrayList<SearchInfo>()
            for ((key, value) in entries) {
                infos.add(SearchInfo(key, TYPE_SEARCH_TAG, value))
            }
            val save = TagUtils.save(infos)
            Log.e("TAG", "插入成功: $save")
        } else {
            Log.e("TAG", "已经是最新的！！ ")
        }
    }

    override fun onLoadMore(info: ArsenalListInfo) {
        listInfoAdapter.appendList(info.infos)
    }

    override fun jumpToTarget(userInfo: ArsenalUserInfo) {
        JumpUtils.jumpToUserDetail(activity, userInfo, null)
    }

    override fun atEnd() {
        listInfoAdapter.totalCount = listInfoAdapter.itemRealCount
        listInfoAdapter.enableLoadMore = true
    }

    override fun onRefresh(refresh: Boolean) {
        refreshHeader.isRefreshing = refresh
    }

    override fun setPullRefreshEnable(enable: Boolean) {
        refreshHeader.isEnabled = enable
    }

    override fun onSearchEvent(event: SearchEvent) {
        if (mType == TYPE_HOME) {
            return
        }
        if (mType == event.type) {
            mPresenter.startSearch(event.key)
            return
        }
        mType = event.type
        mPresenter.onDestroy()
        when (mType) {
            TYPE_SEARCH -> mPresenter = SearchListInfoPresenter(this)
            TYPE_SEARCH_TAG -> mPresenter = TagSearchListInfoPresenter(this)
        }
        mPresenter.startSearch(event.key)
    }

    override fun loadMoreError() {
        listInfoAdapter.loadMoreError()
    }

    override fun initPresenter(): ListInfoContract.Presenter {
        mType = arguments!!.getInt(TYPE_NAME)
        when (mType) {
            TYPE_SEARCH -> return SearchListInfoPresenter(this)
            TYPE_SEARCH_TAG -> return TagSearchListInfoPresenter(this)
            TYPE_USER_DETAIL -> return UserDetailListInfoPresenter(this)
            TYPE_HOME -> return HomeListInfoPresenter(this)
            else -> return HomeListInfoPresenter(this)
        }
    }

    override fun showErrorView() {
        listInfoAdapter.showError()
    }

    override fun showEmptyView() {
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mPresenter.onSaveInstanceState(outState)
        outState.putParcelableArrayList(
            ARSENAL_LIST_INFO,
            listInfoAdapter.list as ArrayList<ArsenalListInfo.ListInfo>
        )
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        RxBus.getInstance().unSubscribe(this)
        super.onDestroy()
    }

    companion object {
        const val ARSENAL_LIST_INFO = "ARSENAL_LIST_INFO"
        const val TYPE_NAME = "TYPE_NAME"
        const val KEY = "KEY"
        const val TAG_NAME = "key_name"
        const val TYPE_HOME = 0
        const val TYPE_SEARCH = 1
        const val TYPE_SEARCH_TAG = 2
        const val TYPE_USER_DETAIL = 3
    }
}
