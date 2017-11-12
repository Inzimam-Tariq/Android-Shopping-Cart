package com.qemasoft.alhabibshop.app.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;
import com.qemasoft.alhabibshop.app.view.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.LOGIN_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.LOGIN_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.SET_CUSTOMER_ID;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragLogin extends MyBaseFragment {

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
        initUtils();

        visibilityIconVisible.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                R.drawable.ic_visibility_black, 0);

        visibilityIconVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
//                    passET.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passET.setTransformationMethod(new PasswordTransformationMethod());
                    // params in below code are as (left,top,right,bottom)
                    visibilityIconVisible.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                            R.drawable.ic_visibility_black, 0);
                    isVisible = false;
                } else {
//                    passET.setInputType(123);
                    passET.setTransformationMethod(null);
                    isVisible = true;
                    visibilityIconVisible.setCompoundDrawablesWithIntrinsicBounds(0, 0,
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
                changeFragment(202);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailVal = emailET.getText().toString().trim();
                String passVal = passET.getText().toString().trim();
                AppConstants.setMidFixApi("login");
                if (emailVal.length() > 0 && passVal.length() > 0) {
                    Map<String, String> map = new HashMap<>();
                    map.put("email", emailVal);
                    map.put("password", passVal);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));

                    JSONObject object = response.optJSONObject("userdata");
                    Log.e("CustomerId", object.optString("customer_id"));

                    SET_CUSTOMER_ID(CUSTOMER_KEY, object.optString("customer_id"));
                    Preferences.setSharedPreferenceBoolean(appContext, LOGIN_KEY, true);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getActivity().recreate();
                        }
                    }, 10);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
