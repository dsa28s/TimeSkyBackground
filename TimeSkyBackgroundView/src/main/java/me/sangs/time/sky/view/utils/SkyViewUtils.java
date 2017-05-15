package me.sangs.time.sky.view.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by dsa28s on 15/05/2017.
 */

public class SkyViewUtils {

    public static int getScreenWidth(Context c) {
        WindowManager mWindowManager = (WindowManager)c.getSystemService(Context.WINDOW_SERVICE);
        Display mDisplay = mWindowManager.getDefaultDisplay();

        DisplayMetrics mMetrics = new DisplayMetrics();
        mDisplay.getMetrics(mMetrics);

        return mMetrics.widthPixels;
    }

    public static int getScreenHeight(Context c) {
        WindowManager mWindowManager = (WindowManager)c.getSystemService(Context.WINDOW_SERVICE);
        Display mDisplay = mWindowManager.getDefaultDisplay();

        DisplayMetrics mMetrics = new DisplayMetrics();
        mDisplay.getMetrics(mMetrics);

        return mMetrics.heightPixels;
    }
}
