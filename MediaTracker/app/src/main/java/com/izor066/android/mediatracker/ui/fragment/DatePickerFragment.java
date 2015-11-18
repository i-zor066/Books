package com.izor066.android.mediatracker.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.izor066.android.mediatracker.ui.UIUtils;

import java.util.Calendar;

/**
 * Created by igor on 14/11/15.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface OnDatePubSetListener {
        public void onDatePubSet (int datePub);
    }

    OnDatePubSetListener onDatePubSetListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        int datePub = UIUtils.componentTimeToTimestamp(year, month, day);
        onDatePubSetListener.onDatePubSet(datePub);


    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        onDatePubSetListener = (OnDatePubSetListener) a;
    }
}
