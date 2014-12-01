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

import java.io.IOException;
import java.io.InputStream;

/**
 * AssetUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class AssetUtils {
    /**
     * Hidden constructor
     */
    private AssetUtils() {
    }

    /**
     * Open an asset using streaming mode. This provides access to files that have been bundled with an
     * application as assets -- that is, files placed in to the "assets" directory.
     *
     * @param context  {@link Context} used to access the {@link android.content.res.AssetManager}.
     * @param fileName The name of the asset to open. This name can be hierarchical.
     *
     * @return An {@link InputStream} containing the contents of the asset.
     */
    public static InputStream open(final Context context, final String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }

    /**
     * Open an asset using streaming mode and reads the contents into a string.
     *
     * @param context  {@link Context} used to access the {@link android.content.res.AssetManager}.
     * @param fileName The name of the asset to open. This name can be hierarchical.
     *
     * @return A {@link String} containing the contents of the asset.
     */
    public static String read(final Context context, final String fileName) throws IOException {
        return FileUtils.readTextFile(AssetUtils.open(context, fileName));
    }
}