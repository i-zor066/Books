package com.izor066.android.mediatracker.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ItemAdapter.OnBookClickListener, SortItemsDialogFragment.OnSortingOptionSelectedListener {

    String TAG = getClass().getSimpleName();
    private ItemAdapter itemAdapter;
    private String currentSortOrder = "added";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort) {
            showSortItemsDialogFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_books) {
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_tv_series) {
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(this, "Add new entry", Toast.LENGTH_SHORT).show();
        showAddBookDialogFragment();
    }

    @Override
    public void onBookClick(Book book) {
       // Toast.makeText(this, book.getTitle(), Toast.LENGTH_SHORT).show();
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
        Bundle args = new Bundle();
        args.putString("sort", currentSortOrder);
        FragmentManager fragmentManager = getSupportFragmentManager();
        SortItemsDialogFragment sortItemsDialogFragment = new SortItemsDialogFragment();
        sortItemsDialogFragment.setArguments(args);
        sortItemsDialogFragment.show(fragmentManager, "Sort");
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTitleSelected() {
        Toast.makeText(this, "TITLE", Toast.LENGTH_SHORT).show();
        currentSortOrder = "title";
        itemAdapter.changeSortOrder(currentSortOrder);
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAuthorSelected() {
        Toast.makeText(this, "AUTHOR", Toast.LENGTH_SHORT).show();
        currentSortOrder = "author";
        itemAdapter.changeSortOrder(currentSortOrder);
        itemAdapter.notifyDataSetChanged();

    }

    @Override
    public void onAddedSelected() {
        Toast.makeText(this, "ADDED", Toast.LENGTH_SHORT).show();
        currentSortOrder = "added";
        itemAdapter.changeSortOrder(currentSortOrder);
        itemAdapter.notifyDataSetChanged();
    }
}
