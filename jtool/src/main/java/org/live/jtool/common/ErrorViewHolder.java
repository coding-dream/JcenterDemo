package org.live.jtool.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.live.jtool.R;

/**
 * Created by lk on 2017/9/20.
 */
public class ErrorViewHolder extends BaseViewHolder {

    private TextView mTitleText;
    private TextView mContentText;
    private TextView mReloadButton;

    private OnReloadClickListener mReloadClickListener;

    public ErrorViewHolder(ViewGroup parent, OnReloadClickListener reloadClickListener) {
        super(parent, R.layout.view_holder_error);
        mReloadClickListener = reloadClickListener;
    }

    @Override
    public void initViews(View itemView) {
        mTitleText = itemView.findViewById(R.id.tv_title);
        mContentText = itemView.findViewById(R.id.tv_content);
        mReloadButton = itemView.findViewById(R.id.btn_reload);
    }

    @Override
    public void updateViews(Object data, int position) {
        if (AppUtils.isNetworkOK()) {
            mTitleText.setText("connect_error_title");
            mContentText.setText("connect_error_text");
        } else {
            mTitleText.setText("network_error_title");
            mContentText.setText("network_error_text");
        }
        mReloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mReloadClickListener != null) {
                    mReloadClickListener.onReload();
                }
            }
        });
    }

    public interface OnReloadClickListener {
        void onReload();
    }
}