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

import android.content.Context;
import android.widget.TextView;
import com.dabay6.libraries.androidshared.util.HashMapUtils;

import java.util.HashMap;

/**
 * FormValidation
 * <p>
 * <p/>
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class FormValidation {
    private final Context context;
    protected HashMap<String, Validator> validators = HashMapUtils.newHashMap();

    /**
     * Initializes this instance.
     *
     * @param context The {@link Context} to passed on to each {@link Validator}.
     */
    public FormValidation(final Context context) {
        this.context = context;
    }

    /**
     * Adds a {@link Validator} to the {@link HashMap} of validators.
     *
     * @param validator The {@link Validator} to be added.
     *
     * @return This instance to support chaining.
     */
    public synchronized FormValidation addValidator(final String key, final Validator validator) {
        validator.setContext(context);

        if (!validators.containsKey(key) || validators.get(key) == null) {
            validators.put(key, validator);
        }

        return this;
    }

    /**
     * Performs the validation rules for all validators.
     *
     * @return True if all validators are valid, otherwise false.
     */
    public boolean validate() {
        boolean isValid = true;

        for (final Validator validator : validators.values()) {
            final TextView view = validator.getSource();

            if (validator.isEnabled()) {
                if (!validator.isValid()) {
                    isValid = false;
                    view.setError(validator.getMessage());
                }
            }
            else {
                view.setError(null);
            }
        }

        return isValid;
    }
}