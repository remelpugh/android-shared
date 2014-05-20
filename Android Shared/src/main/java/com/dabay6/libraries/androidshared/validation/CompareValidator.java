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
package com.dabay6.libraries.androidshared.validation;

import android.widget.TextView;

/**
 * CompareValidator
 * <p>
 * Compares a views value to another views value.
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
public class CompareValidator extends BaseValidator {
    private TextView compare;
    private CompareOperator operator;
    private CompareType type;

    /**
     * Initializes this instance.
     *
     * @param view  The {@link android.view.View} that will be validated.
     * @param resId The error message string resource id.
     */
    public CompareValidator(final TextView view, final int resId) {
        super(view, resId);
    }

    /**
     * Initializes this instance.
     *
     * @param view    The {@link android.view.View} that will be validated.
     * @param message The error message.
     */
    public CompareValidator(final TextView view, final String message) {
        super(view, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() throws IllegalStateException {
        final TextView source = getSource();

        if (source == null) {
            throw new IllegalStateException("Source must be set.");
        }
        else {
            if (isEnabled()) {
                final String value = compare.getText().toString();

                switch (type) {
                    case String: {
                        switch (operator) {
                            case Equal: {
                                return source.getText().toString().equalsIgnoreCase(value);
                            }
                            default: {
                                return true;
                            }
                        }
                    }
                    default: {
                        return true;
                    }
                }
                // boolean isValid = true;
                //
                // if (source instanceof BaseAdapter) {
                // final BaseAdapter adapter = (BaseAdapter) source;
                //
                // final int value = Integer.parseInt(compare.toString());
                //
                // switch (operator) {
                // case DataTypeCheck: {
                // // do nothing
                // break;
                // }
                // case Equal: {
                // isValid = adapter.getCount() == value;
                // break;
                // }
                // case GreaterThan: {
                // isValid = adapter.getCount() > value;
                // break;
                // }
                // case GreaterThanEqual: {
                // isValid = adapter.getCount() >= value;
                // break;
                // }
                // case LessThan: {
                // isValid = adapter.getCount() < value;
                // break;
                // }
                // case LessThanEqual: {
                // isValid = adapter.getCount() <= value;
                // break;
                // }
                // case NotEqual: {
                // isValid = adapter.getCount() != value;
                // break;
                // }
                // }
                //
                // result = new ValidationResult(isValid, getMessage(), getSource());
                // }
                // else if (source instanceof EditText && compare instanceof EditText) {
                // final String value = ((EditText) compare).getText().toString();
                //
                // switch (type) {
                // case String: {
                // switch (operator) {
                // case Equal: {
                // isValid = ((EditText) source).getText().toString().equalsIgnoreCase(value);
                // break;
                // }
                // }
                // break;
                // }
                // }
                //
                // result = new ValidationResult(isValid, getMessage(), getSource());
            }

            return true;
        }
    }

    /**
     * @param value
     */
    public void setCompareOperator(final CompareOperator value) {
        operator = value;
    }

    /**
     * @param value
     */
    public void setCompareType(final CompareType value) {
        type = value;
    }

    /**
     * @param value
     */
    public void setControlToValidate(final TextView value) {
        compare = value;
    }
}