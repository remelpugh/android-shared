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
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class AppUtils {
    /**
     * Hide constructor
     */
    private AppUtils() {
    }

    /**
     * Retrieves the application version as defined in the manifest.
     *
     * @param context {@link Context} used to retrieve the {@link PackageManager}.
     *
     * @return application version
     */
    public static String getApplicationVersion(final Context context) {
        String version;

        try {
            final PackageInfo info;
            final PackageManager manager = context.getPackageManager();

            info = manager.getPackageInfo(context.getPackageName(), 0);

            version = info.versionName;
        }
        catch (final NameNotFoundException ex) {
            version = "1.0";
        }

        return version;
    }

    /**
     * Determines if the application has been compiled with debugging enabled.
     *
     * @param context {@link Context} used to access the {@link ApplicationInfo}.
     *
     * @return true if debugging is enabled, otherwise false.
     */
    public static boolean isDebugEnabled(final Context context) {
        return (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
    }
}