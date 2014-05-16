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

package com.dabay6.libraries.androidshared.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dabay6.libraries.androidshared.util.GpsUtils;

/**
 * GpsDialogFragment
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class GpsDialogFragment extends BaseDialogFragment {
    /**
     * Default constructor.
     */
    public GpsDialogFragment() {
    }

    /**
     * Creates a new instance of the {@link GpsDialogFragment}.
     *
     * @return A {@link GpsDialogFragment} instance.
     */
    public static GpsDialogFragment newInstance() {
        return newInstance(null);
    }

    /**
     * Creates a new instance of the {@link GpsDialogFragment}.
     *
     * @param titleResourceId The resource identifier used for the dialog title.
     *
     * @return A {@link GpsDialogFragment} instance.
     */
    public static GpsDialogFragment newInstance(final Integer titleResourceId) {
        final Bundle arguments = new Bundle();
        final GpsDialogFragment fragment = new GpsDialogFragment();

        if (titleResourceId != null) {
            arguments.putInt("TitleResourceId", titleResourceId);
        }

        fragment.setArguments(arguments);

        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Bundle arguments = getArguments();
        final Context context = getActivity();

        if (!GpsUtils.hasNeverShowAgain(context)) {
            return null;
        }

        if (arguments != null && arguments.containsKey("TitleResouceId")) {
            return GpsUtils.createDialog(context, arguments.getInt("TitleResourceId"));
        }

        return GpsUtils.createDialog(context);
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
     * @return
     */
    @Override
    protected Integer getMenuResourceId() {
        return null;
    }
}