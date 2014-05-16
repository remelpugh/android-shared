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

/**
 *
 */
package com.dabay6.libraries.androidshared.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.R.style;
import com.dabay6.libraries.androidshared.R.styleable;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

/**
 * ButtonBar
 * <p>
 * A button bar is a set of buttons at the bottom of an activity. An example is an AlertDialog with OK/Cancel buttons.
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class ButtonBar extends FrameLayout implements OnClickListener {
    private final static String TAG = Logger.makeTag(ButtonBar.class);
    private Button negative;
    private OnButtonBarClickListener onButtonBarClickListener;
    private Button positive;

    /**
     * @param context
     */
    public ButtonBar(final Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attributes
     */
    public ButtonBar(final Context context, final AttributeSet attributes) {
        this(context, attributes, R.attr.util__buttonBarStyle);
    }

    /**
     * @param context
     * @param attributes
     * @param defStyle
     */
    public ButtonBar(final Context context, final AttributeSet attributes, final int defStyle) {
        super(context, attributes, defStyle);

        initialize(context, attributes, defStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(final View view) {
        if (onButtonBarClickListener != null) {
            final int id = view.getId();

            if (id == R.id.negative) {
                onButtonBarClickListener.onNegativeButtonClick();
            }

            if (id == R.id.positive) {
                onButtonBarClickListener.onPositiveButtonClick();
            }
        }
    }

    /**
     * @param drawableResId
     */
    public void setNegativeButtonIcon(final int drawableResId) {
        final Drawable drawable = getContext().getResources().getDrawable(drawableResId);

        negative.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        refreshView();
    }

    /**
     * @param listener
     */
    public void setOnButtonBarClickListener(final OnButtonBarClickListener listener) {
        this.onButtonBarClickListener = listener;
    }

    /**
     * Set the enabled state of this view.
     *
     * @param enabled true if this view is enabled, false otherwise.
     */
    public void setPositiveButtonEnabled(final boolean enabled) {
        positive.setEnabled(enabled);
        refreshView();
    }

    /**
     * @param drawableResId
     */
    public void setPositiveButtonIcon(final int drawableResId) {
        final Drawable drawable = getContext().getResources().getDrawable(drawableResId);

        positive.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        refreshView();
    }

    /**
     * @param textResId
     */
    public void setPositiveButtonText(final int textResId) {
        setPositiveButtonText(getContext().getString(textResId));
    }

    /**
     * @param text
     */
    public void setPositiveButtonText(final String text) {
        positive.setText(text);
        refreshView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void dispatchRestoreInstanceState(final SparseArray<Parcelable> container) {
        super.dispatchThawSelfOnly(container);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void dispatchSaveInstanceState(final SparseArray<Parcelable> container) {
        super.dispatchFreezeSelfOnly(container);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        final SavedState savedState = (SavedState) state;

        super.onRestoreInstanceState(savedState.getSuperState());

        setPositiveButtonEnabled(savedState.getEnabled());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();

        return new SavedState(superState, positive.isEnabled());
    }

    private void configureButton(final TypedArray array, final ButtonType type) {
        Button button = null;
        Drawable drawable = null;
        String text = null;

        switch (type) {
            case Negative: {
                button = negative;
                drawable = array.getDrawable(styleable.ButtonBar_negativeButtonIcon);
                text = array.getString(styleable.ButtonBar_negativeButtonLabel);
                break;
            }
            case Positive: {
                button = positive;
                drawable = array.getDrawable(styleable.ButtonBar_positiveButtonIcon);
                text = array.getString(styleable.ButtonBar_positiveButtonLabel);


                button.setEnabled(array.getBoolean(styleable.ButtonBar_positiveButtonEnabled, false));
                break;
            }
        }

        if (button == null) {
            return;
        }

        if (drawable != null) {
            button.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }

        button.setOnClickListener(this);

        button.setText(text);

    }

    /**
     * @param context
     * @param attributes
     * @param defStyle
     */
    private void initialize(final Context context, final AttributeSet attributes, final int defStyle) {
        final TypedArray array = context.obtainStyledAttributes(attributes, R.styleable.ButtonBar, defStyle,
                                                                style.utils_Widget_ButtonBar);

        try {
            LayoutInflater.from(context).inflate(R.layout.util__merge_button_bar, this, true);

            if (isInEditMode()) {
                // do nothing
            }
            else {
                final ViewsFinder finder = new ViewsFinder(this);

                positive = finder.find(R.id.positive);
                negative = finder.find(R.id.negative);

                configureButton(array, ButtonType.Negative);
                configureButton(array, ButtonType.Positive);
            }
        }
        catch (final Exception ignored) {
        }
        finally {
            array.recycle();
        }
    }

    /**
     *
     */
    private void refreshView() {
        invalidate();
        requestLayout();
    }

    /**
     *
     */
    protected enum ButtonType {
        /**
         *
         */
        Negative,
        /**
         *
         */
        Positive
    }

    /**
     *
     */
    protected static class SavedState extends BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(final Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(final int size) {
                return new SavedState[size];
            }

        };
        private final boolean enabled;

        private SavedState(final Parcel in) {
            super(in);

            enabled = in.readByte() == 1;
        }

        private SavedState(final Parcelable superState, final boolean enabled) {
            super(superState);

            this.enabled = enabled;
        }

        public boolean getEnabled() {
            return enabled;
        }

        @Override
        public void writeToParcel(final Parcel destination, final int flags) {
            super.writeToParcel(destination, flags);

            destination.writeByte((byte) ((enabled) ? 1 : 0));
        }
    }
}