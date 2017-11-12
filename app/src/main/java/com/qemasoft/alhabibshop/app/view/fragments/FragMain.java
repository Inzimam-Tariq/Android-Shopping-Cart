package com.qemasoft.alhabibshop.app.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragMain extends Fragment {

    Context context;
    private RecyclerView mRecyclerView, mRecyclerViewCat;
    private ItemAdapter itemAdapter;
    private CategoryAdapter categoryAdapter;
    private List<MyItem> newArrivalList = new ArrayList<>();
    private List<MyCategory> myCategoryList = new ArrayList<>();
    private List<Integer> myCategoryImagesList = new ArrayList<>();

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
        setAdaptersAndData();


        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.item_recycler_view);
        mRecyclerViewCat = view.findViewById(R.id.cat_recycler_view);
    }

    private void setAdaptersAndData() {

        // for Categories
        categoryAdapter = new CategoryAdapter(myCategoryList);
//        RecyclerView.LayoutManager mLayoutManagerCat =
//                new LinearLayoutManager(getApplicationContext()
//                        ,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager mLayoutManagerCat =
                new GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCat.setLayoutManager(mLayoutManagerCat);
        mRecyclerViewCat.setItemAnimator(new DefaultItemAnimator());

        mRecyclerViewCat.setAdapter(categoryAdapter);

        // for Items
//        Log.e("ItemDataListPopulated", "Item Data list populated");
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(context
                        , LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        Log.e("SettingAdapterForItems", "Setting Adapter For Items");
        itemAdapter = new ItemAdapter(newArrivalList);
        mRecyclerView.setAdapter(itemAdapter);
//        Log.e("AdapterSet", "Adapter Set Success");

    }

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
            MyCategory category = new MyCategory(title[i]);
            myCategoryList.add(category);
            itemNo++;
        }
    }

}
