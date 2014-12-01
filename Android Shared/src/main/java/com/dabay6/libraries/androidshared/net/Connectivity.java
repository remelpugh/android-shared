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

package com.dabay6.libraries.androidshared.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Connectivity
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class Connectivity {
    /**
     * Hidden default constructor
     */
    private Connectivity() {
    }

    /**
     * Determines if an active network connection is available.
     *
     * @param context {@link Context} used to create the {@link ConnectivityManager}.
     * @return true if connected, otherwise false.
     */
    public static boolean isConnected(final Context context) {
        return Connectivity.checkConnection(context);
    }

    /**
     * Determines if an active mobile network connection is available.
     *
     * @param context {@link Context} used to create the {@link ConnectivityManager}.
     * @return true if connected, otherwise false.
     */
    public static boolean isConnectedToMobile(final Context context) {
        return Connectivity.checkConnection(context, ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Determines if an active WiFi connection is available.
     *
     * @param context {@link Context} used to create the {@link ConnectivityManager}.
     * @return true if connected to WiFi, otherwise false.
     */
    public static boolean isConnectedToWifi(final Context context) {
        return Connectivity.checkConnection(context, ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Determines if an active network connection is available.
     *
     * @param context {@link Context} used to create the {@link ConnectivityManager}.
     * @return true if connected, otherwise false.
     */
    private static boolean checkConnection(final Context context) {
        return Connectivity.checkConnection(context, null);
    }

    /**
     * Determines if an active network connection is available.
     *
     * @param context        {@link Context} used to create the {@link ConnectivityManager}.
     * @param connectionType The connection type to check.
     * @return true if connected, otherwise false.
     */
    private static boolean checkConnection(final Context context, final Integer connectionType) {
        final ConnectivityManager manager;
        final NetworkInfo info;

        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectionType == null) {
            info = manager.getActiveNetworkInfo();

            return info != null && info.isAvailable() && info.isConnected();

        } else {
            info = manager.getNetworkInfo(connectionType);

            return info != null && info.isAvailable() && info.isConnected();

        }
    }
}