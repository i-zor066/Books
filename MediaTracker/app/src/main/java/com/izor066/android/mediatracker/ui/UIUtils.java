package com.izor066.android.mediatracker.ui;

import java.util.Calendar;

/**
 * Created by igor on 17/11/15.
 */
public class UIUtils {

    public static int componentTimeToTimestamp(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        return (int) (c.getTimeInMillis() / 1000L);
    }
}
