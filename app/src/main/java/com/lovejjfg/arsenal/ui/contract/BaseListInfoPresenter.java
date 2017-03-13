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
import com.lovejjfg.arsenal.utils.rxbus.RxBus;
import com.lovejjfg.arsenal.utils.rxbus.SearchEvent;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

public abstract class BaseListInfoPresenter extends BasePresenterImpl<ListInfoContract.View> implements ListInfoContract.Presenter {

    private static final String CURRENT_KEY = "currentKey";
    private static final String HAS_MORE = "hasMore";
    public String mCurrentKey;
    public String mHasMore;

    public BaseListInfoPresenter(@Nullable ListInfoContract.View view) {
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
