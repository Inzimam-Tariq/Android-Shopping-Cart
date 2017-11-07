package com.qemasoft.alhabibshop.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qemasoft.alhabibshop.Preferences;
import com.qemasoft.alhabibshop.Utils;
import com.qemasoft.alhabibshop.controller.ExpandableListAdapter;
import com.qemasoft.alhabibshop.model.MenuCategory;
import com.qemasoft.alhabibshop.model.MenuSubCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hostflippa.com.opencart_android.R;

import static com.qemasoft.alhabibshop.AppConstants.LOGIN_KEY;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String KEY_EXTRA = "com.qemasoft.alhabibshop.shopping_cart" + "menuCategoriesData";
    ArrayList<Integer> loggedInIconList = new ArrayList<Integer>() {{
        add(R.drawable.ic_dashboard_black);
        add(R.drawable.ic_edit_black);
        add(R.drawable.ic_vpn_key_black);
        add(R.drawable.ic_add_shopping_cart_black);
        add(R.drawable.ic_exit_to_app_black);
        add(R.drawable.ic_language_black);
        add(R.drawable.ic_attach_money_black);
        add(R.drawable.ic_email_black);
        add(R.drawable.ic_more_vert_black);
    }};
    ArrayList<Integer> NotLoggedInIconList = new ArrayList<Integer>() {{
        add(R.drawable.ic_lock_black);
        add(R.drawable.ic_person_add_black);
        add(R.drawable.ic_language_black);
        add(R.drawable.ic_attach_money_black);
        add(R.drawable.ic_email_black);
        add(R.drawable.ic_more_vert_black);
    }};
    private Toolbar toolbar;
    private Context context;
    private SearchView searchView;
    private MenuItem shoppingCart;
    private DrawerLayout drawer;
    private Utils utils;
    // code from Main2Activity
    private ExpandableListView listViewExpLeft, listViewExpRight;
    private ExpandableListAdapter listAdapter;
    private List<String> headerListRight;
    private HashMap<String, List<MenuSubCategory>> hashMapRight;
    private List<String> headerListLeft;
    private HashMap<String, List<MenuSubCategory>> hashMapLeft;
    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        this.context = this;
        this.utils = new Utils(this);
        setSupportActionBar(toolbar);
        decorateToolbar();
        checkIsLoggedIn();


        loadData();
