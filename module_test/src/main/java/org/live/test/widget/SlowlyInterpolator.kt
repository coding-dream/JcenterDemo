package org.live.test.widget

import android.view.animation.Interpolator
import com.orhanobut.logger.Logger

/**
 * Created by wl on 2018/10/22.
 */
class SlowlyInterpolator : Interpolator {

    override fun getInterpolation(input: Float): Float {
        Logger.d("input: $input")
        return if (input < 0.5) {
            0.1f
        } else {
            input
        }
    }
}