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

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lovejjfg.arsenal.R
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo
import com.lovejjfg.arsenal.ui.contract.ListInfoContract
import com.lovejjfg.arsenal.utils.JumpUtils
import com.lovejjfg.arsenal.utils.glide.GlideApp
import com.lovejjfg.arsenal.utils.glide.ImageTarget
import com.lovejjfg.powerrecycle.PowerAdapter
import com.lovejjfg.powerrecycle.holder.PowerHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_arsenal_info.iv_android
import kotlinx.android.synthetic.main.layout_arsenal_info.iv_img
import kotlinx.android.synthetic.main.layout_arsenal_info.ll_container
import kotlinx.android.synthetic.main.layout_arsenal_info.tv_date
import kotlinx.android.synthetic.main.layout_arsenal_info.tv_des
import kotlinx.android.synthetic.main.layout_arsenal_info.tv_free
import kotlinx.android.synthetic.main.layout_arsenal_info.tv_name
import kotlinx.android.synthetic.main.layout_arsenal_info.tv_new
import kotlinx.android.synthetic.main.layout_arsenal_info.tv_tag
import kotlinx.android.synthetic.main.layout_arsenal_info.tv_user

class ArsenalListInfoAdapter(private val mPresenter: ListInfoContract.Presenter) :
    PowerAdapter<ArsenalListInfo.ListInfo>() {

    override fun onViewHolderCreate(parent: ViewGroup, viewType: Int): PowerHolder<ArsenalListInfo.ListInfo> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_arsenal_info, parent, false)
        return ArsenalListInfoHolder(view, mPresenter)
    }

    override fun onViewHolderBind(holder: PowerHolder<ArsenalListInfo.ListInfo>, position: Int) {
        holder.onBind(list[position])
    }

    override fun onViewRecycled(holder: PowerHolder<ArsenalListInfo.ListInfo>) {
        if (holder is ArsenalListInfoHolder) {
            holder.img.visibility = View.GONE
        }
    }

    internal class ArsenalListInfoHolder(
        override val containerView: View,
        private val mPresenter: ListInfoContract.Presenter
    ) : PowerHolder<ArsenalListInfo.ListInfo>(containerView), LayoutContainer {

        private lateinit var mListInfo: ArsenalListInfo.ListInfo
        private val mTarget: ImageTarget

        var title: TextView = tv_name
        var tag: TextView = tv_tag
        var badgeFree: TextView = tv_free
        var badgeNew: TextView = tv_new
        var desc: TextView = tv_des
        var img: ImageView = iv_img
        var mContainer: LinearLayout = ll_container
        var registeredDate: TextView = tv_date
        var ivAndroid: ImageView = iv_android
        var tvUser: TextView = tv_user

        init {
            mTarget = ImageTarget(img)
        }

        override fun onBind(info: ArsenalListInfo.ListInfo) {
            mListInfo = info
            title.text = info.title
            tag.text = info.tag
            badgeFree.visibility = if (info.isBadgeFree) View.VISIBLE else View.GONE
            badgeNew.visibility = if (info.isBadgeNew) View.VISIBLE else View.GONE
            val infoDesc = info.desc
            initView(desc, infoDesc)
            if (!TextUtils.isEmpty(info.imgUrl)) {
                img.visibility = View.VISIBLE
                GlideApp.with(img.context)
                    .load(info.imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(mTarget)
            } else {
                img.visibility = View.GONE
            }
            initView(registeredDate, info.registeredDate)
            ivAndroid.visibility = if (info.isAndroid) View.VISIBLE else View.GONE
            tvUser.text = info.userName
            tvUser.visibility = if (info.isUser) View.VISIBLE else View.GONE
            tag.setOnClickListener { v -> JumpUtils.jumpToSearchList(tag.context, info.tag, info.tagUrl) }
            mContainer.setOnClickListener { v -> mPresenter.onItemClick(mListInfo.userDetailUrl) }
        }

        private fun initView(view: TextView, infoDesc: String?) {
            if (TextUtils.isEmpty(infoDesc)) {
                view.visibility = View.GONE
            } else {
                view.visibility = View.VISIBLE
                view.setText(infoDesc, TextView.BufferType.SPANNABLE)
            }
        }
    }
}

