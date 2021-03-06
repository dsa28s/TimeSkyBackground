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

package me.sangs.time.sky.view.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

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
