package com.qemasoft.alhabibshop.app.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.qemasoft.alhabibshop.app.R;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    private ExpandableListView listView;
//    private ExpandableListAdapter listAdapter;
//    private List<String> dataListHeader;
//    private HashMap<String, List<String>> listHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        listView = (ExpandableListView) findViewById(R.id.lvExp);
//        initData();
//        listAdapter = new ExpandableListAdapter(this, dataListHeader, listHashMap);
//        listView.setAdapter(listAdapter);
//        enableOnlyOneSelection();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
    }

//    private void enableOnlyOneSelection() {
//        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            int previousGroup = -1;
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if(groupPosition != previousGroup)
//                    listView.collapseGroup(previousGroup);
//                previousGroup = groupPosition;
//            }
//        });
//
//    }
//
//    private void initData() {
//        dataListHeader = new ArrayList<>();
//        listHashMap = new HashMap<>();
//
//        dataListHeader.add("EDMTDev");
//        dataListHeader.add("Android");
//        dataListHeader.add("Xamarin");
//        dataListHeader.add("UWP");
////        dataListHeader.add("EDMTDev");
//        List<String> edmtDev = new ArrayList<>();
//        edmtDev.add("This is an Expandable ListView");
//        List<String> androidStudio = new ArrayList<>();
//        androidStudio.add("Expandable ListView");
//        androidStudio.add("Google Maps");
//        androidStudio.add("Chat Application");
//        androidStudio.add("Firebase");
//        List<String> xamarin = new ArrayList<>();
//        xamarin.add("xamarin Expandable ListView");
//        xamarin.add("xamarin Google Maps");
//        xamarin.add("xamarin Chat Application");
//        xamarin.add("xamarin Firebase");
//        List<String> uwp = new ArrayList<>();
//        uwp.add("uwp Expandable ListView");
//        uwp.add("uwp Google Maps");
//        uwp.add("uwp Chat Application");
//        uwp.add("uwp Firebase");
//
//        listHashMap.put(dataListHeader.get(0), edmtDev);
//        listHashMap.put(dataListHeader.get(1), androidStudio);
//        listHashMap.put(dataListHeader.get(2), xamarin);
//        listHashMap.put(dataListHeader.get(3), uwp);
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
