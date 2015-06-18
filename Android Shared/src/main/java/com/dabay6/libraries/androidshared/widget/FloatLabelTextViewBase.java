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
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.logging.Logger;

/**
 * @author Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014
 */
@SuppressWarnings("unused")
public abstract class FloatLabelTextViewBase<T extends TextView> extends FloatLabelBase<T> {
    private final static String TAG = Logger.makeTag(FloatLabelTextViewBase.class);

    /**
     * {@inheritDoc}
     */
    public FloatLabelTextViewBase(Context context) {
        super(context, null, 0);
    }

    /**
     * {@inheritDoc}
     */
    public FloatLabelTextViewBase(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    /**
     * {@inheritDoc}
     */
    public FloatLabelTextViewBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void afterLayoutInflated(Context context, AttributeSet attrs, int defStyle) {
        super.afterLayoutInflated(context, attrs, defStyle);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FloatLabelTextViewBase,
                defStyle, R.style.utils_Widget_FloatLabelTextViewBase);
        final int drawableRightId;
        final int drawableLeftId;
        final int drawablePadding;
        final TextView input = getInput();
        final int imeOptions;
        final int textColor;
        final float textSize;

        drawablePadding =
                array.getDimensionPixelSize(R.styleable.FloatLabelTextViewBase_android_drawablePadding, 0);
        drawableLeftId = array.getResourceId(R.styleable.FloatLabelTextViewBase_android_drawableLeft,
                getDefaultDrawableLeftResId());
        drawableRightId = array.getResourceId(R.styleable.FloatLabelTextViewBase_android_drawableRight,
                getDefaultDrawableRightResId());

        imeOptions = array.getInt(R.styleable.FloatLabelTextViewBase_android_imeOptions, EditorInfo.IME_ACTION_NEXT);

        textColor = array.getColor(R.styleable.FloatLabelTextViewBase_util__inputTextColor,
                R.color.util__float_label_input);
        textSize = array.getDimension(R.styleable.FloatLabelTextViewBase_util__inputTextSize,
                getResources().getDimensionPixelSize(R.dimen.util__defaultInputTextSize));

        array.recycle();
//        if (attrs == null) {
//            textColor = R.color.util__float_label_input;
//            textSize = getResources().getDimensionPixelSize(R.dimen.util__defaultInputTextSize);
//            drawableLeftId = getDefaultDrawableLeftResId();
//            drawableRightId = getDefaultDrawableRightResId();
//            drawablePadding = 0;
//        }
//        else {
//        }

        Logger.debug(TAG, "IMEOPTIONS: %d", imeOptions);
        input.setCompoundDrawablePadding(drawablePadding);
        input.setCompoundDrawablesWithIntrinsicBounds(drawableLeftId, 0, drawableRightId, 0);
        input.setImeOptions(imeOptions);
        input.setTextColor(textColor);
        input.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    /**
     * @return
     */
    protected int getDefaultDrawableLeftResId() {
        return 0;
    }

    /**
     * @return
     */
    protected int getDefaultDrawableRightResId() {
        return 0;
    }
}
