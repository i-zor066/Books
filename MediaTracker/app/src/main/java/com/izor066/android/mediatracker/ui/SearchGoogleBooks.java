package com.izor066.android.mediatracker.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.izor066.android.mediatracker.R;

public class SearchGoogleBooks extends AppCompatActivity implements TextView.OnEditorActionListener{

    String TAG = getClass().getSimpleName();

    private String searchString ="";
    EditText searchGoogleBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_google_books);

        searchGoogleBooks = (EditText) findViewById(R.id.et_search_googlebooks);

        searchGoogleBooks.setOnEditorActionListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            return true;
        }
        return false;
    }
    
}
