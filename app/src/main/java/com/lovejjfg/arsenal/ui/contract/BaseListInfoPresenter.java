package com.lovejjfg.arsenal.ui.contract;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lovejjfg.arsenal.base.BasePresenterImpl;

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
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(CURRENT_KEY, mCurrentKey);
        outState.putString(HAS_MORE, mHasMore);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void startSearch(String key) {
        mCurrentKey = key;
        onRefresh();
    }

    @Override
    public void call(Throwable throwable) {
        mView.onRefresh(false);
        super.call(throwable);
    }
}
