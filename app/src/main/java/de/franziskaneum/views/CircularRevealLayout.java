package de.franziskaneum.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Niko on 27.08.2017.
 */

public class CircularRevealLayout extends FrameLayout {
    private float mCircularPercentage = 0.5f;
    private float mCornerRadius = 0;
    private Path mClipPath;

    public CircularRevealLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public CircularRevealLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularRevealLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircularRevealLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mClipPath = new Path();
    }

    public void setCircularPercentage(float circularPercentage) {
        this.mCircularPercentage = circularPercentage;
        calculateCornerRadius(Math.max(getWidth(), getHeight()));
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateCornerRadius(Math.max(w, h));
        invalidate();
    }

    private void calculateCornerRadius(int size) {
        double max = size / 2;
        mCornerRadius = (float) (mCircularPercentage * max);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mClipPath.reset();
        mClipPath.addRoundRect(new RectF(canvas.getClipBounds()), mCornerRadius, mCornerRadius, Path.Direction.CW);
        canvas.clipPath(mClipPath);
        super.onDraw(canvas);
    }
}
