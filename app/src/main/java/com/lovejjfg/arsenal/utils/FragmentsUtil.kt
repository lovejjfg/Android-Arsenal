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

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

import com.lovejjfg.arsenal.base.SupportFragment

import java.util.ArrayList

/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

class FragmentsUtil(private val manager: FragmentManager?) {

    //递归
    val topFragments: List<Fragment>?
        get() {
            if (manager == null) {
                return null
            }
            val fragments = manager.fragments
            val topFragments = ArrayList<Fragment>()
            if (fragments.isEmpty()) {
                return null
            }
            val size = fragments.size
            for (i in size - 1 downTo 0) {
                val f = fragments[i]
                if (f != null && f.isAdded && !f.isHidden && f.userVisibleHint) {
                    val t = getTopFragment(f.childFragmentManager)
                    if (t != null) {
                        topFragments.add(t)
                    } else {
                        topFragments.add(f)
                    }
                }
            }
            return topFragments
        }

    fun addToShow(from: SupportFragment, to: SupportFragment) {
        bindContainerId(from.containerId, to)
        val transaction = manager!!.beginTransaction()
        val tag = to.javaClass.simpleName
        transaction.add(from.containerId, to, tag)
            .addToBackStack(tag)
            .hide(from)
            .show(to)
            .commit()
    }

    fun addToParent(containerViewId: Int, parent: SupportFragment, pos: Int, vararg children: SupportFragment) {
        val transaction = parent.childFragmentManager.beginTransaction()
        if (children.isNotEmpty()) {
            addFragmentsToStack(containerViewId, pos, transaction, false, children)
        }
    }

    fun replaceToParent(containerViewId: Int, parent: SupportFragment, vararg childs: SupportFragment) {
        val transaction = parent.childFragmentManager.beginTransaction()
        if (childs.isNotEmpty()) {
            for (child in childs) {
                bindContainerId(containerViewId, child)
                val tag = child.javaClass.simpleName
                transaction.replace(containerViewId, child, tag)
                    .addToBackStack(tag)
            }
            transaction.commit()
        }
    }

    fun replaceToShow(from: SupportFragment, to: SupportFragment) {
        bindContainerId(from.containerId, to)
        val transaction = manager!!.beginTransaction()
        val tag = to.javaClass.simpleName
        transaction.replace(from.containerId, to, tag)
            .addToBackStack(tag)
            .commit()
    }

    fun loadRoot(containerViewId: Int, pos: Int, vararg roots: SupportFragment) {
        val transaction = manager!!.beginTransaction()
        addFragmentsToStack(containerViewId, pos, transaction, true, roots)
    }

    private fun addFragmentsToStack(
        containerViewId: Int,
        pos: Int,
        transaction: FragmentTransaction,
        isRoot: Boolean,
        fragments: Array<out SupportFragment>
    ) {
        if (fragments.isNotEmpty()) {
            if (pos >= fragments.size || pos < 0) {
                throw IndexOutOfBoundsException("Index: " + pos + ", Size: " + fragments.size)
            }
            for (i in fragments.indices) {
                val f = fragments[i]
                f.isRoot = isRoot
                bindContainerId(containerViewId, f)
                val tag = f.javaClass.simpleName
                transaction.add(containerViewId, f, tag)
                    .addToBackStack(tag)

                if (i == pos) {
                    transaction.show(f)
                } else {
                    transaction.hide(f)
                }
            }
            transaction.commit()
        }
    }

    private fun bindContainerId(containerId: Int, to: SupportFragment) {
        var args = to.arguments
        if (args == null) {
            args = Bundle()
            to.arguments = args
        }
        args.putInt(SupportFragment.ARG_CONTAINER, containerId)
    }

    fun initFragments(savedInstanceState: Bundle?, fragment: SupportFragment) {
        if (savedInstanceState == null) {
            return
        }
        val isSupportHidden = savedInstanceState.getBoolean(SupportFragment.ARG_IS_HIDDEN)

        val ft = manager!!.beginTransaction()
        if (isSupportHidden) {
            ft.hide(fragment)
        } else {
            ft.show(fragment)
        }
        ft.commit()
    }

    fun popTo(target: Class<out SupportFragment>, includeSelf: Boolean): Boolean {
        val flag: Int = if (includeSelf) {
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        } else {
            0
        }
        return manager!!.popBackStackImmediate(target.simpleName, flag)
    }

    fun popSelf(): Boolean {
        return manager!!.popBackStackImmediate()
    }

    fun <T : SupportFragment> findFragment(className: Class<T>): T? {
        return findFragment(null, className)
    }

    fun <T : SupportFragment> findFragment(parentFragment: SupportFragment?, className: Class<T>): T? {
        val tagFragment: Fragment? = if (parentFragment != null) {
            parentFragment.childFragmentManager.findFragmentByTag(className.simpleName)
        } else {
            manager!!.findFragmentByTag(className.simpleName)
        }
        return try {
            tagFragment as T?
        } catch (e: Exception) {
            null
        }
    }

    private fun getTopFragment(manager: FragmentManager): Fragment? {
        val fragments = manager.fragments
        if (fragments.isEmpty()) {
            return null
        }
        val size = fragments.size
        for (i in size - 1 downTo 0) {
            val f = fragments[i]
            if (f != null && f.isAdded && !f.isHidden && f.userVisibleHint) {
                val tTopFragment = getTopFragment(f.childFragmentManager)
                return tTopFragment ?: f
            }
        }
        return null
    }
}
