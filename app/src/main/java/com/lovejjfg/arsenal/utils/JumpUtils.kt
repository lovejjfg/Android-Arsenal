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

package com.lovejjfg.arsenal.utils

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.util.Pair
import android.view.View

import com.lovejjfg.arsenal.R
import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo
import com.lovejjfg.arsenal.ui.AboutActivity
import com.lovejjfg.arsenal.ui.ArsenalDetailInfoActivity
import com.lovejjfg.arsenal.ui.ArsenalListInfoFragment
import com.lovejjfg.arsenal.ui.ArsenalSearchActivity
import com.lovejjfg.arsenal.ui.ArsenalUserInfoActivity

/**
 * Created by Joe on 2016/3/18.
 * Email lovejjfg@gmail.com
 */
object JumpUtils {
    fun jumpToUserDetail(context: Context?, info: ArsenalUserInfo, mView: View?) {
        val intent = Intent(context, ArsenalUserInfoActivity::class.java)
        intent.putExtra(ArsenalUserInfoActivity.USER_INFO, info)
        if (context is Activity && mView != null) {
            val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                context,
                Pair.create(mView.parent as View, context.getString(R.string.name_app_bar)),
                Pair.create(mView.parent as View, context.getString(R.string.container))
            )
            ActivityCompat.startActivity(context, intent, activityOptions.toBundle())
        } else {
            context?.startActivity(intent)
        }
    }

    fun jumpToSearchList(context: Context, tagKey: String) {
        val intent = Intent(context, ArsenalSearchActivity::class.java)
        intent.putExtra(ArsenalListInfoFragment.KEY, tagKey)
        intent.putExtra(
            ArsenalListInfoFragment.TYPE_NAME,
            ArsenalListInfoFragment.TYPE_SEARCH
        )
        if (context is Activity) {
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(context)
            ActivityCompat.startActivity(context, intent, activityOptions.toBundle())
        } else {
            context.startActivity(intent)
        }
    }

    fun jumpToSearchList(context: Context, tagName: String, tagKey: String) {
        val intent = Intent(context, ArsenalSearchActivity::class.java)
        intent.putExtra(ArsenalListInfoFragment.KEY, tagKey)
        intent.putExtra(ArsenalListInfoFragment.TAG_NAME, tagName)
        intent.putExtra(
            ArsenalListInfoFragment.TYPE_NAME,
            ArsenalListInfoFragment.TYPE_SEARCH_TAG
        )
        if (context is Activity) {
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(context)
            ActivityCompat.startActivity(context, intent, activityOptions.toBundle())
        } else {
            context.startActivity(intent)
        }
    }

    fun jumpToDetail(context: Context?, detailUrl: ArsenalDetailInfo, mView: View) {
        val c = context ?: return
        val intent = Intent(context, ArsenalDetailInfoActivity::class.java)
        intent.putExtra(ArsenalDetailInfoActivity.INFO, detailUrl)
        if (c is Activity) {
            val options = ActivityOptions.makeSceneTransitionAnimation(
                c,
                Pair.create(mView, c.getString(R.string.container)),
                Pair.create(mView, c.getString(R.string.name_app_bar))
            )
            c.startActivity(intent, options.toBundle())
        } else {
            c.startActivity(intent)
        }
    }

    fun jumpToAbout(context: Context) {
        val intent = Intent(context, AboutActivity::class.java)
        if (context is Activity) {
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(context)
            ActivityCompat.startActivity(context, intent, activityOptions.toBundle())
        } else {
            context.startActivity(intent)
        }
    }

    fun jumpToWeb(context: Context, url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val uri = Uri.parse(url)
        intent.data = uri
        context.startActivity(Intent.createChooser(intent, url))
    }
}
