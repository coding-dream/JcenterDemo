package org.live.test.activity;

import android.view.View;
import android.widget.TextView;

import org.live.test.R;
import org.live.test.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by wl on 2018/11/28.
 *
 * 常见错误:
 *
 * 网络请求未结束,用户返回页面的情况, 回调函数里面更新UI.
 *
 * 如果没有使用ButterKnife, 下面代码也并没有什么问题, 但是如果使用, 因为BaseActivity在onDestroy中unBinder.unBind(), 所以会崩溃.
 * Error: void android.view.View.setVisibility(int)' on a null object reference
 */
public class ErrorSlowlyActivity extends BaseActivity {

    @BindView(R.id.tv_message)
    TextView tvMessage;

    @Override
    public void initView() {
        WhiteManager.getInstance().loadWhiteUser(new WhiteManager.Callback() {
            @Override
            public void done() {
                // 必须加上判断,否则崩溃
                if (isAlive()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMessage.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_error;
    }
}

class WhiteManager {

    public static WhiteManager whiteManager;

    public static WhiteManager getInstance() {
        if (whiteManager == null) {
            synchronized (WhiteManager.class) {
                whiteManager = new WhiteManager();
            }
        }
        return whiteManager;
    }

    interface Callback {
        void done();
    }

    void loadWhiteUser(Callback callback) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(8000);
                    callback.done();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}