package org.live.test.activity;

import android.animation.Animator;
import android.view.View;
import android.widget.TextView;

import org.live.test.R;
import org.live.test.animator.BaseAnimatorSet;
import org.live.test.animator.effect.FadeEnter.FadeEnter;
import org.live.test.base.BaseActivity;
import org.live.test.widget.CustomDialog;

import butterknife.BindView;

/**
 * Created by wl on 2018/12/4.
 */
public class DialogActivity extends BaseActivity {

    @BindView(R.id.tv_default)
    TextView tvDefault;

    @Override
    public void initView() {
        // 在指定位置显示Dialog
        CustomDialog customDialog1 = new CustomDialog(DialogActivity.this);
        customDialog1.setPopUpStyle(true);
        customDialog1.setDimEnable(false);
        customDialog1.showAtLocation(0, 0);

        // 在指定锚点显示Dialog
        tvDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog2 = new CustomDialog(DialogActivity.this);
                customDialog2.setPopUpStyle(true);
                customDialog2.setDimEnable(false);
                customDialog2.anchorView(tvDefault);
            }
        });

        // 在某个View上执行动画
        BaseAnimatorSet animatorSet = new FadeEnter();
        animatorSet.delay(200);
        animatorSet.duration(2000);
        animatorSet.listener(new BaseAnimatorSet.SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {

            }
        });
        animatorSet.playOn(tvDefault);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_default;
    }
}
