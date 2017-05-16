package me.sangs.time.sky.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;

import me.sangs.time.sky.view.utils.Point;
import me.sangs.time.sky.view.utils.SkyViewUtils;

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
    private boolean isPlanetVisible = false;
    private int planetPosition = 0;
    private int mPlanetSpeed = 0;
    private boolean isPlanetAnimationOn = false;
    private int[] mDrawables = new int[3];
    private AnimatorSet mAnimatorSet = new AnimatorSet();
    private AppCompatImageView mPlanetView;

    private boolean isChangeWait = false;

    private Handler mHandler = new Handler();

    private ArrayList<Point> mPathList = new ArrayList();

    int mAnimationIndex = 0;

    public SkyTimeBackgroundView(Context context) {
        super(context);
        //initBackground(null, 0);
    }

    public SkyTimeBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //calculateParabolaPath();
        initBackground(attrs, 0);
        startAnimation();
        //planetAnimationStart();
    }

    public SkyTimeBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //calculateParabolaPath();
        initBackground(attrs, defStyleAttr);
        startAnimation();
        //planetAnimationStart();
    }

    private void setTime(Time t) {
        mSkyTime = t;

        if(mSkyTime == Time.AFTERNOON) {
            mDrawables[0] = R.drawable.morning;
            mDrawables[1] = R.drawable.morning_before;
            mDrawables[2] = R.drawable.morning_after;
        } else if(mSkyTime == Time.EARLY_NIGHT) {
            mDrawables[0] = R.drawable.evening;
            mDrawables[1] = R.drawable.evening_before;
            mDrawables[2] = R.drawable.evening_after;
        } else if(mSkyTime == Time.NIGHT) {
            mDrawables[0] = R.drawable.early_evening;
            mDrawables[1] = R.drawable.early_evening_before;
            mDrawables[2] = R.drawable.early_evening_after;

        }
    }

    public void changeTime(Time t) {
        if(mSkyTime != t) {
            setTime(t);

            if(t == Time.AFTERNOON) {
                mPainter.startAnimationInInternalPosition(mDrawables, getBackground(), ContextCompat.getDrawable(getContext(), R.drawable.morning_after));
            } else if(t == Time.EARLY_NIGHT) {
                mPainter.startAnimationInInternalPosition(mDrawables, getBackground(), ContextCompat.getDrawable(getContext(), R.drawable.evening));
            } else if(t == Time.NIGHT) {
                mPainter.startAnimationInInternalPosition(mDrawables, getBackground(), ContextCompat.getDrawable(getContext(), R.drawable.early_evening));
            }

            isChangeWait = true;

            if(!isPlanetAnimationOn) {
                changePlanet();
            }
        }
    }

    private void initBackground(AttributeSet attrs, int defStyle) {
        mPlanetView = new AppCompatImageView(getContext());

        TypedArray mArray;

        if(defStyle == 0) {
            mArray = getContext().obtainStyledAttributes(attrs, R.styleable.SkyTimeBackgroundView);
        } else {
            mArray = getContext().obtainStyledAttributes(attrs, R.styleable.SkyTimeBackgroundView, defStyle, 0);
        }

        isAutoStart = mArray.getBoolean(R.styleable.SkyTimeBackgroundView_autoStart, false);
        isPlanetAnimationOn = mArray.getBoolean(R.styleable.SkyTimeBackgroundView_planetAnimation, false);
        isPlanetVisible = mArray.getBoolean(R.styleable.SkyTimeBackgroundView_planetVisible, true);

        planetPosition = mArray.getInt(R.styleable.SkyTimeBackgroundView_planetPosition, 0);
        mPlanetSpeed = mArray.getInt(R.styleable.SkyTimeBackgroundView_planetSpeed, 300);

        mAnimationIndex = planetPosition;

        mArray.recycle();

        setTime(Time.AFTERNOON);

        mPainter = new SkyViewGradientBackgroundPainter(this, mDrawables);
    }

    private void calculateParabolaPath() {
        double mWidthSplit = SkyViewUtils.getScreenWidth(getContext()) / (double)120;

        for(int i = 0; i <= 120; i++) {
            double mX;
            if(i == 0) {
                mX = 0;
            } else {
                mX = mWidthSplit * i;
            }

            int mRadius = getWidth() / 2 + 200;

            double mArea = mRadius - (getWidth() / 2 + 200);

            double mY = Math.pow(mRadius, 2);
            mY = mY - Math.pow(((getWidth() / 2 - mX) + mArea), 2);
            mY = Math.abs(Math.sqrt(Math.abs(mY)) - getHeight() / 6 * 5);

            Point mPoint = new Point();
            mPoint.x = (float)mX;
            mPoint.y = (float)mY;

            mPathList.add(mPoint);

            //Log.e("TEST", mX + " / " + mY);
        }

        Collections.reverse(mPathList);
    }

    boolean isT = false;

    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        /*Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(1);

        double mWidthSplit = SkyViewUtils.getScreenWidth(getContext()) / (double)100;

        if(!isT) {
            for(int i = 0; i < 100; i++) {
                double mX = mWidthSplit * (i + 1);
                int mRadius = getWidth() / 2 + 200;

                double mArea = mRadius - (getWidth() / 2 + 200);

                double mY = Math.pow(mRadius, 2);
                mY = mY - Math.pow(((getWidth() / 2 - mX) + mArea), 2);
                mY = Math.abs(Math.sqrt(Math.abs(mY)) - getHeight() / 6 * 5);

                canvas.drawCircle((float)mX, (float)mY, 1, mPaint);

                Log.e("TEST", mX + " / " + mY);
            }

            isT = true;
        }*/
        if(!isT) {
            calculateParabolaPath();
            planetAnimationStart();
            isT = true;
        }

    }

    private void startAnimation() {
        if(isAutoStart) {
            start();
        }
    }

    private void changePlanet() {
        if(mSkyTime == Time.AFTERNOON) {
            mPlanetView.setBackgroundResource(R.drawable.sunny);
        } else if(mSkyTime == Time.EARLY_NIGHT) {
            mPlanetView.setBackgroundResource(R.drawable.moon);
        } else if(mSkyTime == Time.NIGHT) {
            mPlanetView.setBackgroundResource(R.drawable.moon_c);
        }
    }

    private void planetAnimationStart() {
        if(isPlanetVisible) {
            if(mAnimatorSet.isRunning()) {
                mAnimatorSet.cancel();
                mAnimatorSet = null;
            }

            try {
                removeView(mPlanetView);
            } catch(Exception e) {

            }

            mPlanetView = null;
            mPlanetView = new AppCompatImageView(getContext());
            final LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(200, 200);
            mPlanetView.setLayoutParams(mParams);

            mPlanetView.setX(mPathList.get(mAnimationIndex).x);
            mPlanetView.setY(mPathList.get(mAnimationIndex).y);

            mHandler.post(new Runnable() {
                @Override
                public void run() {

                }
            });

            changePlanet();

            addView(mPlanetView);

            if(isPlanetAnimationOn) {
                mAnimatorSet = new AnimatorSet();

                ObjectAnimator mXAnimator = ObjectAnimator.ofFloat(mPlanetView, "translationX", mPlanetView.getX(), (float)mPathList.get(mAnimationIndex).x);
                ObjectAnimator mYAnimator = ObjectAnimator.ofFloat(mPlanetView, "translationY", mPlanetView.getY(), (float)mPathList.get(mAnimationIndex).y);

                //mAnimatorSet.setInterpolator(null);
                mAnimatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if(mAnimationIndex >= 500) {
                            mAnimationIndex = 0;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    isChangeWait = false;
                                    planetAnimationStart();
                                }
                            });

                            return;
                        }
                        if(mAnimationIndex >= mPathList.size() - 1) {
                            if(mSkyTime == Time.AFTERNOON) {
                                changeTime(Time.EARLY_NIGHT);
                            } else if(mSkyTime == Time.EARLY_NIGHT) {
                                changeTime(Time.NIGHT);
                            } else if(mSkyTime == Time.NIGHT) {
                                changeTime(Time.AFTERNOON);
                            }

                            ObjectAnimator mXAnimator = ObjectAnimator.ofFloat(mPlanetView, "translationX", mPlanetView.getX(), (float)mPathList.get(120).x - 200);
                            ObjectAnimator mYAnimator = ObjectAnimator.ofFloat(mPlanetView, "translationY", mPlanetView.getY(), (float)mPathList.get(120).y + 200);

                            mAnimatorSet.playTogether(mXAnimator, mYAnimator);
                            mAnimatorSet.setDuration(2000);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mAnimatorSet.start();
                                }
                            });

                            mAnimationIndex = 500;
                        } else {
                            ObjectAnimator mXAnimator;
                            ObjectAnimator mYAnimator;

                            if(isChangeWait) {
                                mXAnimator = ObjectAnimator.ofFloat(mPlanetView, "translationX", mPlanetView.getX(), (float)mPathList.get(mAnimationIndex).x);
                                mYAnimator = ObjectAnimator.ofFloat(mPlanetView, "translationY", mPlanetView.getY(), getHeight() + 300);
                                mAnimatorSet.setDuration(1000);
                                mAnimationIndex = 500;
                            } else {
                                mXAnimator = ObjectAnimator.ofFloat(mPlanetView, "translationX", mPlanetView.getX(), (float)mPathList.get(mAnimationIndex).x);
                                mYAnimator = ObjectAnimator.ofFloat(mPlanetView, "translationY", mPlanetView.getY(), (float)mPathList.get(mAnimationIndex).y);
                            }
                            mAnimatorSet.playTogether(mXAnimator, mYAnimator);
                            mAnimationIndex++;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mAnimatorSet.start();
                                }
                            });

                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }

                    @Override
                    public void onAnimationStart(Animator animation) {

                    }
                });
                mAnimatorSet.playTogether(mXAnimator, mYAnimator);
                mAnimatorSet.setDuration(mPlanetSpeed);
                mAnimatorSet.start();
            }
        }
    }

    public void start() {
        mPainter.start();
    }

    public void destroy() {
        mPainter.stop();
    }
}
