package com.izor066.android.mediatracker.api.model.database.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by igor on 7/11/15.
 */
public class BooksTable extends Table {


    private static final String NAME ="books";

    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_DATE_PUBLISHED = "date_published";
    private static final String COLUMN_COVER_IMG_URI = "cover_img_uri";
    private static final String COLUMN_SYNOPSIS = "synopsis";

    public static class Builder implements Table.Builder {

        ContentValues values = new ContentValues();

        public Builder setTitle(String title) {
            values.put(COLUMN_TITLE, title);
            return this;
        }

        public Builder setAuthor(String author) {
            values.put(COLUMN_AUTHOR, author);
            return this;
        }

        public Builder setDatePublished(long datePublished) {
            values.put(COLUMN_DATE_PUBLISHED, datePublished);
            return this;
        }

        public Builder setCoverImgUri(String coverImgUri) {
            values.put(COLUMN_COVER_IMG_URI, coverImgUri);
            return this;
        }

        public Builder setSynopsis(String synopsis) {
            values.put(COLUMN_SYNOPSIS, synopsis);
            return this;
        }

        @Override
        public long insert(SQLiteDatabase writableDB) {
            return writableDB.insert(BooksTable.NAME, null, values);
        }
    }

    public static String getTitle(Cursor cursor) {
        return getString(cursor, COLUMN_TITLE);
    }

    public static String getAuthor(Cursor cursor) {
        return getString(cursor, COLUMN_AUTHOR);
    }


    public static long getDatePublished(Cursor cursor) {
        return getLong(cursor, COLUMN_DATE_PUBLISHED);
    }

    public static String getCoverImgUri(Cursor cursor) {
        return getString(cursor, COLUMN_COVER_IMG_URI);
    }

    public static String getSynopsis(Cursor cursor) {
        return getString(cursor, COLUMN_SYNOPSIS);
    }

    public static Cursor getRowFromTitle(SQLiteDatabase readonlyDatabase, String title) {
        return readonlyDatabase.query(true, NAME, null, COLUMN_TITLE + " = ?", new String[]{title}, null, null, null, null);
    } // ToDo: Implement a different recovery method, titles might duplicate

    public static Cursor fetchAllBooks(SQLiteDatabase readonlyDatabase) {
        return readonlyDatabase.rawQuery("SELECT * FROM " + NAME + " ORDER BY ?", new String[]{COLUMN_TITLE});
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getCreateStatement() {
        return "CREATE TABLE " + getName() + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_AUTHOR + " TEXT, "
                + COLUMN_DATE_PUBLISHED + " INTEGER,"
                + COLUMN_COVER_IMG_URI + " TEXT,"
                + COLUMN_SYNOPSIS + " TEXT)";
    }

}
