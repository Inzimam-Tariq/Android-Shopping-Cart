package com.qemasoft.alhabibshop.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qemasoft.alhabibshop.Utils;

import hostflippa.com.opencart_android.R;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragForgotPass extends Fragment {

    private Button passResetBtn;
    public FragForgotPass() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_forgot_pass, container, false);
        initViews(view);

        passResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils utils = new Utils(getContext());
                utils.showAlertDialog("Confirmation Message!",
                        "A Password Reset Link Has Been Sent To Your Email");
                ((MainActivity)getActivity()).changeFragment(0);
            }
        });

        return view;
    }

    private void initViews(View view) {
        passResetBtn = view.findViewById(R.id.passResetBtn);
    }

}