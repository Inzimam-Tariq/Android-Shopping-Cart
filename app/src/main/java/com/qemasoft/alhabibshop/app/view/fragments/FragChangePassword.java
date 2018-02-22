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

import static com.qemasoft.alhabibshop.app.AppConstants.CHANGE_PASS_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_EMAIL;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.FORCE_CANCELED;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragChangePassword extends MyBaseFragment {
    
    private EditText currentPass, newPass, confirmPass;
    private Button changePassBtn;
    private TextInputLayout inputLayoutPassword, newPassLayout, confirmPassLayout;
    
    public FragChangePassword() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_change_pass, container, false);
        initViews(view);
        initUtils();
        
        inputLayoutPassword.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/DroidKufi-Regular.ttf"));
        newPassLayout.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/DroidKufi-Regular.ttf"));
        confirmPassLayout.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/DroidKufi-Regular.ttf"));
        
        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String currentPassVal = currentPass.getText().toString().trim();
                String newPassVal = newPass.getText().toString().trim();
                String confirmPassVal = confirmPass.getText().toString().trim();
                
                if (!newPassVal.equals(confirmPassVal)) {
                    utils.showAlert(R.string.information_text, R.string.pass_mis_match,
                            false,
                            R.string.ok, null,
                            R.string.cancel_text, null);
                    return;
                }
                
                if (currentPassVal.length() < 1) {
                    utils.setError(currentPass);
                } else if (newPassVal.length() < 1) {
                    utils.setError(newPass);
                } else if (confirmPassVal.length() < 1) {
                    utils.setError(confirmPass);
                } else {
                    
                    AppConstants.setMidFixApi("editPassword");
                    
                    Map<String, String> map = new HashMap<>();
                    map.put("email", Preferences.getSharedPreferenceString(appContext,
                            CUSTOMER_EMAIL, DEFAULT_STRING_VAL));
                    map.put("oldPassword", currentPassVal);
                    map.put("password", newPassVal);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, CHANGE_PASS_REQUEST_CODE);
                }
                
            }
        });
        
        return view;
    }
    
    
    private void initViews(View view) {
        
        inputLayoutPassword = view.findViewById(R.id.input_layout_password);
        newPassLayout = view.findViewById(R.id.new_pass_layout);
        confirmPassLayout = view.findViewById(R.id.confirm_password_layout);
        
        currentPass = view.findViewById(R.id.current_pass_et);
        newPass = view.findViewById(R.id.pass_et);
        confirmPass = view.findViewById(R.id.new_pass_et);
        changePassBtn = view.findViewById(R.id.change_pass_btn);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHANGE_PASS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    
                    String message = response.optString("message");
                    if (message.length() > 0) {
                        utils.showAlert(R.string.information_text, message,
                                false,
                                R.string.ok, new Dashboard(),
                                R.string.cancel_text, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (resultCode == FORCE_CANCELED) {
                try {
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
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    String message = response.optString("message");
                    if (message.length() > 0) {
                        utils.showAlert(R.string.information_text, message,
                                false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                    } else {
                        utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                                false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            
        }
    }
    
}
