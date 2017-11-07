package com.qemasoft.alhabibshop.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.qemasoft.alhabibshop.controller.CategoryAdapter;
import com.qemasoft.alhabibshop.controller.ItemAdapter;
import com.qemasoft.alhabibshop.model.MyCategory;
import com.qemasoft.alhabibshop.model.MyItem;

import java.util.ArrayList;
import java.util.List;

import hostflippa.com.opencart_android.R;

public class ShowItems extends AppCompatActivity {

    private RecyclerView mRecyclerView, mRecyclerViewCat;
    private ItemAdapter itemAdapter;
    private CategoryAdapter categoryAdapter;
    private List<MyItem> newArrivalList = new ArrayList<>();
    private List<MyCategory> myCategoryList = new ArrayList<>();
    private List<Integer> myCategoryImagesList;// = new ArrayList<>();
    Context context;

    private ViewPager mPager;
    private int currentPage = 0;
    private final Integer[] XMEN= {R.drawable.bannar1,R.drawable.bannar2,R.drawable.bannar3,R.drawable.bannar2};
    private ArrayList<Integer> XMENArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_main);
        initViews();
        this.context = this;

//        setupSlider();
        loadDummyData();
//        loadData();
        setAdaptersAndData();

    }

    private void setAdaptersAndData() {

        // for Categories
        Log.e("DataListPopulated", "Data list populated");
        categoryAdapter = new CategoryAdapter(myCategoryList);
//        RecyclerView.LayoutManager mLayoutManagerCat =
//                new LinearLayoutManager(getApplicationContext()
//                        ,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager mLayoutManagerCat =
                new GridLayoutManager(context,2,LinearLayoutManager.VERTICAL,false);
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

//    private void setupSlider() {
//        for(int i=0;i<XMEN.length;i++)
//            XMENArray.add(XMEN[i]);
//
//        mPager = (ViewPager) findViewById(R.id.pager);
//        mPager.setAdapter(new MyPagerAdapter(context,XMENArray));
//        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
//        indicator.setViewPager(mPager);
//
//        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == XMEN.length) {
//                    currentPage = 0;
//                }
//                mPager.setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 2500, 2500);
//    }

    private void loadDummyData() {

        String Item = "Item ";
        String []price = {"$3000", "$2490", "$4965", "$3000", "$2490", "$4965"};
        String []title = {"Woman", "Shoes", "Man", "Camera", "Clothing", "Child"};
        myCategoryImagesList = new ArrayList<>();
        myCategoryImagesList.add(R.drawable.bed1);
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

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.item_recycler_view);
        mRecyclerViewCat = (RecyclerView) findViewById(R.id.cat_recycler_view);

    }
}
