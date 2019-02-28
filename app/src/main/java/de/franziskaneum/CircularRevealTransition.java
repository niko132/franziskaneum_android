package de.franziskaneum;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Niko on 03.05.2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CircularRevealTransition extends ChangeBounds {

    private static final String PROPNAME_VIEW_WIDTH = "de.franziskaneum:cirularrevealtransition:width";

    private long duration = 500;

    public CircularRevealTransition() {
        super();
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMaximumAngle(90);
        arcMotion.setMinimumHorizontalAngle(10);
        arcMotion.setMinimumVerticalAngle(10);
        this.setPathMotion(arcMotion);
    }

    @Override
    public Transition setDuration(long duration) {
        return this;
    }

    public Transition setDuration1(long duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);

        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_VIEW_WIDTH, view.getWidth());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);

        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_VIEW_WIDTH, view.getWidth());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, final TransitionValues endValues) {
        Animator changeBounds = super.createAnimator(sceneRoot, startValues, endValues);

        int startWidth = (int) startValues.values.get(PROPNAME_VIEW_WIDTH);
        int endWidth = (int) endValues.values.get(PROPNAME_VIEW_WIDTH);

        if (startWidth < endWidth) {
            changeBounds.setDuration(500);

            AnimatorSet animatorSet = new AnimatorSet();

            final View v = endValues.view;
            if (v instanceof de.franziskaneum.CircleImageView) {

                final de.franziskaneum.CircleImageView layout = (de.franziskaneum.CircleImageView) v;

                ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0f);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        layout.setCircularPercentage((Float) valueAnimator.getAnimatedValue());
                    }
                });

                valueAnimator.setDuration(500);

                animatorSet.playTogether(changeBounds, valueAnimator);
            } else {
                animatorSet.playSequentially(changeBounds);
            }

            setDuration(-1);

            return animatorSet;
        } else {
            changeBounds.setDuration(500);

            AnimatorSet animatorSet = new AnimatorSet();

            final View v = endValues.view;
            if (v instanceof de.franziskaneum.CircleImageView) {

                final de.franziskaneum.CircleImageView layout = (de.franziskaneum.CircleImageView) v;

                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        layout.setCircularPercentage((Float) valueAnimator.getAnimatedValue());
                    }
                });

                valueAnimator.setDuration(500);

                animatorSet.playTogether(changeBounds, valueAnimator);
            } else {
                animatorSet.playSequentially(changeBounds);
            }

            setDuration(-1);

            return animatorSet;
        }
    }
}
