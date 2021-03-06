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

package com.dabay6.libraries.androidshared.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dabay6.libraries.androidshared.annotations.ForApplication;
import com.dabay6.libraries.androidshared.interfaces.FragmentBase;
import com.dabay6.libraries.androidshared.interfaces.FragmentLifeCycleListener;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.ui.BaseActivity;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

import javax.inject.Inject;

/**
 * BaseFragment
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public abstract class BaseFragment extends Fragment implements FragmentBase {
    private final static String TAG = Logger.makeTag(BaseFragment.class);
    @Inject
    @ForApplication
    protected Context applicationContext;
    protected ViewsFinder finder;
    @Inject
    ActionBarController actionBarController;
    @Inject
    ActivityTitleController titleController;
    private boolean isDualPane = false;
    private boolean isFirstAttach = true;
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
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        if (activity instanceof FragmentLifeCycleListener) {
            onFragmentLifeCycleListener = (FragmentLifeCycleListener) activity;
            onFragmentLifeCycleListener.onFragmentAttached(this);
        }

        if (getActivity() instanceof BaseActivity && isFirstAttach) {
            ((BaseActivity) getActivity()).inject(this);

            isFirstAttach = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize(savedInstanceState);
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        if (getLayoutResourceId() != null) {
            return inflater.inflate(getLayoutResourceId(), container, false);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
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

        if (view != null) {
            finder = new ViewsFinder(view);
            afterViews();
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

        titleController.setSubtitle(subtitle);
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

        titleController.setSubtitle(title);
    }

    /**
     * Called after the view has been created.
     */
    protected abstract void afterViews();

    /**
     * Gets the layout resource for this instance.
     *
     * @return Resource identifier for the layout resource.
     */
    protected abstract Integer getLayoutResourceId();

    /**
     * Gets the menu resource for this instance.
     *
     * @return Resource identifier for the menu resource.
     */
    @SuppressWarnings("SameReturnValue")
    protected abstract Integer getMenuResourceId();

    /**
     * Initializes this instance.
     *
     * @param savedInstanceState If the fragment is being re-initialized after previously being shut down then this
     *                           Bundle contains the data it most recently supplied in {@link
     *                           BaseFragment#onSaveInstanceState(android.os.Bundle)}. Note: Otherwise it is null.
     */
    protected void initialize(final Bundle savedInstanceState) {
    }
}