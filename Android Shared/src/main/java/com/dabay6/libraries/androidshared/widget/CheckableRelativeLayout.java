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

package com.dabay6.libraries.androidshared.widget;

import android.R.attr;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.RelativeLayout;
import com.dabay6.libraries.androidshared.R.drawable;
import com.dabay6.libraries.androidshared.logging.Logger;

/**
 * CheckableRelativeLayout
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class CheckableRelativeLayout extends RelativeLayout implements Checkable {
    private static final int[] ACTIVATED_STATE_SET = {attr.state_checked};
    private final static String TAG = Logger.makeTag(CheckableRelativeLayout.class);
    private boolean isChecked = false;

    /**
     * @param context
     */
    public CheckableRelativeLayout(final Context context) {
        this(context, null);
        initialize();
    }

    /**
     * @param context
     * @param attributes
     */
    public CheckableRelativeLayout(final Context context, final AttributeSet attributes) {
        super(context, attributes);
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setChecked(final boolean checked) {
        if (isChecked != checked) {
            isChecked = checked;

            setCheckedRecursive(this, checked);
            refreshDrawableState();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onInterceptTouchEvent(final MotionEvent ev) {
        return onTouchEvent(ev);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performClick() {
        toggle();

        return super.performClick();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void drawableStateChanged() {
        final Drawable drawable = getBackground();

        super.drawableStateChanged();

        if (drawable != null) {
            final int[] drawableState = getDrawableState();

            drawable.setState(drawableState);

            invalidate();
        }
    }

    /**
     * @param extraSpace
     * @return
     */
    @Override
    protected int[] onCreateDrawableState(final int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

        if (isChecked()) {
            mergeDrawableStates(drawableState, ACTIVATED_STATE_SET);
        }

        return drawableState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        final SavedState savedState = (SavedState) state;

        super.onRestoreInstanceState(savedState.getSuperState());

        setChecked(savedState.isChecked);

        requestLayout();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        final SavedState state = new SavedState(super.onSaveInstanceState());

        state.isChecked = isChecked();

        return state;
    }

    /**
     *
     */
    private void initialize() {
        if (getBackground() == null) {
            setBackgroundResource(drawable.util__list_item_selector);
        }
    }

    /**
     * @param parent
     * @param checked
     */
    private void setCheckedRecursive(final ViewGroup parent, final boolean checked) {
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            final View view = parent.getChildAt(i);

            if (view instanceof Checkable) {
                ((Checkable) view).setChecked(checked);
            }

            if (view instanceof ViewGroup) {
                setCheckedRecursive((ViewGroup) view, checked);
            }
        }
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(final Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(final int size) {
                return new SavedState[size];
            }
        };
        boolean isChecked;

        /**
         * Constructor used when reading from a parcel. Reads the state of the superclass.
         */
        public SavedState(final Parcel source) {
            super(source);

            final Boolean value = (Boolean) source.readValue(null);

            if (value == null) {
                isChecked = false;
            }
            else {
                isChecked = value;
            }
        }

        /**
         * Constructor called by derived classes when creating their SavedState objects
         *
         * @param superState The state of the superclass of this view
         */
        public SavedState(final Parcelable superState) {
            super(superState);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("NullableProblems")
        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            super.writeToParcel(dest, flags);

            dest.writeValue(isChecked);
        }
    }
}