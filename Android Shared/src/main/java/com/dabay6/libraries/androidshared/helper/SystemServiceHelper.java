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

import android.accounts.AccountManager;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;

/**
 * A fluent wrapper around android system services.
 *
 * @author Remel Pugh
 */
@SuppressWarnings("unused")
public class SystemServiceHelper {
    private final static Object lock = new Object();
    private static SystemServiceHelper singleton = null;
    private final Context context;

    SystemServiceHelper(final Context context) {
        this.context = context;
    }

    /**
     * Retrieve the account manager service.
     *
     * @return A {@link android.accounts.AccountManager}
     */
    public AccountManager account() {
        return (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
    }

    /**
     * Retrieve the connectivity service.
     *
     * @return A {@link android.net.ConnectivityManager}
     */
    public ConnectivityManager connectivity() {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * Retrieve the layout inflator service
     *
     * @return A {@link android.view.LayoutInflater}
     */
    public LayoutInflater layout() {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Retrieve the location service.
     *
     * @return A {@link android.location.LocationManager}
     */
    public LocationManager location() {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Retrieve the wifi service.
     *
     * @return A {@link android.net.wifi.WifiManager}
     */
    public WifiManager wifi() {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * @param context the {@link Context} used to create the {@link android.widget.Toast} widget.
     *
     * @return {@link ToastHelper}
     */
    public static SystemServiceHelper with(final Context context) {
        if (singleton == null) {
            synchronized (lock) {
                if (singleton == null) {
                    singleton = new SystemServiceHelperBuilder(context).build();
                }
            }
        }

        return singleton;
    }

    private static class SystemServiceHelperBuilder {
        private final Context context;

        private SystemServiceHelperBuilder(final Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }

            this.context = context;
        }

        public SystemServiceHelper build() {
            return new SystemServiceHelper(context);
        }
    }
}
