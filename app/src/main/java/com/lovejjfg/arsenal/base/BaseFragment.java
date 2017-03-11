package com.lovejjfg.arsenal.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Joe on 2016/12/28.
 * Email lovejjfg@gmail.com
 */

public abstract class BaseFragment<P extends BasePresenter> extends SupportFragment implements IBaseView<P> {
    public P mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPresenter = initPresenter();
        mPresenter.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);

    }
}

