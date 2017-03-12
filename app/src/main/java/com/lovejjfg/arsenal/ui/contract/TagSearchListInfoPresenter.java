package com.lovejjfg.arsenal.ui.contract;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.lovejjfg.arsenal.api.DataManager;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;

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
        Subscription subscription = DataManager.handleNormalService(DataManager.getArsenalApi().search(mCurrentKey), new Action1<ArsenalListInfo>() {
            @Override
            public void call(ArsenalListInfo info) {
                mHasMore = info.getHasMore();
                mView.onRefresh(false);
                mView.onRefresh(info);
                if (TextUtils.isEmpty(mHasMore)) {
                    mView.atEnd();
                }
            }
        }, this);
        subscribe(subscription);
    }

    @Override
    public void onLoadMore() {
        unSubscribe();
        Subscription subscription = DataManager.handleNormalService(DataManager.getArsenalApi().search(mCurrentKey + mHasMore), new Action1<ArsenalListInfo>() {
            @Override
            public void call(ArsenalListInfo info) {
                // TODO: 2017/3/9 404 ERROE
                mHasMore = info.getHasMore();
                mView.onLoadMore(info);
                if (TextUtils.isEmpty(mHasMore)) {
                    mView.atEnd();
                }
            }
        }, this);
        subscribe(subscription);

    }

}
