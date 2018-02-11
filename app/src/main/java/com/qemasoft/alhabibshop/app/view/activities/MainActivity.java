package com.qemasoft.alhabibshop.app.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.controller.ExpandableListAdapterCategory;
import com.qemasoft.alhabibshop.app.controller.ExpandableListAdapterUser;
import com.qemasoft.alhabibshop.app.model.MenuCategory;
import com.qemasoft.alhabibshop.app.model.MenuSubCategory;
import com.qemasoft.alhabibshop.app.model.UserSubMenu;
import com.qemasoft.alhabibshop.app.view.fragments.Dashboard;
import com.qemasoft.alhabibshop.app.view.fragments.FragCartDetail;
import com.qemasoft.alhabibshop.app.view.fragments.FragChangePassword;
import com.qemasoft.alhabibshop.app.view.fragments.FragCheckout;
import com.qemasoft.alhabibshop.app.view.fragments.FragContactUs;
import com.qemasoft.alhabibshop.app.view.fragments.FragEditAccount;
import com.qemasoft.alhabibshop.app.view.fragments.FragLogin;
import com.qemasoft.alhabibshop.app.view.fragments.FragOrderHistory;
import com.qemasoft.alhabibshop.app.view.fragments.FragProduct;
import com.qemasoft.alhabibshop.app.view.fragments.FragRegister;
import com.qemasoft.alhabibshop.app.view.fragments.FragShowText;
import com.qemasoft.alhabibshop.app.view.fragments.MainFrag;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.qemasoft.alhabibshop.app.AppConstants.CURRENCY_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.CURRENCY_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.CURRENCY_SYMBOL_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.FORCED_CANCEL;
import static com.qemasoft.alhabibshop.app.AppConstants.LANGUAGE_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.LANGUAGE_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.LOGO_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.LOGO_TYPE;
import static com.qemasoft.alhabibshop.app.AppConstants.SEARCH_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;
import static com.qemasoft.alhabibshop.app.AppConstants.findStringByName;

