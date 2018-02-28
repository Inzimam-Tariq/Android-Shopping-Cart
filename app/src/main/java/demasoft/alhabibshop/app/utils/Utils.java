package demasoft.alhabibshop.app.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import demasoft.alhabibshop.app.R;
import demasoft.alhabibshop.app.controller.MyPagerAdapter;
import demasoft.alhabibshop.app.model.Slideshow;
import demasoft.alhabibshop.app.view.activities.MainActivity;
import demasoft.alhabibshop.app.view.fragments.FragCartDetail;
import me.relex.circleindicator.CircleIndicator;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Inzimam Tariq on 18/10/2017.
 */

public class Utils {
    
    private Context mContext;
    private ProgressDialog progressBar;
    private int position, currentPage;
    private MyCountDownTimer myCountDownTimer;
    
    
    public Utils(Context mContext) {
        this.mContext = mContext;
    }
    
    public interface ClickInterface {
        void OnItemClicked(int item_id);
    }
    
    
    public static int getScreenWidth(Context context) {
        
        int screenWidth = 0;
        
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        
        return screenWidth;
    }
    
    
    public void startNewActivity(Class activityToStart, Bundle extras, boolean isFinish) {
        
        Intent intent = new Intent(mContext, activityToStart);
        if (extras != null)
            intent.putExtras(extras);
        mContext.startActivity(intent);
        
        if (isFinish)
            ((Activity) mContext).finish();
    }
    
    public void hideKeyboard(View view) {
        
        InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    
    
    public boolean validName(String s) {
        
        String validNumber = "(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,15})$";
        Pattern pattern = Pattern.compile(validNumber);
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
    
    public boolean validPassword(String s) {
        
        String validPass = "^([a-zA-Z0-9@*#]{8,15})$";
        Pattern pattern = Pattern.compile(validPass);
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
    
    public boolean validNumber(String s) {
        
        String validNumber = "^((\\+92)|(0092))-{0,1}\\d{3}-{0,1}\\d{7}$|^\\d{11}$|^\\d{4}-\\d{7}$";
        Pattern pattern = Pattern.compile(validNumber);
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
    
    public boolean validEmail(String email) {
        
        String emailPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
    
    public boolean validCNIC(String s) {
        
        String validNumber = "^\\d{5}[- .]?\\d{7}[- .]?\\d{1}$";
        Pattern pattern = Pattern.compile(validNumber);
        Matcher matcher = pattern.matcher(s);
        return matcher.find();
    }
    
    public boolean isNetworkConnected() {
        
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null) && netInfo.isConnected();
    }
    
    
    public void showProgress() {
        
        String title = AppConstants.findStringByName("progress_dialog_title");
        String text = AppConstants.findStringByName("progress_dialog_text");
        progressBar = ProgressDialog.show(mContext, title, text);
    }
    
    public void hideProgress() {
        
        if (progressBar.isShowing()) {
            progressBar.dismiss();
        }
    }
    
    public int checkOrientation() {
        //        Activity.getResources().getConfiguration().orientation;
        
        return mContext.getResources().getConfiguration().orientation;
    }
    
    public void setCompoundDrawable(TextView textView, String position, int icon) {
        
        switch (position) {
            case "top":
                textView.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
                break;
            case "right":
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0);
                break;
            case "bottom":
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, icon);
                break;
            default:
                textView.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
                break;
        }
    }
    
    public void setCompoundDrawable(Button button, String position, int icon) {
        
        switch (position) {
            case "top":
                button.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
                break;
            case "right":
                button.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0);
                break;
            case "bottom":
                button.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, icon);
                break;
            default:
                button.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
                break;
        }
    }
    
