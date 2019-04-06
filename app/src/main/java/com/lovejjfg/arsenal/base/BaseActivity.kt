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

/**
 * Created by Joe on 2016/12/28.
 * Email lovejjfg@gmail.com
 */

abstract class BaseActivity<P : BasePresenter> : SupportActivity(), IBaseView<P> {
    private lateinit var mPresenter: P

    override val context: Context?
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = initPresenter()
        mPresenter.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onStop()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.onPause()
    }

    override fun onRestart() {
        super.onRestart()
        mPresenter.onRestart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mPresenter.onSaveInstanceState(outState)
    }
}

