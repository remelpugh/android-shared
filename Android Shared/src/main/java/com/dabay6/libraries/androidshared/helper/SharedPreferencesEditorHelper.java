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
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.AndroidUtils;
import com.dabay6.libraries.androidshared.util.SharedPreferenceUtils;

import java.util.Set;

/**
 * SharedPreferencesEditorHelper
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class SharedPreferencesEditorHelper {
    private final static String TAG = Logger.makeTag(SharedPreferencesEditorHelper.class);
    private final Editor editor;

    /**
     * @param context
     */
    public SharedPreferencesEditorHelper(final Context context) {
        editor = SharedPreferenceUtils.get(context).edit();
    }

    /**
     * @param context
     * @param name
     * @param mode
     */
    public SharedPreferencesEditorHelper(final Context context, final String name, final int mode) {
        editor = SharedPreferenceUtils.get(context, name, mode).edit();
    }

    /**
     * @return
     */
    public SharedPreferencesEditorHelper clear() {
        editor.clear();
        commit();

        return this;
    }

    /**
     *
     */
    public void commit() {
        commit(false);
    }

    /**
     * @param forceSave
     */
    public void commit(final boolean forceSave) {
        if (forceSave) {
            editor.commit();

            return;
        }

        if (AndroidUtils.isAtLeastGingerbread()) {
            editor.apply();
        }
        else {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(final Void... voids) {
                    editor.commit();

                    return null;
                }
            }.execute();
        }
    }

    /**
     * @param key
     * @param value
     *
     * @return
     */
    public SharedPreferencesEditorHelper putBoolean(final String key, final Boolean value) {
        editor.putBoolean(key, value);

        return this;
    }

    /**
     * @param key
     * @param value
     *
     * @return
     */
    public SharedPreferencesEditorHelper putFloat(final String key, final Float value) {
        editor.putFloat(key, value);

        return this;
    }

    /**
     * @param key
     * @param value
     *
     * @return
     */
    public SharedPreferencesEditorHelper putInt(final String key, final Integer value) {
        editor.putInt(key, value);

        return this;
    }

    /**
     * @param key
     * @param value
     *
     * @return
     */
    public SharedPreferencesEditorHelper putLong(final String key, final Long value) {
        editor.putLong(key, value);

        return this;
    }

    /**
     * @param key
     * @param value
     *
     * @return
     */
    public SharedPreferencesEditorHelper putString(final String key, final String value) {
        editor.putString(key, value);

        return this;
    }

    /**
     * @param key
     * @param value
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SharedPreferencesEditorHelper putStringSet(final String key, final Set<String> value) {
        if (AndroidUtils.isAtLeastHoneycomb()) {
            editor.putStringSet(key, value);

            return this;
        }

        return this;
    }
}