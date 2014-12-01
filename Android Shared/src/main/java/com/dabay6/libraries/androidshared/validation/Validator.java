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

/**
 * Validator
 * <p>
 * Interface for defining a validation control.
 * </p>
 *
 * @author Remel Pugh
 * @version 1.1
 */
@SuppressWarnings("unused")
public interface Validator {
    /**
     * Gets the {@link Context} used by this instance.
     *
     * @return The {@link Context}.
     */
    Context getContext();

    /**
     * Sets the {@link Context} used by this instance.
     *
     * @param context The {@link Context}.
     */
    void setContext(Context context);

    /**
     * Gets the message that will be displayed when the validation requirements are NOT met.
     *
     * @return The error message to be displayed.
     */
    String getMessage();

    /**
     * Set a message that will be displayed when the validation requirements are NOT met.
     *
     * @param message the fault message to be displayed
     */
    void setMessage(String message);

    /**
     * Sets the message string resource id.
     *
     * @param resId The resource id of the error message.
     */
    void setMessage(int resId);

    /**
     * This method return the source object that is under validation. The actual implementation of the {@link
     * Validator}
     * is responsible for the correctness of the Object.
     *
     * @return the {@link TextView} source.
     */
    TextView getSource();

    /**
     * Determine if the {@link Validator} is enabled.
     *
     * @return true if enabled, otherwise false.
     */
    boolean isEnabled();

    /**
     * Set whether the {@link Validator} is enabled or not
     *
     * @param enabled true if enabled, otherwise false.
     */
    void setEnabled(boolean enabled);

    /**
     * Determine if the {@link Validator} passes all validation rules.
     *
     * @return True if valid, otherwise false.
     */
    boolean isValid() throws IllegalStateException;
}