package com.qemasoft.alhabibshop.app.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qemasoft.alhabibshop.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginMaterial extends Fragment {


    public LoginMaterial() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_material, container, false);
    }

}
