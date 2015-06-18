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

package com.dabay6.libraries.androidshared.helper;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.dabay6.libraries.androidshared.util.HandlerUtils;

/**
 * A fluent wrapper around the {@link android.widget.Toast} widget.
 *
 * @author Remel Pugh
 */
@SuppressWarnings("unused")
public class ToastHelper {
    private final static Object lock = new Object();
    private static int DEFAULT_DURATION = Toast.LENGTH_SHORT;
    private static ToastHelper singleton = null;
    private final Context context;

    ToastHelper(final Context context) {
        this.context = context;
    }

    /**
     * Make a standard toast that just contains a text view with the text from a resource.
     *
     * @param messageResourceId The resource containing the message to be displayed
     */
    public void show(final int messageResourceId) {
        show(messageResourceId, DEFAULT_DURATION);
    }

    /**
     * Make a standard toast that just contains a text view with the text from a resource.
     *
     * @param messageResourceId The resource containing the message to be displayed
     * @param duration          How long to display the message. Either {@link Toast#LENGTH_SHORT} or {@link
     *                          Toast#LENGTH_LONG}
     */
    public void show(final int messageResourceId, final int duration) {
        showToast(context.getString(messageResourceId), null, duration);
    }

    /**
     * Make a standard toast that just contains a text view
     *
     * @param message The message to be displayed
     */
    public void show(final String message) {
        show(message, DEFAULT_DURATION);
    }

    /**
     * Make a standard toast that just contains a text view
     *
     * @param message  The message to be displayed
     * @param duration How long to display the message. Either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     */
    public void show(final String message, final int duration) {
        showToast(message, null, duration);
    }

    /**
     * Make a standard toast that displays a {@link android.view.View}.
     *
     * @param view The {@link android.view.View} to show
     */
    public void show(final View view) {
        show(view, DEFAULT_DURATION);
    }

    /**
     * Make a standard toast that displays a {@link View}.
     *
     * @param view     The {@link View} to show
     * @param duration How long to display the message. Either {@link Toast#LENGTH_SHORT} or {@link Toast#LENGTH_LONG}
     */
    public void show(final View view, final int duration) {
        showToast(null, view, duration);
    }

    /**
     * @param context the {@link Context} used to create the {@link android.widget.Toast} widget.
     *
     * @return {@link ToastHelper}
     */
    public static ToastHelper with(final Context context) {
        if (singleton == null) {
            synchronized (lock) {
                if (singleton == null) {
                    singleton = new ToastHelperBuilder(context).build();
                }
            }
        }

        return singleton;
    }

    private void showToast(final String message, final View view, final int duration) {
        if (TextUtils.isEmpty(message) && view == null) {
            return;
        }

        HandlerUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (context != null) {
                    final Toast toast = Toast.makeText(context, message, duration);

                    if (view != null) {
                        toast.setView(view);
                    }

                    toast.show();
                }
            }
        });

        //        final Context context = activity.getApplicationContext();
        //
        //        activity.runOnUiThread(new Runnable() {
        //            @Override
        //            public void run() {
        //                if (context != null) {
        //                    final Toast toast = Toast.makeText(context, message, duration);
        //
        //                    if (view != null) {
        //                        toast.setView(view);
        //                    }
        //
        //                    toast.show();
        //                }
        //            }
        //        });
    }

    private static class ToastHelperBuilder {
        private final Context context;

        private ToastHelperBuilder(final Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }

            this.context = context;
        }

        public ToastHelper build() {
            return new ToastHelper(context);
        }
    }
}
