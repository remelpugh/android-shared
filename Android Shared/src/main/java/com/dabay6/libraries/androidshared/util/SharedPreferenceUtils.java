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
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferenceUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class SharedPreferenceUtils {
    /**
     * Hidden constructor.
     */
    private SharedPreferenceUtils() {
    }

    /**
     * Gets a SharedPreferences instance that points to the default file.
     *
     * @param context the {@link Context} used to access {@link SharedPreferences}.
     *
     * @return the single {@link SharedPreferences} instance that can be used to retrieve and modify the preference
     */
    public static SharedPreferences get(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Retrieve and hold the contents of the preferences file 'name', returning a SharedPreferences through which you
     * can retrieve and modify its values.
     *
     * @param context the {@link Context} used to access {@link SharedPreferences}.
     * @param name    Desired preferences file. If a preferences file by this name does not exist, it will be created
     *                when you retrieve an editor (SharedPreferences.edit()) and then commit changes (Editor.commit()).
     * @param mode    Operating mode.
     *
     * @return the single {@link SharedPreferences} instance that can be used to retrieve and modify the preference
     * values.
     */
    public static SharedPreferences get(final Context context, final String name, final int mode) {
        return context.getSharedPreferences(name, mode);
    }
}