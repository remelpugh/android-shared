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

package com.dabay6.libraries.androidshared.util;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.dabay6.libraries.androidshared.helper.PreferenceHelper;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

/**
 * AccountUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class AccountUtils {
    private static final String PREFIX_PREF_AUTH_TOKEN = "auth_token_";
    private static final String PREF_ACTIVE_ACCOUNT = "util__chosen_account";
    private final static String TAG = Logger.makeTag(AccountUtils.class);
    /**
     *
     */
    public static int PICK_ACCOUNT_REQUEST = 0x1000;

    /**
     * @param context
     * @param accountType
     *
     * @return
     */
    public static Account getActiveAccount(final Context context, final String accountType) {
        final String account = getActiveAccountName(context);

        if (account != null) {
            return new Account(account, accountType);
        }
        else {
            return null;
        }
    }

    /**
     * @param context
     *
     * @return
     */
    public static String getActiveAccountName(final Context context) {
        return PreferenceHelper.with(context).getString(PREF_ACTIVE_ACCOUNT, null);
    }

    public static String getAuthToken(final Context context) {
        return hasActiveAccount(context) ?
                PreferenceHelper.with(context).getString(makeAccountSpecificPrefKey(context, PREFIX_PREF_AUTH_TOKEN),
                        null) : null;
    }

    /**
     * @param context
     *
     * @return
     */
    public static boolean hasActiveAccount(final Context context) {
        return !TextUtils.isEmpty(getActiveAccountName(context));
    }

    /**
     * @param context
     * @param accountName
     *
     * @return
     */
    public static boolean hasToken(final Context context, final String accountName) {
        return !TextUtils.isEmpty(PreferenceHelper.with(context).getString(makeAccountSpecificPrefKey(accountName,
                PREFIX_PREF_AUTH_TOKEN), null));
    }

    /**
     * @param accountName
     * @param prefix
     *
     * @return
     */
    public static String makeAccountSpecificPrefKey(final String accountName, final String prefix) {
        return prefix + accountName;
    }

    /**
     * @param context
     * @param prefix
     *
     * @return
     */
    public static String makeAccountSpecificPrefKey(final Context context, final String prefix) {
        return hasActiveAccount(context) ? makeAccountSpecificPrefKey(getActiveAccountName(context),
                prefix) : null;
    }

    /**
     * @param context
     * @param accountName
     *
     * @return
     */
    public static boolean setActiveAccount(final Context context, final String accountName) {
        Logger.debug(TAG, "Set active account to: " + accountName);

        PreferenceHelper.with(context).save(PREF_ACTIVE_ACCOUNT, accountName);

        return true;
    }

    public static void setAuthToken(final Context context, final String accountName, final String authToken) {
        Logger.info(TAG, "Auth token of length "
                         + (TextUtils.isEmpty(authToken) ? 0 : authToken.length()) + " for "
                         + accountName);

        PreferenceHelper.with(context).save(makeAccountSpecificPrefKey(accountName, PREFIX_PREF_AUTH_TOKEN),
                authToken);

        Logger.verbose(TAG, "Auth Token: " + authToken);
    }

    public static void setAuthToken(final Context context, final String authToken) {
        if (hasActiveAccount(context)) {
            setAuthToken(context, getActiveAccountName(context), authToken);
        }
        else {
            Logger.error(TAG, "Can't set auth token because there is no chosen account!");
        }
    }

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