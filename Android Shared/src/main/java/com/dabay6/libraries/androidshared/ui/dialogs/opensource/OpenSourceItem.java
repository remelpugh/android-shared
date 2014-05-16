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

package com.dabay6.libraries.androidshared.ui.dialogs.opensource;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * OpenSourceItem
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class OpenSourceItem implements Parcelable {
    public static final Parcelable.Creator<OpenSourceItem> CREATOR = new Parcelable.Creator<OpenSourceItem>() {
        public OpenSourceItem createFromParcel(final Parcel in) {
            return new OpenSourceItem(in);
        }

        public OpenSourceItem[] newArray(final int size) {
            return new OpenSourceItem[size];
        }
    };
    private String license;
    private String name;

    /**
     * Create a new instance of {@link OpenSourceItem}.
     */
    public OpenSourceItem() {
    }

    /**
     * Create a new instance of {@link OpenSourceItem}.
     *
     * @param in {@link Parcel} used to initialize this instance.
     */
    public OpenSourceItem(final Parcel in) {
        this(in.readString(), in.readString());
    }

    /**
     * Create a new instance of {@link OpenSourceItem}.
     *
     * @param name    The name of the open source item.
     * @param license The license of the open source item.
     */
    public OpenSourceItem(final String name, final String license) {
        this.license = license;
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Get the license for this instance.
     *
     * @return {@link String} containing the license information.
     */
    public String getLicense() {
        return license;
    }

    /**
     * Sets the license for this instance.
     *
     * @param license {@link String} containing the license information.
     */
    public void setLicense(final String license) {
        this.license = license;
    }

    /**
     * Gets the name for this instance.
     *
     * @return {@link String} containing the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for this instance.
     *
     * @param name {@link String} containing the name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(name);
        dest.writeString(license);
    }
}