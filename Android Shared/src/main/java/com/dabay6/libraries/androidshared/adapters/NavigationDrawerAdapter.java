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
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION_CODES;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.dabay6.libraries.androidshared.R.layout;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.AndroidUtils;
import com.dabay6.libraries.androidshared.util.ListUtils;
import com.dabay6.libraries.androidshared.util.StringUtils;

import java.util.List;

/**
 * NavigationDrawerAdapter
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class NavigationDrawerAdapter extends BaseAdapter {
    private final static String TAG = Logger.makeTag(NavigationDrawerAdapter.class);
    private final Context context;
    private List<BaseNavigationListItem> items;

    /**
     * @param context
     */
    public NavigationDrawerAdapter(final Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param items
     */
    public NavigationDrawerAdapter(final Context context, final List<BaseNavigationListItem> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * @param title
     */
    public void add(final CharSequence title) {
        add(title, null);
    }

    /**
     * @param title
     * @param iconResourceId
     */
    public void add(final CharSequence title, final Integer iconResourceId) {
        ensureItems();

        items.add(new NavigationDrawerItem(title, iconResourceId));
        notifyDataSetChanged();
    }

    /**
     * @param items
     */
    public void addAll(final List<BaseNavigationListItem> items) {
        ensureItems();

        this.items = items;
        notifyDataSetChanged();
    }

    /**
     * @param title
     */
    public void addCategory(final CharSequence title) {
        ensureItems();

        items.add(new NavigationDrawerCategory(title));
        notifyDataSetChanged();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    /**
     *
     */
    public void clear() {
        items.clear();
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
    public BaseNavigationListItem getItem(final int position) {
        if (items == null) {
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
    public int getItemViewType(final int position) {
        return getItem(position) instanceof NavigationDrawerItem ? 0 : 1;
    }

    /**
     * @return
     */
    public List<BaseNavigationListItem> getItems() {
        return items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final BaseNavigationListItem listItem = getItem(position);
        final int layoutResourceId;
        final CharSequence title = listItem.getTitle();
        final TextView text;
        View view = convertView;

        layoutResourceId = (listItem instanceof NavigationDrawerCategory) ? layout.util__menu_row_category
                                                                          : layout.util__menu_row_item;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(layoutResourceId, parent, false);
        }

        text = (TextView) view;
        if (text != null) {
            text.setText(title);
        }

        if (AndroidUtils.isVersionBefore(VERSION_CODES.ICE_CREAM_SANDWICH) &&
            listItem instanceof NavigationDrawerCategory && text != null) {
            text.setText(StringUtils.toUpperCase(text.getText()));
        }

        if (listItem instanceof NavigationDrawerItem) {
            final NavigationDrawerItem item = (NavigationDrawerItem) listItem;

            if (item.getIconRes() != null) {
                final Drawable drawable = context.getResources().getDrawable(item.getIconRes());
                final TextView menuItemView;

                menuItemView = (TextView) view;
                if (menuItemView != null) {
                    menuItemView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                    menuItemView.setPadding(8, menuItemView.getPaddingTop(), menuItemView.getPaddingRight(),
                                            menuItemView.getPaddingBottom());
                }
            }
        }

        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled(final int position) {
        return getItem(position) instanceof NavigationDrawerItem;
    }

    private void ensureItems() {
        if (items == null) {
            items = ListUtils.newList();
        }
    }
}