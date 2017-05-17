/*
 * Copyright (C) 2017 MR.LEE_(LEE SANG HUN)(leeshoon1344@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

public class SkyViewGradientBackgroundPainter {

    private static final int MIN = 2000;
    private static final int MAX = 4500;

    private final Random random;
    private final Handler handler;
    private final View target;
    private int[] drawables;
    private final Context context;

    private Drawable mFirst;
    private Drawable mSecond;

    private boolean isChanged = false;

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

        setDrawables(drawables, firstDrawable, secondDrawable);

        mFirst = ContextCompat.getDrawable(context, drawables[firstDrawable]);
        mSecond = ContextCompat.getDrawable(context, drawables[secondDrawable]);

        final TransitionDrawable mTransitionDrawable = new TransitionDrawable(new Drawable[] {mFirst, mSecond});

        if(Build.VERSION.SDK_INT >= 17) {
            target.setBackground(mTransitionDrawable);
        } else {
            target.setBackgroundDrawable(mTransitionDrawable);
        }

        mTransitionDrawable.setCrossFadeEnabled(false);
        mTransitionDrawable.startTransition(duration);

        final int mLocalSecondDrawable = secondDrawable;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animate(mLocalSecondDrawable, mLocalSecondDrawable + 1, randomInt(MIN, MAX));
            }
        }, duration);
    }

    private void animate(Drawable d1, Drawable d2, int duration) {
        mFirst = d1;
        mSecond = d2;

        final TransitionDrawable mTransitionDrawable = new TransitionDrawable(new Drawable[] {mFirst, mSecond});

        if(Build.VERSION.SDK_INT >= 17) {
            target.setBackground(mTransitionDrawable);
        } else {
            target.setBackgroundDrawable(mTransitionDrawable);
        }

        mTransitionDrawable.setCrossFadeEnabled(false);
        mTransitionDrawable.startTransition(duration);

        if(isChanged) {
            isChanged = false;
            animate(mFirst, mSecond, 2000);
            //animate(0, 1, randomInt(MIN, MAX));
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animate(0, 1, randomInt(MIN, MAX));
                }
            }, 2000);
        }
    }

    protected void setDrawables(int[] drawables, int i, int ii) {
        this.drawables = drawables;

        mFirst = ContextCompat.getDrawable(context, drawables[i]);
        mSecond = ContextCompat.getDrawable(context, drawables[ii]);
    }

    public void start() {
        final int duration = randomInt(MIN, MAX);
        animate(0, 1, duration);
    }

    protected void startAnimationInInternalPosition(int[] drawables, Drawable d1, Drawable d2) {
        isChanged = true;
        stop();

        setDrawables(drawables, 0, 1);
        animate(d1, d2, randomInt(MIN, MAX));
    }

    public void stop() {
        handler.removeCallbacksAndMessages(null);
    }

    private int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
