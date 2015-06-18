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

import android.content.Context;
import android.text.TextUtils;

import com.dabay6.libraries.androidshared.helper.PreferenceHelper;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.Scopes;

/**
 * @author Remel Pugh
 */
@SuppressWarnings("unused")
public final class GoogleAccountUtils {
    // Auth scopes we need
    public static final String AUTH_SCOPES[] = {
            Scopes.PLUS_LOGIN,
            Scopes.DRIVE_APPFOLDER,
            "https://www.googleapis.com/auth/plus.profile.emails.read"
    };
    public static final String AUTH_TOKEN_TYPE;
    private static final String PREFIX_PREF_PLUS_COVER_URL = "plus_cover_url_";
    private static final String PREFIX_PREF_PLUS_IMAGE_URL = "plus_image_url_";
    private static final String PREFIX_PREF_PLUS_NAME = "plus_name_";
    private static final String PREFIX_PREF_PLUS_PROFILE_ID = "plus_profile_id_";

    public static String getPlusCoverUrl(final Context context) {
        return AccountUtils.hasActiveAccount(context) ? PreferenceHelper.with(context).getString
                (AccountUtils.makeAccountSpecificPrefKey(context,
                        PREFIX_PREF_PLUS_COVER_URL), null) : null;
    }

    public static String getPlusImageUrl(final Context context) {
        return AccountUtils.hasActiveAccount(context) ? PreferenceHelper.with(context).getString
                (AccountUtils.makeAccountSpecificPrefKey(context,
                        PREFIX_PREF_PLUS_IMAGE_URL), null) : null;
    }

    public static String getPlusImageUrl(final Context context, final String accountName) {
        return AccountUtils.hasActiveAccount(context) ? PreferenceHelper.with(context).getString
                (AccountUtils.makeAccountSpecificPrefKey(accountName,
                        PREFIX_PREF_PLUS_IMAGE_URL), null) : null;
    }

    public static String getPlusName(final Context context) {
        return AccountUtils.hasActiveAccount(context) ? PreferenceHelper.with(context).getString
                (AccountUtils.makeAccountSpecificPrefKey(context,
                        PREFIX_PREF_PLUS_NAME), null) : null;
    }

    /**
     * @param context
     *
     * @return
     */
    public static String getPlusProfileId(final Context context) {
        return AccountUtils.hasActiveAccount(context) ? PreferenceHelper.with(context).getString
                (AccountUtils.makeAccountSpecificPrefKey(context, PREFIX_PREF_PLUS_PROFILE_ID), null) : null;
    }

    /**
     * @param context
     * @param accountName
     *
     * @return
     */
    public static boolean hasPlusInfo(final Context context, final String accountName) {
        return !TextUtils.isEmpty(PreferenceHelper.with(context).getString(AccountUtils.makeAccountSpecificPrefKey
                (accountName,
                        PREFIX_PREF_PLUS_PROFILE_ID), null));
    }

    public static void setPlusCoverUrl(final Context context, final String accountName, String coverPhotoUrl) {
        PreferenceHelper.with(context).save(AccountUtils.makeAccountSpecificPrefKey(accountName,
                        PREFIX_PREF_PLUS_COVER_URL),
                coverPhotoUrl);
    }

    public static void setPlusImageUrl(final Context context, final String accountName, final String imageUrl) {
        PreferenceHelper.with(context).save(AccountUtils.makeAccountSpecificPrefKey(accountName,
                        PREFIX_PREF_PLUS_IMAGE_URL),
                imageUrl);
    }

    public static void setPlusName(final Context context, final String accountName, final String name) {
        PreferenceHelper.with(context).save(AccountUtils.makeAccountSpecificPrefKey(accountName, PREFIX_PREF_PLUS_NAME),
                name);
    }

    public static void setPlusProfileId(final Context context, final String accountName, final String profileId) {
        PreferenceHelper.with(context).save(AccountUtils.makeAccountSpecificPrefKey(accountName,
                        PREFIX_PREF_PLUS_PROFILE_ID),
                profileId);
    }

    static void invalidateAuthToken(final Context context) {
        GoogleAuthUtil.invalidateToken(context, AccountUtils.getAuthToken(context));
        AccountUtils.setAuthToken(context, null);
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("oauth2:");
        for (String scope : AUTH_SCOPES) {
            sb.append(scope);
            sb.append(" ");
        }
        AUTH_TOKEN_TYPE = sb.toString();
    }
}
