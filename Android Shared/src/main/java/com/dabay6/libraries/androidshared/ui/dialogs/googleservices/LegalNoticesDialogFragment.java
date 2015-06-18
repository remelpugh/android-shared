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

package com.dabay6.libraries.androidshared.ui.dialogs.googleservices;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dabay6.libraries.androidshared.R.string;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.ui.dialogs.BaseDialogFragment;
import com.dabay6.libraries.androidshared.ui.dialogs.googleservices.util.LegalNoticesUtils;

/**
 * LegalNoticesDialogFragment <p> Used to display the Google Play Services attribution text as required by <a
 * href="https://developers.google.com/maps/documentation/android/intro#attribution_requirements">https
 * ://developers.google.com/maps/documentation/android/intro#attribution_requirements</a> </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class LegalNoticesDialogFragment extends BaseDialogFragment {
    private final static String TAG = Logger.makeTag(LegalNoticesDialogFragment.class);

    /**
     * Default constructor.
     */
    public LegalNoticesDialogFragment() {
    }

    /**
     * @return
     */
    public static LegalNoticesDialogFragment newInstance() {
        return newInstance(string.google_services_legal_notices_title);
    }

    /**
     * @param titleResourceId
     *
     * @return
     */
    public static LegalNoticesDialogFragment newInstance(final int titleResourceId) {
        final Bundle arguments = new Bundle();
        final LegalNoticesDialogFragment fragment = new LegalNoticesDialogFragment();

        arguments.putInt("titleResourceId", titleResourceId);

        fragment.setArguments(arguments);

        return fragment;
    }

    /**
     * @param title
     *
     * @return
     */
    public static LegalNoticesDialogFragment newInstance(final String title) {
        final Bundle arguments = new Bundle();
        final LegalNoticesDialogFragment fragment = new LegalNoticesDialogFragment();

        arguments.putString("title", title);

        fragment.setArguments(arguments);

        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Bundle bundle = getArguments();

        if (bundle.containsKey("titleResourceId")) {
            return LegalNoticesUtils.createDialog(activityContext, bundle.getInt("titleResourceId"));
        }

        return LegalNoticesUtils.createDialog(activityContext, bundle.getString("title"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return null;
    }

    /**
     *
     */
    @SuppressWarnings("EmptyMethod")
    public void show() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void afterViews(final Bundle savedInstanceState) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getMenuResourceId() {
        return null;
    }
}