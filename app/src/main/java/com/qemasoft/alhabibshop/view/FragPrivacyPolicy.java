package com.qemasoft.alhabibshop.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hostflippa.com.opencart_android.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragPrivacyPolicy extends Fragment {


    public FragPrivacyPolicy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_privacy_policy, container, false);
    }

}
