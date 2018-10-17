package org.live.ui;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * ViewPager切换效果
 */

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static final float SCALE_MAX = 0.8f;
    private static final float ALPHA_MAX = 0.6f;

    @Override
    public void transformPage(@NonNull View view, float position) {
        float scale = (position < 0)
                    ? (1 - SCALE_MAX) * position + 1
                    : (SCALE_MAX - 1) * position + 1;
        float alpha = (position < 0)
                    ? (1 - ALPHA_MAX) * position + 1
                    : (ALPHA_MAX - 1) * position + 1;

        view.setPivotX(position < 0 ? view.getWidth() * 0.8f : view.getWidth() * 0.2f);
        view.setPivotY(view.getHeight() / 2);
        view.setScaleX(scale);
        view.setScaleY(scale);
        view.setAlpha(alpha);
    }
}
