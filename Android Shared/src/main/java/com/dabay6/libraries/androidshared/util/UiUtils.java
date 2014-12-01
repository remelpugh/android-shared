/*
 * Copyright (c) 2014 Remel Pugh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.dabay6.libraries.androidshared.util;

import android.content.Context;
import android.content.res.Configuration;

/**
 * UiUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class UiUtils {
    /**
     * Hidden constructor
     */
    private UiUtils() {
    }

    /**
     * Determine if the device is a Honeycomb or better tablet.
     *
     * @param context {@link Context} used to check the device {@link Configuration}.
     * @return true if the device is a HONEYCOMB tablet, otherwise false.
     */
    public static boolean isHoneycombTablet(final Context context) {
        return AndroidUtils.isAtLeastHoneycomb() && UiUtils.isTablet(context);
    }

    /**
     * Determine if the device is a tablet.
     *
     * @param context {@link Context} used to check the device {@link Configuration}.
     * @return true if the device is a tablet, otherwise false.
     */
    public static boolean isTablet(final Context context) {
        final Configuration configuration = context.getResources().getConfiguration();

        return (configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}