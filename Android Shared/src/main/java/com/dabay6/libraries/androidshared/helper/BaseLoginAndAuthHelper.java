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

package com.dabay6.libraries.androidshared.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.dabay6.libraries.androidshared.interfaces.LoginAndAuthCallbacks;
import com.dabay6.libraries.androidshared.logging.Logger;

import java.lang.ref.WeakReference;

/**
 * @author Remel Pugh
 */
@SuppressWarnings("unused")
public abstract class BaseLoginAndAuthHelper {
    /**
     * Boolean indicating whether we should attempt to sign in on startup (default true).
     */
    public final static String PREF_USER_REFUSED_SIGN_IN = "pref_user_refused_sign_in";
    protected final static int REQUEST_AUTHENTICATE = 100;
    protected final static int REQUEST_ERROR_DIALOG = 103;
    protected final static int REQUEST_RECOVER_FROM_AUTH_ERROR = 101;
    protected final static int REQUEST_RECOVER_FROM_ERROR = 102;
    private final static String TAG = Logger.makeTag(BaseLoginAndAuthHelper.class);
    protected static boolean canShowAuthUi = true;
    // Controls whether or not we can show sign-in UI. Starts as true;
    // when sign-in *fails*, we will show the UI only once and set this flag to false.
    // After that, we don't attempt again in order not to annoy the user.
    protected static boolean canShowSignInUi = true;
    // Name of the account to log in as (e.g. "foo@example.com")
    protected String accountName;
    // The Activity this object is bound to (we use a weak ref to avoid context leaks)
    protected WeakReference<Activity> activityReference;
    // Callbacks interface we invoke to notify the user of this class of useful events
    protected WeakReference<LoginAndAuthCallbacks> callbacksReference;
    protected Context context;
    // True if we are currently showing UIs to resolve a connection error.
    protected boolean isResolving = false;
    // Are we in the started state? Started state is between onStart and onStop.
    protected boolean isStarted = false;

    /**
     * @param activity
     * @param callbacks
     * @param accountName
     */
    public BaseLoginAndAuthHelper(final Activity activity, final LoginAndAuthCallbacks callbacks,
                                  final String accountName) {
        Logger.debug(TAG, "Helper created. Account: " + accountName);

        activityReference = new WeakReference<>(activity);
        callbacksReference = new WeakReference<>(callbacks);
        context = activity.getApplicationContext();
        this.accountName = accountName;

        if (PreferenceHelper.with(context).getBoolean(PREF_USER_REFUSED_SIGN_IN, false)) {
            // If we know the user refused sign-in, let's not annoy them.
            canShowSignInUi = canShowAuthUi = false;
        }
    }

    /**
     * @return
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * @return
     */
    public boolean isStarted() {
        return isStarted;
    }

    /**
     * Handles an Activity result. Call this from your Activity's onActivityResult().
     */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        final Activity activity = getActivity("onActivityResult()");

        if (activity == null) {
            return false;
        }

        if (requestCode == REQUEST_AUTHENTICATE ||
            requestCode == REQUEST_RECOVER_FROM_AUTH_ERROR ||
            requestCode == REQUEST_ERROR_DIALOG) {

            Logger.debug(TAG, "onActivityResult, req=" + requestCode + ", result=" + resultCode);
            if (requestCode == REQUEST_RECOVER_FROM_ERROR) {
                isResolving = false;
            }

            if (resultCode == Activity.RESULT_OK) {
                Logger.debug(TAG, "Since activity result was RESULT_OK, reconnecting client.");
                handleClientConnected();
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                Logger.debug(TAG, "User explicitly cancelled sign-in/auth flow.");
                // save this as a preference so we don't annoy the user again
                PreferenceHelper.with(context).save(PREF_USER_REFUSED_SIGN_IN, true);
            }
            else {
                Logger.warn(TAG, "Failed to recover from a login/auth failure, resultCode=" + resultCode);
            }
            return true;
        }
        return false;
    }

    /**
     *
     */
    public abstract void retryAuthByUserRequest();

    /**
     * Starts the helper. Call this from your Activity's onStart().
     */
    public void start() {
        final Activity activity = getActivity("start()");

        if (activity == null) {
            return;
        }

        if (isStarted) {
            Logger.warn(TAG, "Helper already started. Ignoring redundant call.");
            return;
        }

        isStarted = true;

        if (isResolving) {
            // if isResolving, don't reconnect the plus client
            Logger.debug(TAG, "Helper ignoring signal to start because we're isResolving a failure.");
            return;
        }

        Logger.debug(TAG, "Helper starting. Connecting " + accountName);

        startAuth(activity);
    }

    /**
     * Stop the helper. Call this from your Activity's onStop().
     */
    public void stop() {
        if (!isStarted) {
            Logger.warn(TAG, "Helper already stopped. Ignoring redundant call.");
            return;
        }

        isStarted = false;

        Logger.debug(TAG, "Helper stopping.");

        stopProcess();

        isResolving = false;
    }

    protected Activity getActivity(String methodName) {
        final Activity activity = activityReference.get();

        if (activity == null) {
            Logger.debug(TAG, "Helper lost Activity reference, ignoring (" + methodName + ")");
        }

        return activity;
    }

    protected abstract void handleClientConnected();

    protected void reportAuthFailure() {
        Logger.debug(TAG, "Auth FAILURE for account " + accountName);
        final LoginAndAuthCallbacks callbacks;

        if (null != (callbacks = callbacksReference.get())) {
            callbacks.onAuthFailure(accountName);
        }
    }

    protected void reportAuthSuccess(boolean newlyAuthenticated) {
        Logger.debug(TAG, "Auth success for account " + accountName + ", newlyAuthenticated=" + newlyAuthenticated);
        final LoginAndAuthCallbacks callbacks;

        if (null != (callbacks = callbacksReference.get())) {
            callbacks.onAuthSuccess(accountName, newlyAuthenticated);
        }
    }

    protected void showAuthRecoveryFlow(Intent intent) {
        final Activity activity = getActivity("showAuthRecoveryFlow()");

        if (activity == null) {
            return;
        }

        if (canShowAuthUi) {
            canShowAuthUi = false;
            Logger.debug(TAG, "Starting auth recovery Intent.");
            activity.startActivityForResult(intent, REQUEST_RECOVER_FROM_AUTH_ERROR);
        }
        else {
            Logger.debug(TAG, "Not showing auth recovery flow because canShowSignInUi==false.");
            reportAuthFailure();
        }
    }

    protected abstract void startAuth(final Activity activity);

    protected abstract void stopProcess();
}