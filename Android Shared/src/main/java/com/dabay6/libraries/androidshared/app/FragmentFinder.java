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

package com.dabay6.libraries.androidshared.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * FragmentFinder
 * <p>
 * Utility class that helps to find {@link Fragment}s.
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
public class FragmentFinder {
    private final Finder finder;

    /**
     * Initializes this instance of {@link FragmentFinder}.
     *
     * @param activity The {@link FragmentActivity} to search for fragments.
     */
    public FragmentFinder(final FragmentActivity activity) {
        finder = new ActivityFinder(activity);
    }

    /**
     * Lookup fragment by its assigned resource identifier.
     *
     * @param resId The resource identifier of the fragment to lookup.
     *
     * @return The fragment for the given resource identifier, otherwise null.
     */
    @SuppressWarnings("unchecked")
    public <T extends Fragment> T find(final int resId) {
        return (T) finder.findFragmentById(resId);
    }

    /**
     * Lookup fragment by its assigned tag.
     *
     * @param tag The name the fragment was tagged with.
     *
     * @return The fragment for the given tag, otherwise null.
     */
    @SuppressWarnings("unchecked")
    public <T extends Fragment> T find(final String tag) {
        return (T) finder.findFragmentByTag(tag);
    }

    private static interface Finder {
        Fragment findFragmentById(int id);

        Fragment findFragmentByTag(String tag);
    }

    private static class ActivityFinder implements Finder {
        final FragmentManager manager;

        public ActivityFinder(final FragmentActivity activity) {
            manager = activity.getSupportFragmentManager();
        }

        @Override
        public Fragment findFragmentById(int id) {
            return manager.findFragmentById(id);
        }

        @Override
        public Fragment findFragmentByTag(String tag) {
            return manager.findFragmentByTag(tag);
        }
    }
}