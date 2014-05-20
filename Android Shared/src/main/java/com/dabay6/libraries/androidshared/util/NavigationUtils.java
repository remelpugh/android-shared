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

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;

/**
 * NavigationUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class NavigationUtils {
    /**
     * Hidden constructor.
     */
    private NavigationUtils() {
    }

    /**
     * @param activity
     */
    public static void navigateUp(final Activity activity) {
        // This is called when the Home (Up) button is pressed in the action bar. Create a simple intent that starts the
        // hierarchical parent activity and use NavUtils in the Support Package to ensure proper handling of Up.
        final Intent upIntent = NavUtils.getParentActivityIntent(activity);

        if (NavUtils.shouldUpRecreateTask(activity, upIntent)) {
            // This activity is not part of the application's task, so create a new task with a synthesized back stack.
            TaskStackBuilder.create(activity)
                    // If there are ancestor activities, they should be added here.
                    .addNextIntent(upIntent).startActivities();
            activity.finish();
        }
        else {
            // This activity is part of the application's task, so simply navigate up to the hierarchical parent
            // activity.
            NavUtils.navigateUpTo(activity, upIntent);
        }
    }
}