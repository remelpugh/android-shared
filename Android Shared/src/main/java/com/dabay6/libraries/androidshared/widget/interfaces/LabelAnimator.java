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

package com.dabay6.libraries.androidshared.widget.interfaces;

import android.view.View;

/**
 * Interface for providing custom animations to the label TextView.
 *
 * @author Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014
 */
@SuppressWarnings("unused")
public interface LabelAnimator<T extends View> {

    /**
     * Called when the label should be anchored on top of the input widget
     *
     * @param label TextView to animate
     */
    public void anchorLabel(T inputWidget, View label);

    /**
     * Called when the label should be floating on top of the input widget
     *
     * @param label TextView to animate
     */
    public void floatLabel(T inputWidget, View label);

    /**
     * Tells if the label is currently anchored or on the contrary is floating
     *
     * @return true if the label is anchored to the input widget
     */
    public boolean isAnchored();

    /**
     * Sets the state of the label. No need to animate here.
     *
     * @param isAnchored true if the label is initially anchored
     */
    public void setLabelAnchored(T inputWidget, View label, boolean isAnchored);
}
