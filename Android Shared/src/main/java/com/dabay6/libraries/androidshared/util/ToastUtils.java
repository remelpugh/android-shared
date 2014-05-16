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

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

/**
 * ToastUtils
 * <p>
 * Displays a {@link Toast} message.
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class ToastUtils {
    public static int DEFAULT_DURATION = Toast.LENGTH_SHORT;

    /**
     * Make a standard toast that just contains a text view with the text from a resource.
     *
     * @param activity          {@link Activity} used to create the {@link Toast} message
     * @param messageResourceId The resource containing the message to be displayed
     */
    public static void show(final Activity activity, final int messageResourceId) {
        ToastUtils.show(activity, messageResourceId, ToastUtils.DEFAULT_DURATION);
    }

    /**
     * Make a standard toast that just contains a text view with the text from a resource.
     *
     * @param activity          {@link Activity} used to create the {@link Toast} message
     * @param messageResourceId The resource containing the message to be displayed
     * @param duration          How long to display the message. Either {@link Toast#LENGTH_SHORT} or {@link
     *                          Toast#LENGTH_LONG}
     */
    public static void show(final Activity activity, final int messageResourceId, final int duration) {
        ToastUtils.showToast(activity, activity.getString(messageResourceId), null, duration);
    }

    /**
     * Make a standard toast that just contains a text view
     *
     * @param activity {@link Activity} used to create the {@link Toast} message
     * @param message  The message to be displayed
     */
    public static void show(final Activity activity, final String message) {
        ToastUtils.show(activity, message, ToastUtils.DEFAULT_DURATION);
    }

    /**
     * Make a standard toast that just contains a text view
     *
     * @param activity {@link Activity} used to create the {@link Toast} message
     * @param message  The message to be displayed
     * @param duration How long to display the message. Either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     */
    public static void show(final Activity activity, final String message, final int duration) {
        ToastUtils.showToast(activity, message, null, duration);
    }

    /**
     * Make a standard toast that displays a {@link View}.
     *
     * @param activity {@link Activity} used to create the {@link Toast} message
     * @param view     The {@link View} to show
     */
    public static void show(final Activity activity, final View view) {
        ToastUtils.show(activity, view, ToastUtils.DEFAULT_DURATION);
    }

    /**
     * Make a standard toast that displays a {@link View}.
     *
     * @param activity {@link Activity} used to create the {@link Toast} message
     * @param view     The {@link View} to show
     * @param duration How long to display the message. Either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     */
    public static void show(final Activity activity, final View view, final int duration) {
        ToastUtils.showToast(activity, null, view, duration);
    }

    /**
     * @param activity {@link Activity} used to create the {@link Toast} message.
     * @param message  The message to be displayed.
     * @param view     The {@link View} to be displayed.
     * @param duration How long to display the message. Either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}.
     */
    private static void showToast(final Activity activity, final String message, final View view, final int duration) {
        if (activity == null) {
            return;
        }
        if (TextUtils.isEmpty(message) && view == null) {
            return;
        }

        final Context context = activity.getApplicationContext();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Toast toast = Toast.makeText(context, message, duration);

                if (view != null) {
                    toast.setView(view);
                }

                toast.show();
            }
        });
    }
}
