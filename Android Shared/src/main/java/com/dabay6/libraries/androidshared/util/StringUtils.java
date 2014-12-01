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

import android.text.TextUtils;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * StringUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class StringUtils {
    private static final Pattern PATTERN = Pattern.compile("([a-z])([A-Z])");

    /**
     * Hidden constructor.
     */
    private StringUtils() {
    }

    /**
     * @return An empty string.
     */
    @SuppressWarnings("SameReturnValue")
    public static String empty() {
        return "";
    }

    /**
     * Formats the passed in string with the supplied parameters.
     *
     * @param format The string containing the format.
     * @param params The list of arguments passed to the formatter.
     * @return The formatted string.
     */
    public static String format(final String format, final Object... params) {
        return StringUtils.format(format, Locale.getDefault(), params);
    }

    /**
     * Formats the passed in string with the supplied parameters.
     *
     * @param format The string containing the format.
     * @param locale The locale to apply.
     * @param params The list of arguments passed to the formatter.
     * @return The formatted string.
     */
    public static String format(final String format, final Locale locale, final Object... params) {
        return String.format(locale, format, params);
    }

    /**
     * @return the joined {@link String}, null otherwise
     */
    public static <T> String join(final List<T> list, final String separator) {
        if (list != null && list.size() > 0) {
            final StringBuilder builder = new StringBuilder();

            for (final T item : list) {
                if (builder.length() > 0) {
                    builder.append(separator);
                }
                builder.append(item.toString());
            }

            return builder.toString();
        }

        return null;
    }

    /**
     * @return the joined {@link String}, null otherwise
     */
    public static <T> String join(final List<T> list) {
        return StringUtils.join(list, ',');
    }

    /**
     * @return the joined {@link String}, null otherwise
     */
    public static <T> String join(final List<T> list, final char separator) {
        return StringUtils.join(list, String.valueOf(separator));
    }

    /**
     * Splits the passed in string into words.
     *
     * @param s The string to be split.
     * @return The split string.
     */
    public static String splitIntoWords(final String s) {
        return PATTERN.matcher(s).replaceAll("$1 $2");
    }

    /**
     * Converts the passed in string to lower case, using the specified locale
     *
     * @param s The string to be changed to lower case.
     * @return The lower case string.
     */
    public static String toLowerCase(final String s) {
        return StringUtils.toLowerCase(s, Locale.getDefault());
    }

    /**
     * Converts the passed in string to lower case, using the specified locale
     *
     * @param s      The string to be changed to lower case.
     * @param locale The locale to apply.
     * @return The lower case string.
     */
    public static String toLowerCase(final String s, Locale locale) {
        return s.toLowerCase(locale);
    }

    /**
     * Converts the passed in string to upper case, using the specified locale
     *
     * @param s The string to be changed to upper case.
     * @return The lower case string.
     */
    public static String toUpperCase(final CharSequence s) {
        return StringUtils.toUpperCase(s.toString());
    }

    /**
     * Converts the passed in string to upper case, using the specified locale
     *
     * @param s The string to be changed to upper case.
     * @return The lower case string.
     */
    public static String toUpperCase(final String s) {
        return StringUtils.toUpperCase(s, Locale.getDefault());
    }

    /**
     * Converts the passed in string to upper case, using the specified locale
     *
     * @param s      The string to be changed to upper case.
     * @param locale The locale to apply.
     * @return The lower case string.
     */
    public static String toUpperCase(final CharSequence s, Locale locale) {
        return StringUtils.toUpperCase(s.toString(), locale);
    }

    /**
     * Converts the passed in string to upper case, using the specified locale
     *
     * @param s      The string to be changed to upper case.
     * @param locale The locale to apply.
     * @return The lower case string.
     */
    public static String toUpperCase(final String s, Locale locale) {
        if (TextUtils.isEmpty(s)) {
            return StringUtils.empty();
        }

        return s.toUpperCase(locale);
    }
}