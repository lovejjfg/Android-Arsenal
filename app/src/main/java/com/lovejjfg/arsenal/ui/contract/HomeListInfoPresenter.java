package com.lovejjfg.arsenal.ui.contract;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.lovejjfg.arsenal.api.DataManager;
import com.lovejjfg.arsenal.api.mode.ArsenalListInfo;
import com.lovejjfg.arsenal.api.mode.ArsenalUserInfo;
import com.lovejjfg.arsenal.base.BasePresenterImpl;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

public class HomeListInfoPresenter extends BasePresenterImpl<ListInfoContract.View> implements ListInfoContract.Presenter {

    private String mHasMore;

    public HomeListInfoPresenter(@Nullable ListInfoContract.View view) {
        super(view);
    }

    @Override
    public void onItemClick(View itemView, ArsenalListInfo.ListInfo info) {
        DataManager.handleNormalService(DataManager.getArsenalApi().getArsenalUserInfo(info.getUserDetailUrl()), new Action1<ArsenalUserInfo>() {
            @Override
            public void call(ArsenalUserInfo info) {
                mView.jumpToTarget(info);
            }
        }, this);
    }

    @Override
    public void onRefresh() {
        unSubscribe();
        mView.onRefresh(true);
        Subscription subscription = DataManager.handleNormalService(DataManager.getArsenalApi().getArsenalListInfo(), new Action1<ArsenalListInfo>() {
            @Override
            public void call(ArsenalListInfo info) {
                mHasMore = info.getHasMore();
                mView.onRefresh(info);
                mView.onRefresh(false);
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
        Subscription subscription = DataManager.handleNormalService(DataManager.getArsenalApi().loadMoreArsenalListInfo(mHasMore), new Action1<ArsenalListInfo>() {
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

    @Override
    public void startSearch(String key) {
        //do nothing
    }

    @Override
    public void call(Throwable throwable) {
        mView.onRefresh(false);
        super.call(throwable);
    }
}