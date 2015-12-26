package com.izor066.android.mediatracker.api;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.izor066.android.mediatracker.BuildConfig;
import com.izor066.android.mediatracker.api.model.Book;
import com.izor066.android.mediatracker.api.model.database.DatabaseOpenHelper;
import com.izor066.android.mediatracker.api.model.database.table.BooksTable;
import com.izor066.android.mediatracker.api.model.database.table.Table;

import java.util.ArrayList;
import java.util.List;

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
            //createPlaceholderData();

        }
    }


    public void insertBookToDatabase(Book book) {
        SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
        new BooksTable.Builder()
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setDatePublished(book.getDatePublished())
                .setCoverImgUri(book.getCoverImgUri())
                .setSynopsis(book.getSynopsis())
                .setPages(book.getPages())
                .setPublisher(book.getPublisher())
                .setTimeAdded(System.currentTimeMillis())
                .insert(writableDatabase);

    }

    public void editBookForRowId(Book book, long rowId) {
        SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
        new BooksTable.Builder()
                .setTitle(book.getTitle())
                .setAuthor(book.getAuthor())
                .setDatePublished(book.getDatePublished())
                .setCoverImgUri(book.getCoverImgUri())
                .setSynopsis(book.getSynopsis())
                .setPages(book.getPages())
                .setPublisher(book.getPublisher())
                .setTimeAdded(System.currentTimeMillis())
                .updateForRowId(writableDatabase, rowId);

    }

    public void deleteBookForRowId(long rowId) {
        SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();
        new BooksTable.Builder()
                .deleteForRowId(writableDatabase, rowId);

    }

    public Book getBookFromDBwithTitle(String title) {
        Cursor cursor = BooksTable.getRowFromTitle(databaseOpenHelper.getReadableDatabase(), title);
        cursor.moveToFirst();
        Book book = bookFromCursor(cursor);
        cursor.close();
        return book;
    }

    public Book getBookWithId(long rowId) {
        Cursor cursor = BooksTable.getRowWithId(databaseOpenHelper.getReadableDatabase(), rowId);
        cursor.moveToFirst();
        Book book = bookFromCursor(cursor);
        cursor.close();
        return book;
    }

    public List<Book> getAllBooks() {
        Cursor cursor = BooksTable.fetchAllBooks(databaseOpenHelper.getReadableDatabase());
        List<Book> allBooks = new ArrayList<Book>();
        if (cursor.moveToFirst()) {
            do {
                allBooks.add(bookFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allBooks;
    }

    public List<Book> searchResults(String query) {
        Cursor cursor = BooksTable.getAllForTitleAndAuthor(databaseOpenHelper.getReadableDatabase(), query);
        List<Book> allBooks = new ArrayList<Book>();
        if (cursor.moveToFirst()) {
            do {
                allBooks.add(bookFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allBooks;
    }

    private static Book bookFromCursor(Cursor cursor) {
        long rowId = Table.getRowId(cursor);
        String title = BooksTable.getTitle(cursor);
        String author = BooksTable.getAuthor(cursor);
        long datePublished = BooksTable.getDatePublished(cursor);
        String coverimgUri = BooksTable.getCoverImgUri(cursor);
        String synopsis = BooksTable.getSynopsis(cursor);
        int pages = BooksTable.getPages(cursor);
        String publisher = BooksTable.getPublisher(cursor);
        long timeAdded = BooksTable.getTimeAdded(cursor);
        return new Book(rowId, title, author, datePublished, coverimgUri, synopsis, pages, publisher, timeAdded);
    }



    private void createPlaceholderData() {
        SQLiteDatabase writableDatabase = databaseOpenHelper.getWritableDatabase();

        new BooksTable.Builder()
                .setTitle("A Dance With Dragons")
                .setAuthor("George R.R. Martin")
                .setDatePublished(1310428800000l)
                .setCoverImgUri("https://d.gr-assets.com/books/1327885335l/10664113.jpg")
                .setSynopsis("In the aftermath of a colossal battle, the future of the Seven Kingdoms hangs in the balance — beset by newly emerging threats from every direction. In the east, Daenerys Targaryen, the last scion of House Targaryen, rules with her three dragons as queen of a city built on dust and death. But Daenerys has thousands of enemies, and many have set out to find her. As they gather, one young man embarks upon his own quest for the queen, with an entirely different goal in mind.\n" +
                        "\n" +
                        "Fleeing from Westeros with a price on his head, Tyrion Lannister, too, is making his way to Daenerys. But his newest allies in this quest are not the rag-tag band they seem, and at their heart lies one who could undo Daenerys's claim to Westeros forever.\n" +
                        "\n" +
                        "Meanwhile, to the north lies the mammoth Wall of ice and stone — a structure only as strong as those guarding it. There, Jon Snow, 998th Lord Commander of the Night's Watch, will face his greatest challenge. For he has powerful foes not only within the Watch but also beyond, in the land of the creatures of ice.\n" +
                        "\n" +
                        "From all corners, bitter conflicts reignite, intimate betrayals are perpetrated, and a grand cast of outlaws and priests, soldiers and skinchangers, nobles and slaves, will face seemingly insurmountable obstacles. Some will fail, others will grow in the strength of darkness. But in a time of rising restlessness, the tides of destiny and politics will lead inevitably to the greatest dance of all.")
                .setPages(505)
                .setPublisher("Random House Publishing")
                .setTimeAdded(System.currentTimeMillis())
                .insert(writableDatabase);

        new BooksTable.Builder()
                .setTitle("The Dark Forest")
                .setAuthor("Liu Cixin")
                .setDatePublished(1439251200000l)
                .setCoverImgUri("https://d.gr-assets.com/books/1412064931l/23168817.jpg")
                .setSynopsis("This is the second novel in the \"Three Body\" near-future trilogy. Written by the China's multiple-award-winning science fiction author, Cixin Liu.\n" +
                        "\n" +
                        "In Dark Forest, Earth is reeling from the revelation of a coming alien invasion—four centuries in the future. The aliens' human collaborators have been defeated, but the presence of the sophons, the subatomic particles that allow Trisolaris instant access to all human information, means that Earth's defense plans are exposed to the enemy. Only the human mind remains a secret.\n" +
                        "\n" +
                        "This is the motivation for the Wallfacer Project, a daring plan that grants four men enormous resources to design secret strategies, hidden through deceit and misdirection from Earth and Trisolaris alike. Three of the Wallfacers are influential statesmen and scientists, but the fourth is a total unknown. Luo Ji, an unambitious Chinese astronomer and sociologist, is baffled by his new status. All he knows is that he's the one Wallfacer that Trisolaris wants dead.")
                .setPages(505)
                .setPublisher("Random House Publishing")
                .setTimeAdded(System.currentTimeMillis())
                .insert(writableDatabase);


        new BooksTable.Builder()
                .setTitle("The Diamond Age: or, A Young Lady's Illustrated Primer")
                .setAuthor("Neal Stephenson")
                .setDatePublished(957225600000l)
                .setCoverImgUri("https://d.gr-assets.com/books/1388180931l/827.jpg")
                .setSynopsis("The Diamond Age: Or, A Young Lady's Illustrated Primer is a postcyberpunk novel by Neal Stephenson. It is to some extent a science fiction coming-of-age story, focused on a young girl named Nell, and set in a future world in which nanotechnology affects all aspects of life. The novel deals with themes of education, social class, ethnicity, and the nature of artificial intelligence.")
                .setPages(505)
                .setPublisher("Random House Publishing")
                .setTimeAdded(System.currentTimeMillis())
                .insert(writableDatabase);

        new BooksTable.Builder()
                .setTitle("Reamde")
                .setAuthor("Neal Stephenson")
                .setDatePublished(1316476800000l)
                .setCoverImgUri("https://d.gr-assets.com/books/1305993115l/10552338.jpg")
                .setSynopsis("Four decades ago, Richard Forthrast, the black sheep of an Iowa family, fled to a wild and lonely mountainous corner of British Columbia to avoid the draft. Smuggling backpack loads of high-grade marijuana across the border into Northern Idaho, he quickly amassed an enormous and illegal fortune. With plenty of time and money to burn, he became addicted to an online fantasy game in which opposing factions battle for power and treasure in a vast cyber realm. Like many serious gamers, he began routinely purchasing virtual gold pieces and other desirables from Chinese gold farmers—young professional players in Asia who accumulated virtual weapons and armor to sell to busy American and European buyers.\n" +
                        "\n" +
                        "For Richard, the game was the perfect opportunity to launder his aging hundred dollar bills and begin his own high-tech start up—a venture that has morphed into a Fortune 500 computer gaming group, Corporation 9592, with its own super successful online role-playing game, T’Rain. But the line between fantasy and reality becomes dangerously blurred when a young gold farmer accidently triggers a virtual war for dominance—and Richard is caught at the center.\n" +
                        "\n" +
                        "In this edgy, 21st century tale, Neal Stephenson, one of the most ambitious and prophetic writers of our time, returns to the terrain of his cyberpunk masterpieces Snow Crash and Cryptonomicon, leading readers through the looking glass and into the dark heart of imagination.")
                .insert(writableDatabase);

        new BooksTable.Builder()
                .setTitle("The Peripheral ")
                .setAuthor("William Gibson")
                .setDatePublished(1316476800000l)
                .setCoverImgUri("https://d.gr-assets.com/books/1402651292l/20821159.jpg")
                .setSynopsis("Where Flynne and her brother, Burton, live, jobs outside the drug business are rare. Fortunately, Burton has his veteran’s benefits, for neural damage he suffered from implants during his time in the USMC’s elite Haptic Recon force. Then one night Burton has to go out, but there’s a job he’s supposed to do—a job Flynne didn’t know he had. Beta-testing part of a new game, he tells her. The job seems to be simple: work a perimeter around the image of a tower building. Little buglike things turn up. He’s supposed to get in their way, edge them back. That’s all there is to it. He’s offering Flynne a good price to take over for him. What she sees, though, isn’t what Burton told her to expect. It might be a game, but it might also be murder.")
                .setPages(505)
                .setPublisher("Random House Publishing")
                .setTimeAdded(System.currentTimeMillis())
                .insert(writableDatabase);

        new BooksTable.Builder()
                .setTitle("Redshirts: A Novel with Three Codas ")
                .setAuthor("John Scalzi")
                .setDatePublished(1370390400000l)
                .setCoverImgUri("https://d.gr-assets.com/books/1339166503l/15517815.jpg")
                .setSynopsis("Ensign Andrew Dahl has just been assigned to the Universal Union Capital Ship Intrepid, flagship of the Universal Union since the year 2456. It’s a prestige posting, and Andrew is thrilled all the more to be assigned to the ship’s Xenobiology laboratory.\n" +
                        "\n" +
                        "Life couldn’t be better…until Andrew begins to pick up on the fact that (1) every Away Mission involves some kind of lethal confrontation with alien forces, (2) the ship’s captain, its chief science officer, and the handsome Lieutenant Kerensky always survive these confrontations, and (3) at least one low-ranked crew member is, sadly, always killed.\n" +
                        "\n" +
                        "Not surprisingly, a great deal of energy below decks is expended on avoiding, at all costs, being assigned to an Away Mission. Then Andrew stumbles on information that completely transforms his and his colleagues’ understanding of what the starship Intrepid really is…and offers them a crazy, high-risk chance to save their own lives")
                .setPages(505)
                .setPublisher("Random House Publishing")
                .setTimeAdded(System.currentTimeMillis())
                .insert(writableDatabase);

        new BooksTable.Builder()
                .setTitle("Ancilliary Justice")
                .setAuthor("Ann Leckie")
                .setDatePublished(1380585600000l)
                .setCoverImgUri("https://d.gr-assets.com/books/1397215917l/17333324.jpg")
                .setSynopsis("On a remote, icy planet, the soldier known as Breq is drawing closer to completing her quest. Once, she was the Justice of Toren - a colossal starship with an artificial intelligence linking thousands of soldiers in the service of the Radch, the empire that conquered the galaxy. Now, an act of treachery has ripped it all away, leaving her with one fragile human body, unanswered questions, and a burning desire for vengeance.")
                .setPages(505)
                .setPublisher("Random House Publishing")
                .setTimeAdded(System.currentTimeMillis())
                .insert(writableDatabase);


    }


}
