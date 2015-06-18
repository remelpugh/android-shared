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

package com.dabay6.libraries.androidshared.helper;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.AndroidUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * CheckableAdapterHelper
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class CheckableAdapterHelper implements OnItemLongClickListener, OnItemClickListener, OnCheckedChangeListener {
    private final static String KEY_SELECTED_ITEM = "UTILS__KEY_SELECTED_ITEM";
    private final static String KEY_SELECTED_ITEMS = "UTILS__KEY_SELECTED_ITEMS";
    private final static String TAG = Logger.makeTag(CheckableAdapterHelper.class);
    private final BaseAdapter baseAdapter;
    private final Set<Long> selectedItems = new HashSet<Long>();
    private ActionMode actionMode;
    private AdapterView<? super BaseAdapter> adapterView;
    private Boolean hasCheckbox;
    private boolean isDualPane;
    private OnItemClickListener onItemClickListener;
    private Long savedSelectedId = null;

    /**
     * @param adapter
     */
    public CheckableAdapterHelper(final BaseAdapter adapter) {
        baseAdapter = adapter;
    }

    /**
     *
     */
    public void clearSelectedItems() {
        selectedItems.clear();
        baseAdapter.notifyDataSetChanged();
    }

    /**
     * @param mode
     */
    public void destroyActionMode(final ActionMode mode) {
        actionMode = null;
        clearSelectedItems();

        if (savedSelectedId != null) {
            select(savedSelectedId);
        }
    }

    /**
     *
     */
    public void finishActionMode() {
        if (actionMode != null) {
            actionMode.finish();
        }
    }

    /**
     * @return
     */
    public Context getContext() {
        return adapterView.getContext();
    }

    /**
     * @return
     */
    public int getSelectedCount() {
        return selectedItems.size();
    }

    /**
     * @return
     */
    public Set<Long> getSelectedItems() {
        return new HashSet<Long>(selectedItems);
    }

    /**
     * @param position
     * @param view
     * @return
     */
    @SuppressWarnings("UnusedReturnValue")
    @TargetApi(VERSION_CODES.HONEYCOMB)
    public View initializeView(final int position, final View view) {
        if (view instanceof Checkable) {
            final long itemId = baseAdapter.getItemId(position);
            final boolean isSelected = isChecked(itemId);

            if (AndroidUtils.isAtLeastHoneycomb()) {
                view.setActivated(isSelected);
            }
            else {
                ((Checkable) view).setChecked(isSelected);
            }
        }

        if (hasCheckboxView(view)) {
            initializeCheckbox(position, (ViewGroup) view);
        }

        return view;
    }

    /**
     * @param itemId
     * @return
     */
    public boolean isChecked(final long itemId) {
        return selectedItems.contains(itemId);
    }

    /**
     * @return
     */
    public boolean isDualPane() {
        return isDualPane;
    }

    /**
     * @param isDualPane
     */
    public void setDualPane(final boolean isDualPane) {
        this.isDualPane = isDualPane;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        final Long itemId = (Long) buttonView.getTag();

        setSelected(itemId, isChecked);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (actionMode == null) {
            if (isDualPane) {
                savedSelectedId = id;
                clearSelectedItems();
                toggle(baseAdapter.getItemId(position));
            }

            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(adapterView, view, position, id);
            }
        }
        else {
            onItemLongClick(adapterView, view, position, id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (actionMode == null) {
            clearSelectedItems();
            startActionMode();
        }

        toggle(baseAdapter.getItemId(position));

        return true;
    }

    /**
     * @param savedInstanceState
     */
    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }

        selectedItems.clear();

        if (savedInstanceState.containsKey(KEY_SELECTED_ITEMS)) {
            final long[] items = savedInstanceState.getLongArray(KEY_SELECTED_ITEMS);

            if (items != null) {
                for (final long id : items) {
                    selectedItems.add(id);
                }
            }
        }

        if (savedInstanceState.containsKey(KEY_SELECTED_ITEM)) {
            savedSelectedId = savedInstanceState.getLong(KEY_SELECTED_ITEM);
        }
    }

    /**
     * @param savedInstanceState
     */
    public void onSaveInstanceState(final Bundle savedInstanceState) {
        final long[] items = new long[selectedItems.size()];
        int index = 0;

        if (isDualPane && savedSelectedId != null) {
            savedInstanceState.putLong(KEY_SELECTED_ITEM, savedSelectedId);
        }

        for (final Long id : selectedItems) {
            items[index] = id;
            index += 1;
        }

        savedInstanceState.putLongArray(KEY_SELECTED_ITEMS, items);
    }

    /**
     * @param adapterView
     */
    public void setAdapterView(final AdapterView<? super BaseAdapter> adapterView) {
        this.adapterView = adapterView;

        ensureActivity();

        adapterView.setOnItemLongClickListener(this);
        adapterView.setOnItemClickListener(this);
        adapterView.setAdapter(baseAdapter);

        if (!selectedItems.isEmpty()) {
            startActionMode();
            onItemSelectedStateChanged();
        }
    }

    /**
     * @param onItemClickListener
     */
    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * @param itemId
     * @param isChecked
     */
    public void setSelected(final long itemId, final boolean isChecked) {
        if (isChecked) {
            select(itemId);
        }
        else {
            unSelect(itemId);
        }
    }

    /**
     * @param itemId
     */
    public void toggle(final long itemId) {
        setSelected(itemId, !isChecked(itemId));
    }

    private void ensureActivity() {
        final Context context = adapterView.getContext();

        if (context instanceof ListActivity) {
            throw new RuntimeException("ListView cannot belong to an activity which subclasses ListActivity");
        }

        if (context instanceof ActionBarActivity) {
            return;
        }

        throw new RuntimeException("ListView must belong to an activity which subclasses SherlockActivity");
    }

    private boolean hasCheckboxView(final View view) {
        if (hasCheckbox == null) {
            if (!(view instanceof ViewGroup)) {
                hasCheckbox = false;
            }
            else {
                final ViewGroup root = (ViewGroup) view;

                hasCheckbox = root.findViewById(android.R.id.checkbox) != null;
            }
        }
        return hasCheckbox;
    }

    private void initializeCheckbox(final int position, final ViewGroup view) {
        final CheckBox checkBox = (CheckBox) view.findViewById(android.R.id.checkbox);
        final boolean selected = isChecked(position);

        checkBox.setTag(position);
        checkBox.setChecked(selected);
        checkBox.setOnCheckedChangeListener(this);
    }

    private void onItemSelectedStateChanged() {
        if (actionMode != null) {
            final int count = getSelectedCount();

            if (count == 0) {
                finishActionMode();
                return;
            }

            final Resources res = adapterView.getResources();

            if (res != null) {
                final String title = res.getQuantityString(R.plurals.util__number_of_selected_items, count, count);

                actionMode.setTitle(title);
            }
        }
    }

    private void select(final long itemId) {
        final boolean isChecked = isChecked(itemId);

        if (isChecked) {
            return;
        }

        selectedItems.add(itemId);
        baseAdapter.notifyDataSetChanged();

        onItemSelectedStateChanged();
    }

    private void startActionMode() {
        if (!(adapterView.getContext() instanceof ActionBarActivity)) {
            throw new IllegalStateException("Context must be an ActionBarActivity");
        }
        if (!(baseAdapter instanceof Callback)) {
            throw new IllegalStateException("Adapter must implement ActionMode.Callback");
        }

        ActionBarActivity activity = (ActionBarActivity) adapterView.getContext();
        actionMode = activity.startSupportActionMode((ActionMode.Callback) baseAdapter);
    }

    private void unSelect(final long itemId) {
        final boolean isChecked = isChecked(itemId);

        if (!isChecked) {
            return;
        }

        selectedItems.remove(itemId);

        if (getSelectedCount() == 0) {
            finishActionMode();
            return;
        }

        baseAdapter.notifyDataSetChanged();

        onItemSelectedStateChanged();

    }
}