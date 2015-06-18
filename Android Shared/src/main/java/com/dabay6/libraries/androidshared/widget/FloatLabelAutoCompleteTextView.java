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

package com.dabay6.libraries.androidshared.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.AndroidUtils;
import com.dabay6.libraries.androidshared.widget.animations.TextViewLabelAnimator;
import com.dabay6.libraries.androidshared.widget.interfaces.EditTextListener;
import com.dabay6.libraries.androidshared.widget.interfaces.LabelAnimator;

import hugo.weaving.DebugLog;

/**
 * FloatLabelAutoCompleteTextView
 *
 * @author Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014
 */
@SuppressWarnings("unused")
public class FloatLabelAutoCompleteTextView extends FloatLabelTextViewBase<AutoCompleteTextView> {
    private final static String TAG = Logger.makeTag(FloatLabelAutoCompleteTextView.class);
    protected EditTextListener editTextListener;

    /**
     * {@inheritDoc}
     */
    public FloatLabelAutoCompleteTextView(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    public FloatLabelAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * {@inheritDoc}
     */
    public FloatLabelAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Delegate method for the input widget
     */
    public void addTextChangedListener(TextWatcher watcher) {
        getInput().addTextChangedListener(watcher);
    }

    public EditTextListener getEditTextListener() {
        return editTextListener;
    }

    public void setEditTextListener(EditTextListener editTextListener) {
        this.editTextListener = editTextListener;
    }

    /**
     * Delegate method for the input widget
     */
    public Editable getText() {
        return getInput().getText();
    }

    /**
     * Delegate method for the input widget
     */
    public void setText(CharSequence text) {
        getInput().setText(text);
    }

    /**
     * Delegate method for the input widget
     */
    public void removeTextChangedListener(TextWatcher watcher) {
        getInput().removeTextChangedListener(watcher);
    }

    /**
     * Delegate method for the input widget
     */
    public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {
        getInput().setAdapter(adapter);
    }

    /**
     * Delegate method for the input widget
     */
    public void setInputType(int type) {
        getInput().setInputType(type);
    }

    /**
     * Delegate method for the input widget
     */
    public void setKeyListener(KeyListener input) {
        getInput().setKeyListener(input);
    }

    /**
     * @param listener
     */
    public void setOnEditorActionListener(final TextView.OnEditorActionListener listener) {
        getInput().setOnEditorActionListener(listener);
    }

    /**
     * <p>Sets the listener that will be notified when the user clicks an item in the drop down list.</p>
     *
     * @param listener the item click listener
     */
    public void setOnItemClickListener(final AdapterView.OnItemClickListener listener) {
        getInput().setOnItemClickListener(listener);
    }

    /**
     * Delegate method for the input widget
     */
    public void setText(int resId) {
        getInput().setText(resId);
    }

    /**
     * Delegate method for the input widget
     */
    public void setText(CharSequence text, TextView.BufferType type) {
        getInput().setText(text, type);
    }

    /**
     * Like {@link #setText(CharSequence)}, except that it can disable filtering.
     *
     * @param filter If <code>false</code>, no filtering will be performed as a result of this call.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setText(final CharSequence text, final boolean filter) {
        if (AndroidUtils.isAtLeastJellyBeanMR1()) {
            getInput().setText(text, filter);
        }
        else {
            setText(text);
        }
    }

    /**
     * Delegate method for the input widget
     */
    public void setTextColor(int color) {
        getInput().setTextColor(color);
    }

    /**
     * Delegate method for the input widget
     */
    public void setTextColor(ColorStateList colors) {
        getInput().setTextColor(colors);
    }

    /**
     * Delegate method for the input widget
     */
    public void setTextSize(float size) {
        getInput().setTextSize(size);
    }

    /**
     * Delegate method for the input widget
     */
    public void setTextSize(int unit, float size) {
        getInput().setTextSize(unit, size);
    }

    /**
     * Delegate method for the input widget
     */
    public void setThreshold(int threshold) {
        getInput().setThreshold(threshold);
    }

    /**
     * Delegate method for the input widget
     */
    public void setTypeface(Typeface tf, int style) {
        getInput().setTypeface(tf, style);
    }

    @Override
    protected void afterLayoutInflated(Context context, AttributeSet attrs, int defStyle) {
        super.afterLayoutInflated(context, attrs, defStyle);

        final TypedArray array =
                context.obtainStyledAttributes(attrs, R.styleable.FloatLabelAutoCompleteTextView, defStyle,
                        R.style.utils_Widget_FloatLabelAutoCompleteTextView);
        final CharSequence completionHint;
        final int completionThreshold;
        final int dropDownHeight;
        final int dropDownWidth;
        final AutoCompleteTextView input = getInput();
        final int popupBackground;

//        if (attrs == null) {
//            completionHint = "";
//            completionThreshold = 1;
//            dropDownHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
//            dropDownWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
//            popupBackground = getDefaultPopupBackgroundResId();
//        }
//        else {
//        }

        completionHint = array.getText(R.styleable.FloatLabelAutoCompleteTextView_android_completionHint);
        completionThreshold =
                array.getInt(R.styleable.FloatLabelAutoCompleteTextView_android_completionThreshold, 1);
        dropDownHeight =
                array.getDimensionPixelSize(R.styleable.FloatLabelAutoCompleteTextView_android_dropDownHeight,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        dropDownWidth = array.getDimensionPixelSize(R.styleable.FloatLabelAutoCompleteTextView_android_dropDownWidth,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupBackground = array.getResourceId(R.styleable.FloatLabelAutoCompleteTextView_android_popupBackground,
                getDefaultPopupBackgroundResId());
        array.recycle();

        input.addTextChangedListener(new EditTextWatcher());
        input.setCompletionHint(completionHint);
        input.setDropDownHeight(dropDownHeight);
        input.setDropDownWidth(dropDownWidth);
        input.setThreshold(completionThreshold);

        if (popupBackground != 0) {
            input.setDropDownBackgroundResource(popupBackground);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected LabelAnimator<AutoCompleteTextView> getDefaultLabelAnimator() {
        return new TextViewLabelAnimator<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getDefaultLayoutId() {
        return R.layout.util__float_label_autocompletetextview;
    }

    protected int getDefaultPopupBackgroundResId() {
        //return R.drawable.bg_dropdown_panel;
        return 0;
    }

    /**
     * Called when the text within the input widget is updated
     *
     * @param s The new text
     */
    @DebugLog
    protected void onTextChanged(String s) {
        if (s.trim().length() == 0) {
            anchorLabel();
        }
        else {
            floatLabel();
        }

        if (editTextListener != null) {
            //noinspection unchecked
            editTextListener.onTextChanged(this, s);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void restoreState(Parcelable state) {
        getInput().onRestoreInstanceState(state);
        // setLabelAnchored(isEditTextEmpty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Parcelable saveInstanceState() {
        return getInput().onSaveInstanceState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setInitialWidgetState() {
        setLabelAnchored(isEditTextEmpty());
    }

    /**
     * @return true if the input widget is empty
     */
    private boolean isEditTextEmpty() {
        return getInput().getText().toString().isEmpty();
    }

    /**
     * TextWatcher that changes the floating label state when the EditText content changes between empty and not empty.
     */
    private class EditTextWatcher implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
            FloatLabelAutoCompleteTextView.this.onTextChanged(s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }
}
