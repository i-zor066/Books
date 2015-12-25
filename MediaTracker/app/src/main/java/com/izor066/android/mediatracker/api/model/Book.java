package com.izor066.android.mediatracker.api.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by igor on 7/11/15.
 */
public class Book implements Parcelable {

    private final long rowId;
    private final String title;
    private final String author;
    private final long datePublished;
    private final String coverImgUri;
    private final String synopsis;
    private final int pages;
    private final String publisher;
    private final long timeAdded;


    public Book(long rowId, String title, String author, long datePublished, String coverImgUri, String synopsis, int pages, String publisher, long timeAdded) {
        this.rowId = rowId;
        this.title = title;
        this.author = author;
        this.datePublished = datePublished;
        this.coverImgUri = coverImgUri;
        this.synopsis = synopsis;
        this.pages = pages;
        this.publisher = publisher;
        this.timeAdded = timeAdded;
    }

    @Override
    public String toString() {
        return "Book{" +
                "rowId='" + rowId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", datePublished=" + datePublished +
                ", coverImgUri='" + coverImgUri + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", pages='" + pages + '\'' +
                ", publisher='" + publisher + '\'' +
                ", timeAdded='" + timeAdded + '\'' +
                '}';
    }

    public long getRowId() {
        return rowId;
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

    public int getPages() {
        return pages;
    }

    public String getPublisher() {
        return publisher;
    }

    public long getTimeAdded() {
        return timeAdded;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();

        bundle.putLong("rowId", rowId);
        bundle.putString("title", title);
        bundle.putString("author", author);
        bundle.putLong("datePublished", datePublished);
        bundle.putString("coverImgUri", coverImgUri);
        bundle.putString("synopsis", synopsis);
        bundle.putInt("pages", pages);
        bundle.putString("publisher", publisher);
        bundle.putLong("timeAdded", timeAdded);

        dest.writeBundle(bundle);

    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel source) {
            Bundle bundle = source.readBundle();

            return new Book(
                    bundle.getLong("rowId"),
                    bundle.getString("title"),
                    bundle.getString("author"),
                    bundle.getLong("datePublished"),
                    bundle.getString("coverImgUri"),
                    bundle.getString("synopsis"),
                    bundle.getInt("pages"),
                    bundle.getString("publisher"),
                    bundle.getLong("timeAdded"));
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
