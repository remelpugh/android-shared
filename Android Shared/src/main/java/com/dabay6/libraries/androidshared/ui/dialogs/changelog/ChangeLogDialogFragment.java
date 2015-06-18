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

package com.dabay6.libraries.androidshared.ui.dialogs.changelog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.ui.dialogs.BaseDialogFragment;
import com.dabay6.libraries.androidshared.ui.dialogs.changelog.util.ChangeLogDialogUtils;

/**
 * ChangeLogDialogFragment
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class ChangeLogDialogFragment extends BaseDialogFragment {
    private final static String TAG = Logger.makeTag(ChangeLogDialogFragment.class);
    private OnChangeLogDialogListener onChangeLogDialogDismissedListener;

    /**
     * Default constructor.
     */
    public ChangeLogDialogFragment() {
    }

    /**
     * Creates a new instance of the {@link ChangeLogDialogFragment}.
     *
     * @param assetName The name of the asset file containing the change log json.
     *
     * @return A {@link ChangeLogDialogFragment}.
     */
    public static ChangeLogDialogFragment newInstance(final String assetName) {
        return newInstance(assetName, ChangeLogDialogUtils.getStyle());
    }

    /**
     * Creates a new instance of the {@link ChangeLogDialogFragment}.
     *
     * @param assetName The name of the asset file containing the change log json.
     * @param style     The css style to be applied to the html.
     *
     * @return A {@link ChangeLogDialogFragment}.
     */
    public static ChangeLogDialogFragment newInstance(final String assetName, final String style) {
        final Bundle arguments = new Bundle();
        final ChangeLogDialogFragment fragment = new ChangeLogDialogFragment();

        arguments.putString("assetName", assetName);
        arguments.putString("style", style);

        fragment.setArguments(arguments);

        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        try {
            onChangeLogDialogDismissedListener = (OnChangeLogDialogListener) activity;
        }
        catch (final ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement OnChangeLogDialogListener");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Bundle bundle = getArguments();

        return ChangeLogDialogUtils.createDialog(activityContext,
                bundle.getString("assetName"),
                bundle.getString("style"),
                onChangeLogDialogDismissedListener);
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