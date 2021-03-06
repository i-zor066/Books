package com.izor066.android.mediatracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.izor066.android.mediatracker.MediaTrackerApplication;
import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.api.model.Book;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchResultDetails extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();

    private Book book;

    private TextView title;
    private TextView author;
    private ImageView cover;
    private TextView synopsis;
    private FloatingActionButton fabAdd;
    private TextView datePublished;
    private TextView pages;
    private TextView publisher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView) findViewById(R.id.tv_book_details_title);
        author = (TextView) findViewById(R.id.tv_book_details_author);
        cover = (ImageView) findViewById(R.id.iv_img_details_placeholder);
        synopsis = (TextView) findViewById(R.id.tv_details_synopsis);
        datePublished = (TextView) findViewById(R.id.tv_details_date_published);
        pages = (TextView) findViewById(R.id.tv_details_pages);
        publisher = (TextView) findViewById(R.id.tv_details_publisher);


        Intent intent = getIntent();
        book = intent.getParcelableExtra("Book");

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        synopsis.setText(book.getSynopsis());
        pages.setText(String.valueOf(book.getPages()));
        publisher.setText(book.getPublisher());

        Picasso.with(this)
                .load(book.getCoverImgUri())
                .into(cover);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMM, y");
        String datePub = "Unknown";
        Date date = new Date(book.getDatePublished());
        datePub = simpleDateFormat.format(date);

        datePublished.setText(datePub);


        fabAdd = (FloatingActionButton) findViewById(R.id.fab_book_details_add);
        fabAdd.setOnClickListener(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Added entry: " + book.getTitle(), Toast.LENGTH_SHORT).show();
        MediaTrackerApplication.getSharedDataSource().insertBookToDatabase(book);
        finish();
    }

}
