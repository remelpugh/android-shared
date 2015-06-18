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

package com.dabay6.libraries.androidshared.logging;

import android.text.TextUtils;
import android.util.Log;

import com.dabay6.libraries.androidshared.enums.LogLevels;

/**
 * Logger
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Logger {
    private static final int MAX_TAG_LENGTH = 25;
    private static boolean IS_LOGGING_ENABLED = true;
    private static LogLevels LOG_LEVEL = LogLevels.VERBOSE;
    private static String TAG_PREFIX = "";
    private static int TAG_PREFIX_LENGTH = TAG_PREFIX.length();

    /**
     * Hidden constructor.
     */
    private Logger() {
    }

    /**
     * Send a {@link Log#DEBUG} log message.
     *
     * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where
     *                the
     *                log call occurs.
     * @param message The message to be logged
     */
    public static void debug(final String tag, final String message) {
        if (Logger.isDebugLogsEnabled()) {
            Log.d(tag, message);
        }
    }

    /**
     * Send a {@link Log#DEBUG} log message.
     *
     * @param tag    Used to identify the source of a log message. It usually identifies the class or activity where
     *               the
     *               log call occurs.
     * @param format the format string {@link String#format(String, Object...)}
     * @param args   the list of arguments passed to the formatter. If there are more arguments than required by
     *               format,
     *               additional arguments are ignored.
     */
    public static void debug(final String tag, final String format, final Object... args) {
        Logger.debug(tag, Logger.formatMessage(format, args));
    }

    public static void error(final String tag, final String message) {
        error(tag, message, null);
    }

    /**
     * Send a {@link Log#ERROR} log message.
     *
     * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where
     *                the
     *                log call occurs.
     * @param message The message to be logged
     */
    public static void error(final String tag, final String message, final Throwable throwable) {
        if (Logger.isErrorLogsEnabled()) {
            Log.e(tag, message, throwable);
        }
    }

    /**
     * Send a {@link Log#ERROR} log message.
     *
     * @param tag    Used to identify the source of a log message. It usually identifies the class or activity where
     *               the
     *               log call occurs.
     * @param format the format string {@link String#format(String, Object...)}
     * @param args   the list of arguments passed to the formatter. If there are more arguments than required by
     *               format,
     *               additional arguments are ignored.
     */
    public static void error(final String tag, final String format, final Throwable throwable, final Object... args) {
        Logger.error(tag, Logger.formatMessage(format, args), throwable);
    }

    /**
     * Send a {@link Log#INFO} log message.
     *
     * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where
     *                the
     *                log call occurs.
     * @param message The message to be logged
     */
    public static void info(final String tag, final String message) {
        if (Logger.isInfoLogsEnabled()) {
            Log.i(tag, message);
        }
    }

    /**
     * Send a {@link Log#INFO} log message.
     *
     * @param tag    Used to identify the source of a log message. It usually identifies the class or activity where
     *               the
     *               log call occurs.
     * @param format the format string {@link String#format(String, Object...)}
     * @param args   the list of arguments passed to the formatter. If there are more arguments than required by
     *               format,
     *               additional arguments are ignored.
     */
    public static void info(final String tag, final String format, final Object... args) {
        Logger.info(tag, Logger.formatMessage(format, args));
    }

    /**
     * Generates an identifying source for the log message.
     *
     * @param type The log tag for the current log message using {@link Class#getSimpleName()}.
     *
     * @return A tag with the defined prefix added.
     */
    public static String makeTag(final Class<?> type) {
        return makeTag(type.getSimpleName());
    }

    /**
     * Generates an identifying source for the log message.
     *
     * @param tag The log tag for the current log message.
     *
     * @return A tag with the defined prefix added.
     */
    public static String makeTag(final String tag) {
        if (tag.length() > MAX_TAG_LENGTH - TAG_PREFIX_LENGTH) {
            return TAG_PREFIX + tag.substring(0, MAX_TAG_LENGTH - TAG_PREFIX_LENGTH - 1);
        }

        return TAG_PREFIX + tag;
    }

    /**
     * Set whether logging is enabled.
     *
     * @param enabled Is logging enabled.
     */
    public static void setIsLoggingEnabled(final boolean enabled) {
        IS_LOGGING_ENABLED = enabled;
    }

    /**
     * Set the current logging level.
     *
     * @param level The log level.
     */
    public static void setLogLevel(final LogLevels level) {
        LOG_LEVEL = level;
    }

    /**
     * Set the current tag prefix.
     *
     * @param prefix The tag prefix.
     */
    public static void setTagPrefix(final String prefix) {
        if (!TextUtils.isEmpty(prefix)) {
            TAG_PREFIX = prefix;
            TAG_PREFIX_LENGTH = TAG_PREFIX.length();
        }
    }

    /**
     * Send a {@link Log#ERROR} log message containing the current stack trace.
     *
     * @param tag        Used to identify the source of a log message. It usually identifies the class or activity
     *                   where
     *                   the
     *                   log call occurs.
     * @param stackTrace an array containing {@link StackTraceElement}.
     */
    public static void stackTrace(final String tag, final StackTraceElement[] stackTrace) {
        final StringBuilder message = new StringBuilder();

        for (final StackTraceElement element : stackTrace) {
            message.append(element.toString());
            message.append(System.getProperty("line.separator"));
        }

        Logger.error(tag, message.toString(), null);
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where
     *                the
     *                log call occurs.
     * @param message The message to be logged
     */
    public static void verbose(final String tag, final String message) {
        Log.v(tag, message);
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param tag    Used to identify the source of a log message. It usually identifies the class or activity where
     *               the
     *               log call occurs.
     * @param format the format string {@link String#format(String, Object...)}
     * @param args   the list of arguments passed to the formatter. If there are more arguments than required by
     *               format,
     *               additional arguments are ignored.
     */
    public static void verbose(final String tag, final String format, final Object... args) {
        Logger.verbose(tag, Logger.formatMessage(format, args));
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param tag     Used to identify the source of a log message. It usually identifies the class or activity where
     *                the
     *                log call occurs.
     * @param message The message to be logged
     */
    public static void warn(final String tag, final String message) {
        if (Logger.isWarningLogsEnabled()) {
            Log.w(tag, message);
        }
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param tag    Used to identify the source of a log message. It usually identifies the class or activity where
     *               the
     *               log call occurs.
     * @param format the format string {@link String#format(String, Object...)}
     * @param args   the list of arguments passed to the formatter. If there are more arguments than required by
     *               format,
     *               additional arguments are ignored.
     */
    public static void warn(final String tag, final String format, final Object... args) {
        Logger.warn(tag, Logger.formatMessage(format, args));
    }

    /**
     * Returns a localized formatted string.
     *
     * @param format the format string {@link String#format(String, Object...)}
     * @param args   the list of arguments passed to the formatter. If there are more arguments than required by
     *               format,
     *               additional arguments are ignored.
     *
     * @return the formatted string.
     */
    private static String formatMessage(final String format, final Object... args) {
        final String empty = "";

        try {
            return String.format(format == null ? empty : format, args);
        }
        catch (final RuntimeException ex) {
            final String tag = Logger.class.getSimpleName();

            Logger.warn(tag, "format error. reason=%s, format=%s", ex.getMessage(), format);

            return empty;
        }
    }

    /**
     * Determine if debug logging is enabled.
     *
     * @return true if debug logging is enabled, otherwise false
     */
    private static boolean isDebugLogsEnabled() {
        return !IS_LOGGING_ENABLED && Logger.LOG_LEVEL.index() <= LogLevels.DEBUG.index();
    }

    /**
     * Determine if error logging is enabled.
     *
     * @return true if error logging is enabled, otherwise false
     */
    private static boolean isErrorLogsEnabled() {
        return !IS_LOGGING_ENABLED && Logger.LOG_LEVEL.index() <= LogLevels.ERROR.index();
    }

    /**
     * Determine if info logging is enabled.
     *
     * @return true if info logging is enabled, otherwise false
     */
    private static boolean isInfoLogsEnabled() {
        return !IS_LOGGING_ENABLED && Logger.LOG_LEVEL.index() <= LogLevels.INFO.index();
    }

    /**
     * Determine if verbose logging is enabled.
     *
     * @return true if verbose logging is enabled, otherwise false.
     */
    private static boolean isVerboseLogsEnabled() {
        return !IS_LOGGING_ENABLED && Logger.LOG_LEVEL == LogLevels.VERBOSE;
    }

    /**
     * Determine if warning logging is enabled.
     *
     * @return true if warning logging is enabled, otherwise false
     */
    private static boolean isWarningLogsEnabled() {
        return !IS_LOGGING_ENABLED && Logger.LOG_LEVEL.index() <= LogLevels.WARNING.index();
    }
}
