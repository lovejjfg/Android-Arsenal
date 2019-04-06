package com.lovejjfg.arsenal.utils

import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.view.View

/**
 * Created by joe on 2019/4/6.
 * Email: lovejjfg@gmail.com
 */
fun View.transationHint() {
    val x = if ((Math.random() * 10).toInt() % 2 == 0) 1 else -1

    val translationValue = context.dip2px(
        25f * x
    ).toFloat()

    this.animate()
        .translationX(translationValue)
        .setDuration(100)
        .setInterpolator(FastOutLinearInInterpolator())
        .withEndAction {
            this.animate().translationX(-translationValue)
                .setDuration(200)
                .setInterpolator(FastOutLinearInInterpolator())
                .withEndAction {
                    this.animate().translationX(0f)
                        .setDuration(100)
                        .setInterpolator(FastOutLinearInInterpolator())
                        .start()
                }
                .start()

        }
        .start()
}
