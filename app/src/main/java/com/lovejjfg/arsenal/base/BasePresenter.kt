package com.lovejjfg.arsenal.base

import android.os.Bundle

import rx.Subscription

/**
 * Android lifecycle callbacks from activities/fragments are linked to the Presenters by this
 * behavior interface. Linking callbacks is declared as an optional behavior and not a need for
 * every presenter.
 */
interface BasePresenter {
    fun onStart()

    fun onResume()

    fun onDestroy()

    fun subscribe(subscriber: Subscription)

    fun unSubscribe()

    fun onCreate(savedInstanceState: Bundle?)

    fun onStop()

    fun onPause()

    fun onRestart()

    fun onSaveInstanceState(outState: Bundle?)

    fun onViewPrepared()
}
