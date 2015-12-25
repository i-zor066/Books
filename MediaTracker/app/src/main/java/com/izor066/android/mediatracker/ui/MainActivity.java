package com.izor066.android.mediatracker.ui;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.izor066.android.mediatracker.MediaTrackerApplication;
import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.api.model.Book;
import com.izor066.android.mediatracker.ui.adapter.ItemAdapter;
import com.izor066.android.mediatracker.ui.fragment.AddBookDialogFragment;
import com.izor066.android.mediatracker.ui.fragment.SortItemsDialogFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener, ItemAdapter.OnBookClickListener, SortItemsDialogFragment.OnSortingOptionSelectedListener {

    private String TAG = getClass().getSimpleName();
    private ItemAdapter itemAdapter;
    private ItemAdapter.SortCriteria currentSortCriteria = ItemAdapter.SortCriteria.ADDED;
    private boolean isAscending = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher_white);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);



        List<Book> allbooks = MediaTrackerApplication.getSharedDataSource().getAllBooks();


        itemAdapter = new ItemAdapter(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_activity_media_tracker);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);

        TextView firstRun = (TextView) findViewById(R.id.tv_click_plus);

        if (allbooks.size() > 0) {
            firstRun.setVisibility(View.GONE);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_order) {
            isAscending = !isAscending;
            itemAdapter.changeSortOrder();
            itemAdapter.notifyDataSetChanged();
            return true;
        }

        if (id == R.id.action_sort) {
            showSortItemsDialogFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        showAddBookDialogFragment();
    }

    @Override
    public void onBookClick(Book book) {
        Intent intent = new Intent(this, BookDetails.class);
        intent.putExtra("Book", book);
        this.startActivity(intent);
    }

    private void showAddBookDialogFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddBookDialogFragment addBookDialogFragment = new AddBookDialogFragment();
        addBookDialogFragment.show(fragmentManager, "New Entry");
    }

    private void showSortItemsDialogFragment() {
        SortItemsDialogFragment sortItemsDialogFragment = SortItemsDialogFragment.createWith(currentSortCriteria);
        sortItemsDialogFragment.show(getSupportFragmentManager(), "sort");
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTitleSelected() {
        Toast.makeText(this, "Sorting by Title", Toast.LENGTH_SHORT).show();
        currentSortCriteria = ItemAdapter.SortCriteria.TITLE;
        itemAdapter.changeSortCriteria(currentSortCriteria);
        itemAdapter.notifyDataSetChanged();

    }

    @Override
    public void onAuthorSelected() {
        Toast.makeText(this, "Sorting by Author", Toast.LENGTH_SHORT).show();
        currentSortCriteria = ItemAdapter.SortCriteria.AUTHOR;
        itemAdapter.changeSortCriteria(currentSortCriteria);
        itemAdapter.notifyDataSetChanged();


    }

    @Override
    public void onAddedSelected() {
        Toast.makeText(this, "Sorting by Time Added", Toast.LENGTH_SHORT).show();
        currentSortCriteria = ItemAdapter.SortCriteria.ADDED;
        itemAdapter.changeSortCriteria(currentSortCriteria);
        itemAdapter.notifyDataSetChanged();

    }
}
