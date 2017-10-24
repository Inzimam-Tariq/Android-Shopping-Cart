package hostflippa.com.opencart_android;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public abstract class CustomBaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    Context context;
//    MenuItem shoppingCart;
//    DrawerLayout drawer;
//    NavigationView navigationView, navigationView2;

    protected void onCreate(Bundle savedInstanceState, int layoutId, Context context) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        initViews();
        this.context = context;

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Al Habib");

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        navigationView.setNavigationItemSelectedListener(this);
//        navigationView2.setNavigationItemSelectedListener(this);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView2 = (NavigationView) findViewById(R.id.nav_view2);
    }

//    @Override
//    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
//            drawer.closeDrawer(GravityCompat.END);
//        } else {
//            super.onBackPressed();
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//
//        shoppingCart = menu.findItem(R.id.action_cart);
//
//        // Start Adding Notification counter functionality
//        MenuItemCompat.setActionView(shoppingCart, R.layout.actionbar_badge_layout);
//        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(shoppingCart);
//        TextView tv = notifCount.findViewById(R.id.actionbar_notifcation_textview);
//        tv.setText("10");
//        notifCount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shoppingCart.setVisible(false);
//                shoppingCart.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM |
//                        MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//                invalidateOptionsMenu();
//            }
//        });
//        // End Adding Notification counter functionality
//
//        return super.onCreateOptionsMenu(menu);
////        return false;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (id == R.id.action_cart) {
//            Toast.makeText(context, "Cart Clicked", Toast.LENGTH_LONG).show();
//            shoppingCart.setVisible(false);
//            shoppingCart.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM |
//                    MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//            invalidateOptionsMenu();
//
//            return true;
//        }
//        if (id == R.id.action_overflow) {
//            if (drawer.isDrawerOpen(GravityCompat.END)) {
//                drawer.closeDrawer(GravityCompat.END);
//            } else {
//                drawer.openDrawer(GravityCompat.END);
//            }
//            return true;
//        }
//        drawer.closeDrawer(GravityCompat.START);
//        drawer.closeDrawer(GravityCompat.END);
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        boolean isRight = false;
//
//        if (id == R.id.nav_women) {
//
//        } else if (id == R.id.nav_men) {
//
//        } else if (id == R.id.nav_child) {
//
//        } else if (id == R.id.nav_sports) {
//
//        } else if (id == R.id.nav_electronics) {
//
//        } else if (id == R.id.nav_gifts) {
//            startActivity(new Intent(context, ShowItems.class));
//        } else if (id == R.id.nav_home) {
//
//        } else if (id == R.id.nav_login) {
//            startActivity(new Intent(context, LoginActivity.class));
//            isRight = true;
//        } else if (id == R.id.nav_register) {
//            startActivity(new Intent(context, RegisterActivity.class));
//            isRight = true;
//        } else if (id == R.id.nav_wish_list) {
//
//            isRight = true;
//        } else if (id == R.id.nav_language) {
//
//            isRight = true;
//        } else if (id == R.id.nav_currency) {
//
//            isRight = true;
//        } else if (id == R.id.nav_about_us) {
//            startActivity(new Intent(context, AboutUs.class));
//            isRight = true;
//        } else if (id == R.id.nav_contact_us) {
//            startActivity(new Intent(context, ContactUs.class));
//            isRight = true;
//        }
//
//        if (isRight) {
//            drawer.closeDrawer(GravityCompat.END);
//        }
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

}