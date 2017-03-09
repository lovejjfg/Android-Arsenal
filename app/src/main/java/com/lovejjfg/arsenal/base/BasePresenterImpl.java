package com.lovejjfg.arsenal.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lovejjfg.arsenal.utils.ErrorUtil;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Joe on 2016/9/18.
 * Email lovejjfg@gmail.com
 */
public abstract class BasePresenterImpl<T extends IBaseView> implements BasePresenter, Action1<Throwable> {
    private CompositeSubscription mCompositeSubscription;
    protected final T mView;

    public BasePresenterImpl(@Nullable T view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        unSubscribe();
    }

    @Override
    public void subscribe(Subscription subscriber) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscriber);
    }

    @Override
    public void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }

    @Override
    public void call(Throwable throwable) {
        ErrorUtil.handleError(mView, throwable, true, false);
    }
}
