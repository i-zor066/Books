package com.izor066.android.mediatracker.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.izor066.android.mediatracker.MediaTrackerApplication;
import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.api.model.Book;

import java.util.ArrayList;
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
        List<Book> searchResultList = new ArrayList<>();
        searchResultList = MediaTrackerApplication.getSharedDataSource().searchResults(query);
        if (searchResultList.size() == 0) {
            Toast.makeText(this, "Nothing found", Toast.LENGTH_SHORT).show();
            this.finish();
        } else {
            Intent intent = new Intent(this, EntrySearchResultsActivity.class);
            intent.putParcelableArrayListExtra("resultList", (ArrayList<? extends Parcelable>) searchResultList);
            startActivity(intent);
        }
    }
}
