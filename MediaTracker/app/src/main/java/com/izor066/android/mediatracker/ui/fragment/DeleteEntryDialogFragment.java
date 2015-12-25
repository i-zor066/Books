package com.izor066.android.mediatracker.ui.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.izor066.android.mediatracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteEntryDialogFragment extends DialogFragment implements Button.OnClickListener {


    private Button delete;
    private Button cancel;
    private OnDeleteConfirmationListener onDeleteConfirmationListener;

    public static interface OnDeleteConfirmationListener {
        public void onDeleteSelected();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            onDeleteConfirmationListener = (OnDeleteConfirmationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDeleteConfirmationListener");
        }
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Delete");
        return dialog;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_delete_entry_dialog, container, false);
        delete = (Button) view.findViewById(R.id.bt_delete_entry);
        cancel = (Button) view.findViewById(R.id.bt_delete_entry_cancel);

        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {

        if (v == v.findViewById(R.id.bt_delete_entry)) {
            onDeleteConfirmationListener.onDeleteSelected();
            dismiss();
        } else {
            dismiss();
        }

    }
}
