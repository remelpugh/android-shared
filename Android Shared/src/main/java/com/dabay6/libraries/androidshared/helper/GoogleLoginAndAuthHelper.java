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
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.dabay6.libraries.androidshared.interfaces.LoginAndAuthCallbacks;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.AccountUtils;
import com.dabay6.libraries.androidshared.util.GoogleAccountUtils;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import java.io.IOException;

/**
 * This helper handles the UI flow for signing in and authenticating an account. It handles connecting to the Google+
 * API to fetch profile data (name, cover photo, etc) and also getting the auth token for the necessary scopes. The life
 * of this object is tied to an Activity. Do not attempt to share it across Activities, as unhappiness will result.
 *
 * @author Google
 */
@SuppressWarnings("unused")
public class GoogleLoginAndAuthHelper extends BaseLoginAndAuthHelper
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<People.LoadPeopleResult> {

    // Request codes for the UIs that we show
    private final static String TAG = Logger.makeTag(GoogleLoginAndAuthHelper.class);
    // Async task that fetches the token
    GetTokenTask tokenTask = null;
    // API client to interact with Google services
    private GoogleApiClient googleApiClient;

    /**
     * @param activity
     * @param callbacks
     * @param accountName
     */
    public GoogleLoginAndAuthHelper(final Activity activity, final LoginAndAuthCallbacks callbacks,
                                    final String accountName) {
        super(activity, callbacks, accountName);
    }

    /**
     * Called when the Google+ client is connected.
     */
    @Override
    public void onConnected(final Bundle bundle) {
        final Activity activity = getActivity("onConnected()");

        if (activity == null) {
            return;
        }

        Logger.debug(TAG, "Helper connected, account " + accountName);

        // load user's Google+ profile, if we don't have it yet
        if (!GoogleAccountUtils.hasPlusInfo(activity, accountName)) {
            Logger.debug(TAG, "We don't have Google+ info for " + accountName + " yet, so loading.");
            PendingResult<People.LoadPeopleResult> result = Plus.PeopleApi.load(googleApiClient, "me");
            result.setResultCallback(this);
        }
        else {
            Logger.debug(TAG, "No need for Google+ info, we already have it.");
        }

        // try to authenticate, if we don't have a token yet
        if (!AccountUtils.hasToken(activity, accountName)) {
            Logger.debug(TAG, "We don't have auth token for " + accountName + " yet, so getting it.");
            tokenTask = new GetTokenTask();
            tokenTask.execute();
        }
        else {
            Logger.debug(TAG, "No need for auth token, we already have it.");
            reportAuthSuccess(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConnectionFailed(final ConnectionResult connectionResult) {
        final Activity activity = getActivity("onConnectionFailed()");

        if (activity == null) {
            return;
        }

        if (connectionResult.hasResolution()) {
            if (canShowSignInUi) {
                Logger.debug(TAG, "onConnectionFailed, with resolution. Attempting to resolve.");
                canShowSignInUi = false;
                try {
                    isResolving = true;
                    connectionResult.startResolutionForResult(activity,
                            REQUEST_RECOVER_FROM_ERROR);
                }
                catch (IntentSender.SendIntentException e) {
                    Logger.error(TAG, "SendIntentException occurred: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            else {
                Logger.debug(TAG, "onConnectionFailed with resolution but canShowSignInUi==false.");
                reportAuthFailure();
            }
            return;
        }

        Logger.debug(TAG, "onConnectionFailed, no resolution.");
        final int errorCode = connectionResult.getErrorCode();
        if (GooglePlayServicesUtil.isUserRecoverableError(errorCode) && canShowSignInUi) {
            canShowSignInUi = false;
            GooglePlayServicesUtil.getErrorDialog(errorCode, activity,
                    REQUEST_ERROR_DIALOG).show();
        }
        else {
            reportAuthFailure();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConnectionSuspended(int i) {
        Logger.debug(TAG, "onConnectionSuspended.");
    }

    /**
     * Called asynchronously -- result of loadPeople() call
     */
    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {
        Logger.debug(TAG, "onPeopleLoaded, status=" + loadPeopleResult.getStatus().toString());

        if (loadPeopleResult.getStatus().isSuccess()) {
            final PersonBuffer personBuffer = loadPeopleResult.getPersonBuffer();

            if (personBuffer != null && personBuffer.getCount() > 0) {
                Logger.debug(TAG, "Got plus profile for account " + accountName);
                final Person currentUser = personBuffer.get(0);

                personBuffer.close();

                // Record profile ID, image URL and name
                Logger.debug(TAG, "Saving plus profile ID: " + currentUser.getId());

                GoogleAccountUtils.setPlusProfileId(context, accountName, currentUser.getId());

                String imageUrl = currentUser.getImage().getUrl();

                if (imageUrl != null) {
                    imageUrl = Uri.parse(imageUrl)
                                  .buildUpon().appendQueryParameter("sz", "256").build().toString();
                }

                Logger.debug(TAG, "Saving plus image URL: " + imageUrl);

                GoogleAccountUtils.setPlusImageUrl(context, accountName, imageUrl);

                Logger.debug(TAG, "Saving plus display name: " + currentUser.getDisplayName());

                GoogleAccountUtils.setPlusName(context, accountName, currentUser.getDisplayName());

                final Person.Cover cover = currentUser.getCover();

                if (cover != null) {
                    final Person.Cover.CoverPhoto coverPhoto = cover.getCoverPhoto();

                    if (coverPhoto != null) {
                        Logger.debug(TAG, "Saving plus cover URL: " + coverPhoto.getUrl());
                        GoogleAccountUtils.setPlusCoverUrl(context, accountName, coverPhoto.getUrl());
                    }
                }
                else {
                    Logger.debug(TAG, "Profile has no cover.");
                }

                final LoginAndAuthCallbacks callbacks;

                if (null != (callbacks = callbacksReference.get())) {
                    callbacks.onAccountInfoLoaded(accountName);
                }
            }
            else {
                Logger.error(TAG, "Plus response was empty! Failed to load profile.");
            }
        }
        else {
            Logger.error(TAG, "Failed to load plus profile, error " + loadPeopleResult.getStatus().getStatusCode());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void retryAuthByUserRequest() {
        Logger.debug(TAG, "Retrying sign-in/auth (user-initiated).");

        if (!googleApiClient.isConnected()) {
            canShowAuthUi = canShowSignInUi = true;
            PreferenceHelper.with(context).getBoolean(PREF_USER_REFUSED_SIGN_IN, false);
            googleApiClient.connect();
        }
        else if (!AccountUtils.hasToken(context, accountName)) {
            canShowAuthUi = canShowSignInUi = true;
            PreferenceHelper.with(context).getBoolean(PREF_USER_REFUSED_SIGN_IN, false);
            tokenTask = new GetTokenTask();
            tokenTask.execute();
        }
        else {
            Logger.debug(TAG, "No need to retry auth: GoogleApiClient is connected and we have auth token.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleClientConnected() {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
        else {
            Logger.debug(TAG, "Activity result was RESULT_OK, but we have no client to reconnect.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void startAuth(final Activity activity) {
        if (googleApiClient == null) {
            Logger.debug(TAG, "Creating client.");

            GoogleApiClient.Builder builder = new GoogleApiClient.Builder(activity);
            for (String scope : GoogleAccountUtils.AUTH_SCOPES) {
                builder.addScope(new Scope(scope));
            }
            googleApiClient = builder.addApi(Plus.API)
                                     .addConnectionCallbacks(this)
                                     .addOnConnectionFailedListener(this)
                                     .setAccountName(accountName)
                                     .build();
        }
        Logger.debug(TAG, "Connecting client.");
        googleApiClient.connect();
    }

    @Override
    protected void stopProcess() {
        if (tokenTask != null) {
            Logger.debug(TAG, "Helper cancelling token task.");
            tokenTask.cancel(false);
        }

        if (googleApiClient.isConnected()) {
            Logger.debug(TAG, "Helper disconnecting client.");
            googleApiClient.disconnect();
        }
    }

    private void showRecoveryDialog(int statusCode) {
        final Activity activity = getActivity("showRecoveryDialog()");

        if (activity == null) {
            return;
        }

        if (canShowAuthUi) {
            canShowAuthUi = false;
            Logger.debug(TAG, "Showing recovery dialog for status code " + statusCode);
            final Dialog d = GooglePlayServicesUtil.getErrorDialog(
                    statusCode, activity, REQUEST_RECOVER_FROM_ERROR);
            d.show();
        }
        else {
            Logger.debug(TAG, "Not showing Play Services recovery dialog because canShowSignInUi==false.");
            reportAuthFailure();
        }
    }


    /**
     * Async task that obtains the auth token.
     */
    private class GetTokenTask extends AsyncTask<Void, Void, String> {
        public GetTokenTask() {
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                if (isCancelled()) {
                    Logger.debug(TAG, "doInBackground: task cancelled, so giving up on auth.");
                    return null;
                }

                Logger.debug(TAG, "Starting background auth for " + accountName);

                final String token =
                        GoogleAuthUtil.getToken(context, accountName, GoogleAccountUtils.AUTH_TOKEN_TYPE);

                // Save auth token.
                Logger.debug(TAG, "Saving token: " + (token == null ? "(null)" : "(length " +
                                                                                 token.length() + ")") +
                                  " for account " +
                                  accountName);
                AccountUtils.setAuthToken(context, accountName, token);
                return token;
            }
            catch (GooglePlayServicesAvailabilityException e) {
                postShowRecoveryDialog(e.getConnectionStatusCode());
            }
            catch (UserRecoverableAuthException e) {
                postShowAuthRecoveryFlow(e.getIntent());
            }
            catch (IOException e) {
                Logger.error(TAG, "IOException encountered: " + e.getMessage());
            }
            catch (GoogleAuthException e) {
                Logger.error(TAG, "GoogleAuthException encountered: " + e.getMessage());
            }
            catch (RuntimeException e) {
                Logger.error(TAG, "RuntimeException encountered: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String token) {
            super.onPostExecute(token);

            if (isCancelled()) {
                Logger.debug(TAG, "Task cancelled, so not reporting auth success.");
            }
            else if (!isStarted) {
                Logger.debug(TAG, "Activity not started, so not reporting auth success.");
            }
            else {
                Logger.debug(TAG, "GetTokenTask reporting auth success.");
                reportAuthSuccess(true);
            }
        }

        private void postShowAuthRecoveryFlow(final Intent intent) {
            final Activity activity = getActivity("postShowAuthRecoveryFlow()");

            if (activity == null) {
                return;
            }

            if (isCancelled()) {
                Logger.debug(TAG, "Task cancelled, so not showing auth recovery flow.");
                return;
            }

            Logger.debug(TAG, "Requesting display of auth recovery flow.");
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isStarted) {
                        showAuthRecoveryFlow(intent);
                    }
                    else {
                        Logger.error(TAG, "Activity not started, so not showing auth recovery flow.");
                    }
                }
            });
        }

        private void postShowRecoveryDialog(final int statusCode) {
            final Activity activity = getActivity("postShowRecoveryDialog()");

            if (activity == null) {
                return;
            }

            if (isCancelled()) {
                Logger.debug(TAG, "Task cancelled, so not showing recovery dialog.");
                return;
            }

            Logger.debug(TAG, "Requesting display of recovery dialog for status code " + statusCode);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isStarted) {
                        showRecoveryDialog(statusCode);
                    }
                    else {
                        Logger.error(TAG, "Activity not started, so not showing recovery dialog.");
                    }
                }
            });
        }
    }
}