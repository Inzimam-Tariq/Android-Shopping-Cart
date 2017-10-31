package com.qemasoft.alhabibshop.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qemasoft.alhabibshop.Utils;

import hostflippa.com.opencart_android.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragLogin extends Fragment {

    private Utils utils;
    private EditText emailET, passET;
    private Button loginBtn;
    private TextView visibilityIconVisible, registerTV, forgotPassTV;
    private boolean isVisible = false;

    public FragLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_login, container, false);
        initViews(view);

        visibilityIconVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
//                    passET.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passET.setTransformationMethod(new PasswordTransformationMethod());
                    // params in below code are as (left,top,right,bottom)
                    passET.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    passET.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                            R.drawable.ic_visibility_black, 0);
                    isVisible = false;
                }else {
//                    passET.setInputType(123);
                    passET.setTransformationMethod(null);
                    isVisible = true;
                    passET.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    passET.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                            R.drawable.ic_visibility_off_black, 0);
                }
                passET.setSelection(passET.getText().length());
            }
        });

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(102);
            }
        });
        forgotPassTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(105);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(0);
            }
        });


        return view;
    }

    private void changeFragment(int frag) {
        ((MainActivity)getActivity()).changeFragment(frag);

        // OR This

//        Fragment fragRegister = new  FragRegister();
//        //           FragRegister fragRegister = new  FragRegister();
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.addToBackStack(null);
//        transaction.replace(R.id.flFragments, fragRegister).commit();
    }

    private void initViews(View view) {

        emailET = view.findViewById(R.id.emailET);
        passET = view.findViewById(R.id.passwordET);
        loginBtn = view.findViewById(R.id.loginBtn);
        visibilityIconVisible = view.findViewById(R.id.visibility_icon);
        registerTV = view.findViewById(R.id.reg_tv_in_login);
        forgotPassTV = view.findViewById(R.id.forgot_pass_tv);
    }



}
