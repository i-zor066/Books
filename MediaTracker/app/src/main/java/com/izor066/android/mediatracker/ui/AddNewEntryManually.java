package com.izor066.android.mediatracker.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.izor066.android.mediatracker.MediaTrackerApplication;
import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.api.model.Book;
import com.izor066.android.mediatracker.ui.fragment.DatePickerFragment;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddNewEntryManually extends AppCompatActivity implements DatePickerFragment.OnDatePubSetListener, Button.OnClickListener, TextView.OnEditorActionListener {

    String TAG = getClass().getSimpleName();

    private final String IMAGE_PLACEHOLDER = Uri.parse("android.resource://com.izor066.android.mediatracker/" + R.drawable.cover_placeholder).toString();
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1111;


    private String mAddTitle = "";
    private EditText addTitle;
    private String mAddAuthor = "";
    private EditText addAuthor;
    private int mPubDate = 0;
    private String mAddSynopsis = "";
    private EditText addSynopsis;
    private String mAddCover = IMAGE_PLACEHOLDER;
    private ImageView coverPreview;
    private Button camera;
    private Button setCover;
    private Button submit;
    private Button cancel;
    private EditText addNumberOfPages;
    private int mPages;
    private EditText addPublisher;
    private String mPublisher;
    private boolean isImgLoaded = false;


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

        camera = (Button) findViewById(R.id.btn_camera);
        camera.setOnClickListener(this);

        setCover = (Button) findViewById(R.id.btn_pick_img);
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
        if (v.getId() == R.id.btn_pick_img) {
            captureCoverFromGallery();
        }

        if (v.getId() == R.id.btn_camera) {
            captureCoverWithCamera();
        }
        if (v.getId() == R.id.btn_add_book_cancel) {
            finish();
        }

        if (v.getId() == R.id.btn_add_book_submit) {

            if ((addTitle.getText().toString()).equals("")) {
                Toast.makeText(this, "Title cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                mAddTitle = addTitle.getText().toString();
            }

            if ((addAuthor.getText().toString()).equals("")) {
                mAddAuthor = "Unknown";
            } else {
                mAddAuthor = addAuthor.getText().toString();
            }
            mAddSynopsis = addSynopsis.getText().toString();

            if ((addNumberOfPages.getText().toString()).equals("")) {
                mPages = 0;
            } else {
                mPages = Integer.parseInt(addNumberOfPages.getText().toString());
            }

            if (!isImgLoaded) {
                mAddCover = IMAGE_PLACEHOLDER;
            }
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

    private void captureCoverFromGallery() {

        Intent pickPictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (pickPictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(pickPictureIntent, RESULT_LOAD_IMAGE);
        }

    }

    private void captureCoverWithCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mAddCover = cursor.getString(columnIndex);
            cursor.close();

            Log.e(TAG, mAddCover);

            coverPreview.setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(new File(mAddCover))
                    .into(coverPreview);
            isImgLoaded = true;
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap bitmapRaw = (Bitmap) extras.get("data");
            Bitmap imageBitmap = rotateBitmap(bitmapRaw, 90);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mAddCover = destination.getAbsolutePath();


            Log.e(TAG, mAddCover);

            coverPreview.setVisibility(View.VISIBLE);

            Picasso.with(this)
                    .load(new File(mAddCover))
                    .into(coverPreview);
            isImgLoaded = true;

        }

    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}