/**
 * Created by Inzimam Tariq on 18/10/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    public static final String KEY_EXTRA = "com.qemasoft.alhabibshop.app" + "getMainScreenExtra";
    
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    
    public TextView counterTV;
    ArrayList<Integer> loggedInIconList = new ArrayList<Integer>() {{
        add(R.drawable.ic_dashboard_black);
        add(R.drawable.ic_edit_black);
        add(R.drawable.ic_vpn_key_black);
        add(R.drawable.ic_add_shopping_cart_black);
        add(R.drawable.ic_exit_to_app_black);
        add(R.drawable.ic_email_black);
        add(R.drawable.ic_language_black);
        add(R.drawable.ic_attach_money_black);
        add(R.drawable.ic_info_outline_black);
    }};
    ArrayList<Integer> NotLoggedInIconList = new ArrayList<Integer>() {{
        add(R.drawable.ic_lock_black);
        add(R.drawable.ic_person_add_black);
        add(R.drawable.ic_email_black);
        add(R.drawable.ic_language_black);
        add(R.drawable.ic_attach_money_black);
        add(R.drawable.ic_info_outline_black);
    }};
    //   Toolbar stuff;
    private ImageView drawerIconCategory, drawerIconUser, searchIcon; // logoIcon;
    private RelativeLayout cartLayout;
    private Context context;
    
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
//    private ImageView closeRightDrawerIV, closeLeftDrawerIV;
    
    private Utils utils;
    
    private ExpandableListView listViewExpCategory, listViewExpUser;
    private ExpandableListAdapterCategory listAdapterCategory;
    private ExpandableListAdapterUser listAdapterUser;
    private List<String> headerListUser;
    private HashMap<String, List<UserSubMenu>> hashMapUser;
    private List<MenuCategory> headerListCategory;
    private HashMap<MenuCategory, List<MenuSubCategory>> hashMapCategory;
    
    
    private RelativeLayout appbarTop, appbarBottom, toolbarLayout;
    private LinearLayout drawerCategory, drawerUser;
    private ImageView myAccountTV, checkoutTV, discountTV, homeTV;
    private TextView siteName, discountedCategoryTV;
    private ImageView logoIcon;
    private int clicks = 0;
    
    
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        this.utils = new Utils(this);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        this.context = this;
        setupToolbar(this);
        utils.getUniqueId();
        utils.switchFragment(new MainFrag());
//        setCompoundDrawable();
        setOnClickListener();
        
        initRightMenuData();
        initLeftMenuData();
        
        listAdapterCategory = new ExpandableListAdapterCategory(headerListCategory, hashMapCategory,
                false);
        
        listViewExpCategory.setAdapter(listAdapterCategory);
        if (utils.isLoggedIn()) {
            listAdapterUser = new ExpandableListAdapterUser(headerListUser, hashMapUser,
                    loggedInIconList);
        } else {
            listAdapterUser = new ExpandableListAdapterUser(headerListUser, hashMapUser,
                    NotLoggedInIconList);
        }
        listViewExpUser.setAdapter(listAdapterUser);
        
        enableSingleSelection();
        setExpandableListViewClickListener();
        setExpandableListViewChildClickListener();
        
        
    }
    
    private void setOnClickListener() {
        
        myAccountTV.setOnClickListener(this);
        checkoutTV.setOnClickListener(this);
        discountTV.setOnClickListener(this);
        homeTV.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        cartLayout.setOnClickListener(this);
//        closeRightDrawerIV.setOnClickListener(this);
//        closeLeftDrawerIV.setOnClickListener(this);
        discountedCategoryTV.setOnClickListener(this);
    }
    
    private void deSetOnClickListener() {
        
        myAccountTV.setOnClickListener(null);
        checkoutTV.setOnClickListener(null);
        discountTV.setOnClickListener(null);
        homeTV.setOnClickListener(null);
        searchIcon.setOnClickListener(null);
        cartLayout.setOnClickListener(null);
//        closeRightDrawerIV.setOnClickListener(null);
//        closeLeftDrawerIV.setOnClickListener(null);
        discountedCategoryTV.setOnClickListener(null);
//        logoIcon.setOnClickListener(null);
        drawerIconCategory.setOnClickListener(null);
        drawerIconUser.setOnClickListener(null);
        listViewExpCategory.setOnClickListener(null);
        listViewExpUser.setOnClickListener(null);
        
    }
    
    
    private void setExpandableListViewClickListener() {
        
        listViewExpUser.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                                        long id) {
                
                utils.printLog("GroupClicked", " Id = " + id);
                int childCount = parent.getExpandableListAdapter().getChildrenCount(groupPosition);
                if (!utils.isLoggedIn()) {
                    if (childCount < 1) {
                        if (groupPosition == 0) {
                            utils.switchFragment(new FragLogin());
                        } else if (groupPosition == 1) {
                            utils.switchFragment(new FragRegister());
                        } else if (groupPosition == 2) {
                            utils.switchFragment(new FragContactUs());
                        } else {
                            utils.switchFragment(new MainFrag());
                        }
                        closeDrawer();
                    }
                } else {
                    if (childCount < 1) {
                        changeFragment(103 + groupPosition);
                        closeDrawer();
                    }
                }
                
                return false;
            }
        });
        
        listViewExpCategory.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                
                int childCount = parent.getExpandableListAdapter().getChildrenCount(groupPosition);
                
                if (childCount < 1) {
                    MenuCategory textChild = (MenuCategory) parent.getExpandableListAdapter()
                            .getGroup(groupPosition);
                    moveToProductFragment(textChild.getMenuCategoryId());
                    utils.printLog("InsideChildClick", "" + textChild.getMenuCategoryId());
                    
                }
                
                return false;
            }
        });
    }
    
    private void closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
    }
    
    private void setExpandableListViewChildClickListener() {
        
        listViewExpCategory.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                
                MenuSubCategory subCategory = (MenuSubCategory) parent.getExpandableListAdapter()
                        .getChild(groupPosition, childPosition);
                
                moveToProductFragment(subCategory.getMenuSubCategoryId());
                utils.printLog("InsideChildClick", "" + subCategory.getMenuSubCategoryId());
                
                return true;
            }
        });
        listViewExpUser.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                
                String str = parent.getExpandableListAdapter().getGroup(groupPosition).toString();
                UserSubMenu userSubMenu = (UserSubMenu) parent.getExpandableListAdapter()
                        .getChild(groupPosition, childPosition);
                if (str.contains("Information") || str.contains("معلومات")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", userSubMenu.getUserSubMenuCode());
                    utils.switchFragment(new FragShowText(), bundle);
                } else if (str.contains("اللغة") || str.contains("Language")) {
                    String language = userSubMenu.getUserSubMenuCode();
                    String lang;
                    if (language.contains("-")) {
                        String langArray[] = language.split("-");
                        lang = langArray[0];
                        utils.printLog("Main", "Language = " + lang);
                    } else lang = language;
                    
                    Preferences.setSharedPreferenceString(appContext,
                            LANGUAGE_KEY, lang);
                    recreate();
                } else if (str.contains("دقة") || str.contains("Currency")) {
                    makeDefaultCurrencyCall(userSubMenu.getUserSubMenuCode());
                }
                utils.printLog("InsideChildClick", "" + userSubMenu.getUserSubMenuCode());
                closeDrawer();
                
                return false;
            }
        });
    }
    
    private void makeDefaultCurrencyCall(String code) {
        
        AppConstants.setMidFixApi("getCurrencyByCode");
        Map<String, String> map = new HashMap<>();
        if (code.isEmpty()) {
            map.put("code", Preferences.getSharedPreferenceString(appContext
                    , CURRENCY_KEY, DEFAULT_STRING_VAL));
        } else {
            map.put("code", code);
        }
        Bundle bundle = new Bundle();
        
        bundle.putBoolean("hasParameters", true);
        bundle.putSerializable("parameters", (Serializable) map);
        Intent intent = new Intent(context, FetchData.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, CURRENCY_REQUEST_CODE);
    }
    
    private void setupToolbar(Context context) {
        
        String toolbarColor = Preferences.getSharedPreferenceString(appContext,
                "toolbar_color", "");
        String drawerColor = Preferences.getSharedPreferenceString(appContext,
                "nav_drawer_color", "");
        String footerColor = Preferences.getSharedPreferenceString(appContext,
                "footer_color", "");
        if (toolbarColor != null && !toolbarColor.isEmpty())
            toolbarLayout.setBackgroundColor(Color.parseColor(toolbarColor));
        if (footerColor != null && !footerColor.isEmpty())
            appbarBottom.setBackgroundColor(Color.parseColor(footerColor));
        if (drawerColor != null && !drawerColor.isEmpty()) {
            drawerUser.setBackgroundColor(Color.parseColor(drawerColor));
            drawerCategory.setBackgroundColor(Color.parseColor(drawerColor));
        }
//        boolean isRightToLeft = TextUtilsCompat.getLayoutDirectionFromLocale(Locale
//                .getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL;
//        int menuType = Preferences.getSharedPreferenceInt(appContext, MENU_TYPE, 0);
//        utils.printLog("MenuType = " + menuType);
//        if (menuType == 1) {
////            ViewCompat.setLayoutDirection(appbarBottom, ViewCompat.LAYOUT_DIRECTION_RTL);
////            ViewCompat.setLayoutDirection(appbarTop, ViewCompat.LAYOUT_DIRECTION_LTR);
//            ViewCompat.setLayoutDirection(drawer, ViewCompat.LAYOUT_DIRECTION_LTR);
//
//        } else {
//            if (Build.VERSION.SDK_INT > JELLY_BEAN_MR1) {
//                ViewCompat.setLayoutDirection(drawer, ViewCompat.LAYOUT_DIRECTION_RTL);
//            }
////            ViewCompat.setLayoutDirection(appbarTop, ViewCompat.LAYOUT_DIRECTION_LTR);
////            ViewCompat.setLayoutDirection(drawerUser, ViewCompat.LAYOUT_DIRECTION_LTR);
////            ViewCompat.setLayoutDirection(drawerCategory, ViewCompat.LAYOUT_DIRECTION_LTR);
//        }
        
        
        utils.setItemCount();

//        int width = getResources().getDisplayMetrics().widthPixels / 2 + 100;
//        DrawerLayout.LayoutParams paramsLeft = (android.support.v4.widget.DrawerLayout.LayoutParams) drawerCategory.getLayoutParams();
//        paramsLeft.width = width;
//        drawerCategory.setLayoutParams(paramsLeft);
//        DrawerLayout.LayoutParams paramsRight = (android.support.v4.widget.DrawerLayout.LayoutParams) drawerUser.getLayoutParams();
//        paramsRight.width = width;
//        drawerUser.setLayoutParams(paramsRight);
        String logoType = Preferences.getSharedPreferenceString(appContext, LOGO_TYPE, "");
        utils.printLog("LogoType = " + logoType);
        String logo = Preferences
                .getSharedPreferenceString(appContext, LOGO_KEY, DEFAULT_STRING_VAL);
        if (logoType.equals("image")) {
            utils.printLog("Product Logo = " + logo);
            if (logo != null && !logo.isEmpty()) {
                Picasso.with(getApplicationContext()).load(logo)
                        .into(logoIcon);
            }
            logoIcon.setOnClickListener(this);
        } else {
            siteName.setText(logo);
        }
        
        
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        actionbarToggle();
        drawer.addDrawerListener(mDrawerToggle);
        
        drawerIconCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                    drawerIconCategory.setScaleX(1);
                    drawerIconCategory.setImageResource(R.drawable.ic_arrow_back_black);
                }
            }
        });
        drawerIconUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });
    }
    
    private void initViews() {
//        toolbar = (Toolbar) findViewById(toolbar);
        drawerIconCategory = findViewById(R.id.drawer_icon_left);
        drawerIconUser = findViewById(R.id.drawer_icon_right);
        logoIcon = findViewById(R.id.logo_icon);
        siteName = findViewById(R.id.site_name);
        searchIcon = findViewById(R.id.search_icon);
        cartLayout = findViewById(R.id.cart_layout);
        counterTV = findViewById(R.id.actionbar_notification_tv);
        
        drawer = findViewById(R.id.drawer_layout);
        listViewExpCategory = findViewById(R.id.category_menu_expandable_lv);
        listViewExpUser = findViewById(R.id.user_menu_expandable_lv);
        
        drawerCategory = findViewById(R.id.category_menu);
        drawerUser = findViewById(R.id.user_menu);
//        closeLeftDrawerIV = findViewById(R.id.close_left_drawer_iv);
//        closeRightDrawerIV = findViewById(R.id.close_right_drawer_iv);
        
        appbarBottom = findViewById(R.id.appbar_bottom);
        appbarTop = findViewById(R.id.appbar_top);
        toolbarLayout = findViewById(R.id.toolbar_layout);
        myAccountTV = findViewById(R.id.my_account_tv);
        discountTV = findViewById(R.id.disc_tv);
        checkoutTV = findViewById(R.id.checkout_tv);
        homeTV = findViewById(R.id.home_tv);
        
        discountedCategoryTV = findViewById(R.id.disc_category_tv);
        
    }
    
    @Override
    public void onBackPressed() {
        
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            clicks++;
            if (clicks == 1) {
                Toast.makeText(context, findStringByName("exit_app_text"),
                        Toast.LENGTH_SHORT).show();
            }
            if (clicks % 2 == 0) {
                super.onBackPressed();
            }
            
        }
    }
    
    public void changeFragment(int position) {
        
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new Fragment();
        
        switch (position) {
            
            case 103:
                if (utils.isLoggedIn())
                    utils.switchFragment(new Dashboard());
                else utils.switchFragment(new FragLogin());
                break;
            case 104:
                fragment = new FragEditAccount();
                break;
            case 105:
                fragment = new FragChangePassword();
                break;
            case 106:
                fragment = new FragOrderHistory();
                break;
            case 107:
                // Clear Login Session
                utils.clearSession();
                utils.switchFragment(new MainFrag());
                recreate();
                break;
            case 108:
                fragment = new FragContactUs();
//                appbarBottom.setVisibility(View.GONE);
                break;
            
        }
        
        transaction.replace(R.id.flFragments, fragment);
        transaction.commit();
        
    }
    
    private void initRightMenuData() {
        
        headerListUser = new ArrayList<>();
        hashMapUser = new HashMap<>();
        
        String[] notLoggedInMenu = {findStringByName("login_text"),
                findStringByName("action_register_text"),
                findStringByName("contact_us_text")};
        String[] loggedInMenu = {findStringByName("account"), findStringByName("edit_account_text"),
                findStringByName("action_change_pass_text"),
                findStringByName("order_history_text"),
                findStringByName("logout"), findStringByName("contact_us_text")};
        
        List<UserSubMenu> userSubMenusList = new ArrayList<>();
        if (utils.isLoggedIn()) {
            for (int i = 0; i < loggedInMenu.length; i++) {
                headerListUser.add(loggedInMenu[i]);
                hashMapUser.put(headerListUser.get(i), userSubMenusList);
            }
        } else {
            for (int i = 0; i < notLoggedInMenu.length; i++) {
                headerListUser.add(notLoggedInMenu[i]);
                hashMapUser.put(headerListUser.get(i), userSubMenusList);
                
            }
        }
        String responseStr = "";
        if (getIntent().hasExtra(KEY_EXTRA)) {
            responseStr = getIntent().getStringExtra(KEY_EXTRA);
            utils.printLog("ResponseInInitData", responseStr);
            try {
                JSONObject responseObject = new JSONObject(responseStr);
                boolean success = responseObject.optBoolean("success");
                if (success) {
                    try {
                        JSONObject homeObject = responseObject.getJSONObject("home");
                        JSONArray slideshow = homeObject.optJSONArray("slideshow");
                        AppConstants.setSlideshowExtra(slideshow.toString());
                        JSONArray menuRight = homeObject.optJSONArray("usermenu");
                        
                        for (int z = 0; z < menuRight.length(); z++) {
                            List<UserSubMenu> userSubMenuList = new ArrayList<>();
                            JSONObject object = menuRight.getJSONObject(z);
                            headerListUser.add(object.optString("name"));
                            JSONArray childArray = object.optJSONArray("children");
                            
                            for (int y = 0; y < childArray.length(); y++) {
                                JSONObject obj = childArray.optJSONObject(y);
                                userSubMenuList.add(new UserSubMenu(obj.optString("code"),
                                        obj.optString("title"),
                                        obj.optString("symbol_left"),
                                        obj.optString("symbol_right"),
                                        obj.optString("image")));
                            }
                            hashMapUser.put(headerListUser.get(headerListUser.size() - 1), userSubMenuList);
                            utils.printLog("AfterHashMap", "" + hashMapUser.size());
                        }
                        
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utils.showErrorDialog("Error Getting Data From Server");
                    utils.printLog("SuccessFalse", "Within getCategories");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                utils.printLog("JSONObjEx_MainAct", responseStr);
            }
        } else {
            utils.printLog("ResponseExMainActivity", responseStr);
            throw new IllegalArgumentException("Activity cannot find  extras " + KEY_EXTRA);
        }
        
    }
    
    private void enableSingleSelection() {
        
        listViewExpCategory.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            
            @Override
            public void onGroupExpand(int groupPosition) {
                
                if (groupPosition != previousGroup)
                    listViewExpCategory.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
        listViewExpUser.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            
            @Override
            public void onGroupExpand(int groupPosition) {
                
                if (groupPosition != previousGroup)
                    listViewExpUser.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
    }
    
    private void initLeftMenuData() {
        
        headerListCategory = new ArrayList<>();
        hashMapCategory = new HashMap<>();
        
        String responseStr = "";
        if (getIntent().hasExtra(KEY_EXTRA)) {
            responseStr = getIntent().getStringExtra(KEY_EXTRA);
            utils.printLog("ResponseInMainActivity", responseStr);
            try {
                JSONObject responseObject = new JSONObject(responseStr);
                utils.printLog("JSON_Response", "" + responseObject);
                boolean success = responseObject.optBoolean("success");
                if (success) {
                    try {
                        JSONObject homeObject = responseObject.getJSONObject("home");
                        
                        JSONArray menuCategories = homeObject.optJSONArray("categoryMenu");
                        utils.printLog("Categories", menuCategories.toString());
                        for (int i = 0; i < menuCategories.length(); i++) {
                            
                            JSONObject menuCategoryObj = menuCategories.getJSONObject(i);
                            JSONArray menuSubCategoryArray = menuCategoryObj.optJSONArray(
                                    "children");
                            
                            List<MenuSubCategory> childMenuList = new ArrayList<>();
                            for (int j = 0; j < menuSubCategoryArray.length(); j++) {
                                JSONObject menuSubCategoryObj = menuSubCategoryArray.getJSONObject(j);
                                MenuSubCategory menuSubCategory = new MenuSubCategory(
                                        menuSubCategoryObj.optString("child_id"),
                                        menuSubCategoryObj.optString("name"));
                                childMenuList.add(menuSubCategory);
                            }
                            MenuCategory menuCategory = new MenuCategory(menuCategoryObj.optString(
                                    "category_id"), menuCategoryObj.optString("name"),
                                    menuCategoryObj.optString("icon"), childMenuList);
                            headerListCategory.add(menuCategory);
                            hashMapCategory.put(headerListCategory.get(i), menuCategory.getMenuSubCategory());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utils.showErrorDialog(findStringByName("error_fetching_data"));
                    utils.printLog("SuccessFalse", "Within getCategories");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                utils.printLog("JSONObjEx_MainAct", responseStr);
            }
        } else {
            utils.printLog("ResponseExMainActivity", responseStr);
            throw new IllegalArgumentException("Activity cannot find  extras " + KEY_EXTRA);
        }
    }
    
    private void actionbarToggle() {
        
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                
                super.onDrawerClosed(view);
                drawerIconCategory.setImageResource(R.drawable.ic_list_black);
                drawerIconCategory.setScaleX(-1);
            }
            
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                
                super.onDrawerOpened(drawerView);
            }
        };
    }
    
    private void moveToProductFragment(String id) {
        
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        utils.switchFragment(new FragProduct(), bundle);
        
        closeDrawer();
    }

//    private void setCompoundDrawable() {
//
//        utils.setCompoundDrawable(myAccountTV, "top", R.drawable.ic_person_black);
//        utils.setCompoundDrawable(checkoutTV, "top", R.drawable.ic_shopping_cart_black);
//        utils.setCompoundDrawable(discountTV, "top", R.drawable.ic_tag_black);
//        utils.setCompoundDrawable(homeTV, "top", R.drawable.ic_home_black);
//
//    }
    
    @Override
    public void onClick(View v) {
        
        int id = v.getId();
        
        if (id == R.id.logo_icon) {
            utils.switchFragment(new MainFrag());
            recreate();
//        } else
//        if (id == R.id.close_right_drawer_iv) {
//            drawer.closeDrawer(GravityCompat.END);
//            drawer.closeDrawer(GravityCompat.START);
//        } else if (id == R.id.close_left_drawer_iv) {
//            drawer.closeDrawer(GravityCompat.END);
//            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.disc_category_tv) {
            Bundle bundle = new Bundle();
            bundle.putString("from", "mainActivity");
            utils.switchFragment(new FragProduct(), bundle);
            closeDrawer();
        } else if (id == R.id.my_account_tv) {
            if (utils.isLoggedIn())
                utils.switchFragment(new Dashboard());
            else utils.switchFragment(new FragLogin());
        } else if (id == R.id.disc_tv) {
            utils.printLog("From = Main Act");
            Bundle bundle = new Bundle();
            bundle.putString("from", "mainActivity");
            utils.switchFragment(new FragProduct(), bundle);
            
        } else if (id == R.id.checkout_tv) {
            
            if (utils.isLoggedIn()) {
                utils.switchFragment(new FragCheckout());
            } else {
                AlertDialog alertDialog = utils.showAlertDialogReturnDialog(
                        findStringByName("continue_text"),
                        findStringByName("please_select"));
//
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                        findStringByName("login_text"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                
                                utils.switchFragment(new FragLogin());
                            }
                        });
                alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL,
                        findStringByName("action_register_text"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                
                                utils.switchFragment(new FragRegister());
                            }
                        });
                alertDialog.show();
            }
        } else if (id == R.id.home_tv) {
            utils.switchFragment(new MainFrag());
            recreate();
            //            utils.switchFragment(new LoginMaterial());
        } else if (id == R.id.search_icon) {
            startActivityForResult(new Intent(context, SearchActivity.class), SEARCH_REQUEST_CODE);
        } else if (id == R.id.cart_layout) {
            Bundle bundle = new Bundle();
            bundle.putString("midFix", "cartProducts");
            utils.switchFragment(new FragCartDetail(), bundle);
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == SEARCH_REQUEST_CODE || requestCode == CURRENCY_REQUEST_CODE
                || requestCode == LANGUAGE_REQUEST_CODE) {
            if (data != null) {
                String responseStr = data.getStringExtra("result");
                utils.printLog("ResponseIs = " + responseStr);
                
                if (responseStr != null) {
                    if (resultCode == Activity.RESULT_OK) {
                        JSONObject response;
                        if (!isJSONString(responseStr)) {
                            try {
                                response = new JSONObject(responseStr);
                                if (requestCode == CURRENCY_REQUEST_CODE) {
                                    JSONObject object = response.optJSONObject("currency");
                                    Preferences.setSharedPreferenceString(appContext
                                            , CURRENCY_SYMBOL_KEY
                                            , object.optString("symbol_left")
                                                    + object.optString("symbol_right"));
                                    recreate();
                                } else if (requestCode == LANGUAGE_REQUEST_CODE) {
                                    JSONObject object = response.optJSONObject("language");
                                    Preferences.setSharedPreferenceString(appContext
                                            , LANGUAGE_KEY
                                            , object.optString("code")
                                    );
                                    recreate();
                                }
                                
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (requestCode == SEARCH_REQUEST_CODE) {
                                if (responseStr.isEmpty())
                                    return;
                                Bundle bundle = new Bundle();
                                bundle.putString("id", responseStr);
                                bundle.putString("from", "fromSearch");
                                utils.switchFragment(new FragProduct(), bundle);
                            } else {
                                utils.showAlertDialog("Alert", responseStr);
                            }
                        }
                    } else if (resultCode == FORCED_CANCEL) {
                        utils.printLog("WithinSearchResult", "If Success False" + responseStr);
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        utils.printLog("WithinSearchResult", "Result Cancel" + responseStr);
                    }
                }
            }
        }
    }
    
    private boolean isJSONString(String data) {
        
        try {
            Object json = new JSONTokener(data).nextValue();
            return !(json instanceof JSONObject);
        } catch (JSONException e) {
            return true;
        }
    }
    
}