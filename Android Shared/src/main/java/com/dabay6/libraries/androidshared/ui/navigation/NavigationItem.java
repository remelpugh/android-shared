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

package com.dabay6.libraries.androidshared.ui.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.util.AndroidUtils;
import com.dabay6.libraries.androidshared.util.StringUtils;
import com.dabay6.libraries.androidshared.util.ViewUtils;

/**
 * NavigationDrawerItem
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class NavigationItem<T> implements View.OnTouchListener {
    private final int colorPressed;
    private final int colorSelected;
    private final int colorUnpressed;
    private final Context context;
    private String actionBarTitle = null;
    private int count;
    private boolean hasItemColor;
    private ImageView icon;
    private int iconColor;
    private boolean isSelected;
    private NavigationItemListener listener;
    private TextView notifications;
    private int position;
    private T target;
    private TextView text;
    private String title;
    private View view;

    /**
     * @param context
     * @param hasIcon
     */
    public NavigationItem(Context context, boolean hasIcon) {
        final int layoutResId;
        final Resources resources = context.getResources();

        this.context = context;

        layoutResId = !hasIcon ? R.layout.util__navigation_drawer_item : R.layout.util__navigation_drawer_item_icon;

        view = LayoutInflater.from(context).inflate(layoutResId, null);

        if (view == null) {
            throw new IllegalStateException("Unable to find item layout resource");
        }

        view.setOnTouchListener(this);

        notifications = (TextView) view.findViewById(R.id.util__drawer_item_count);
        text = (TextView) view.findViewById(R.id.util__drawer_item_text);

        if (hasIcon) {
            icon = (ImageView) view.findViewById(R.id.util__drawer_item_icon);
        }

        colorPressed = resources.getColor(R.color.util__navigation_drawer_item_pressed);
        colorUnpressed = resources.getColor(R.color.util__navigation_drawer_item_unpressed);
        colorSelected = resources.getColor(R.color.util__navigation_drawer_item_selected);
        iconColor = resources.getColor(R.color.util__black);

        count = 0;
        hasItemColor = false;
        isSelected = false;
    }

    /**
     *
     */
    public void deselect(final int itemColor) {
        isSelected = false;

        view.setBackgroundColor(colorUnpressed);

        if (hasItemColor) {
            text.setTextColor(itemColor);

            if (icon != null) {
                icon.setColorFilter(colorUnpressed);
                setAlpha(icon, 0.54f);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NavigationItem<?> that = (NavigationItem<?>) o;

        return count == that.count && position == that.position && title.equals(that.title);

    }

    /**
     * Gets the title to be used by application's actionbar.
     *
     * @return The actionbar title.
     */
    public String getActionBarTitle() {
        return actionBarTitle;
    }

    /**
     * Sets the application's actionbar title.
     *
     * @param title The title to be set.
     */
    public NavigationItem<T> setActionBarTitle(final String title) {
        this.actionBarTitle = title;

        return this;
    }

    /**
     * @return
     */
    public int getItemColor() {
        return iconColor;
    }

    /**
     * @param color
     *
     * @return
     */
    public NavigationItem<T> setItemColor(int color) {
        iconColor = color;
        hasItemColor = true;

        return this;
    }

    /**
     * @return
     */
    public int getNotifications() {
        return count;
    }

    /**
     * Set the number of notification for this item
     *
     * @param notifications the number of notification active for this item
     *
     * @return this item
     */
    public NavigationItem<T> setNotifications(int notifications) {
        String text = String.valueOf(notifications);

        if (notifications < 1) {
            text = StringUtils.empty();
        }

        if (notifications > 99) {
            text = context.getString(R.string.util__navigation_drawer_notification_limit);
        }

        ViewUtils.setVisible(this.notifications);

        this.notifications.setText(text);

        count = notifications;

        return this;
    }

    /**
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return
     */
    public T getTarget() {
        return target;
    }

    /**
     * @param target
     */
    public void setTarget(T target) {
        if (!(target instanceof android.app.Fragment) &&
            !(target instanceof android.support.v4.app.Fragment) &&
            !(target instanceof Activity) &&
            !(target instanceof Intent) &&
            !(target instanceof NavigationItemListener)) {
            throw new IllegalStateException("Target must be either a android.app.Fragment, " +
                                            "android.support.v4.app.Fragment, Activity, Intent, or NavigationItemListener");
        }

        this.target = target;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
        this.text.setText(title);
    }

    /**
     * @return
     */
    public View getView() {
        return view;
    }

    /**
     * @return
     */
    public boolean hasItemColor() {
        return hasItemColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = count;
        result = 31 * result + position;
        result = 31 * result + title.hashCode();
        return result;
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to get a chance to respond before the
     * target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about the event.
     *
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            view.setBackgroundColor(colorPressed);

            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            view.setBackgroundColor(isSelected ? colorSelected : colorUnpressed);

            return true;
        }


        if (event.getAction() == MotionEvent.ACTION_UP) {
            view.setBackgroundColor(colorSelected);
            actionUp();

            return true;
        }

        return false;
    }

    /**
     *
     */
    public void select() {
        isSelected = true;

        view.setBackgroundColor(colorSelected);

        if (hasItemColor) {
            text.setTextColor(iconColor);

            if (icon != null) {
                icon.setColorFilter(iconColor);
                setAlpha(icon, 1f);
            }
        }
    }

    /**
     * @param resourceId
     */
    public void setIcon(final int resourceId) {
        icon.setImageResource(resourceId);
        icon.setColorFilter(iconColor);
    }

    /**
     * @param icon
     */
    public void setIcon(Drawable icon) {
        this.icon.setImageDrawable(icon);
        this.icon.setColorFilter(iconColor);
    }

    /**
     * @param icon
     */
    public void setIcon(Bitmap icon) {
        this.icon.setImageBitmap(icon);
        this.icon.setColorFilter(iconColor);
    }

    /**
     * @param listener
     */
    public void setOnClickListener(NavigationItemListener listener) {
        this.listener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NavigationItem{" +
               "colorPressed=" + colorPressed +
               ", colorSelected=" + colorSelected +
               ", colorUnpressed=" + colorUnpressed +
               ", actionBarTitle='" + actionBarTitle + '\'' +
               ", count=" + count +
               ", hasItemColor=" + hasItemColor +
               ", iconColor=" + iconColor +
               ", isSelected=" + isSelected +
               ", position=" + position +
               ", title='" + title + '\'' +
               '}';
    }

    //region PRIVATE GETTERS, SETTERS, AND METHODS

    private void actionUp() {
        isSelected = true;

        if (hasItemColor) {
            text.setTextColor(iconColor);

            if (icon != null) {
                icon.setColorFilter(iconColor);
                setAlpha(icon, 1f);
            }
        }

        if (listener != null) {
            listener.onClick(this);
        }

        if (target instanceof NavigationItemListener) {
            ((NavigationItemListener) target).onClick(this);
        }
    }

    private void setAlpha(View view, float alpha) {
        if (AndroidUtils.isAtLeastHoneycomb()) {
            view.setAlpha(alpha);
        }
        else {
            final AlphaAnimation animation = new AlphaAnimation(alpha, alpha);

            animation.setDuration(0);
            animation.setFillAfter(true);

            view.startAnimation(animation);
        }
    }

    //endregion PRIVATE GETTERS, SETTERS, AND METHODS
}