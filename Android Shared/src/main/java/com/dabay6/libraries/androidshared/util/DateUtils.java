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
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * DateUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class DateUtils {
    /**
     * Hidden constructor.
     */
    private DateUtils() {
    }

    /**
     * Gets the current system time in the number of milliseconds since 1/1/1970.
     *
     * @return The number of seconds since 1/1/1970.
     */
    public synchronized static long getCurrentDateTime() {
        return System.currentTimeMillis();
    }

    /**
     * Gets the current system time in the number of seconds since 1/1/1970.
     *
     * @return The number of seconds since 1/1/1970.
     */
    public synchronized static long getCurrentDateTimeInSeconds() {
        long value = System.currentTimeMillis();

        value = (value / 1000L);

        return value;
    }

    /**
     * Get the user locale formatted date.
     *
     * @param context      The {@link android.content.Context} used to retrieve {@link java.text.DateFormat}.
     * @param milliseconds The milliseconds to be formatted.
     *
     * @return A user locale formatted date.
     */
    public synchronized static String getUserLocaleFormattedDate(final Context context, final Long milliseconds) {
        return getUserLocaleFormattedDate(context, milliseconds, DateFormats.Default);
    }

    /**
     * Get the user locale formatted date.
     *
     * @param context      The {@link android.content.Context} used to retrieve {@link java.text.DateFormat}.
     * @param milliseconds The milliseconds to be formatted.
     * @param format       The format to be used for the date.
     *
     * @return A user locale formatted date.
     */
    public synchronized static String getUserLocaleFormattedDate(final Context context, final Long milliseconds,
                                                                 DateFormats format) {
        final Calendar calendar = getCalendar(milliseconds);
        final java.text.DateFormat dateFormat;

        switch (format) {
            case Medium: {
                dateFormat = DateFormat.getMediumDateFormat(context);
                break;
            }
            case Long: {
                dateFormat = DateFormat.getLongDateFormat(context);
                break;
            }
            default: {
                dateFormat = DateFormat.getDateFormat(context);
                break;
            }
        }

        return dateFormat.format(calendar.getTime());
    }

    /**
     * Get the user locale formatted time.
     *
     * @param context      The {@link android.content.Context} used to retrieve {@link java.text.DateFormat}.
     * @param milliseconds The milliseconds to be formatted.
     *
     * @return A user locale formatted time.
     */
    public synchronized static String getUserLocaleFormattedTime(final Context context, final Long milliseconds) {

        final Calendar calendar = getCalendar(milliseconds);
        final java.text.DateFormat timeFormat = DateFormat.getTimeFormat(context);

        return timeFormat.format(calendar.getTime());
    }

    /**
     * Retrieve the {@link Calendar} for the given milliseconds.
     *
     * @param milliseconds The number milliseconds used to initialize the {@link Calendar}.
     *
     * @return A {@link Calendar}.
     */
    private synchronized static Calendar getCalendar(final Long milliseconds) {
        final Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(milliseconds);

        return calendar;
    }

    /**
     *
     */
    public enum DateFormats {
        /**
         *
         */
        Default,
        /**
         *
         */
        Medium,
        /**
         *
         */
        Long
    }
}