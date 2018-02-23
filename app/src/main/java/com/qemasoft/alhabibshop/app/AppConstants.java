package com.qemasoft.alhabibshop.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.qemasoft.alhabibshop.app.model.ProductOptionValueItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inzimam on 29-Oct-17.
 */

public class AppConstants {
    
    public static final int SPLASH_REQUEST_CODE = 1;
    public static final int SEARCH_REQUEST_CODE = 769;
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
    public static final int SPECIAL_PRODUCTS_REQUEST_CODE = 12;
    public static final int PRODUCT_REQUEST_CODE = 13;
    public static final int PRODUCT_DETAIL_REQUEST_CODE = 14;
    public static final int RIGHT_MENU_REQUEST_CODE = 15;
    public static final int ADD_TO_CART_REQUEST_CODE = 16;
    public static final int ADDRESS_BOOK_REQUEST_CODE = 17;
    public static final int ADD_ADDRESS_REQUEST_CODE = 18;
    public static final int EDIT_ADDRESS_REQUEST_CODE = 19;
    public static final int DELETE_ADDRESS_REQUEST_CODE = 20;
    public static final int COUNTRIES_REQUEST_CODE = 21;
    public static final int STATES_REQUEST_CODE = 22;
    public static final int SHIPPING_METHOD_REQUEST_CODE = 23;
    public static final int PAYMENT_METHOD_REQUEST_CODE = 24;
    public static final int CONFIRM_CHECKOUT_REQUEST_CODE = 25;
    public static final int PLACE_ORDER_REQUEST_CODE = 26;
    public static final int LANGUAGE_REQUEST_CODE = 27;
    public static final int CURRENCY_REQUEST_CODE = 28;
    public static final int FORCE_CANCELED = 2;
    
    public static final String DEFAULT_STRING_VAL = "";
    public static final String UNIQUE_ID_KEY = "UNIQUE_ID_KEY";
    public static final Context appContext = MyApp.getAppContext();
    public static final String SECRET_KEY_FILE = "secret_key";
    public static final String KEY_FOR_KEY = "KEY_VALUE";
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String LOGO_KEY = "LOGO_KEY";
    public static final String LOGO_TYPE = "LOGO_TYPE";
    public static final String MENU_TYPE = "MENU_TYPE";
    public static final String CURRENCY_KEY = "DEFAULT_CURRENCY";
    public static final String CURRENCY_SYMBOL_KEY = "CURRENCY_SYMBOL";
    public static final String LANGUAGE_KEY = "DEFAULT_LANGUAGE";
    public static final String CUSTOMER_ID_KEY = "CUSTOMER_ID_KEY";
    public static final String CUSTOMER_EMAIL = "CUSTOMER_EMAIL";
    public static final String CUSTOMER_FIRST_NAME = "CUSTOMER_FIRST_NAME";
    public static final String CUSTOMER_LAST_NAME = "CUSTOMER_LAST_NAME";
    public static final String CUSTOMER_CONTACT = "CUSTOMER_CONTACT";
    public static final String RIGHT = "right";
    public static final String LEFT = "left";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String PRIMARY_COLOR = "PRIMARY_COLOR";
    public static final String ACCENT_COLOR = "ACCENT_COLOR";
    public static final String THEME_CODE = "ThemeCode";
        private static final String BASE_URL = "https://www.opencartgulf.com/api/";
//    private static final String BASE_URL = "https://www.alhabibshop.com/api/";
    //    private static final String BASE_URL = "https://www.tecnicomovil.es/api/";
    public static final String SECRET_KEY_URL = BASE_URL + "getKey";
    
    public static List<ProductOptionValueItem> optionsList = new ArrayList<>();
    private static String MID_FIX_API;
    private static String HOME_EXTRA;
    private static String SLIDESHOW_EXTRA;
    static private int shippingSelectedIndex = -1;
    static private int paymentSelectedIndex = -1;
    
    
    public static String findStringByName(String name) {
        Resources res = appContext.getResources();
        return res.getString(res.getIdentifier(name, "string", appContext.getPackageName()));
    }
    
    public static String getApiCallUrl() {
        return BASE_URL + getMidFixApi() + "/" + GET_KEY(appContext, KEY_FOR_KEY);
    }
    
    private static String getMidFixApi() {
        return MID_FIX_API;
    }
    
    public static void setMidFixApi(String midFixApi) {
        MID_FIX_API = midFixApi;
    }
    
    public static String GET_KEY(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(SECRET_KEY_FILE, 0);
        
        return sharedPref.getString(key, "");
    }
    
    public static void SET_KEY(String secretKey, String secretKeyVal) {
        SharedPreferences settings = appContext.getSharedPreferences(SECRET_KEY_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(secretKey, secretKeyVal);
        editor.apply();
    }
    
    public static String getHomeExtra() {
        return HOME_EXTRA;
    }
    
    public static void setHomeExtra(String homeExtra) {
        HOME_EXTRA = homeExtra;
    }
    
    public static String getSlideshowExtra() {
        return SLIDESHOW_EXTRA;
    }
    
    public static void setSlideshowExtra(String slideshowExtra) {
        SLIDESHOW_EXTRA = slideshowExtra;
    }
    
    
    public static int getShippingSelectedIndex() {
        return shippingSelectedIndex;
    }
    
    public static void setShippingSelectedIndex(int shippingSelectedIndex) {
        AppConstants.shippingSelectedIndex = shippingSelectedIndex;
    }
    
    public static int getPaymentSelectedIndex() {
        return paymentSelectedIndex;
    }
    
    public static void setPaymentSelectedIndex(int paymentSelectedIndex) {
        AppConstants.paymentSelectedIndex = paymentSelectedIndex;
    }
}
