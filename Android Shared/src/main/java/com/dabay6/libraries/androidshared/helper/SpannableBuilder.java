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

package com.dabay6.libraries.androidshared.helper;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

/**
 * SpannableHelper
 *
 * @author Remel Pugh
 */
@SuppressWarnings("unused")
public final class SpannableBuilder {
    private final int flags = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
    private final Spannable spannable;

    private SpannableBuilder(final String text) {
        spannable = new SpannableString(text);
    }

    /**
     * @param start
     * @param length
     * @param color
     *
     * @return
     */
    public SpannableBuilder backColor(int start, int length, int color) {
        final BackgroundColorSpan span = new BackgroundColorSpan(color);

        spannable.setSpan(span, start, start + length, flags);

        return this;
    }

    /**
     * @param start
     * @param length
     *
     * @return
     */
    public SpannableBuilder bold(int start, int length) {
        final StyleSpan span = new StyleSpan(android.graphics.Typeface.BOLD);

        spannable.setSpan(span, start, start + length, flags);

        return this;
    }

    /**
     * @return
     */
    public Spannable build() {
        return spannable;
    }

    /**
     * @param start
     * @param length
     * @param color
     *
     * @return
     */
    public SpannableBuilder foreColor(int start, int length, int color) {
        final ForegroundColorSpan span = new ForegroundColorSpan(color);

        spannable.setSpan(span, start, start + length, flags);

        return this;
    }

    /**
     * @param start
     * @param length
     *
     * @return
     */
    public SpannableBuilder italic(int start, int length) {
        final StyleSpan span = new StyleSpan(Typeface.ITALIC);

        spannable.setSpan(span, start, start + length, flags);

        return this;
    }

    /**
     * @param start
     * @param length
     * @param proportion
     *
     * @return
     */
    public SpannableBuilder proportionate(int start, int length, float proportion) {
        final RelativeSizeSpan span = new RelativeSizeSpan(proportion);

        spannable.setSpan(span, start, start + length, flags);

        return this;
    }

    /**
     * @param start
     * @param length
     *
     * @return
     */
    public SpannableBuilder strikethrough(int start, int length) {
        final StrikethroughSpan span = new StrikethroughSpan();

        spannable.setSpan(span, start, start + length, flags);

        return this;
    }

    /**
     * @param start
     * @param length
     *
     * @return
     */
    public SpannableBuilder underline(int start, int length) {
        final UnderlineSpan span = new UnderlineSpan();

        spannable.setSpan(span, start, start + length, flags);

        return this;
    }

    /**
     * @param text
     *
     * @return
     */
    public static SpannableBuilder with(final String text) {
        return new SpannableBuilder(text);
    }
}
