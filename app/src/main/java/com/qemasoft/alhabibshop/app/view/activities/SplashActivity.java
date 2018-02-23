package com.qemasoft.alhabibshop.app.view.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.qemasoft.alhabibshop.app.AppConstants.CURRENCY_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.CURRENCY_SYMBOL_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.GET_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.KEY_FOR_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.LANGUAGE_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.LOGO_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.LOGO_TYPE;
import static com.qemasoft.alhabibshop.app.AppConstants.SECRET_KEY_FILE;
import static com.qemasoft.alhabibshop.app.AppConstants.SECRET_KEY_URL;
import static com.qemasoft.alhabibshop.app.AppConstants.SET_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.SPLASH_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.THEME_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;
import static com.qemasoft.alhabibshop.app.AppConstants.setHomeExtra;

/**
 * Created by Inzimam Tariq on 18/10/2017.
 */

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    
    private Context context;
    private Utils utils;
    private int clicks = 0;
    private ProgressBar progressBar;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.utils = new Utils(this);
        context = getApplicationContext();
        utils.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        try {
            progressBar = findViewById(R.id.progress_bar);
            LinearLayout splash = findViewById(R.id.splash_layout);
            splash.setOnClickListener(this);
            
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
                                    utils.printLog("StoredKey", "Key = " + keyVal);
                                    if (keyVal.isEmpty() || keyVal.length() < 1) {
                                        utils.printLog("StoringKey", "Success");
                                        SET_KEY(KEY_FOR_KEY, secretKey);
                                        utils.printLog("KeyStored", "Success");
                                    }
                                    Bundle bundle = new Bundle();
                                    bundle.putBoolean("hasParameters", false);
                                    Intent intent = new Intent(SplashActivity.this, FetchData.class);
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, SPLASH_REQUEST_CODE);
                                    
                                } else {
                                    utils.printLog("Splash", "Success False");
                                    utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                                            false,
                                            R.string.ok, null,
                                            R.string.cancel_text, null);
                                }
                            }
                            
                            @Override
                            public void onError(ANError anError) {
                                
                                anError.printStackTrace();
                                utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                                        false,
                                        R.string.ok, null,
                                        R.string.cancel_text, null);
                                utils.showToast("ErrorGettingDataFromServer");
                            }
                        });
            } else {
                utils.showAlertDialogTurnWifiOn();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                        JSONObject settingObject = homeObject.optJSONObject("setting");
                        int width = Utils.getScreenWidth(appContext);
                        
                        String themeCode = settingObject.optString("skin");
                        Preferences.setSharedPreferenceString(
                                appContext, THEME_CODE, themeCode);
                        
                        String language = settingObject.optString("language");
                        String currency = settingObject.optString("currency");
                        String logoType = settingObject.optString("feed_rest_api_logo_type");
                        Preferences.setSharedPreferenceString(appContext,
                                LOGO_TYPE, logoType);
                        
                        String logo;
                        if (logoType.equals("image")) {
                            
                            if (width <= 480) {
                                logo = settingObject.optString("logo_small");
                            } else {
                                logo = settingObject.optString("logo");
                            }
                            
                        } else {
                            logo = settingObject.optString("site_name");
                        }
                        Preferences.setSharedPreferenceString(appContext,
                                LOGO_KEY, logo);
                        
                        String lang;
                        if (language != null && !language.isEmpty()) {
                            if (language.contains("-")) {
                                String langArray[] = language.split("-");
                                lang = langArray[0];
                                utils.printLog("Splash", "Language = " + lang);
                            } else lang = language;
                            Preferences.setSharedPreferenceString(appContext,
                                    LANGUAGE_KEY, lang);
                        }
                        Preferences.setSharedPreferenceString(appContext,
                                CURRENCY_KEY, currency);
                        String symbol = settingObject.optString("symbol");
                        
                        Preferences.setSharedPreferenceString(appContext,
                                CURRENCY_SYMBOL_KEY, symbol);
                        utils.printLog("Symbol", "Symbol = "
                                + Preferences.getSharedPreferenceString(appContext
                                , CURRENCY_SYMBOL_KEY, ""));
                        
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                
                                Intent intent = new Intent(context, MainActivity.class);
//                                intent.putExtra(MainActivity.KEY_EXTRA, response.toString());
                                startActivity(intent);
                                setHomeExtra(response.toString());
                                finish();
                            }
                        }, 700);
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
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                        false,
                        R.string.ok, null,
                        R.string.cancel_text, null);
                utils.printLog("RequestCanceled", "Canceled");
            }
        }
    }
    
    @Override
    public void onClick(View v) {
        clicks++;
        if (clicks % 2 == 0) {
            recreate();
        }
    }
    
    
}
