package org.live.test.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_http_api.*
import org.live.test.R
import org.live.test.viewmodel.HttpApiViewModel

/**
 * Created by wl on 2018/10/14.
 */
class HttpApiActivity : AppCompatActivity() {

    private lateinit var httpApiViewModel: HttpApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http_api)
        initViewModel()

        btn_get.setOnClickListener {
            httpApiViewModel.loadUsers()
        }

        btn_post.setOnClickListener {

        }

        btn_upload.setOnClickListener {

        }

        btn_down.setOnClickListener {

        }
    }

    private fun initViewModel() {
        httpApiViewModel = ViewModelProviders.of(this).get(HttpApiViewModel::class.java)
        httpApiViewModel.userLiveData.observe(this, Observer {
            Logger.d("code: ${it?.code} result: ${it?.users}")
        })
    }
}