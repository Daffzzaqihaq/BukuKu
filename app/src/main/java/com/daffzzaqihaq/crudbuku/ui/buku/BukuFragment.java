package com.daffzzaqihaq.crudbuku.ui.buku;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daffzzaqihaq.crudbuku.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BukuFragment extends Fragment {


    public BukuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buku, container, false);
    }

}
