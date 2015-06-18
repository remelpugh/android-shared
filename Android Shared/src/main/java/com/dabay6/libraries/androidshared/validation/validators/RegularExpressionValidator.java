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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * RegularExpressionValidator <p> Validates a view based on a regular expression. </p>
 *
 * @author Remel Pugh
 * @version 1.1
 */
@SuppressWarnings("unused")
public class RegularExpressionValidator extends BaseValidator {
    private String expression;
    private String faultPatternMessage = "";
    private Pattern pattern = null;

    /**
     * Initializes this instance.
     *
     * @param context the {@link android.content.Context} used to create the validator.
     * @param view    The {@link android.view.View} that will be validated.
     * @param pattern The validation pattern to be used
     * @param resId   The error message string resource id.
     */
    public RegularExpressionValidator(final Context context, final TextView view, final String pattern,
                                      final int resId) {
        super(context, view, resId);
        setExpression(pattern);
    }

    /**
     * Initializes this instance.
     *
     * @param context the {@link android.content.Context} used to create the validator.
     * @param view    The {@link android.view.View} that will be validated.
     * @param pattern The validation pattern to be used
     * @param message The error message.
     */
    public RegularExpressionValidator(final Context context, final TextView view, final String pattern,
                                      final String message) {
        super(context, view, message);
        setExpression(pattern);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() throws IllegalStateException {
        if (source == null) {
            throw new IllegalStateException("Source must be set.");
        }
        else if (isEnabled()) {
            if (TextUtils.isEmpty(expression) || pattern == null) {
                if (expression == null) {
                    throw new IllegalStateException("No valid expression found");
                }
                else {
                    throw new IllegalStateException(faultPatternMessage);
                }
            }
            else {
                final CharSequence text = source.getText();

                if (!TextUtils.isEmpty(text)) {
                    final Matcher matcher = pattern.matcher(text);

                    return matcher.find();
                }
                else {
                    return true;
                }
            }
        }

        return true;
    }

    private void setExpression(final String value) {
        expression = value;

        try {
            pattern = Pattern.compile(expression);
        }
        catch (final PatternSyntaxException ex) {
            faultPatternMessage = ex.getMessage();
            pattern = null;
        }
    }
}