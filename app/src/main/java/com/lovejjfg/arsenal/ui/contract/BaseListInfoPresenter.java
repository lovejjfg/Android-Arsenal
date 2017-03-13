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
import android.util.Log;
import android.view.View;

import com.lovejjfg.arsenal.api.DataManager;
import com.lovejjfg.arsenal.api.mode.ArsenalDetailInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;
import com.lovejjfg.arsenal.base.BasePresenterImpl;
import com.lovejjfg.arsenal.utils.JumpUtils;

import rx.functions.Action1;

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

public abstract class BaseListInfoPresenter extends BasePresenterImpl<ListInfoContract.View> implements ListInfoContract.Presenter {

    private static final String CURRENT_KEY = "currentKey";
    private static final String HAS_MORE = "hasMore";
    String mCurrentKey;
    String mHasMore;

    BaseListInfoPresenter(@Nullable ListInfoContract.View view) {
        super(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentKey = savedInstanceState.getString(CURRENT_KEY);
            mHasMore = savedInstanceState.getString(HAS_MORE);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemClick(View itemView, ArsenalListInfo.ListInfo info) {
        DataManager.handleNormalService(DataManager.getArsenalApi().getArsenalDetailInfo(info.getListDetailUrl()), new Action1<ArsenalDetailInfo>() {
            @Override
            public void call(ArsenalDetailInfo data) {
                Log.e("TAG", "call: " + data);
                JumpUtils.jumpToDetail(mView.getContext(), data);
            }
        }, this);
    }

    @Override
    public void onItemClick(String user) {
        DataManager.handleNormalService(DataManager.getArsenalApi().getArsenalUserInfo(user), new Action1<ArsenalUserInfo>() {
            @Override
            public void call(ArsenalUserInfo info) {
                mView.jumpToTarget(info);
            }
        }, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(CURRENT_KEY, mCurrentKey);
        outState.putString(HAS_MORE, mHasMore);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void startSearch(String key) {
        if (TextUtils.equals(mCurrentKey, key)) {
            return;
        }
        mCurrentKey = key;
        onRefresh();
    }

    @Override
    public void call(Throwable throwable) {
        mView.onRefresh(false);
        super.call(throwable);
    }

    @Override
    public void onViewPrepared() {

    }
}
