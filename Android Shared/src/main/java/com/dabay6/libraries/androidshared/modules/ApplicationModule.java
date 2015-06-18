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

package com.dabay6.libraries.androidshared.modules;

import android.content.Context;
import android.location.LocationManager;

import com.dabay6.libraries.androidshared.annotations.ForApplication;
import com.dabay6.libraries.androidshared.app.ApplicationExtension;
import com.dabay6.libraries.androidshared.helper.SystemServiceHelper;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A module for Android-specific dependencies which require a {@link Context} or {@link android.app.Application} to
 * create.
 *
 * @author Remel Pugh
 */
@Module(
        complete = false,
        library = true)
@SuppressWarnings("unused")
public class ApplicationModule {
    private final Context context;

    /**
     * @param context
     */
    public ApplicationModule(ApplicationExtension context) {
        this.context = context;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with {@link
     * com.dabay6.libraries.androidshared.annotations.ForApplication
     */
    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return context;
    }

    /**
     * @return The event {@link com.squareup.otto.Bus}.
     */
    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    /**
     * @return
     */
    @Provides
    @Singleton
    LocationManager provideLocationManager() {
        return SystemServiceHelper.with(context).location();
    }
}
