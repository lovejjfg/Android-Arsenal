package com.lovejjfg.arsenal.ui.contract;

import android.support.annotation.Nullable;

/**
 * Created by Joe on 2017/3/9.
 * Email lovejjfg@gmail.com
 */

public class UserDetailListInfoPresenter extends BaseListInfoPresenter {


    public UserDetailListInfoPresenter(@Nullable ListInfoContract.View view) {
        super(view);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onViewPrepared() {
        mView.setPullRefreshEnable(false);
        mView.atEnd();
    }
}
