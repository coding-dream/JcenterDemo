package org.live.test.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.live.test.R;
import org.live.test.base.BaseActivity;
import org.live.test.util.WeakHandler;

/**
 * Created by wl on 2018/11/29.
 */
public class DelayHanlderActivity extends BaseActivity implements View.OnClickListener {

    Button button;
    TextView textView;
    WeakHandler weakHandler = new WeakHandler();

    @Override
    public void initView() {
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        textView = findViewById(R.id.tv_content);
        textView.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_handler;
    }

    @Override
    public void onClick(View view) {
        Logger.d("doAnimator");
        weakHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.VISIBLE);
                textView.setAlpha(0);
                textView.animate()
                        .alpha(1)
                        .start();
            }
        }, 6000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
        textView.clearAnimation();
    }
}
