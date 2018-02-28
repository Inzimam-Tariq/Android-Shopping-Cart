package demasoft.alhabibshop.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import demasoft.alhabibshop.app.R;

import java.util.Locale;

/**
 * Created by SKAFS on 2/25/2018.
 */

public class ThemeUtils {
    
    public void setTheme(Context c) {
        String themeCode = Preferences.getSharedPreferenceString(
                AppConstants.appContext, AppConstants.THEME_CODE, "default");
        
        Resources resources = c.getResources();
        Log.e("ThemeUtils", " Set Theme, ThemeCode = " + themeCode);
        if (!MyApp.isRTL(Locale.getDefault())) {
            switch (themeCode) {
                case "red": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryRed);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentRed);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialRed);
                    break;
                }
                case "pink": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryPink);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentPink);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialPink);
                    break;
                }
                case "purple": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryPurple);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentPurple);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialPurple);
                    break;
                }
                case "deep_purple": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryDeepPurple);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentDeepPurple);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, pColor, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialDeepPurple);
                    break;
                }
                case "indigo": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryIndigo);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentIndigo);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, pColor, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialIndigo);
                    break;
                }
                case "blue": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryBlue);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentBlue);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialBlue);
                    break;
                }
                case "light_blue": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryLightBlue);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentLightBlue);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialLightBlue);
                    break;
                }
                case "cyan": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryCyan);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentCyan);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialCyan);
                    break;
                }
                case "teal": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryTeal);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentTeal);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialTeal);
                    break;
                }
                case "green": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryGreen);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentGreen);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialGreen);
                    break;
                }
                case "light_green": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryLightGreen);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentLightGreen);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialLightGreen);
                    break;
                }
                case "lime": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryLime);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentLime);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialLime);
                    break;
                }
                case "yellow": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryYellow);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentYellow);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialYellow);
                    break;
                }
                case "amber": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryAmber);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentAmber);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialAmber);
                    break;
                }
                case "orange": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryOrange);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentOrange);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialOrange);
                    break;
                }
                case "deep_orange": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryDeepOrange);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentDeepOrange);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialDeepOrange);
                    break;
                }
                case "brown": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryBrown);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentBrown);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialBrown);
                    break;
                }
                case "grey": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryGrey);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentGrey);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialGrey);
                    break;
                }
                case "blue_grey": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryBlueGrey);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentBlueGrey);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialBlueGrey);
                    break;
                }
                
                default:
                    
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, "#ffffff");
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, "#EC7625");
                    c.setTheme(R.style.AppTheme_NoActionBar);
                    break;
            }
            
        } else {
            switch (themeCode) {
                case "red": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryRed);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentRed);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialRed_RTL);
                    break;
                }
                case "pink": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryPink);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentPink);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialPink_RTL);
                    break;
                }
                case "purple": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryPurple);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentPurple);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialPurple_RTL);
                    break;
                }
                case "deep_purple": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryDeepPurple);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentDeepPurple);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, pColor, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialDeepPurple_RTL);
                    break;
                }
                case "indigo": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryIndigo);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentIndigo);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, pColor, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialIndigo_RTL);
                    break;
                }
                case "blue": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryBlue);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentBlue);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialBlue_RTL);
                    break;
                }
                case "light_blue": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryLightBlue);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentLightBlue);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialLightBlue_RTL);
                    break;
                }
                case "cyan": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryCyan);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentCyan);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialCyan_RTL);
                    break;
                }
                case "teal": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryTeal);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentTeal);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialTeal_RTL);
                    break;
                }
                case "green": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryGreen);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentGreen);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialGreen_RTL);
                    break;
                }
                case "light_green": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryLightGreen);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentLightGreen);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialLightGreen_RTL);
                    break;
                }
                case "lime": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryLime);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentLime);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialLime_RTL);
                    break;
                }
                case "yellow": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryYellow);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentYellow);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialYellow_RTL);
                    break;
                }
                case "amber": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryAmber);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentAmber);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialAmber_RTL);
                    break;
                }
                case "orange": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryOrange);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentOrange);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialOrange_RTL);
                    break;
                }
                case "deep_orange": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryDeepOrange);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentDeepOrange);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialDeepOrange_RTL);
                    break;
                }
                case "brown": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryBrown);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentBrown);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialBrown_RTL);
                    break;
                }
                case "grey": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryGrey);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentGrey);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialGrey_RTL);
                    break;
                }
                case "blue_grey": {
                    @SuppressLint("ResourceType")
                    String pColor = resources.getString(R.color.colorPrimaryBlueGrey);
                    @SuppressLint("ResourceType")
                    String aColor = resources.getString(R.color.colorAccentBlueGrey);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, pColor);
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, aColor);
                    c.setTheme(R.style.MaterialBlueGrey_RTL);
                    break;
                }
                
                default:
                    
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.PRIMARY_COLOR, "#ffffff");
                    Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.ACCENT_COLOR, "#EC7625");
                    c.setTheme(R.style.AppTheme_NoActionBar_RTL);
                    break;
            }
        }
    }
}
