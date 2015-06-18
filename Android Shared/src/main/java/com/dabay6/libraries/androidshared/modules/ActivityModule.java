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

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.dabay6.libraries.androidshared.annotations.ForActivity;
import com.dabay6.libraries.androidshared.ui.dialogs.DateTimePickerDialogFragment;
import com.dabay6.libraries.androidshared.ui.dialogs.GpsDialogFragment;
import com.dabay6.libraries.androidshared.ui.dialogs.changelog.ChangeLogDialogFragment;
import com.dabay6.libraries.androidshared.ui.dialogs.googleservices.LegalNoticesDialogFragment;
import com.dabay6.libraries.androidshared.ui.dialogs.opensource.OpenSourceDialogFragment;
import com.dabay6.libraries.androidshared.ui.fragments.ActionBarController;
import com.dabay6.libraries.androidshared.ui.fragments.ActivityTitleController;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Remel Pugh
 */
@Module(
        addsTo = ApplicationModule.class,
        complete = false,
        injects = {
                // fragments
                ChangeLogDialogFragment.class,
                DateTimePickerDialogFragment.class,
                GpsDialogFragment.class,
                LegalNoticesDialogFragment.class,
                OpenSourceDialogFragment.class
        },
        library = true)
@SuppressWarnings("unused")
public class ActivityModule {
    final static int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
    protected final Activity activity;

    /**
     * @param activity
     */
    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    static OkHttpClient createOkHttpClient(final Context context) {
        OkHttpClient client = new OkHttpClient();

        // Install an HTTP cache in the application cache directory.
        try {
            final File cacheDir = new File(context.getCacheDir(), "http");
            final Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

            client.setCache(cache);
        }
        catch (IOException e) {
            //Timber.e(e, "Unable to install disk cache.");
        }

        return client;
    }

    /**
     * @return
     */
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return createOkHttpClient(activity.getApplicationContext());
    }

    /**
     * @return
     */
    @Provides
    @Singleton
    Picasso providePicasso() {
        final Context context = activity.getApplicationContext();

        return new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(createOkHttpClient(context)))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
                        //Timber.e(e, "Failed to load image: %s", uri);
                    }
                })
                .build();
    }

    /**
     * @return
     */
    @Provides
    @Singleton
    ActionBarController providesActionBarController() {
        return new ActionBarController(activity);
    }

    /**
     * @return
     */
    @Provides
    @Singleton
    Activity providesActivity() {
        return activity;
    }

    /**
     * Allow the activity context to be injected but require that it be annotated with {@link
     * com.dabay6.libraries.androidshared.annotations.ForActivity @ForActivity} to explicitly differentiate it from
     * application context.
     */
    @Provides
    @Singleton
    @ForActivity
    Context providesActivityContext() {
        return activity;
    }

    /**
     * @return
     */
    @Provides
    @Singleton
    ActivityTitleController providesTitleController() {
        return new ActivityTitleController(activity);
    }
}