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
package com.dabay6.libraries.androidshared.validation;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.dabay6.libraries.androidshared.enums.CompareOperator;
import com.dabay6.libraries.androidshared.enums.CompareType;
import com.dabay6.libraries.androidshared.interfaces.Validator;
import com.dabay6.libraries.androidshared.util.CollectionUtils;
import com.dabay6.libraries.androidshared.validation.validators.CompareValidator;
import com.dabay6.libraries.androidshared.validation.validators.RegularExpressionValidator;
import com.dabay6.libraries.androidshared.validation.validators.RequiredValidator;

import java.util.HashMap;

/**
 * ValidationSummary
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class ValidationSummary {
    private final static Object lock = new Object();
    private static ValidationSummary singleton = null;
    private final HashMap<String, Validator> validators = CollectionUtils.newHashMap();
    private Context context;

    /**
     * Initializes this instance.
     *
     * @param context The {@link Context} to passed on to each {@link Validator}.
     */
    ValidationSummary(final Context context) {
        this.context = context;
    }

    /**
     * Adds a {@link Validator} to the {@link HashMap} of validators.
     *
     * @param validator The {@link Validator} to be added.
     *
     * @return This instance to support chaining.
     */
    public synchronized ValidationSummary add(final String key, final Validator validator) {
        if (!validators.containsKey(key) || validators.get(key) == null) {
            validators.put(key, validator);
        }

        return this;
    }

    /**
     * @param key
     * @param view
     * @param compare
     * @param type
     * @param operator
     * @param message
     *
     * @return
     */
    public ValidationSummary compare(final String key, final TextView view, final TextView compare, CompareType type,
                                     final CompareOperator operator,
                                     final String message) {
        CompareValidator validator = new CompareValidator(context, view, compare, type, operator, message);

        if (!validators.containsKey(key) || validators.get(key) == null) {
            validators.put(key, validator);
        }

        return this;
    }

    /**
     * @param key
     * @param view
     * @param compare
     * @param type
     * @param operator
     * @param message
     *
     * @return
     */
    public ValidationSummary compare(final String key,
                                     final TextView view,
                                     final TextView compare,
                                     final CompareType type,
                                     final CompareOperator operator,
                                     final int message) {
        return compare(key, view, compare, type, operator, context.getString(message));
    }

    /**
     * @param key
     * @param viewId
     * @param compare
     * @param type
     * @param operator
     * @param message
     *
     * @return
     */
    public ValidationSummary compare(final String key, final int viewId, final TextView compare,
                                     final CompareType type, final CompareOperator operator,
                                     final String message) {
        final Activity context = (Activity) this.context;
        final TextView view = (TextView) context.findViewById(viewId);

        return compare(key, view, compare, type, operator, message);
    }

    /**
     * @param key
     * @param view
     * @param pattern
     * @param message
     *
     * @return This instance to support chaining.
     */
    public ValidationSummary regularExpression(final String key, final TextView view, final String pattern,
                                               final String message) {
        RegularExpressionValidator validator = new RegularExpressionValidator(context, view, pattern, message);

        if (!validators.containsKey(key) || validators.get(key) == null) {
            validators.put(key, validator);
        }

        return this;
    }

    /**
     * @param key
     * @param view
     * @param pattern
     * @param message
     *
     * @return
     */
    public ValidationSummary regularExpression(final String key,
                                               final TextView view,
                                               final String pattern,
                                               final int message) {
        return regularExpression(key, view, pattern, context.getString(message));
    }

    /**
     * @param key
     * @param viewId
     * @param pattern
     * @param message
     *
     * @return
     */
    public ValidationSummary regularExpression(final String key, final int viewId, final String pattern,
                                               final String message) {
        final Activity context = (Activity) this.context;
        final TextView view = (TextView) context.findViewById(viewId);

        return regularExpression(key, view, pattern, message);
    }

    /**
     * @param key
     * @param viewId
     * @param pattern
     * @param message
     *
     * @return
     */
    public ValidationSummary regularExpression(final String key, final int viewId, final String pattern,
                                               final int message) {
        return regularExpression(key, viewId, pattern, context.getString(message));
    }

    /**
     * @param key
     * @param view
     * @param message
     *
     * @return This instance to support chaining.
     */
    public ValidationSummary required(final String key, final TextView view, final String message) {
        RequiredValidator validator = new RequiredValidator(context, view, message);

        if (!validators.containsKey(key) || validators.get(key) == null) {
            validators.put(key, validator);
        }

        return this;
    }

    /**
     * @param key
     * @param view
     * @param message
     *
     * @return This instance to support chaining.
     */
    public ValidationSummary required(final String key, final TextView view, final int message) {
        return required(key, view, context.getString(message));
    }

    /**
     * @param key
     * @param viewId
     * @param message
     *
     * @return This instance to support chaining.
     */
    public ValidationSummary required(final String key, final int viewId, final String message) {
        final Activity context = (Activity) this.context;
        final TextView view = (TextView) context.findViewById(viewId);

        return required(key, view, message);
    }

    /**
     * @param key
     * @param viewId
     * @param message
     *
     * @return This instance to support chaining.
     */
    public ValidationSummary required(final String key, final int viewId, final int message) {
        return required(key, viewId, context.getString(message));
    }

    /**
     * Performs the validation rules for all validators.
     *
     * @return True if all validators are valid, otherwise false.
     */
    public boolean validate() {
        boolean isValid = true;

        for (final Validator validator : validators.values()) {
            if (validator.isEnabled()) {
                if (!validator.isValid()) {
                    isValid = false;
                    validator.trigger();
                }
            }
            else {
                validator.clear();
            }
        }

        return isValid;
    }

    /**
     * @param context the {@link Context} used to create the {@link com.dabay6.libraries.androidshared.validation.ValidationSummary}
     *                widget.
     *
     * @return {@link ValidationSummary}
     */
    public static ValidationSummary with(final Context context) {
        if (singleton == null) {
            synchronized (lock) {
                if (singleton == null) {
                    singleton = new ValidationSummaryBuilder(context).build();
                }
                else {
                    singleton.context = context;
                }
            }
        }

        return singleton;
    }

    private static class ValidationSummaryBuilder {
        private final Context context;

        private ValidationSummaryBuilder(final Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            if (!(context instanceof Activity)) {
                throw new IllegalStateException("Context must be an activity.");
            }

            this.context = context;
        }

        public ValidationSummary build() {
            return new ValidationSummary(context);
        }
    }

}