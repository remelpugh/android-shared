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

/**
 *
 */
package com.dabay6.libraries.androidshared.validation.validators;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.dabay6.libraries.androidshared.enums.CompareOperator;
import com.dabay6.libraries.androidshared.enums.CompareType;
import com.dabay6.libraries.androidshared.util.DateUtils;
import com.dabay6.libraries.androidshared.util.StringUtils;

import java.util.Date;

/**
 * CompareValidator <p> Compares a views value to another views value. </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class CompareValidator extends BaseValidator {
    private TextView compare;
    private CompareOperator operator;
    private CompareType type;

    /**
     * Initializes this instance.
     *
     * @param context  the {@link android.content.Context} used to create the validator.
     * @param view     The {@link android.view.View} that will be validated.
     * @param compare  The control to validate against.
     * @param type     The {@link com.dabay6.libraries.androidshared.enums.CompareType} to be used.
     * @param operator The {@link com.dabay6.libraries.androidshared.enums.CompareOperator} to be used.
     * @param resId    The error message string resource id.
     */
    public CompareValidator(final Context context, final TextView view, final TextView compare, final CompareType type,
                            final CompareOperator operator,
                            final int resId) {
        super(context, view, resId);

        this.compare = compare;
        this.operator = operator;
        this.type = type;
    }

    /**
     * Initializes this instance.
     *
     * @param context  the {@link android.content.Context} used to create the validator.
     * @param view     The {@link android.view.View} that will be validated.
     * @param compare  The control to validate against.
     * @param type     The {@link com.dabay6.libraries.androidshared.enums.CompareType} to be used.
     * @param operator The {@link com.dabay6.libraries.androidshared.enums.CompareOperator} to be used.
     * @param message  The error message.
     */
    public CompareValidator(final Context context, final TextView view, final TextView compare, final CompareType type,
                            final CompareOperator operator,
                            final String message) {
        super(context, view, message);

        this.compare = compare;
        this.operator = operator;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() throws IllegalStateException {
        if (source == null) {
            throw new IllegalStateException("Source must be set.");
        }
        else {
            if (isEnabled()) {
                if (compare != null && !TextUtils.isEmpty(compare.getText())) {
                    final String value = compare.getText().toString();

                    switch (type) {
                        case Date:
                            if (operator == CompareOperator.DataTypeCheck) {
                                return DateUtils.isValid(context, source.getText());
                            }
                            else {
                                final Date date1 = DateUtils.parse(context, source.getText());
                                final Date date2 = DateUtils.parse(context, value);

                                if (date1 == null || date2 == null) {
                                    return false;
                                }

                                switch (operator) {
                                    case Equal:
                                        return date1.compareTo(date2) == 0;
                                    case GreaterThan:
                                        return date1.compareTo(date2) > 0;
                                    case GreaterThanEqual:
                                        return date1.compareTo(date2) >= 0;
                                    case LessThan:
                                        return date1.compareTo(date2) < 0;
                                    case LessThanEqual:
                                        return date1.compareTo(date2) <= 0;
                                    case NotEqual:
                                        return date1.compareTo(date2) != 0;
                                }
                            }
                        case Double:
                        case Float:
                        case Integer:
                            if (operator == CompareOperator.DataTypeCheck) {
                                try {
                                    final Double check = Double.valueOf(source.getText().toString());

                                    return true;
                                }
                                catch (final NumberFormatException ignored) {
                                    return false;
                                }
                            }
                            else {
                                final Double number1;
                                final Double number2;

                                try {
                                    number1 = Double.valueOf(source.getText().toString());
                                    number2 = Double.valueOf(value);
                                }
                                catch (final NumberFormatException ignored) {
                                    return false;
                                }

                                switch (operator) {
                                    case Equal:
                                        return number1.compareTo(number2) == 0;
                                    case GreaterThan:
                                        return number1.compareTo(number2) > 0;
                                    case GreaterThanEqual:
                                        return number1.compareTo(number2) >= 0;
                                    case LessThan:
                                        return number1.compareTo(number2) < 0;
                                    case LessThanEqual:
                                        return number1.compareTo(number2) <= 0;
                                    case NotEqual:
                                        return number1.compareTo(number2) != 0;
                                }
                            }
                        case String:
                            switch (operator) {
                                case Equal:
                                    return !TextUtils.isEmpty(source.getText())
                                           && StringUtils.equals(source.getText(), value);
                                default:
                                    return true;
                            }
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
}