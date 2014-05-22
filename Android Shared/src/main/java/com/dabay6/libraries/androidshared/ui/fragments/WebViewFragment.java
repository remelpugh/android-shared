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

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.AssetUtils;
import com.dabay6.libraries.androidshared.util.ViewUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * WebViewFragment
 * <p>
 * This fragment shows a {@link WebView}.
 * </p>
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class WebViewFragment extends BaseFragment {
    private final static String ASSET_NAME_KEY =
            "com.dabay6.libraries.androidshared.ui.fragments.WebViewFragment" + ".AssetName";
    private final static String TAG = Logger.makeTag(WebViewFragment.class);
    private String assetName;
    private View contentContainer;
    private boolean isContentShown;
    private View loadingContainer;
    private WebView webView;

    /**
     * Default constructor.
     */
    public WebViewFragment() {
    }

    /**
     * @param assetName The name of the asset containing the html.
     *
     * @return A {@link Bundle} containing the default arguments for an instance of {@link WebViewFragment}.
     */
    public static Bundle getDefaultArgs(final String assetName) {
        final Bundle bundle = new Bundle();

        bundle.putString(WebViewFragment.ASSET_NAME_KEY, assetName);

        return bundle;
    }

    /**
     * Instantiate a new instance of {@link WebViewFragment}.
     *
     * @return A new instance of {@link WebViewFragment}.
     */
    public static WebViewFragment newInstance(final String assetName) {
        final WebViewFragment fragment = new WebViewFragment();

        if (!TextUtils.isEmpty(assetName)) {
            fragment.setArguments(getDefaultArgs(assetName));
        }

        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroyView() {
        isContentShown = false;

        super.onDestroyView();
    }

    /**
     *
     */
    protected final void afterViews() {
        if (contentContainer == null && loadingContainer == null) {
            View root = getView();

            if (root == null) {
                throw new IllegalStateException("Content view not yet created");
            }

            contentContainer = finder.find(R.id.webview_container_content);
            loadingContainer = finder.find(R.id.webview_container_loading);

            webView = finder.find(R.id.webView);

            configureWebView();
        }

        loadWebView();
    }

    /**
     *
     */
    protected void configureWebView() {
        if (webView != null) {
            final WebSettings settings;

            settings = webView.getSettings();
            settings.setSupportZoom(false);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    setContentShown(true);
                }
            });
        }
    }

    /**
     * @return The html contained within the assets folder.
     */
    protected String getHtml() {
        String html;

        try {
            html = AssetUtils.read(context, assetName);
        }
        catch (final UnsupportedEncodingException ex) {
            html = null;
            Logger.error(WebViewFragment.TAG, ex.getMessage(), ex);
        }
        catch (final IOException ex) {
            html = null;
            Logger.error(WebViewFragment.TAG, ex.getMessage(), ex);
        }

        return html;
    }

    /**
     * @return
     */
    @Override
    protected Integer getLayoutResourceId() {
        return R.layout.util__fragment_webview;
    }

    /**
     * @return The menu resource id.
     */
    @SuppressWarnings("unused")
    protected Integer getMenuResourceId() {
        return null;
    }

    /**
     * @param savedInstanceState The fragment saved instance state, if available.
     */
    @Override
    protected void initialize(final Bundle savedInstanceState) {
        final Bundle arguments = getArguments();

        if (arguments != null) {
            assetName = arguments.getString(WebViewFragment.ASSET_NAME_KEY);
        }
    }

    /**
     * @param shown
     */
    protected void setContentShown(boolean shown) {
        final Context context = getActivity();
        Animation animation;

        if (isContentShown == shown) {
            return;
        }

        isContentShown = shown;

        if (shown) {
            animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
            if (animation != null) {
                loadingContainer.startAnimation(animation);
            }

            animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            if (animation != null) {
                contentContainer.startAnimation(animation);
            }

            ViewUtils.setGone(loadingContainer, true);
            ViewUtils.setGone(contentContainer, false);
        }
        else {
            animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            if (animation != null) {
                loadingContainer.startAnimation(animation);
            }

            animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
            if (animation != null) {
                contentContainer.startAnimation(animation);
            }

            ViewUtils.setGone(loadingContainer, false);
            ViewUtils.setGone(contentContainer, true);
        }
    }

    private void loadWebView() {
        if (webView != null) {
            final String html = getHtml();

            if (TextUtils.isEmpty(html)) {
                return;
            }

            webView.loadData(html, "text/html", "utf-8");
        }
    }
}