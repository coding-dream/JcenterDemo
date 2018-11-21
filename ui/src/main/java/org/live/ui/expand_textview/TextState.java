package org.live.ui.expand_textview;

public class TextState {
    public int mTextState;

    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_COLLAPSED = 1; // 文本行数超过限定行数,处于折叠状态
    public static final int STATE_EXPANDED = 2; // 文本行数超过限定行数,被点击全文展开

    public String text;

    void setTextState(int textState){
        this.mTextState = textState;
    }

    int getTextState() {
        return mTextState;
    }
}
