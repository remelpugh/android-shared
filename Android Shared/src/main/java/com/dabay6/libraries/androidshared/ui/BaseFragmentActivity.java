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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.dabay6.libraries.androidshared.app.FragmentFinder;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.ui.fragments.FragmentBase;
import com.dabay6.libraries.androidshared.util.NavigationUtils;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

/**
 * BaseFragmentActivity
 * <p>
 * Provides basic functionality for a {@link ActionBarActivity}.
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class BaseFragmentActivity extends ActionBarActivity
        implements FragmentActivityBase, OnBackStackChangedListener {
    private final static String TAG = Logger.makeTag(BaseFragmentActivity.class);
    protected Bundle activityState;
    protected ViewsFinder finder;
    protected FragmentFinder fragmentFinder;
    private boolean isDualPane = true;
    private FragmentTransaction transaction;

    /**
     * @param fragment
     * @param containerViewId
     * @param tag
     */
    public <T extends Fragment> void addFragment(final T fragment, final int containerViewId, final String tag) {
        if (fragment instanceof FragmentBase) {
            ((FragmentBase) fragment).setDualPane(isDualPane());
        }

        transaction.add(containerViewId, fragment, tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDualPane() {
        return isDualPane;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDualPane(final boolean isDualPane) {
        this.isDualPane = isDualPane;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackStackChanged() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final Integer resourceId = getMenuResource();

        if (resourceId != null) {
            getMenuInflater().inflate(resourceId, menu);
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();

        switch (id) {
            case android.R.id.home: {
                navigateUp();

                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    /**
     * @param fragment
     * @param containerViewId
     * @param tag
     */
    public <T extends Fragment> void replaceFragment(final T fragment, final int containerViewId, final String tag) {
        if (fragment instanceof FragmentBase) {
            ((FragmentBase) fragment).setDualPane(isDualPane());
        }

        transaction.replace(containerViewId, fragment, tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentView(final int layoutResID) {
        super.setContentView(layoutResID);

        afterSetContentView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentView(final View view) {
        super.setContentView(view);

        afterSetContentView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentView(final View view, final LayoutParams params) {
        super.setContentView(view, params);

        afterSetContentView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubtitle(final int subtitleResId) {
        setSubtitle(getString(subtitleResId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubtitle(final String subtitle) {
        getSupportActionBar().setSubtitle(subtitle);
    }

    /**
     * @return
     */
    public FragmentTransaction startTransaction() {
        transaction = getSupportFragmentManager().beginTransaction();

        return transaction;
    }

    /**
     *
     */
    protected void afterSetContentView() {
        finder = new ViewsFinder(this);
        fragmentFinder = new FragmentFinder(this);

        afterViews(activityState);
    }

    /**
     * Executed after the activity's layout has been assigned
     */
    protected abstract void afterViews(Bundle savedInstanceState);

    /**
     * @return
     */
    @SuppressWarnings("SameReturnValue")
    protected <T extends Fragment> T getFragment() {
        return null;
    }

    /**
     * @return
     */
    @SuppressWarnings("SameReturnValue")
    protected String getFragmentTag() {
        return null;
    }

    /**
     * Identifies the layout resource to be used by the current activity.
     *
     * @return The resource identifier for the layout
     */
    protected abstract Integer getLayoutResource();

    /**
     * Identifies the menu resource to be used by the current activity
     *
     * @return The resource identifier for the menu
     */
    @SuppressWarnings("SameReturnValue")
    protected Integer getMenuResource() {
        return null;
    }

    /**
     * @param savedInstanceState
     */
    protected void initialize(final Bundle savedInstanceState) {
        activityState = savedInstanceState;
    }

    /**
     * @return
     */
    protected boolean isHomeAsUpEnabled() {
        return false;
    }

    /**
     * @return
     */
    @SuppressWarnings("SameReturnValue")
    protected boolean isHomeButtonEnabled() {
        return true;
    }

    /**
     * @return
     */
    @SuppressWarnings("SameReturnValue")
    protected boolean isLogoEnabled() {
        return false;
    }

    /**
     * Determines if the application title is displayed by default it is not
     * displayed.
     *
     * @return true if the title is to be displayed, otherwise false
     */
    protected boolean isTitleEnabled() {
        return false;
    }

    /**
     *
     */
    protected void navigateUp() {
        NavigationUtils.navigateUp(this);
    }

    /**
     * Sets the default configuration for the activity {@link ActionBar}.
     */
    protected void onConfigureActionBar() {
        final ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(isHomeAsUpEnabled());
        actionBar.setDisplayShowTitleEnabled(isTitleEnabled());
        actionBar.setDisplayUseLogoEnabled(isLogoEnabled());
        actionBar.setHomeButtonEnabled(isHomeButtonEnabled());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        initialize(savedInstanceState);

        super.onCreate(savedInstanceState);

        if (getLayoutResource() == null && getFragment() == null) {
            throw new IllegalStateException("Either a layout or a fragment must be specified.");
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        if (getLayoutResource() != null) {
            setContentView(getLayoutResource());
        }
        else {
            final FragmentTransaction transaction;

            if (getFragmentTag() == null) {
                throw new IllegalStateException("If a fragment is used, fragment tag must be specified.");
            }

            transaction = startTransaction();

            replaceFragment(getFragment(), android.R.id.content, getFragmentTag());

            transaction.commit();
        }

        onConfigureActionBar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (transaction != null) {
            transaction = null;
        }
    }
}