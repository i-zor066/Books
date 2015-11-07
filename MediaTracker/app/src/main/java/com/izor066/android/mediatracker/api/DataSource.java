package com.izor066.android.mediatracker.api;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.izor066.android.mediatracker.BuildConfig;
import com.izor066.android.mediatracker.api.model.Book;
import com.izor066.android.mediatracker.api.model.database.DatabaseOpenHelper;
import com.izor066.android.mediatracker.api.model.database.table.BooksTable;

/**
 * Created by igor on 7/11/15.
 */

public class DataSource {

    private DatabaseOpenHelper databaseOpenHelper;
    private BooksTable booksTable;


    public DataSource(Context context) {
        booksTable = new BooksTable();
        databaseOpenHelper = new DatabaseOpenHelper(context, booksTable);

        if (BuildConfig.DEBUG) {
            context.deleteDatabase(DatabaseOpenHelper.NAME);
            SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();

        }
    }


    public void insertBookToDatabase(Book book) {
        SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
        new BooksTable.Builder()
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthpr())
                .setDatePublished(book.getDatePublished())
                .insert(writableDatabase);
    }

    public Book getBookFromDBwithTitle(String title) {
        Cursor cursor = BooksTable.getRowFromTitle(databaseOpenHelper.getReadableDatabase(), title);
        cursor.moveToFirst();
        Book book = bookFromCursor(cursor);
        cursor.close();
        return book;
    }

    private static Book bookFromCursor(Cursor cursor) {
        String title = BooksTable.getTitle(cursor);
        String author = BooksTable.getAuthor(cursor);
        long datePublished = BooksTable.getDatePublished(cursor);
        return new Book(title, author, datePublished);
    }


}
