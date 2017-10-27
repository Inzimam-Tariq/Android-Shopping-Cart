package com.qemasoft.alhabibshop.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hostflippa.com.opencart_android.R;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragContactUs extends Fragment {


    public FragContactUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_contact_us, container, false);
    }

}