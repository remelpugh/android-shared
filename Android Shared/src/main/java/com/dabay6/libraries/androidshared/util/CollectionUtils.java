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

package com.dabay6.libraries.androidshared.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * ArrayUtils
 *
 * @author Remel Pugh
 */
@SuppressWarnings("unused")
public final class CollectionUtils {
    private CollectionUtils() {
    }

    /**
     * Creates a {@link java.util.ArrayList} from the passed in array.
     *
     * @param list the array that will be copied from.
     *
     * @return a newly-created {@link java.util.ArrayList} containing the list list.
     */
    public static <T> ArrayList<T> asList(final T[] list) {
        final ArrayList<T> newList = CollectionUtils.newArrayList();

        Collections.addAll(newList, list);

        return newList;
    }

    /**
     * Determines if a given {@link java.util.List} is empty.
     *
     * @param list The list to check.
     * @param <T>  The generic type of the list items.
     *
     * @return True if the list is empty, otherwise false.
     */
    public static <T> boolean isEmpty(final List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }

        boolean isEmpty = true;

        for (T item : list) {
            if (item != null) {
                isEmpty = false;
                break;
            }
        }

        return isEmpty;
    }

    /**
     * Detect if an array is empty.
     *
     * @param array The array to be checked
     *
     * @return True if the array is null or empty, otherwise false.
     */
    public static <T> boolean isEmpty(final T[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        boolean isEmpty = true;

        for (T item : array) {
            if (item != null) {
                isEmpty = false;
                break;
            }
        }

        return isEmpty;
    }

    /**
     * Creates an empty {@link ArrayList} instance.
     *
     * @return a newly-created, initially-empty {@link ArrayList}
     */
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * Creates a resizable {@link ArrayList} instance containing the given elements.
     *
     * @param elements the elements that the list should contain, in order
     *
     * @return a newly-created {@link ArrayList} containing those elements
     */
    @SafeVarargs
    public static <T> ArrayList<T> newArrayList(final T... elements) {
        final int capacity = (elements.length * 110) / 100 + 5;
        final ArrayList<T> list = newArrayList(capacity);

        Collections.addAll(list, elements);

        return list;
    }

    /**
     * Creates an empty {@link ArrayList} instance.
     *
     * @param capacity the initial capacity of this {@link List} instance.
     *
     * @return a newly-created, initially-empty {@link List}
     */
    public static <T> ArrayList<T> newArrayList(final int capacity) {
        return new ArrayList<>(capacity);
    }

    /**
     * Creates a {@link java.util.HashMap} instance.
     *
     * @return a newly-created, initially-empty {@code HashMap}
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    /**
     * Creates a {@link java.util.LinkedHashMap} instance.
     *
     * @return a newly-created, initially-empty {@code HashMap}
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    /**
     * Creates a resizable {@link List} instance containing the given elements.
     *
     * @param elements the elements that the list should contain, in order
     *
     * @return a newly-created {@link List} containing those elements
     */
    @SafeVarargs
    public static <T> List<T> newList(final T... elements) {
        final int capacity = (elements.length * 110) / 100 + 5;
        final List<T> list = newList(capacity);

        Collections.addAll(list, elements);

        return list;
    }

    /**
     * Creates an empty {@link List} instance.
     *
     * @param capacity the initial capacity of this {@link List} instance.
     *
     * @return a newly-created, initially-empty {@link List}
     */
    public static <T> List<T> newList(final int capacity) {
        return new ArrayList<>(capacity);
    }

    /**
     * Creates an empty {@link List} instance.
     *
     * @return a newly-created, initially-empty {@link List}
     */
    public static <T> List<T> newList() {
        return new ArrayList<>();
    }

    /**
     * @param list          the {@link ArrayList} that will be copied from.
     * @param componentType the component componentType of the new array
     *
     * @return a newly-created array containing the {@link ArrayList} elements.
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(final ArrayList<T> list, final Class<T> componentType) {
        return list.toArray((T[]) Array.newInstance(componentType, list.size()));
    }

    /**
     * @param list          the {@link List} that will be copied from.
     * @param componentType the component componentType of the new array
     *
     * @return a newly-created array containing the {@link ArrayList} elements.
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(final List<T> list, final Class<T> componentType) {
        return list.toArray((T[]) Array.newInstance(componentType, list.size()));
    }
}