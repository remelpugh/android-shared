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

import android.R.id;
import android.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.dabay6.libraries.androidshared.util.ListUtils;
import com.dabay6.libraries.androidshared.util.StringUtils;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

import java.util.*;

/**
 * DetailsAdapter
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class DetailsAdapter extends BaseAdapter implements Filterable {
    private final Context context;
    private final LayoutInflater inflater;
    private final Object lock = new Object();
    private final int resource;
    private final int[] textViewResourceId;
    private int dropDownResource;
    private DetailsItemFilter filter;
    private DetailsItemList items;
    private boolean notifyOnChange = true;
    private DetailsItemList originalItems;

    /**
     * @param context The current context.
     */
    public DetailsAdapter(final Context context) {
        this(context, new DetailsItemList());
    }

    /**
     * @param context The current context.
     * @param data    The {@link DetailsItem} to represent in the {@link android.widget.ListView}.
     */
    public DetailsAdapter(final Context context, final DetailsItemList data) {
        this(context, data, layout.simple_list_item_2, new int[]{id.text1, id.text2});
    }

    /**
     * @param context  The current context.
     * @param data     The {@link DetailsItem} to represent in the {@link android.widget.ListView}.
     * @param resource The resource ID for a layout file containing a layout to use when
     *                 instantiating views.
     */
    public DetailsAdapter(final Context context, final DetailsItemList data, final int resource) {
        this(context, data, resource, new int[]{id.text1, id.text2});
    }

    /**
     * @param context            The current context.
     * @param data               The {@link DetailsItem} to represent in the {@link android.widget.ListView}.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The resource IDs for a layout file containing 2 TextView to use when
     *                           instantiating views.
     */
    public DetailsAdapter(final Context context, final DetailsItemList data, final int resource,
                          final int[] textViewResourceId) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items = data;
        this.resource = dropDownResource = resource;
        this.textViewResourceId = textViewResourceId;
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param item The item to add at the end of the array.
     */
    public void add(final DetailsItem item) {
        synchronized (lock) {
            if (originalItems != null) {
                originalItems.add(item);
            }
            else {
                items.add(item);
            }
        }
        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param collection The Collection to add at the end of the array.
     */
    public void addAll(final Collection<? extends DetailsItem> collection) {
        synchronized (lock) {
            if (originalItems != null) {
                originalItems.addAll(collection);
            }
            else {
                items.addAll(collection);
            }
        }
        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * Adds the specified items at the end of the array.
     *
     * @param items The items to add at the end of the array.
     */
    public void addAll(final DetailsItem... items) {
        synchronized (lock) {
            if (originalItems != null) {
                Collections.addAll(originalItems, items);
            }
            else {
                Collections.addAll(this.items, items);
            }
        }
        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        synchronized (lock) {
            if (originalItems != null) {
                originalItems.clear();
            }
            else {
                items.clear();
            }
        }
        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * Creates a new DetailsAdapter from external resources.
     *
     * @param context            The current context.
     * @param items              The {@link DetailsItem} to represent in the {@link android.widget.ListView}.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The resource IDs for a layout file containing 2 TextView to use when
     *                           instantiating views.
     *
     * @return An DetailsAdapter
     */
    public static DetailsAdapter createFromResource(final Context context, final DetailsItemList items,
                                                    final int resource, final int[] textViewResourceId) {
        return new DetailsAdapter(context, items, resource, textViewResourceId);
    }

    /**
     * Returns the context associated with this array adapter. The context is used
     * to create views from the resource passed to the constructor.
     *
     * @return The Context associated with this adapter.
     */
    public Context getContext() {
        return context;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /**
     *
     */
    public Filter getFilter() {
        if (filter == null) {
            filter = new DetailsItemFilter();
        }

        return filter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DetailsItem getItem(final int position) {
        if (items.size() == 0) {
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
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     *
     * @return The position of the specified item.
     */
    public int getPosition(final DetailsItem item) {
        return items.indexOf(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, resource);
    }

    /**
     * Inserts the specified item at the specified index in the array.
     *
     * @param item  The item to insert into the array.
     * @param index The index at which the item must be inserted.
     */
    public void insert(final DetailsItem item, final int index) {
        synchronized (lock) {
            if (originalItems != null) {
                originalItems.add(index, item);
            }
            else {
                items.add(index, item);
            }
        }
        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

        notifyOnChange = true;
    }

    /**
     * Removes the specified item from the array.
     *
     * @param item The item to remove.
     */
    public void remove(final DetailsItem item) {
        synchronized (lock) {
            if (originalItems != null) {
                originalItems.remove(item);
            }
            else {
                items.remove(item);
            }
        }
        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    /**
     * <p>Sets the layout resource to create the drop down views.</p>
     *
     * @param resource the layout resource defining the drop down views
     *
     * @see #getDropDownView(int, android.view.View, android.view.ViewGroup)
     */
    public void setDropDownViewResource(final int resource) {
        this.dropDownResource = resource;
    }

    /**
     * Control whether methods that change the list ({@link #add}, {@link #insert}, {@link #remove},
     * {@link #clear}) automatically call {@link #notifyDataSetChanged}.  If set to false, caller must
     * manually call notifyDataSetChanged() to have the changes reflected in the attached view. The default is true,
     * and calling notifyDataSetChanged() resets the flag to true.
     *
     * @param notifyOnChange if true, modifications to the list will automatically call {@link #notifyDataSetChanged}
     */
    public void setNotifyOnChange(final boolean notifyOnChange) {
        this.notifyOnChange = notifyOnChange;
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained
     *                   in this adapter.
     */
    public void sort(final Comparator<? super DetailsItem> comparator) {
        synchronized (lock) {
            if (originalItems != null) {
                Collections.sort(originalItems, comparator);
            }
            else {
                Collections.sort(items, comparator);
            }
        }
        if (notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    private View createViewFromResource(final int position, View convertView, final ViewGroup parent,
                                        final int resource) {
        final ViewsFinder finder;
        final ViewHolder holder;
        final DetailsItem item = getItem(position);

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(resource, parent, false);
            if (convertView != null) {
                finder = new ViewsFinder(convertView);

                holder.label = finder.find(textViewResourceId[0]);
                holder.data = finder.find(textViewResourceId[1]);

                convertView.setTag(holder);
            }
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.data.setText(item.getData());
        holder.label.setText(item.getLabel());

        return convertView;
    }

    /**
     *
     */
    private class DetailsItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(final CharSequence prefix) {
            final FilterResults results = new FilterResults();

            if (originalItems == null) {
                synchronized (lock) {
                    originalItems = new DetailsItemList(items);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                final DetailsItemList list;

                synchronized (lock) {
                    list = new DetailsItemList(originalItems);
                }

                results.values = list;
                results.count = list.size();
            }
            else {
                final ArrayList<DetailsItem> values;
                final String prefixString = StringUtils.toLowerCase(prefix.toString());

                synchronized (lock) {
                    values = new ArrayList<DetailsItem>(originalItems);
                }

                final ArrayList<DetailsItem> newValues = ListUtils.newArrayList();

                for (final DetailsItem value : values) {
                    final String valueText = StringUtils.toLowerCase(value.toString());

                    // First match against the whole, non-split value
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    }
                    else {
                        final String[] words = valueText.split(" ");

                        // Start at index 0, in case valueText starts with space(s)
                        for (final String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(final CharSequence constraint, final FilterResults results) {
            items = (DetailsItemList) results.values;

            if (results.count > 0) {
                notifyDataSetChanged();
            }
            else {
                notifyDataSetInvalidated();
            }
        }
    }

    static class ViewHolder {
        public TextView data;
        public TextView label;
    }
}