//        Log.e("DataLoadingMethodCalled","Success");
//        setupSearchView();

        initData();
        listAdapter = new ExpandableListAdapter(headerListLeft, hashMapLeft,
                false, isLoggedIn, loggedInIconList);
        listViewExpLeft.setAdapter(listAdapter);
        if (isLoggedIn) {
            listAdapter = new ExpandableListAdapter(headerListRight, hashMapRight,
                    true, isLoggedIn, loggedInIconList);
        } else {
            listAdapter = new ExpandableListAdapter(headerListRight, hashMapRight,
                    true, isLoggedIn, NotLoggedInIconList);
        }
        listViewExpRight.setAdapter(listAdapter);

        enableSingleSelection();
        setExpandableListViewClickListener();
        setExpandableListViewChildClickListener();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        navigationView2.setNavigationItemSelectedListener(this);
    }

    private void checkIsLoggedIn() {
        isLoggedIn = Preferences.getSharedPreferenceBoolean(getApplicationContext(), LOGIN_KEY, false);

    }
    private void clearLoginSession() {
        Preferences.setSharedPreferenceBoolean(getApplicationContext(), LOGIN_KEY, false);
        isLoggedIn = Preferences.getSharedPreferenceBoolean(getApplicationContext(), LOGIN_KEY, false);

    }

    private void setExpandableListViewClickListener() {
        listViewExpRight.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                                        long id) {

                Log.e("GroupClicked", " Id = " + id);

//                int count = listAdapter.getChildrenCount(groupPosition);
                int childCount = parent.getExpandableListAdapter().getChildrenCount(groupPosition);
                if (!isLoggedIn) {
                    if (childCount < 1) {
                        if (groupPosition == 0) {
                            changeFragment(101);
                        } else if (groupPosition == 1) {
                            changeFragment(102);
                        } else if (groupPosition == 2) {
                            changeFragment(201);
                        } else if (groupPosition == 3) {
//                            changeFragment(103);
                        } else if (groupPosition == 4) {
                            changeFragment(109);
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
    }

    private void setExpandableListViewChildClickListener() {
        listViewExpLeft.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                Log.e("InsideChildClick", " GP = " + groupPosition + " CP = " + childPosition);
                changeFragment(5);

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void decorateToolbar() {
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("Al Habib");
        changeFragment(0);
//        toolbar.setTitleTextAppearance();
//        toolbar.setLogo(R.drawable.logo_trans);

//        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
//                R.drawable.ic_dots_vertical);
//        toolbar.setOverflowIcon(drawable);
    }

    private void setupSearchView() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(context, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(context, newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        listViewExpLeft = (ExpandableListView) findViewById(R.id.expandable_lv_left);
        listViewExpRight = (ExpandableListView) findViewById(R.id.expandable_lv_right);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        return false;
        getMenuInflater().inflate(R.menu.main, menu);

        shoppingCart = menu.findItem(R.id.action_cart);

        // Start Adding Notification counter functionality
        MenuItemCompat.setActionView(shoppingCart, R.layout.actionbar_badge_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(shoppingCart);
        TextView tv = notifCount.findViewById(R.id.actionbar_notifcation_textview);
        tv.setText("10");
        notifCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                        Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
            }
        });
        // End Notification counter functionality

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Toast.makeText(context, "Cart Clicked", Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_overflow) {
            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                drawer.openDrawer(GravityCompat.END);
            }
            return true;
        }
        drawer.closeDrawer(GravityCompat.START);
        drawer.closeDrawer(GravityCompat.END);

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean isRight = false;

        if (id == R.id.nav_women) {
// Handle the camera action
            changeFragment(0);
        } else if (id == R.id.nav_men) {
            changeFragment(3);
        } else if (id == R.id.nav_child) {
            changeFragment(4);
        } else if (id == R.id.nav_sports) {
//            changeFragment(5);
            startActivity(new Intent(context, Main2Activity.class));
        } else if (id == R.id.nav_electronics) {
//            changeFragment(2);
        } else if (id == R.id.nav_gifts) {
            startActivity(new Intent(context, ShowItems.class));
        } else if (id == R.id.nav_home) {

        } else if (id == R.id.nav_login) {
            changeFragment(101);
            isRight = true;
        } else if (id == R.id.nav_register) {
            changeFragment(102);
            isRight = true;
        } else if (id == R.id.nav_edit_account) {
            changeFragment(103);
            isRight = true;
        } else if (id == R.id.nav_order_history) {
            changeFragment(104);
            isRight = true;
        } else if (id == R.id.nav_password) {
            changeFragment(105);
            isRight = true;
        } else if (id == R.id.nav_language) {

            isRight = true;
        } else if (id == R.id.nav_currency) {
            changeFragment(107);
            isRight = true;
        } else if (id == R.id.nav_about_us) {
            changeFragment(108);
            isRight = true;
        } else if (id == R.id.nav_contact_us) {
            changeFragment(109);
            isRight = true;
        } else if (id == R.id.nav_logout) {
            changeFragment(110);
            isRight = true;
        }

        if (isRight) {
            drawer.closeDrawer(GravityCompat.END);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new Fragment();

        switch (position) {
            case 0:
                fragment = new FragMain();
                break;
            case 1:
                fragment = new FragPrivacyPolicy();
                break;
            case 2:
                fragment = new SettingsFragment();
                break;
            case 3:
                fragment = new FragDummy();
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
                fragment = new FragForgotPass();
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
                fragment = new FragMain();
                break;
            case 109:
                fragment = new FragMain();
                break;
            case 110:
                fragment = new FragContactUs();
                break;
            case 111:
                fragment = new FragAboutUs();
                break;
            case 112:
                fragment = new FragOrderDetail();
                break;
            case 201:
                fragment = new FragSlider();
                break;
        }

        transaction.replace(R.id.flFragments, fragment);
        transaction.commit();
    }

    private void initData() {
        headerListRight = new ArrayList<>();
        hashMapRight = new HashMap<>();

        List<MenuSubCategory> menuSubCategories = new ArrayList<>();
        menuSubCategories.add(new MenuSubCategory("1", "About Us"));
        menuSubCategories.add(new MenuSubCategory("2", "Privacy Policy"));
        menuSubCategories.add(new MenuSubCategory("3", "Child three"));
        menuSubCategories.add(new MenuSubCategory("4", "Child Four"));

        String[] notLoggedInMenu = {"Login", "Register",
                "Language", "Currency", "Contact Us", "Information..."};
        String[] loggedInMenu = {"Dashboard", "Edit Account", "Change Password", "Order History", "Logout",
                "Language", "Currency", "Contact Us", "Information..."};

        if (isLoggedIn) {
            for (int i = 0; i < loggedInMenu.length; i++) {
                headerListRight.add(loggedInMenu[i]);
                if (i < loggedInMenu.length - 1) {
                    hashMapRight.put(headerListRight.get(i), new ArrayList<MenuSubCategory>());
                } else {
                    hashMapRight.put(headerListRight.get(i), menuSubCategories);
                }
            }
        } else {
            for (int i = 0; i < notLoggedInMenu.length; i++) {
                headerListRight.add(notLoggedInMenu[i]);
                if (i < notLoggedInMenu.length - 1) {
                    hashMapRight.put(headerListRight.get(i), new ArrayList<MenuSubCategory>());
                } else {
                    hashMapRight.put(headerListRight.get(i), menuSubCategories);
                    Log.e("WorkingSubListRight", menuSubCategories.toString());
                }
            }
        }

//        Log.e("HashMapEntry", "Data Entered Successfully \n"
//                + menuSubCategories.get(0).getMenuSubCategoryName());
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
    }

    private void loadData() {
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
                    JSONArray menuCategories = responseObject.optJSONArray("menu_categories");
                    Log.e("Categories", menuCategories.toString());
                    for (int i = 0; i < menuCategories.length(); i++) {
                        try {
                            JSONObject menuCategoryObj = menuCategories.getJSONObject(i);
                            JSONArray menuSubCategoryArray = menuCategoryObj.optJSONArray(
                                    "menu_subcategories");

                            List<MenuSubCategory> childMenuList = new ArrayList<>();
                            for (int j = 0; j < menuSubCategoryArray.length(); j++) {
                                JSONObject menuSubCategoryObj = menuSubCategoryArray.getJSONObject(j);
                                MenuSubCategory menuSubCategory = new MenuSubCategory(
                                        menuSubCategoryObj.optString("subcategory_id"),
                                        menuSubCategoryObj.optString("name"));
                                childMenuList.add(menuSubCategory);
                            }
                            MenuCategory menuCategory = new MenuCategory(menuCategoryObj.optString(
                                    "category_id"), menuCategoryObj.optString("name"), childMenuList);
                            headerListLeft.add(menuCategory.getMenuCategoryName());
                            hashMapLeft.put(headerListLeft.get(i), menuCategory.getMenuSubCategory());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
}