package com.qemasoft.alhabibshop.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qemasoft.alhabibshop.app.controller.MyPagerAdapter;
import com.qemasoft.alhabibshop.app.model.ProductOptionValueItem;
import com.qemasoft.alhabibshop.app.model.Slideshow;
import com.qemasoft.alhabibshop.app.view.activities.MainActivity;

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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.relex.circleindicator.CircleIndicator;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_EMAIL;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_NAME;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.ITEM_COUNTER;
import static com.qemasoft.alhabibshop.app.AppConstants.LANGUAGE_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.LOGIN_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.UNIQUE_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;

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
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null) && netInfo.isConnected();
    }

    public void showInternetErrorDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("An Error Occurred")
                .setMessage("No Internet Connection")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void showErrorDialog(String errorMsg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("An Error Occurred!")
                .setMessage(errorMsg)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void showAlertDialog(String title, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public AlertDialog showAlertDialogReturnDialog(String title, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title)
                .setMessage(msg);

        // Create the AlertDialog object and return it

        return builder.create();
    }

    public void showAlertDialogTurnWifiOn() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Enable Internet")
                .setMessage("It seems you have no internet connection, To turn on wifi press Wifi" +
                        " or Goto settings")
                .setPositiveButton("Wifi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        WifiManager wifiManager = (WifiManager) mContext.getApplicationContext()
                                .getSystemService(Context.WIFI_SERVICE);
                        if (!wifiManager.isWifiEnabled()) {
                            wifiManager.setWifiEnabled(true);
//                        } else if (wifiManager.isWifiEnabled()) {
//                            wifiManager.setWifiEnabled(false);
                        }
                    }
                })
                .setNegativeButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ((AppCompatActivity) mContext).startActivityForResult(
                                new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

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
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                button.setHint(list.get(position));
                if (clickInterface != null)
                    clickInterface.OnItemClicked(position);
                printLog("Which", "List position =" + list.get(position));
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
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
                LANGUAGE_KEY, "en");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        appContext.getResources().updateConfiguration(config,
                appContext.getResources().getDisplayMetrics());

//        showToast(" Locale Country = " + locale.getDisplayLanguage());

    }

    public void clearSession() {

        Preferences.setSharedPreferenceBoolean(appContext, LOGIN_KEY, false);
        Preferences.setSharedPreferenceString(appContext, CUSTOMER_KEY, DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(appContext, CUSTOMER_EMAIL, DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(appContext, CUSTOMER_NAME, DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceString(appContext, UNIQUE_ID_KEY, DEFAULT_STRING_VAL);
        Preferences.setSharedPreferenceInt(appContext, ITEM_COUNTER, 0);

    }

    public void showToast(String msg) {

        Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
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
        final int time = 4000;
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

    public String getUniqueId() {

        String uniqueId = UUID.randomUUID().toString();
        String subStrId = uniqueId.substring(0, 24);
        printLog("UniqueId", subStrId);
        return subStrId;
    }

    public void sendAppMsg(View view) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        mContext.startActivity(sendIntent);
    }

    public void setError(EditText editText) {

        editText.setError("Required!");
        editText.requestFocus();
    }

    public void switchFragment(Fragment fragment) {

        ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragments, fragment).commit();
    }

    public void switchFragment(Fragment fragment, Bundle bundle) {

        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragments, fragment).commit();
    }

    public void removeDuplicates(List<?> list) {

        Set hs = new HashSet<>(list);
        list.clear();
        list.addAll(hs);

        ProductOptionValueItem item = (ProductOptionValueItem) list.get(0);
        printLog("Parent Id = " + item.getOptionValueId() + " Child Id = " + item.getName()
                + " List Size = " + list.size());

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

    public interface ClickInterface {

        void OnItemClicked(int item_id);
    }

}
