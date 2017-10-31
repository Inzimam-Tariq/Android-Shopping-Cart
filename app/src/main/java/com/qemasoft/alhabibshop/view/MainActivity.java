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
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qemasoft.alhabibshop.Utils;
import com.qemasoft.alhabibshop.model.MenuCategory;
import com.qemasoft.alhabibshop.model.MenuSubCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hostflippa.com.opencart_android.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String KEY_EXTRA = "com.qemasoft.alhabibshop.shopping_cart" + "menuCategoriesData";

    private Toolbar toolbar;
    private Context context;
    private SearchView searchView;
    private MenuItem shoppingCart;
    private DrawerLayout drawer;
    private NavigationView navigationView, navigationView2;
    private Utils utils;

    // code from Main2Activity
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> dataListHeader;
    private HashMap<String, List<String>> listHashMap;
    private List<String> headerList;
    private HashMap<String, List<MenuSubCategory>> childList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        this.context = this;
        this.utils = new Utils(this);
        setSupportActionBar(toolbar);
        decorateToolbar();

        loadData();
//        Log.e("DataLoadingMethodCalled","Success");
//        setupSearchView();

        initData();
        listAdapter = new ExpandableListAdapter(this, headerList, childList);
        listView.setAdapter(listAdapter);
        enableSingleSelection();
        setExpandableListViewClickListener();
        setExpandableListViewChildClickListener();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        navigationView.setNavigationItemSelectedListener(this);
        navigationView2.setNavigationItemSelectedListener(this);
    }

    private void setExpandableListViewClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void setExpandableListViewChildClickListener() {
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                Log.e("InsideChildClick", " GP = " + groupPosition + " CP = " + childPosition);
                changeFragment(1);

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

//        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
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
        listView = (ExpandableListView) findViewById(R.id.lvExp);
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView2 = (NavigationView) findViewById(R.id.nav_view2);

        searchView = (SearchView) findViewById(R.id.search_view);
    }

    @Override
    public void onBackPressed() {
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                shoppingCart.setVisible(false);
                shoppingCart.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM |
                        MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                invalidateOptionsMenu();
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
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            case 101:
                fragment = new FragLogin();
                break;
            case 102:
                fragment = new FragRegister();
                break;
            case 103:
                fragment = new FragEditAccount();
                break;
            case 104:
                fragment = new FragOrderHistory();
                break;
            case 105:
                fragment = new FragForgotPass();
                break;
            case 106:
                fragment = new FragOrderDetail();
                break;
            case 108:
                fragment = new FragAboutUs();
                break;
            case 109:
                fragment = new FragContactUs();
                break;
            case 110:
                fragment = new FragMain();
                break;

        }

        transaction.replace(R.id.flFragments, fragment);
        transaction.commit();
    }

    private void initData() {
        dataListHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        dataListHeader.add("EDMTDev");
        dataListHeader.add("Android");
        dataListHeader.add("Xamarin");
        dataListHeader.add("UWP");
//        dataListHeader.add("EDMTDev");
        List<String> edmtDev = new ArrayList<>();
        edmtDev.add("This is an Expandable ListView");
        List<String> androidStudio = new ArrayList<>();
        androidStudio.add("Expandable ListView");
        androidStudio.add("Google Maps");
        androidStudio.add("Chat Application");
        androidStudio.add("Firebase");
        List<String> xamarin = new ArrayList<>();
        xamarin.add("xamarin Expandable ListView");
        xamarin.add("xamarin Google Maps");
        xamarin.add("xamarin Chat Application");
        xamarin.add("xamarin Firebase");
        List<String> uwp = new ArrayList<>();
        uwp.add("uwp Expandable ListView");
        uwp.add("uwp Google Maps");
        uwp.add("uwp Chat Application");
        uwp.add("uwp Firebase");

        listHashMap.put(dataListHeader.get(0), edmtDev);
        listHashMap.put(dataListHeader.get(1), androidStudio);
        listHashMap.put(dataListHeader.get(2), xamarin);
        listHashMap.put(dataListHeader.get(3), uwp);
    }

    private void enableSingleSelection() {
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    listView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
    }

    private void loadData() {
        headerList = new ArrayList<>();
        childList = new HashMap<>();
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
                            headerList.add(menuCategory.getMenuCategoryName());
                            childList.put(headerList.get(i), menuCategory.getMenuSubCategory());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    utils.showErrorDialog("Error Getting Data From Server");
                    Log.e("SuccessFalse", "Within getCategories");
                }
//                Toast.makeText(context,""+responseObject.optString("success"),Toast.LENGTH_LONG).show();
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