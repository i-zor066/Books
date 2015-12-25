package com.izor066.android.mediatracker.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.util.Calendar;

/**
 * Created by igor on 17/11/15.
 */
public class UIUtils {

    public static long componentTimeToTimestamp(int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        return c.getTimeInMillis();
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
