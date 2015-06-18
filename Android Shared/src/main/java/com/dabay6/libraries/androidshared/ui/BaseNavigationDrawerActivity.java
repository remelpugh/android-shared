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

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.R.drawable;
import com.dabay6.libraries.androidshared.R.id;
import com.dabay6.libraries.androidshared.R.layout;
import com.dabay6.libraries.androidshared.R.string;
import com.dabay6.libraries.androidshared.app.FragmentFinder;
import com.dabay6.libraries.androidshared.content.IntentBuilder;
import com.dabay6.libraries.androidshared.helper.BaseLoginAndAuthHelper;
import com.dabay6.libraries.androidshared.helper.GoogleLoginAndAuthHelper;
import com.dabay6.libraries.androidshared.helper.SystemServiceHelper;
import com.dabay6.libraries.androidshared.helper.ToastHelper;
import com.dabay6.libraries.androidshared.interfaces.LoginAndAuthCallbacks;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.ui.navigation.NavigationItem;
import com.dabay6.libraries.androidshared.ui.navigation.NavigationItemListener;
import com.dabay6.libraries.androidshared.util.AccountUtils;
import com.dabay6.libraries.androidshared.util.CollectionUtils;
import com.dabay6.libraries.androidshared.util.GoogleAccountUtils;
import com.dabay6.libraries.androidshared.util.ViewUtils;
import com.dabay6.libraries.androidshared.view.ViewsFinder;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

