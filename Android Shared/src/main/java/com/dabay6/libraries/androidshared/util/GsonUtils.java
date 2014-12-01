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

package com.dabay6.libraries.androidshared.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * GsonUtils
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public final class GsonUtils {
    private static final Gson GSON = createGson(true);
    private static final Gson GSON_NO_NULLS = createGson(false);

    /**
     * Hidden constructor.
     */
    private GsonUtils() {
    }

    /**
     * Converts {@link String} to given type.
     *
     * @param json the json to convert.
     * @param type type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Class<T> type) {
        return GSON.fromJson(json, type);
    }

    /**
     * Converts {@link String} to given type.
     *
     * @param json the json to convert.
     * @param type type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final String json, final Type type) {
        return GSON.fromJson(json, type);
    }

    /**
     * Converts {@link Reader} to given type.
     *
     * @param reader the reader to convert.
     * @param type   type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final Reader reader, final Class<T> type) {
        return GSON.fromJson(reader, type);
    }

    /**
     * Converts {@link Reader} to given type.
     *
     * @param reader the reader to convert.
     * @param type   type type json will be converted to.
     * @return instance of type
     */
    public static <T> T fromJson(final Reader reader, final Type type) {
        return GSON.fromJson(reader, type);
    }

    /**
     * Gets pre-configured {@link Gson} instance.
     *
     * @return {@link Gson} instance.
     */
    public static Gson getGson() {
        return GsonUtils.getGson(true);
    }

    /**
     * Gets pre-configured {@link Gson} instance.
     *
     * @param serializeNulls determines if nulls will be serialized.
     * @return {@link Gson} instance.
     */
    public static Gson getGson(final boolean serializeNulls) {
        return serializeNulls ? GSON_NO_NULLS : GSON;
    }

    /**
     * Serializes an object into json.
     *
     * @param object the object to serialize.
     * @return object serialized into json.
     */
    public static String toJson(final Object object) {
        return GsonUtils.toJson(object, true);
    }

    /**
     * Serializes an object into json.
     *
     * @param object       the object to serialize.
     * @param includeNulls determines if nulls will be included.
     * @return object serialized into json.
     */
    public static String toJson(final Object object, final boolean includeNulls) {
        return includeNulls ? GSON.toJson(object) : GSON_NO_NULLS.toJson(object);
    }

    /**
     * Create a pre-configured {@link Gson} instance.
     *
     * @param serializeNulls determines if nulls will be serialized.
     * @return {@link Gson} instance.
     */
    private static Gson createGson(final boolean serializeNulls) {
        final GsonBuilder builder = new GsonBuilder();

        if (serializeNulls) {
            builder.serializeNulls();
        }

        return builder.create();
    }
}