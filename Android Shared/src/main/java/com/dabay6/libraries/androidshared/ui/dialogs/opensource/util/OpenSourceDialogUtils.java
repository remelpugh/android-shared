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

package com.dabay6.libraries.androidshared.ui.dialogs.opensource.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.R.string;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.ui.dialogs.opensource.OnOpenSourceDialogListener;
import com.dabay6.libraries.androidshared.ui.dialogs.opensource.OpenSourceItem;

import java.util.List;

/**
 * OpenSourceDialogUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class OpenSourceDialogUtils {
    public final static String HTML = "<html>\n" +
                                      "<head>\n" +
                                      "    <title></title>\n" +
                                      "    <style>\n" +
                                      "        li {\n" +
                                      "            margin-left: 0px;\n" +
                                      "            font-size: 10pt;\n" +
                                      "        }\n" +
                                      "        pre {\n" +
                                      "            background-color: #eeeeee;\n" +
                                      "            font-size: 8pt;\n" +
                                      "            padding: .5em;\n" +
                                      "            white-space: pre-wrap;\n" +
                                      "        }\n" +
                                      "        ul {\n" +
                                      "            padding-left: 30px;\n" +
                                      "        }\n" +
                                      "    </style>\n" +
                                      "</head>\n" +
                                      "<body>\n" +
                                      "%s" +
                                      "</body>\n" +
                                      "</html>";
    public final static String HTML_ITEM = "<ul>\n" +
                                           "   <li>%s</li>\n" +
                                           "</ul>\n" +
                                           "%s";
    private final static String TAG = Logger.makeTag(OpenSourceDialogUtils.class);

    /**
     * Hidden constructor
     */
    private OpenSourceDialogUtils() {
    }

    /**
     * @param context
     * @param items
     */
    public static void addDefaultItems(final Context context, final List<OpenSourceItem> items) {
        OpenSourceItem item;

        item = new OpenSourceItem(context.getString(string.open_source_actionbar_sherlock),
                                  context.getString(string.open_source_actionbar_sherlock_license));
        items.add(item);

        item = new OpenSourceItem(context.getString(string.open_source_google_gson),
                                  context.getString(string.open_source_google_gson_license));
        items.add(item);
    }

    /**
     * @param context
     * @param items
     * @param onOpenSourceDialogListener
     * @return
     */
    public static AlertDialog createDialog(final Context context, final List<OpenSourceItem> items,
                                           final OnOpenSourceDialogListener onOpenSourceDialogListener) {
        final AlertDialog.Builder builder;
        final WebSettings settings;
        final WebView web = new WebView(context);
        String html = "";

        settings = web.getSettings();
        settings.setSupportZoom(false);

        for (final OpenSourceItem item : items) {
            html += String.format(HTML_ITEM, item.getName(), item.getLicense());
        }

        web.loadData(String.format(HTML, html), "text/html", "utf-8");

        builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.about_licenses)
               .setView(web)
               .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                   public void onClick(final DialogInterface dialog, final int whichButton) {
                       dialog.dismiss();
                       if (onOpenSourceDialogListener != null) {
                           onOpenSourceDialogListener.onOpenSourceDialogDismissed();
                       }
                   }
               });

        return builder.create();
    }

    /**
     * Displays the open source items used by the application.
     *
     * @param context  {@link Context} used to create the open source dialog.
     * @param callback The callback to call upon dismissal of the open source alert dialog.
     */
    public static void displayOpenSourceDialog(final Context context, final List<OpenSourceItem> items,
                                               final OnOpenSourceDialogListener callback) {
        displayOpenSourceDialog(context, items, true, callback);
    }

    /**
     * Displays the open source items used by the application.
     *
     * @param context  {@link Context} used to create the open source dialog.
     * @param callback The callback to call upon dismissal of the open source alert dialog.
     */
    public static void displayOpenSourceDialog(final Context context, final List<OpenSourceItem> items,
                                               final boolean addDefaultItems,
                                               final OnOpenSourceDialogListener callback) {
        if (addDefaultItems) {
            addDefaultItems(context, items);
        }

        OpenSourceDialogUtils.createDialog(context, items, callback).show();
    }
}