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

package com.dabay6.libraries.androidshared.interfaces;

/**
 * @author Remel Pugh
 */
@SuppressWarnings("unused")
public interface LoginAndAuthCallbacks {
    /**
     * @param accountName
     */
    void onAccountInfoLoaded(String accountName);

    /**
     * @param accountName
     */
    void onAuthFailure(String accountName);

    /**
     * Called when authentication succeeds. This may either happen because the user just authenticated for the first
     * time (and went through the sign in flow), or because it's a returning user.
     *
     * @param accountName        name of the account that just authenticated successfully.
     * @param newlyAuthenticated If true, this user just authenticated for the first time. If false, it's a returning
     *                           user.
     */
    void onAuthSuccess(String accountName, boolean newlyAuthenticated);
}
