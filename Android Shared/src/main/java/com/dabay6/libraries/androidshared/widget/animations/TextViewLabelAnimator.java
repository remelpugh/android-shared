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

package com.dabay6.libraries.androidshared.widget.animations;

import android.view.View;
import android.widget.TextView;

/**
 * @author Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014
 */
@SuppressWarnings("unused")
public class TextViewLabelAnimator<T extends TextView> extends DefaultLabelAnimator<T> {

    /**
     * {@inheritDoc}
     */
    public TextViewLabelAnimator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public TextViewLabelAnimator(float alphaAnchored, float alphaFloating, float scaleAnchored, float scaleFloating) {
        super(alphaAnchored, alphaFloating, scaleAnchored, scaleFloating);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected float getTargetX(T inputWidget, View label, boolean isAnchored) {
        float x = inputWidget.getLeft();

        // Add left drawable size and padding when anchored
        if (isAnchored) {
            x += inputWidget.getCompoundPaddingLeft();
        }
        else {
            x += inputWidget.getPaddingLeft();
        }

        return x;
    }

    protected float getTargetY(T inputWidget, View label, boolean isAnchored) {
        if (isAnchored) {
            int lineHeight = inputWidget.getLineHeight();
            int lineCount = inputWidget.getLineCount();
            float targetY = inputWidget.getBottom() - inputWidget.getPaddingBottom() - label.getHeight();

            if (lineCount > 1) {
                targetY -= (lineCount - 1) * lineHeight;
            }

            return targetY;
        }

        final float targetScale = getTargetScale(inputWidget, label, false);

        return inputWidget.getTop() - targetScale * label.getHeight();
    }
}
