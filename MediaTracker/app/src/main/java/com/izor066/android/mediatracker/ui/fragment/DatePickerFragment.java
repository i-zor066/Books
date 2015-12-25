package com.izor066.android.mediatracker.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.izor066.android.mediatracker.util.UIUtils;

import java.util.Calendar;

/**
 * Created by igor on 14/11/15.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface OnDatePubSetListener {
        public void onDatePubSet(long datePub);
    }

    OnDatePubSetListener onDatePubSetListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();

        if (getArguments() != null) {
            long datePub = getArguments().getLong("datePublished");
            c = Calendar.getInstance();
            c.setTimeInMillis(datePub);
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        return new DatePickerDialog(getActivity(), this, year, month, day);


    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        long datePub = UIUtils.componentTimeToTimestamp(year, month, day);
        onDatePubSetListener.onDatePubSet(datePub);


    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        onDatePubSetListener = (OnDatePubSetListener) a;
    }
}
