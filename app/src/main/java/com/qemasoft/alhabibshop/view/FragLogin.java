package com.qemasoft.alhabibshop.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qemasoft.alhabibshop.AppConstants;
import com.qemasoft.alhabibshop.Preferences;
import com.qemasoft.alhabibshop.Utils;

import org.json.JSONObject;

import hostflippa.com.opencart_android.R;

import static com.qemasoft.alhabibshop.AppConstants.CUSTOMER_KEY;
import static com.qemasoft.alhabibshop.AppConstants.LOGIN_KEY;
import static com.qemasoft.alhabibshop.AppConstants.SET_CUSTOMER_ID;
import static com.qemasoft.alhabibshop.AppConstants.getApiCallUrl;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragLogin extends Fragment {

    private Context context;
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
        this.context = getActivity();
        this.utils = new Utils(getActivity());

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
                } else {
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
                changeFragment(104);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailVal = emailET.getText().toString().trim();
                String passVal = passET.getText().toString().trim();

                if (emailVal.length() > 0 && passVal.length() > 0) {
                    Log.e("InsideLoginClicked = ", "Inside if");
                    if (utils.isNetworkConnected()) {
                        Log.e("InsideLoginClicked = ", "isNetwork");
                        AppConstants.setMidFixApi("login");
                        Log.e("LoginUrl = ", getApiCallUrl());
                        AndroidNetworking.post(getApiCallUrl())
                                .addBodyParameter("email", emailVal)
                                .addBodyParameter("password", passVal)
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        boolean success = response.optBoolean("success");
                                        if (success) {
                                            JSONObject object = response.optJSONObject("userdata");
                                            Log.e("CustomerId",object.optString("customer_id"));
                                            SET_CUSTOMER_ID(CUSTOMER_KEY,object.optString("customer_id"));
                                            Preferences.setSharedPreferenceBoolean(SplashActivity
                                                    .getAppContext(), LOGIN_KEY, true);
                                            getActivity().recreate();
                                        } else {
                                            utils.showErrorDialog("The Email/Password you're Trying is Incorrect");
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        anError.printStackTrace();
                                        utils.showErrorDialog("Error Getting Data From Server");
                                        Toast.makeText(context, "ErrorGettingDataFromServer", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        utils.showInternetErrorDialog();
                    }
                }else {
                    // set empty field error message
                }
            }
        });


        return view;
    }

    private void changeFragment(int frag) {
        ((MainActivity) getActivity()).changeFragment(frag);

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
