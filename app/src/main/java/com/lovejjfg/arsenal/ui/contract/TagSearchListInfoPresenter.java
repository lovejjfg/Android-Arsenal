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

package com.lovejjfg.arsenal.ui.contract;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.lovejjfg.arsenal.api.DataManager;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.utils.ErrorUtil;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

public class TagSearchListInfoPresenter extends BaseListInfoPresenter {


    public TagSearchListInfoPresenter(@Nullable ListInfoContract.View view) {
        super(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onRefresh() {
        mView.onRefresh(true);
        unSubscribe();
        Subscription subscription = DataManager.handleNormalService(DataManager.getArsenalApi().search(mCurrentKey), info -> {
            mHasMore = info.getHasMore();
            mView.onRefresh(false);
            mView.onRefresh(info);
            if (TextUtils.isEmpty(mHasMore)) {
                mView.atEnd();
            }
        }, this);
        subscribe(subscription);
    }

    @Override
    public void onLoadMore() {
        if (TextUtils.isEmpty(mHasMore)) {
            mView.atEnd();
            return;
        }
        unSubscribe();
        Subscription subscription = DataManager.handleNormalService(DataManager.getArsenalApi().search(mHasMore), info -> {
            // TODO: 2017/3/9 404 ERROE
            mHasMore = info.getHasMore();
            mView.onLoadMore(info);
            if (TextUtils.isEmpty(mHasMore)) {
                mView.atEnd();
            }
        }, throwable -> {
            mView.loadMoreError();
            ErrorUtil.handleError(mView, throwable, true, false);
        });
        subscribe(subscription);

    }

}
