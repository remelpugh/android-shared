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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.ListUtils;

import java.util.List;

/**
 * BaseFragmentTabNavigationActivity
 * <p>
 * Provides basic functionality for a {@link android.support.v7.app.ActionBarActivity} using tab based navigation
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class BaseFragmentTabNavigationActivity extends BaseFragmentActivity
        implements TabListener, ViewPager.OnPageChangeListener {
    private static final String KEY_TAB = "SELECTED_TAB";
    private final static String TAG = Logger.makeTag(BaseFragmentTabNavigationActivity.class);
    protected int position = -1;
    protected List<String> tabNames;
    private List<NavigationTab> navigationTabs;
    private ViewPager pager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPageScrollStateChanged(final int state) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPageSelected(final int position) {
        setSelectedNavigationItem(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(final Bundle state) {
        state.putInt(KEY_TAB, getSupportActionBar().getSelectedNavigationIndex());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTabSelected(final Tab tab, final FragmentTransaction ft) {
        if (pager != null) {
            for (int i = 0; i < navigationTabs.size(); i++) {
                final NavigationTab navigationTab = navigationTabs.get(i);

                if (navigationTab.text.equalsIgnoreCase(tab.getText().toString())) {
                    pager.setCurrentItem(i, true);
                    break;
                }
            }
        }
    }

    /**
     * @param tabNames The list of tab names.
     */
    protected abstract List<NavigationTab> generateTabs(final List<String> tabNames);

    /**
     * @return
     */
    @Override
    protected final <T extends Fragment> T getFragment() {
        return null;
    }

    /**
     * @return
     */
    @Override
    protected String getFragmentTag() {
        return null;
    }

    /**
     * @return
     */
    protected Integer getLayoutResource() {
        return R.layout.util__activity_tabpager;
    }

    /**
     * Identifies the array resource to be used to populate the {@link Tab}s
     *
     * @return The navigation resource.
     */
    protected abstract Integer getNavigationResource();

    /**
     * @param savedInstanceState The saved state of the activity.
     */
    protected void initialize(final Bundle savedInstanceState) {
        super.initialize(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_TAB)) {
            position = savedInstanceState.getInt(KEY_TAB);
        }
    }

    /**
     * Sets the default configuration for the activity {@link ActionBar}.
     */
    protected void onConfigureActionBar() {
        final ActionBar actionBar = getSupportActionBar();

        super.onConfigureActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        if (navigationTabs != null) {
            for (final NavigationTab navigationTab : navigationTabs) {
                final Tab tab = actionBar.newTab();

                tab.setTabListener(this);
                tab.setText(navigationTab.text);

                actionBar.addTab(tab);
            }
        }

        pager = finder.find(R.id.pager);
        if (pager != null) {
            final TabsAdapter adapter;

            adapter = new TabsAdapter(this, navigationTabs);

            pager.setAdapter(adapter);
            pager.setOnPageChangeListener(this);
        }

        setSelectedNavigationItem(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        tabNames = ListUtils.asList(getResources().getStringArray(getNavigationResource()));

        navigationTabs = generateTabs(tabNames);

        super.onCreate(savedInstanceState);
    }

    /**
     * Set the selected navigation item in list or tabbed navigation modes.
     *
     * @param position Position of the item to select.
     */
    protected void setSelectedNavigationItem(final int position) {
        if (position < 0) {
            return;
        }

        getSupportActionBar().setSelectedNavigationItem(position);
    }

    /**
     *
     */
    private static class TabsAdapter extends FragmentStatePagerAdapter {
        private final Context context;
        private List<NavigationTab> tabs = ListUtils.newList();

        /**
         * @param activity The {@link ActionBarActivity}
         * @param tabs     The collection of tabs
         */
        public TabsAdapter(final ActionBarActivity activity, final List<NavigationTab> tabs) {
            super(activity.getSupportFragmentManager());

            this.context = activity;
            this.tabs = tabs;
        }

        /**
         * @param tab The {@link NavigationTab} to be added.
         */
        @SuppressWarnings("unused")
        public void addTab(final NavigationTab tab) {
            tabs.add(tab);

            notifyDataSetChanged();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getCount() {
            return tabs.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Fragment getItem(final int position) {
            final NavigationTab tab = tabs.get(position);

            return Fragment.instantiate(context, tab.type.getName(), tab.args);
        }
    }

    /**
     * NavigationTab
     *
     * @author Remel Pugh
     * @version 1.0
     */
    public static final class NavigationTab {
        private final Bundle args;
        private final String text;
        private final Class<?> type;

        /**
         * @param type The type of fragment that will be loaded.
         * @param args The arguments to be passed to the fragment.
         * @param text The text to be displayed on the tab.
         */
        public NavigationTab(final Class<?> type, final Bundle args, final String text) {
            this.args = args;
            this.text = text;
            this.type = type;
        }
    }
}