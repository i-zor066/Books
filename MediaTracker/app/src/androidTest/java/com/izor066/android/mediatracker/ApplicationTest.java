package com.izor066.android.mediatracker;

import android.test.ApplicationTestCase;
import android.test.RenamingDelegatingContext;

import com.izor066.android.mediatracker.api.model.Book;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<MediaTrackerApplication> {
    public ApplicationTest() {
        super(MediaTrackerApplication.class);
    }

    public void testApplicationHasDataSource() {
        setContext(new RenamingDelegatingContext(getContext(), "test_"));

        createApplication();

        MediaTrackerApplication application = getApplication();

        application.onCreate();

        assertNotNull(application.getDataSource());

    }

    public void testBookDatabaseInsert() {

        createApplication();

        MediaTrackerApplication application = getApplication();

        application.onCreate();

        assertNotNull(application.getDataSource());

        Book testBook = new Book("Plese z zmaji", "Zorz DvojniR Martinov", 1380585600, "https://d.gr-assets.com/books/1397215917l/17333324.jpg", "Povzetek");

        application.getDataSource().insertBookToDatabase(testBook);

        Book bookFromDB = application.getDataSource().getBookFromDBwithTitle(testBook.getTitle());

        assertEquals("Title not the same!", testBook.getTitle(), bookFromDB.getTitle());
        assertEquals("Author not the same!", testBook.getAuthor(), bookFromDB.getAuthor());
        assertEquals("DatePub not the same!", testBook.getDatePublished(), bookFromDB.getDatePublished());
        assertEquals("coverImg not the same!", testBook.getCoverImgUri(), bookFromDB.getCoverImgUri());
        assertEquals("Synopsis not the same!", testBook.getSynopsis(), bookFromDB.getSynopsis());
    }


}
