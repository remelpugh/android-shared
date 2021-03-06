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

package com.dabay6.libraries.androidshared.interfaces;

/**
 * FragmentActivityBase
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public interface FragmentActivityBase {
    /**
     * Gets whether the activity is running on a phone or tablet.
     *
     * @return True if the activity is running on a tablet, otherwise false.
     */
    boolean isDualPane();

    /**
     * Sets whether the fragment is running on a phone or tablet.
     *
     * @param isDualPane True if the activity is running on a tablet, otherwise false.
     */
    void setDualPane(final boolean isDualPane);

    /**
     * Sets the parent {@link android.app.Activity}'s subtitle.
     */
    void setSubtitle(final String subtitle);

    /**
     * Sets the parent {@link android.app.Activity}'s subtitle.
     *
     * @param subtitleResId The resource id of the subtitle.
     */
    void setSubtitle(final int subtitleResId);
}