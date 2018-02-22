package com.qemasoft.alhabibshop.app.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.REGISTER_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.getApiCallUrl;

/**
 * Created by Inzimam Tariq on 24-Oct-17.
 */

public class FragRegister extends MyBaseFragment {
    
    private RadioGroup rgNewsletter;
    private RadioButton rbYes, rbNo;
    private EditText fName, lName, email, contact, pass, confirmPass;
    private CheckBox termsCB;
    private Button registerBtn;
    private TextView titleTV, privacyPolicyTV, clickLoginTV;
    private TextInputLayout inputLayoutPassword, confirmPassLayout;
//    private boolean asGuest;
    
    public FragRegister() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_register, container, false);
        initUtils();
        initViews(view);
        applyAccent();
//        guestCheck();
        
        inputLayoutPassword.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/DroidKufi-Regular.ttf"));
        confirmPassLayout.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/DroidKufi-Regular.ttf"));
        rgNewsletter.check(R.id.rbNo);
        privacyPolicyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                utils.switchFragment(new FragShowText());
            }
        });
        clickLoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                utils.switchFragment(new FragLogin());
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String fNameVal = fName.getText().toString().trim();
                String lNameVal = lName.getText().toString().trim();
                String emailVal = email.getText().toString().trim();
                String contactVal = contact.getText().toString().trim();
                String passVal = pass.getText().toString().trim();
                String rePassVal = confirmPass.getText().toString().trim();
                int isNewsLetterSubscribed = 0;
                if (rbYes.isChecked()) {
                    isNewsLetterSubscribed = 1;
                }
//                utils.showToast("Radio Value " + isNewsLetterSubscribed);
                
                
                if (fNameVal.isEmpty()) utils.setError(fName);
                if (lNameVal.isEmpty()) utils.setError(lName);
                if (emailVal.isEmpty()) utils.setError(email);
                if (contactVal.isEmpty()) utils.setError(contact);
                if (passVal.isEmpty()) utils.setError(pass);
                if (rePassVal.isEmpty()) utils.setError(confirmPass);
                boolean hasReadPrivacyPolicy = privacyPolicyCheck();
                if (!passVal.equals(rePassVal)) {
                    utils.showAlert(R.string.information_text, R.string.pass_mis_match,
                            false,
                            R.string.ok, null,
                            R.string.cancel_text, null);
                    return;
                }
                if (!hasReadPrivacyPolicy) {
                    utils.showAlert(R.string.information_text, R.string.read_privacy_policy,
                            false,
                            R.string.ok, null,
                            R.string.cancel_text, null);
                    return;
                }
                utils.printLog("InsideLoginClicked = ", "Inside if");
                if (utils.isNetworkConnected()) {
                    utils.printLog("InsideLoginClicked = ", "isNetwork");
                    AppConstants.setMidFixApi("register");
                    utils.printLog("RegisterUrl = ", getApiCallUrl());
                    Map<String, String> map = new HashMap<>();
                    map.put("firstname", fNameVal);
                    map.put("lastname", lNameVal);
                    map.put("email", emailVal);
                    map.put("telephone", contactVal);
                    map.put("password", passVal);
                    map.put("newsletter", String.valueOf(isNewsLetterSubscribed));
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REGISTER_REQUEST_CODE);
                    
                }
            }
        });
        
        return view;
    }
    
    
    private void applyAccent() {
        utils.applyAccentColor(privacyPolicyTV);
        utils.applyAccentColor(clickLoginTV);
    }
    
    
    private boolean privacyPolicyCheck() {
        
        return termsCB.isChecked();
    }
    
    private void initViews(View view) {
        
        inputLayoutPassword = view.findViewById(R.id.input_layout_password);
        confirmPassLayout = view.findViewById(R.id.confirm_password_layout);
        
        titleTV = view.findViewById(R.id.frag_title_tv);
        rgNewsletter = view.findViewById(R.id.newsletterRadioGroup);
        privacyPolicyTV = view.findViewById(R.id.privacy_policy_tv);
        clickLoginTV = view.findViewById(R.id.login_tv_in_register);
        fName = view.findViewById(R.id.f_name_et);
        lName = view.findViewById(R.id.l_name_et);
        email = view.findViewById(R.id.email_et);
        contact = view.findViewById(R.id.phone_et);
        pass = view.findViewById(R.id.password_et);
        confirmPass = view.findViewById(R.id.confirm_password_et);
        rbYes = view.findViewById(R.id.rbYes);
        rbNo = view.findViewById(R.id.rbNo);
        termsCB = view.findViewById(R.id.terms_cb);
        registerBtn = view.findViewById(R.id.register_btn);
        
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
                    final String msg = response.optString("message");
                    if (!msg.isEmpty()) {
                        
                        utils.showAlert(R.string.information_text, msg,
                                false,
                                R.string.continue_text, new Dashboard(),
                                R.string.login_text, new FragLogin());
                    }
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
