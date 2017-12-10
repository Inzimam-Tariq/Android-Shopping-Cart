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
        utils.log("Inside FetchData", "Has Extra = " + hasParameters);

        if (hasParameters) {
            doParametrisedRequest();
        } else {
            doSimpleRequest();
        }
    }

    private void doParametrisedRequest() {
        final ANRequest.PostRequestBuilder request = AndroidNetworking.post(getApiCallUrl());
        utils.log("Url = ", getApiCallUrl());
        bundle = getIntent().getExtras();

        if (bundle != null) {
            Map<String, String> parameterMap = (Map<String, String>) bundle.getSerializable("parameters");

            request.addBodyParameter(parameterMap);
            request.setPriority(Priority.HIGH);
            request.build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    utils.log("ResponseFetchData = ", response.toString());
                    boolean success = response.optBoolean("success");
                    if (success) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", response.toString());
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                        utils.log("doParametrizedRequest", "If Success");
                        utils.log("doParametrizedRequest", response.toString());
                    } else {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", response.toString());
                        setResult(AppConstants.FORCED_CANCEL, returnIntent);
                        finish();
                        utils.log("doParametrizedRequest", "If Success False");
                        utils.log("ForceCanceled", response.toString());
                    }
                }

                @Override
                public void onError(ANError anError) {
                    utils.log("doParametrizedRequest", "If anError");
                    anError.printStackTrace();
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, returnIntent);
                    finish();
                }
            });
        }
    }

    private void doSimpleRequest() {
        utils.log("Url = ", getApiCallUrl());
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
                            utils.log("doSimpleRequest", "If Success");
                            utils.log("doSimpleRequest", response.toString());
                        } else {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", response.toString());
                            setResult(AppConstants.FORCED_CANCEL, returnIntent);
                            finish();
                            utils.log("doSimpleRequest", "Success False");
                            utils.log("doSimpleRequest", response.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        utils.log("doSimpleRequest", "If anError");
                        anError.printStackTrace();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                    }
                });
    }
}
