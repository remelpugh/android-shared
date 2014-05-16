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

package com.dabay6.libraries.androidshared.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.dabay6.libraries.androidshared.util.AndroidUtils;
import com.dabay6.libraries.androidshared.util.SharedPreferenceUtils;

import java.util.Set;

/**
 * SharedPreferencesHelper
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class SharedPreferencesHelper {
    private final SharedPreferences preferences;

    /**
     * @param context
     */
    public SharedPreferencesHelper(final Context context) {
        preferences = SharedPreferenceUtils.get(context);
    }

    /**
     * @param context
     * @param name
     * @param mode
     */
    public SharedPreferencesHelper(final Context context, final String name, final int mode) {
        preferences = SharedPreferenceUtils.get(context, name, mode);
    }

    /**
     * @param key
     * @param defaultValue
     *
     * @return
     */
    public Boolean booleanValue(final String key, final Boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     * @param key
     * @param defaultValue
     *
     * @return
     */
    public Float floatValue(final String key, final Float defaultValue) {
        return preferences.getFloat(key, defaultValue);
    }

    /**
     * @param key
     * @param defaultValue
     *
     * @return
     */
    public Integer intValue(final String key, final Integer defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    /**
     * @param key
     * @param defaultValue
     *
     * @return
     */
    public long longValue(final String key, final Long defaultValue) {
        if (preferences.contains(key)) {
            return preferences.getLong(key, 0);
        }

        return defaultValue;
    }

    /**
     * @param listener
     *
     * @return
     */
    public SharedPreferencesHelper registerListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.registerOnSharedPreferenceChangeListener(listener);

        return this;
    }

    /**
     * @param key
     * @param defaultValue
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> stringSetValue(final String key, final Set<String> defaultValue) {
        if (AndroidUtils.isAtLeastHoneycomb()) {
            return preferences.getStringSet(key, defaultValue);
        }

        return null;
    }

    /**
     * @param key
     * @param defaultValue
     *
     * @return
     */
    public String stringValue(final String key, final String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    /**
     * @param listener
     *
     * @return
     */
    public SharedPreferencesHelper unRegisterListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener);

        return this;
    }
}