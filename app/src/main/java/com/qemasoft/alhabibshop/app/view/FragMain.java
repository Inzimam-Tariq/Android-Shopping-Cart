package com.qemasoft.alhabibshop.app.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.CategoryAdapter;
import com.qemasoft.alhabibshop.app.controller.ItemAdapter;
import com.qemasoft.alhabibshop.app.model.MyCategory;
import com.qemasoft.alhabibshop.app.model.MyItem;

import java.util.ArrayList;
import java.util.List;

import static com.qemasoft.alhabibshop.app.view.MainActivity.KEY_EXTRA;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragMain extends Fragment {

    private final Integer[] XMEN = {R.drawable.bannar1,
            R.drawable.bannar2, R.drawable.bannar3, R.drawable.bannar2};
    Context context;
    private RecyclerView mRecyclerView, mRecyclerViewCat;
    private ItemAdapter itemAdapter;
    private CategoryAdapter categoryAdapter;
    private List<MyItem> newArrivalList = new ArrayList<>();
    private List<MyCategory> myCategoryList = new ArrayList<>();
    private List<Integer> myCategoryImagesList = new ArrayList<>();
//    private ViewPager mPager;
//    private int currentPage = 0;
//    private ArrayList<String> sliderImagesUrl = new ArrayList<>();
//    private CircleIndicator indicator;

    public FragMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_main, container, false);
        initViews(view);
        this.context = getActivity();


        loadDummyData();
//        setupSlider();
        setAdaptersAndData();

        loadData();

        return view;
    }

    private void loadData() {
        String response = "";
        if (getActivity().getIntent().hasExtra(KEY_EXTRA)) {
            response = getActivity().getIntent().getStringExtra(KEY_EXTRA);
//            Log.e("ResponseInMainFrag", response);
        } else {
            Log.e("ResponseExMainFrag", response);
            throw new IllegalArgumentException("Activity cannot find  extras " + KEY_EXTRA);
        }
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.item_recycler_view);
        mRecyclerViewCat = view.findViewById(R.id.cat_recycler_view);
//        mPager = view.findViewById(R.id.pager);
//        indicator = view.findViewById(R.id.indicator);
    }

    // lkj

    private void setAdaptersAndData() {

        // for Categories
//        Log.e("DataListPopulated", "Data list populated");
        categoryAdapter = new CategoryAdapter(myCategoryList);
//        RecyclerView.LayoutManager mLayoutManagerCat =
//                new LinearLayoutManager(getApplicationContext()
//                        ,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager mLayoutManagerCat =
                new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewCat.setLayoutManager(mLayoutManagerCat);
        mRecyclerViewCat.setItemAnimator(new DefaultItemAnimator());
//        Log.e("SettingAdapter", "Setting Adapter");
        mRecyclerViewCat.setAdapter(categoryAdapter);
//        Log.e("AdapterSet", "Adapter Set Success");

        // for Items
//        Log.e("ItemDataListPopulated", "Item Data list populated");
        itemAdapter = new ItemAdapter(newArrivalList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(context
                        , LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        Log.e("SettingAdapterForItems", "Setting Adapter For Items");
        mRecyclerView.setAdapter(itemAdapter);
//        Log.e("AdapterSet", "Adapter Set Success");

    }

//    private void setupSlider() {
//        final String[] urls = {"http://www.opencartgulf.com/image/cache/catalog/demo/banners/iPhone6-1140x380.jpg"
//                , "http://www.opencartgulf.com/image/cache/catalog/demo/banners/MacBookAir-1140x380.jpg"};
//        Collections.addAll(sliderImagesUrl, urls);
//
//
//        mPager.setAdapter(new MyPagerAdapter(context, sliderImagesUrl));
//
//        indicator.setViewPager(mPager);
//
//        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == urls.length) {
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
        String[] fullPrice = {"$3000", "$2490", "$4965", "$3000", "$2490", "$4965"};
        String[] discPrice = {"$2790", "$2360", "$4540", "$2810", "$2405", "$4884"};
        String[] title = {"Woman", "Shoes", "Man", "Camera", "Clothing", "Child"};
        myCategoryImagesList = new ArrayList<>();
        myCategoryImagesList.add(R.drawable.bed1);
        myCategoryImagesList.add(R.drawable.bed2);
        myCategoryImagesList.add(R.drawable.bed3);
        myCategoryImagesList.add(R.drawable.bed4);
        myCategoryImagesList.add(R.drawable.bed5);
        myCategoryImagesList.add(R.drawable.bed7);
        int itemNo = 1;
        for (int i = 0; i < title.length; i++) {

            MyItem data = new MyItem("" + itemNo, Item + itemNo, discPrice[i], fullPrice[i]);
            newArrivalList.add(data);
            MyCategory category = new MyCategory(title[i], myCategoryImagesList.get(i));
            myCategoryList.add(category);
            itemNo++;
        }
    }

}
