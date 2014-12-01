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

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

/**
 * AndroidUtils
 * <p>
 * Provides helper methods for the Android OS.
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class AndroidUtils {
    /**
     * Hidden constructor
     */
    private AndroidUtils() {
    }

    /**
     * Determine if the OS is at least FROYO (API Level 8).
     *
     * @return true if the android version is FROYO (API Level 8) or greater, otherwise false.
     */
    public static boolean isAtLeastFroyo() {
        return VERSION.SDK_INT >= VERSION_CODES.FROYO;
    }

    /**
     * Determine if the OS is at least GINGERBREAD (API Level 9).
     *
     * @return true if the android version is GINGERBREAD (API Level 9) or greater, otherwise false.
     */
    public static boolean isAtLeastGingerbread() {
        return VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
    }

    /**
     * Determine if the OS is at least GINGERBREAD MR1 (API Level 10).
     *
     * @return true if the android version is GINGERBREAD MR1 (API Level 10) or greater, otherwise false.
     */
    public static boolean isAtLeastGingerbreadMR1() {
        return VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD_MR1;
    }

    /**
     * Determine if the OS is at least HONEYCOMB (API Level 11).
     *
     * @return true if the android version is HONEYCOMB (API Level 11) or greater, otherwise false.
     */
    public static boolean isAtLeastHoneycomb() {
        return VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
    }

    /**
     * Determine if the OS is at least HONEYCOMB MR1 (API Level 12).
     *
     * @return true if the android version is HONEYCOMB MR1 (API Level 12) or greater, otherwise false.
     */
    public static boolean isAtLeastHoneycombMR1() {
        return VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * Determine if the OS is at least HONEYCOMB MR2 (API Level 13).
     *
     * @return true if the android version is HONEYCOMB MR2 (API Level 13) or greater, otherwise false.
     */
    public static boolean isAtLeastHoneycombMR2() {
        return VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR2;
    }

    /**
     * Determine if the OS is at least ICECREAM SANDWICH (API Level 14).
     *
     * @return true if the android version is ICECREAM SANDWICH (API Level 14) or greater, otherwise false.
     */
    public static boolean isAtLeastICS() {
        return VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * Determine if the OS is at least ICECREAM SANDWICH MR1 (API Level 15).
     *
     * @return true if the android version is ICECREAM SANDWICH MR1 (API Level 15) or greater, otherwise false.
     */
    public static boolean isAtLeastICSMR1() {
        return VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    /**
     * Determine if the OS is at least JELLYBEAN (API Level 16).
     *
     * @return true if the android version is JELLYBEAN (API Level 16) or greater, otherwise false.
     */
    public static boolean isAtLeastJellyBean() {
        return VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Determine if the OS is at least JELLYBEAN MR1 (API Level 17).
     *
     * @return true if the android version is JELLYBEAN (API Level 17) or greater, otherwise false.
     */
    public static boolean isAtLeastJellyBeanMR1() {
        return VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * Determine if the OS is at least the passed in version.
     *
     * @param version the {@link android.os.Build.VERSION_CODES} to check against.
     * @return true if the android version is at least the version passed in, otherwise false.
     */
    public static boolean isVersionAtLeast(final int version) {
        return VERSION.SDK_INT >= version;
    }

    /**
     * Determine if the OS is older than the passed in version.
     *
     * @param version the {@link android.os.Build.VERSION_CODES} to check against.
     * @return true if the android version is less than the version passed in, otherwise false.
     */
    public static boolean isVersionBefore(final int version) {
        return VERSION.SDK_INT < version;
    }
}