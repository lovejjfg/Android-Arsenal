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

import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.provider.Settings
import android.support.v4.view.ViewCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView

/**
 * Created by Joe on 2015/2/25.
 * Email lovejjfg@gmail.com
 */
object UIUtils {

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    fun canChildScrollUp(mTarget: View): Boolean {
        return if (Build.VERSION.SDK_INT < 14) {
            if (mTarget is AbsListView) {
                mTarget.childCount > 0 && (mTarget.firstVisiblePosition > 0 || mTarget.getChildAt(0)
                    .top < mTarget.paddingTop)
            } else {
                ViewCompat.canScrollVertically(mTarget, -1) || mTarget.scrollY > 0
            }
        } else {
            ViewCompat.canScrollVertically(mTarget, -1)
        }
    }

    fun setAccessibilityIgnore(view: View) {
        view.isClickable = false
        view.isFocusable = false
        view.contentDescription = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        }
    }

    fun inflate(resId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(resId, parent, false)
    }

    fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    fun getNavigationBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun hasNavigationBar(activity: Context): Boolean {
        var hasNavigationBar = false
        val rs = activity.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {
        }

        return hasNavigationBar
    }

    /**
     * 双击事件、多击事件
     */
    //存储时间的数组
    private val mTwiceHits = LongArray(2)
    private val mThirdsHits = LongArray(3)

    fun doubleClick(): Boolean {
        // 双击事件响应
        /**
         * arraycopy,拷贝数组
         * src 要拷贝的源数组
         * srcPos 源数组开始拷贝的下标位置
         * dst 目标数组
         * dstPos 开始存放的下标位置
         * length 要拷贝的长度（元素的个数）
         *
         */
        //实现数组的移位操作，点击一次，左移一位，末尾补上当前开机时间（cpu的时间）
        System.arraycopy(mTwiceHits, 1, mTwiceHits, 0, mTwiceHits.size - 1)
        mTwiceHits[mTwiceHits.size - 1] = SystemClock.uptimeMillis()
        //双击事件的时间间隔500ms
        return mTwiceHits[0] >= SystemClock.uptimeMillis() - 500
    }

    fun thirdClick(): Boolean {
        // 双击事件响应
        /**
         * arraycopy,拷贝数组
         * src 要拷贝的源数组
         * srcPos 源数组开始拷贝的下标位置
         * dst 目标数组
         * dstPos 开始存放的下标位置
         * length 要拷贝的长度（元素的个数）
         *
         */
        //实现数组的移位操作，点击一次，左移一位，末尾补上当前开机时间（cpu的时间）
        System.arraycopy(mThirdsHits, 1, mThirdsHits, 0, mThirdsHits.size - 1)
        mThirdsHits[mThirdsHits.size - 1] = SystemClock.uptimeMillis()
        //双击事件的时间间隔500ms
        return mThirdsHits[0] >= SystemClock.uptimeMillis() - 500
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun checkDontkeep(context: Activity) {
        val alwaysFinish =
            Settings.Global.getInt(context.contentResolver, Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0)
        if (alwaysFinish == 1) {
            val dialog: Dialog
            dialog = AlertDialog.Builder(context)
                .setMessage(
                    "由于您已开启‘不保留活动’,导致部分功能无法正常使用.我们建议您点击左下方'设置'按钮,在'开发者选项'中关闭'不保留活动'功能."
                )
                .setNegativeButton("取消") { dialog1, which -> dialog1.dismiss() }
                .setPositiveButton("设置") { dialog12, which ->
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS
                    )
                    context.startActivity(intent)
                }.create()
            dialog.show()
        }
    }
}
