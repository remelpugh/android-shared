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

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.widget.animations.TextViewLabelAnimator;
import com.dabay6.libraries.androidshared.widget.interfaces.EditTextListener;
import com.dabay6.libraries.androidshared.widget.interfaces.LabelAnimator;

/**
 * FloatLabelEditText
 *
 * @author Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014
 */
@SuppressWarnings("unused")
public class FloatLabelEditText extends FloatLabelTextViewBase<EditText> {
    private final static String TAG = Logger.makeTag(FloatLabelEditText.class);
    protected EditTextListener editTextListener;

    /**
     * {@inheritDoc}
     */
    public FloatLabelEditText(Context context) {
        super(context);
    }

    /**
     * {@inheritDoc}
     */
    public FloatLabelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * {@inheritDoc}
     */
    public FloatLabelEditText(Context context, AttributeSet attrs, int defStyle) {
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
     * Register a callback to be invoked when focus of this view changed.
     *
     * @param listener The callback that will run.
     */
    public void setOnFocusChangeListener(OnFocusChangeListener listener) {
        getInput().setOnFocusChangeListener(listener);
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
    public void setTypeface(Typeface tf, int style) {
        getInput().setTypeface(tf, style);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void afterLayoutInflated(Context context, AttributeSet attrs, int defStyle) {
        super.afterLayoutInflated(context, attrs, defStyle);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FloatLabelEditText, defStyle,
                R.style.utils_Widget_FloatLabelEditText);
        final EditText input = getInput();
        final int inputType;

        inputType = array.getInt(R.styleable.FloatLabelEditText_android_inputType, InputType.TYPE_CLASS_TEXT);

        array.recycle();

//        if (attrs == null) {
//            inputType = InputType.TYPE_CLASS_TEXT;
//        }
//        else {
//        }

        input.setInputType(inputType);
        input.addTextChangedListener(new EditTextWatcher());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected LabelAnimator<EditText> getDefaultLabelAnimator() {
        return new TextViewLabelAnimator<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getDefaultLayoutId() {
        return R.layout.util__float_label_edittext;
    }

    /**
     * Called when the text within the input widget is updated
     *
     * @param s The new text
     */
    protected void onTextChanged(String s) {
        if (s.length() == 0) {
            anchorLabel();
        }
        else {
            floatLabel();
        }

        if (editTextListener != null) {
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
            FloatLabelEditText.this.onTextChanged(s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Ignored
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }
}