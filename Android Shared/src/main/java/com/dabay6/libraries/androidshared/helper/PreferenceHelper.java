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

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.AndroidUtils;
import com.dabay6.libraries.androidshared.util.SharedPreferenceUtils;

import java.util.Map;
import java.util.Set;

/**
 * A fluent wrapper around the android preferences.
 *
 * @author Remel Pugh
 */
@SuppressWarnings("unused")
public class PreferenceHelper {
    private final static String TAG = Logger.makeTag(PreferenceHelper.class);
    private final static Object lock = new Object();
    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;
    private static PreferenceHelper singleton = null;

    /**
     * @param context the {@link Context} used to access {@link SharedPreferences}.
     */
    private PreferenceHelper(final Context context) {
        this(context, TAG, Context.MODE_PRIVATE);
    }

    /**
     * @param context the {@link Context} used to access {@link SharedPreferences}.
     * @param name    Desired preferences file. If a preferences file by this name does not exist, it will be created
     *                when you retrieve an editor (SharedPreferences.edit()) and then commit changes (Editor.commit()).
     * @param mode    Operating mode.
     */
    @SuppressLint("CommitPrefEdits")
    private PreferenceHelper(final Context context, final String name, final int mode) {
        preferences = SharedPreferenceUtils.get(context, name, mode);
        editor = preferences.edit();
    }

    /**
     * Mark in the editor to remove <em>all</em> values from the preferences.
     *
     * @return {@link PreferenceHelper}
     */
    public PreferenceHelper clear() {
        editor.clear();
        commitChanges();

        return this;
    }

    /**
     * Retrieve all values from the preferences.
     *
     * @return Returns a map containing a list of pairs key/value representing the preferences.
     */
    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    /**
     * Retrieve a boolean value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defaultValue.
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     * Retrieve a float value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defaultValue.
     */
    public float getFloat(String key, float defaultValue) {
        return preferences.getFloat(key, defaultValue);
    }

    /**
     * Retrieve a int value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defaultValue.
     */
    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    /**
     * Retrieve a long value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defaultValue.
     */
    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    /**
     * Retrieve a string value from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defaultValue.
     */
    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    /**
     * Retrieve a set of String values from the preferences.
     *
     * @param key          The name of the preference to retrieve.
     * @param defaultValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defaultValue.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        if (AndroidUtils.isAtLeastHoneycomb()) {
            return preferences.getStringSet(key, defaultValue);
        }

        return null;
    }

    /**
     * Registers a callback to be invoked when a change happens to a preference.
     *
     * @param listener The callback that will run.
     *
     * @return {@link PreferenceHelper}
     */
    public PreferenceHelper registerListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.registerOnSharedPreferenceChangeListener(listener);

        return this;
    }

    public void remove(String key) {
        editor.remove(key);
        commitChanges();
    }

    /**
     * Set a boolean value in the preferences editor.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public void save(String key, boolean value) {
        editor.putBoolean(key, value);
        commitChanges();
    }

    /**
     * Set a float value in the preferences editor.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public void save(String key, float value) {
        editor.putFloat(key, value);
        commitChanges();
    }

    /**
     * Set a int value in the preferences editor.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public void save(String key, int value) {
        editor.putInt(key, value);
        commitChanges();
    }

    /**
     * Set a long value in the preferences editor.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public void save(String key, long value) {
        editor.putLong(key, value);
        commitChanges();
    }

    /**
     * Set a String value in the preferences editor.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public void save(String key, String value) {
        editor.putString(key, value);
        commitChanges();
    }

    /**
     * Set a set of String values in the preferences editor.
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void save(String key, Set<String> value) {
        if (AndroidUtils.isAtLeastHoneycomb()) {
            editor.putStringSet(key, value);
            commitChanges();
        }
    }

    /**
     * Unregisters a previous callback.
     *
     * @param listener The callback that should be unregistered.
     *
     * @return {@link PreferenceHelper}
     */
    public PreferenceHelper unregisterListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener);

        return this;
    }

    /**
     * @param context the {@link Context} used to access {@link SharedPreferences}.
     *
     * @return {@link PreferenceHelper}
     */
    public static PreferenceHelper with(Context context) {
        if (singleton == null) {
            synchronized (lock) {
                if (singleton == null) {
                    singleton = new PreferenceHelper(context);
                }
            }
        }

        return singleton;
    }

    /**
     * @param context the {@link Context} used to access {@link SharedPreferences}.
     * @param name    Desired preferences file. If a preferences file by this name does not exist, it will be created
     *                when you retrieve an editor (SharedPreferences.edit()) and then commit changes (Editor.commit()).
     * @param mode    Operating mode.
     *
     * @return {@link PreferenceHelper}
     */
    public static PreferenceHelper with(Context context, final String name, final int mode) {
        if (singleton == null) {
            synchronized (lock) {
                if (singleton == null) {
                    singleton = new PreferenceHelper(context, name, mode);
                }
            }
        }

        return singleton;
    }

    private void commitChanges() {
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
}