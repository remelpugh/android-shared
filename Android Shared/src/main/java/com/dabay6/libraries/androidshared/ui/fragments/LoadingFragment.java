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
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.util.ViewUtils;

/**
 * LoadingFragment
 * <p>
 * This fragment shows a loading progress spinner. Upon reaching a set timeout (in case
 * of a poor network connection), the user can try again.
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class LoadingFragment extends BaseFragment {
    private static final String LOADING_DELAY_KEY = "LOADING_DELAY";
    private static final int LOADING_DELAY_SECONDS = 5;
    private static final String LOADING_MESSAGE = "LOADING_MESSAGE";
    private static Integer TRY_AGAIN_DELAY = null;
    private final Handler handler = new Handler();
    private OnRetryListener callback;
    private View content;
    private View contentContainer;
    private View empty;
    private boolean isContentEmpty;
    private boolean isContentShown;
    private View loadingContainer;
    private String loadingMessage;

    /**
     * Default constructor.
     */
    public LoadingFragment() {
    }

    /**
     * @return
     */
    public View getContentView() {
        return content;
    }

    /**
     * @param view
     */
    public void setContentView(final View view) {
        afterViews();

        if (view == null) {
            throw new IllegalArgumentException("Content view can't be null");
        }

        if (contentContainer instanceof ViewGroup) {
            final ViewGroup container = (ViewGroup) contentContainer;

            if (content == null) {
                container.addView(view);
            }
            else {
                final int index = container.indexOfChild(content);

                // replace content view
                container.removeView(content);
                container.addView(view, index);
            }

            content = view;
        }
        else {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
    }

    /**
     * @return True if content is empty, otherwise false.
     */
    public boolean isContentEmpty() {
        return isContentEmpty;
    }

    /**
     * @param isEmpty
     */
    public void setContentEmpty(final boolean isEmpty) {
        afterViews();

        if (content == null) {
            throw new IllegalStateException("Content view must be initialized before");
        }
        if (isEmpty) {
            ViewUtils.setGone(empty, false);
            ViewUtils.setGone(content, true);
        }
        else {
            ViewUtils.setGone(empty, true);
            ViewUtils.setGone(content, false);
        }

        isContentEmpty = isEmpty;
    }

    /**
     * Creates a new instance of the {@link LoadingFragment}.
     *
     * @param message The message to be displayed.
     *
     * @return an instance of {@link LoadingFragment}
     */
    public static LoadingFragment newInstance(final String message) {
        return LoadingFragment.newInstance(null, message);
    }

    /**
     * Creates a new instance of the {@link LoadingFragment}.
     *
     * @param loadingDelay amount of time to wait before showing the try again button.
     * @param message      The message to be displayed.
     *
     * @return an instance of {@link LoadingFragment}
     */
    public static LoadingFragment newInstance(final Integer loadingDelay, final String message) {
        final Bundle bundle = new Bundle();
        final LoadingFragment fragment = new LoadingFragment();

        if (loadingDelay != null) {
            bundle.putInt(LOADING_DELAY_KEY, 15);
        }
        if (!TextUtils.isEmpty(message)) {
            bundle.putString(LOADING_MESSAGE, message);
        }

        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     * Creates a new instance of the {@link LoadingFragment}.
     *
     * @return an instance of {@link LoadingFragment}
     */
    public static LoadingFragment newInstance() {
        return LoadingFragment.newInstance(null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        try {
            callback = (OnRetryListener) activity;
        }
        catch (final ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement OnRetryListener");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroyView() {
        isContentEmpty = false;
        isContentShown = false;

        super.onDestroyView();
    }

    /**
     * @param shown
     */
    public void setContentShown(final boolean shown) {
        setContentShown(shown, true);
    }

    /**
     * Like {@link #setContentShown(boolean)}, but no animation is used when
     * transitioning from the previous state.
     */
    public void setContentShownNoAnimation(final boolean shown) {
        setContentShown(shown, false);
    }

    /**
     * @param layoutResId ID for an XML layout resource to load.
     */
    public void setContentView(final int layoutResId) {
        final LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View contentView = layoutInflater.inflate(layoutResId, null);

        setContentView(contentView);
    }

    /**
     * @param fragment
     * @param tag
     */
    public void setContentView(final Fragment fragment, final String tag) {
        afterViews();

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.container_content, fragment, tag).commit();
    }

    /**
     * @param resId Resource id for the string.
     */
    public void setEmptyText(final int resId) {
        setEmptyText(getString(resId));
    }

    /**
     * @param text
     */
    public void setEmptyText(final CharSequence text) {
        afterViews();

        if (empty != null && empty instanceof TextView) {
            ((TextView) empty).setText(text);
        }
        else {
            throw new IllegalStateException("Can't be used with a custom content view");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void afterViews() {
        final View button;
        final View progress;

        if (contentContainer != null && loadingContainer != null) {
            return;
        }

        final View root = getView();

        if (root == null) {
            throw new IllegalStateException("Content view not yet created");
        }

        contentContainer = finder.find(R.id.container_content);
        if (contentContainer == null) {
            throw new RuntimeException(
                    "Your content must have a ViewGroup whose id attribute is 'R.id.container_content'");
        }

        loadingContainer = finder.find(R.id.container_loading);
        if (loadingContainer == null) {
            throw new RuntimeException(
                    "Your content must have a ViewGroup whose id attribute is 'R.id.container_loading'");
        }

        empty = finder.find(android.R.id.empty);
        if (empty != null) {
            empty.setVisibility(View.GONE);
        }

        button = finder.find(R.id.retry_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                getFragmentManager().popBackStack();
                if (callback != null) {
                    callback.onRetry();
                }
            }
        });

        progress = finder.find(R.id.taking_a_while_panel);
        if (LoadingFragment.TRY_AGAIN_DELAY != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isAdded()) {
                        return;
                    }

                    ViewUtils.setInvisible(progress, true);
                }
            }, LoadingFragment.TRY_AGAIN_DELAY);
        }
        else {
            final TextView message = finder.find(R.id.loading_message);

            ViewUtils.setInvisible(progress, false);
            ViewUtils.setGone(button, true);

            message.setText(loadingMessage);
        }

        isContentShown = true;

        if (content == null) {
            setContentShown(false, false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getLayoutResourceId() {
        return R.layout.util__fragment_loading;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getMenuResourceId() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initialize(final Bundle savedInstanceState) {
        final Bundle arguments = getArguments();
        Integer delay = null;

        if (arguments != null) {
            if (arguments.containsKey(LOADING_DELAY_KEY)) {
                delay = arguments.getInt(LOADING_DELAY_KEY, LOADING_DELAY_SECONDS);
            }
            if (arguments.containsKey(LOADING_MESSAGE)) {
                loadingMessage = arguments.getString(LOADING_MESSAGE);
            }
        }
        else {
            loadingMessage = getString(R.string.loading_please_wait);
        }

        if (delay != null) {
            LoadingFragment.TRY_AGAIN_DELAY = delay * 1000;
        }
    }

    private void setContentShown(final boolean shown, final boolean animate) {
        final Context context = getActivity();
        Animation animation;

        afterViews();

        if (isContentShown == shown) {
            return;
        }

        isContentShown = shown;

        if (shown) {
            if (animate) {
                animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
                if (animation != null) {
                    loadingContainer.startAnimation(animation);
                }

                animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
                if (animation != null) {
                    contentContainer.startAnimation(animation);
                }
            }
            else {
                loadingContainer.clearAnimation();
                contentContainer.clearAnimation();
            }

            ViewUtils.setGone(loadingContainer, true);
            ViewUtils.setGone(contentContainer, false);
        }
        else {
            if (animate) {
                animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
                if (animation != null) {
                    loadingContainer.startAnimation(animation);
                }

                animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
                if (animation != null) {
                    contentContainer.startAnimation(animation);
                }
            }
            else {
                loadingContainer.clearAnimation();
                contentContainer.clearAnimation();
            }

            ViewUtils.setGone(loadingContainer, false);
            ViewUtils.setGone(contentContainer, true);
        }
    }

    /**
     * @author Remel Pugh
     * @version 1.0
     */
    public interface OnRetryListener {
        void onRetry();
    }
}