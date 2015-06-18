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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dabay6.libraries.androidshared.app.ApplicationExtension;
import com.dabay6.libraries.androidshared.interfaces.Injector;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.modules.ActivityModule;
import com.dabay6.libraries.androidshared.util.CollectionUtils;
import com.dabay6.libraries.androidshared.util.NavigationUtils;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

import java.util.List;

import dagger.ObjectGraph;

/**
 * BaseListActivity <p> Provides basic functionality for a {@link ActionBarActivity} that displays a list. </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class BaseListActivity extends ActionBarActivity implements Injector {
    private final static String TAG = Logger.makeTag(BaseListActivity.class);
    private ObjectGraph activityGraph;
    private ListView listView;

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
     * @return
     */
    protected abstract List<Object> getActivityModules();

    /**
     * @return
     */
    protected ListAdapter getListAdapter() {
        ListAdapter adapter = getListView().getAdapter();

        if (adapter instanceof HeaderViewListAdapter) {
            return ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        }

        return adapter;
    }

    protected void setListAdapter(ListAdapter adapter) {
        getListView().setAdapter(adapter);
    }

    /**
     * @return
     */
    protected ListView getListView() {
        final ViewsFinder finder = new ViewsFinder(this);

        if (listView == null) {
            listView = finder.find(android.R.id.list);
        }

        return listView;
    }

    /**
     * Identifies the menu resource to be used by the current activity.
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
    @SuppressWarnings("EmptyMethod")
    protected void initialize(final Bundle savedInstanceState) {
    }

    /**
     * @return
     */
    @SuppressWarnings("SameReturnValue")
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
    @SuppressWarnings("SameReturnValue")
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
     * @param listView
     * @param view
     * @param position
     * @param id
     */
    protected void onListItemClick(final ListView listView, final View view, final int position, final long id) {
        getListView().getOnItemClickListener().onItemClick(listView, view, position, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();
        populateList();
    }

    /**
     *
     */
    protected abstract void populateList();

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