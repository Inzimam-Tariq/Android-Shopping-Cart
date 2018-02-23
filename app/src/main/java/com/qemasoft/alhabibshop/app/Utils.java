package com.qemasoft.alhabibshop.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
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
import com.qemasoft.alhabibshop.app.controller.MyPagerAdapter;
import com.qemasoft.alhabibshop.app.model.Slideshow;
import com.qemasoft.alhabibshop.app.view.activities.MainActivity;
import com.qemasoft.alhabibshop.app.view.fragments.FragCartDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.relex.circleindicator.CircleIndicator;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.qemasoft.alhabibshop.app.AppConstants.ACCENT_COLOR;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_CONTACT;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_EMAIL;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_FIRST_NAME;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_LAST_NAME;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.IS_LOGIN;
import static com.qemasoft.alhabibshop.app.AppConstants.LANGUAGE_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.PRIMARY_COLOR;
import static com.qemasoft.alhabibshop.app.AppConstants.THEME_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.UNIQUE_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;
import static com.qemasoft.alhabibshop.app.AppConstants.findStringByName;
import static com.qemasoft.alhabibshop.app.AppConstants.getApiCallUrl;

/**
 * Created by Inzimam Tariq on 18/10/2017.
 */

public class Utils {
    
    private Context mContext;
    private ProgressDialog progressBar;
    private int position, currentPage;
    private MyCountDownTimer myCountDownTimer;
    
    private String theme = Preferences.getSharedPreferenceString(appContext,
            THEME_CODE, "default");
    private String pColor = Preferences.getSharedPreferenceString(
            appContext, PRIMARY_COLOR, "#EC7625");
    private String aColor = Preferences.getSharedPreferenceString(
            appContext, ACCENT_COLOR, "#555555");
    
    
    public Utils(Context mContext) {
        
        this.mContext = mContext;
    }
    
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        
        if (height > reqHeight || width > reqWidth) {
            
            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            
            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        
        return inSampleSize;
    }
    
    public static File getVideoCacheDir(Context context) {
        
        return new File(context.getExternalCacheDir(), "video-cache");
    }
    
    public static void cleanVideoCacheDir(Context context) throws IOException {
        
        File videoCacheDir = getVideoCacheDir(context);
        cleanDirectory(videoCacheDir);
    }
    
    private static void cleanDirectory(File file) throws IOException {
        
        if (!file.exists()) {
            return;
        }
        File[] contentFiles = file.listFiles();
        if (contentFiles != null) {
            for (File contentFile : contentFiles) {
                delete(contentFile);
            }
        }
    }
    
    private static void delete(File file) throws IOException {
        
        if (file.isFile() && file.exists()) {
            deleteOrThrow(file);
        } else {
            cleanDirectory(file);
            deleteOrThrow(file);
        }
    }
    