    public void changeLanguage(String languageCode) {
        
        String language = Preferences.getSharedPreferenceString(AppConstants.appContext,
                AppConstants.LANGUAGE_KEY, "ar");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        AppConstants.appContext.getResources().updateConfiguration(config,
                AppConstants.appContext.getResources().getDisplayMetrics());
    }
    
    
    public void clearSession() {
        
        Preferences.setSharedPreferenceBoolean(AppConstants.appContext, AppConstants.IS_LOGIN, false);
        Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.CUSTOMER_ID_KEY, AppConstants.DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.CUSTOMER_EMAIL, AppConstants.DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.CUSTOMER_FIRST_NAME, AppConstants.DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.CUSTOMER_LAST_NAME, AppConstants.DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.UNIQUE_ID_KEY, AppConstants.DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.CUSTOMER_CONTACT, AppConstants.DEFAULT_STRING_VAL);
        ((MainActivity) mContext).counterTV.setText("0");
        Preferences.setSharedPreferenceString(AppConstants.appContext, "couponCode", "");
        
    }
    
    
    public void printLog(String msg) {
        Log.e("TAG " + mContext.getPackageName(), msg);
    }
    
    public void printLog(String tag, String msg) {
        Log.e(mContext.getClass() + " " + tag, msg);
    }
    
    public void setupSlider(final ViewPager mPager, CircleIndicator indicator,
                            final ProgressBar pb,
                            boolean isIndicatorSet, boolean isClickListenerSet) {
        
        currentPage = 0;
        final ArrayList<Slideshow> slideshowArrayList = new ArrayList<>();
        final String responseStr = AppConstants.getSlideshowExtra();
        
        if (!responseStr.isEmpty()) {
            printLog("ResponseInSliderFrag", responseStr);
            try {
                JSONArray slideShowArray = new JSONArray(responseStr);
                
                for (int i = 0; i < slideShowArray.length(); i++) {
                    try {
                        JSONObject sliderObj = slideShowArray.getJSONObject(i);
                        slideshowArrayList.add(new Slideshow(sliderObj.optString("image"),
                                sliderObj.optString("id"), sliderObj.optString("banertype")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                printLog("JSONObjEx_SliderFrag", responseStr);
            }
        } else {
            printLog("ResponseExSliderFrag", responseStr);
        }
        mPager.setAdapter(new MyPagerAdapter(mContext, slideshowArrayList, isClickListenerSet));
        
        if (isIndicatorSet)
            indicator.setViewPager(mPager);
        // Auto start of viewpager
        
        if (slideshowArrayList.size() <= 1) {
            pb.setVisibility(View.GONE);
            return;
        }
        final int time = 6000;
        final Handler handler = new Handler();
        
        final Runnable Update = new Runnable() {
            public void run() {
                
                myCountDownTimer = new MyCountDownTimer(time, 10, pb);
                myCountDownTimer.start();
                if (currentPage == slideshowArrayList.size()) {
                    currentPage = 0;
                    mPager.setCurrentItem(currentPage++, false);
                } else
                    mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                
                handler.post(Update);
            }
        }, time, time);
    }
    
    public void getUniqueId() {
        
        String uniqueId = Preferences.getSharedPreferenceString(AppConstants.appContext,
                AppConstants.UNIQUE_ID_KEY, AppConstants.DEFAULT_STRING_VAL);
        if (uniqueId.isEmpty() || uniqueId.equals("")) {
            Preferences.setSharedPreferenceString(AppConstants.appContext, AppConstants.UNIQUE_ID_KEY, generateUniqueId());
        }
    }
    
    private String generateUniqueId() {
        String uniqueId = UUID.randomUUID().toString();
        String subStrId = uniqueId.substring(0, 24);
        printLog("UniqueId", subStrId);
        return subStrId;
    }
    
    public void shareContent(String title, String url) {
        
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, title);
//        i.putExtra(Intent.EXTRA_COMPONENT_NAME, title);
        i.putExtra(Intent.EXTRA_TEXT, url);
        mContext.startActivity(Intent.createChooser(i, "Share URL"));
        
    }
    
    public void setError(EditText editText) {
        editText.setError(AppConstants.findStringByName("required"));
        editText.requestFocus();
    }
    
    public void applyAccentColor(TextView textView) {
        String accentColor = Preferences.getSharedPreferenceString(
                AppConstants.appContext, AppConstants.ACCENT_COLOR, "#EC7625");
        
        if (!accentColor.isEmpty())
            textView.setTextColor(Color.parseColor(accentColor));
        
    }
    
    public void applyAccentColor(Button button) {
        String accentColor = Preferences.getSharedPreferenceString(
                AppConstants.appContext, AppConstants.ACCENT_COLOR, "#EC7625");
        
        if (!accentColor.isEmpty())
            button.setTextColor(Color.parseColor(accentColor));
        
    }
    
    public void switchFragment(Fragment fragment) {
        
        ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragments, fragment).addToBackStack(null).commit();
    }
    
    public void switchFragment(Fragment fragment, Bundle bundle) {
        
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragments, fragment).addToBackStack(null).commit();
    }
    
    
    public int getSelectedRadioIndex(RadioGroup radioGroup) {
        
        return radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()));
    }
    
    public void ChangeFont(String type) {
        if (type.equals("bold"))
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/DroidKufi-Bold.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );
        else CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/DroidKufi-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
    
    public void applyCoupon(final String couponCode) {
        
        showProgress();
        if (!couponCode.isEmpty()) {
            AndroidNetworking.post(AppConstants.getApiCallUrl())
                    .addBodyParameter("coupon", couponCode)
                    .addBodyParameter("session_id",
                            Preferences.getSharedPreferenceString(mContext,
                                    AppConstants.UNIQUE_ID_KEY, AppConstants.DEFAULT_STRING_VAL))
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            boolean success = response.optBoolean("success");
                            if (success) {
                                hideProgress();
                                String msg = response.optString("message");
                                if (!msg.isEmpty()) {
                                    showAlert(R.string.information_text,
                                            msg, true,
                                            R.string.ok, null,
                                            R.string.cancel_text, null);
                                }
                                Preferences.setSharedPreferenceString(AppConstants.appContext
                                        , "couponCode", couponCode);
                                Bundle bundle = new Bundle();
                                bundle.putString("midFix", "cartProducts");
                                switchFragment(new FragCartDetail(), bundle);
                            } else {
                                hideProgress();
                                printLog("doSimpleRequest", "Success False");
                                printLog("doSimpleRequest", response.toString());
                            }
                        }
                        
                        @Override
                        public void onError(ANError anError) {
                            hideProgress();
                            printLog("doSimpleRequest", "If anError");
                            anError.printStackTrace();
                            
                        }
                    });
        }
    }
    
    
    public boolean isLoggedIn() {
        
        boolean isLoggedIn = Preferences.getSharedPreferenceBoolean(AppConstants.appContext, AppConstants.IS_LOGIN, false);
        printLog("IsLoggedIn = ", "" + isLoggedIn);
        
        return isLoggedIn;
    }
    
    public void setItemCount() {
        AppConstants.setMidFixApi("countProducts");
        printLog("Url = ", AppConstants.getApiCallUrl());
        AndroidNetworking.post(AppConstants.getApiCallUrl())
                .addBodyParameter("session_id", Preferences.getSharedPreferenceString(
                        AppConstants.appContext,
                        AppConstants.UNIQUE_ID_KEY,
                        AppConstants.DEFAULT_STRING_VAL))
                .addBodyParameter("customer_id", Preferences.getSharedPreferenceString(
                        AppConstants.appContext,
                        AppConstants.CUSTOMER_ID_KEY,
                        "0"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean success = response.optBoolean("success");
                        if (success) {
                            String itemCount = response.optString("total");
                            ((MainActivity) mContext).counterTV.setText(itemCount);
                            
                            printLog("GettingItemCount", "If Success");
                            printLog("GettingItemCount", response.toString());
                        } else {
                            printLog("GettingItemCount", "Success False");
                            printLog("GettingItemCount", response.toString());
                        }
                    }
                    
                    @Override
                    public void onError(ANError anError) {
                        printLog("GettingItemCount", "If anError");
                        anError.printStackTrace();
                        printLog("onError errorCode : " + anError.getErrorCode());
                        printLog("onError errorBody : " + anError.getErrorBody());
                        printLog("onError errorDetail : " + anError.getErrorDetail());
                        
                    }
                });
    }
    
    //    Dialogs and Toasts
    public void showAlert(int titleId, int msgId, boolean cancelable, int posBtnTextId,
                          final Fragment posBtnActionFrag,
                          int negBtnTextId, final Fragment negBtnActionFrag) {
        
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(cancelable);
        LayoutInflater inflater = ((AppCompatActivity) mContext).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_alert_dialog, null);
        
        builder.setView(dialogView);
        
        TextView titleTV = dialogView.findViewById(R.id.dialog_title);
        View separator = dialogView.findViewById(R.id.title_separator);
        TextView msgTV = dialogView.findViewById(R.id.msg_tv);
        Button buttonPositive = dialogView.findViewById(R.id.positive_btn);
        Button buttonNegative = dialogView.findViewById(R.id.negative_btn);
        
        titleTV.setText(titleId);
        msgTV.setText(msgId);
        buttonPositive.setText(posBtnTextId);
        styleDialog(titleTV, separator, buttonPositive, buttonNegative, false);
        
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        
        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posBtnActionFrag != null)
                    switchFragment(posBtnActionFrag);
                alertDialog.dismiss();
            }
        });
        buttonNegative.setText(negBtnTextId);
        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (negBtnActionFrag != null)
                    switchFragment(negBtnActionFrag);
                alertDialog.dismiss();
            }
        });
    }
    
    public void showAlert(int titleId, String msg, boolean cancelable, int posBtnTextId,
                          final Fragment posBtnActionFrag,
                          int negBtnTextId, final Fragment negBtnActionFrag) {
        
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(cancelable);
        LayoutInflater inflater = ((AppCompatActivity) mContext).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_alert_dialog, null);
        
        builder.setView(dialogView);
        
        TextView titleTV = dialogView.findViewById(R.id.dialog_title);
        View separator = dialogView.findViewById(R.id.title_separator);
        TextView msgTV = dialogView.findViewById(R.id.msg_tv);
        Button buttonPositive = dialogView.findViewById(R.id.positive_btn);
        Button buttonNegative = dialogView.findViewById(R.id.negative_btn);
        
        titleTV.setText(titleId);
        msgTV.setText(msg);
        buttonPositive.setText(posBtnTextId);
        styleDialog(titleTV, separator, buttonPositive, buttonNegative, false);
        
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posBtnActionFrag != null)
                    switchFragment(posBtnActionFrag);
                alertDialog.dismiss();
            }
        });
        
        buttonNegative.setText(negBtnTextId);
        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (negBtnActionFrag != null)
                    switchFragment(negBtnActionFrag);
                alertDialog.dismiss();
            }
        });
    }
    
    
    public void styleDialog(TextView titleTV, View separator,
                            Button pBtn, Button nBtn, boolean hasToSetParams) {
        
        if (hasToSetParams) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(40, 0, 40, 0);
            params.weight = 1f;
            
            pBtn.setLayoutParams(params);
            nBtn.setLayoutParams(params);
        }
        
        String theme = Preferences.getSharedPreferenceString(AppConstants.appContext,
                AppConstants.THEME_CODE, "default");
        String pColor = Preferences.getSharedPreferenceString(
                AppConstants.appContext, AppConstants.PRIMARY_COLOR, "#EC7625");
        String aColor = Preferences.getSharedPreferenceString(
                AppConstants.appContext, AppConstants.ACCENT_COLOR, "#555555");
        
        if (theme != null && !theme.isEmpty() &&
                !theme.equalsIgnoreCase("default")) {
            pBtn.setBackgroundColor(Color.parseColor(pColor));
            if (titleTV != null)
                titleTV.setTextColor(Color.parseColor(pColor));
            if (separator != null)
                separator.setBackgroundColor(Color.parseColor(pColor));
            nBtn.setBackgroundColor(Color.parseColor(aColor));
        } else {
            pColor = "#EC7625";
            aColor = "#555555";
            pBtn.setBackgroundColor(Color.parseColor(pColor));
            nBtn.setBackgroundColor(Color.parseColor(aColor));
        }
    }
    
    public void showAlertDialogTurnWifiOn() {
        
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Enable Internet")
                .setMessage("It seems you have no internet connection, Goto Wifi setting" +
                        " or Goto settings")
                .setPositiveButton(R.string.wifi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                        WifiManager wifiManager = (WifiManager) mContext.getApplicationContext()
                                .getSystemService(Context.WIFI_SERVICE);
                        assert wifiManager != null;
//                        if (!wifiManager.isWifiEnabled()) {
//                            wifiManager.setWifiEnabled(true);
                        ((AppCompatActivity) mContext).startActivityForResult(
                                new Intent(Settings.ACTION_WIFI_SETTINGS), 0);
//                        }
                    }
                })
                .setNegativeButton(R.string.action_settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                        ((AppCompatActivity) mContext).startActivityForResult(
                                new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        
        Button pBtn = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button nBtn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        
        styleDialog(null, null, pBtn, nBtn, true);
        
    }
    
    public void showRadioAlertDialog(final Button button, String title,
                                     final List<String> list, int selectedIndex,
                                     final ClickInterface clickInterface) {
        
        this.position = 0;
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setSingleChoiceItems(list.toArray(new String[list.size()]),
                selectedIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                        if (which > -1) {
                            position = which;
                        }
                    }
                });
        builder.setPositiveButton(R.string.confirm_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                button.setHint(list.get(position));
                if (clickInterface != null)
                    clickInterface.OnItemClicked(position);
                printLog("Which", "List position =" + list.get(position));
            }
        });
