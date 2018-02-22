package com.qemasoft.alhabibshop.app.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.CONTACT_US_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_CONTACT;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_EMAIL;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_FIRST_NAME;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_LAST_NAME;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;


/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragContactUs extends MyBaseFragment {
    
    private Button contactUsBtn;
    private EditText nameET, emailET, contactET, enquiryET;
    
    
    public FragContactUs() {
        // Required empty public constructor
    }
    
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_contact_us, container, false);
        initUtils();
        initViews(view);
        
        nameET.setText(Preferences.getSharedPreferenceString(
                appContext,
                CUSTOMER_FIRST_NAME,
                DEFAULT_STRING_VAL)
                .concat(" ")
                .concat(Preferences.getSharedPreferenceString(
                        appContext,
                        CUSTOMER_LAST_NAME,
                        DEFAULT_STRING_VAL)));
        emailET.setText(Preferences.getSharedPreferenceString(
                appContext,
                CUSTOMER_EMAIL,
                DEFAULT_STRING_VAL));
        contactET.setText(Preferences.getSharedPreferenceString(
                appContext,
                CUSTOMER_CONTACT,
                DEFAULT_STRING_VAL));
        
        
        contactUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String nameVal = nameET.getText().toString().trim();
                String emailVal = emailET.getText().toString().trim();
                String contactVal = contactET.getText().toString().trim();
                String enquiryVal = enquiryET.getText().toString().trim();
                
                if (enquiryVal.isEmpty()) utils.setError(enquiryET);
                if (contactVal.isEmpty()) utils.setError(contactET);
                if (emailVal.isEmpty()) utils.setError(emailET);
                if (nameVal.isEmpty()) utils.setError(nameET);
                if (!nameVal.isEmpty() && !emailVal.isEmpty()
                        && !contactVal.isEmpty() && !enquiryVal.isEmpty()) {
                    utils.printLog("InsideLoginClicked = ", "Inside if");
                    
                    AppConstants.setMidFixApi("contact");
                    
                    Map<String, String> map = new HashMap<>();
                    map.put("email", emailVal);
                    map.put("name", Preferences.getSharedPreferenceString(
                            appContext,
                            CUSTOMER_FIRST_NAME,
                            DEFAULT_STRING_VAL)
                            .concat(" ")
                            .concat(Preferences.getSharedPreferenceString(
                                    appContext,
                                    CUSTOMER_LAST_NAME,
                                    DEFAULT_STRING_VAL)));
                    map.put("phone", contactVal);
                    map.put("enquiry", enquiryVal);
                    
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, CONTACT_US_REQUEST_CODE);
                    
                }
            }
        });
        
        return view;
    }
    
    private void initViews(View view) {
        contactUsBtn = view.findViewById(R.id.submit_contact_btn);
        nameET = view.findViewById(R.id.name_et);
        emailET = view.findViewById(R.id.email_et);
        contactET = view.findViewById(R.id.phone_et);
        enquiryET = view.findViewById(R.id.inquiry_et);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACT_US_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
                    utils.printLog("ContactUs", "Inside OK");
                    String msg = response.optString("message");
                    if (!msg.isEmpty()) {
                        utils.showAlert(R.string.information_text, msg,
                                false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                    }
                    utils.switchFragment(new MainFrag());
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == AppConstants.FORCE_CANCELED) {
                try {
                    utils.printLog("ContactUs", "Inside Force Cancel");
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    String error = response.optString("message");
                    if (!error.isEmpty()) {
                        utils.showAlert(R.string.information_text, error,
                                false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.printLog("ContactUs", "Inside Cancel");
                utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                        false,
                        R.string.ok, null,
                        R.string.cancel_text, null);
            }
        }
    }
    
}