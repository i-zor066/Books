package com.izor066.android.mediatracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import com.izor066.android.mediatracker.GoogleBooks.Search;
import com.izor066.android.mediatracker.MediaTrackerApplication;
import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.api.model.Book;
import com.izor066.android.mediatracker.ui.adapter.SearchResultsAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchGoogleBooks extends AppCompatActivity implements TextView.OnEditorActionListener, SearchResultsAdapter.OnResultClickListener {

    private final String TAG = getClass().getSimpleName();

    private String searchString = "";
    private EditText searchGoogleBooks;
    private List<Book> resultsToAdd = new ArrayList<Book>();
    private SearchResultsAdapter searchResultsAdapter;
    private RecyclerView recyclerView;
    private ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_google_books);

        searchGoogleBooks = (EditText) findViewById(R.id.et_search_googlebooks);

        searchGoogleBooks.setOnEditorActionListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Book placeholderBook = MediaTrackerApplication.getSharedDataSource().getAllBooks().get(0);
        resultsToAdd.add(placeholderBook);

        searchResultsAdapter = new SearchResultsAdapter(resultsToAdd, this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_search_results);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(searchResultsAdapter);

        recyclerView.setVisibility(View.GONE);

        pb = (ProgressBar) findViewById(R.id.pb_search_books_item);
        pb.setVisibility(View.GONE);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchString = searchGoogleBooks.getText().toString();
            Toast.makeText(this, "Search for: " + searchString, Toast.LENGTH_SHORT).show();
            SearchTask task = new SearchTask(resultsToAdd, searchResultsAdapter, pb, recyclerView);
            task.execute(searchString);
            InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            return true;
        }
        return false;
    }

    @Override
    public void onResultClick(Book book) {
        Toast.makeText(this, "View details for: " + book.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SearchResultDetails.class);
        intent.putExtra("Book", book);
        this.startActivity(intent);
    }

    @Override
    public void onResultAddClick(Book book) {
        Toast.makeText(this, "Added entry: " + book.getTitle(), Toast.LENGTH_SHORT).show();
        MediaTrackerApplication.getSharedDataSource().insertBookToDatabase(book);
    }

    private static class SearchTask extends AsyncTask<String, Void, Volumes> {
        private final List<Book> resultsToAdd;
        private final SearchResultsAdapter searchResultsAdapter;
        private final ProgressBar pb;
        private final RecyclerView recyclerView;
        private final SimpleDateFormat simpleDateformatComplete = new SimpleDateFormat("yyyy-MM-dd");
        private final SimpleDateFormat simpleDateFormatYm = new SimpleDateFormat("yyyy-MM");
        private final SimpleDateFormat simpleDateFormatY = new SimpleDateFormat("yyyy");
        private final String IMAGE_PLACEHOLDER = "https://s.gr-assets.com/assets/nophoto/book/blank-133x176-8b769f39ba6687a82d2eef30bdf46977.jpg";
        private final String AUTHOR_PLACEHOLDER = "Anonymous";

        private final String TAG = getClass().getSimpleName();

        public SearchTask(List<Book> resultsToAdd, SearchResultsAdapter searchResultsAdapter, ProgressBar pb, RecyclerView recyclerView) {
            this.resultsToAdd = resultsToAdd;
            this.searchResultsAdapter = searchResultsAdapter;
            this.pb = pb;
            this.recyclerView = recyclerView;
        }

        @Override
        protected void onPreExecute() {
            resultsToAdd.clear();
            searchResultsAdapter.notifyDataSetChanged();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Volumes doInBackground(String... params) {
            Log.v(TAG + "Search term", String.valueOf(params));
            return Search.searchVolumes(params[0]);
        }

        @Override
        protected void onPostExecute(Volumes searchListResponse) {
            if (searchListResponse == null) {
                pb.setVisibility(View.GONE);
                return;
            }

            Log.d(TAG, String.valueOf(searchListResponse));


            if (searchListResponse.getItems() == null) {
                pb.setVisibility(View.GONE);
                return;
            }

            for (int i = 0; i < searchListResponse.getItems().size(); i++) {
                Volume volume = searchListResponse.getItems().get(i);

                String authorsAll;

                if (volume.getVolumeInfo().getAuthors() == null) {
                   authorsAll = AUTHOR_PLACEHOLDER;
                } else {

                List<String> authors = volume.getVolumeInfo().getAuthors();
                StringBuilder sb = new StringBuilder();
                for (String author : authors) {
                    sb.append(", " + author);
                }
                authorsAll = sb.toString().replaceFirst(", ", "");
                }

                String thumbnail;

                if (volume.getVolumeInfo().getImageLinks() == null) {
                    thumbnail = IMAGE_PLACEHOLDER;
                } else {
                    thumbnail = volume.getVolumeInfo().getImageLinks().getThumbnail();
                }

                String dateString = volume.getVolumeInfo().getPublishedDate();
                Log.e(TAG, "Date: " + dateString);


                Date date = new Date();
                if (dateString!=null) {

                    try {
                        date = simpleDateformatComplete.parse(dateString);
                    } catch (ParseException e) {
                        Log.e(TAG, "Could not parse: " + dateString + ", switching to yyyy-MM format.");
                        try {
                            date = simpleDateFormatYm.parse(dateString);
                        } catch (ParseException f) {
                            Log.e(TAG, "Could not parse: " + dateString + ", switching to yyyy format.");
                            try {
                                date = simpleDateFormatY.parse(dateString);
                            } catch (ParseException g) {
                                Log.e(TAG, "Could not parse: " + dateString + ". Human sacrifice, dogs and cats living together, mass hysteria.");
                            }
                        }
                    }
                }


                Book book = new Book(
                        volume.getVolumeInfo().getTitle(),
                        authorsAll,
                        date.getTime(),
                        thumbnail,
                        volume.getVolumeInfo().getDescription());

                resultsToAdd.add(book);
            }
            pb.setVisibility(View.GONE);

            searchResultsAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);


        }


    }


}