//        builder.setCustomTitle(textView);
        builder.setNegativeButton(R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
        
        Button pBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button nBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        
        styleDialog(null, null, pBtn, nBtn, true);
    }
    
    public void showInternetErrorDialog(int msgId) {
        
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        
        LayoutInflater inflater = ((MainActivity) mContext).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_alert_dialog, null);
        builder.setView(dialogView);
        
        TextView titleTV = dialogView.findViewById(R.id.dialog_title);
        titleTV.setText(R.string.information_text);
        Button buttonPositive = dialogView.findViewById(R.id.positive_btn);
        buttonPositive.setText(R.string.ok);
        
        TextView msgTV = dialogView.findViewById(R.id.msg_tv);
        msgTV.setText(msgId);
        
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
    
    public void showNumberPickerDialog() {
        
        final NumberPicker picker = new NumberPicker(mContext);
        picker.setMinValue(1);
        picker.setMaxValue(10);
        final FrameLayout layout = new FrameLayout(mContext);
        layout.addView(picker, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));
        
        new AlertDialog.Builder(mContext)
                .setView(layout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do something with picker.getValue()
                        picker.getValue();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
    
    
    // Toast Messages
    public void showToast(String msg) {
        Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
    }
    
    public void showToast(String msg, int length) {
        Toast.makeText(mContext, "" + msg, Toast.LENGTH_LONG).show();
    }
    
    public void showToast(int msgStrId) {
        Toast.makeText(mContext, "" + msgStrId, Toast.LENGTH_SHORT).show();
    }
    
    public void showToast(int msgStrId, int length) {
        Toast.makeText(mContext, "" + msgStrId, Toast.LENGTH_LONG).show();
    }
    
    
}
