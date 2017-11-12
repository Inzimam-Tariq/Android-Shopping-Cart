package com.qemasoft.alhabibshop.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.qemasoft.alhabibshop.app.view.activities.SplashActivity;

/**
 * Created by Inzimam on 29-Oct-17.
 */

public class AppConstants {

    public static final int SPLASH_REQUEST_CODE = 1;
    public static final int LOGIN_REQUEST_CODE = 2;
    public static final int REGISTER_REQUEST_CODE = 3;
    public static final int ORDER_HISTORY_REQUEST_CODE = 4;
    public static final int ORDER_DETAIL_REQUEST_CODE = 5;
    public static final int DASHBOARD_REQUEST_CODE = 6;
    public static final int EDIT_ACCOUNT_REQUEST_CODE = 7;
    public static final int CHANGE_PASS_REQUEST_CODE = 8;
    public static final int FORGOT_PASS_REQUEST_CODE = 9;
    public static final int CONTACT_US_REQUEST_CODE = 10;
    public static final int HOME_REQUEST_CODE = 11;
    public static final int CATEGORY_REQUEST_CODE = 12;
    public static final int PRODUCT_REQUEST_CODE = 13;
    public static final String DEFAULT_STRING_VALUE = "";
    public static final Context appContext = SplashActivity.getAppContext();
    public static final String SECRET_KEY_FILE = "secret_key";
    public static final String KEY_FOR_KEY = "KEY_VALUE";
    public static final String LOGIN_KEY = "LOGIN_KEY";
    public static final String CUSTOMER_KEY = "CUSTOMER_KEY";
    private static final String BASE_URL = "http://www.opencartgulf.com/api/";
    public static final String SECRET_KEY_URL = BASE_URL + "getKey";
    private static String MID_FIX_API;
    private static String HOME_EXTRA;
    private static String PRODUCT_EXTRA;

    public static String findStringByName(String name) {
        Resources res = appContext.getResources();
        return res.getString(res.getIdentifier(name, "string", appContext.getPackageName()));
    }

    public static String getApiCallUrl() {
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

    public static String getHomeExtra() {
        return HOME_EXTRA;
    }

    public static void setHomeExtra(String homeExtra) {
        HOME_EXTRA = homeExtra;
    }

    public static String getProductExtra() {
        return PRODUCT_EXTRA;
    }

    public static void setProductExtra(String productExtra) {
        PRODUCT_EXTRA = productExtra;
    }
}
