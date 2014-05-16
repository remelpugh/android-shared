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
import android.os.Environment;

import java.io.File;

/**
 * ExternalStorageUtils
 * <p>
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class ExternalStorageUtils {
    /**
     * Hidden constructor.
     */
    private ExternalStorageUtils() {
    }

    /**
     * Determine if an external storage location is available.
     *
     * @return True if external storage is available, otherwise false.
     */
    public static boolean isExternalStorageAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * @return Returns the path of the directory holding application files on external storage.
     */
    public static File openMusicDirectory(final Context context) {
        if (isExternalStorageAvailable()) {
            return (AndroidUtils.isAtLeastFroyo()) ? context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
                                                   : openDirectory("music");
        }

        return null;
    }

    /**
     * @return Returns the path of the directory holding application files on external storage.
     */
    public static File openPublicPicturesDirectory() {
        if (isExternalStorageAvailable()) {
            return (AndroidUtils.isAtLeastFroyo()) ? Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES) : openDirectory("Pictures");
        }
        return null;
    }

    /**
     * @param path
     *
     * @return
     */
    private static File openDirectory(final String path) {
        File file = null;

        if (isExternalStorageAvailable()) {
            final File storageDirectory = Environment.getExternalStorageDirectory();

            file = new File(storageDirectory, path);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    file = null;
                }
            }
        }

        return file;
    }
}