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

import com.lovejjfg.arsenal.api.mode.ArsenalListInfo
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo
import com.lovejjfg.arsenal.base.BasePresenter
import com.lovejjfg.arsenal.base.IBaseView
import com.lovejjfg.arsenal.utils.rxbus.SearchEvent

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

interface ListInfoContract {
    interface View : IBaseView<Presenter> {
        fun onRefresh(info: ArsenalListInfo)

        fun onLoadMore(info: ArsenalListInfo)

        fun jumpToTarget(userInfo: ArsenalUserInfo)

        fun atEnd()

        fun onRefresh(refresh: Boolean)

        fun setPullRefreshEnable(enable: Boolean)

        fun onSearchEvent(event: SearchEvent)

        fun loadMoreError()
    }

    interface Presenter : BasePresenter {
        fun onItemClick(itemView: android.view.View, info: ArsenalListInfo.ListInfo)

        fun onItemClick(user: String)

        fun onRefresh()

        fun onLoadMore()

        fun startSearch(key: String)
    }
}
