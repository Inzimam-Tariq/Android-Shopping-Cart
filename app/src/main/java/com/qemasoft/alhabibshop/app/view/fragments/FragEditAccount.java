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

import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_CONTACT;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_EMAIL;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_FIRST_NAME;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_LAST_NAME;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.EDIT_ACCOUNT_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragEditAccount extends MyBaseFragment {
    
    Button editAccountBtn;
    private EditText fName, lName, email, contact;
    
    public FragEditAccount() {
        // Required empty public constructor
    }
    
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_edit_account, container, false);
        initUtils();
        initViews(view);
        
        fName.setText(Preferences.getSharedPreferenceString(
                appContext,
                CUSTOMER_FIRST_NAME,
                DEFAULT_STRING_VAL));
        lName.setText(Preferences.getSharedPreferenceString(
                appContext,
                CUSTOMER_LAST_NAME,
                DEFAULT_STRING_VAL));
        email.setText(Preferences.getSharedPreferenceString(
                appContext,
                CUSTOMER_EMAIL,
                DEFAULT_STRING_VAL));
        contact.setText(Preferences.getSharedPreferenceString(
                appContext,
                CUSTOMER_CONTACT,
                DEFAULT_STRING_VAL));
        
        editAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String fNameVal = fName.getText().toString().trim();
                String lNameVal = lName.getText().toString().trim();
                String emailVal = email.getText().toString().trim();
                String contactVal = contact.getText().toString().trim();
                if (contactVal.isEmpty())
                    utils.setError(contact);
                if (emailVal.isEmpty())
                    utils.setError(email);
                if (lNameVal.isEmpty())
                    utils.setError(lName);
                if (fNameVal.isEmpty()) {
                    utils.setError(fName);
                } else {
                    AppConstants.setMidFixApi("editCustomer");
                    
                    Map<String, String> map = new HashMap<>();
                    map.put("email", emailVal);
                    map.put("firstname", fNameVal);
                    map.put("lastname", lNameVal);
                    map.put("telephone", contactVal);
                    map.put("customer_id", Preferences.getSharedPreferenceString(appContext,
                            CUSTOMER_ID_KEY, DEFAULT_STRING_VAL));
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, EDIT_ACCOUNT_REQUEST_CODE);
                }
            }
        });
        
        return view;
    }
    
    private void initViews(View view) {
        editAccountBtn = view.findViewById(R.id.edit_account_btn);
        fName = view.findViewById(R.id.f_name_et);
        lName = view.findViewById(R.id.l_name_et);
        email = view.findViewById(R.id.email_et);
        contact = view.findViewById(R.id.phone_et);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        
        if (requestCode == EDIT_ACCOUNT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    String fName = response.optString("firstname");
                    String lName = response.optString("lastname");
                    
                    Preferences.setSharedPreferenceString(appContext, CUSTOMER_FIRST_NAME, fName);
                    Preferences.setSharedPreferenceString(appContext, CUSTOMER_LAST_NAME, lName);
                    
                    String msg = response.optString("message");
                    if (!msg.isEmpty())
                        utils.showAlert(R.string.information_text, msg,
                                false,
                                R.string.ok, new Dashboard(),
                                R.string.cancel_text, null);
                } catch (JSONException e) {
//                    utils.showErrorDialog("Invalid JSON");
                    e.printStackTrace();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                        false,
                        R.string.ok, null,
                        R.string.cancel_text, null);
            }
        }
    }
    
    
}
