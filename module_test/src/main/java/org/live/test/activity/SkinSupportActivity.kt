package org.live.test.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_skin_support.*
import org.live.test.R
import skin.support.SkinCompatManager

/**
 * Created by wl on 2018/10/21.
 */
class SkinSupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skin_support)
        button.setOnClickListener {
            val input = et_skin_input.text.toString()
            SkinCompatManager.getInstance().loadSkin("$input.skin")
        }
    }
}