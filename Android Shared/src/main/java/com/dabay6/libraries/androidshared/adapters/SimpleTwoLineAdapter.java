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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.dabay6.libraries.androidshared.util.ArrayUtils;
import com.dabay6.libraries.androidshared.util.ListUtils;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * SimpleTwoLineAdapter
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class SimpleTwoLineAdapter extends BaseAdapter {
    private final static String DATA_KEY = "Line2";
    private final static String LABEL_KEY = "Line1";
    private final LayoutInflater inflater;
    private final int layoutResourceId;
    private ArrayList<Map<String, String>> items;

    /**
     * @param context
     */
    public SimpleTwoLineAdapter(final Context context) {
        this(context, new ArrayList<Map<String, String>>());
    }

    /**
     * @param context
     * @param data
     */
    public SimpleTwoLineAdapter(final Context context, final ArrayList<Map<String, String>> data) {
        this(context, data, android.R.layout.simple_list_item_2);
    }

    /**
     * @param context
     * @param data
     * @param layoutResourceId
     */
    public SimpleTwoLineAdapter(final Context context, final ArrayList<Map<String, String>> data,
                                final int layoutResourceId) {
        inflater = LayoutInflater.from(context);
        items = data;
        this.layoutResourceId = layoutResourceId;
    }

    /**
     * @param label
     * @param data
     */
    @SuppressWarnings("unchecked")
    public void add(final String label, final String data) {
        add(createItem(label, data));
    }

    /**
     * @param data
     */
    public void add(final Map<String, String>... data) {
        if (!ArrayUtils.isEmpty(data)) {
            add(ListUtils.asList(data));
        }
    }

    /**
     * @param data
     */
    public void add(final ArrayList<Map<String, String>> data) {
        if (items == null) {
            items = new ArrayList<Map<String, String>>();
        }

        if (!ListUtils.isEmpty(data)) {
            items.addAll(data);
        }

        notifyDataSetChanged();
    }

    /**
     *
     */
    public void clear() {
        if (items != null) {
            items.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * @param label
     * @param data
     * @return
     */
    public static Map<String, String> createItem(final String label, final String data) {
        final HashMap<String, String> map = new HashMap<String, String>();

        map.put(LABEL_KEY, label);
        map.put(DATA_KEY, data);

        return map;
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
    public Map<String, String> getItem(final int position) {
        if (items == null || items.size() == 0) {
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
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewsFinder finder;
        final ViewHolder holder;
        final Map<String, String> item = getItem(position);

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(layoutResourceId, parent, false);

            if (convertView != null) {
                finder = new ViewsFinder(convertView);

                holder.data = finder.find(android.R.id.text1);
                holder.label = finder.find(android.R.id.text2);

                convertView.setTag(holder);
            }
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.data.setText(item.get(DATA_KEY));
        holder.label.setText(item.get(LABEL_KEY));

        return convertView;
    }

    static class ViewHolder {
        public TextView data;
        public TextView label;
    }
}