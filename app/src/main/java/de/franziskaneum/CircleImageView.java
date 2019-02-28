package de.franziskaneum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Niko on 27.08.2017.
 */

public class CircleImageView extends AppCompatImageView {
    private float mCircularPercentage = 0.0f;
    private float mCornerRadius = 0;
    private Path mClipPath;
    private boolean mDisableCircularTransform;

    public CircleImageView(@NonNull Context context) {
        super(context);
        init();
    }

    public CircleImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleImageView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    public void setDisableCircularTransformation(boolean disable) {
        this.mDisableCircularTransform = disable;
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
        if (!mDisableCircularTransform) {
            mClipPath.reset();
            mClipPath.addRoundRect(new RectF(canvas.getClipBounds()), mCornerRadius, mCornerRadius, Path.Direction.CW);
            canvas.clipPath(mClipPath);
        }
        super.onDraw(canvas);
    }
}