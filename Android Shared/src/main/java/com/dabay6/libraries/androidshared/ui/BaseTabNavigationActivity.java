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

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.CollectionUtils;
import com.dabay6.libraries.androidshared.widget.SlidingTabLayout;

import java.util.List;

/**
 * BaseFragmentTabNavigationActivity <p> Provides basic functionality for a {@link
 * android.support.v7.app.ActionBarActivity} using tab based navigation </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class BaseTabNavigationActivity extends BaseActivity {
    private static final String KEY_TAB = "SELECTED_TAB";
    private final static String TAG = Logger.makeTag(BaseTabNavigationActivity.class);
    protected int position = -1;
    protected List<String> tabNames;
    private List<NavigationTab> navigationTabs;

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
    protected void afterViews(Bundle savedInstanceState) {
        final TabsAdapter adapter;
        final ViewPager pager;
        final Resources resources = getResources();
        final SlidingTabLayout slidingTabLayout;

        if (finder.find(R.id.util__toolbar_tabs) == null) {
            throw new IllegalStateException("Unable to find sliding tab strip layout");
        }

        if (finder.find(R.id.util__sliding_tab_strip) == null) {
            throw new IllegalStateException("Unable to find view pager");
        }

        adapter = new TabsAdapter(this, navigationTabs);

        pager = finder.find(R.id.util__sliding_tab_strip);
        pager.setAdapter(adapter);

        slidingTabLayout = finder.find(R.id.util__toolbar_tabs);
        slidingTabLayout.setCustomTabView(R.layout.util__tab_indicator, android.R.id.text1);
        slidingTabLayout.setSelectedIndicatorColors(resources.getColor(R.color.util__accent_color));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(pager);

        pager.setCurrentItem(position, true);
//        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrollStateChanged(int state) {
////                enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset,
//                                       int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (AndroidUtils.isAtLeastJellyBean()) {
////                    slidingTabLayout.announceForAccessibility(
////                            getString(R.string.my_schedule_page_desc_a11y,
////                                    getDayName(position)));
//                }
//            }
//        });
    }

    /**
     * @param tabNames The list of tab names.
     */
    protected abstract List<NavigationTab> generateTabs(final List<String> tabNames);

    /**
     * @return
     */
    protected Integer getLayoutResource() {
        return R.layout.util__activity_tabpager;
    }

    /**
     * Identifies the array resource to be used to populate the tabs.
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
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        tabNames = CollectionUtils.asList(getResources().getStringArray(getNavigationResource()));

        navigationTabs = generateTabs(tabNames);

        super.onCreate(savedInstanceState);
    }

    /**
     *
     */
    private static class TabsAdapter extends FragmentStatePagerAdapter {
        private final Context context;
        private List<NavigationTab> tabs = CollectionUtils.newList();

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

        @Override
        public int getCount() {
            return tabs.size();
        }

        @Override
        public Fragment getItem(final int position) {
            final NavigationTab tab = tabs.get(position);

            return Fragment.instantiate(context, tab.type.getName(), tab.args);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position).text;
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