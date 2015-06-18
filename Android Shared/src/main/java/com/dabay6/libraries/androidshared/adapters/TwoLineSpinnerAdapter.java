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

package com.dabay6.libraries.androidshared.adapters;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

/**
 * @author Remel Pugh
 */
@SuppressWarnings("unused")
public abstract class TwoLineSpinnerAdapter extends CursorAdapter {
    private final Context context;
    private final LayoutInflater inflater;
    private int dropDownLayout;
    private int spinnerLayout;
    private String title;

    /**
     * <p>Default constructor.</p>
     *
     * @param context The context
     * @param cursor  The cursor from which to get the data.
     * @param flags   Flags used to determine the behavior of the adapter; may be any combination of {@link
     *                #FLAG_AUTO_REQUERY} and {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public TwoLineSpinnerAdapter(final Context context, final Cursor cursor, final int flags) {
        this(context, R.layout.util__toolbar_spinner_item_2, R.layout.util__toolbar_spinner_dropdown, cursor, flags);
    }

    /**
     * <p>Default constructor.</p>
     *
     * @param context       The context
     * @param spinnerLayout The resource id of the layout to be used for the {@link android.widget.Spinner}.
     * @param cursor        The cursor from which to get the data.
     * @param flags         Flags used to determine the behavior of the adapter; may be any combination of {@link
     *                      #FLAG_AUTO_REQUERY} and {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public TwoLineSpinnerAdapter(final Context context, final int spinnerLayout, final Cursor cursor, final int flags) {
        this(context, spinnerLayout, R.layout.util__toolbar_spinner_dropdown, cursor, flags);
    }

    /**
     * <p>Default constructor.</p>
     *
     * @param context        The context
     * @param spinnerLayout  The resource id of the layout to be used for the {@link android.widget.Spinner}.
     * @param dropDownLayout The resource id of the layout to be used for the {@link android .widget.Spinner} drop
     *                       down.
     * @param cursor         The cursor from which to get the data.
     * @param flags          Flags used to determine the behavior of the adapter; may be any combination of {@link
     *                       #FLAG_AUTO_REQUERY} and {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public TwoLineSpinnerAdapter(final Context context, final int spinnerLayout, final int dropDownLayout,
                                 final Cursor cursor, final int flags) {
        super(context, cursor, flags);

        this.context = context;
        this.dropDownLayout = R.layout.util__toolbar_spinner_dropdown;
        inflater = LayoutInflater.from(context);
        this.spinnerLayout = spinnerLayout;
        title = context.getString(R.string.app_name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        final TextView text1;
        final TextView text2;
        final ViewsFinder finder = new ViewsFinder(view);
        boolean isDropDown = false;

        text1 = finder.find(getText1ResourceId());
        text2 = finder.find(getText2ResourceId());

        if (!TextUtils.isEmpty(this.title)) {
            text1.setText(this.title);
        }

        if (finder.find(R.id.spinner_indicator) != null) {
            if (text1 == null && text2 == null) {
                throw new IllegalStateException("Unable to find text1 and text2 TextViews.");
            }
        }
        else {
            isDropDown = true;
            if (text1 == null) {
                throw new IllegalStateException("Unable to find drop down TextView");
            }
        }

        if (isDropDown) {
            setDropDownItemText(cursor, text1);
            return;
        }

        setTextViews(cursor, text1, text2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View newDropDownView(final Context context, final Cursor cursor, final ViewGroup parent) {
        return inflater.inflate(dropDownLayout, parent, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
        return inflater.inflate(spinnerLayout, parent, false);
    }

    /**
     * <p>Sets the layout resource of the drop down views.</p>
     *
     * @param dropDownLayout the layout resources used to createItems drop down views
     */
    public void setDropDownViewResource(final int dropDownLayout) {
        this.dropDownLayout = dropDownLayout;
    }

    /**
     * <p>Set the title of the spinner.</p>
     *
     * @param title The title
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * <p>Set the title of the spinner.</p>
     *
     * @param resourceId The resource id of the title.
     */
    public void setTitle(final int resourceId) {
        setTitle(context.getString(resourceId));
    }

    /**
     * Get the resource id of the title {@link android.widget.TextView}. Default is android.R.id.text1;
     *
     * @return The resource id.
     */
    protected int getText1ResourceId() {
        return android.R.id.text1;
    }

    /**
     * Get the resource id of the subtitle {@link android.widget.TextView}. Default is android.R.id.text2;
     *
     * @return The resource id.
     */
    protected int getText2ResourceId() {
        return android.R.id.text2;
    }

    /**
     * @param cursor
     * @param text
     */
    protected abstract void setDropDownItemText(final Cursor cursor, final TextView text);

    /**
     * @param cursor
     * @param text1
     * @param text2
     */
    protected abstract void setTextViews(final Cursor cursor, final TextView text1, final TextView text2);
}