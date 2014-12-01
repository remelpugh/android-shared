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

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.dabay6.libraries.androidshared.logging.Logger;

/**
 * DataUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class DataUtils {
    @SuppressWarnings("unused")
    private final static String TAG = Logger.makeTag(DataUtils.class);

    /**
     * Hidden constructor
     */
    private DataUtils() {
    }

    /**
     * Closes the passed in {@link Cursor}.
     *
     * @param cursor The {@link Cursor} to be closed.
     */
    public static void close(final Cursor cursor) {
        if ((cursor != null) && (!cursor.isClosed())) {
            cursor.close();
        }
    }

    /**
     * Execute all of the SQL statements in the String[] array.
     *
     * @param db  the database on which to execute the statements
     * @param sql an array of SQL statements to execute
     */
    public static void execMultipleSQL(final SQLiteDatabase db, final String sql) {
        final String[] statements;

        statements = sql.replaceAll("\n", "").replaceAll("\t", "").split(";");

        execMultipleSQL(db, statements);
    }

    /**
     * Execute all of the SQL statements in the String[] array.
     *
     * @param db  the database on which to execute the statements
     * @param sql an array of SQL statements to execute
     */
    public static void execMultipleSQL(final SQLiteDatabase db, final String[] sql) {
        for (final String s : sql) {
            if (!TextUtils.isEmpty(s.trim())) {
                try {
                    Logger.debug(TAG, s.replace('|', ';'));
                    db.execSQL(s.replace('|', ';'));
                }
                catch (final Exception ex) {
                    Logger.error(TAG, ex.getMessage(), ex);
                }
            }
        }
    }

    /**
     * Determines if the passed in {@link Cursor} contains any data.
     *
     * @param cursor The {@link Cursor} that will be checked.
     *
     * @return A {@link Boolean} indicating whether the passed in {@link Cursor} has data.
     */
    public static boolean hasData(final Cursor cursor) {
        return DataUtils.hasData(cursor, true);
    }

    /**
     * Determines if the passed in {@link Cursor} contains any data.
     *
     * @param cursor    The {@link Cursor} that will be checked.
     * @param moveFirst Indicates if the {@link Cursor} has data, move to the first record.
     *
     * @return A {@link Boolean} indicating whether the passed in {@link Cursor} has data.
     */
    public static boolean hasData(final Cursor cursor, final boolean moveFirst) {
        boolean success;

        try {
            success = (cursor != null && cursor.getCount() > 0);
            if (success && moveFirst) {
                success = cursor.moveToFirst();
            }
        }
        catch (Exception ex) {
            success = false;
        }

        return success;
    }
}