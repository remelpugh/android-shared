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

package com.dabay6.libraries.androidshared.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.dabay6.libraries.androidshared.exceptions.ActivityStartException;

/**
 * ActivityIntentBuilder <p> </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class ActivityIntentBuilder {
    private final Context activityContext;
    private final Intent intent;

    /**
     * @param context
     * @param cls
     */
    private ActivityIntentBuilder(final Context context, final Class<? extends Activity> cls) {
        activityContext = context;
        intent = new Intent(context, cls);
    }

    /**
     * @return
     */
    public ActivityIntentBuilder clearTop() {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        return this;
    }

    /**
     * @return
     */
    public Intent intent() {
        return intent;
    }

    /**
     * @return
     */
    public ActivityIntentBuilder newTask() {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return this;
    }

    /**
     * @return
     */
    public ActivityIntentBuilder noAnimation() {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        return this;
    }

    /**
     * @return
     */
    public ActivityIntentBuilder noHistory() {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        return this;
    }

    /**
     * Starts the activity without an animation.
     *
     * @return True if activity was successfully started, otherwise false.
     */
    public boolean start() {
        try {
            startWithAnimation(0, 0);

            return true;
        }
        catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Starts the activity with the default fade in animation.
     */
    public void startWithAnimation() throws ActivityStartException {
        startWithAnimation(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * Starts the activity with the specified animation resources.
     *
     * @param enterAnimation A resource ID of the animation resource to use for the incoming activity. Use 0 for no
     *                       animation.
     * @param exitAnimation  A resource ID of the animation resource to use for the outgoing activity. Use 0 for no
     *                       animation.
     */
    public void startWithAnimation(final int enterAnimation, final int exitAnimation) throws ActivityStartException {
        try {
            if (!(activityContext instanceof Activity)) {
                throw new ActivityStartException("Context must of type Activity");
            }

            final Activity activity = (Activity) activityContext;

            activity.startActivity(intent);
            activity.overridePendingTransition(enterAnimation, exitAnimation);
        }
        catch (Exception ex) {
            throw new ActivityStartException(ex);
        }
    }

    /**
     * @return {@link ActivityIntentBuilder}
     */
    public static ActivityIntentBuilder with(final Context context, final Class<? extends Activity> cls) {
        return new ActivityIntentBuilder(context, cls);
    }
}
