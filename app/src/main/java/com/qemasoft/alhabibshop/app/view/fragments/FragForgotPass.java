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
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.FORGOT_PASS_REQUEST_CODE;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragForgotPass extends MyBaseFragment {
    
    private Button passResetBtn;
    private EditText email;
    private TextInputLayout emailLayout;
    
    
    public FragForgotPass() {
        // Required empty public constructor
    }
    
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_forgot_pass, container, false);
        initViews(view);
        initUtils();
        
        utils.printLog("InsideForgetPassFragment");
        emailLayout.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/DroidKufi-Regular.ttf"));
        
        passResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.printLog("ForgetClicked");
                String emailVal = email.getText().toString().trim();
                if (emailVal.isEmpty() || emailVal.length() < 1) {
                    utils.setError(email);
                } else {
                    AppConstants.setMidFixApi("forgotten");
                    utils.printLog("Email= " + emailVal);
                    Map<String, String> map = new HashMap<>();
                    map.put("email", emailVal);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, FORGOT_PASS_REQUEST_CODE);
                }
            }
        });
        
        return view;
    }
    
    private void initViews(View view) {
        emailLayout = view.findViewById(R.id.input_layout_email);
        passResetBtn = view.findViewById(R.id.passResetBtn);
        email = view.findViewById(R.id.email_et);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FORGOT_PASS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
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