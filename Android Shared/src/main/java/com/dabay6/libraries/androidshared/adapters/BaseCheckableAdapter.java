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

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.dabay6.libraries.androidshared.logging.Logger;

import java.util.List;

/**
 * BaseCheckableAdapter
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class BaseCheckableAdapter<T> extends BaseAdapter {
    private final static String TAG = Logger.makeTag(BaseCheckableAdapter.class);
    private final LayoutInflater inflater;
    private final int layoutResourceId;
    private List<T> items;
    private SparseBooleanArray selectedItems;

    /**
     * @param context
     * @param data
     * @param layoutResourceId
     */
    public BaseCheckableAdapter(final Context context, final List<T> data, final int layoutResourceId) {
        inflater = LayoutInflater.from(context);
        items = data;
        this.layoutResourceId = layoutResourceId;
        selectedItems = new SparseBooleanArray();
    }

    /**
     * {@inheritDoc}
     */
    public void clearSelections() {
        selectedItems = new SparseBooleanArray();

        notifyDataSetChanged();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        if (items == null) {
            return 0;
        }

        return items.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getItem(final int position) {
        if (items == null) {
            return null;
        }

        if (position > items.size()) {
            return null;
        }

        return items.get(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getItemId(final int position) {
        return position;
    }

    /**
     * {@inheritDoc}
     */
    public int getSelectedCount() {
        return selectedItems.size();
    }

    /**
     * {@inheritDoc}
     */
    public SparseBooleanArray getSelectedItems() {
        return selectedItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            final Object viewHolder;

            convertView = inflater.inflate(layoutResourceId, parent, false);

            if (convertView != null) {
                viewHolder = getViewHolder(convertView);
                if (viewHolder != null) {
                    convertView.setTag(viewHolder);
                }
            }
        }

        bindView(convertView != null ? convertView.getTag() : null);

        return convertView;
    }

    /**
     * {@inheritDoc}
     */
    public void select(final int position, final boolean isSelected) {
        if (isSelected) {
            selectedItems.put(position, true);
        }
        else {
            selectedItems.delete(position);
        }

        notifyDataSetChanged();
    }

    /**
     * {@inheritDoc}
     */
    public void toggle(final int position) {
        select(position, selectedItems.get(position));
    }

    /**
     * @param viewHolder
     */
    protected abstract void bindView(final Object viewHolder);

    /**
     * @param view
     * @return
     */
    protected abstract Object getViewHolder(final View view);
}