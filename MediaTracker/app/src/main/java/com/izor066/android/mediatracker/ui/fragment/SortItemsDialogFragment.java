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
import com.izor066.android.mediatracker.ui.adapter.ItemAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortItemsDialogFragment extends DialogFragment implements RadioGroup.OnCheckedChangeListener {

    private RadioButton title;
    private RadioButton author;
    private RadioButton added;
    private RadioGroup sortItems;
    private OnSortingOptionSelectedListener onSortingOptionSelectedListener;
    private ItemAdapter.SortCriteria currentSortCriteria = ItemAdapter.SortCriteria.ADDED;
    private boolean isAscending = true;
    private static final String EXTRA_SORT_CRITERIA = "com.izor066.android.mediatracker.EXTRA_SORT_CRITERIA";

    public static SortItemsDialogFragment createWith(ItemAdapter.SortCriteria currentSortCriteria) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_SORT_CRITERIA, currentSortCriteria);
        SortItemsDialogFragment fragment = new SortItemsDialogFragment();
        fragment.setArguments(args);
        return fragment;
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

        currentSortCriteria = (ItemAdapter.SortCriteria) getArguments().getSerializable(EXTRA_SORT_CRITERIA);

        sortItems = (RadioGroup) view.findViewById(R.id.rg_sort);
        sortItems.setOnCheckedChangeListener(this);


        switch (currentSortCriteria) {
            case TITLE:
                title.setChecked(true);
                break;
            case AUTHOR:
                author.setChecked(true);
                break;
            case ADDED:
                added.setChecked(true);
                break;
            default:
                throw new IllegalArgumentException("Cannot sort by: " + currentSortCriteria);
        }


        return view;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.radio_title) {
            onSortingOptionSelectedListener.onTitleSelected();

        }

        if (checkedId == R.id.radio_author) {
            onSortingOptionSelectedListener.onAuthorSelected();

        }

        if (checkedId == R.id.radio_added) {
            onSortingOptionSelectedListener.onAddedSelected();

        }
    }
}
