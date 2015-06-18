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
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.AndroidUtils;
import com.dabay6.libraries.androidshared.widget.animations.DefaultLabelAnimator;
import com.dabay6.libraries.androidshared.widget.interfaces.LabelAnimator;

/**
 * @author Vincent Mimoun-Prat @ MarvinLabs, 28/08/2014
 */
@SuppressWarnings("unused")
public abstract class FloatLabelBase<T extends View> extends FrameLayout {
    private static final String SAVE_STATE_KEY_INPUT = "saveStateInput";
    private static final String SAVE_STATE_KEY_LABEL = "saveStateLabel";
    private static final String SAVE_STATE_KEY_PARENT = "saveStateParent";
    private static final String SAVE_STATE_TAG = "saveStateTag";
    private final static String TAG = Logger.makeTag(FloatLabelBase.class);
    private boolean initCompleted = false;
    private T input;
    private boolean isLaidOut = false;
    private TextView label;
    private LabelAnimator<T> labelAnimator;
    private Bundle savedState;

    /**
     * {@inheritDoc}
     */
    public FloatLabelBase(Context context) {
        this(context, null);
    }

    /**
     * {@inheritDoc}
     */
    public FloatLabelBase(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.util__floatLabelBase);
    }

    /**
     * {@inheritDoc}
     */
    public FloatLabelBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initialize(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        if (initCompleted) {
            throw new UnsupportedOperationException("You cannot add child views to a FloatLabel");
        }
        else {
            super.addView(child);
        }
    }

    @Override
    public void addView(View child, int index) {
        if (initCompleted) {
            throw new UnsupportedOperationException("You cannot add child views to a FloatLabel");
        }
        else {
            super.addView(child, index);
        }
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        if (initCompleted) {
            throw new UnsupportedOperationException("You cannot add child views to a FloatLabel");
        }
        else {
            super.addView(child, index, params);
        }
    }

    @Override
    public void addView(View child, int width, int height) {
        if (initCompleted) {
            throw new UnsupportedOperationException("You cannot add child views to a FloatLabel");
        }
        else {
            super.addView(child, width, height);
        }
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        if (initCompleted) {
            throw new UnsupportedOperationException("You cannot add child views to a FloatLabel");
        }
        else {
            super.addView(child, params);
        }
    }

    /**
     * Delegate method for the floating label animator
     */
    public void anchorLabel() {
        if (!isLaidOut) {
            return;
        }
        getLabelAnimator().anchorLabel(getInput(), getLabel());
    }

    /**
     * Delegate method for the floating label animator
     */
    public void floatLabel() {
        if (!isLaidOut) {
            return;
        }
        getLabelAnimator().floatLabel(getInput(), getLabel());
    }

    /**
     * Get the input widget being used.
     *
     * @return The input view.
     */
    public T getInput() {
        return input;
    }

    /**
     * Get the animator for the label
     *
     * @return
     */
    public LabelAnimator<T> getLabelAnimator() {
        return labelAnimator;
    }

    /**
     * Specifies a new LabelAnimator to handle calls to show/hide the label
     *
     * @param labelAnimator LabelAnimator to use; null causes use of the default LabelAnimator
     */
    public void setLabelAnimator(LabelAnimator labelAnimator) {
        if (labelAnimator == null) {
            this.labelAnimator = new DefaultLabelAnimator();
        }
        else {
            if (this.labelAnimator != null) {
                labelAnimator.setLabelAnchored(getInput(), getLabel(), this.labelAnimator.isAnchored());
            }
            this.labelAnimator = labelAnimator;
        }

        if (isInEditMode()) {
            this.labelAnimator.setLabelAnchored(getInput(), getLabel(), false);
        }
    }

    /**
     * Delegate method for the floating label animator
     */
    public boolean isLabelAnchored() {
        return getLabelAnimator().isAnchored();
    }

    /**
     * Delegate method for the floating label animator
     */
    public void setLabelAnchored(boolean isAnchored) {
        if (!isLaidOut) {
            return;
        }
        getLabelAnimator().setLabelAnchored(getInput(), getLabel(), isAnchored);
    }

    /**
     * Set the enabled state of this view. The interpretation of the enabled state varies by subclass.
     *
     * @param enabled True if this view is enabled, false otherwise.
     */
    public void setEnabled(final boolean enabled) {
        input.setEnabled(enabled);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelAllCaps(boolean allCaps) {
        label.setAllCaps(allCaps);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelColor(int color) {
        label.setTextColor(color);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelText(int resid) {
        label.setText(resid);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelText(CharSequence hint) {
        label.setText(hint);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelTextColor(ColorStateList colors) {
        label.setTextColor(colors);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelTextSize(float size) {
        label.setTextSize(size);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelTextSize(int unit, float size) {
        label.setTextSize(unit, size);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelTypeface(Typeface tf, int style) {
        label.setTypeface(tf, style);
    }

    /**
     * Delegate method for the floating label TextView
     */
    public void setLabelTypeface(Typeface tf) {
        label.setTypeface(tf);
    }

    /**
     * Can be overriden to do something after the layout inflation
     */
    protected void afterLayoutInflated(Context context, AttributeSet attrs, int defStyle) {
    }

    /**
     * The default animator to use for the label. That method is called in init so that subclasses can provide their own
     * specialized animator if appropriate.
     *
     * @return
     */
    protected LabelAnimator<T> getDefaultLabelAnimator() {
        return new DefaultLabelAnimator<>();
    }

    /**
     * Specifies the layout to be inflated for the widget content. That layout must at least declare an EditText with id
     * "util__floating_label" and an input widget of the proper class with id "util__floating_input"
     *
     * @return The ID of the layout to inflate and attach to this view group
     */
    protected abstract int getDefaultLayoutId();

    /**
     * Delegate method for the floating label TextView
     */
    protected TextView getLabel() {
        return label;
    }

    /**
     * Returns the saved state of this widget
     *
     * @return
     */
    protected Bundle getSavedState() {
        return savedState;
    }

    /**
     * @param heightMeasureSpec
     *
     * @return
     */
    protected int measureHeight(int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        int result;

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        else {
            result = getInput().getMeasuredHeight() + label.getMeasuredHeight();
            result += getPaddingTop() + getPaddingBottom();
            result = Math.max(result, getSuggestedMinimumHeight());

            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    /**
     * @param widthMeasureSpec
     *
     * @return
     */
    protected int measureWidth(int widthMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        int result;

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        else {
            result = Math.max(getInput().getMeasuredWidth(), label.getMeasuredWidth());
            result = Math.max(result, getSuggestedMinimumWidth());
            result += getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    /**
     * Can be overriden to do something after we are done with init
     */
    protected void onInitCompleted() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int childLeft = getPaddingLeft();
        final int childRight = right - left - getPaddingRight();

        int childTop = getPaddingTop();
        final int childBottom = bottom - top - getPaddingBottom();

        layoutChild(label, childLeft, childTop, childRight, childBottom);
        layoutChild(getInput(), childLeft, childTop + label.getMeasuredHeight(), childRight, childBottom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Restore any state that's been pending before measuring
        if (savedState != null) {
            Parcelable childState = savedState.getParcelable(SAVE_STATE_KEY_LABEL);
            label.onRestoreInstanceState(childState);

            childState = savedState.getParcelable(SAVE_STATE_KEY_INPUT);
            // Because View#onRestoreInstanceState is protected, we got to ask subclasses to do it for us
            restoreState(childState);

            savedState = null;
        }

        measureChild(label, widthMeasureSpec, heightMeasureSpec);
        measureChild(getInput(), widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle savedState = (Bundle) state;

            if (savedState.getBoolean(SAVE_STATE_TAG, false)) {
                // Save our state for later since children will have theirs restored after this and having more than
                // one FloatLabel in an Activity or Fragment means you have multiple views of the same ID
                this.savedState = savedState;

                restoreAdditionalInstanceState(savedState);

                super.onRestoreInstanceState(savedState.getParcelable(SAVE_STATE_KEY_PARENT));

                return;
            }
        }

        super.onRestoreInstanceState(state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        final Bundle saveState = new Bundle();

        saveState.putParcelable(SAVE_STATE_KEY_INPUT, saveInstanceState());
        saveState.putParcelable(SAVE_STATE_KEY_LABEL, label.onSaveInstanceState());
        saveState.putParcelable(SAVE_STATE_KEY_PARENT, superState);
        saveState.putBoolean(SAVE_STATE_TAG, true);

        putAdditionalInstanceState(saveState);

        return saveState;
    }

    /**
     * Give the opportunity to child classes to save additional state variables
     *
     * @param saveState The state of the floating label widget
     */
    protected void putAdditionalInstanceState(Bundle saveState) {
    }

    /**
     * Give the opportunity to child classes to restore additional state variables they had saved
     *
     * @param savedState The state of the floating label widget
     */
    protected void restoreAdditionalInstanceState(Bundle savedState) {
    }

    /**
     * Restore the saved state of the input widget
     *
     * @param state The state of the input widget
     */
    protected void restoreState(Parcelable state) {
    }

    /**
     * Save the input widget state. Usually you will simply call the widget's onSaveInstanceState method
     *
     * @return The saved input widget state
     */
    protected Parcelable saveInstanceState() {
        return new Bundle();
    }

    /**
     * Set the initial state for the label
     */
    protected void setInitialWidgetState() {
        setLabelAnchored(true);
    }

    private void inflateLayout(Context context, int layoutId) {
        final View input;

        inflate(context, layoutId, this);

        label = (TextView) findViewById(R.id.util__floating_label);
        if (label == null) {
            throw new RuntimeException("Your layout must have a TextView whose ID is @id/util__floating_label");
        }

        input = findViewById(R.id.util__floating_input);
        if (input == null) {
            throw new RuntimeException("Your layout must have an input widget whose ID is @id/util__floating_input");
        }
        try {
            //noinspection unchecked
            this.input = (T) input;
        }
        catch (ClassCastException e) {
            throw new RuntimeException("The input widget is not of the expected type");
        }
    }

    /**
     * @param context
     * @param attributes
     * @param defStyle
     */
    private void initialize(final Context context, final AttributeSet attributes, final int defStyle) {
        final TypedArray array = context.obtainStyledAttributes(attributes, R.styleable.FloatLabelBase, defStyle,
                R.style.utils_Widget_FloatLabelBase);
        final boolean enabled;
        final int layoutId;
        final CharSequence text;
        final int textColor;
        final float textSize;

        enabled = array.getBoolean(R.styleable.FloatLabelBase_android_enabled, true);
        layoutId = array.getResourceId(R.styleable.FloatLabelBase_android_layout, getDefaultLayoutId());
        text = array.getText(R.styleable.FloatLabelBase_util__labelText);
        textColor = array.getColor(R.styleable.FloatLabelBase_util__labelTextColor, R.color.util__float_label_color);
        textSize = array.getDimension(R.styleable.FloatLabelBase_util__labelTextSize,
                getResources().getDimensionPixelSize(R.dimen.util__defaultLabelTextSize));

        array.recycle();
//        if (attributes == null) {
//            layoutId = getDefaultLayoutId();
//            floatLabelText = null;
//            floatLabelTextColor = R.color.util__float_label_color;
//            floatLabelTextSize = getResources().getDimensionPixelSize(R.dimen.util__defaultLabelTextSize);
//        }
//        else {
//        }

        inflateLayout(context, layoutId);

        getLabel().setFocusableInTouchMode(false);
        getLabel().setFocusable(false);

        setLabelAnimator(getDefaultLabelAnimator());
        setLabelText(text);
        setLabelColor(textColor);
        setLabelTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        afterLayoutInflated(context, attributes, defStyle);

        isLaidOut = false;

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                isLaidOut = true;
                setInitialWidgetState();

                if (AndroidUtils.isAtLeastJellyBean()) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                else {
                    //noinspection deprecation
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        // Mark init as complete to prevent accidentally breaking the view by adding children
        initCompleted = true;

        onInitCompleted();

        setEnabled(enabled);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void layoutChild(View child, int parentLeft, int parentTop, int parentRight, int parentBottom) {
        if (child.getVisibility() != GONE) {
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            final int height = child.getMeasuredHeight();
            final int layoutDirection;
            final int width = child.getMeasuredWidth();
            final int childTop = parentTop + lp.topMargin;
            int childLeft;
            int gravity = lp.gravity;

            if (gravity == -1) {
                gravity = Gravity.TOP | Gravity.START;
            }

            if (AndroidUtils.isVersionBefore(Build.VERSION_CODES.JELLY_BEAN_MR1)) {
                layoutDirection = LAYOUT_DIRECTION_LTR;
            }
            else {
                layoutDirection = getLayoutDirection();
            }

            final int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);

            switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                case Gravity.CENTER_HORIZONTAL:
                    childLeft = parentLeft + (parentRight - parentLeft - width) / 2 + lp.leftMargin - lp.rightMargin;
                    break;
                case Gravity.RIGHT:
                    childLeft = parentRight - width - lp.rightMargin;
                    break;
                case Gravity.LEFT:
                default:
                    childLeft = parentLeft + lp.leftMargin;
            }

            child.layout(childLeft, childTop, childLeft + width, childTop + height);
        }
    }
}