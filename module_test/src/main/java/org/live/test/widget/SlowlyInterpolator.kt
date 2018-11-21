package org.live.test.widget

import android.view.animation.Interpolator

/**
 * Created by wl on 2018/10/22.
 */
class SlowlyInterpolator : Interpolator {

    override fun getInterpolation(input: Float): Float {
        return if (input < 0.8) {
            0f
        } else {
            input
        }
    }
}