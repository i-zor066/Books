package com.izor066.android.mediatracker.ui.fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.izor066.android.mediatracker.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class SortItemsDialogFragment extends Fragment {


    public SortItemsDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sort_items_dialog, container, false);
    }

}
