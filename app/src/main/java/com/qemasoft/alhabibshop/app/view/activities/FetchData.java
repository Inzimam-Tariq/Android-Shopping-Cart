package com.qemasoft.alhabibshop.app.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;

import org.json.JSONObject;

import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.getApiCallUrl;

public class FetchData extends AppCompatActivity {

    private Utils utils;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        this.setFinishOnTouchOutside(false);
        this.utils = new Utils(this);
        this.context = this;

        Bundle bundle = getIntent().getExtras();

        boolean hasParameters = bundle.getBoolean("hasParameters", false);
//        getBooleanExtra("hasParameters", false);
//         getExtras().getBoolean("hasParameters");

        Log.e("Inside FetchData", "Got Extra = " + hasParameters);

        if (hasParameters) {
            doParametrisedRequest();
        } else {
            doSimpleRequest();
        }
    }

    private void doParametrisedRequest() {
        final ANRequest.PostRequestBuilder request = AndroidNetworking.post(getApiCallUrl());
        Log.e("Url = ", getApiCallUrl());
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            Map<String, String> parameterMap = (Map<String, String>)
                    bundle.getSerializable("parameters");
            request.addBodyParameter(parameterMap);
            request.setPriority(Priority.HIGH);
            request.build().getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("ResponseBaseFrag = ", response.toString());
                    boolean success = response.optBoolean("success");
                    if (success) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", response.toString());
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    } else {
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                    }
                }

                @Override
                public void onError(ANError anError) {
                    utils.showErrorDialog(anError.getErrorBody());
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
                        } else {
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_CANCELED, returnIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_CANCELED, returnIntent);
                        finish();
                    }
                });
    }
}
