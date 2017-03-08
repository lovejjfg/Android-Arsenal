package com.lovejjfg.arsenal.base;

import android.os.Bundle;

import rx.Subscription;

/**
 * Android lifecycle callbacks from activities/fragments are linked to the Presenters by this
 * behavior interface. Linking callbacks is declared as an optional behavior and not a need for
 * every presenter.
 */
public interface BasePresenter {
    void onStart();

    void onResume();

    void onDestroy();

    void subscribe(Subscription subscriber);

    void unSubscribe();

    void onCreate(Bundle savedInstanceState);

    void onStop();

    void onPause();

    void onRestart();

    void onSaveInstanceState(Bundle outState);
}
