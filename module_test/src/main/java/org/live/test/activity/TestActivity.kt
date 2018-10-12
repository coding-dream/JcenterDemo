package org.live.test.activity

import android.app.Application
import android.arch.lifecycle.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.live.baselib.toast
import org.live.test.R

/**
 * Created by wl on 2018/9/5.
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val tvName: TextView = findViewById<TextView>(R.id.tv_name)
        val lp: LinearLayout.LayoutParams  = tvName.layoutParams as LinearLayout.LayoutParams
        lp.leftMargin = 50
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
        tvName.layoutParams = lp

        val testViewModel = ViewModelProviders.of(this).get(TestViewModel::class.java)
        testViewModel.getInitLiveData().observe(this, Observer {
            toast("init: $it")
        })
        testViewModel.init()
    }
}

class TestViewModel(application: Application) : AndroidViewModel(application) {

    private val initLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getInitLiveData(): LiveData<Boolean> {
        return initLiveData
    }

    fun init() {
        Observable
                .create<Boolean> {
                    it.onNext(true)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    initLiveData.value = it
                }
    }
}