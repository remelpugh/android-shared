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

package com.dabay6.libraries.androidshared.enums;

import android.util.Log;
import android.util.SparseArray;

import java.util.EnumSet;

/**
 * LogLevels
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public enum LogLevels {
    /**
     * Log only {@link Log#DEBUG}, {@link Log#INFO}, {@link Log#WARN}, and {@link Log#ERROR} messages.
     */
    DEBUG(1),
    /**
     * Log only {@link Log#ERROR} messages.
     */
    ERROR(4),
    /**
     * Log only {@link Log#INFO}, {@link Log#WARN}, and {@link Log#ERROR} messages.
     */
    INFO(2),
    /**
     * Log only {@link Log#WARN} and {@link Log#ERROR} messages.
     */
    WARNING(3),
    /**
     * Log all messages.
     */
    VERBOSE(0);
    private static final SparseArray<LogLevels> lookup = new SparseArray<LogLevels>();
    private final int index;

    /**
     * Hidden constructor.
     *
     * @param index The index value to each enumeration.
     */
    private LogLevels(final int index) {
        this.index = index;
    }

    /**
     * Lookup the {@link LogLevels} enumeration value by ordinal value.
     *
     * @param index The ordinal value to lookup the enumeration value.
     *
     * @return The {@link LogLevels} value associated to the ordinal value, otherwise null.
     */
    public static LogLevels get(final int index) {
        return lookup.get(index);
    }

    /**
     * The ordinal value of the current enumeration instance.
     *
     * @return The ordinal of the enumeration.
     */
    public int index() {
        return index;
    }

    static {
        for (final LogLevels type : EnumSet.allOf(LogLevels.class)) {
            lookup.put(type.index(), type);
        }
    }
}
