package me.sangs.time.sky.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.Random;

/**
 * Created by dsa28s on 15/05/2017.
 */

public class SkyViewGradientBackgroundPainter {

    private static final int MIN = 4000;
    private static final int MAX = 5000;

    private final Random random;
    private final Handler handler;
    private final View target;
    private final int[] drawables;
    private final Context context;

    public SkyViewGradientBackgroundPainter(@NonNull View target, int[] drawables) {
        this.target = target;
        this.drawables = drawables;
        random = new Random();
        handler = new Handler();
        context = target.getContext().getApplicationContext();
    }

    private void animate(final int firstDrawable, int secondDrawable, final int duration) {
        if(secondDrawable >= drawables.length) {
            secondDrawable = 0;
        }

        final Drawable mFirst = ContextCompat.getDrawable(context, drawables[firstDrawable]);
        final Drawable mSecond = ContextCompat.getDrawable(context, drawables[secondDrawable]);

        final TransitionDrawable mTransitionDrawable = new TransitionDrawable(new Drawable[] {mFirst, mSecond});

        if(Build.VERSION.SDK_INT >= 17) {
            target.setBackground(mTransitionDrawable);
        } else {
            target.setBackgroundDrawable(mTransitionDrawable);
        }

        mTransitionDrawable.setCrossFadeEnabled(true);
        mTransitionDrawable.startTransition(duration);

        final int mLocalSecondDrawable = secondDrawable;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animate(mLocalSecondDrawable, mLocalSecondDrawable + 1, randomInt(MIN, MAX));
            }
        }, duration);
    }

    public void start() {
        final int duration = randomInt(MIN, MAX);
        animate(0, 1, duration);
    }

    public void stop() {
        handler.removeCallbacksAndMessages(null);
    }

    private int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
