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

package com.dabay6.libraries.androidshared.enums;

import com.dabay6.libraries.androidshared.util.HashMapUtils;

import java.util.EnumSet;
import java.util.Map;

/**
 * NetworkTypes
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public enum NetworkTypes {
    WIFI(0);
    private static final Map<Integer, NetworkTypes> lookup = HashMapUtils.newHashMap();
    private final int index;

    private NetworkTypes(final int index) {
        this.index = index;
    }

    /**
     * @param index The ordinal value to lookup the enumeration value.
     */
    public static NetworkTypes get(final int index) {
        return lookup.get(index);
    }

    /**
     * The ordinal value of the current enumeration instance.
     *
     * @return The ordinal of the enumeration.
     */
    public int index() {
        return index;
    }

    static {
        for (final NetworkTypes type : EnumSet.allOf(NetworkTypes.class)) {
            lookup.put(type.index(), type);
        }
    }
}