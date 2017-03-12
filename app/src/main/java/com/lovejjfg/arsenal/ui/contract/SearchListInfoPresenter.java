package com.lovejjfg.arsenal.ui.contract;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.lovejjfg.arsenal.api.DataManager;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

public class SearchListInfoPresenter extends BaseListInfoPresenter {


    public SearchListInfoPresenter(@Nullable ListInfoContract.View view) {
        super(view);
    }


    @Override
    public void onRefresh() {
        unSubscribe();
        mView.onRefresh(true);
        Subscription subscription = DataManager.handleNormalService(DataManager.getArsenalApi().search(String.format("/search?q=%s", mCurrentKey)), new Action1<ArsenalListInfo>() {
            @Override
            public void call(ArsenalListInfo info) {
                mView.onRefresh(false);
                mHasMore = info.getHasMore();
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
        Subscription subscription = DataManager.handleNormalService(DataManager.getArsenalApi().search(mHasMore), new Action1<ArsenalListInfo>() {
            @Override
            public void call(ArsenalListInfo info) {
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
