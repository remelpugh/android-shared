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

package com.dabay6.libraries.androidshared.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.dabay6.libraries.androidshared.helper.CheckableAdapterHelper;
import com.dabay6.libraries.androidshared.logging.Logger;

import java.util.Set;

/**
 * BaseCheckableCursorAdapter
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class BaseCheckableCursorAdapter extends CursorAdapter implements Callback, CheckableAdapter {
    private final static String TAG = Logger.makeTag(BaseCheckableCursorAdapter.class);
    private final LayoutInflater inflater;
    private final int layoutResourceId;
    protected CheckableAdapterHelper helper = new CheckableAdapterHelper(this);
    private OnActionModeCallbackListener onActionModeCallbackListener;

    /**
     * @param context
     * @param c
     */
    @SuppressWarnings("deprecation")
    public BaseCheckableCursorAdapter(final Context context, final Bundle savedInstanceState,
                                      final int layoutResourceId, final Cursor c) {
        super(context, c);

        inflater = LayoutInflater.from(context);
        this.layoutResourceId = layoutResourceId;
        helper.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * @param context
     * @param c
     * @param flags
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public BaseCheckableCursorAdapter(final Context context, final Bundle savedInstanceState,
                                      final int layoutResourceId, final Cursor c, final int flags) {
        super(context, c, flags);

        inflater = LayoutInflater.from(context);
        this.layoutResourceId = layoutResourceId;
        helper.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        final int position = cursor.getPosition();

        helper.initializeView(position, view);

        populateData(context, view, cursor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearSelections() {
        helper.clearSelectedItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finishActionMode() {
        helper.finishActionMode();
    }

    /**
     * @return
     */
    public OnActionModeCallbackListener getOnActionModeCallbackListener() {
        return onActionModeCallbackListener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnActionModeCallbackListener(final OnActionModeCallbackListener listener) {
        onActionModeCallbackListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSelectedCount() {
        return helper.getSelectedCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Long> getSelectedItems() {
        return helper.getSelectedItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDualPane() {
        return helper.isDualPane();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDualPane(final boolean isDualPane) {
        helper.setDualPane(isDualPane);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSelected(final Long itemId) {
        return helper.isChecked(itemId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
        return inflater.inflate(layoutResourceId, parent, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroyActionMode(final ActionMode mode) {
        if (onActionModeCallbackListener != null) {
            onActionModeCallbackListener.onActionModeDestroyed(mode);
        }

        helper.destroyActionMode(mode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(final Bundle savedInstanceState) {
        helper.onSaveInstanceState(savedInstanceState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void select(final Long itemId, final boolean isSelected) {
        helper.setSelected(itemId, isSelected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAdapterView(final AdapterView<? super BaseAdapter> adapterView) {
        helper.setAdapterView(adapterView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        helper.setOnItemClickListener(onItemClickListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggle(final Long itemId) {
        helper.toggle(itemId);
    }

    /**
     * @param context
     * @param view
     * @param cursor
     */
    protected abstract void populateData(final Context context, final View view, final Cursor cursor);
}