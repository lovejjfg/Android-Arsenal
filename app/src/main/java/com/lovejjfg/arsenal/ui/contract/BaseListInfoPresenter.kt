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

package com.lovejjfg.arsenal.ui.contract

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lovejjfg.arsenal.api.DataManager
import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo
import com.lovejjfg.arsenal.base.BasePresenterImpl
import com.lovejjfg.arsenal.utils.JumpUtils
import rx.functions.Action1

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

abstract class BaseListInfoPresenter constructor(view: ListInfoContract.View) :
    BasePresenterImpl<ListInfoContract.View>(view), ListInfoContract.Presenter {
    protected var mCurrentKey: String? = null
    protected var mHasMore: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mCurrentKey = savedInstanceState.getString(CURRENT_KEY)
            mHasMore = savedInstanceState.getString(HAS_MORE)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onItemClick(itemView: View, info: ArsenalListInfo.ListInfo) {
        mView.showLoadingDialog(null)
        DataManager.handleNormalService<ArsenalDetailInfo>(
            DataManager.getArsenalApi().getArsenalDetailInfo(info.listDetailUrl),
            Action1 { data ->
                JumpUtils.jumpToDetail(mView.context, data, itemView)
                mView.closeLoadingDialog()
            },
            this
        )
    }

    override fun onItemClick(user: String) {
        mView.showLoadingDialog(null)
        DataManager.handleNormalService<ArsenalUserInfo>(
            DataManager.getArsenalApi().getArsenalUserInfo(user),
            Action1 { info ->
                mView.jumpToTarget(info)
                mView.closeLoadingDialog()
            },
            this
        )
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(CURRENT_KEY, mCurrentKey)
        outState?.putString(HAS_MORE, mHasMore)
        super.onSaveInstanceState(outState)
    }

    override fun startSearch(key: String) {
        if (TextUtils.equals(mCurrentKey, key)) {
            return
        }
        mCurrentKey = key
        onRefresh()
    }

    override fun call(throwable: Throwable) {
        mView.onRefresh(false)
        super.call(throwable)
    }

    override fun onViewPrepared() {
    }

    companion object {
        private const val CURRENT_KEY = "currentKey"
        private const val HAS_MORE = "hasMore"
    }
}
