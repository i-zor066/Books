package com.izor066.android.mediatracker.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.izor066.android.mediatracker.MediaTrackerApplication;
import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.api.model.Book;
import com.izor066.android.mediatracker.ui.fragment.DatePickerFragment;
import com.squareup.picasso.Picasso;

public class AddNewEntryManually extends AppCompatActivity implements DatePickerFragment.OnDatePubSetListener, Button.OnClickListener, TextView.OnEditorActionListener {

    String TAG = getClass().getSimpleName();

    private final String IMAGE_PLACEHOLDER = "https://s.gr-assets.com/assets/nophoto/book/blank-133x176-8b769f39ba6687a82d2eef30bdf46977.jpg"; // ToDo: your own resource for this

    private String mAddTitle = "";
    private EditText addTitle;
    private String mAddAuthor = "";
    private EditText addAuthor;
    private int mPubDate = 0;
    private String mAddSynopsis = "";
    private EditText addSynopsis;
    private String mAddCover = IMAGE_PLACEHOLDER;
    private EditText addCover;
    private ImageView coverPreview;
    private Button setCover;
    private Button submit;
    private Button cancel;
    private EditText addNumberOfPages;
    private int mPages;
    private EditText addPublisher;
    private String mPublisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_entry_manually);

        addTitle = (EditText) findViewById(R.id.et_add_new_title);
        addTitle.setOnEditorActionListener(this);

        addAuthor = (EditText) findViewById(R.id.et_add_new_author);
        addAuthor.setOnEditorActionListener(this);

        addSynopsis = (EditText) findViewById(R.id.et_add_new_synopsis);
        addSynopsis.setOnEditorActionListener(this);

        addCover = (EditText) findViewById(R.id.et_add_cover);
        addCover.setOnEditorActionListener(this);

        addNumberOfPages = (EditText) findViewById(R.id.et_add_new_pages);
        addNumberOfPages.setOnEditorActionListener(this);

        addPublisher = (EditText) findViewById(R.id.et_add_new_publisher);
        addPublisher.setOnEditorActionListener(this);

        coverPreview = (ImageView) findViewById(R.id.iv_img_preview);

        if (mAddCover != IMAGE_PLACEHOLDER) {
            coverPreview.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(mAddCover)
                    .into(coverPreview);
        }

        setCover = (Button) findViewById(R.id.btn_set_cover);
        setCover.setOnClickListener(this);

        submit = (Button) findViewById(R.id.btn_add_book_submit);
        submit.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.btn_add_book_cancel);
        cancel.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void updateDatePub(int datePub) {
        mPubDate = datePub;
    }

    @Override
    public void onDatePubSet(int datePub) {
        mPubDate = datePub;

        Log.v(TAG, String.valueOf(mPubDate));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_set_cover) {
            loadCover();
        }
        if (v.getId() == R.id.btn_add_book_cancel) {
            finish();
        }

        if (v.getId() == R.id.btn_add_book_submit) {
            mAddTitle = addTitle.getText().toString();
            mAddAuthor = addAuthor.getText().toString();
            mAddSynopsis = addSynopsis.getText().toString();
            mAddCover = addCover.getText().toString();
            mPages = Integer.parseInt(addNumberOfPages.getText().toString());
            mPublisher = addPublisher.getText().toString();
            Log.v(TAG, mAddSynopsis);
            Book book = new Book(mAddTitle, mAddAuthor, (long) mPubDate, mAddCover, mAddSynopsis, mPages, mPublisher, System.currentTimeMillis());
            MediaTrackerApplication.getSharedDataSource().insertBookToDatabase(book);
            finish();
        }

    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((EditorInfo.IME_ACTION_NEXT == actionId) && (v.getId() == R.id.et_add_new_title)) {
            mAddTitle = addTitle.getText().toString();
            Log.v(TAG, mAddTitle);
        }

        if ((EditorInfo.IME_ACTION_NEXT == actionId) && (v.getId() == R.id.et_add_new_author)) {
            mAddAuthor = addAuthor.getText().toString();
            Log.v(TAG, mAddAuthor);
        }

        if ((EditorInfo.IME_ACTION_NEXT == actionId) && (v.getId() == R.id.et_add_new_synopsis)) {
            mAddSynopsis = addSynopsis.getText().toString();
            Log.v(TAG, mAddSynopsis);
        }

        if ((EditorInfo.IME_ACTION_NEXT == actionId) && (v.getId() == R.id.et_add_new_pages)) {
            mPages = Integer.parseInt(addNumberOfPages.getText().toString());
            Log.v(TAG, String.valueOf(mPages));
        }

        if ((EditorInfo.IME_ACTION_NEXT == actionId) && (v.getId() == R.id.et_add_new_publisher)) {
            mPublisher = addPublisher.getText().toString();
            Log.v(TAG, mPublisher);
        }

        if ((EditorInfo.IME_ACTION_DONE == actionId) && (v.getId() == R.id.et_add_cover)) {

            loadCover();

            // ToDO: URL validation

        }

        return false;
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

    private void loadCover() {
        if (addCover.getText().length() == 0) {
            mAddCover = IMAGE_PLACEHOLDER;

        } // has to be in, otherwise the placeholder won't load if the user enters a non-valid url and then deletes it

        if (addCover.getText().length() > 0) {
            mAddCover = addCover.getText().toString();
        }

        coverPreview.setVisibility(View.VISIBLE);
        Picasso.with(this)
                .load(mAddCover)
                .into(coverPreview);
    }
}
