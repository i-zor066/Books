package com.izor066.android.mediatracker.ui.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.izor066.android.mediatracker.R;
import com.izor066.android.mediatracker.ui.AddNewEntryManually;
import com.izor066.android.mediatracker.ui.SearchGoogleBooks;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBookDialogFragment extends DialogFragment implements Button.OnClickListener {

    Button addManually;
    Button searchGoodReads;
    Button cancel;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //dialog.getWindow().requestFeature(Window.FEATURE_CUSTOM_TITLE);
        dialog.setTitle("New Entry");
        return dialog;

        // ToDO: Show Title "New Entry"
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_book_dialog, container, false);
        addManually = (Button) view.findViewById(R.id.bt_add_manually);
        searchGoodReads = (Button) view.findViewById(R.id.bt_search_goodreads);
        cancel = (Button) view.findViewById(R.id.bt_add_book_cancel);

        addManually.setOnClickListener(this);
        searchGoodReads.setOnClickListener(this);
        cancel.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {

        if (v == v.findViewById(R.id.bt_add_manually)) {
            dismiss();
            Intent intent = new Intent(getActivity(), AddNewEntryManually.class);
            this.startActivity(intent);
        } else if (v == v.findViewById(R.id.bt_search_goodreads)) {
            dismiss();
            Intent intent = new Intent(getActivity(), SearchGoogleBooks.class);
            this.startActivity(intent);
        } else {
            dismiss();
        }

    }
}
