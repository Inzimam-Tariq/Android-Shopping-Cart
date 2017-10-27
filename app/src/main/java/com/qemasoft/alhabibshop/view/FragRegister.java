package com.qemasoft.alhabibshop.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import hostflippa.com.opencart_android.R;
import com.qemasoft.alhabibshop.Utils;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragRegister extends Fragment {

    RadioGroup rgNewsletter;
    RadioButton rbYes, rbNo;
    TextView privacyPolicyTV;

    Utils utils;

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
//                String val =
                utils.showAlertDialog("Privacy Policy", "Privacy Policy text here");
            }
        });

        return view;
    }

    private void initViews(View view) {
        rgNewsletter = view.findViewById(R.id.newsletterRadioGroup);
        privacyPolicyTV = view.findViewById(R.id.privacy_policy_tv);
    }

}
