package org.live.test.activity

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_rotate.*
import org.live.test.R
import org.live.test.util.RotateManager

/**
 * Created by wl on 2018/11/14.
 *
 * 手动方式调用setRequestedOrientation会触发onConfigurationChanged
 */
class RotateActivity : AppCompatActivity() {

    val rotateManager = RotateManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotate)

        btnLandscape.setOnClickListener {
            // 如果非锁状态，屏幕旋转是自己期望的(横屏最好平稳一些)。如果是锁状态，点击后反向横屏可能旋转两次。
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }

        btnPortrait.setOnClickListener {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        rotateManager.register(this, object : RotateManager.OnOrientationChangeListener {

            override fun onLandscape() {
                if(rotateTooFrequently()) {
                    return
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            }

            override fun onPortrait() {
                if(rotateTooFrequently()) {
                    return
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }

        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Logger.dd("vvvv", "===========> 横屏")
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Logger.dd("vvvv","===========> 竖屏")
        }
    }

    var lastChangeOrientationTime = 0L

    private fun rotateTooFrequently(): Boolean {
        var result = false
        if (System.currentTimeMillis() - lastChangeOrientationTime < 1000L) {
            result = true
        }
        lastChangeOrientationTime = System.currentTimeMillis()
        return result
    }
}