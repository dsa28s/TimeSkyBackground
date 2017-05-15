package me.sangs.time.sky.view;

import android.content.Context;
import android.content.res.TypedArray;
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

    public void setTime(Time t) {
        mSkyTime = t;
    }

    public void changeTime(Time t) {
        mSkyTime = t;
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

        final int[] mDrawables = new int[3];

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
