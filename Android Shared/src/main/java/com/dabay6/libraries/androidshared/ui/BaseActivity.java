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

package com.dabay6.libraries.androidshared.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.app.ApplicationExtension;
import com.dabay6.libraries.androidshared.app.FragmentFinder;
import com.dabay6.libraries.androidshared.interfaces.FragmentActivityBase;
import com.dabay6.libraries.androidshared.interfaces.FragmentBase;
import com.dabay6.libraries.androidshared.interfaces.Injector;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.modules.ActivityModule;
import com.dabay6.libraries.androidshared.util.CollectionUtils;
import com.dabay6.libraries.androidshared.util.NavigationUtils;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

import java.util.List;

import dagger.ObjectGraph;

/**
 * BaseFragmentActivity <p> Provides basic functionality for a {@link ActionBarActivity}. </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class BaseActivity extends ActionBarActivity
        implements FragmentActivityBase, Injector, FragmentManager.OnBackStackChangedListener {
    private final static String TAG = Logger.makeTag(BaseActivity.class);
    protected Bundle activityState;
    protected ViewsFinder finder;
    protected FragmentFinder fragmentFinder;
    private ObjectGraph activityGraph;
    private boolean isDualPane = false;
    private Spinner spinner;
    private View spinnerView;
    private Toolbar toolbar;
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
    public ObjectGraph getObjectGraph() {
        return activityGraph;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inject(Object target) {
        // ensure object graph has been created
        buildGraph();

        activityGraph.inject(target);
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
     * @param adapter
     */
    protected void addNavigationSpinner(final BaseAdapter adapter) {
        addNavigationSpinner(adapter, null);
    }

    /**
     * @param adapter
     * @param listener
     */
    protected void addNavigationSpinner(final BaseAdapter adapter, final AdapterView.OnItemSelectedListener listener) {
        if (toolbar == null) {
            return;
        }

        final ActionBar.LayoutParams params = new ActionBar.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);

        if (spinnerView == null) {
            spinnerView = LayoutInflater.from(this).inflate(R.layout.util__toolbar_spinner, toolbar, false);
        }

        toolbar.removeView(spinnerView);
        toolbar.addView(spinnerView, params);

        if (spinner == null) {
            spinner = (Spinner) spinnerView.findViewById(R.id.util__toolbar_spinner);
        }

        if (spinner != null) {
            spinner.setAdapter(adapter);

            if (listener != null) {
                spinner.setOnItemSelectedListener(listener);
            }
        }
    }

    /**
     *
     */
    protected void afterSetContentView() {
        finder = new ViewsFinder(this);
        fragmentFinder = new FragmentFinder(this);

        setToolbar();

        afterViews(activityState);

        onConfigureActionBar();
    }

    /**
     * Executed after the activity's layout has been assigned
     */
    protected abstract void afterViews(Bundle savedInstanceState);

    /**
     * @return
     */
    protected abstract List<Object> getActivityModules();

    /**
     * @return
     */
    protected <T extends Fragment> T getFragment() {
        return null;
    }

    /**
     * @return
     */
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
     * @return
     */
    protected Toolbar getToolbar() {
        return toolbar;
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
     * Determines if the application title is displayed by default it is not displayed.
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
        buildGraph();

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

            afterSetContentView();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as soon as possible.
        activityGraph = null;

        super.onDestroy();

        if (transaction != null) {
            transaction = null;
        }
    }

    /**
     *
     */
    protected void removeNavigationSpinner() {
        if (spinnerView == null) {
            return;
        }

        toolbar.removeView(spinnerView);
    }

    /**
     */
    protected void setToolbar() {
        toolbar = finder.find(R.id.util__toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    private void buildGraph() {
        if (activityGraph == null) {
            final List<Object> activityModules = getActivityModules();
            final ApplicationExtension application = (ApplicationExtension) getApplication();
            final List<Object> modules = CollectionUtils.newList();

            // Create the activity graph by .plus-ing our modules onto the application graph.
            modules.add(new ActivityModule(this));
            if (activityModules != null) {
                modules.addAll(getActivityModules());
            }

            activityGraph = application.getObjectGraph().plus(modules.toArray());

            // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
            activityGraph.inject(this);
        }
    }
}