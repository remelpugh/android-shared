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

import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.dabay6.libraries.androidshared.exceptions.ActivityStartException;

import java.io.File;
import java.io.Serializable;
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
     *
     * @return
     */
    public static ActivityIntentBuilder createActivityIntent(final Context context,
                                                             final Class<? extends Activity> cls) {
        return new ActivityIntentBuilder(context, cls);
    }

    /**
     * Creates an intent which when fired, will launch the phone's dialer.
     *
     * @param phone number to be dialed.
     *
     * @return the newly-created {@link Intent}
     */
    public static Intent newDialNumberIntent(final String phone) {
        final IntentBuilder builder = new IntentBuilder(Intent.ACTION_DIAL);

        return builder.addData(Uri.parse("tel:" + phone.replace(" ", ""))).isExternal().toIntent();
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
        final IntentBuilder builder = new IntentBuilder(Intent.ACTION_SEND);

        return builder.add(Intent.EXTRA_EMAIL, new String[]{to})
                      .add(Intent.EXTRA_TEXT, body)
                      .add(Intent.EXTRA_SUBJECT, subject)
                      .addType("message/rfc822")
                      .toIntent();
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
        final IntentBuilder builder = new IntentBuilder(Intent.ACTION_VIEW);
        final StringBuilder request = new StringBuilder();

        request.append("geo:0,0?q=");
        request.append(Uri.encode(address));

        if (!TextUtils.isEmpty(title)) {
            final String encoded = String.format("(%s)", title);

            request.append(encoded);
        }

        request.append("&hl=");
        request.append(Locale.getDefault().getLanguage());

        return builder.addData(Uri.parse(request.toString())).toIntent();
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
        final IntentBuilder builder = new IntentBuilder(Intent.ACTION_PICK);

        builder.addType("image/*");

        if (TextUtils.isEmpty(title)) {
            return builder.toIntent();
        }
        else {
            return Intent.createChooser(builder.toIntent(), title);
        }
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
        final IntentBuilder builder = new IntentBuilder(MediaStore.ACTION_IMAGE_CAPTURE);

        return builder.add(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file)).toIntent();
    }

    /**
     * Creates an intent which when fired, will launch a web browser.
     *
     * @param url {@link String} containing the url to be displayed.
     *
     * @return the newly-created {@link Intent}
     */
    public static Intent newWebIntent(final String url) {
        return (new IntentBuilder(Intent.ACTION_VIEW)).addData(Uri.parse(url)).toIntent();
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

    /**
     * ActivityIntentBuilder
     * <p>
     * </p>
     *
     * @author Remel Pugh
     * @version 1.0
     */
    public static class ActivityIntentBuilder {
        private final Context activityContext;
        private final Intent intent;

        /**
         * @param context
         * @param cls
         */
        public ActivityIntentBuilder(final Context context, final Class<? extends Activity> cls) {
            activityContext = context;
            intent = new Intent(context, cls);
        }

        /**
         * @return
         */
        public ActivityIntentBuilder clearTop() {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            return this;
        }

        /**
         * @return
         */
        public Intent get() {
            return intent;
        }

        /**
         * @return
         */
        public ActivityIntentBuilder newTask() {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            return this;
        }

        /**
         * @return
         */
        public ActivityIntentBuilder noAnimation() {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            return this;
        }

        /**
         * @return
         */
        public ActivityIntentBuilder noHistory() {
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            return this;
        }

        /**
         * @return True if activity was successfully started, otherwise false.
         */
        public boolean start() {
            try {
                startWithAnimation(0, 0);

                return true;
            }
            catch (Exception ignored) {
                return false;
            }
        }

        /**
         *
         * @throws ActivityStartException
         */
        public void startWithAnimation() throws ActivityStartException {
            startWithAnimation(anim.fade_in, anim.fade_out);
        }

        /**
         * @param enterAnimation A resource ID of the animation resource to use for the incoming activity. Use 0 for no
         *                       animation.
         * @param exitAnimation  A resource ID of the animation resource to use for the outgoing activity. Use 0 for no
         *                       animation.
         */
        public void startWithAnimation(final int enterAnimation, final int exitAnimation)
                throws ActivityStartException {
            try {
                if (!(activityContext instanceof Activity)) {
                    throw new ActivityStartException("Context must of type Activity");
                }

                final Activity activity = (Activity) activityContext;

                activity.startActivity(intent);
                activity.overridePendingTransition(enterAnimation, exitAnimation);
            }
            catch (Exception ex) {
                throw new ActivityStartException(ex);
            }
        }
    }

    /**
     * IntentBuilder
     * <p>
     * </p>
     *
     * @author Remel Pugh
     * @version 1.0
     */
    public static class IntentBuilder {
        private final Intent intent;

        /**
         * Create {@link IntentUtils.IntentBuilder} with a default action.
         */
        public IntentBuilder(final String action) {
            intent = new Intent(action);
        }

        /**
         * Add extra field data value to intent being built up
         *
         * @return this builder
         */
        public IntentBuilder add(final String fieldName, final boolean[] values) {
            intent.putExtra(fieldName, values);

            return this;
        }

        /**
         * Add extra field data values to intent being built up
         *
         * @return this builder
         */
        public IntentBuilder add(final String fieldName, final CharSequence[] values) {
            intent.putExtra(fieldName, values);

            return this;
        }

        /**
         * Add extra field data value to intent being built up
         *
         * @return this builder
         */
        public IntentBuilder add(final String fieldName, final int value) {
            intent.putExtra(fieldName, value);

            return this;
        }

        /**
         * Add extra field data value to intent being built up
         *
         * @return this builder
         */
        public IntentBuilder add(final String fieldName, final int[] values) {
            intent.putExtra(fieldName, values);

            return this;
        }

        /**
         * Add extra field data value to intent being built up
         *
         * @return this builder
         */
        public IntentBuilder add(final String fieldName, final Serializable value) {
            intent.putExtra(fieldName, value);

            return this;
        }

        /**
         * Add extra field data value to intent being built up
         *
         * @return this builder
         */
        public IntentBuilder add(final String fieldName, final String value) {
            intent.putExtra(fieldName, value);

            return this;
        }

        /**
         *
         * @param fieldName
         * @param uri
         * @return
         */
        public IntentBuilder add(final String fieldName, final Uri uri) {
            intent.putExtra(fieldName, uri);

            return this;
        }

        /**
         * Set the data this intent is operating on.
         *
         * @param uri The Uri of the data this intent is now targeting.
         *
         * @return this builder
         */
        public IntentBuilder addData(final Uri uri) {
            intent.setData(uri);

            return this;
        }

        /**
         * Add additional flags to the intent (or with existing flags value).
         *
         * @param flag The new flags to set.
         *
         * @return this builder
         */
        public IntentBuilder addFlag(final int flag) {
            intent.addFlags(flag);

            return this;
        }

        /**
         * Set an explicit MIME data type.
         *
         * @param type The MIME type of the data being handled by this intent.
         *
         * @return this builder
         */
        public IntentBuilder addType(final String type) {
            intent.setType(type);

            return this;
        }

        /**
         * @return this builder
         */
        public IntentBuilder isExternal() {
            return addFlag(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        }

        /**
         * Get built intent
         *
         * @return intent
         */
        public Intent toIntent() {
            return intent;
        }
    }
}