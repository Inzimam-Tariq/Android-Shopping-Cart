package com.qemasoft.alhabibshop.app.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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

import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_CONTACT;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_EMAIL;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_FIRST_NAME;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_LAST_NAME;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.IS_LOGIN;
import static com.qemasoft.alhabibshop.app.AppConstants.LOGIN_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.UNIQUE_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragLogin extends MyBaseFragment {
    
    private EditText emailET, passET;
    private Button loginBtn;
    private TextView registerTV, forgotPassTV;
    
    private TextInputLayout inputLayoutPassword;
    
    public FragLogin() {
        // Required empty public constructor
    }
    
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_login, container, false);
        initViews(view);
        initUtils();
        applyAccent();
        
        inputLayoutPassword.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/DroidKufi-Regular.ttf"));
        
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.printLog("RegisterClicked");
                utils.switchFragment(new FragRegister());
            }
        });
        forgotPassTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.printLog("ForgetClicked");
                utils.switchFragment(new FragForgotPass());
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String emailVal = emailET.getText().toString().trim();
                String passVal = passET.getText().toString().trim();
                AppConstants.setMidFixApi("login");
                if (emailVal.isEmpty() || emailVal.length() < 1) {
                    utils.setError(emailET);
                } else if (passVal.isEmpty() || passVal.length() < 1) {
                    utils.setError(passET);
                } else {
                    utils.printLog("LoginClicked Email = " + emailVal
                            + "\tPassword = " + passVal);
                    Map<String, String> map = new HashMap<>();
                    map.put("email", emailVal);
                    map.put("password", passVal);
                    map.put("session_id", Preferences.getSharedPreferenceString(appContext
                            , UNIQUE_ID_KEY, DEFAULT_STRING_VAL));
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
    
    private void applyAccent() {
        utils.applyAccentColor(registerTV);
        utils.applyAccentColor(forgotPassTV);
    }
    
    private void initViews(View view) {
        
        inputLayoutPassword = view.findViewById(R.id.input_layout_password);
        emailET = view.findViewById(R.id.email_et);
        passET = view.findViewById(R.id.password_et);
        loginBtn = view.findViewById(R.id.login_btn);
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
                    
                    JSONObject object = response.optJSONObject("data");
                    String customerId = object.optString("customer_id");
                    String customerEmail = object.optString("email");
                    String fName = object.optString("firstname");
                    String lName = object.optString("lastname");
                    String contact = object.optString("telephone");
                    
                    Preferences.setSharedPreferenceString(appContext, CUSTOMER_EMAIL, customerEmail);
                    Preferences.setSharedPreferenceString(appContext, CUSTOMER_ID_KEY, customerId);
                    Preferences.setSharedPreferenceString(appContext, CUSTOMER_FIRST_NAME, fName);
                    Preferences.setSharedPreferenceString(appContext, CUSTOMER_LAST_NAME, lName);
                    Preferences.setSharedPreferenceString(appContext, CUSTOMER_CONTACT, contact);
                    Preferences.setSharedPreferenceBoolean(appContext, IS_LOGIN, true);
                    
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((MainActivity) context).recreate();
                        }
                    }, 100);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == AppConstants.FORCE_CANCELED) {
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    String msg = response.optString("message");
                    if (!msg.isEmpty()) {
                        utils.showAlert(R.string.information_text, msg,
                                false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                        false,
                        R.string.ok, null,
                        R.string.cancel_text, null);
            }
            
        }
    }
}
