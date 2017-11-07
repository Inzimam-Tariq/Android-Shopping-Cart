package com.qemasoft.alhabibshop.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qemasoft.alhabibshop.AppConstants;
import com.qemasoft.alhabibshop.Utils;

import org.json.JSONObject;

import hostflippa.com.opencart_android.R;

import static com.qemasoft.alhabibshop.AppConstants.GET_KEY;
import static com.qemasoft.alhabibshop.AppConstants.KEY_FOR_KEY;
import static com.qemasoft.alhabibshop.AppConstants.SECRET_KEY_URL;
import static com.qemasoft.alhabibshop.AppConstants.SET_KEY;
import static com.qemasoft.alhabibshop.AppConstants.getApiCallUrl;

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
                                String secretKey = response.optString("secret_key");
                                String keyVal = GET_KEY(context, KEY_FOR_KEY);
                                Log.e("StoredKey", "Key = " + keyVal);
                                if (keyVal.isEmpty() || keyVal.length()<1){
                                    Log.e("StoringKey", "Success");
                                    SET_KEY(KEY_FOR_KEY,secretKey);
                                    Log.e("KeyStored", "Success");
                                    getCategoriesInfo();
                                }else {
                                    getCategoriesInfo();
                                }
                                Log.e("KeyResponse", "Key = " + secretKey);

                            } else {
                                utils.showErrorDialog("Error Getting Data From Server");
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

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            }
//        }, 3000);
    }

    private void getCategoriesInfo() {

        if (utils.isNetworkConnected()) {

            AppConstants.setMidFixApi("getMenuCategories");
            AndroidNetworking.post(getApiCallUrl())
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("LoadingData", "" + "Successful" + response);
                            boolean success = response.optBoolean("success");
                            if (success) {
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra(MainActivity.KEY_EXTRA, ""+response);
                                startActivity(intent);
//                            JSONArray categoriesInfoArray = response.optJSONArray("category_info");
//                            Log.e("Questions", categoriesInfoArray.toString());
//                            for (int i = 0; i < categoriesInfoArray.length(); i++) {
//
//                                try {
//                                    JSONObject obj = categoriesInfoArray.getJSONObject(i);
////                                    MyData data = new MyData(
////                                            obj.getString("question_id"),
////                                            obj.getString("question_text"));
//                                    Log.e("Categories" + " " + i,
//                                            "\nCategory id = " + obj.optString("category_id") +
//                                                    " Category name = " + obj.optString("name"));
////                                    dataList.add(data);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
                            } else {
                                utils.showErrorDialog("Error Getting Data From Server");
                                Log.e("SuccessFalse","Within getCategories");
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            anError.printStackTrace();
                            Log.e("Invalid request","Within onError getCategories");
                            Toast.makeText(context, "ErrorGettingDataFromServer", Toast.LENGTH_LONG).show();
                        }
                    });

        } else {
            utils.showInternetErrorDialog();
        }
    }
}
