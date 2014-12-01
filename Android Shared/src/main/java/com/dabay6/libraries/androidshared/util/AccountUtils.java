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

package com.dabay6.libraries.androidshared.util;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

/**
 * AccountUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class AccountUtils {
    /**
     *
     */
    public static int PICK_ACCOUNT_REQUEST = 0x1000;

    /**
     * @param activity {@link Activity} used to start the account picker activity.
     */
    public static void startAccountPicker(final Activity activity) {
        startAccountPicker(activity, null, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
    }

    /**
     * @param activity {@link Activity} used to start the account picker activity.
     * @param account  The currently selected {@link Account}.
     */
    public static void startAccountPicker(final Activity activity, final Account account) {
        startAccountPicker(activity, account, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
    }

    /**
     * @param activity    {@link Activity} used to start the account picker activity.
     * @param account     The currently selected {@link Account}.
     * @param accountType The type of accounts to choose from.
     */
    public static void startAccountPicker(final Activity activity, final Account account, final String... accountType) {
        startAccountPicker(activity, account, PICK_ACCOUNT_REQUEST, accountType);
    }

    /**
     * @param activity    {@link Activity} used to start the account picker activity.
     * @param account     The currently selected {@link Account}.
     * @param accountType The type of accounts to choose from.
     */
    public static void startAccountPicker(final Activity activity, final Account account, final Bundle bundle,
                                          final String... accountType) {
        startAccountPicker(activity, account, PICK_ACCOUNT_REQUEST, bundle, accountType);
    }

    /**
     * @param activity    {@link Activity} used to start the account picker activity.
     * @param account     The currently selected {@link Account}.
     * @param requestCode If >= 0, this code will be returned in onActivityResult() when the activity exits.
     * @param accountType The type of accounts to choose from.
     */
    public static void startAccountPicker(final Activity activity, final Account account, final int requestCode,
                                          final String... accountType) {
        startAccountPicker(activity, account, requestCode, null, accountType);
    }

    /**
     * @param activity    {@link Activity} used to start the account picker activity.
     * @param account     The currently selected {@link Account}.
     * @param requestCode If >= 0, this code will be returned in onActivityResult() when the activity exits.
     * @param accountType The type of accounts to choose from.
     */
    public static void startAccountPicker(final Activity activity, final Account account, final int requestCode,
                                          final Bundle options, final String... accountType) {
        final Intent intent =
                AccountPicker.newChooseAccountIntent(account, null, accountType, true, null, null, null, options);

        activity.startActivityForResult(intent, requestCode);
    }
}