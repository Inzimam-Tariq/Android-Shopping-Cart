package com.qemasoft.alhabibshop.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

import static com.qemasoft.alhabibshop.app.AppConstants.LANGUAGE_KEY;

/**
 * Created by Inzimam Tariq on 1/17/2018.
 */

public class MyApp extends Application {
    
    private Locale locale;
    private static Context context;
    
    public static Context getAppContext() {
        return context;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        context = getBaseContext();

//        // Setup handler for uncaught exceptions.
//        Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
//        {
//            @Override
//            public void uncaughtException (Thread thread, Throwable e)
//            {
//                handleUncaughtException (thread, e);
//            }
//        });
        
        try {
            
            changeLocale(context);
            Configuration config = context.getResources().getConfiguration();
    
            if (isRTL(Locale.getDefault())) {
                CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/DroidKufi-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLayoutDirection(locale);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void changeLocale(Context context) {
        Configuration config = context.getResources().getConfiguration();
        String lang = Preferences
                .getSharedPreferenceString(this, LANGUAGE_KEY, "ar");
        Log.e("MyApp", "language in MyApp = " + lang);
        if (!(config.locale.getLanguage().equals(lang))) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            Log.e("MyApp", "Inside if = " + lang);
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
    }

//    public void handleUncaughtException (Thread thread, Throwable e) {
//        e.printStackTrace(); // not all Android versions will print the stack trace automatically
//
////        System.exit(1); // kill off the crashed app
//    }
    
    
    public static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (locale != null) {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            context.getResources().updateConfiguration(newConfig, context.getResources().getDisplayMetrics());
        }
    }
    
}
