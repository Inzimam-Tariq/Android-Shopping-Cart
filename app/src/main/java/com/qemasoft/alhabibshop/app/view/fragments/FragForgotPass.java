package com.qemasoft.alhabibshop.app.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.qemasoft.alhabibshop.app.R;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragForgotPass extends MyBaseFragment {

    private Button passResetBtn;
    private EditText email;

    public FragForgotPass() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_forgot_pass, container, false);
        initViews(view);
        initUtils();

        passResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailVal = email.getText().toString().trim();
                if (emailVal.length() > 0) {

                } else {
                    utils.showErrorDialog("Email Required!");
                }
            }
        });

        return view;
    }

    private void initViews(View view) {
        passResetBtn = view.findViewById(R.id.passResetBtn);
        email = view.findViewById(R.id.emailET);
    }

}