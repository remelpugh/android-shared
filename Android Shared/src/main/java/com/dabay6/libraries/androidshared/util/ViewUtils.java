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

package com.dabay6.libraries.androidshared.util;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * ViewUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class ViewUtils {
    /**
     * Hidden constructor.
     */
    private ViewUtils() {

    }

    /**
     * Clears the text for all the passed in views.
     *
     * @param views The views to be cleared.
     */
    public static void clearText(final TextView... views) {
        for (final TextView view : views) {
            view.setText(StringUtils.empty());
        }
    }

    /**
     * Return the text the TextView is displaying.
     *
     * @param view The {@link EditText} used to retrieve the text.
     * @return The text of the {@link EditText} view, otherwise null.
     */
    @SuppressWarnings("ConstantConditions")
    public static String getText(final EditText view) {
        if (view != null && !TextUtils.isEmpty(view.getText())) {
            return view.getText().toString();
        }

        return null;
    }

    /**
     * Determines if all {@link EditText} views supplied are empty.
     *
     * @param views {@link EditText} views to be checked.
     * @return true if all {@link EditText} views are empty, false otherwise.
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean isEmpty(final EditText... views) {
        for (final EditText view : views) {
            if (view != null) {
                if (!TextUtils.isEmpty(view.getText().toString())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Set visibility of given view to be gone or visible
     * <p>
     * This method has no effect if the view visibility is currently invisible
     * </p>
     *
     * @param view The {@link View} to displayed or not.
     * @param gone Determines if the view is displayed.
     * @return view
     */
    @SuppressWarnings("UnusedReturnValue")
    public static <T extends View> T setGone(final T view, final boolean gone) {
        if (view != null) {
            if (gone) {
                if (View.GONE != view.getVisibility()) {
                    view.setVisibility(View.GONE);
                }
            } else {
                if (View.VISIBLE != view.getVisibility()) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
        return view;
    }

    /**
     * Set visibility of given view to be invisible or visible
     * <p>
     * This method has no effect if the view visibility is currently gone
     * </p>
     *
     * @param view      The {@link View} whose visibility will changed.
     * @param invisible Determines if the view is visible.
     * @return view
     */
    @SuppressWarnings("UnusedReturnValue")
    public static <T extends View> T setInvisible(final T view, final boolean invisible) {
        if (view != null) {
            if (invisible) {
                if (View.INVISIBLE != view.getVisibility()) {
                    view.setVisibility(View.INVISIBLE);
                }
            } else {
                if (View.VISIBLE != view.getVisibility()) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
        return view;
    }
}