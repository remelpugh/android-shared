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
import android.util.AttributeSet;
import android.widget.Button;
import com.dabay6.libraries.androidshared.R;

/**
 * ButtonBarButton
 * <p>
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
class ButtonBarButton extends Button {
    /**
     * @param context
     */
    public ButtonBarButton(final Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attributes
     */
    public ButtonBarButton(final Context context, final AttributeSet attributes) {
        this(context, attributes, R.attr.util__buttonBarStyle);
    }

    /**
     * @param context
     * @param attributes
     * @param defStyle
     */
    public ButtonBarButton(final Context context, final AttributeSet attributes, final int defStyle) {
        super(context, attributes, defStyle);

        initialize(context, attributes, defStyle);
    }

    /**
     * @param context
     * @param attributes
     * @param defStyle
     */
    private void initialize(final Context context, final AttributeSet attributes, final int defStyle) {
    }
}