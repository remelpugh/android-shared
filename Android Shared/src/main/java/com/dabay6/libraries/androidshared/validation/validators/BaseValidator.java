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
import android.widget.TextView;

import com.dabay6.libraries.androidshared.interfaces.Validator;

/**
 * BaseValidator <p> Abstract class providing base functionality to all validators. </p>
 *
 * @author Remel Pugh
 * @version 1.1
 */
@SuppressWarnings("unused")
public abstract class BaseValidator implements Validator {
    protected final TextView source;
    protected Context context;
    private boolean isEnabled = true;
    private String message;
    private Integer messageResourceId;

    /**
     * Initializes this instance.
     *
     * @param context the {@link android.content.Context} used to create the validator.
     * @param view    The {@link android.view.View} that will be validated.
     * @param resId   The error message string resource id.
     */
    BaseValidator(final Context context, final TextView view, final int resId) {
        this(context, view, null);
        messageResourceId = resId;
    }

    /**
     * Initializes this instance.
     *
     * @param context the {@link android.content.Context} used to create the validator.
     * @param view    The {@link android.view.View} that will be validated.
     * @param message The error message.
     */
    BaseValidator(final Context context, final TextView view, final String message) {
        messageResourceId = null;
        source = view;
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        source.setError(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        if (context == null || messageResourceId == null) {
            return message;
        }
        else {
            return context.getString(messageResourceId);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMessage(final int resId) {
        messageResourceId = resId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEnabled(final boolean enabled) {
        isEnabled = enabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean isValid() throws IllegalStateException;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trigger() {
        source.setError(getMessage());
    }
}