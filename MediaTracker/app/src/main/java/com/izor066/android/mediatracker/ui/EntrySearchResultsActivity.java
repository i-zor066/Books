package com.izor066.android.mediatracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.api.model.Book;
import com.izor066.android.mediatracker.ui.adapter.EntrySearchResultsAdapter;

import java.util.ArrayList;

public class EntrySearchResultsActivity extends AppCompatActivity implements EntrySearchResultsAdapter.OnEntryClickListener {

    private ArrayList<Book> searchResults = new ArrayList<>(); // get from intent
    private EntrySearchResultsAdapter entrySearchResultsAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_search_results);

        Intent intent = getIntent();
        searchResults =  intent.getParcelableArrayListExtra("resultList");
        entrySearchResultsAdapter = new EntrySearchResultsAdapter(searchResults, this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_entry_search_results);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(entrySearchResultsAdapter);

    }

    @Override
    public void onEntryClick(Book book) {
        boolean isEntrySearchResult = true;
        Intent intent = new Intent(this, BookDetails.class);
        intent.putExtra("Book", book);
        intent.putExtra("isEntrySearchResult", isEntrySearchResult);
        this.startActivity(intent);
    }


}
