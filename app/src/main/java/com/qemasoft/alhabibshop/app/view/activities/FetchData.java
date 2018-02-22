package com.qemasoft.alhabibshop.app.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;

import org.json.JSONObject;

import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.getApiCallUrl;

public class FetchData extends AppCompatActivity {
    
    
    private Bundle bundle;
    private Utils utils;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.utils = new Utils(this);
//        utils.setTheme(this);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        this.setFinishOnTouchOutside(false);
        
        boolean hasParameters = getIntent().getBooleanExtra("hasParameters",
                false);
        utils.printLog("Inside FetchData", "Has Extra = " + hasParameters);
        
        if (hasParameters) {
            doParametrisedRequest();
        } else {
            doSimpleRequest();
        }
    }
    
    private void doParametrisedRequest() {
        final ANRequest.PostRequestBuilder request = AndroidNetworking.post(getApiCallUrl());
        utils.printLog("Url = ", getApiCallUrl());
        bundle = getIntent().getExtras();
        
        if (bundle != null) {
            Map<String, String> parameterMap = (Map<String, String>) bundle.getSerializable("parameters");
            
            request.addBodyParameter(parameterMap);
            request.setPriority(Priority.HIGH);
            request.build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    utils.printLog("ResponseFetchData = ", response.toString());
                    boolean success = response.optBoolean("success");
                    if (success) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", response.toString());
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                        utils.printLog("doParametrizedRequest", "If Success");
                        utils.printLog("doParametrizedRequest", response.toString());
                    } else {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", response.toString());
                        setResult(AppConstants.FORCE_CANCELED, returnIntent);
                        finish();
                        utils.printLog("doParametrizedRequest", "If Success False");
                        utils.printLog("ForceCanceled", response.toString());
                    }
                }
                
                @Override
                public void onError(ANError anError) {
                    utils.printLog("doParametrizedRequest", "If anError");
                    anError.printStackTrace();
                    utils.printLog("onError errorCode : " + anError.getErrorCode());
                    utils.printLog("onError errorBody : " + anError.getErrorBody());
                    utils.printLog("onError errorDetail : " + anError.getErrorDetail());
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }
            });
        }
    }
    
    private void doSimpleRequest() {
        utils.printLog("Url = ", getApiCallUrl());
        AndroidNetworking.post(getApiCallUrl())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean success = response.optBoolean("success");
                        if (success) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", response.toString());
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                            utils.printLog("doSimpleRequest", "If Success");
                            utils.printLog("doSimpleRequest", response.toString());
                        } else {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", response.toString());
                            setResult(AppConstants.FORCE_CANCELED, returnIntent);
                            finish();
                            utils.printLog("doSimpleRequest", "Success False");
                            utils.printLog("doSimpleRequest", response.toString());
                        }
                    }
                    
                    @Override
                    public void onError(ANError anError) {
                        utils.printLog("doSimpleRequest", "If anError");
                        anError.printStackTrace();
                        utils.printLog("onError errorCode : " + anError.getErrorCode());
                        utils.printLog("onError errorBody : " + anError.getErrorBody());
                        utils.printLog("onError errorDetail : " + anError.getErrorDetail());
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                    }
                });
    }
}
