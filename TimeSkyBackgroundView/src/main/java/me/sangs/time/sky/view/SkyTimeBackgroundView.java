package me.sangs.time.sky.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by dsa28s on 15/05/2017.
 */

public class SkyTimeBackgroundView extends RelativeLayout {
    private SkyViewGradientBackgroundPainter mPainter;

    public enum Time {
        AFTERNOON,
        EARLY_NIGHT,
        NIGHT
    }

    private Time mSkyTime = Time.AFTERNOON;
    private boolean isAutoStart = false;
    private int[] mDrawables = new int[3];

    public SkyTimeBackgroundView(Context context) {
        super(context);
        //initBackground(null, 0);
    }

    public SkyTimeBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initBackground(attrs, 0);
        startAnimation();
    }

    public SkyTimeBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initBackground(attrs, defStyleAttr);
        startAnimation();
    }

    private void setTime(Time t) {
        mSkyTime = t;

        if(mSkyTime == Time.AFTERNOON) {
            mDrawables[0] = R.drawable.morning;
            mDrawables[1] = R.drawable.morning_before;
            mDrawables[2] = R.drawable.morning_after;
        } else if(mSkyTime == Time.EARLY_NIGHT) {
            mDrawables[0] = R.drawable.early_evening;
            mDrawables[1] = R.drawable.early_evening_before;
            mDrawables[2] = R.drawable.early_evening_after;
        } else if(mSkyTime == Time.NIGHT) {
            mDrawables[0] = R.drawable.evening;
            mDrawables[1] = R.drawable.evening_before;
            mDrawables[2] = R.drawable.evening_after;
        }
    }

    public void changeTime(Time t) {
        setTime(t);

        if(t == Time.AFTERNOON) {
            mPainter.startAnimationInInternalPosition(mDrawables, getBackground(), ContextCompat.getDrawable(getContext(), R.drawable.morning_after));
        } else if(t == Time.EARLY_NIGHT) {
            mPainter.startAnimationInInternalPosition(mDrawables, getBackground(), ContextCompat.getDrawable(getContext(), R.drawable.early_evening));
        } else if(t == Time.NIGHT) {
            mPainter.startAnimationInInternalPosition(mDrawables, getBackground(), ContextCompat.getDrawable(getContext(), R.drawable.evening));
        }

    }

    private void initBackground(AttributeSet attrs, int defStyle) {
        TypedArray mArray;

        if(defStyle == 0) {
            mArray = getContext().obtainStyledAttributes(attrs, R.styleable.SkyTimeBackgroundView);
        } else {
            mArray = getContext().obtainStyledAttributes(attrs, R.styleable.SkyTimeBackgroundView, defStyle, 0);
        }

        isAutoStart = mArray.getBoolean(R.styleable.SkyTimeBackgroundView_autoStart, false);

        mArray.recycle();

        setTime(Time.AFTERNOON);

        mPainter = new SkyViewGradientBackgroundPainter(this, mDrawables);
    }

    private void startAnimation() {
        if(isAutoStart) {
            start();
        }
    }

    public void start() {
        mPainter.start();
    }

    public void destroy() {
        mPainter.stop();
    }
}
