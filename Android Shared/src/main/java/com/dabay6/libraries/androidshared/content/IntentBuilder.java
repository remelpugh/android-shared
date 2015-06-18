/*
 * Copyright (c) 2015 Remel Pugh
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

package com.dabay6.libraries.androidshared.content;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION_CODES;

import com.dabay6.libraries.androidshared.util.AndroidUtils;

import java.io.Serializable;

/**
 * IntentBuilder <p> </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
public final class IntentBuilder {
    private final Intent intent;

    /**
     * Create {@link IntentBuilder} with a default action.
     */
    private IntentBuilder(final String action) {
        intent = new Intent(action);
    }

    /**
     * Add extended data to the intent.  The name must include a package prefix, for example the app
     * com.android.contacts would use names like "com.android.contacts.ShowAll".
     *
     * @param name  The name of the extra data, with package prefix.
     * @param value The boolean data value.
     *
     * @return The {@link IntentBuilder} allowing for chaining.
     */
    public IntentBuilder add(final String name, final boolean value) {
        intent.putExtra(name, value);

        return this;
    }

    /**
     * Add extended data to the intent.  The name must include a package prefix, for example the app
     * com.android.contacts would use names like "com.android.contacts.ShowAll".
     *
     * @param name  The name of the extra data, with package prefix.
     * @param value The boolean array data value.
     *
     * @return The {@link IntentBuilder} allowing for chaining.
     */
    public IntentBuilder add(final String name, final boolean[] value) {
        intent.putExtra(name, value);

        return this;
    }

    /**
     * Add extended data to the intent.  The name must include a package prefix, for example the app
     * com.android.contacts would use names like "com.android.contacts.ShowAll".
     *
     * @param name  The name of the extra data, with package prefix.
     * @param value The CharSequence data value.
     *
     * @return The {@link IntentBuilder} allowing for chaining.
     */
    public IntentBuilder add(final String name, final CharSequence value) {
        intent.putExtra(name, value);

        return this;
    }

    /**
     * Add extended data to the intent.  The name must include a package prefix, for example the app
     * com.android.contacts would use names like "com.android.contacts.ShowAll".
     *
     * @param name  The name of the extra data, with package prefix.
     * @param value The CharSequence array data value.
     *
     * @return The {@link IntentBuilder} allowing for chaining.
     */
    public IntentBuilder add(final String name, final CharSequence[] value) {
        intent.putExtra(name, value);

        return this;
    }

    /**
     * Add extended data to the intent.  The name must include a package prefix, for example the app
     * com.android.contacts would use names like "com.android.contacts.ShowAll".
     *
     * @param name  The name of the extra data, with package prefix.
     * @param value The int data value.
     *
     * @return The {@link IntentBuilder} allowing for chaining.
     */
    public IntentBuilder add(final String name, final int value) {
        intent.putExtra(name, value);

        return this;
    }

    /**
     * Add extended data to the intent.  The name must include a package prefix, for example the app
     * com.android.contacts would use names like "com.android.contacts.ShowAll".
     *
     * @param name  The name of the extra data, with package prefix.
     * @param value The integer array data value.
     *
     * @return The {@link IntentBuilder} allowing for chaining.
     */
    public IntentBuilder add(final String name, final int[] value) {
        intent.putExtra(name, value);

        return this;
    }

    /**
     * Add extended data to the intent.  The name must include a package prefix, for example the app
     * com.android.contacts would use names like "com.android.contacts.ShowAll".
     *
     * @param name  The name of the extra data, with package prefix.
     * @param value The Serializable data value.
     *
     * @return The {@link IntentBuilder} allowing for chaining.
     */
    public IntentBuilder add(final String name, final Serializable value) {
        intent.putExtra(name, value);

        return this;
    }

    /**
     * Add extended data to the intent.  The name must include a package prefix, for example the app
     * com.android.contacts would use names like "com.android.contacts.ShowAll".
     *
     * @param name  The name of the extra data, with package prefix.
     * @param value The String data value.
     *
     * @return The {@link IntentBuilder} allowing for chaining.
     */
    public IntentBuilder add(final String name, final String value) {
        intent.putExtra(name, value);

        return this;
    }

    /**
     * Add extended data to the intent.  The name must include a package prefix, for example the app
     * com.android.contacts would use names like "com.android.contacts.ShowAll".
     *
     * @param name  The name of the extra data, with package prefix.
     * @param value The Uri data value.
     *
     * @return The {@link IntentBuilder} allowing for chaining.
     */
    public IntentBuilder add(final String name, final Uri value) {
        intent.putExtra(name, value);

        return this;
    }

    /**
     * Set the data this intent is operating on.
     *
     * @param uri The Uri of the data this intent is now targeting.
     *
     * @return this builder
     */
    public IntentBuilder addData(final Uri uri) {
        intent.setData(uri);

        return this;
    }

    /**
     * Add additional flags to the intent (or with existing flags value).
     *
     * @param flag The new flags to set.
     *
     * @return this builder
     */
    public IntentBuilder addFlag(final int flag) {
        intent.addFlags(flag);

        return this;
    }

    /**
     * Set an explicit MIME data type.
     *
     * @param type The MIME type of the data being handled by this intent.
     *
     * @return this builder
     */
    public IntentBuilder addType(final String type) {
        intent.setType(type);

        return this;
    }

    /**
     * Get built intent
     *
     * @return intent
     */
    public Intent build() {
        return intent;
    }

    /**
     * @return this builder
     */
    @TargetApi(VERSION_CODES.LOLLIPOP)
    public IntentBuilder isExternal() {
        if (AndroidUtils.isAtLeastLollipop()) {
            return addFlag(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        }

        //noinspection deprecation
        return addFlag(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    }

    /**
     * @return {@link IntentBuilder}
     */
    public static IntentBuilder with(final String action) {
        return new IntentBuilder(action);
    }
}
