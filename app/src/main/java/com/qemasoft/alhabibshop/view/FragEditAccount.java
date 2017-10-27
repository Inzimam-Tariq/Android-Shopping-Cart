package com.qemasoft.alhabibshop.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hostflippa.com.opencart_android.R;
import com.qemasoft.alhabibshop.Utils;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragEditAccount extends Fragment {

    Utils utils;
    Button editAccountBtn;

    public FragEditAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_edit_account, container, false);
        utils = new Utils(getContext());
        initViews(view);


        editAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.showAlertDialog("Information Saved!", "You Changed Account Information Successfully");
            }
        });

        return view;
    }

    private void initViews(View view) {
        editAccountBtn = view.findViewById(R.id.edit_account_btn);

    }

}
