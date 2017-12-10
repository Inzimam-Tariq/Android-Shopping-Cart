package com.qemasoft.alhabibshop.app.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.controller.ExpandableListAdapter;
import com.qemasoft.alhabibshop.app.controller.ExpandableListAdapterRight;
import com.qemasoft.alhabibshop.app.model.MenuCategory;
import com.qemasoft.alhabibshop.app.model.MenuSubCategory;
import com.qemasoft.alhabibshop.app.model.UserSubMenu;
import com.qemasoft.alhabibshop.app.view.fragments.Dashboard;
import com.qemasoft.alhabibshop.app.view.fragments.FragCartDetail;
import com.qemasoft.alhabibshop.app.view.fragments.FragCategories;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.FORCED_CANCEL;
import static com.qemasoft.alhabibshop.app.AppConstants.ITEM_COUNTER;
import static com.qemasoft.alhabibshop.app.AppConstants.LANGUAGE_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.LOGIN_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.LOGO_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.SEARCH_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.UNIQUE_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;
import static com.qemasoft.alhabibshop.app.AppConstants.findStringByName;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_EXTRA = "com.qemasoft.alhabibshop.app" + "getMainScreenExtra";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

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
    private ImageView drawerIconLeft, drawerIconRight, logoIcon, searchIcon;
    private RelativeLayout cartLayout;
    private TextView counterTV;


    private Context context;
    private SearchView searchView;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Utils utils;

    private ExpandableListView listViewExpLeft, listViewExpRight;
    private ExpandableListAdapter listAdapter;
    private ExpandableListAdapterRight listAdapterRight;
    private List<String> headerListRight;
    private HashMap<String, List<UserSubMenu>> hashMapRight;
    private List<MenuCategory> headerListLeft;
    private HashMap<MenuCategory, List<MenuSubCategory>> hashMapLeft;
