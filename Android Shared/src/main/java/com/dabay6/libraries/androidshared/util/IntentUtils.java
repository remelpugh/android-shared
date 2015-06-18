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

package com.dabay6.libraries.androidshared.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.dabay6.libraries.androidshared.app.ActivityIntentBuilder;
import com.dabay6.libraries.androidshared.content.IntentBuilder;

import java.io.File;
import java.util.List;
import java.util.Locale;

/**
 * IntentUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class IntentUtils {
    /**
     * Hide constructor
     */
    private IntentUtils() {
    }

    /**
     * @param context
     * @param cls
     * @return
     */
    public static ActivityIntentBuilder createActivityIntent(final Context context,
                                                             final Class<? extends Activity> cls) {
        return ActivityIntentBuilder.with(context, cls);
    }

    /**
     * Creates an intent which when fired, will launch the phone's dialer.
     *
     * @param phone number to be dialed.
     *
     * @return the newly-created {@link Intent}
     */
    public static Intent newDialNumberIntent(final String phone) {
        return IntentBuilder.with(Intent.ACTION_DIAL)
                            .addData(Uri.parse("tel:" + phone.replace(" ", "")))
                            .isExternal()
                            .build();
    }

    /**
     * Creates an intent which when fired, will launch an email application.
     *
     * @param to      who the email will be sent to
     * @param subject email subject
     * @param body    email body
     *
     * @return the newly-created {@link Intent}
     */
    public static Intent newEmailIntent(final String to, final String subject, final String body) {
        return IntentBuilder.with(Intent.ACTION_SEND)
                            .add(Intent.EXTRA_EMAIL, new String[]{to})
                            .add(Intent.EXTRA_TEXT, body)
                            .add(Intent.EXTRA_SUBJECT, subject)
                            .addType("message/rfc822")
                            .build();
    }

    /**
     * Creates an intent which when fired, will launch Google Maps
     *
     * @param address {@link String} containing the address to be displayed on the map.
     * @param title   {@link String} containing the title for the address.
     *
     * @return the newly-created {@link Intent}
     */
    public static Intent newMapIntent(final String address, final String title) {
        final StringBuilder request = new StringBuilder();

        request.append("geo:0,0?q=");
        request.append(Uri.encode(address));

        if (!TextUtils.isEmpty(title)) {
            final String encoded = String.format("(%s)", title);

            request.append(encoded);
        }

        request.append("&hl=");
        request.append(Locale.getDefault().getLanguage());

        return IntentBuilder.with(Intent.ACTION_VIEW).addData(Uri.parse(request.toString())).build();
    }

    /**
     * Creates an intent which when fired, will launch the phone's picture
     * gallery to select a picture from it.
     *
     * @param title {@link String} containing the title to be used with the {@link Intent} chooser.
     *
     * @return the newly-created {@link Intent}
     */
    public static Intent newSelectPictureIntent(final String title) {
        return Intent.createChooser(IntentBuilder.with(Intent.ACTION_PICK).addType("image/*").build(), title);
    }

    /**
     * Creates an intent which when fired, will launch the camera to take a
     * picture that's saved to a temporary file so you can use it directly
     * without going through the gallery.
     *
     * @param file {@link File} that should be used to temporarily store the picture
     *
     * @return the newly-created {@link Intent}
     */
    public static Intent newTakePictureIntent(final File file) {
        return IntentBuilder.with(MediaStore.ACTION_IMAGE_CAPTURE)
                            .add(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
                            .build();
    }

    /**
     * Creates an intent which when fired, will launch a web browser.
     *
     * @param url {@link String} containing the url to be displayed.
     *
     * @return the newly-created {@link Intent}
     */
    public static Intent newWebIntent(final String url) {
        return IntentBuilder.with(Intent.ACTION_VIEW).addData(Uri.parse(url)).build();
    }

    /**
     * Starts an {@link Activity} using the supplied {@link Intent}.
     *
     * @param context {@link Context} used to retrieve the {@link PackageManager}.
     * @param intent  {@link Intent} to check for availability.
     */
    public static void startActivityByIntent(final Context context, final Intent intent) {
        if (IntentUtils.isIntentAvailable(context, intent)) {
            context.startActivity(intent);
        }
    }

    /**
     * Indicates whether the specified action can be used as an intent. This
     * method queries the package manager for installed packages that can
     * respond to an intent with the specified action. If no suitable package is
     * found, this method returns false.
     *
     * @param context {@link Context} used to retrieve the {@link PackageManager}.
     * @param intent  {@link Intent} to check for availability.
     *
     * @return true if an Intent with the specified action can be sent and responded to, false otherwise.
     */
    private static boolean isIntentAvailable(final Context context, final Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> list;

        if (packageManager == null) {
            return false;
        }

        list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        return list.size() > 0;
    }

}