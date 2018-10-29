package org.live.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import org.live.baselib.util.SystemUtils;

public class TriangleView extends View {
    private static final String LOCATION_LEFT = "left";
    private static final String LOCATION_TOP = "top";
    private static final String LOCATION_RIGHT = "right";
    private static final String LOCATION_BOTTOM = "bottom";

    private int defaultWidth = 10;
    private int defaultHeight = 10;

    private int width;
    private int height;

    private String location = LOCATION_LEFT;

    private Paint paint;
    private Path leftPath;
    private Path topPath;
    private Path rightPath;
    private Path bottomPath;

    public TriangleView(Context context) {
        super(context);
        init(context, null);
    }

    public TriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TriangleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int color = Color.WHITE;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TriangleView);
            color = ta.getInteger(R.styleable.TriangleView_tri_color, Color.WHITE);
            location = ta.getString(R.styleable.TriangleView_tri_location);
            if (TextUtils.isEmpty(location)) {
                location = LOCATION_LEFT;
            }
            ta.recycle();
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);

        width = SystemUtils.dip2px(getContext(), defaultWidth);
        height = SystemUtils.dip2px(getContext(), defaultHeight);
        makePath();
    }

    private void makePath() {
        leftPath = new Path();
        leftPath.moveTo(width, 0);
        leftPath.lineTo(0, height / 2);
        leftPath.lineTo(width, height);
        leftPath.close();

        topPath = new Path();
        topPath.moveTo(width / 2, 0);
        topPath.lineTo(0, height);
        topPath.lineTo(width, height);
        topPath.close();

        rightPath = new Path();
        rightPath.moveTo(0, 0);
        rightPath.lineTo(width, height / 2);
        rightPath.lineTo(0, height);
        rightPath.close();

        bottomPath = new Path();
        bottomPath.moveTo(0, 0);
        bottomPath.lineTo(width, 0);
        bottomPath.lineTo(width / 2, height);
        bottomPath.close();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = SystemUtils.dip2px(getContext(), defaultWidth);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = SystemUtils.dip2px(getContext(), defaultHeight);
        }

        if (width > 0 && height > 0) {
            setMeasuredDimension(width, height);
            makePath();
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path;
        switch (location) {
            case LOCATION_LEFT:
                path = leftPath;
                break;
            case LOCATION_TOP:
                path = topPath;
                break;
            case LOCATION_RIGHT:
                path = rightPath;
                break;
            case LOCATION_BOTTOM:
                path = bottomPath;
                break;
            default:
                path = leftPath;
                break;
        }
        if (path != null) {
            canvas.drawPath(path, paint);
        }
    }

    public void setColor(int color) {
        if (paint != null) {
            paint.setColor(color);
        }
    }

    public void setGravity(int gravity) {
        if (gravity == Gravity.LEFT) {
            location = LOCATION_LEFT;
        } else if (gravity == Gravity.RIGHT) {
            location = LOCATION_RIGHT;
        } else if (gravity == Gravity.TOP) {
            location = LOCATION_TOP;
        } else if (gravity == Gravity.BOTTOM) {
            location = LOCATION_BOTTOM;
        }
    }
}