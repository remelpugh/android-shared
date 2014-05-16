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

package com.dabay6.libraries.androidshared.ui.dialogs.changelog.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.helper.SharedPreferencesEditorHelper;
import com.dabay6.libraries.androidshared.helper.SharedPreferencesHelper;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.ui.dialogs.changelog.ChangeLogDialogFragment;
import com.dabay6.libraries.androidshared.ui.dialogs.changelog.ChangeLogItem;
import com.dabay6.libraries.androidshared.ui.dialogs.changelog.OnChangeLogDialogListener;
import com.dabay6.libraries.androidshared.ui.dialogs.changelog.Release;
import com.dabay6.libraries.androidshared.util.AppUtils;
import com.dabay6.libraries.androidshared.util.AssetUtils;
import com.dabay6.libraries.androidshared.util.ListUtils;
import com.dabay6.libraries.androidshared.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * ChangeLogDialogUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class ChangeLogDialogUtils {
    private static final String CHANGE_LOG = "change_log.json";
    private static final String PREF_CHANGE_LOG = "com.util.android.ui.changelog.PREF_CHANGE_LOG";
    private final static String TAG = Logger.makeTag(ChangeLogDialogUtils.class);

    /**
     * Hidden constructor
     */
    private ChangeLogDialogUtils() {
    }

    /**
     * Will display the application change log if it hasn't already been displayed.
     *
     * @param activity {@link android.app.Activity} used to create the change log dialog.
     */
    public static void autoDisplayChangeLog(final Activity activity) {
        if (!ChangeLogDialogUtils.hasShownChangeLog(activity)) {
            ChangeLogDialogUtils.displayChangeLog(activity, new OnChangeLogDialogListener() {
                @Override
                public void onChangeLogDismissed() {
                    setChangeLogShown(activity);
                }
            });
        }
    }

    /**
     * @param context   {@link Context} used to retrieve resources.
     * @param assetName Name of the asset file containing the change log json.
     * @param callback  The callback to call upon dismissal of the change log alert dialog.
     *
     * @return {@link AlertDialog} used to display the change log.
     */
    public static AlertDialog createDialog(final Context context, final String assetName,
                                           final OnChangeLogDialogListener callback) {
        return createDialog(context, assetName, getStyle(), callback);
    }

    /**
     * @param context   {@link Context} used to retrieve resources.
     * @param assetName Name of the asset file containing the change log json.
     * @param style     The css style to be applied to the html.
     * @param callback  The callback to call upon dismissal of the change log alert dialog.
     *
     * @return {@link AlertDialog} used to display the change log.
     */
    public static AlertDialog createDialog(final Context context, final String assetName, final String style,
                                           final OnChangeLogDialogListener callback) {
        final AlertDialog.Builder builder;
        final Resources res = context.getResources();
        final String title = res.getString(R.string.change_log_title, AppUtils.getApplicationVersion(context));
        final WebSettings settings;
        final WebView web = new WebView(context);
        String html;

        try {
            html = getHtmlChangeLog(context, assetName, style);

            settings = web.getSettings();
            settings.setSupportZoom(false);
        }
        catch (final IOException ex) {
            html = StringUtils.empty();

            Logger.error(TAG, ex.getMessage(), ex);
        }

        web.loadData(html, "text/html", "utf-8");

        builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
               .setView(web)
               .setPositiveButton(R.string.change_log_close, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(final DialogInterface dialog, final int which) {
                       dialog.dismiss();
                       if (callback != null) {
                           callback.onChangeLogDismissed();
                       }
                   }
               });

        return builder.create();
    }

    /**
     * Displays the applications change log.
     *
     * @param context  {@link Context} used to create the change log dialog.
     * @param callback The callback to call upon dismissal of the change log alert dialog.
     */
    public static void displayChangeLog(final Activity context, final OnChangeLogDialogListener callback) {
        displayChangeLog(context, CHANGE_LOG, getStyle(), callback);
    }

    /**
     * Displays the applications change log.
     *
     * @param context   {@link Context} used to create the change log dialog.
     * @param assetName Name of the asset file containing the change log json.
     * @param style     The css style to be applied to the html.
     * @param callback  The callback to call upon dismissal of the change log alert dialog.
     */
    public static void displayChangeLog(final Activity context, final String assetName, final String style,
                                        final OnChangeLogDialogListener callback) {
        if (context.isFinishing()) {
            return;
        }

        ChangeLogDialogUtils.createDialog(context, assetName, style, callback).show();
    }

    /**
     * Displays the applications change log.
     *
     * @param context {@link Context} used to create the {@link ChangeLogDialogFragment}.
     */
    public static void displayChangeLogDialogFragment(final FragmentActivity context) {
        displayChangeLogDialogFragment(context, CHANGE_LOG, getStyle());
    }

    /**
     * Displays the applications change log.
     *
     * @param context {@link Context} used to create the {@link ChangeLogDialogFragment}.
     */
    public static void displayChangeLogDialogFragment(final FragmentActivity context, final String assetName,
                                                      final String style) {
        if (context.isFinishing()) {
            return;
        }

        final DialogFragment fragment = ChangeLogDialogFragment.newInstance(assetName, style);

        fragment.show(context.getSupportFragmentManager(), "change_log");
    }

    /**
     * Builds the CSS style used by the html.
     *
     * @return A {@link String} containing the defined CSS styles.
     */
    public static String getStyle() {
        final StringBuilder style = new StringBuilder();

        style.append("<style type=\"text/css\">");
        style.append("h1 { margin-left: 0px; font-size: 14pt; }");
        style.append("h3 { margin-left: 0px; padding-left: 10px; font-size: 10pt; }");
        style.append("li { margin-left: 0px; font-size: 10pt;}");
        style.append("ul { padding-left: 30px;}");
        style.append("</style>");

        return style.toString();
    }

    /**
     * Check to see if the change log for the current application version has been displayed.
     *
     * @param context the {@link Context} used to retrieve the {@link android.content.SharedPreferences} instance.
     *
     * @return true if change log has been viewed, otherwise false.
     */
    public static boolean hasShownChangeLog(final Context context) {
        final String currentVersionName = AppUtils.getApplicationVersion(context);
        final SharedPreferencesHelper helper = new SharedPreferencesHelper(context);
        final String versionName;

        versionName = helper.stringValue(PREF_CHANGE_LOG, "Unknown");

        return currentVersionName.equalsIgnoreCase(versionName);
    }

    /**
     * Updates the shared preferences to indicate the change log have been viewed.
     *
     * @param context the {@link Context} used to retrieve the {@link android.content.SharedPreferences} instance.
     */
    public static void setChangeLogShown(final Context context) {
        final SharedPreferencesEditorHelper helper = new SharedPreferencesEditorHelper(context);
        final String currentVersionName = AppUtils.getApplicationVersion(context);

        helper.putString(PREF_CHANGE_LOG, currentVersionName).commit();
    }

    /**
     * Builds the html using the change log json asset file.
     *
     * @param context   {@link Context} used to retrieve resources.
     * @param assetName Name of the asset file containing the change log json.
     * @param style     The css style to be applied to the html.
     *
     * @return A {@link String} containing html.
     */
    private static String getHtmlChangeLog(final Context context, final String assetName, final String style)
            throws IOException {
        final JsonReader reader;
        final Gson gson = new Gson();
        final List<Release> releases = ListUtils.newList();
        String html = null;

        try {
            reader = new JsonReader(new InputStreamReader(AssetUtils.open(context, assetName), "UTF-8"));
            reader.beginArray();

            while (reader.hasNext()) {
                final Release release = gson.fromJson(reader, Release.class);

                releases.add(release);
            }

            reader.endArray();
            reader.close();

            if (releases.size() > 0) {
                html = String.format("<html><head>%s</head><body>", style);
                html += parseReleases(context, releases);
                html += "</body></html>";
            }
        }
        catch (final Exception ex) {
            html = null;

            Logger.error(TAG, ex.getMessage(), ex);
        }

        return html;
    }

    private static String parseReleases(final Context context, final List<Release> releases) {
        final Resources res = context.getResources();
        final StringBuilder html = new StringBuilder();

        for (final Release release : releases) {
            html.append("<h1>");
            html.append(res.getString(R.string.change_log_release, release.getVersion()));
            html.append("</h1>");

            if (release.getChanges().size() > 0) {
                final List<ChangeLogItem> list = release.getChanges();

                html.append("<h3>");
                html.append(res.getString(R.string.change_log_changes));
                html.append("</h3>");
                html.append("<ul>");

                for (final ChangeLogItem item : list) {
                    html.append(String.format("<li>%s</li>", item.getDescription()));
                }

                html.append("</ul>");
            }

            if (release.getFixes().size() > 0) {
                final List<ChangeLogItem> list = release.getFixes();

                html.append("<h3>");
                html.append(res.getString(R.string.change_log_fixes));
                html.append("</h3>");
                html.append("<ul>");

                for (final ChangeLogItem item : list) {
                    html.append(String.format("<li>%s</li>", item.getDescription()));
                }

                html.append("</ul>");
            }
        }

        return html.toString();
    }
}