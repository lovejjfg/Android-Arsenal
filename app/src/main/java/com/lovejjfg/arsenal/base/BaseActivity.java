package com.lovejjfg.arsenal.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Joe on 2016/12/28.
 * Email lovejjfg@gmail.com
 */

public abstract class BaseActivity<P extends BasePresenter> extends SupportActivity implements IBaseView<P> {
    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.onRestart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);

    }

    @Override
    public Context getContext() {
        return this;
    }
}

