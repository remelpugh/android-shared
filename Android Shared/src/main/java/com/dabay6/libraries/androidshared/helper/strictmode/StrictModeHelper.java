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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode.VmPolicy;
import com.dabay6.libraries.androidshared.util.AndroidUtils;
import com.dabay6.libraries.androidshared.util.AppUtils;

/**
 * StrictModeHelper
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
@TargetApi(VERSION_CODES.GINGERBREAD)
public final class StrictModeHelper {
    private final Context context;
    private ThreadPolicy.Builder threadBuilder;
    private VmPolicy.Builder vmBuilder;

    /**
     *
     */
    public StrictModeHelper(final Context context) {
        this.context = context;

        reset();
    }

    /**
     *
     */
    public void generate() {
        if (!AppUtils.isDebugEnabled(context)) {
            return;
        }

        StrictMode.setThreadPolicy(threadBuilder.build());
        StrictMode.setVmPolicy(vmBuilder.build());
    }

    /**
     * @param policy
     * @param runnable
     *
     * @return
     */
    @TargetApi(VERSION_CODES.HONEYCOMB)
    public void permit(final Policy.Thread policy, final Runnable runnable) {
        final ThreadPolicy oldPolicy = StrictMode.getThreadPolicy();
        final ThreadPolicy.Builder builder = new ThreadPolicy.Builder(oldPolicy);

        switch (policy) {
            case PermitAll: {
                builder.permitAll();
                break;
            }
            case PermitCustomSlowCalls: {
                if (AndroidUtils.isAtLeastHoneycomb()) {
                    builder.permitCustomSlowCalls();
                }
                break;
            }
            case PermitDiskReads: {
                builder.permitDiskReads();
                break;
            }
            case PermitDiskWrites: {
                builder.permitDiskWrites();
                break;
            }
            case PermitNetwork: {
                builder.permitNetwork();
                break;
            }
        }

        StrictMode.setThreadPolicy(builder.build());

        if (runnable != null) {
            runnable.run();
        }

        StrictMode.setThreadPolicy(oldPolicy);
    }

    /**
     * @return The current instance of {@link StrictModeHelper}.
     */
    public StrictModeHelper reset() {
        if (!AppUtils.isDebugEnabled(context)) {
            return this;
        }

        threadBuilder = new ThreadPolicy.Builder();
        vmBuilder = new VmPolicy.Builder();

        return this;
    }

    /**
     * @return The current instance of {@link StrictModeHelper}.
     */
    @TargetApi(VERSION_CODES.HONEYCOMB)
    public StrictModeHelper setThreadPolicy(final Policy.Thread... policies) {
        if (!AppUtils.isDebugEnabled(context)) {
            return this;
        }

        for (final Policy.Thread policy : policies) {
            switch (policy) {
                case DetectAll: {
                    threadBuilder = threadBuilder.detectAll();
                    break;
                }
                case DetectCustomSlowCalls: {
                    if (AndroidUtils.isAtLeastHoneycomb()) {
                        threadBuilder = threadBuilder.detectCustomSlowCalls();
                    }
                    break;
                }
                case DetectDiskReads: {
                    threadBuilder = threadBuilder.detectDiskReads();
                    break;
                }
                case DetectDiskWrites: {
                    threadBuilder = threadBuilder.detectDiskWrites();
                    break;
                }
                case DetectNetwork: {
                    threadBuilder = threadBuilder.detectNetwork();
                    break;
                }
                case PenaltyDeath: {
                    threadBuilder = threadBuilder.penaltyDeath();
                    break;
                }
                case PenaltyDeathOnNetwork: {
                    if (AndroidUtils.isAtLeastHoneycomb()) {
                        threadBuilder = threadBuilder.penaltyDeathOnNetwork();
                    }
                    break;
                }
                case PenaltyDialog: {
                    threadBuilder = threadBuilder.penaltyDialog();
                    break;
                }
                case PenaltyDropBox: {
                    threadBuilder = threadBuilder.penaltyDropBox();
                    break;
                }
                case PenaltyFlashScreen: {
                    if (AndroidUtils.isAtLeastHoneycomb()) {
                        threadBuilder = threadBuilder.penaltyFlashScreen();
                    }
                    break;
                }
                case PenaltyLog: {
                    threadBuilder = threadBuilder.penaltyLog();
                    break;
                }
            }
        }

        return this;
    }

    /**
     * @return The current instance of {@link StrictModeHelper}.
     */
    @TargetApi(VERSION_CODES.JELLY_BEAN)
    public StrictModeHelper setVmPolicy(final Policy.Vm... policies) {
        if (!AppUtils.isDebugEnabled(context)) {
            return this;
        }

        for (final Policy.Vm policy : policies) {
            switch (policy) {
                case DetectActivityLeaks: {
                    if (AndroidUtils.isAtLeastHoneycomb()) {
                        vmBuilder = vmBuilder.detectActivityLeaks();
                    }
                    break;
                }
                case DetectAll: {
                    vmBuilder = vmBuilder.detectAll();
                    break;
                }
                case DetectLeakedClosableObjects: {
                    if (AndroidUtils.isAtLeastHoneycomb()) {
                        vmBuilder = vmBuilder.detectLeakedClosableObjects();
                    }
                    break;
                }
                case DetectLeakedRegistrationObjects: {
                    if (AndroidUtils.isAtLeastJellyBean()) {
                        vmBuilder = vmBuilder.detectLeakedRegistrationObjects();
                    }
                    break;
                }
                case DetectLeakedSqlLiteObjects: {
                    vmBuilder = vmBuilder.detectLeakedSqlLiteObjects();
                    break;
                }
                case PenaltyDeath: {
                    vmBuilder = vmBuilder.penaltyDeath();
                    break;
                }
                case PenaltyDropBox: {
                    vmBuilder = vmBuilder.penaltyDropBox();
                    break;
                }
                case PenaltyLog: {
                    vmBuilder = vmBuilder.penaltyLog();
                    break;
                }
            }
        }

        return this;
    }
}