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

package com.dabay6.libraries.androidshared.app;

import android.app.Application;
import com.dabay6.libraries.androidshared.BuildConfig;
import com.dabay6.libraries.androidshared.enums.LogLevels;
import com.dabay6.libraries.androidshared.helper.strictmode.Policy;
import com.dabay6.libraries.androidshared.helper.strictmode.StrictModeHelper;
import com.dabay6.libraries.androidshared.logging.Logger;

/**
 * ApplicationExtension
 * <p>
 * Provides base functionality for an android application.
 * </p>
 *
 * @author Remel Pugh
 * @version 1.2
 */
@SuppressWarnings("unused")
public abstract class ApplicationExtension extends Application {
    /**
     * The application log level.
     *
     * @return the application log level.
     */
    public abstract LogLevels getLogLevel();

    /**
     * The application tag prefix.
     *
     * @return the application tag prefix.
     */
    public abstract String getTagPrefix();

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        final StrictModeHelper helper = new StrictModeHelper(this);

        super.onCreate();

        if (BuildConfig.DEBUG) {
            Logger.setIsLoggingEnabled(true);
        }

        Logger.setLogLevel(getLogLevel());
        Logger.setTagPrefix(getTagPrefix());

        helper.setThreadPolicy(Policy.Thread.DetectAll, Policy.Thread.PenaltyLog)
              .setVmPolicy(Policy.Vm.DetectAll, Policy.Vm.PenaltyLog)
              .generate();
    }
}
