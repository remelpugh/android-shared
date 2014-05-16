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

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import java.util.Set;

/**
 * CheckableAdapter
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public interface CheckableAdapter {
    /**
     * Clears all currently selected items.
     */
    void clearSelections();

    /**
     *
     */
    void finishActionMode();

    /**
     * Get the number of currently selected items.
     *
     * @return A count of all selected items.
     */
    int getSelectedCount();

    /**
     * Gets the currently selected positions
     *
     * @return A {@link SparseBooleanArray} of currently selected items.
     */
    Set<Long> getSelectedItems();

    /**
     * Gets whether the activity is running on a phone or tablet.
     *
     * @return True if the activity is running on a phone, otherwise false.
     */
    boolean isDualPane();

    /**
     * Sets whether the fragment is running on a phone or tablet.
     *
     * @param isDualPane True if the activity is running on a tablet, otherwise false.
     */
    void setDualPane(final boolean isDualPane);

    /**
     * Gets whether the passed in item id is selected.
     *
     * @param itemId The id of the item to check.
     *
     * @return True if the item is selected, otherwise false.
     */
    boolean isSelected(Long itemId);

    /**
     * @param savedInstanceState
     */
    void onSaveInstanceState(final Bundle savedInstanceState);

    /**
     * Selected/Un-selects the item specified by the passed in position.
     *
     * @param itemId     The position of the item to be selected/un-selected.
     * @param isSelected True if the items is selected, otherwise false.
     */
    void select(final Long itemId, final boolean isSelected);

    /**
     * @param adapterView
     */
    void setAdapterView(AdapterView<? super BaseAdapter> adapterView);

    /**
     * @param listener
     */
    void setOnActionModeCallbackListener(final OnActionModeCallbackListener listener);

    /**
     * @param onItemClickListener
     */
    void setOnItemClickListener(OnItemClickListener onItemClickListener);

    /**
     * Toggles the current position.
     *
     * @param itemId The position to be toggled.
     */
    void toggle(final Long itemId);
}