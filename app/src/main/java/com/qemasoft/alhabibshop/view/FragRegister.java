package com.qemasoft.alhabibshop.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qemasoft.alhabibshop.Utils;

import hostflippa.com.opencart_android.R;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragRegister extends Fragment {

    private RadioGroup rgNewsletter;
    private RadioButton rbYes, rbNo;
    private Button registerBtn;
    private TextView privacyPolicyTV, clickRegisterTV;

    private Utils utils;

    public FragRegister() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_register, container, false);
        utils = new Utils(getContext());
        initViews(view);


        rgNewsletter.check(R.id.rbYes);
        privacyPolicyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(1);
//                utils.showAlertDialog("Privacy Policy", "Privacy Policy text here");
            }
        });
        clickRegisterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(101);
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(0);
            }
        });

        return view;
    }

    private void initViews(View view) {
        rgNewsletter = view.findViewById(R.id.newsletterRadioGroup);
        privacyPolicyTV = view.findViewById(R.id.privacy_policy_tv);
        clickRegisterTV = view.findViewById(R.id.login_tv_in_register);
        registerBtn = view.findViewById(R.id.registerBtn);
    }

}
