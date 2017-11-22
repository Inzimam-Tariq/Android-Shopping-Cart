package com.qemasoft.alhabibshop.app.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;

import org.json.JSONObject;

import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.getApiCallUrl;

public class FetchData extends AppCompatActivity {


    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fetch_data);
        this.setFinishOnTouchOutside(false);

        boolean hasParameters = getIntent().getBooleanExtra("hasParameters", false);
        Log.e("Inside FetchData", "Has Extra = " + hasParameters);

        if (hasParameters) {
            doParametrisedRequest();
        } else {
            doSimpleRequest();
        }
    }

    private void doParametrisedRequest() {
        final ANRequest.PostRequestBuilder request = AndroidNetworking.post(getApiCallUrl());
        Log.e("Url = ", getApiCallUrl());
        bundle = getIntent().getExtras();

        if (bundle != null) {
            Map<String, String> parameterMap = (Map<String, String>) bundle.getSerializable("parameters");

            request.addBodyParameter(parameterMap);
            request.setPriority(Priority.HIGH);
            request.build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("ResponseFetchData = ", response.toString());
                    boolean success = response.optBoolean("success");
                    if (success) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", response.toString());
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                        Log.e("doParametrizedRequest", "If Success");
                        Log.e("doParametrizedRequest", response.toString());
                    } else {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", response.toString());
                        setResult(AppConstants.FORCED_CANCEL, returnIntent);
                        finish();
                        Log.e("doParametrizedRequest", "If Success False");
                        Log.e("ForceCanceled", response.toString());
                    }
                }

                @Override
                public void onError(ANError anError) {
                    Log.e("doParametrizedRequest", "If anError");
                    anError.printStackTrace();
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }
            });
        }
    }

    private void doSimpleRequest() {
        Log.e("Url = ", getApiCallUrl());
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
                            Log.e("doSimpleRequest", "If Success");
                            Log.e("doSimpleRequest", response.toString());
                        } else {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", response.toString());
                            setResult(AppConstants.FORCED_CANCEL, returnIntent);
                            finish();
                            Log.e("doSimpleRequest", "Success False");
                            Log.e("doSimpleRequest", response.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("doSimpleRequest", "If anError");
                        anError.printStackTrace();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                    }
                });
    }
}
