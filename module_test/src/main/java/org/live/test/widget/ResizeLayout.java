package org.live.test.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import org.live.test.util.LogHelper;

/**
 * Created by wl on 2018/12/7.
 */
public class ResizeLayout extends RelativeLayout {

    public ResizeLayout(Context context) {
        super(context);
    }

    public ResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogHelper.d("onSizeChanged: " + w + " " + h);
    }
}
