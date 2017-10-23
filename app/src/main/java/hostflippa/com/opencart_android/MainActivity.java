package hostflippa.com.opencart_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText searchField;
    TextView backArrow, menuLeft;
    Toolbar toolbar;
    Context context;
    RelativeLayout searchLayout;
    //    SearchView search;
    MenuItem shoppingCart;
    String text;
    DrawerLayout drawer;
    NavigationView navigationView, navigationView2;

    // Data form showItem Activity
    private RecyclerView mRecyclerView, mRecyclerViewCat;
    private ItemAdapter itemAdapter;
    private CategoryAdapter categoryAdapter;
    private List<MyItem> newArrivalList = new ArrayList<>();
    private List<MyCategory> myCategoryList = new ArrayList<>();
    private List<Integer> myCategoryImagesList;// = new ArrayList<>();

    private ViewPager mPager;
    private int currentPage = 0;
    private final Integer[] XMEN= {R.drawable.bannar1,R.drawable.bannar2,R.drawable.bannar3,R.drawable.bannar2};
    private ArrayList<Integer> XMENArray = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(toolbar);
        this.context = this;


//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("Al Habib");
//        toolbar.setTitleTextAppearance();
//        toolbar.setLogo(R.drawable.logo_trans);

//        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
//                R.drawable.ic_dots_vertical);
//        toolbar.setOverflowIcon(drawable);

        setupSlider();
        loadDummyData();
//        loadData();
        setAdaptersAndData();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView2.setNavigationItemSelectedListener(this);
    }


    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView2 = (NavigationView) findViewById(R.id.nav_view2);

        mRecyclerView = (RecyclerView) findViewById(R.id.item_recycler_view);
        mRecyclerViewCat = (RecyclerView) findViewById(R.id.cat_recycler_view);
//        menuLeft = (TextView) findViewById(R.id.menuLeft);
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
//        MenuItem searchView = menu.findItem(R.id.action_search);
//        search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        shoppingCart = menu.findItem(R.id.action_cart);

//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("Clicked", "bla bla bla clicked");
//                shoppingCart.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM |
//                        MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//                Log.e("Clicked", "action performed");
//
//            }
//        });
//
////        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //tabLayout.getTabAt(0).select();
//                Toast.makeText(context, query, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

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
//                Toast.makeText(context, "Clicked!", Toast.LENGTH_LONG).show();
            }
        });
        // End Adding Notification counter functionality

        return super.onCreateOptionsMenu(menu);
//        return false;
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
//        if (id == R.id.action_search) {
//            return false;
//        }
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
//            startActivity(new Intent(context, RecyclerViewTest.class));
        } else if (id == R.id.nav_men) {
//            startActivity(new Intent(context, JSONDataViewer.class));
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
            startActivity(new Intent(context, LoginActivity.class));
            isRight = true;
        } else if (id == R.id.nav_register) {
            startActivity(new Intent(context, RegisterActivity.class));
            isRight = true;
        } else if (id == R.id.nav_wish_list) {

            isRight = true;
        } else if (id == R.id.nav_language) {

            isRight = true;
        } else if (id == R.id.nav_currency) {

            isRight = true;
        } else if (id == R.id.nav_about_us) {
            startActivity(new Intent(context, AboutUs.class));
            isRight = true;
        } else if (id == R.id.nav_contact_us) {
            startActivity(new Intent(context, ContactUs.class));
            isRight = true;
        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (isRight) {
            drawer.closeDrawer(GravityCompat.END);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = new Fragment();

        switch (position) {
            case 0:
                fragment = new LibraryFragment();
                break;
            case 1:
                fragment = new AddBookFragment();
                break;
            case 2:
                fragment = new SettingsFragment();
                break;
        }

        transaction.replace(R.id.flFragments, fragment);
        transaction.commit();
    }

    //
    private void setAdaptersAndData() {

        // for Categories
        Log.e("DataListPopulated", "Data list populated");
        categoryAdapter = new CategoryAdapter(myCategoryList);
//        RecyclerView.LayoutManager mLayoutManagerCat =
//                new LinearLayoutManager(getApplicationContext()
//                        ,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager mLayoutManagerCat =
                new GridLayoutManager(context,2, LinearLayoutManager.VERTICAL,false);
        mRecyclerViewCat.setLayoutManager(mLayoutManagerCat);
        mRecyclerViewCat.setItemAnimator(new DefaultItemAnimator());
        Log.e("SettingAdapter", "Setting Adapter");
        mRecyclerViewCat.setAdapter(categoryAdapter);
        Log.e("AdapterSet", "Adapter Set Success");

        // for Items
        Log.e("DataListPopulated", "Data list populated");
        itemAdapter = new ItemAdapter(newArrivalList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext()
                        ,LinearLayoutManager.HORIZONTAL,false);
//        RecyclerView.LayoutManager mLayoutManager =
//                new GridLayoutManager(context,2,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.e("SettingAdapter", "Setting Adapter");
        mRecyclerView.setAdapter(itemAdapter);
        Log.e("AdapterSet", "Adapter Set Success");

    }

    private void setupSlider() {
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(context,XMENArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

    private void loadDummyData() {

        String Item = "Item ";
        String []price = {"$3000", "$2490", "$4965", "$3000", "$2490", "$4965"};
        String []title = {"Woman", "Shoes", "Man", "Camera", "Clothing", "Child"};
        myCategoryImagesList = new ArrayList<>();
        myCategoryImagesList.add(R.drawable.bed0);
        myCategoryImagesList.add(R.drawable.bed5);
        myCategoryImagesList.add(R.drawable.bed2);
        myCategoryImagesList.add(R.drawable.bed3);
        myCategoryImagesList.add(R.drawable.bed4);
        myCategoryImagesList.add(R.drawable.bed7);
        int itemNo = 1;
        for (int i = 0; i < title.length; i++) {

            MyItem data = new MyItem(""+itemNo, Item + itemNo, price[i]);
            Log.e("Question" + " " + itemNo,
                    "\nQuestion id = " + data.getItemId() +
                            " Question text = " + data.getItemTitle());
            newArrivalList.add(data);
            MyCategory category = new MyCategory(title[i],myCategoryImagesList.get(i));
            myCategoryList.add(category);
            itemNo++;
        }
    }

}
