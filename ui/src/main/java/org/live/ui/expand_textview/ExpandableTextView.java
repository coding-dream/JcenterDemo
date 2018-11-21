package org.live.ui.expand_textview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

public class ExpandableTextView extends AppCompatTextView {
    public static final int MAX_LINE_COUNT = 7;

    private TimeInterpolator mExpandInterpolator;
    private TimeInterpolator mCollapseInterpolator;

    private long mAnimationDuration = 300;
    private int mCollapsedHeight;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mExpandInterpolator = new AccelerateDecelerateInterpolator();
        mCollapseInterpolator = new AccelerateDecelerateInterpolator();
        setLineSpacing(0, 1.2f);
        setEllipsize(TextUtils.TruncateAt.END);
    }

    public void toggle(TextState textState, View readMoreBtn) {
        readMoreBtn.setEnabled(false);
        if (textState.getTextState() == TextState.STATE_EXPANDED) {
            textState.setTextState(TextState.STATE_COLLAPSED);
            collapseWithAnimator(readMoreBtn);
        } else if (textState.getTextState() == TextState.STATE_COLLAPSED) {
            textState.setTextState(TextState.STATE_EXPANDED);
            expandWithAnimator(readMoreBtn);
        } else {
            readMoreBtn.setEnabled(true);
        }
    }

    private void collapseWithAnimator(final View readMoreBtn) {
        final int expandedHeight = getMeasuredHeight();

        final ValueAnimator valueAnimator = ValueAnimator.ofInt(expandedHeight, mCollapsedHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                final ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();
                setLayoutParams(layoutParams);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                setMaxLines(MAX_LINE_COUNT);

                final ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                setLayoutParams(layoutParams);
                readMoreBtn.setEnabled(true);
            }
        });

        valueAnimator.setInterpolator(mCollapseInterpolator);
        valueAnimator.setDuration(mAnimationDuration).start();
        readMoreBtn.animate().rotationBy(0).setDuration(mAnimationDuration).start();
    }

    private void expandWithAnimator(final View readMoreBtn) {
        measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        mCollapsedHeight = getMeasuredHeight();

        setMaxLines(Integer.MAX_VALUE);

        measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY), MeasureSpec
                .makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

        final int expandedHeight = getMeasuredHeight();

        final ValueAnimator valueAnimator = ValueAnimator.ofInt(mCollapsedHeight, expandedHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                final ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();
                setLayoutParams(layoutParams);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                final ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                setLayoutParams(layoutParams);
                readMoreBtn.setEnabled(true);
            }
        });

        valueAnimator.setInterpolator(mExpandInterpolator);
        valueAnimator.setDuration(mAnimationDuration).start();
        readMoreBtn.animate().rotationBy(0).setDuration(mAnimationDuration).start();
    }
}