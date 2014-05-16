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

package com.dabay6.libraries.androidshared.ui.dialogs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import com.dabay6.libraries.androidshared.R;
import com.dabay6.libraries.androidshared.R.id;
import com.dabay6.libraries.androidshared.logging.Logger;
import com.dabay6.libraries.androidshared.util.AndroidUtils;
import com.dabay6.libraries.androidshared.view.ViewsFinder;

import java.util.Calendar;

/**
 * DateTimePickerDialogFragment
 *
 * @author Remel Pugh
 * @version 1.0
 */
@SuppressWarnings("unused")
public class DateTimePickerDialogFragment extends BaseDialogFragment {
    private final static String TAG = Logger.makeTag(DateTimePickerDialogFragment.class);
    private DatePicker datePicker;
    private OnDateTimePickerListener onDateTimePickerListener;
    private TimePicker timePicker;

    /**
     * Default constructor.
     */
    public DateTimePickerDialogFragment() {
    }

    /**
     * Creates a new instance of the {@link DateTimePickerDialogFragment}.
     */
    public static DateTimePickerDialogFragment newInstance() {
        return newInstance(null);
    }

    /**
     * Creates a new instance of the {@link DateTimePickerDialogFragment}.
     *
     * @return A {@link DateTimePickerDialogFragment}.
     */
    public static DateTimePickerDialogFragment newInstance(final Long milliseconds) {
        return newInstance(milliseconds, null, null);
    }

    /**
     * @param milliseconds
     * @param minDateTime
     * @param maxDateTime
     *
     * @return
     */
    public static DateTimePickerDialogFragment newInstance(final Long milliseconds, final Long minDateTime,
                                                           final Long maxDateTime) {
        final Bundle arguments = new Bundle();
        final DateTimePickerDialogFragment fragment = new DateTimePickerDialogFragment();

        if (milliseconds != null) {
            arguments.putLong("milliseconds", milliseconds);
        }
        if (minDateTime != null) {
            arguments.putLong("minDateTime", minDateTime);
        }
        if (maxDateTime != null) {
            arguments.putLong("maxDateTime", maxDateTime);
        }

        fragment.setArguments(arguments);

        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        try {
            onDateTimePickerListener = (OnDateTimePickerListener) activity;
        }
        catch (final ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement OnDateTimePickerListener");
        }
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(VERSION_CODES.HONEYCOMB)
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder;
        final Bundle arguments = getArguments();
        final Context context = getActivity();
        final Resources res = getActivity().getResources();
        final String title = res.getString(R.string.date_time_picker_title);
        final View view = getActivity().getLayoutInflater().inflate(R.layout.util__date_time_picker, null);
        final ViewsFinder finder;
        Long milliseconds = null;

        finder = new ViewsFinder(view);

        if (arguments.containsKey("milliseconds")) {
            milliseconds = arguments.getLong("milliseconds");
        }

        datePicker = finder.find(id.date_picker);
        timePicker = finder.find(id.time_picker);

        if (AndroidUtils.isAtLeastHoneycomb()) {
            if (arguments.containsKey("minDateTime")) {
                datePicker.setMinDate(arguments.getLong("minDateTime"));
            }
            if (arguments.containsKey("maxDateTime")) {
                datePicker.setMaxDate(arguments.getLong("maxDateTime"));
            }
        }

        if (milliseconds != null) {
            final Calendar calendar = Calendar.getInstance();

            calendar.setTimeInMillis(milliseconds);

            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH), null);

            timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }

        builder = new AlertDialog.Builder(context).setTitle(title).setView(view);

        builder.setPositiveButton(R.string.date_time_picker_set, new OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                if (onDateTimePickerListener != null) {
                    final Calendar calendar = Calendar.getInstance();

                    calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                                 timePicker.getCurrentHour(), timePicker.getCurrentMinute());

                    onDateTimePickerListener.onDateTimeSet(calendar.getTimeInMillis());
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                if (onDateTimePickerListener != null) {
                    onDateTimePickerListener.onDateTimeCancel();
                }
                dialog.dismiss();
            }
        });
        builder.setNeutralButton(R.string.date_time_picker_now, new OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                if (onDateTimePickerListener != null) {
                    onDateTimePickerListener.onDateTimeNow(System.currentTimeMillis());
                }
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void afterViews(final Bundle savedInstanceState) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    protected Integer getMenuResourceId() {
        return null;
    }

    /**
     * OnDateTimePickerListener
     *
     * @author Remel Pugh
     * @version 1.0
     */
    public interface OnDateTimePickerListener {
        /**
         *
         */
        void onDateTimeCancel();

        /**
         * @param milliseconds The current system date/time.
         */
        void onDateTimeNow(final long milliseconds);

        /**
         * @param milliseconds The selected date/time.
         */
        void onDateTimeSet(final long milliseconds);
    }
}