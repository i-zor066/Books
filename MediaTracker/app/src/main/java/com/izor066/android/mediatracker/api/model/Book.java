package com.izor066.android.mediatracker.api.model;

/**
 * Created by igor on 7/11/15.
 */
public class Book {

    private final String title;
    private final String authpr;
    private long datePublished;
    //ToDo: Add cover image


    public Book(String title, String authpr, long datePublished) {
        this.title = title;
        this.authpr = authpr;
        this.datePublished = datePublished;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthpr() {
        return authpr;
    }

    public long getDatePublished() {
        return datePublished;
    }
}
