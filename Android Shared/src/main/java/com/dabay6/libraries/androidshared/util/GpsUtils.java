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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import com.dabay6.libraries.androidshared.R.id;
import com.dabay6.libraries.androidshared.R.layout;
import com.dabay6.libraries.androidshared.R.string;
import com.dabay6.libraries.androidshared.helper.PreferenceHelper;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.IntentUtils.IntentBuilder;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

/**
 * GpsUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class GpsUtils {
    private final static String PREF_GPS_NEVER_SHOW = "com.util.android.util.PREF_GPS_NEVER_SHOW";
    private final static String TAG = Logger.makeTag(GpsUtils.class);

    /**
     * Hidden constructor.
     */
    private GpsUtils() {
    }

    /**
     * Check if the user has selected to never show the GPS dialog again.
     *
     * @param context The {@link Context} used to retrieve the user preference.
     *
     * @return True if the dialog can be shown, otherwise false.
     */
    public static boolean canBeShown(final Context context) {
        return PreferenceHelper.with(context).getBoolean(PREF_GPS_NEVER_SHOW, false);
    }

    /**
     * Creates the GPS dialog that allows the user to turn on GPS.
     *
     * @param context         The {@link Context} used to create the dialog.
     * @param titleResourceId The resource id for the title of the dialog.
     */
    public static AlertDialog createDialog(final Context context, final int titleResourceId) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(layout.util__gps_dialog_never_show, null);
        final ViewsFinder finder = new ViewsFinder(view);
        final CheckBox checkBox = finder.find(id.gps_never_show);

        builder.setMessage(string.gps_message)
               .setNegativeButton(string.gps_skip, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(final DialogInterface dialog, final int id) {
                       if (checkBox != null && checkBox.isChecked()) {
                           setCanBeShown(context);
                       }
                   }
               })
               .setPositiveButton(string.gps_settings, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(final DialogInterface dialog, final int id) {
                       final IntentBuilder intent;

                       if (checkBox != null && checkBox.isChecked()) {
                           setCanBeShown(context);
                       }

                       intent = new IntentBuilder(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                       context.startActivity(intent.toIntent());
                   }
               })
               .setTitle(context.getString(titleResourceId))
               .setView(view);

        return builder.create();
    }

    /**
     * Creates the GPS dialog that allows the user to turn on GPS, with the default title.
     *
     * @param context The {@link Context} used to create the dialog.
     */
    public static AlertDialog createDialog(final Context context) {
        return createDialog(context, string.gps_title);
    }

    /**
     * Determines if device GpsUtils is enabled.
     *
     * @param context  {@link Context} used to create the {@link LocationManager}.
     * @param provider the name of the provider.
     *
     * @return true if GpsUtils is enabled, otherwise false.
     */
    public static boolean isEnabled(final Context context, final String provider) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(provider);
    }

    /**
     * Determines if device GpsUtils is enabled.
     *
     * @param context {@link Context} used to create the {@link LocationManager}.
     *
     * @return true if GpsUtils is enabled, otherwise false.
     */
    public static boolean isEnabled(final Context context) {
        boolean enabled = GpsUtils.isEnabled(context, LocationManager.GPS_PROVIDER);

        if (!enabled) {
            enabled = GpsUtils.isEnabled(context, LocationManager.NETWORK_PROVIDER);
        }

        if (!enabled) {
            enabled = GpsUtils.isEnabled(context, LocationManager.PASSIVE_PROVIDER);
        }

        return enabled;
    }

    /**
     * Displays an {@link AlertDialog} indicating that GpsUtils is disabled, giving the user the option to turn it on.
     *
     * @param context {@link Context} used to create the {@link AlertDialog}.
     */
    public static void showDisabledAlert(final Context context) {
        showDisabledAlert(context, string.gps_title);
    }

    /**
     * Displays an {@link AlertDialog} indicating that GpsUtils is disabled, giving the user the option to turn it on.
     *
     * @param context         {@link Context} used to create the {@link AlertDialog}.
     * @param titleResourceId The resource containing the dialog title.
     */
    public static void showDisabledAlert(final Context context, final int titleResourceId) {
        if (canBeShown(context)) {
            final AlertDialog alert = createDialog(context, titleResourceId);

            alert.show();
        }
    }

    private static void setCanBeShown(final Context context) {
        PreferenceHelper.with(context).save(PREF_GPS_NEVER_SHOW, true);
    }
}