//    private boolean isLoggedIn = false;


    private RelativeLayout appbarBottom;
    private LinearLayout abBottom;
    private TextView myAccountTV, checkoutTV, discountTV, homeTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.utils = new Utils(this);
        utils.changeLanguage("en");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        this.context = this;
        setupToolbar(this);
        checkForUniqueId();
        utils.switchFragment(new MainFrag());
        setCompoundDrawable();
        setOnClickListener();

        initRightMenuData();
        initLeftMenuData();

        listAdapter = new ExpandableListAdapter(headerListLeft, hashMapLeft,
                false, loggedInIconList);
        listViewExpLeft.setAdapter(listAdapter);
        if (isLoggedIn()) {
            listAdapterRight = new ExpandableListAdapterRight(headerListRight, hashMapRight,
                    loggedInIconList);
        } else {
            listAdapterRight = new ExpandableListAdapterRight(headerListRight, hashMapRight,
                    NotLoggedInIconList);
        }
        listViewExpRight.setAdapter(listAdapterRight);

        enableSingleSelection();
        setExpandableListViewClickListener();
        setExpandableListViewChildClickListener();
    }

    private void checkForUniqueId() {
        String uniqueId = Preferences.getSharedPreferenceString(appContext,
                UNIQUE_ID_KEY, DEFAULT_STRING_VAL);
        if (uniqueId.isEmpty() || uniqueId.equals("")) {
            Preferences.setSharedPreferenceString(appContext, UNIQUE_ID_KEY, utils.getUniqueId());
        }
    }

    private void setOnClickListener() {
        myAccountTV.setOnClickListener(this);
        checkoutTV.setOnClickListener(this);
        discountTV.setOnClickListener(this);
        homeTV.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        cartLayout.setOnClickListener(this);
    }

    public boolean isLoggedIn() {
        boolean isLoggedIn = Preferences.getSharedPreferenceBoolean(appContext, LOGIN_KEY, false);
        utils.printLog("IsLoggedIn = ", "" + isLoggedIn);
        int val = Preferences.getSharedPreferenceInt(appContext, ITEM_COUNTER, 0);
        counterTV.setText(String.valueOf(val));


        return isLoggedIn;
    }

    private void setExpandableListViewClickListener() {
        listViewExpRight.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                                        long id) {

                utils.printLog("GroupClicked", " Id = " + id);
                int childCount = parent.getExpandableListAdapter().getChildrenCount(groupPosition);
                if (!isLoggedIn()) {
                    if (childCount < 1) {
                        if (groupPosition == 0) {
                            utils.switchFragment(new FragLogin());
                        } else if (groupPosition == 1) {
                            utils.switchFragment(new FragRegister());
                        } else if (groupPosition == 2) {
                            utils.switchFragment(new FragContactUs());
                        } else {
                            recreate();
                        }
                        drawer.closeDrawer(GravityCompat.END);
                    }
                } else {
                    if (childCount < 1) {
                        changeFragment(103 + groupPosition);
                        drawer.closeDrawer(GravityCompat.END);
                    }
                }

                return false;
            }
        });

        listViewExpLeft.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
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

    private void setExpandableListViewChildClickListener() {
        listViewExpLeft.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
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
        listViewExpRight.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String str = parent.getExpandableListAdapter().getGroup(groupPosition).toString();
                UserSubMenu userSubMenu = (UserSubMenu) parent.getExpandableListAdapter()
                        .getChild(groupPosition, childPosition);
                if (str.contains("Information")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", userSubMenu.getUserSubMenuCode());
                    utils.switchFragment(new FragShowText(), bundle);
                } else if (str.contains("اللغة") || str.contains("Language")) {
                    if (userSubMenu.getUserSubMenuTitle().contains("عربي")
                            || userSubMenu.getUserSubMenuTitle().contains("Arabic")) {
                        Preferences.setSharedPreferenceString(appContext, LANGUAGE_KEY, "ar");
                    } else if ((userSubMenu.getUserSubMenuTitle().contains("English"))) {
                        Preferences.setSharedPreferenceString(appContext, LANGUAGE_KEY, "en");
                    }
                    recreate();
                }
                utils.printLog("InsideChildClick", "" + userSubMenu.getUserSubMenuCode());
                drawer.closeDrawer(GravityCompat.END);

                return false;
            }
        });
    }

    private void setupToolbar(Context context) {

//        boolean isRightToLeft = TextUtilsCompat.getLayoutDirectionFromLocale(Locale
//                .getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL;
//        if (!isRightToLeft) {
//            ViewCompat.setLayoutDirection(appbarBottom, ViewCompat.LAYOUT_DIRECTION_RTL);
//            ViewCompat.setLayoutDirection(abBottom, ViewCompat.LAYOUT_DIRECTION_RTL);
//        }

        Picasso.with(getApplicationContext()).load(Preferences
                .getSharedPreferenceString(appContext, LOGO_KEY, DEFAULT_STRING_VAL))
                .into(logoIcon);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        actionbarToggle();
        drawer.addDrawerListener(mDrawerToggle);

        drawerIconLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                    drawerIconLeft.setScaleX(1);
                    drawerIconLeft.setImageResource(R.drawable.ic_arrow_back_black);
                }
            }
        });
        drawerIconRight.setOnClickListener(new View.OnClickListener() {
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
        drawerIconLeft = findViewById(R.id.drawer_icon_left);
        drawerIconRight = findViewById(R.id.drawer_icon_right);
        logoIcon = findViewById(R.id.logo_icon);
        searchIcon = findViewById(R.id.search_icon);
        cartLayout = findViewById(R.id.cart_layout);
        counterTV = findViewById(R.id.actionbar_notification_tv);

        drawer = findViewById(R.id.drawer_layout);
        listViewExpLeft = findViewById(R.id.expandable_lv_left);
        listViewExpRight = findViewById(R.id.expandable_lv_right);

        appbarBottom = findViewById(R.id.appbar_bottom);
        abBottom = findViewById(R.id.ab_bottom);
        myAccountTV = findViewById(R.id.my_account_tv);
        discountTV = findViewById(R.id.disc_tv);
        checkoutTV = findViewById(R.id.checkout_tv);
        homeTV = findViewById(R.id.home_tv);

        searchView = findViewById(R.id.search_view);
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    public void changeFragment(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new Fragment();

        switch (position) {
            case 2:
                fragment = new FragCategories();
                break;
            case 103:
                fragment = new Dashboard();
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
        headerListRight = new ArrayList<>();
        hashMapRight = new HashMap<>();

        String[] notLoggedInMenu = {findStringByName("login_text"),
                findStringByName("action_register_text"),
                findStringByName("contact_us_text")};
        String[] loggedInMenu = {findStringByName("account"), findStringByName("edit_account_text"),
                findStringByName("action_change_pass_text"),
                findStringByName("order_history_text"),
                findStringByName("logout"), findStringByName("contact_us_text")};

        List<UserSubMenu> userSubMenusList = new ArrayList<>();
        if (isLoggedIn()) {
            for (int i = 0; i < loggedInMenu.length; i++) {
                headerListRight.add(loggedInMenu[i]);
                hashMapRight.put(headerListRight.get(i), userSubMenusList);
            }
        } else {
            for (int i = 0; i < notLoggedInMenu.length; i++) {
                headerListRight.add(notLoggedInMenu[i]);
                hashMapRight.put(headerListRight.get(i), userSubMenusList);

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
                            headerListRight.add(object.optString("name"));
                            JSONArray childArray = object.optJSONArray("children");

                            for (int y = 0; y < childArray.length(); y++) {
                                JSONObject obj = childArray.optJSONObject(y);
                                userSubMenuList.add(new UserSubMenu(obj.optString("code"),
                                        obj.optString("title"), obj.optString("symbol_left"),
                                        obj.optString("symbol_right")));
                            }
                            hashMapRight.put(headerListRight.get(headerListRight.size() - 1), userSubMenuList);
                            utils.printLog("AfterHashMap", "" + hashMapRight.size());
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
        listViewExpLeft.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    listViewExpLeft.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
        listViewExpRight.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    listViewExpRight.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
    }

    private void initLeftMenuData() {
        headerListLeft = new ArrayList<>();
        hashMapLeft = new HashMap<>();

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
                            headerListLeft.add(menuCategory);
                            hashMapLeft.put(headerListLeft.get(i), menuCategory.getMenuSubCategory());
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

    private void actionbarToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                drawerIconLeft.setImageResource(R.drawable.ic_list_black);
                drawerIconLeft.setScaleX(-1);
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

        drawer.closeDrawer(GravityCompat.START);
        drawer.closeDrawer(GravityCompat.END);
    }

    private void setCompoundDrawable() {
        utils.setCompoundDrawable(myAccountTV, "top", R.drawable.ic_person_black);
        utils.setCompoundDrawable(checkoutTV, "top", R.drawable.ic_shopping_cart_black);
        utils.setCompoundDrawable(discountTV, "top", R.drawable.ic_tag_black);
        utils.setCompoundDrawable(homeTV, "top", R.drawable.ic_home_black);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.my_account_tv) {
            utils.switchFragment(new Dashboard());
        } else if (id == R.id.disc_tv) {
//            utils.showNumberPickerDialog();
        } else if (id == R.id.checkout_tv) {
            if (isLoggedIn()) {
                utils.switchFragment(new FragCheckout());
            } else {
                utils.showAlertDialog("Login Alert", "You Need to Login to Proceed");
                utils.switchFragment(new FragLogin());
            }
        } else if (id == R.id.home_tv) {
            recreate();
        } else if (id == R.id.search_icon) {
            startActivityForResult(new Intent(context, SearchActivity.class), SEARCH_REQUEST_CODE);
        } else if (id == R.id.cart_layout) {
            utils.switchFragment(new FragCartDetail());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String responseStr = data.getStringExtra("result");
        utils.printLog("ResponseIsString", "" + responseStr);

        if (responseStr != null) {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SEARCH_REQUEST_CODE) {
                    JSONObject response = null;
                    if (!isJSONString(responseStr)) {
                        try {
                            response = new JSONObject(responseStr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", responseStr);
                        bundle.putBoolean("isFromSearch", true);
                        utils.switchFragment(new FragProduct(), bundle);
                    }
                }
            } else if (resultCode == FORCED_CANCEL) {
                utils.printLog("WithinSearchResult", "If Success False" + responseStr);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.printLog("WithinSearchResult", "Result Cancel" + responseStr);
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