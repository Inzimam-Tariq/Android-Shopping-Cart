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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import hostflippa.com.opencart_android.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private Context context;
    private SearchView searchView;
    private MenuItem shoppingCart;
    private DrawerLayout drawer;
    private NavigationView navigationView, navigationView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(toolbar);
        this.context = this;
        decorateToolbar();
//        setupSearchView();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView2.setNavigationItemSelectedListener(this);
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
        navigationView = (NavigationView) findViewById(R.id.nav_view);
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
            shoppingCart.setVisible(false);
            shoppingCart.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM |
                    MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            invalidateOptionsMenu();

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

        } else if (id == R.id.nav_child) {
//            changeFragment(0);
        } else if (id == R.id.nav_sports) {
//            changeFragment(1);
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
            changeFragment(106);
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

}
