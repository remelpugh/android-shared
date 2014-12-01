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

package com.dabay6.libraries.androidshared.ui.dialogs.googleservices.util;

import android.app.AlertDialog;
import android.content.Context;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

import com.dabay6.libraries.androidshared.R.string;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * LegalNoticesUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class LegalNoticesUtils {
    @SuppressWarnings("unused")
    private final static String TAG = Logger.makeTag(LegalNoticesUtils.class);

    /**
     * Adds a Google Play Services legal notice {@link Preference} to a {@link PreferenceActivity}.
     *
     * @param context The {@link PreferenceActivity} to be added to.
     */
    @SuppressWarnings("deprecation")
    public static Preference buildLegalNoticePreference(final PreferenceActivity context) {
        final Preference preference;

        preference = new Preference(context);
        preference.setKey("GOOGLE_PLAY_SERVICES");
        preference.setSummary(string.google_services_legal_notices_summary);
        preference.setTitle(string.google_services_legal_notices_title);
        preference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(final Preference preference) {
                displayDialog(context);
                return true;
            }
        });

        return preference;
    }

    /**
     * @param context
     * @return
     */
    public static AlertDialog createDialog(final Context context) {
        return createDialog(context, string.google_services_legal_notices_title);
    }

    /**
     * @param context
     * @param titleResourceId
     * @return
     */
    public static AlertDialog createDialog(final Context context, final int titleResourceId) {
        return createDialog(context, context.getString(titleResourceId));
    }

    /**
     * @param context
     * @param title
     * @return
     */
    public static AlertDialog createDialog(final Context context, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        return builder.setTitle(title)
                .setMessage(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(context))
                .create();
    }

    /**
     * @param context
     */
    public static void displayDialog(final Context context) {
        displayDialog(context, string.google_services_legal_notices_title);
    }

    /**
     * @param context
     * @param titleResourceId
     */
    public static void displayDialog(final Context context, final int titleResourceId) {
        displayDialog(context, context.getString(titleResourceId));
    }

    /**
     * @param context
     * @param title
     */
    public static void displayDialog(final Context context, final String title) {
        createDialog(context, title).show();
    }
}