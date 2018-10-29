package org.live.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.live.baselib.util.SystemUtils;

import skin.support.content.res.SkinCompatResources;
import skin.support.widget.SkinCompatHelper;
import skin.support.widget.SkinCompatRelativeLayout;

public class TitleBar extends SkinCompatRelativeLayout implements View.OnClickListener {
    private TextView mTitleTV;
    private TextView mLeftActionTV;
    private TextView mRightActionTV;
    private ImageView mLeftActionIV;
    private ImageView mRightActionIV;
    private ImageView mTitleIVActionIV;
    private View mSeparatorLineLy;
    private View mRootLy;
    private View mBottomSeparator;
    private boolean isSeparatorLine;
    private boolean isAutoBackFinish;
    private boolean isShowBottomSeparator = true;
    private int separatorColor;
    private int separatorSize;
    private int titleColorId;
    private int titleSize;
    private int leftColorId;
    private int leftSize;
    private int rightColorId;
    private int rightSize;
    private int leftTextStyle;
    private int rightTextStyle;
    private int titleTextStyle;
    private int leftTextPaddingLeft;
    private int leftTextPaddingRight;
    private int rightTextPaddingLeft;
    private int rightTextPaddingRight;
    private String title;
    private String leftText;
    private String rightText;
    private int leftImgId;
    private int rightImgId;
    private int titleImgId;

    //title的color是否跟随换肤包一起换，配置false时换肤不自动换颜色，要自己配置rightColorId
    private boolean isTitleTextColorAuto = true;
    private boolean isLeftTextColorAuto = true;
    private boolean isRightTextColorAuto = true;

    public void setSeparatorLineColor(@ColorInt int color)
    {
        this.mSeparatorLineLy.setBackgroundColor(color);
    }

    public void setSeparatorLineColorRes(@ColorRes int resId) {
        this.mSeparatorLineLy.setBackgroundResource(resId);
    }

    public void setSeparatorLineSize(int size) {
        if (size > 0) {
            ViewGroup.LayoutParams layoutParams = this.mSeparatorLineLy.getLayoutParams();
            layoutParams.height = size;
            this.mSeparatorLineLy.setLayoutParams(layoutParams);
        }
    }

    public void setSeparatorLineSizeRes(@DimenRes int resId) {
        int size = getResources().getDimensionPixelSize(resId);
        if (size > 0) {
            ViewGroup.LayoutParams layoutParams = this.mSeparatorLineLy.getLayoutParams();
            layoutParams.height = size;
            this.mSeparatorLineLy.setLayoutParams(layoutParams);
        }
    }

    public void setTitle(String text) {
        this.mTitleTV.setText(text);
    }

    public void setTitle(@StringRes int resId) {
        setTitle(getResources().getText(resId).toString());
    }

    public void setTitleColorRes(@ColorRes int resId) {
        this.mTitleTV.setTextColor(ContextCompat.getColor(getContext(), resId));
    }

    private void setTitleColor() {
        this.titleColorId = SkinCompatHelper.checkResourceId(titleColorId);
        if (titleColorId != SkinCompatHelper.INVALID_ID) {
            int color = isTitleTextColorAuto ?
                    SkinCompatResources.getColor(getContext(), R.color.color_title_bar_text_color)
                    : SkinCompatResources.getColor(getContext(), titleColorId);
            this.mTitleTV.setTextColor(color);
        }
    }

    public void setTitleSize(int size) {
        this.mTitleTV.setTextSize(size);
    }

    public void setTitleSize(int unit, int size) {
        this.mTitleTV.setTextSize(unit, size);
    }

    public void setLeftActionDrawable(@DrawableRes int resId) {
        if (resId != 0) {
            this.mLeftActionIV.setImageResource(resId);
            this.mLeftActionIV.setVisibility(View.VISIBLE);
        }else {
            this.mLeftActionIV.setVisibility(View.GONE);
        }
    }

