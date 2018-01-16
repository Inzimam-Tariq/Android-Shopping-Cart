package com.qemasoft.alhabibshop.app.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qemasoft.alhabibshop.app.R;


/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragContactUs extends MyBaseFragment {

    private Button contactUsBtn;


    public FragContactUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_contact_us, container, false);
        initUtils();
        initViews(view);


        contactUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.showAlertDialog("Message","You Query Submitted Successfully\nWe will contact you back soon");
                utils.switchFragment(new MainFrag());
            }
        });

        return view;
    }

    private void initViews(View view) {
        contactUsBtn = view.findViewById(R.id.submit_contact_btn);
    }

}