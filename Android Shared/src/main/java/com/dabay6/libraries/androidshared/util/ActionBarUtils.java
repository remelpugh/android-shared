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

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

import java.util.List;

/**
 * ActionBarUtils
 * <p>
 * A {@link ActionBar} utility class used to setup the {@link ActionBar} in different configurations}.
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class ActionBarUtils {
    /**
     * Hidden constructor
     */
    private ActionBarUtils() {
    }

    /**
     * Configures the {@link ActionBar} to display a custom discard/done button group.
     *
     * @param actionBar the {@link ActionBar} to customize.
     * @param listener  the callback to be invoked when the action bar buttons are clicked.
     */
    @SuppressLint("InflateParams")
    public static void configureDiscardDone(final ActionBar actionBar, final OnActionBarDiscardDoneListener listener) {
        final LayoutInflater inflater;
        final View view;
        final ViewsFinder finder;

        inflater = (LayoutInflater) actionBar.getThemedContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.util__actionbar_discard_done, null);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                                    ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME |
                                    ActionBar.DISPLAY_SHOW_TITLE
        );
        actionBar.setCustomView(view, new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        finder = new ViewsFinder(view);
        finder.onClick(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                final int id = view.getId();

                if (listener != null) {
                    if (id == R.id.action_discard) {
                        listener.onDiscard();
                    }
                    else if (id == R.id.action_done) {
                        listener.onDone();
                    }
                }
            }
        }, R.id.action_discard, R.id.action_done);

    }

    /**
     * Configures the {@link ActionBar} to display the list navigation spinner.
     *
     * @param activity the {@link ActionBarActivity} containing access to the {@link ActionBar}.
     * @param <T>      Generic activity type that extends {@link ActionBarActivity}.
     */
    public static <T extends ActionBarActivity> void configureListNavigation(final T activity) {
        final ActionBar actionBar = activity.getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);

    }

    /**
     * Configures the {@link ActionBar} to display the list navigation spinner.
     *
     * @param actionBar      the {@link ActionBar} to customize.
     * @param textArrayResId the identifier of the array to use as the data source.
     * @param callback       an {@link OnNavigationListener} that will receive events when the user selects a
     *                       navigation
     *                       item.
     */
    public static void configureListNavigation(final ActionBar actionBar, final int textArrayResId,
                                               final OnNavigationListener callback) {
        final ArrayAdapter<CharSequence> list;
        final Context context;

        if (callback == null) {
            throw new IllegalArgumentException("OnNavigationListener must be supplied");
        }

        context = actionBar.getThemedContext();

        list = ArrayAdapter.createFromResource(context, textArrayResId, R.layout.support_simple_spinner_dropdown_item);
        list.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(list, callback);
    }

    /**
     * Configures the {@link ActionBar} to display the standard navigation.
     *
     * @param activity the {@link ActionBarActivity} containing access to the {@link ActionBar}.
     * @param <T>      Generic activity type that extends {@link ActionBarActivity}.
     */
    public static <T extends ActionBarActivity> void configureStandardNavigation(final T activity) {
        final ActionBar actionBar = activity.getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    /**
     * Configures the {@link ActionBar} to display navigation tabNames.
     *
     * @param actionBar the {@link ActionBar} to customize.
     * @param names     the collection of tab names.
     * @param listener  the {@link TabListener} to handle tab selection events.
     */
    public static void configureTabNavigation(final ActionBar actionBar, final List<String> names,
                                              final TabListener listener) {

        if (listener == null) {
            throw new IllegalArgumentException("TabListener must be supplied");
        }

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (final String name : names) {
            final Tab tab = actionBar.newTab();

            tab.setTabListener(listener);
            tab.setText(name);

            actionBar.addTab(tab);
        }
    }

    /**
     * OnActionBarDiscardDoneListener
     * <p>
     * Interface definition for a callback to be invoked when the done or discard views are clicked.
     * </p>
     *
     * @author Remel Pugh
     * @version 1.0
     */
    public interface OnActionBarDiscardDoneListener {
        /**
         * Called when the discard has been clicked.
         */
        void onDiscard();

        /**
         * Called when the done has been clicked.
         */
        void onDone();
    }
}