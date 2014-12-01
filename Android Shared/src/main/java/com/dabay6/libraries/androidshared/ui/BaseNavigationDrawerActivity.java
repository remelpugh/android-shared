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

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.dabay6.libraries.androidshared.R.drawable;
import com.dabay6.libraries.androidshared.R.id;
import com.dabay6.libraries.androidshared.R.layout;
import com.dabay6.libraries.androidshared.R.string;
import com.dabay6.libraries.androidshared.adapters.BaseNavigationListItem;
import com.dabay6.libraries.androidshared.adapters.NavigationDrawerAdapter;
import com.dabay6.libraries.androidshared.adapters.NavigationDrawerCategory;
import com.dabay6.libraries.androidshared.adapters.NavigationDrawerItem;
import com.dabay6.libraries.androidshared.app.FragmentFinder;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

import java.util.List;

import static android.widget.AdapterView.OnItemClickListener;

/**
 * BaseNavigationDrawerActivity
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class BaseNavigationDrawerActivity extends BaseFragmentActivity {
    private final static String TAG = Logger.makeTag(BaseNavigationDrawerActivity.class);
    private NavigationDrawerAdapter adapter;
    private DrawerLayout drawerLayout;
    private CharSequence drawerTitle;
    private ActionBarDrawerToggle drawerToggle;
    private ListView navigationList;
    private CharSequence subtitle;
    private CharSequence title;

    /**
     * Gets the {@link android.support.v4.widget.DrawerLayout} control.
     *
     * @return A {@link android.support.v4.widget.DrawerLayout}.
     */
    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    /**
     * Gets the {@link android.widget.ListView} that represents the navigation menu.
     *
     * @return A {@link android.widget.ListView}.
     */
    public ListView getNavigationList() {
        return navigationList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isDrawerOpen()) {
                drawerLayout.closeDrawer(navigationList);
            }
            else {
                drawerLayout.openDrawer(navigationList);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        onHideOptionsMenuItems(menu, isDrawerOpen());

        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTitle(final CharSequence title) {
        this.title = title;
        getSupportActionBar().setTitle(title);
    }

    /**
     * @param items
     */
    protected void addAllNavigationItems(final List<BaseNavigationListItem> items) {
        if (adapter != null) {
            adapter.addAll(items);
        }
    }

    /**
     * @param title
     */
    protected void addNavigationCategory(final CharSequence title) {
        if (adapter != null) {
            adapter.addCategory(title);
        }
    }

    /**
     * @param title
     */
    protected void addNavigationItem(final CharSequence title) {
        addNavigationItem(title, null);
    }

    /**
     * @param title
     * @param iconResourceId
     */
    protected void addNavigationItem(final CharSequence title, final Integer iconResourceId) {
        if (adapter != null) {
            adapter.add(title, iconResourceId);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void afterSetContentView() {
        final ActionBar actionBar = getSupportActionBar();

        finder = new ViewsFinder(this);
        fragmentFinder = new FragmentFinder(this);

        if (finder.find(getContentResourceId()) == null) {
            throw new IllegalStateException("Unable to find content FrameLayout with the specified id");
        }
        if (finder.find(id.utils__drawer_layout) == null) {
            throw new IllegalStateException("Layout must have a DrawerLayout with id of @id/utils__drawer_layout");
        }
        if (finder.find(id.utils__navigation_list) == null) {
            throw new IllegalStateException("Layout must have a ListView with id of @id/utils__navigation_list");
        }

        drawerLayout = finder.find(id.utils__drawer_layout);
        drawerLayout.setDrawerShadow(drawable.drawer_shadow, GravityCompat.START);

        adapter = new NavigationDrawerAdapter(this, createNavigationItems());

        navigationList = finder.find(id.utils__navigation_list);
        navigationList.setAdapter(adapter);
        navigationList.setOnItemClickListener(new DrawerItemClickListener());

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, drawable.ic_drawer, string.utils__drawer_open,
                                                 string.utils__drawer_close) {
            @Override
            public void onDrawerClosed(final View view) {
                onNavigationDrawerClosed();

                actionBar.setTitle(title);

                if (!TextUtils.isEmpty(subtitle)) {
                    actionBar.setSubtitle(subtitle);
                }

                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(final View drawerView) {
                onNavigationDrawerOpened();

                subtitle = actionBar.getSubtitle();

                actionBar.setTitle(drawerTitle);
                actionBar.setSubtitle(null);

                supportInvalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        if (activityState == null) {
            final List<BaseNavigationListItem> items = adapter.getItems();

            if (items != null && items.size() > 0) {
                for (int i = 0, length = items.size(); i < length; i += 1) {
                    final BaseNavigationListItem item = items.get(i);

                    if (item instanceof NavigationDrawerItem) {
                        selectItem(i, false);
                        break;
                    }
                }
            }
        }

        afterViews(activityState);
    }

    /**
     * @return
     */
    protected abstract List<BaseNavigationListItem> createNavigationItems();

    /**
     * @param position
     * @return
     */
    protected int getActualPosition(final int position) {
        int length = adapter.getCount();
        int count = 0;

        length = (length < position) ? length : position;

        for (int i = 0; i < length; i++) {
            final BaseNavigationListItem item = adapter.getItem(i);

            if (item instanceof NavigationDrawerCategory) {
                count += 1;
            }
        }

        return (position - count < 0) ? 0 : position - count;
    }

    /**
     * The id of the content layout.
     *
     * @return A id resource identifier.
     */
    protected int getContentResourceId() {
        return id.utils__content_frame;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getLayoutResource() {
        return layout.util__navigation_drawer;
    }

    /**
     * Indicates whether the drawer is open or not.
     *
     * @return True if the drawer is open, otherwise false.
     */
    protected boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(navigationList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isHomeAsUpEnabled() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isTitleEnabled() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = drawerTitle = getTitle();
    }

    /**
     * Determine action bar items visibility based on whether or not the navigation drawer is open.
     *
     * @param isDrawerOpen True if the navigation drawer is open, otherwise false.
     */
    protected abstract void onHideOptionsMenuItems(final Menu menu, final boolean isDrawerOpen);

    /**
     * Executed when the navigation drawer is closed.
     */
    protected abstract void onNavigationDrawerClosed();

    /**
     * @param position The position of the selected navigation item.
     */
    protected abstract void onNavigationDrawerItemSelected(final int position);

    /**
     * Executed when the navigation drawer is opened.
     */
    protected abstract void onNavigationDrawerOpened();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        drawerToggle.syncState();
    }

    /**
     * @param position
     */
    protected void selectItem(final int position) {
        selectItem(position, true);
    }

    /**
     * @param position
     * @param notifySelection
     */
    protected void selectItem(final int position, final boolean notifySelection) {
        final int actualPosition = getActualPosition(position);

        navigationList.setItemChecked(actualPosition, true);

        if (notifySelection) {
            onNavigationDrawerItemSelected(actualPosition);
            drawerLayout.closeDrawer(navigationList);
        }
    }

    private class DrawerItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            selectItem(position);
        }
    }
}