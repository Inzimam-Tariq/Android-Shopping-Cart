package com.qemasoft.alhabibshop.app.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.view.fragments.FragSlider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.qemasoft.alhabibshop.app.AppConstants.GET_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.KEY_FOR_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.SECRET_KEY_FILE;
import static com.qemasoft.alhabibshop.app.AppConstants.SECRET_KEY_URL;
import static com.qemasoft.alhabibshop.app.AppConstants.SET_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.SPLASH_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.setHomeExtra;

public class SplashActivity extends AppCompatActivity {

    private static Context context;
    private Utils utils;

    public static Context getAppContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = getApplicationContext();
        this.utils = new Utils(this);
        AndroidNetworking.initialize(context);

        if (utils.isNetworkConnected()) {
            AndroidNetworking.post(SECRET_KEY_URL)
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            boolean success = response.optBoolean("success");
                            if (success) {
                                AppConstants.setMidFixApi("home");
                                String secretKey = response.optString(SECRET_KEY_FILE);
                                String keyVal = GET_KEY(context, KEY_FOR_KEY);
                                Log.e("StoredKey", "Key = " + keyVal);
                                if (keyVal.isEmpty() || keyVal.length() < 1) {
                                    Log.e("StoringKey", "Success");
                                    SET_KEY(KEY_FOR_KEY, secretKey);
                                    Log.e("KeyStored", "Success");
                                }
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("hasParameters", false);
                                Intent intent = new Intent(SplashActivity.this, FetchData.class);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, SPLASH_REQUEST_CODE);
                            } else {
                                utils.showAlertDialog("Invalid Request!", "No Relevant Record Found");
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            anError.printStackTrace();
                            utils.showErrorDialog("Error Getting Data From Server");
                            Toast.makeText(context, "ErrorGettingDataFromServer", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            utils.showAlertDialogTurnWifiOn();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SPLASH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
                    boolean success = response.optBoolean("success");
                    if (success) {
                        JSONObject homeObject = response.getJSONObject("home");
                        final JSONArray slideshow = homeObject.optJSONArray("slideshow");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra(MainActivity.KEY_EXTRA, "" + response);
                                intent.putExtra(FragSlider.SLIDER_EXTRA, "" + slideshow);
                                startActivity(intent);
                                setHomeExtra(response.toString());
                            }
                        }, 700);
                    } else {
                        utils.showErrorDialog("Server Response is False!");
                        Log.e("SuccessFalse", "Within getCategories");
                        utils.hideProgress();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                utils.showAlertDialog("Invalid Request!", "Either the request is invalid or no relevant record found");
            }
        }
    }
}
