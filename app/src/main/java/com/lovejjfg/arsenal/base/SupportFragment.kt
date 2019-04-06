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

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Joe on 2016/10/13.
 * Email lovejjfg@gmail.com
 */

abstract class SupportFragment : Fragment(), ISupportFragment, ISupportView {
    private var activity: SupportActivity? = null
    var isRoot: Boolean = false

    val containerId: Int
        get() = arguments!!.getInt(ARG_CONTAINER)
    override var toolbar: Toolbar? = activity?.toolbar

    override var topFragment: List<Fragment>? = activity?.topFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragments(savedInstanceState, this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(ARG_IS_HIDDEN, isHidden)
        super.onSaveInstanceState(outState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity = context as SupportActivity?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(initLayoutRes(), container, false)
    }

    override fun onDetach() {
        activity = null
        super.onDetach()
    }

    override fun addToParent(
        containerViewId: Int,
        parent: SupportFragment,
        pos: Int,
        vararg children: SupportFragment
    ) {
        if (activity != null) {
            activity!!.addToParent(containerViewId, parent, pos, *children)
        }
    }

    override fun replaceToParent(containerViewId: Int, parent: SupportFragment, vararg children: SupportFragment) {
        if (activity != null) {
            activity!!.replaceToParent(containerViewId, parent, *children)
        }
    }

    override fun initFragments(savedInstanceState: Bundle?, fragment: SupportFragment) {
        if (activity != null) {
            activity!!.initFragments(savedInstanceState, this)
        }
    }

    override fun <F : SupportFragment> findFragment(className: Class<F>): F? {
        return if (activity != null) {
            activity!!.findFragment(className)
        } else null
    }

    override fun <F : SupportFragment> findFragment(parentFragment: SupportFragment, className: Class<F>): F? {
        return if (activity != null) {
            activity!!.findFragment(parentFragment, className)
        } else null
    }

    override fun loadRoot(containerViewId: Int, vararg root: SupportFragment) {
        if (activity != null) {
            activity!!.loadRoot(containerViewId, *root)
        }
    }

    override fun addToShow(from: SupportFragment, to: SupportFragment) {
        if (activity != null) {
            activity!!.addToShow(from, to)
        }
    }

    override fun popTo(target: Class<out SupportFragment>, includeSelf: Boolean): Boolean {
        return activity != null && activity!!.popTo(target, includeSelf)
    }

    override fun replaceToShow(from: SupportFragment, to: SupportFragment) {
        if (activity != null) {
            activity!!.replaceToShow(from, to)
        }
    }

    override fun showToast(toast: String?) {
        toast?.let {
            activity?.showToast(toast)
        }
    }

    override fun showToast(stringId: Int) {
        if (activity != null) {
            activity!!.showToast(stringId)
        }
    }

    override fun showLoadingDialog(msg: String?) {
        if (activity != null) {
            activity!!.showLoadingDialog(msg)
        }
    }

    override fun closeLoadingDialog() {
        if (activity != null) {
            activity!!.closeLoadingDialog()
        }
    }

    override fun openKeyBoard() {
        if (activity != null) {
            activity!!.openKeyBoard()
        }
    }

    override fun openKeyBoard(focusView: View) {
        if (activity != null) {
            activity!!.openKeyBoard(focusView)
        }
    }

    override fun closeKeyBoard() {
        if (activity != null) {
            activity!!.closeKeyBoard()
        }
    }

    override fun finishInner(): Boolean {
        return handleFinish() && activity != null && activity!!.popSelf()
    }

    override fun handleFinish(): Boolean {
        return false
    }

    //    @Override
    //    public void saveToSharedPrefs(String key, Object value) {
    //        if (activity != null) {
    //            activity.saveToSharedPrefs(key, value);
    //        }
    //    }

    override fun saveViewData(bundle: Bundle) {
        val arguments = arguments
        if (arguments == null) {
            setArguments(bundle)
        } else {
            arguments.putAll(bundle)
        }
    }

    override fun saveViewData(key: String, bundle: Bundle) {
        val arguments = arguments
        if (arguments == null) {
            val b = Bundle()
            b.putBundle(key, bundle)
            setArguments(b)
        } else {
            arguments.putBundle(key, bundle)
            setArguments(arguments)
        }
    }

    companion object {
        const val ARG_IS_HIDDEN = "ARG_IS_HIDDEN"
        const val ARG_CONTAINER = "ARG_CONTAINER_ID"
    }
}
