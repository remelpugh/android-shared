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

import com.dabay6.libraries.androidshared.logging.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;

/**
 * FileUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class FileUtils {
    @SuppressWarnings("unused")
    private final static String TAG = Logger.makeTag(FileUtils.class);

    /**
     * Hidden constructor.
     */
    private FileUtils() {
    }

    /**
     * @param file The {@link File} to retrieve the extension of.
     *
     * @return The extension of the file.
     */
    public static String getFileExtension(final File file) {
        final String name = file.getName();
        final int index;
        String ext = "";

        index = name.lastIndexOf('.');
        if (index > 0 && index < file.length() - 1) {
            ext = StringUtils.toLowerCase(name.substring(index));
        }

        return ext;
    }

    /**
     * This method reads simple text file
     *
     * @param stream The {@link InputStream} to read.
     *
     * @return data from file
     */
    public static String readTextFile(final InputStream stream) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        final byte buf[] = new byte[1024];
        int len;

        try {
            while ((len = stream.read(buf)) != -1) {
                output.write(buf, 0, len);
            }

            output.close();
            stream.close();
        }
        catch (final IOException ex) {
            Logger.error(FileUtils.TAG, ex.getMessage(), ex);
        }

        return output.toString();
    }

    /**
     * @author Remel Pugh
     * @version 1.0
     */
    public static class DirectoryAlphaComparator implements Comparator<File> {
        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(final File fileOne, final File fileTwo) {
            if (fileOne.isDirectory() && !fileTwo.isDirectory()) {
                return -1;
            }
            else if (!fileOne.isDirectory() && fileTwo.isDirectory()) {
                return 1;
            }
            else {
                return fileOne.getName().compareToIgnoreCase(fileTwo.getName());
            }
        }
    }
}