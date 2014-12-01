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

package com.dabay6.libraries.androidshared.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;

import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.ActionBarUtils;
import com.dabay6.libraries.androidshared.util.ListUtils;

import java.util.List;

/**
 * FragmentListNavigationActivity
 * <p>
 * Provides basic functionality for a {@link BaseFragmentActivity} using list based
 * navigation
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class BaseFragmentListNavigationActivity extends BaseFragmentActivity implements OnNavigationListener {
    private final static String TAG = Logger.makeTag(BaseFragmentListNavigationActivity.class);
    protected List<String> navigationItems;

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean onNavigationItemSelected(int itemPosition, long itemId);

    /**
     * Identifies the array resource to be used to populate the navigation spinner
     */
    protected abstract Integer getNavigationResource();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onConfigureActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        final Integer resId = getNavigationResource();

        super.onConfigureActionBar();

        if (resId != null) {
            navigationItems = ListUtils.asList(getResources().getStringArray(resId));

            ActionBarUtils.configureListNavigation(actionBar, resId, this);
        }
    }
}