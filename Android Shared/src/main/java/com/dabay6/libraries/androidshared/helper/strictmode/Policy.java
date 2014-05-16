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

package com.dabay6.libraries.androidshared.helper.strictmode;

/**
 * Policy
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class Policy {
    /**
     *
     */
    public enum Thread {
        /**
         * Detect everything that's potentially suspect.
         */
        DetectAll,
        /**
         * Enable detection of slow calls.
         */
        DetectCustomSlowCalls,
        /**
         * Enable detection of disk reads.
         */
        DetectDiskReads,
        /**
         * Enable detection of disk writes.
         */
        DetectDiskWrites,
        /**
         * Enable detection of network operations.
         */
        DetectNetwork,
        /**
         * Crash the whole process on violation.
         */
        PenaltyDeath,
        /**
         * Crash the whole process on any network usage.
         */
        PenaltyDeathOnNetwork,
        /**
         * Show an annoying dialog to the developer on detected violations.
         */
        PenaltyDialog,
        /**
         * Enable detected violations log a stacktrace and timing data to the {@link android.os.DropBoxManager} on
         * policy
         * violation.
         */
        PenaltyDropBox,
        /**
         * Flash the screen during a violation.
         */
        PenaltyFlashScreen,
        /**
         * Log detected violations to the system log.
         */
        PenaltyLog,
        /**
         * Detect everything that's potentially suspect.
         */
        PermitAll,
        /**
         * Enable detection of slow calls.
         */
        PermitCustomSlowCalls,
        /**
         * Enable detection of disk reads.
         */
        PermitDiskReads,
        /**
         * Enable detection of disk writes.
         */
        PermitDiskWrites,
        /**
         * Enable detection of network operations.
         */
        PermitNetwork,
    }

    /**
     *
     */
    public enum Vm {
        /**
         * Detect leaks of {@link android.app.Activity} subclasses.
         */
        DetectActivityLeaks,
        /**
         * Detect everything that's potentially suspect.
         */
        DetectAll,
        /**
         * Detect when an {@link java.io.Closeable} or other object with a explicit termination method is finalize
         * without
         * having been closed.
         */
        DetectLeakedClosableObjects,
        /**
         * Detect when a {@link android.content.BroadcastReceiver} or {@link android.content.ServiceConnection} is
         * leaked
         * during {@link android.content.Context} teardown.
         */
        DetectLeakedRegistrationObjects,
        /**
         * Detect when an {@link android.database.sqlite.SQLiteCursor} or other SQLite object is finalized without
         * having
         * been closed.
         */
        DetectLeakedSqlLiteObjects,
        /**
         * Crash the whole process on violation.
         */
        PenaltyDeath,
        /**
         * Enable detected violations log a stacktrace and timing data to the {@link android.os.DropBoxManager} on
         * policy
         * violation.
         */
        PenaltyDropBox,
        /**
         * Log detected violations to the system log.
         */
        PenaltyLog
    }
}