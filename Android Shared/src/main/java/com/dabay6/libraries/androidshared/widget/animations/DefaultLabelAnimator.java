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

import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.widget.interfaces.LabelAnimator;

/**
 * @author Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014
 */
@SuppressWarnings("unused")
public class DefaultLabelAnimator<T extends View> implements LabelAnimator<T> {
    public static final float ALPHA_ANCHORED = 0.8f;
    public static final float ALPHA_FLOATING = 1.0f;
    public static final float SCALE_ANCHORED = 1.0f;
    public static final float SCALE_FLOATING = 0.8f;
    private static final String TAG = Logger.makeTag(DefaultLabelAnimator.class);
    private float alphaAnchored;
    private float alphaFloating;
    private boolean isAnchored;
    private float scaleAnchored;
    private float scaleFloating;

    public DefaultLabelAnimator() {
        this(ALPHA_ANCHORED, ALPHA_FLOATING, SCALE_ANCHORED, SCALE_FLOATING);
    }

    public DefaultLabelAnimator(float alphaAnchored, float alphaFloating, float scaleAnchored, float scaleFloating) {
        this.alphaAnchored = alphaAnchored;
        this.alphaFloating = alphaFloating;
        this.scaleAnchored = scaleAnchored;
        this.scaleFloating = scaleFloating;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void anchorLabel(T inputWidget, View label) {
        if (isAnchored) {
            return;
        }
        setLabelAnchored(inputWidget, label, true, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void floatLabel(T inputWidget, View label) {
        if (!isAnchored) {
            return;
        }
        setLabelAnchored(inputWidget, label, false, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAnchored() {
        return isAnchored;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLabelAnchored(T inputWidget, View label, boolean isAnchored) {
        setLabelAnchored(inputWidget, label, isAnchored, false);
    }

    protected float getTargetAlpha(T inputWidget, View label, boolean isAnchored) {
        return isAnchored ? alphaAnchored : alphaFloating;
    }

    protected float getTargetScale(T inputWidget, View label, boolean isAnchored) {
        return isAnchored ? scaleAnchored : scaleFloating;
    }

    protected float getTargetX(T inputWidget, View label, boolean isAnchored) {
        return inputWidget.getLeft() + inputWidget.getPaddingLeft();
    }

    protected float getTargetY(T inputWidget, View label, boolean isAnchored) {
        if (isAnchored) {
            return inputWidget.getBottom() - inputWidget.getPaddingBottom() - label.getHeight();
        }
        else {
            final float targetScale = getTargetScale(inputWidget, label, isAnchored);
            return inputWidget.getTop() - targetScale * label.getHeight();
        }
    }

    private void setLabelAnchored(T inputWidget, View label, boolean isAnchored, boolean animateLabel) {
        this.isAnchored = isAnchored;

        final float targetAlpha = getTargetAlpha(inputWidget, label, isAnchored);
        final float targetX = getTargetX(inputWidget, label, isAnchored);
        final float targetY = getTargetY(inputWidget, label, isAnchored);
        final float targetScale = getTargetScale(inputWidget, label, isAnchored);

        if (animateLabel) {
            label.setPivotX(0);
            label.setPivotY(0);
            label.animate()
                 .alpha(targetAlpha)
                 .x(targetX)
                 .y(targetY)
                 .scaleX(targetScale)
                 .scaleY(targetScale);
        }
        else {
            label.setAlpha(targetAlpha);
            label.setX(targetX);
            label.setY(targetY);
            label.setPivotX(0);
            label.setPivotY(0);
            label.setScaleX(targetScale);
            label.setScaleY(targetScale);
        }

        Logger.debug(TAG, String.format("Setting label state to (x=%.0f, y=%.0f, alpha=%.1f, scale=%.1f, anchored=%d)",
                targetX, targetY,
                targetAlpha,
                targetScale,
                isAnchored ? 1 : 0));
    }
}