    public void setLeftActionText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.mLeftActionTV.setText(text);
            this.mLeftActionTV.setVisibility(View.VISIBLE);
        } else {
            this.mLeftActionTV.setVisibility(View.GONE);
        }
    }

    public void setLeftActionText(@StringRes int resId) {
        setLeftActionText(getResources().getText(resId).toString());
    }

    public void setLeftActionTextColorRes(@ColorRes int resId) {
        this.mLeftActionTV.setTextColor(ContextCompat.getColor(getContext(), resId));
    }

    private void setLeftActionTextColor() {
        this.leftColorId = SkinCompatHelper.checkResourceId(leftColorId);
        if (leftColorId != SkinCompatHelper.INVALID_ID) {
            int color = isLeftTextColorAuto ?
                    SkinCompatResources.getColor(getContext(), R.color.color_title_bar_left_text_color)
                    : SkinCompatResources.getColor(getContext(), leftColorId);
            this.mLeftActionTV.setTextColor(color);
        }
    }

    public void setLeftActionTextSize(int size) {
        this.mLeftActionTV.setTextSize(size);
    }

    public void setLeftActionTextSize(int unit, int size) {
        this.mLeftActionTV.setTextSize(unit, size);
    }

    public void setLeftTextPadding(int paddingLeft, int paddingRight) {
        this.mLeftActionTV.setPadding(paddingLeft, 0, paddingRight, 0);
    }

    public void setRightActionDrawable(@DrawableRes int resId) {
        if (resId != 0) {
            this.mRightActionIV.setImageResource(resId);
            this.mRightActionIV.setVisibility(View.VISIBLE);
        } else {
            this.mRightActionIV.setVisibility(View.GONE);
        }
    }

    public void setRightActionText(String text) {
        if (!TextUtils.isEmpty(text)) {
            this.mRightActionTV.setText(text);
            this.mRightActionTV.setVisibility(View.VISIBLE);
        } else {
            this.mRightActionTV.setVisibility(View.GONE);
        }
    }

    public void setRightActionText(@StringRes int resId) {
        setRightActionText(getResources().getText(resId).toString());
    }

    public void setRightActionTextColorRes(@ColorRes int resId) {
        this.mRightActionTV.setTextColor(ContextCompat.getColor(getContext(), resId));
    }

    private void setRightActionTextColor() {
        this.rightColorId = SkinCompatHelper.checkResourceId(rightColorId);
        if (rightColorId != SkinCompatHelper.INVALID_ID) {
            int color = isRightTextColorAuto ?
                    SkinCompatResources.getColor(getContext(), R.color.color_title_bar_right_text_color)
                    : SkinCompatResources.getColor(getContext(), rightColorId);
            this.mRightActionTV.setTextColor(color);
        }
    }

    public void setRightActionTextSize(int size) {
        this.mRightActionTV.setTextSize(size);
    }

    public void setRightActionTextSize(int unit, int size) {
        this.mRightActionTV.setTextSize(unit, size);
    }

    public void setRightTextPadding(int leftPadding, int rightPadding) {
        this.mRightActionTV.setPadding(leftPadding, 0, rightPadding, 0);
    }

    public TextView getTitleTextView() {
        return this.mTitleTV;
    }

    public void setOnTitleClick(View.OnClickListener listener) {
        this.mTitleTV.setOnClickListener(listener);
    }

    public void setTitleActionDrawable(@DrawableRes int resId) {
        if (resId != 0) {
            mTitleIVActionIV.setImageResource(resId);
            mTitleIVActionIV.setVisibility(View.VISIBLE);
            mTitleTV.setVisibility(View.GONE);
        }else {
            mTitleIVActionIV.setVisibility(View.GONE);
            mTitleTV.setVisibility(View.VISIBLE);
        }
    }

    public void setTitleActionClick(View.OnClickListener listener) {
        this.mTitleIVActionIV.setOnClickListener(listener);
    }

    public TextView getLeftTextView() {
        return this.mLeftActionTV;
    }

    public void setOnLeftTextClick(View.OnClickListener listener) {
        this.mLeftActionTV.setOnClickListener(listener);
    }

    public TextView getRightTextView() {
        return this.mRightActionTV;
    }

    public void setOnRightTextClick(View.OnClickListener listener) {
        this.mRightActionTV.setOnClickListener(listener);
    }

    public ImageView getLeftImageView() {
        return this.mLeftActionIV;
    }

    public void setOnLeftImageClick(View.OnClickListener listener) {
        this.mLeftActionIV.setOnClickListener(listener);
    }

    public ImageView getRightImageView() {
        return this.mRightActionIV;
    }

    public void setOnRightImageClick(View.OnClickListener listener) {
        this.mRightActionIV.setOnClickListener(listener);
    }

    public void setmBottomSeparatorVisible(boolean visible) {
        this.mBottomSeparator.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        getObtainStyleAttributes(attrs, R.style.TitleBarDefaultStyle);

        LayoutInflater.from(getContext()).inflate(R.layout.nn_title_bar_layout, this);

        this.mRootLy = findViewById(R.id.ly_root);
        this.mSeparatorLineLy = findViewById(R.id.separator_line_ly);

        this.mTitleTV = ((TextView)findViewById(R.id.tv_title));
        this.mTitleIVActionIV = (ImageView)findViewById(R.id.iv_title);

        this.mLeftActionIV = ((ImageView)findViewById(R.id.iv_left_action_img));
        this.mLeftActionTV = ((TextView)findViewById(R.id.tv_left_action_text));

        this.mRightActionIV = ((ImageView)findViewById(R.id.iv_right_action_img));
        this.mRightActionTV = ((TextView)findViewById(R.id.tv_right_action_text));

        this.mBottomSeparator = findViewById(R.id.v_bottom_separator);

        if (this.isAutoBackFinish) {
            this.mLeftActionIV.setOnClickListener(this);
        }

        setTitle(this.title);
        setTitleColor();
        setTitleSize(SystemUtils.px2dip(getContext(), this.titleSize));
        setTitleActionDrawable(this.titleImgId);

        setLeftActionDrawable(leftImgId);
        setLeftActionText(this.leftText);
        setLeftActionTextColor();
        setLeftActionTextSize(SystemUtils.px2dip(getContext(), this.leftSize));
        setLeftTextPadding(this.leftTextPaddingLeft, this.leftTextPaddingRight);

        setRightActionDrawable(this.rightImgId);
        setRightActionText(this.rightText);
        setRightActionTextColor();
        setRightActionTextSize(SystemUtils.px2dip(getContext(), this.rightSize));
        setRightTextPadding(this.rightTextPaddingLeft, this.rightTextPaddingRight);

        if (this.isSeparatorLine) {
            setSeparatorLineSize(this.separatorSize);
            setSeparatorLineColor(this.separatorColor);
        }

        if (!this.isShowBottomSeparator) {
            mBottomSeparator.setVisibility(View.GONE);
        }

        Drawable background = getBackground();
        if (background == null) {
            setBackgroundResource(R.color.color_theme_background_color);
        }
    }

    private void getObtainStyleAttributes(AttributeSet attrs, int titleBarDefaultStyle) {
        TypedArray typeArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TitleBar, 0, titleBarDefaultStyle);

        int N = typeArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int a = typeArray.getIndex(i);
            if (a == R.styleable.TitleBar_tb_isSeparatorLine)
                this.isSeparatorLine = typeArray.getBoolean(a, true);
            else if (a == R.styleable.TitleBar_tb_separatorLineColor)
                this.separatorColor = typeArray.getColor(a, ContextCompat.getColor(getContext(), R.color.black));
            else if (a == R.styleable.TitleBar_tb_separatorLineSize)
                this.separatorSize = typeArray.getDimensionPixelSize(a, 0);
            else if (a == R.styleable.TitleBar_tb_title_text)
                this.title = typeArray.getString(a);
            else if (a == R.styleable.TitleBar_tb_title_textColor)
                this.titleColorId = typeArray.getResourceId(a, SkinCompatHelper.INVALID_ID);
            else if (a == R.styleable.TitleBar_tb_title_action_img)
                this.titleImgId = typeArray.getResourceId(a, 0);
            else if (a == R.styleable.TitleBar_tb_title_textSize)
                this.titleSize = typeArray.getDimensionPixelSize(a, 0);
            else if (a == R.styleable.TitleBar_tb_title_textStyle)
                this.titleTextStyle = typeArray.getInt(a, 0);
            else if (a == R.styleable.TitleBar_tb_left_action_img)
                this.leftImgId = typeArray.getResourceId(a, 0);
            else if (a == R.styleable.TitleBar_tb_left_action_text)
                this.leftText = typeArray.getString(a);
            else if (a == R.styleable.TitleBar_tb_left_action_textColor)
                this.leftColorId = typeArray.getResourceId(a, SkinCompatHelper.INVALID_ID);
            else if (a == R.styleable.TitleBar_tb_left_action_textSize)
                this.leftSize = typeArray.getDimensionPixelOffset(a, 0);
            else if (a == R.styleable.TitleBar_tb_left_action_textStyle)
                this.leftTextStyle = typeArray.getInt(a, 0);
            else if (a == R.styleable.TitleBar_tb_right_action_img)
                this.rightImgId = typeArray.getResourceId(a, 0);
            else if (a == R.styleable.TitleBar_tb_right_action_text)
                this.rightText = typeArray.getString(a);
            else if (a == R.styleable.TitleBar_tb_right_action_textColor)
                this.rightColorId = typeArray.getResourceId(a, SkinCompatHelper.INVALID_ID);
            else if (a == R.styleable.TitleBar_tb_right_action_textSize)
                this.rightSize = typeArray.getDimensionPixelSize(a, 0);
            else if (a == R.styleable.TitleBar_tb_right_action_textStyle)
                this.rightTextStyle = typeArray.getInt(a, 0);
            else if (a == R.styleable.TitleBar_tb_left_action_textPaddingLeft)
                this.leftTextPaddingLeft = typeArray.getDimensionPixelOffset(a, 0);
            else if (a == R.styleable.TitleBar_tb_left_action_textPaddingRight)
                this.leftTextPaddingRight = typeArray.getDimensionPixelOffset(a, 0);
            else if (a == R.styleable.TitleBar_tb_right_action_textPaddingLeft)
                this.rightTextPaddingLeft = typeArray.getDimensionPixelOffset(a, 0);
            else if (a == R.styleable.TitleBar_tb_right_action_textPaddingRight)
                this.rightTextPaddingRight = typeArray.getDimensionPixelOffset(a, 0);
            else if (a == R.styleable.TitleBar_tb_isAutoBackFinish)
                this.isAutoBackFinish = typeArray.getBoolean(a, true);
            else if (a == R.styleable.TitleBar_tb_is_show_bottom_separator)
                this.isShowBottomSeparator = typeArray.getBoolean(a, true);
            else if (a == R.styleable.TitleBar_tb_title_text_color_auto)
                this.isTitleTextColorAuto = typeArray.getBoolean(a, true);
            else if (a == R.styleable.TitleBar_tb_left_text_color_auto)
                this.isLeftTextColorAuto = typeArray.getBoolean(a, true);
            else if (a == R.styleable.TitleBar_tb_right_text_color_auto)
                this.isRightTextColorAuto = typeArray.getBoolean(a, true);
        }

        typeArray.recycle();
    }

    public void onClick(View v)
    {
        int viewId = v.getId();
        if (viewId == R.id.iv_left_action_img) {
            Context context = getContext();
            if ((context instanceof Activity))
                ((Activity)context).finish();
        }
    }

    @Override
    public void applySkin() {
        super.applySkin();
        setTitleColor();
        setLeftActionTextColor();
        setRightActionTextColor();
    }
}
