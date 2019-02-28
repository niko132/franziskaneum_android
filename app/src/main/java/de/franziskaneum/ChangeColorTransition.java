package de.franziskaneum;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Niko on 07.05.2017.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class ChangeColorTransition extends Transition {
    private static final String PROPNAME_BACKGROUND_COLOR = "de.franziskaneum:changecolortransition:backgroundcolor";

    private void caputreValues(TransitionValues transitionValues) {
        View view = transitionValues.view;

        int color = Color.TRANSPARENT;
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();

        transitionValues.values.put(PROPNAME_BACKGROUND_COLOR, color);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        caputreValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        caputreValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        int colorFrom = (int) startValues.values.get(PROPNAME_BACKGROUND_COLOR);
        int colorTo = (int) endValues.values.get(PROPNAME_BACKGROUND_COLOR);

        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                endValues.view.setBackgroundColor((Integer) animator.getAnimatedValue());
            }

        });

        return colorAnimator;
    }
}
