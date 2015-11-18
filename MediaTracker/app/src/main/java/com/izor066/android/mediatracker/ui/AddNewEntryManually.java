package com.izor066.android.mediatracker.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.izor066.android.mediatracker.MediaTrackerApplication;
import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.api.model.Book;
import com.izor066.android.mediatracker.ui.fragment.DatePickerFragment;

public class AddNewEntryManually extends AppCompatActivity implements DatePickerFragment.OnDatePubSetListener, Button.OnClickListener, TextView.OnEditorActionListener {

    String TAG = getClass().getSimpleName();

    String mAddTitle ="";
    EditText addTitle;
    String mAddAuthor ="";
    EditText addAuthor;
    int mPubDate = 0;
    String mAddSynopsis = "";
    EditText addSynopsis;
    String mAddCover ="https://s.gr-assets.com/assets/nophoto/book/blank-133x176-8b769f39ba6687a82d2eef30bdf46977.jpg";
    EditText addCover;
    Button submit;
    Button cancel;

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

        submit = (Button) findViewById(R.id.btn_add_book_submit);
        submit.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.btn_add_book_cancel);
        cancel.setOnClickListener(this);


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
        if (v.getId() == R.id.btn_add_book_cancel) {
            finish();
        } else {
            Book book = new Book(mAddTitle, mAddAuthor, (long) mPubDate, mAddCover, mAddSynopsis);
            MediaTrackerApplication.getSharedDataSource().insertBookToDatabase(book);
            finish();
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if((EditorInfo.IME_ACTION_DONE == actionId) && (v.getId() == R.id.et_add_new_title)) {
            mAddTitle = addTitle.getText().toString();
            Log.v(TAG, mAddTitle);
        }

        if((EditorInfo.IME_ACTION_DONE == actionId) && (v.getId() == R.id.et_add_new_author)) {
            mAddAuthor = addAuthor.getText().toString();
            Log.v(TAG, mAddAuthor);
        }

        if((EditorInfo.IME_ACTION_DONE == actionId) && (v.getId() == R.id.et_add_new_synopsis)) {
            mAddSynopsis = addSynopsis.getText().toString();
            Log.v(TAG, mAddSynopsis);
        }

        if((EditorInfo.IME_ACTION_DONE == actionId) && (v.getId() == R.id.et_add_cover)) {
            mAddCover = addCover.getText().toString();
            Log.v(TAG, mAddCover);
        }

        return false;
    }
}
