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
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
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
import com.qemasoft.alhabibshop.app.view.fragments.FragContactUs;
import com.qemasoft.alhabibshop.app.view.fragments.FragEditAccount;
import com.qemasoft.alhabibshop.app.view.fragments.FragForgotPass;
import com.qemasoft.alhabibshop.app.view.fragments.FragLogin;
import com.qemasoft.alhabibshop.app.view.fragments.FragOrderDetail;
import com.qemasoft.alhabibshop.app.view.fragments.FragOrderHistory;
import com.qemasoft.alhabibshop.app.view.fragments.FragProduct;
import com.qemasoft.alhabibshop.app.view.fragments.FragProductDetail;
import com.qemasoft.alhabibshop.app.view.fragments.FragRegister;
import com.qemasoft.alhabibshop.app.view.fragments.FragShowText;
import com.qemasoft.alhabibshop.app.view.fragments.FragSlider;
import com.qemasoft.alhabibshop.app.view.fragments.MainFrag;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.ITEM_COUNTER;
import static com.qemasoft.alhabibshop.app.AppConstants.LANGUAGE_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.LOGIN_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.LOGO_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.MA_GOTO_ITEMS_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;
import static com.qemasoft.alhabibshop.app.AppConstants.findStringByName;
import static com.qemasoft.alhabibshop.app.AppConstants.setProductExtra;


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
    private boolean isLoggedIn = false;


    private RelativeLayout appbarBottom;
    private TextView myAccountTV, cartTV, discountTV, homeTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupDefaultLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        this.context = this;


        setupToolbar(this);
        checkIsLoggedIn();
        changeFragment(0);
        setCompoundDrawable();
        setOnClickListener();

        initRightMenuData();
        initLeftMenuData();

        listAdapter = new ExpandableListAdapter(headerListLeft, hashMapLeft,
                false, loggedInIconList);
        listViewExpLeft.setAdapter(listAdapter);
        if (isLoggedIn) {
            listAdapterRight = new ExpandableListAdapterRight(headerListRight, hashMapRight,
                    true, loggedInIconList);
        } else {
            listAdapterRight = new ExpandableListAdapterRight(headerListRight, hashMapRight,
                    true, NotLoggedInIconList);
        }
        listViewExpRight.setAdapter(listAdapterRight);

        enableSingleSelection();
        setExpandableListViewClickListener();
        setExpandableListViewChildClickListener();
    }

    private void setOnClickListener() {
        myAccountTV.setOnClickListener(this);
        cartTV.setOnClickListener(this);
        discountTV.setOnClickListener(this);
        homeTV.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        cartLayout.setOnClickListener(this);
    }

    private void checkIsLoggedIn() {
        isLoggedIn = Preferences.getSharedPreferenceBoolean(appContext, LOGIN_KEY, false);
        Log.e("IsLoggedIn = ", "" + isLoggedIn);
        int val = Preferences.getSharedPreferenceInt(appContext, ITEM_COUNTER, 0);
        counterTV.setText(String.valueOf(val));
    }

    private void clearLoginSession() {
        Preferences.setSharedPreferenceBoolean(appContext, LOGIN_KEY, false);
        isLoggedIn = Preferences.getSharedPreferenceBoolean(appContext, LOGIN_KEY, false);

    }

    private void setExpandableListViewClickListener() {
        listViewExpRight.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                                        long id) {

                Log.e("GroupClicked", " Id = " + id);
                int childCount = parent.getExpandableListAdapter().getChildrenCount(groupPosition);
                if (!isLoggedIn) {
                    if (childCount < 1) {
                        if (groupPosition == 0) {
                            changeFragment(101);
                        } else if (groupPosition == 1) {
                            changeFragment(102);
                        } else if (groupPosition == 2) {
                            changeFragment(108);
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
                    Log.e("InsideChildClick", "" + textChild.getMenuCategoryId());

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
                Log.e("InsideChildClick", "" + subCategory.getMenuSubCategoryId());

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
                    switchFragment(new FragShowText(), bundle);
                } else if (str.contains("اللغة") || str.contains("Language")) {
                    if (userSubMenu.getUserSubMenuTitle().contains("عربي")) {
                        utils.changeLanguage("ar");
                    } else if ((userSubMenu.getUserSubMenuTitle().contains("English"))) {
                        utils.changeLanguage("en-gb");
                    }

                    recreate();
                }
                Log.e("InsideChildClick", "" + userSubMenu.getUserSubMenuCode());
                drawer.closeDrawer(GravityCompat.END);

                return false;
            }
        });
    }

    private void setupToolbar(Context context) {

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
        drawerIconLeft = (ImageView) findViewById(R.id.drawer_icon_left);
        drawerIconRight = (ImageView) findViewById(R.id.drawer_icon_right);
        logoIcon = (ImageView) findViewById(R.id.logo_icon);
        searchIcon = (ImageView) findViewById(R.id.search_icon);
        cartLayout = (RelativeLayout) findViewById(R.id.cart_layout);
        counterTV = (TextView) findViewById(R.id.actionbar_notification_tv);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        listViewExpLeft = (ExpandableListView) findViewById(R.id.expandable_lv_left);
        listViewExpRight = (ExpandableListView) findViewById(R.id.expandable_lv_right);

        appbarBottom = (RelativeLayout) findViewById(R.id.appbar_bottom);
        myAccountTV = (TextView) findViewById(R.id.my_account_tv);
        discountTV = (TextView) findViewById(R.id.disc_tv);
        cartTV = (TextView) findViewById(R.id.checkout_tv);
        homeTV = (TextView) findViewById(R.id.home_tv);

        searchView = (SearchView) findViewById(R.id.search_view);
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
            case 0:
                fragment = new MainFrag();
                break;
            case 1:
//                fragment = new FragPrivacyPolicy();
                break;
            case 2:
                fragment = new FragProduct();
                break;
            case 3:
                fragment = new FragProductDetail();
                break;
            case 4:
                fragment = new FragCartDetail();
                break;
            case 5:
                fragment = new FragCategories();
                break;
            case 101:
                fragment = new FragLogin();
                break;
            case 102:
                fragment = new FragRegister();
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
                clearLoginSession();
                recreate();
                break;
            case 108:
                fragment = new FragContactUs();
//                appbarBottom.setVisibility(View.GONE);
                break;
            case 109:
                fragment = new MainFrag();
                break;
            case 110:
                fragment = new MainFrag();
                break;
            case 112:
                fragment = new FragOrderDetail();
                break;
            case 201:
                fragment = new FragSlider();
                break;
            case 202:
                fragment = new FragForgotPass();
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
        if (isLoggedIn) {
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
            Log.e("ResponseInInitData", responseStr);
            try {
                JSONObject responseObject = new JSONObject(responseStr);
                boolean success = responseObject.optBoolean("success");
                if (success) {
                    try {
                        JSONObject homeObject = responseObject.getJSONObject("home");
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
                            Log.e("AfterHashMap", "" + hashMapRight.size());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utils.showErrorDialog("Error Getting Data From Server");
                    Log.e("SuccessFalse", "Within getCategories");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSONObjEx_MainAct", responseStr);
            }
        } else {
            Log.e("ResponseExMainActivity", responseStr);
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
            Log.e("ResponseInMainActivity", responseStr);
            try {
                JSONObject responseObject = new JSONObject(responseStr);
                Log.e("JSON_Response", "" + responseObject);
                boolean success = responseObject.optBoolean("success");
                if (success) {
                    try {
                        JSONObject homeObject = responseObject.getJSONObject("home");

                        JSONArray menuCategories = homeObject.optJSONArray("categoryMenu");
                        Log.e("Categories", menuCategories.toString());
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
                    Log.e("SuccessFalse", "Within getCategories");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSONObjEx_MainAct", responseStr);
            }
        } else {
            Log.e("ResponseExMainActivity", responseStr);
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
        switchFragment(new FragProduct(), bundle);

        drawer.closeDrawer(GravityCompat.START);
        drawer.closeDrawer(GravityCompat.END);
    }

    public void switchFragment(Fragment fragment, Bundle args) {
        FragmentManager manager = getSupportFragmentManager();
        if (args != null) {
            fragment.setArguments(args);
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.flFragments, fragment);
        transaction.commit();
    }

    private void setCompoundDrawable() {
        utils.setCompoundDrawable(myAccountTV, "top", R.drawable.ic_person_black);
        utils.setCompoundDrawable(cartTV, "top", R.drawable.ic_shopping_cart_black);
        utils.setCompoundDrawable(discountTV, "top", R.drawable.ic_tag_black);
        utils.setCompoundDrawable(homeTV, "top", R.drawable.ic_home_black);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.my_account_tv) {
            switchFragment(new Dashboard(), new Bundle());
        } else if (id == R.id.disc_tv) {
//            switchFragment(new FragCartDetail(), new Bundle());
        } else if (id == R.id.checkout_tv) {
            switchFragment(new FragCartDetail(), new Bundle());
        } else if (id == R.id.home_tv) {
            recreate();
        } else if (id == R.id.search_icon) {
            startActivity(new Intent(context, SearchActivity.class));
        } else if (id == R.id.cart_layout) {
            switchFragment(new FragCartDetail(), new Bundle());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MA_GOTO_ITEMS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
                    setProductExtra(response.toString());
//                    switchFragment(new FragProduct());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == AppConstants.FORCED_CANCEL) {
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    Log.e("Res Else", response.toString());
                    String error = response.optString("message");
                    if (!error.isEmpty()) {
                        utils.showErrorDialog(error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.showErrorDialog("Maybe your Internet is too slow, try again");
            }
        }
    }

    private void setupDefaultLanguage() {
        this.utils = new Utils(this);
        String language = Preferences.getSharedPreferenceString(appContext,
                LANGUAGE_KEY, DEFAULT_STRING_VAL);
        utils.changeLanguage(language);
    }

}