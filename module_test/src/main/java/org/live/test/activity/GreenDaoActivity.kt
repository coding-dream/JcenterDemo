package org.live.test.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_greendao.*
import org.live.test.R
import org.live.test.database.dao.CMessageDbHelper
import org.live.test.database.entity.CMessage
import java.util.*

/**
 * Created by wl on 2018/10/10.
 */
class GreenDaoActivity : AppCompatActivity() {

    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greendao)
        button.setOnClickListener {
            val cMessage = CMessage()
            val datetime = Date().time
            cMessage.content = "new Data 1" + Random().nextInt(100)
            cMessage.time = datetime
            CMessageDbHelper.getInstance().insert(cMessage)

            handler.postDelayed({
                showMessage()
            },100)
        }
    }

    private fun showMessage() {
        val messageList = CMessageDbHelper.getInstance().findAll()
        val stringBuilder = StringBuilder()
        stringBuilder.apply {
            messageList.forEach {
                append("id: ${it.id} content: ${it.content} \r\n")
            }
        }
        tv_message.text = stringBuilder.toString()
    }
}