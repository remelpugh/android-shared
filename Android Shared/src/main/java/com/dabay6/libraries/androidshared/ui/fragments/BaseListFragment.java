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

package com.dabay6.libraries.androidshared.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

/**
 * BaseListFragment
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class BaseListFragment extends ListFragment implements FragmentBase {
    private final static String TAG = Logger.makeTag(BaseListFragment.class);
    protected Context applicationContext;
    protected ViewsFinder finder;
    private boolean isDualPane = false;
    private FragmentLifeCycleListener onFragmentLifeCycleListener;

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
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupListView(savedInstanceState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        if (activity instanceof FragmentLifeCycleListener) {
            onFragmentLifeCycleListener = (FragmentLifeCycleListener) activity;
            onFragmentLifeCycleListener.onFragmentAttached(this);
        }

        applicationContext = activity.getApplicationContext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(getMenuResourceId() != null && getMenuResourceId() > 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        if (hasOptionsMenu() && getMenuResourceId() != null) {
            inflater.inflate(getMenuResourceId(), menu);

            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetach() {
        applicationContext = null;
        super.onDetach();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (onFragmentLifeCycleListener != null) {
            onFragmentLifeCycleListener.onFragmentViewCreated(this);
        }
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
        if (!isAdded()) {
            return;
        }

        if (getActivity() instanceof ActionBarActivity) {
            ((ActionBarActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTitle(final int titleId) {
        setTitle(getString(titleId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTitle(final String title) {
        if (!isAdded()) {
            return;
        }

        getActivity().setTitle(title);
    }

    /**
     * Gets the menu resource for this instance.
     *
     * @return Resource identifier for the menu resource.
     */
    protected abstract Integer getMenuResourceId();

    /**
     * Setup the {@link android.widget.ListView}.
     *
     * @param savedInstanceState If the fragment is being re-initialized after previously being shut down then this
     *                           Bundle contains the data it most recently supplied in {@link
     *                           BaseFragment#onSaveInstanceState(android.os.Bundle)}. Note:
     *                           Otherwise it is null.
     */
    protected abstract void setupListView(final Bundle savedInstanceState);
}