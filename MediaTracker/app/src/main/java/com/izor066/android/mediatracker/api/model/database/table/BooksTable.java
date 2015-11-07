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

    public static Cursor getRowFromTitle(SQLiteDatabase readonlyDatabase, String title) {
        return readonlyDatabase.query(true, NAME, null, COLUMN_TITLE + " = ?",
                new String[]{title}, null, null, null, null);
    } // ToDo: Implement a different recovery method, titles might duplicate

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getCreateStatement() {
        return "CREATE TABLE " + getName() + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DATE_PUBLISHED + " INTEGER)";
    }

}