/**
 * BaseNavigationDrawerActivity
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class BaseNavigationDrawerActivity extends BaseActivity implements NavigationItemListener {
    private final static int ACCOUNT_BOX_EXPAND_ANIM_DURATION = 200;
    private final static String BACK_STACK_NAME = "com.dabay6.libraries.androidshared.back.stack.name";
    private final static int BOTTOM_ITEMS_START = 100;
    private final static String TAG = Logger.makeTag(BaseNavigationDrawerActivity.class);
    @Inject
    Picasso picasso;
    private ImageView accountBoxIndicator;
    private FrameLayout accountChooser;
    private LinearLayout accountListContainer;
    private List<NavigationItem<?>> bottomItemList;
    private LinearLayout bottomItemsContainer;
    private ViewGroup content;
    private NavigationItem<?> currentItem;
    private float density;
    private RelativeLayout drawer;
    private DrawerLayout drawerLayout;
    private CharSequence drawerTitle;
    private ActionBarDrawerToggle drawerToggle;
    private TextView email;
    private boolean isAccountBoxExpanded = false;
    private int itemColor;
    private List<NavigationItem<?>> itemList;
    private LinearLayout itemsContainer;
    private BaseLoginAndAuthHelper loginAndAuthHelper;
    private TextView name;
    private Stack<NavigationItem<?>> navigationItemStack;
    private int primaryColor;
    private ImageView profileImage;
    private Resources resources;
    private CharSequence subtitle;
    private Resources.Theme theme;
    private CharSequence title;
    private boolean usesAccounts = true;
    private boolean usesSupportFragments = false;

    //region ADD ITEMS

    /**
     * Add a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to the bottom section of the
     * drawer.
     *
     * @param item The {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem to be added.
     */
    public void addBottomItem(NavigationItem item) {
        final LinearLayout.LayoutParams params;

        item.setPosition(BOTTOM_ITEMS_START + bottomItemList.size());

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * density));

        bottomItemList.add(item);
        bottomItemsContainer.addView(item.getView(), params);
    }

    /**
     * Adds a divider between navigation items.
     */
    public void addDivider() {
        final LinearLayout.LayoutParams params;
        final View view =
                LayoutInflater.from(this).inflate(layout.util__navigation_drawer_item_separator, itemsContainer,
                        false);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.setMargins(0, (int) (8 * density), 0, (int) (8 * density));

        itemsContainer.addView(view, params);
    }

    /**
     * Add a header to a section of {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}.
     *
     * @param title The title of the header.
     */
    public void addHeader(CharSequence title) {
        final View divider =
                LayoutInflater.from(this).inflate(layout.util__navigation_drawer_item_separator, itemsContainer,
                        false);
        final View header =
                LayoutInflater.from(this).inflate(layout.util__navigation_drawer_header, itemsContainer, false);
        final LinearLayout.LayoutParams params;
        final TextView text = (TextView) header.findViewById(id.util__drawer_header_text);

        text.setText(title);

        params = (LinearLayout.LayoutParams) divider.getLayoutParams();
        params.setMargins(0, (int) (8 * density), 0, 0);

        itemsContainer.addView(divider, params);
        itemsContainer.addView(header);
    }

    /**
     * Add a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to the drawer.
     *
     * @param item The {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem to be added.
     */
    public void addItem(NavigationItem item) {
        final LinearLayout.LayoutParams params;

        item.setPosition(itemList.size());

        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (48 * density));
        itemList.add(item);

        itemsContainer.addView(item.getView(), params);
    }

    //endregion ADD ITEMS

    //region CREATE ITEMS

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param icon   The icon of the item.
     * @param target The {@link android.app.Fragment} that will be added on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<android.app.Fragment> createItem(String title, Drawable icon, android.app.Fragment target) {
        NavigationItem<android.app.Fragment> item = new NavigationItem<>(this, true);

        item.setOnClickListener(this);
        item.setIcon(icon);
        item.setTitle(title);
        item.setTarget(target);

        return item;
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param icon   The icon of the item.
     * @param target The {@link android.support.v4.app.Fragment} that will be added on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<android.support.v4.app.Fragment> createItem(String title, Drawable icon,
                                                                      android.support.v4.app.Fragment target) {
        NavigationItem<android.support.v4.app.Fragment> item = new NavigationItem<>(this, true);

        item.setOnClickListener(this);
        item.setIcon(icon);
        item.setTitle(title);
        item.setTarget(target);

        return item;
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param icon   The icon of the item.
     * @param target The {@link android.content.Intent} that will be started on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<Intent> createItem(String title, Drawable icon, Intent target) {
        final NavigationItem<Intent> item = new NavigationItem<>(this, true);

        item.setIcon(icon);
        item.setOnClickListener(this);
        item.setTarget(target);
        item.setTitle(title);

        return item;
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param icon   The icon of the item.
     * @param target The {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItemListener} that will be
     *               called on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<NavigationItemListener> createItem(String title, Drawable icon,
                                                             NavigationItemListener target) {
        final NavigationItem<NavigationItemListener> item = new NavigationItem<>(this, true);

        item.setIcon(icon);
        item.setOnClickListener(this);
        item.setTarget(target);
        item.setTitle(title);

        return item;
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param icon   The icon of the item.
     * @param target The {@link android.app.Fragment} that will be added on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<android.app.Fragment> createItem(String title, Bitmap icon, android.app.Fragment target) {
        final NavigationItem<android.app.Fragment> section = new NavigationItem<>(this, true);

        section.setOnClickListener(this);
        section.setIcon(icon);
        section.setTitle(title);
        section.setTarget(target);

        return section;
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param icon   The icon of the item.
     * @param target The {@link android.support.v4.app.Fragment} that will be added on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<android.support.v4.app.Fragment> createItem(String title, Bitmap icon,
                                                                      android.support.v4.app.Fragment target) {
        final NavigationItem<android.support.v4.app.Fragment> section = new NavigationItem<>(this, true);

        section.setOnClickListener(this);
        section.setIcon(icon);
        section.setTitle(title);
        section.setTarget(target);

        return section;
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param icon   The icon of the item.
     * @param target The {@link android.content.Intent} that will be started on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<Intent> createItem(String title, Bitmap icon, Intent target) {
        final NavigationItem<Intent> item = new NavigationItem<>(this, true);

        item.setIcon(icon);
        item.setOnClickListener(this);
        item.setTarget(target);
        item.setTitle(title);

        return item;
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param icon   The icon of the item.
     * @param target The {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItemListener} that will be
     *               called on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<NavigationItemListener> createItem(String title, Bitmap icon, NavigationItemListener target) {
        final NavigationItem<NavigationItemListener> item = new NavigationItem<>(this, true);

        item.setIcon(icon);
        item.setOnClickListener(this);
        item.setTarget(target);
        item.setTitle(title);

        return item;
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param icon   The icon of the item.
     * @param target The {@link android.app.Fragment} that will be added on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<android.app.Fragment> createItem(String title, int icon, android.app.Fragment target) {
        return createItem(title, resources.getDrawable(icon), target);
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param icon   The icon of the item.
     * @param target The {@link android.support.v4.app.Fragment} that will be added on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<android.support.v4.app.Fragment> createItem(String title, int icon,
                                                                      android.support.v4.app.Fragment target) {
        return createItem(title, resources.getDrawable(icon), target);
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param icon   The icon of the item.
     * @param target The {@link android.content.Intent} that will be started on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<Intent> createItem(String title, int icon, Intent target) {
        return createItem(title, resources.getDrawable(icon), target);
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param icon   The icon of the item.
     * @param target The {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItemListener} that will be
     *               called on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<NavigationItemListener> createItem(String title, int icon, NavigationItemListener target) {
        return createItem(title, resources.getDrawable(icon), target);
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param target The {@link android.app.Fragment} that will be added on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<android.app.Fragment> createItem(String title, android.app.Fragment target) {
        final NavigationItem<android.app.Fragment> item = new NavigationItem<>(this, false);

        item.setOnClickListener(this);
        item.setTitle(title);
        item.setTarget(target);

        return item;
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param target The {@link android.support.v4.app.Fragment} that will be added on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<android.support.v4.app.Fragment> createItem(String title,
                                                                      android.support.v4.app.Fragment target) {
        final NavigationItem<android.support.v4.app.Fragment> item = new NavigationItem<>(this, false);

        item.setOnClickListener(this);
        item.setTitle(title);
        item.setTarget(target);

        return item;
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param target The {@link android.content.Intent} that will be started on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<Intent> createItem(String title, Intent target) {
        final NavigationItem<Intent> item = new NavigationItem<>(this, false);

        item.setOnClickListener(this);
        item.setTarget(target);
        item.setTitle(title);

        return item;
    }

    /**
     * Create a {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to be added to the drawer.
     *
     * @param title  The title of the item.
     * @param target The {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItemListener} that will be
     *               called on click.
     *
     * @return A {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}
     */
    public NavigationItem<NavigationItemListener> createItem(String title, NavigationItemListener target) {
        final NavigationItem<NavigationItemListener> item = new NavigationItem<>(this, false);

        item.setOnClickListener(this);
        item.setTarget(target);
        item.setTitle(title);

        return item;
    }

    //endregion CREATE ITEMS

    /**
     * Gets the {@link android.support.v4.widget.DrawerLayout} control.
     *
     * @return A {@link android.support.v4.widget.DrawerLayout}.
     */
    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!navigationItemStack.empty()) {
            navigationItemStack.pop();

            if (!navigationItemStack.empty()) {
                final NavigationItem<?> item = navigationItemStack.peek();
                final Object target = item.getTarget();

                item.select();
                setTitleByItem(item);

                if (!(target instanceof Intent) && !(target instanceof NavigationItemListener)) {
                    updateSelectedItem(item);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackStackChanged() {
        final int count;

        if (usesSupportFragments) {
            count = getSupportFragmentManager().getBackStackEntryCount();
        }
        else {
            count = getFragmentManager().getBackStackEntryCount();
        }

        if (count >= navigationItemStack.size()) {
            navigationItemStack.push(null);
        }

        super.onBackStackChanged();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(NavigationItem<?> selected) {
        final Object target = selected.getTarget();

        if (target instanceof android.app.Fragment) {
            if (currentItem.getTarget() instanceof android.app.Fragment) {
                setFragment(target, selected, currentItem.getTarget());
            }
            else {
                setFragment(target, selected, null);
            }
        }

        if (target instanceof android.support.v4.app.Fragment) {
            if (currentItem.getTarget() instanceof android.support.v4.app.Fragment) {
                setFragment(target, selected, currentItem.getTarget());
            }
            else {
                setFragment(target, selected, null);
            }
        }

        if (target instanceof Intent) {
            this.startActivity((Intent) target);
        }

        updateSelectedItem(selected);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (drawerToggle != null) {
            drawerToggle.onConfigurationChanged(newConfig);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (drawerToggle != null) {
            if (drawerToggle.onOptionsItemSelected(item)) {
                return true;
            }
        }

        if (item.getItemId() == android.R.id.home) {
            if (isDrawerOpen()) {
                drawerLayout.closeDrawer(drawer);
            }
            else {
                drawerLayout.openDrawer(drawer);
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
    public void onStop() {
        Logger.debug(TAG, "onStop");
        super.onStop();
        if (loginAndAuthHelper != null) {
            loginAndAuthHelper.stop();
        }
    }

    /**
     * Select bottom navigation item by position.
     *
     * @param position The position of the item.
     */
    public void selectBottomItem(final int position) {
        selectBottomItem(bottomItemList.get(position));
    }

    /**
     * Select bottom navigation item by {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}.
     *
     * @param item The {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to select.
     */
    public void selectBottomItem(final NavigationItem<?> item) {
        deselectAll();
        for (final NavigationItem<?> i : bottomItemList) {
            if (i.equals(item)) {
                i.select();
            }
        }
    }

    /**
     * Select navigation item by position.
     *
     * @param position The position of the item.
     */
    public void selectItem(final int position) {
        selectItem(itemList.get(position));
    }

    /**
     * Select navigation item by {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem}.
     *
     * @param item The {@link com.dabay6.libraries.androidshared.ui.navigation.NavigationItem} to select.
     */
    public void selectItem(final NavigationItem<?> item) {
        deselectAll();
        for (final NavigationItem<?> i : itemList) {
            if (i.equals(item)) {
                i.select();
            }
        }
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
     * Add {@link android.accounts.Account}s to the account selector.
     *
     * @return A collection of {@link android.accounts.Account}s.
     */
    protected List<Account> addAccounts() {
        final AccountManager manager = SystemServiceHelper.with(this).account();
        final Account[] accounts = manager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);

        return CollectionUtils.asList(accounts);
    }

    /**
     * Add navigation items to the drawer.
     */
    protected abstract void addNavigationItems();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void afterSetContentView() {
        final TypedValue typedValue = new TypedValue();

        finder = new ViewsFinder(this);
        fragmentFinder = new FragmentFinder(this);

        setToolbar();

        if (finder.find(id.util__drawer) == null) {
            throw new IllegalStateException("Unable to find a RelativeLayout with id of @id/util__drawer");
        }
        if (finder.find(getContentResourceId()) == null) {
            throw new IllegalStateException("Unable to find content FrameLayout with the specified id");
        }
        if (finder.find(id.util__drawer_layout) == null) {
            throw new IllegalStateException("Layout must have a DrawerLayout with id of @id/utils__drawer_layout");
        }
        if (finder.find(id.util__drawer_items) == null) {
            throw new IllegalStateException("Layout must have a LinearLayout with id of @id/util__drawer_items");
        }

        accountChooser = finder.find(id.util__drawer_chosen_account);
        accountListContainer = finder.find(id.util__drawer_account_list);
        content = finder.find(getContentResourceId());
        drawer = finder.find(id.util__drawer);
        drawerLayout = finder.find(id.util__drawer_layout);
        drawerLayout.setDrawerShadow(drawable.drawer_shadow, GravityCompat.START);
        itemsContainer = finder.find(id.util__drawer_items);
        bottomItemsContainer = (LinearLayout) this.findViewById(id.bottom_sections);

        resources = this.getResources();
        density = resources.getDisplayMetrics().density;

        // get primary color
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        primaryColor = typedValue.data;

        theme.resolveAttribute(R.attr.util__drawer_item_text_color, typedValue, true);
        itemColor = typedValue.data;

        if (drawerLayout.isDrawerOpen(drawer)) {
            drawerLayout.closeDrawer(drawer);
        }

        itemList = CollectionUtils.newList();
        bottomItemList = CollectionUtils.newList();

        populateNavigationDrawer();

        if (itemList.size() == 0) {
            throw new RuntimeException("You must add at least one item to top list.");
        }

        if (!usesAccounts) {
            ViewUtils.setGone(accountChooser);
        }
        else {
            ViewUtils.setVisible(accountChooser);
        }

        if (ViewUtils.isVisible(accountChooser)) {
            setupAccounts();
        }

        final NavigationItem item = itemList.get(0);
        final Object target = item.getTarget();

        currentItem = item;

        item.select();

        if (!(target instanceof Intent) && !(target instanceof NavigationItemListener)) {
            setFragment(item.getTarget(), item, null);
        }

        afterViews(activityState);

        onConfigureActionBar();
    }

    /**
     * @return
     */
    protected LoginAndAuthCallbacks createLoginAndAuthCallbacks() {
        return new LoginAndAuthCallbacks() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void onAccountInfoLoaded(String accountName) {
                setupAccounts();
                populateNavigationDrawer();
            }

            /**
             * @param accountName
             */
            @Override
            public void onAuthFailure(String accountName) {
                Logger.debug(TAG, "Auth failed for account " + accountName);
                refreshAccountDependantData();
            }

            /**
             * @param accountName
             * @param newlyAuthenticated
             */
            @Override
            public void onAuthSuccess(String accountName, boolean newlyAuthenticated) {
                final Account account = new Account(accountName, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
                Logger.debug(TAG,
                        "onAuthSuccess, account " + accountName + ", newlyAuthenticated=" + newlyAuthenticated);

                refreshAccountDependantData();

                if (newlyAuthenticated) {
                    Logger.debug(TAG, "Enabling auto sync on content provider for account " + accountName);
                    //            SyncHelper.updateSyncInterval(this, account);
                    //            SyncHelper.requestManualSync(account);
                }

                setupAccounts();
                populateNavigationDrawer();
                //        registerGCMClient();
            }
        };
    }

    /**
     * @param activity
     * @param callbacks
     * @param accountName
     *
     * @return
     */
    protected BaseLoginAndAuthHelper createLoginAndAuthHelper(final Activity activity,
                                                              final LoginAndAuthCallbacks callbacks,
                                                              final String accountName) {
        return new GoogleLoginAndAuthHelper(activity, callbacks, accountName);
    }

    /**
     * @return
     */
    protected String getAccountType() {
        return GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE;
    }

    /**
     * The id of the content layout.
     *
     * @return A id resource identifier.
     */
    protected int getContentResourceId() {
        return id.util__content_frame;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getLayoutResource() {
        return layout.util__activity_navigation_drawer;
    }

    /**
     * Override to handle the addition of fragments.
     *
     * @return True if fragments have been added, otherwise false.
     */
    protected boolean handleAddFragments(android.app.Fragment fragment,
                                         String title,
                                         android.app.Fragment oldFragment) {
        return false;
    }

    /**
     * Override to handle the addition of fragments.
     *
     * @return True if fragments have been added, otherwise false.
     */
    protected boolean handleAddFragments(android.support.v4.app.Fragment fragment, String title,
                                         android.support.v4.app.Fragment oldFragment) {
        return false;
    }

    /**
     * Indicates whether the drawer is open or not.
     *
     * @return True if the drawer is open, otherwise false.
     */
    protected boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(drawer);
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
     * override if you want to be notified when another account has been selected account has changed
     */
    protected void onAccountChangeRequested() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (loginAndAuthHelper == null || !loginAndAuthHelper.onActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Sets the default configuration for the activity {@link android.support.v7.app.ActionBar}.
     */
    @Override
    protected void onConfigureActionBar() {
        super.onConfigureActionBar();

        final ActionBar actionBar = getSupportActionBar();

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, string.util__drawer_open,
                string.util__drawer_close) {
            @Override
            public void onDrawerClosed(final View view) {
                onNavigationDrawerClosed();

                setTitle(title);

                if (!TextUtils.isEmpty(subtitle)) {
                    actionBar.setSubtitle(subtitle);
                }

                invalidateOptionsMenu();
                //supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(final View drawerView) {
                onNavigationDrawerOpened();

                subtitle = actionBar.getSubtitle();

                setTitle(title);
                actionBar.setSubtitle(null);

                invalidateOptionsMenu();
                //supportInvalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        navigationItemStack = new Stack<>();
        theme = this.getTheme();

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
     * Executed when the navigation drawer is opened.
     */
    protected abstract void onNavigationDrawerOpened();

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (drawerToggle != null) {
            drawerToggle.syncState();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();

        setActionBarColor();
    }

    /**
     * @param chosenAccount
     */
    protected void populateAccountDetails(final Account chosenAccount) {
        if (getAccountType().equalsIgnoreCase(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE)) {
            final String coverImageUrl = GoogleAccountUtils.getPlusCoverUrl(this);
            final String imageUrl = GoogleAccountUtils.getPlusImageUrl(this);
            final String plusName = GoogleAccountUtils.getPlusName(this);

            if (TextUtils.isEmpty(plusName)) {
                ViewUtils.setGone(name);
            }
            else {
                ViewUtils.setVisible(name);
                name.setText(plusName);
            }

            if (!TextUtils.isEmpty(imageUrl)) {
                picasso.load(imageUrl).into(profileImage);
            }

            if (!TextUtils.isEmpty(coverImageUrl)) {
                //mImageLoader.loadImage(coverImageUrl, coverImageView);
            }
            else {
                //coverImageView.setImageResource(R.drawable.default_cover);
            }
        }

        if (email != null && chosenAccount != null) {
            email.setText(chosenAccount.name);
        }
    }

    /**
     *
     */
    protected void refreshAccountDependantData() {
    }

    protected void retryAuth() {
        loginAndAuthHelper.retryAuthByUserRequest();
    }

    /**
     * Indicate if the navigation drawer supports user accounts.
     *
     * @param usesAccounts True if supports accounts, otherwise false.
     */
    protected void setUsesAccounts(final boolean usesAccounts) {
        this.usesAccounts = usesAccounts;
    }

    private void complainNoAccounts() {
        Logger.debug(TAG, "Complaining about missing Google account.");

        new AlertDialog.Builder(this)
                .setTitle(string.util__account_required_title)
                .setMessage(string.util__account_required_message)
                .setPositiveButton(string.util__account_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        promptAddAccount();
                    }
                })
                .setNegativeButton(string.util__account_not_now, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private void deselectAll() {
        for (NavigationItem<?> item : itemList) {
            item.deselect(itemColor);
        }
        for (NavigationItem<?> item : bottomItemList) {
            item.deselect(itemColor);
        }
    }

    /**
     * Returns the default account on the device. We use the rule that the first account should be the default. It's
     * arbitrary, but the alternative would be showing an account chooser popup which wouldn't be a smooth first
     * experience with the app. Since the user can easily switch the account with the nav drawer, we opted for this
     * implementation.
     */
    private String getDefaultAccount() {
        // Choose first account on device.
        Logger.debug(TAG, "Choosing default account (first account on device)");
        final AccountManager manager = SystemServiceHelper.with(this).account();
        final Account[] accounts = manager.getAccountsByType(getAccountType());

        if (accounts.length == 0) {
            Logger.warn(TAG, "No Google accounts on device; not setting default account.");
            return null;
        }

        Logger.debug(TAG, "Default account is: " + accounts[0].name);

        return accounts[0].name;
    }

    private void populateAccountList(List<Account> accounts) {
        final LayoutInflater layoutInflater = LayoutInflater.from(this);

        accountListContainer.removeAllViews();

        for (Account account : accounts) {
            final String accountName = account.name;
            //String imageUrl = AccountUtils.getPlusImageUrl(this, accountName);
            final View itemView =
                    layoutInflater.inflate(layout.util__navigation_account_item, accountListContainer, false);

            ((TextView) itemView.findViewById(R.id.util__drawer_account_item_text)).setText(account.name);

//            if (!TextUtils.isEmpty(imageUrl)) {
//                //mImageLoader.loadImage(imageUrl, (ImageView) itemView.findViewById(R.id.profile_image));
//            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ConnectivityManager cm = SystemServiceHelper.with(BaseNavigationDrawerActivity.this)
                                                                      .connectivity();
                    final NetworkInfo network = cm.getActiveNetworkInfo();

                    if (network == null || !network.isConnected()) {
                        ToastHelper.with(BaseNavigationDrawerActivity.this).show(string.util__no_connection_cant_login,
                                Toast.LENGTH_SHORT);
                    }
                    else {
                        AccountUtils.setActiveAccount(getApplicationContext(), accountName);
                        onAccountChangeRequested();
                        startLoginProcess();

                        isAccountBoxExpanded = false;
                        setupAccountBoxToggle();
                        setupAccounts();
                    }

                    drawerLayout.closeDrawer(drawer);
                }
            });

            accountListContainer.addView(itemView);
        }
    }

    private void populateNavigationDrawer() {
        bottomItemsContainer.removeAllViews();
        itemsContainer.removeAllViews();

        addNavigationItems();
    }

    private void promptAddAccount() {
        final Intent intent = IntentBuilder.with(Settings.ACTION_ADD_ACCOUNT)
                                           .add(Settings.EXTRA_ACCOUNT_TYPES, new String[]{getAccountType()})
                                           .build();

        startActivity(intent);
        finish();
    }

    private void setActionBarColor() {
        if (currentItem != null) {
            if (currentItem.hasItemColor()) {
                this.getToolbar().setBackgroundColor(currentItem.getItemColor());
            }
            else {
                this.getToolbar().setBackgroundColor(primaryColor);
            }
        }
    }

    private <T> void setFragment(T fragment, NavigationItem<?> item, T oldFragment) {
        final int layoutResource = getContentResourceId();
        final String title = item.getTitle();

        navigationItemStack.push(item);
        setTitleByItem(item);

        if (fragment instanceof android.app.Fragment) {
            usesSupportFragments = false;

            if (oldFragment instanceof android.support.v4.app.Fragment) {
                throw new RuntimeException("You should use only one type of Fragment");
            }

            if (!handleAddFragments((android.app.Fragment) fragment, title, (android.app.Fragment) oldFragment)) {
                final android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(layoutResource, (android.app.Fragment) fragment, title);

                if (oldFragment != null) {
                    transaction.addToBackStack(BACK_STACK_NAME);
                }

                transaction.commit();
            }
        }
        else if (fragment instanceof android.support.v4.app.Fragment) {
            usesSupportFragments = true;

            if (oldFragment instanceof android.app.Fragment) {
                throw new RuntimeException("You should use only one type of Fragment");
            }

            if (!handleAddFragments((android.support.v4.app.Fragment) fragment, title, (android.support.v4.app.Fragment)
                    oldFragment)) {
                final android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();

                transaction.replace(layoutResource, (android.support.v4.app.Fragment) fragment, title);

                if (oldFragment != null) {
                    transaction.addToBackStack(BACK_STACK_NAME);
                }

                transaction.commit();
            }
        }
        else {
            throw new RuntimeException("Fragment must be android.app.Fragment or android.support.v4.app.Fragment");
        }
    }

    private void setTitleByItem(NavigationItem<?> found) {
        final String actionBarTitle = found.getActionBarTitle();
        final String title = found.getTitle();

        if (TextUtils.isEmpty(actionBarTitle)) {
            setTitle(title);
        }
        else {
            setTitle(actionBarTitle);
        }
    }

    private void setupAccountBoxToggle() {
        final int hideTranslateY = -accountListContainer.getHeight() / 4; // last 25% of animation
        AnimatorSet set = new AnimatorSet();

        accountBoxIndicator.setImageResource(isAccountBoxExpanded
                ? R.drawable.ic_drawer_accounts_collapse
                : R.drawable.ic_drawer_accounts_expand);

        if (isAccountBoxExpanded && accountListContainer.getTranslationY() == 0) {
            accountListContainer.setAlpha(0);
            accountListContainer.setTranslationY(hideTranslateY);
        }

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                onAnimationEnd(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isAccountBoxExpanded) {
                    ViewUtils.setInvisible(itemsContainer);
                    ViewUtils.setVisible(accountListContainer);
                }
                else {
                    ViewUtils.setVisible(itemsContainer);
                    ViewUtils.setInvisible(accountListContainer);
                }
            }
        });

        if (isAccountBoxExpanded) {
            AnimatorSet subSet = new AnimatorSet();

            ViewUtils.setVisible(accountListContainer);

            subSet.playTogether(
                    ObjectAnimator.ofFloat(accountListContainer, View.ALPHA, 1)
                                  .setDuration(ACCOUNT_BOX_EXPAND_ANIM_DURATION),
                    ObjectAnimator.ofFloat(accountListContainer, View.TRANSLATION_Y, 0)
                                  .setDuration(ACCOUNT_BOX_EXPAND_ANIM_DURATION));
            set.playSequentially(
                    ObjectAnimator.ofFloat(itemsContainer, View.ALPHA, 0)
                                  .setDuration(ACCOUNT_BOX_EXPAND_ANIM_DURATION), subSet);
            set.start();
        }
        else {
            AnimatorSet subSet = new AnimatorSet();

            ViewUtils.setVisible(itemsContainer);

            subSet.playTogether(
                    ObjectAnimator.ofFloat(accountListContainer, View.ALPHA, 0)
                                  .setDuration(ACCOUNT_BOX_EXPAND_ANIM_DURATION),
                    ObjectAnimator.ofFloat(accountListContainer, View.TRANSLATION_Y,
                            hideTranslateY)
                                  .setDuration(ACCOUNT_BOX_EXPAND_ANIM_DURATION));
            set.playSequentially(
                    subSet,
                    ObjectAnimator.ofFloat(itemsContainer, View.ALPHA, 1)
                                  .setDuration(ACCOUNT_BOX_EXPAND_ANIM_DURATION));
            set.start();
        }

        set.start();
    }

    private void setupAccounts() {
        final List<Account> accounts = addAccounts();

        if (accounts != null && accounts.size() > 0) {
            final Account chosenAccount = AccountUtils.getActiveAccount(this, getAccountType());
            final ViewsFinder localFinder = new ViewsFinder(accountChooser);

            accounts.remove(chosenAccount);

            accountBoxIndicator = localFinder.find(R.id.util__drawer_account_box_indicator);
            profileImage = localFinder.find(R.id.util__drawer_profile_image);
            name = localFinder.find(R.id.util__drawer_account_profile_name);
            email = localFinder.find(R.id.util__drawer_account_profile_email);

            accountChooser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isAccountBoxExpanded = !isAccountBoxExpanded;
                    setupAccountBoxToggle();
                }
            });

            populateAccountDetails(chosenAccount);
            setupAccountBoxToggle();
            populateAccountList(accounts);
        }
    }

    /**
     *
     */
    private void startLoginProcess() {
        Logger.debug(TAG, "Starting login process.");

        if (!AccountUtils.hasActiveAccount(this)) {
            Logger.debug(TAG, "No active account, attempting to pick a default.");
            final String defaultAccount = getDefaultAccount();

            if (defaultAccount == null) {
                Logger.error(TAG, "Failed to pick default account (no accounts). Failing.");
                complainNoAccounts();
                return;
            }

            Logger.debug(TAG, "Default to: " + defaultAccount);
            AccountUtils.setActiveAccount(this, defaultAccount);
        }

        if (!AccountUtils.hasActiveAccount(this)) {
            Logger.debug(TAG, "Can't proceed with login -- no account chosen.");
            return;
        }
        else {
            Logger.debug(TAG, "Chosen account: " + AccountUtils.getActiveAccountName(this));
        }

        final String accountName = AccountUtils.getActiveAccountName(this);

        Logger.debug(TAG, "Chosen account: " + AccountUtils.getActiveAccountName(this));

        if (loginAndAuthHelper != null && loginAndAuthHelper.getAccountName().equals(accountName)) {
            Logger.debug(TAG, "Helper already set up; simply starting it.");
            loginAndAuthHelper.start();
            return;
        }

        Logger.debug(TAG, "Starting login process with account " + accountName);

        if (loginAndAuthHelper != null) {
            Logger.debug(TAG, "Tearing down old Helper, was " + loginAndAuthHelper.getAccountName());
            if (loginAndAuthHelper.isStarted()) {
                Logger.debug(TAG, "Stopping old Helper");
                loginAndAuthHelper.stop();
            }
            loginAndAuthHelper = null;
        }

        Logger.debug(TAG, "Creating and starting new Helper with account: " + accountName);
        loginAndAuthHelper = createLoginAndAuthHelper(this, createLoginAndAuthCallbacks(), accountName);
        loginAndAuthHelper.start();
    }

    private void updateSelectedItem(NavigationItem<?> selected) {
        final int position = selected.getPosition();
        final Object target = selected.getTarget();

        drawerLayout.closeDrawer(drawer);

        if (target instanceof Intent || target instanceof NavigationItemListener) {
            return;
        }

        currentItem = selected;

        for (final NavigationItem item : itemList) {
            if (position != item.getPosition()) {
                item.deselect(itemColor);
            }
        }

        for (final NavigationItem item : bottomItemList) {
            if (position != item.getPosition()) {
                item.deselect(itemColor);
            }
        }

        setActionBarColor();
    }
}