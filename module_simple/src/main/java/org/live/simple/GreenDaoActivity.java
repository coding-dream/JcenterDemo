package org.live.simple;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.live.simple.database.dao.CMessageDbHelper;
import org.live.simple.database.entity.CMessage;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by wl on 2018/11/26.
 */
public class GreenDaoActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMessage();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showMessage();
                    }
                }, 3000);
            }
        });
    }

    private void saveMessage() {
        CMessage cMessage = new CMessage();
        cMessage.setContent("new data: " + new Random().nextInt(100));
        cMessage.setTime(new Date().getTime());
        CMessageDbHelper.getInstance().insert(cMessage);
    }

    public void showMessage() {
        List<CMessage> messageList = CMessageDbHelper.getInstance().findAll();
        StringBuilder builder = new StringBuilder();
        for (CMessage cMessage : messageList) {
            builder.append(cMessage.getContent() + " ");
        }
        TextView textView = findViewById(R.id.tv_message);
        textView.setText(builder.toString());
    }
}
