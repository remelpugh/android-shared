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

package com.dabay6.libraries.androidshared.view;

import android.app.Activity;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * ViewsFinder
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class ViewsFinder {
    private final Finder finder;

    /**
     * @param activity
     */
    public ViewsFinder(final Activity activity) {
        finder = new WindowFinder(activity.getWindow());
    }

    /**
     * @param view
     */
    public ViewsFinder(final View view) {
        finder = new ViewFinder(view);
    }

    /**
     * @param window
     */
    public ViewsFinder(final Window window) {
        finder = new WindowFinder(window);
    }

    /**
     * @param id
     * @param runnable
     *
     * @return
     */
    public EditText addTextWatcher(final int id, final Runnable runnable) {
        return addTextWatcher(id, new TextWatcher() {
            @Override
            public void afterTextChanged(final Editable s) {
            }

            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                runnable.run();
            }
        });
    }

    /**
     * @param id
     * @param watcher
     *
     * @return
     */
    public EditText addTextWatcher(final int id, final TextWatcher watcher) {
        final EditText view = find(id);

        view.addTextChangedListener(watcher);

        return view;
    }

    /**
     * @param watcher
     * @param views
     */
    public void addTextWatcher(final TextWatcher watcher, final EditText... views) {
        for (final EditText view : views) {
            if (view != null) {
                view.addTextChangedListener(watcher);
            }
        }
    }

    /**
     * @param watcher
     * @param ids
     */
    public void addTextWatcher(final TextWatcher watcher, final int... ids) {
        for (final int id : ids) {
            final EditText view = find(id);

            view.addTextChangedListener(watcher);
        }
    }

    /**
     * @param id
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T find(final int id) {
        try {
            return (T) finder.findViewById(id);
        }
        catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    /**
     * Register on click listener to child view with given id
     *
     * @return view registered with listener
     */
    public View onClick(final int id, final OnClickListener listener) {
        final View view = find(id);

        view.setOnClickListener(listener);

        return view;
    }

    /**
     * @param id
     * @param runnable
     *
     * @return
     */
    public View onClick(final int id, final Runnable runnable) {
        return onClick(id, new OnClickListener() {
            @Override
            public void onClick(final View v) {
                runnable.run();
            }
        });
    }

    /**
     * @param listener
     * @param ids
     *
     * @return
     */
    public void onClick(final OnClickListener listener, final int... ids) {
        for (final int id : ids) {
            find(id).setOnClickListener(listener);
        }
    }

    /**
     * @param listener
     * @param views
     *
     * @return
     */
    public void onClick(final OnClickListener listener, final View... views) {
        for (final View view : views) {
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    /**
     * @param runnable
     * @param ids
     */
    public void onClick(final Runnable runnable, final int... ids) {
        onClick(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                runnable.run();
            }
        }, ids);
    }

    /**
     * @param runnable
     * @param views
     */
    public void onClick(final Runnable runnable, final View... views) {
        onClick(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                runnable.run();
            }
        }, views);
    }

    /**
     * @param runnable
     * @param imeActionId
     * @param views
     */
    public void onEditorAction(final EditTextRunnable runnable, final int imeActionId, final EditText... views) {
        onEditorAction(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
                if (imeActionId == actionId) {
                    return runnable.run();
                }

                return false;
            }
        }, views);
    }

    /**
     * @param listener
     * @param views
     */
    public void onEditorAction(final OnEditorActionListener listener, final EditText... views) {
        for (final EditText view : views) {
            if (view != null) {
                view.setOnEditorActionListener(listener);
            }
        }
    }

    /**
     * @param watcher
     * @param views
     */
    public void removeTextWatcher(final TextWatcher watcher, final EditText... views) {
        for (final EditText view : views) {
            if (view != null) {
                view.removeTextChangedListener(watcher);
            }
        }
    }

    /**
     * @param watcher
     * @param ids
     */
    public void removeTextWatcher(final TextWatcher watcher, final int... ids) {
        for (final int id : ids) {
            final EditText view = find(id);

            view.removeTextChangedListener(watcher);
        }
    }

    /**
     * @param id
     * @param resId
     *
     * @return
     */
    public TextView setText(final int id, final int resId) {
        return setText(id, finder.getResources().getString(resId));
    }

    /**
     * @param id
     * @param text
     *
     * @return
     */
    public TextView setText(final int id, final String text) {
        final TextView view = find(id);

        if (view != null) {
            view.setText(text);

            return view;
        }

        return null;
    }

    /**
     * A {@link Runnable} that returns a boolean
     */
    public static interface EditTextRunnable {

        /**
         * Runnable that returns true when run, false when not run
         *
         * @return true if run, false otherwise
         */
        boolean run();
    }

    private static interface Finder {
        View findViewById(int id);

        Resources getResources();
    }

    private static class ViewFinder implements Finder {
        private final View view;

        public ViewFinder(final View view) {
            this.view = view;
        }

        @Override
        public View findViewById(final int id) {
            return view.findViewById(id);
        }

        @Override
        public Resources getResources() {
            return view.getResources();
        }
    }

    private static class WindowFinder implements Finder {
        private final Window window;

        public WindowFinder(final Window window) {
            this.window = window;
        }

        @Override
        public View findViewById(final int id) {
            return window.findViewById(id);
        }

        @Override
        public Resources getResources() {
            return window.getContext().getResources();
        }
    }
}