package com.izor066.android.mediatracker.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.izor066.android.mediatracker.MediaTrackerApplication;
import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.api.model.Book;

import java.util.List;

public class SearchableActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        handleIntent(getIntent());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);


        }
    }

    private void doMySearch(String query) {
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        List<Book> searchResultList = MediaTrackerApplication.getSharedDataSource().searchResults(query);
        Book book = searchResultList.get(0);
        Log.e("SEARCH", searchResultList.toString() + searchResultList.size());
    }
}
