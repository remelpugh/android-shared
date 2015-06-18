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
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.app.ApplicationExtension;
import com.dabay6.libraries.androidshared.app.FragmentFinder;
import com.dabay6.libraries.androidshared.interfaces.Injector;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.modules.ActivityModule;
import com.dabay6.libraries.androidshared.util.CollectionUtils;
import com.dabay6.libraries.androidshared.util.NavigationUtils;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

import java.util.List;

import dagger.ObjectGraph;

/**
 * @author Remel Pugh
 */
@SuppressWarnings("unused")
public abstract class BaseRecyclerActivity extends ActionBarActivity implements Injector {
    private final static String TAG = Logger.makeTag(BaseRecyclerActivity.class);
    protected Bundle activityState;
    protected ViewsFinder finder;
    protected FragmentFinder fragmentFinder;
    private ObjectGraph activityGraph;
    private RecyclerView.Adapter<?> adapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

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
    public boolean onCreateOptionsMenu(final Menu menu) {
        final Integer resourceId = getMenuResource();

        if (resourceId != null) {
            getMenuInflater().inflate(getMenuResource(), menu);
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
    public void setContentView(final View view, final ViewGroup.LayoutParams params) {
        super.setContentView(view, params);

        afterSetContentView();
    }

    /**
     *
     */
    protected void afterSetContentView() {
        final RecyclerView.ItemAnimator animator;

        finder = new ViewsFinder(this);
        fragmentFinder = new FragmentFinder(this);

        recyclerView = finder.find(R.id.util__recycler_view);
        if (recyclerView == null) {
            throw new IllegalStateException("Unable to find a RecyclerView with id of @id/util__recycler_view");
        }

        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(getAdapter());

        animator = getItemAnimator();
        if (animator != null) {
            recyclerView.setItemAnimator(animator);
        }

        afterViews(activityState);
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
    protected abstract RecyclerView.Adapter<?> getAdapter();

    /**
     * @return
     */
    protected abstract RecyclerView.ItemAnimator getItemAnimator();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    /**
     * Identifies the layout resource to be used by the current activity.
     *
     * @return The resource identifier for the layout
     */
    protected Integer getLayoutResource() {
        return R.layout.util__activity_recycler_view;
    }

    /**
     * Identifies the menu resource to be used by the current activity.
     *
     * @return The resource identifier for the menu
     */
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
    protected boolean isHomeButtonEnabled() {
        return true;
    }

    /**
     * @return
     */
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
     * Sets the default configuration for the activity {@link android.support.v7.app.ActionBar}.
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

        if (getLayoutResource() == null) {
            throw new IllegalStateException("A layout must be specified.");
        }

        setContentView(getLayoutResource());
        setToolbar();
        onConfigureActionBar();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as soon as possible.
        activityGraph = null;

        super.onDestroy();
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
            final ApplicationExtension application = (ApplicationExtension) getApplication();
            final List<Object> modules = CollectionUtils.newList();

            // Create the activity graph by .plus-ing our modules onto the application graph.
            modules.add(new ActivityModule(this));
            modules.addAll(getActivityModules());

            activityGraph = application.getObjectGraph().plus(modules.toArray());

            // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
            activityGraph.inject(this);
        }
    }
}
