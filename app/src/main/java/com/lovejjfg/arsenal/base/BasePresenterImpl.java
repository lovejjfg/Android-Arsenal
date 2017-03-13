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

package com.lovejjfg.arsenal.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lovejjfg.arsenal.utils.ErrorUtil;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Joe on 2016/9/18.
 * Email lovejjfg@gmail.com
 */
public abstract class BasePresenterImpl<T extends IBaseView> implements BasePresenter, Action1<Throwable> {
    private CompositeSubscription mCompositeSubscription;
    protected final T mView;

    protected BasePresenterImpl(@Nullable T view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        unSubscribe();
    }

    @Override
    public void subscribe(Subscription subscriber) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscriber);
    }

    @Override
    public void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }

    @Override
    public void call(Throwable throwable) {
        ErrorUtil.handleError(mView, throwable, true, false);
    }
}
