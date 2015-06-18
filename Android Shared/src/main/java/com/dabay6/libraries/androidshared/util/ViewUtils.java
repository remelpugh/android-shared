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

package com.dabay6.libraries.androidshared.util;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dabay6.libraries.androidshared.widget.FloatLabelAutoCompleteTextView;
import com.dabay6.libraries.androidshared.widget.FloatLabelEditText;
import com.dabay6.libraries.androidshared.widget.FloatLabelTextViewBase;

/**
 * ViewUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class ViewUtils {
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
    public static void clearText(final View... views) {
        for (final View view : views) {
            if (view instanceof FloatLabelTextViewBase) {
                clearText(((FloatLabelTextViewBase) view).getInput());
                continue;
            }
            if (view instanceof TextView) {
                ((TextView) view).setText(StringUtils.empty());
            }
        }
    }

    /**
     * Return the text the TextView is displaying.
     *
     * @param view The {@link EditText} used to retrieve the text.
     *
     * @return The text of the {@link EditText} view, otherwise null.
     */
    public static String getText(final EditText view) {
        if (view != null && !TextUtils.isEmpty(view.getText())) {
            return view.getText().toString();
        }

        return null;
    }

    /**
     * Return the text the TextView is displaying.
     *
     * @param view The {@link FloatLabelEditText} used to retrieve the text.
     *
     * @return The text of the {@link FloatLabelEditText} view, otherwise null.
     */
    public static String getText(final FloatLabelAutoCompleteTextView view) {
        if (view != null && !TextUtils.isEmpty(view.getText())) {
            return view.getText().toString();
        }

        return null;
    }

    /**
     * Return the text the TextView is displaying.
     *
     * @param view The {@link FloatLabelEditText} used to retrieve the text.
     *
     * @return The text of the {@link FloatLabelEditText} view, otherwise null.
     */
    public static String getText(final FloatLabelEditText view) {
        if (view != null && !TextUtils.isEmpty(view.getText())) {
            return view.getText().toString();
        }

        return null;
    }

    /**
     * Determines if all {@link EditText} views supplied are empty.
     *
     * @param views {@link EditText} views to be checked.
     *
     * @return true if all {@link EditText} views are empty, false otherwise.
     */
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
     * Determines if all {@link FloatLabelAutoCompleteTextView} views supplied are empty.
     *
     * @param views {@link FloatLabelAutoCompleteTextView} views to be checked.
     *
     * @return true if all {@link FloatLabelAutoCompleteTextView} views are empty, false otherwise.
     */
    public static boolean isEmpty(final FloatLabelAutoCompleteTextView... views) {
        for (final FloatLabelAutoCompleteTextView view : views) {
            if (view != null) {
                if (!TextUtils.isEmpty(view.getText().toString())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Determines if all {@link FloatLabelEditText} views supplied are empty.
     *
     * @param views {@link FloatLabelEditText} views to be checked.
     *
     * @return true if all {@link FloatLabelEditText} views are empty, false otherwise.
     */
    public static boolean isEmpty(final FloatLabelEditText... views) {
        for (final FloatLabelEditText view : views) {
            if (view != null) {
                if (!TextUtils.isEmpty(view.getText().toString())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Determine if a view is visible.
     *
     * @param view The {@link View} used to determine visibility.
     *
     * @return True if visible, otherwise false.
     */
    public static <T extends View> boolean isVisible(final T view) {
        if (view != null) {
            final int visibility = view.getVisibility();

            return visibility == View.VISIBLE;
        }

        return false;
    }

    /**
     * Set visibility of given view to be gone.
     *
     * @param view The {@link android.view.View} whose visibility will changed.
     *
     * @return {@link android.view.View}
     */
    public static <T extends View> T setGone(final T view) {
        if (view != null) {
            if (View.GONE != view.getVisibility()) {
                view.setVisibility(View.GONE);
            }
        }

        return view;
    }

    /**
     * Set visibility of given view to be invisible.
     *
     * @param view The {@link android.view.View} whose visibility will changed.
     *
     * @return {@link android.view.View}
     */
    public static <T extends View> T setInvisible(final T view) {
        if (view != null) {
            if (View.INVISIBLE != view.getVisibility()) {
                view.setVisibility(View.INVISIBLE);
            }
        }

        return view;
    }

    /**
     * Set visibility of given view to be visible.
     *
     * @param view The {@link android.view.View} whose visibility will changed.
     *
     * @return {@link android.view.View}
     */
    public static <T extends View> T setVisible(final T view) {
        if (view != null) {
            if (View.VISIBLE != view.getVisibility()) {
                view.setVisibility(View.VISIBLE);
            }
        }

        return view;
    }
}