    private static void deleteOrThrow(File file) throws IOException {
        
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                throw new IOException(String.format("File %s can't be deleted", file.getAbsolutePath()));
            }
        }
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
    
    public static Drawable drawableFromUrl(String url) throws IOException {
        
        Bitmap x;
        
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();
        
        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }
    
    public static double round1(double value, int scale) {
        
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }
    
    public static float round2(float number, int scale) {
        
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ((float) ((int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp))) / pow;
    }
    
    public int getScreenSize() {
        
        return mContext.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
    }
    
    //Cache functions
    
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
    
    public File resizeImageFile(File file, int reqSize) {
        
        if (file != null) {
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            
            
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqSize, reqSize);
            
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return convertBitmapToFile(BitmapFactory.decodeFile(file.getAbsolutePath(), options), file.getName());
        }
        
        return null;
    }
    
    public File convertBitmapToFile(Bitmap bitmap, String filename) {
        //create a file to write bitmap data
        File f = new File(mContext.getCacheDir(), filename);
        try {
            f.createNewFile();
            //Convert bitmap to byte array
            
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 5 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            
            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return f;
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
    
    public String milliSecondsToTimer(long milliseconds) {
        
        String finalTimerString = "";
        String secondsString = "";
        
        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        
        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        
        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        
        // return timer string
        return finalTimerString;
    }
    
    public boolean isNetworkConnected() {
        
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null) && netInfo.isConnected();
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
                        if (!wifiManager.isWifiEnabled()) {
//                            wifiManager.setWifiEnabled(true);
                            ((AppCompatActivity) mContext).startActivityForResult(
                                    new Intent(Settings.ACTION_WIFI_SETTINGS), 0);
                        }
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
        
        styleDialogButtons(pBtn, nBtn);
        
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
        builder.setNegativeButton(R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
        
        Button pBtn = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button nBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        
        styleDialogButtons(pBtn, nBtn);
    }
    
    private void styleDialogButtons(Button pBtn, Button nBtn) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(40, 0, 40, 0);
        params.weight = 1f;
        
        pBtn.setLayoutParams(params);
        nBtn.setLayoutParams(params);
        
        if (theme != null && !theme.isEmpty() &&
                !theme.equalsIgnoreCase("default")) {
            pBtn.setBackgroundColor(Color.parseColor(pColor));
            nBtn.setBackgroundColor(Color.parseColor(aColor));
        } else {
            String pColor = "#EC7625";
            String aColor = "#555555";
            pBtn.setBackgroundColor(Color.parseColor(pColor));
            nBtn.setBackgroundColor(Color.parseColor(aColor));
        }
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
        
        String language = Preferences.getSharedPreferenceString(appContext,
                LANGUAGE_KEY, "ar");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        appContext.getResources().updateConfiguration(config,
                appContext.getResources().getDisplayMetrics());

//        showToast(" Locale Country = " + locale.getDisplayLanguage());
    
    }
    
    public void changeLanguage() {
        Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            appContext.createConfigurationContext(config);
        }
//        appContext.getApplicationContext().getResources().updateConfiguration(config,
//                appContext.getApplicationContext().getResources().getDisplayMetrics());
    }
    
    public void clearSession() {
        
        Preferences.setSharedPreferenceBoolean(appContext, IS_LOGIN, false);
        Preferences.setSharedPreferenceString(appContext, CUSTOMER_ID_KEY, DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(appContext, CUSTOMER_EMAIL, DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(appContext, CUSTOMER_FIRST_NAME, DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(appContext, CUSTOMER_LAST_NAME, DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(appContext, UNIQUE_ID_KEY, DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(appContext, CUSTOMER_CONTACT, DEFAULT_STRING_VAL);
        ((MainActivity) mContext).counterTV.setText("0");
        Preferences.setSharedPreferenceString(appContext, "couponCode", "");
        
    }
    
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
    
    public void getUniqueId() {
        
        String uniqueId = Preferences.getSharedPreferenceString(appContext,
                UNIQUE_ID_KEY, DEFAULT_STRING_VAL);
        if (uniqueId.isEmpty() || uniqueId.equals("")) {
            Preferences.setSharedPreferenceString(appContext, UNIQUE_ID_KEY, generateUniqueId());
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
        editText.setError(findStringByName("required"));
        editText.requestFocus();
    }
    
    public void applyAccentColor(TextView textView) {
        String accentColor = Preferences.getSharedPreferenceString(
                appContext, ACCENT_COLOR, "#EC7625");
        
        if (!accentColor.isEmpty())
            textView.setTextColor(Color.parseColor(accentColor));
        
    }
    
    public void applyAccentColor(Button button) {
        String accentColor = Preferences.getSharedPreferenceString(
                appContext, ACCENT_COLOR, "#EC7625");
        
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
            AndroidNetworking.post(getApiCallUrl())
                    .addBodyParameter("coupon", couponCode)
                    .addBodyParameter("session_id",
                            Preferences.getSharedPreferenceString(mContext,
                                    UNIQUE_ID_KEY, DEFAULT_STRING_VAL))
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
                                Preferences.setSharedPreferenceString(appContext
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
        
        boolean isLoggedIn = Preferences.getSharedPreferenceBoolean(appContext, IS_LOGIN, false);
        printLog("IsLoggedIn = ", "" + isLoggedIn);
        
        return isLoggedIn;
    }
    
    public void setItemCount() {
        AppConstants.setMidFixApi("countProducts");
        printLog("Url = ", getApiCallUrl());
        AndroidNetworking.post(getApiCallUrl())
                .addBodyParameter("session_id", Preferences.getSharedPreferenceString(
                        appContext,
                        UNIQUE_ID_KEY,
                        DEFAULT_STRING_VAL))
                .addBodyParameter("customer_id", Preferences.getSharedPreferenceString(
                        appContext,
                        CUSTOMER_ID_KEY,
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
    
    
    public interface ClickInterface {
        
        void OnItemClicked(int item_id);
    }
    
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
        
        if (theme != null && !theme.isEmpty() &&
                !theme.equalsIgnoreCase("default")) {
            titleTV.setTextColor(Color.parseColor(pColor));
            separator.setBackgroundColor(Color.parseColor(pColor));
            buttonPositive.setBackgroundColor(Color.parseColor(pColor));
            buttonNegative.setBackgroundColor(Color.parseColor(aColor));
        } else {
            String pColor = "#EC7625";
            String aColor = "#555555";
            titleTV.setTextColor(Color.parseColor(pColor));
            separator.setBackgroundColor(Color.parseColor(pColor));
            buttonPositive.setBackgroundColor(Color.parseColor(pColor));
            buttonNegative.setBackgroundColor(Color.parseColor(aColor));
        }
        
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
        
        
        if (theme != null && !theme.isEmpty() &&
                !theme.equalsIgnoreCase("default")) {
            
            titleTV.setTextColor(Color.parseColor(pColor));
            separator.setBackgroundColor(Color.parseColor(pColor));
            buttonPositive.setBackgroundColor(Color.parseColor(pColor));
            buttonNegative.setBackgroundColor(Color.parseColor(aColor));
        } else {
            String pColor = "#EC7625";
            String aColor = "#555555";
            titleTV.setTextColor(Color.parseColor(pColor));
            separator.setBackgroundColor(Color.parseColor(pColor));
            buttonPositive.setBackgroundColor(Color.parseColor(pColor));
            buttonNegative.setBackgroundColor(Color.parseColor(aColor));
        }
        
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
    
    public void setTheme(Context c) {
        String themeCode = Preferences.getSharedPreferenceString(
                appContext, THEME_CODE, "default");
        
        Resources resources = c.getResources();
        
        Log.e("UtilsSetTheme", "ThemeCode = " + themeCode);
        switch (themeCode) {
            case "red": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryRed);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentRed);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialRed);
                break;
            }
            case "pink": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryPink);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentPink);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialPink);
                break;
            }
            case "purple": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryPurple);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentPurple);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialPurple);
                break;
            }
            case "deep_purple": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryDeepPurple);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentDeepPurple);
                Preferences.setSharedPreferenceString(appContext, pColor, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialDeepPurple);
                break;
            }
            case "indigo": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryIndigo);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentIndigo);
                Preferences.setSharedPreferenceString(appContext, pColor, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialIndigo);
                break;
            }
            case "blue":
            case "light_blue": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryBlue);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentBlue);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialBlue);
                break;
            }
            case "cyan": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryCyan);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentCyan);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialCyan);
                break;
            }
            case "teal": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryTeal);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentTeal);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialTeal);
                break;
            }
            case "green": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryGreen);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentGreen);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialGreen);
                break;
            }
            case "light_green": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryLightGreen);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentLightGreen);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialLightGreen);
                break;
            }
            case "lime": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryLime);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentLime);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialLime);
                break;
            }
            case "yellow": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryYellow);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentYellow);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialYellow);
                break;
            }
            case "amber": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryAmber);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentAmber);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialAmber);
                break;
            }
            case "orange": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryOrange);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentOrange);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialOrange);
                break;
            }
            case "deep_orange": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryDeepOrange);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentDeepOrange);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialDeepOrange);
                break;
            }
            case "brown": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryBrown);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentBrown);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialBrown);
                break;
            }
            case "grey": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryGrey);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentGrey);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialGrey);
                break;
            }
            case "blue_grey": {
                @SuppressLint("ResourceType")
                String pColor = resources.getString(R.color.colorPrimaryBlueGrey);
                @SuppressLint("ResourceType")
                String aColor = resources.getString(R.color.colorAccentBlueGrey);
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, pColor);
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, aColor);
                c.setTheme(R.style.MaterialBlueGrey);
                break;
            }
            default:
                printLog("MainAct", "Default Running");
                Preferences.setSharedPreferenceString(appContext, PRIMARY_COLOR, "#ffffff");
                Preferences.setSharedPreferenceString(appContext, ACCENT_COLOR, "#EC7625");
                c.setTheme(R.style.AppTheme_NoActionBar);
                break;
        }

//        setDefaultNightMode(MODE_NIGHT_YES);
    }
    
}
