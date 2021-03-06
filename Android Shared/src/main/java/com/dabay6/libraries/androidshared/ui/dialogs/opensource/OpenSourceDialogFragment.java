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

package com.dabay6.libraries.androidshared.ui.dialogs.opensource;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.ui.dialogs.BaseDialogFragment;
import com.dabay6.libraries.androidshared.ui.dialogs.opensource.util.OpenSourceDialogUtils;
import com.dabay6.libraries.androidshared.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * OpenSourceDialogFragment
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class OpenSourceDialogFragment extends BaseDialogFragment {
    private final static String TAG = Logger.makeTag(OpenSourceDialogFragment.class);
    private OnOpenSourceDialogListener onOpenSourceDialogListener;

    /**
     * Default constructor.
     */
    public OpenSourceDialogFragment() {
    }

    /**
     * @param items
     * @return
     */
    public static OpenSourceDialogFragment newInstance(ArrayList<OpenSourceItem> items) {
        return newInstance(items, true);
    }

    /**
     * @param items
     * @param addDefaultItems
     * @return
     */
    public static OpenSourceDialogFragment newInstance(ArrayList<OpenSourceItem> items, final boolean addDefaultItems) {
        final Bundle arguments = new Bundle();
        final OpenSourceDialogFragment fragment = new OpenSourceDialogFragment();

        arguments.putBoolean("addDefaultItems", addDefaultItems);
        arguments.putParcelableArrayList("items", items);

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
            onOpenSourceDialogListener = (OnOpenSourceDialogListener) activity;
        }
        catch (final ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement OnOpenSourceDialogListener");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final Bundle bundle = getArguments();
        final Context context = getActivity();
        boolean addDefaultItems = true;
        List<OpenSourceItem> items = CollectionUtils.newList();

        if (bundle.containsKey("addDefaultItems")) {
            addDefaultItems = bundle.getBoolean("addDefaultItems");
        }
        if (bundle.containsKey("items")) {
            items = bundle.getParcelableArrayList("items");
        }

        if (addDefaultItems) {
            OpenSourceDialogUtils.addDefaultItems(context, items);
        }

        return OpenSourceDialogUtils.createDialog(context, items, onOpenSourceDialogListener);
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