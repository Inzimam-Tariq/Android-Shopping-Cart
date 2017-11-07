package com.qemasoft.alhabibshop;

import android.content.Context;
import android.content.SharedPreferences;

import com.qemasoft.alhabibshop.view.SplashActivity;

/**
 * Created by Inzimam on 29-Oct-17.
 */

public class AppConstants {

    public static final String DEFAULT_STRING_VALUE = "";
    public static final Context appContext = SplashActivity.getAppContext();
    public static final String SECRET_KEY_FILE = "secretKey";
    public static final String KEY_FOR_KEY = "KEY_VALUE";
    public static final String BASE_URL = "http://www.opencartgulf.com/api/";
    public static final String SECRET_KEY_URL = BASE_URL + "getKey";
    public static final String LOGIN_KEY = "LOGIN_KEY";
    public static final String CUSTOMER_KEY = "CUSTOMER_KEY";
    private static String MID_FIX_API;

    public static String getApiCallUrl(){
        return BASE_URL + getMidFixApi() + "/" + GET_KEY(appContext, KEY_FOR_KEY);
    }

    public static String getMidFixApi() {
        return MID_FIX_API;
    }

    public static void setMidFixApi(String midFixApi) {
        MID_FIX_API = midFixApi;
    }

    public static String GET_KEY(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(SECRET_KEY_FILE, 0);

        return sharedPref.getString(key, "");
    }
    public static String GET_CUSTOMER_ID(String key) {
        SharedPreferences sharedPref = appContext.getSharedPreferences(SECRET_KEY_FILE, 0);

        return sharedPref.getString(key, "");
    }

    public static void SET_KEY(String secretKey, String secretKeyVal) {
        SharedPreferences settings = appContext.getSharedPreferences(SECRET_KEY_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(secretKey, secretKeyVal);
        editor.apply();
    }
    public static void SET_CUSTOMER_ID(String customerIdKey, String customerIdVal) {
        SharedPreferences settings = appContext.getSharedPreferences(SECRET_KEY_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(customerIdKey, customerIdVal);
        editor.apply();
    }


}
