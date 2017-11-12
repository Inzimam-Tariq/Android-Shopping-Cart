package com.qemasoft.alhabibshop.app.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.view.activities.MainActivity;


/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragContactUs extends Fragment {

    private Button contactUsBtn;
    private Utils utils;

    public FragContactUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_contact_us, container, false);
        this.utils = new Utils(getContext());
        initViews(view);


        contactUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.showToast("You Query Submitted Successfully\nWe will contact you back soon");
                ((MainActivity)getActivity()).changeFragment(0);
            }
        });

        return view;
    }

    private void initViews(View view) {
        contactUsBtn = view.findViewById(R.id.submitContactBtn);
    }

}