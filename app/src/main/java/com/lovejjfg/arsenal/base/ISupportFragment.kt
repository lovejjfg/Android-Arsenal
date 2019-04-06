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

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar

/**
 * Created by Joe on 2016/11/13.
 * Email lovejjfg@gmail.com
 */

internal interface ISupportFragment {

    var topFragment: List<Fragment>?

    var toolbar: Toolbar?
    fun initFragments(savedInstanceState: Bundle?, fragment: SupportFragment)

    fun <F : SupportFragment> findFragment(className: Class<F>): F?

    fun <F : SupportFragment> findFragment(parentFragment: SupportFragment, className: Class<F>): F?

    fun loadRoot(containerViewId: Int, vararg root: SupportFragment)

    fun addToShow(from: SupportFragment, to: SupportFragment)

    fun popTo(target: Class<out SupportFragment>, includeSelf: Boolean): Boolean

    fun replaceToShow(from: SupportFragment, to: SupportFragment)

    fun addToParent(@IdRes containerViewId: Int, parent: SupportFragment, pos: Int, vararg children: SupportFragment)

    fun replaceToParent(containerViewId: Int, parent: SupportFragment, vararg children: SupportFragment)

    fun saveViewData(bundle: Bundle)

    fun saveViewData(key: String, bundle: Bundle)
}
