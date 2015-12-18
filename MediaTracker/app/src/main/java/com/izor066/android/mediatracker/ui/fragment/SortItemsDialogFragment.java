package com.izor066.android.mediatracker.ui.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.izor066.android.mediatracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortItemsDialogFragment extends DialogFragment implements RadioGroup.OnCheckedChangeListener {

    private RadioButton title;
    private RadioButton author;
    private RadioButton added;
    private RadioGroup sortItems;
    private OnSortingOptionSelectedListener onSortingOptionSelectedListener;
    private String currentSortOrder = "added";


    public SortItemsDialogFragment() {
        // Required empty public constructor
    }

    public static interface OnSortingOptionSelectedListener {
        public void onTitleSelected();
        public void onAuthorSelected();
        public void onAddedSelected();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            onSortingOptionSelectedListener = (OnSortingOptionSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSortingOptionSelectedListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Select sorting criteria");
        return dialog;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort_items_dialog, container, false);

        title = (RadioButton) view.findViewById(R.id.radio_title);
        author = (RadioButton) view.findViewById(R.id.radio_author);
        added = (RadioButton) view.findViewById(R.id.radio_added);

        currentSortOrder = getArguments().getString("sort");

        sortItems = (RadioGroup) view.findViewById(R.id.rg_sort);
        sortItems.setOnCheckedChangeListener(this);

        if (currentSortOrder == "added") {
            added.setChecked(true);
        }

        if (currentSortOrder == "title") {
            title.setChecked(true);
        }

        if (currentSortOrder == "author") {
            author.setChecked(true);
        }


        return view;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio_title) {
            // Toast.makeText(getActivity(), "TITLE", Toast.LENGTH_SHORT).show();
            onSortingOptionSelectedListener.onTitleSelected();
        }

        if (checkedId == R.id.radio_author) {
            // Toast.makeText(getActivity(), "AUTHOR", Toast.LENGTH_SHORT).show();
            onSortingOptionSelectedListener.onAuthorSelected();
        }

        if (checkedId == R.id.radio_added) {
            // Toast.makeText(getActivity(), "ADDED", Toast.LENGTH_SHORT).show();
            onSortingOptionSelectedListener.onAddedSelected();
        }
    }
}
