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

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.Display;

import com.dabay6.libraries.androidshared.util.FileUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * AppHelper
 *
 * @author Remel Pugh
 */
@SuppressWarnings("unused")
public class AppHelper {
    private final static Object lock = new Object();
    private static AppHelper singleton = null;
    private final Context context;

    AppHelper(final Context context) {
        this.context = context;
    }

    /**
     * Determines if the application has been compiled with debugging enabled.
     *
     * @return true if debugging is enabled, otherwise false.
     */
    public boolean isDebuggable() {
        return context.getApplicationInfo() == null ||
               (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
    }

    /**
     * Open an asset using streaming mode. This provides access to files that have been bundled with an application as
     * assets -- that is, files placed in to the "assets" directory.
     *
     * @param fileName The name of the asset to open. This name can be hierarchical.
     *
     * @return An {@link java.io.InputStream} containing the contents of the asset.
     */
    public InputStream openAsset(final String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }

    /**
     * Return the name of this application's package.
     *
     * @return A string containing the application's package name.
     */
    public String packageName() {
        return context.getPackageName();
    }

    /**
     * Open an asset using streaming mode and reads the contents into a string.
     *
     * @param fileName The name of the asset to open. This name can be hierarchical.
     *
     * @return A {@link String} containing the contents of the asset.
     */
    public String readAsset(final String fileName) throws IOException {
        return FileUtils.readTextFile(openAsset(fileName));
    }

    /**
     * @param activity
     *
     * @return
     */
    public PointF screenDimensions(Activity activity) {
        final Display display = activity.getWindowManager().getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        final float density;

        display.getMetrics(metrics);

        density = context.getResources().getDisplayMetrics().density;

        return new PointF(metrics.widthPixels / density, metrics.heightPixels / density);
    }

    /**
     * Retrieves the application version as defined in the manifest.
     *
     * @return application version
     */
    public String version() {
        String version = "1.0";

        try {
            final PackageManager manager = context.getPackageManager();

            if (manager != null) {
                final PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

                if (info != null) {
                    version = info.versionName;
                }
            }
        }
        catch (final NameNotFoundException ex) {
            version = "1.0";
        }

        return version;
    }

    /**
     * @param context the {@link Context} used to create the {@link android.widget.Toast} widget.
     *
     * @return {@link AppHelper}
     */
    public static AppHelper with(final Context context) {
        if (singleton == null) {
            synchronized (lock) {
                if (singleton == null) {
                    singleton = new AppHelperBuilder(context).build();
                }
            }
        }

        return singleton;
    }

    private static class AppHelperBuilder {
        private final Context context;

        private AppHelperBuilder(final Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }

            this.context = context;
        }

        public AppHelper build() {
            return new AppHelper(context);
        }
    }
}
