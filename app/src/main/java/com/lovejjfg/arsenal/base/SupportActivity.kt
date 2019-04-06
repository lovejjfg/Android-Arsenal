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

package com.lovejjfg.arsenal.base

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.lovejjfg.arsenal.R
import com.lovejjfg.arsenal.ui.LoadingDialog
import com.lovejjfg.arsenal.utils.FragmentsUtil
import com.lovejjfg.arsenal.utils.KeyBoardUtil
import com.lovejjfg.arsenal.utils.ToastUtil

/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

abstract class SupportActivity : AppCompatActivity(), ISupportFragment, ISupportView {
    var fragmentsUtil: FragmentsUtil? = null
        private set
    private val progressDialog: LoadingDialog by lazy {
        LoadingDialog()
    }
    override var toolbar: Toolbar? = null

    override var topFragment: List<Fragment>? = if (fragmentsUtil != null) fragmentsUtil!!.topFragments else null

    override fun addToParent(
        containerViewId: Int, parent: SupportFragment, pos: Int,
        vararg children: SupportFragment
    ) {
        fragmentsUtil?.addToParent(containerViewId, parent, pos, *children)
    }

    override fun replaceToParent(containerViewId: Int, parent: SupportFragment, vararg children: SupportFragment) {
        fragmentsUtil?.replaceToParent(containerViewId, parent, *children)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentsUtil = FragmentsUtil(supportFragmentManager)
        setContentView(initLayoutRes())
        try {
            toolbar = findViewById(R.id.toolbar)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun initFragments(savedInstanceState: Bundle?, fragment: SupportFragment) {
        fragmentsUtil?.initFragments(savedInstanceState, fragment)
    }

    override fun <F : SupportFragment> findFragment(className: Class<F>): F? {
        return if (fragmentsUtil != null) fragmentsUtil!!.findFragment(className) else null
    }

    override fun <F : SupportFragment> findFragment(parentFragment: SupportFragment, className: Class<F>): F? {
        return if (fragmentsUtil != null) fragmentsUtil!!.findFragment(parentFragment, className) else null
    }

    override fun loadRoot(containerViewId: Int, vararg root: SupportFragment) {
        fragmentsUtil?.loadRoot(containerViewId, 0, *root)
    }

    override fun addToShow(from: SupportFragment, to: SupportFragment) {
        fragmentsUtil?.addToShow(from, to)
    }

    override fun popTo(target: Class<out SupportFragment>, includeSelf: Boolean): Boolean {
        return fragmentsUtil != null && fragmentsUtil!!.popTo(target, includeSelf)
    }

    fun popSelf(): Boolean {
        return fragmentsUtil != null && fragmentsUtil!!.popSelf()
    }

    override fun replaceToShow(from: SupportFragment, to: SupportFragment) {
        fragmentsUtil?.replaceToShow(from, to)
    }

    override fun onBackPressed() {
        if (!finishInner()) {
            handleFinish()
        }
    }

    override fun showToast(toast: String?) {
        toast?.let {
            ToastUtil.showToast(this, it)
        }
    }

    override fun showToast(stringId: Int) {
        ToastUtil.showToast(this, getString(stringId))
    }

    override fun showLoadingDialog(msg: String?) {
        try {
            progressDialog.show(supportFragmentManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun closeLoadingDialog() {
        try {
            progressDialog.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun openKeyBoard() {
        KeyBoardUtil.openKeyBoard(this)
    }

    override fun openKeyBoard(focusView: View) {
        KeyBoardUtil.openKeyBoard(this, focusView)
    }

    override fun closeKeyBoard() {
        KeyBoardUtil.closeKeyBoard(this)
    }

    override fun finishInner(): Boolean {
        val topFragments = topFragment
        var finish = false
        if (topFragments != null && !topFragments.isEmpty()) {
            for (fragment in topFragments) {
                if (fragment is SupportFragment) {
                    finish = finish or fragment.finishInner()
                }
            }
        }
        return finish
    }

    override fun saveViewData(bundle: Bundle) {
    }

    override fun saveViewData(key: String, bundle: Bundle) {
    }

    override fun handleFinish(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        } else {
            finish()
        }
        return true
    }
}
