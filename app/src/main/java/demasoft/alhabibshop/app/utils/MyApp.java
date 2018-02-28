package demasoft.alhabibshop.app.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;

import demasoft.alhabibshop.app.R;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static demasoft.alhabibshop.app.utils.AppConstants.LANGUAGE_KEY;
import static demasoft.alhabibshop.app.utils.AppConstants.appContext;

/**
 * Created by Inzimam Tariq on 1/17/2018.
 */

public class MyApp extends Application {
    
    private Locale locale;
    private static Context context;
    
    public static Context getAppContext() {
        return context;
    }
    
    private static final Set<String> RTL;
    
    static {
        Set<String> lang = new HashSet<String>();
        lang.add("ar"); // Arabic
        lang.add("dv"); // Dhivehi
        lang.add("fa"); // Persian (Farsi)
        lang.add("ha"); // Hausa
        lang.add("he"); // Hebrew
        lang.add("iw"); // Hebrew (old code)
        lang.add("ji"); // Yiddish (old code)
        lang.add("ps"); // Pashto, Pushto
        lang.add("ur"); // Urdu
        lang.add("yi"); // Yiddish
        RTL = Collections.unmodifiableSet(lang);
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        context = getBaseContext();
        
        
        try {
            String lang = Preferences
                    .getSharedPreferenceString(appContext, LANGUAGE_KEY, "ar");
            Locale l = new Locale(lang);
            
            Configuration config = getApplicationContext().getResources().getConfiguration();
            Log.e("MyApp", "Before RTL Check Lang = " + lang
                    + "\nLocal Lang = " + l.getLanguage());
            if (isRTLTrue(l)) {
                Log.e("MyApp", "Changing Calligraphy");
                CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/DroidKufi-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLayoutDirection(locale);
                }
            } else {
                Log.e("MyApp", "RTL Check Else");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean isRTLTrue(Locale locale) {
        return locale != null && RTL.contains(locale.getLanguage());
    }
    
    public static boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }
    
    
}
