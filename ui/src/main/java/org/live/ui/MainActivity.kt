package org.live.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

/**
 * Created by wl on 2018/10/6.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val customView = CustomView(this)
        setContentView(customView)

        val array = arrayListOf("1,", "2")
        array.toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            Logger.d("onNext: $it")
                        }
                )
    }
}