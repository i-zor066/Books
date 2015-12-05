package com.izor066.android.mediatracker;

import android.app.Application;

import com.izor066.android.mediatracker.api.DataSource;

/**
 * Created by igor on 7/11/15.
 */
public class MediaTrackerApplication extends Application {

    private static MediaTrackerApplication sharedInstance;
    private DataSource dataSource;

    public static MediaTrackerApplication getSharedInstance() {
        return sharedInstance;
    }

    public static DataSource getSharedDataSource() {
        return MediaTrackerApplication.getSharedInstance().getDataSource();
    }



    @Override
    public void onCreate() {
        super.onCreate();
        sharedInstance = this;
        dataSource = new DataSource(this);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
