package com.izor066.android.mediatracker.api.model;

/**
 * Created by igor on 7/11/15.
 */
public class Book {

    private final String title;
    private final String author;
    private long datePublished;
    private String coverImgUri;
    private String synopsis;
    //ToDo: Add cover image


    public Book(String title, String author, long datePublished, String coverImgUri, String synopsis) {
        this.title = title;
        this.author = author;
        this.datePublished = datePublished;
        this.coverImgUri = coverImgUri;
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public long getDatePublished() {
        return datePublished;
    }

    public String getCoverImgUri() {
        return coverImgUri;
    }

    public String getSynopsis() {
        return synopsis;
    }


}
