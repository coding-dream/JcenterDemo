package org.live.test.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import org.live.test.R;

/**
 * Created by wl on 2018/12/4.
 */
public class CustomDialog extends BaseDialog {

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected View onChildCreateView() {
        View childView = LayoutInflater.from(mContext).inflate(R.layout.dialog_custom, null);
        return childView;
    }
}
