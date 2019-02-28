package de.franziskaneum;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Niko on 07.05.2017.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ChangeImageTransition extends Transition {
    private Paint paint;

    public ChangeImageTransition() {
        super();
        paint = new Paint();
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof ImageView) {
            ImageView view = (ImageView) transitionValues.view;
            transitionValues.values.put("bitmap", ((BitmapDrawable) view.getDrawable()).getBitmap());
        }
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        if (endValues.view instanceof ImageView) {
            final ImageView view = (ImageView) endValues.view;

            final Bitmap toBitmap = (Bitmap) endValues.values.get("bitmap");
            final Bitmap fromBitmap = Bitmap.createScaledBitmap((Bitmap) startValues.values.get("bitmap"), toBitmap.getWidth(), toBitmap.getHeight(), true);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    paint.setAlpha((int) ((float) valueAnimator.getAnimatedValue() * 255f));

                    Bitmap currentBitmap = toBitmap.copy(toBitmap.getConfig(), true);
                    Canvas canvas = new Canvas(currentBitmap);
                    canvas.drawBitmap(fromBitmap, 0, 0, paint);

                    view.setImageBitmap(currentBitmap);
                }
            });
            return valueAnimator;
        } else
            return super.createAnimator(sceneRoot, startValues, endValues);
    }
}
