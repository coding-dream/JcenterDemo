package org.live.nonokt

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var intent: Intent = Intent()
        intent.setClass(this@MainActivity,TestActivity::class.java)
        startActivity(intent)
        finish()
    }
}