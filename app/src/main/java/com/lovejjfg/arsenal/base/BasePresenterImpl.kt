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

import com.lovejjfg.arsenal.utils.ErrorUtil

import rx.Subscription
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

/**
 * Created by Joe on 2016/9/18.
 * Email lovejjfg@gmail.com
 */
abstract class BasePresenterImpl<T : IBaseView<*>>(protected val mView: T) : BasePresenter,
    Action1<Throwable> {
    private var mCompositeSubscription: CompositeSubscription? = null

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
    }

    override fun onPause() {
    }

    override fun onRestart() {
    }

    override fun onSaveInstanceState(outState: Bundle?) {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        unSubscribe()
    }

    override fun subscribe(subscriber: Subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = CompositeSubscription()
        }
        this.mCompositeSubscription?.add(subscriber)
    }

    override fun unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription?.clear()
        }
    }

    override fun call(throwable: Throwable) {
        mView.closeLoadingDialog()
        ErrorUtil.handleError(mView, throwable, true, false)
    }
}
