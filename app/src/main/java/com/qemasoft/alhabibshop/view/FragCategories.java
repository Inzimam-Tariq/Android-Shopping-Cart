package com.qemasoft.alhabibshop.view;

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

import com.qemasoft.alhabibshop.controller.CategoryAdapter;
import com.qemasoft.alhabibshop.model.MyCategory;

import java.util.ArrayList;
import java.util.List;

import hostflippa.com.opencart_android.R;

import static com.qemasoft.alhabibshop.view.MainActivity.KEY_EXTRA;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragCategories extends Fragment {

    Context context;
    private RecyclerView mRecyclerViewCat;
    private CategoryAdapter categoryAdapter;
    private List<MyCategory> myCategoryList = new ArrayList<>();
    private List<Integer> myCategoryImagesList = new ArrayList<>();

    public FragCategories() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_categories, container, false);
        initViews(view);
        this.context = getActivity();


        loadDummyData();
        setAdaptersAndData();

        loadData();

        return view;
    }

    private void loadData() {
        String response = "";
        if (getActivity().getIntent().hasExtra(KEY_EXTRA)) {
            response = getActivity().getIntent().getStringExtra(KEY_EXTRA);
            Log.e("ResponseInMainFrag", response);
        } else {
            Log.e("ResponseExMainFrag", response);
            throw new IllegalArgumentException("Activity cannot find  extras " + KEY_EXTRA);
        }
    }

    private void initViews(View view) {
        mRecyclerViewCat = view.findViewById(R.id.cat_recycler_view);
    }

    private void setAdaptersAndData() {

        // for Categories
        Log.e("DataListPopulated", "Data list populated");
        categoryAdapter = new CategoryAdapter(myCategoryList);

        RecyclerView.LayoutManager mLayoutManagerCat =
                new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewCat.setLayoutManager(mLayoutManagerCat);
        mRecyclerViewCat.setItemAnimator(new DefaultItemAnimator());
        Log.e("SettingAdapter", "Setting Adapter");
        mRecyclerViewCat.setAdapter(categoryAdapter);
        Log.e("AdapterSet", "Adapter Set Success");

    }

    private void loadDummyData() {

        String[] title = {"Woman", "Shoes", "Man", "Camera", "Clothing", "Child"};
        myCategoryImagesList = new ArrayList<>();
        myCategoryImagesList.add(R.drawable.bed1);
        myCategoryImagesList.add(R.drawable.bed2);
        myCategoryImagesList.add(R.drawable.bed3);
        myCategoryImagesList.add(R.drawable.bed4);
        myCategoryImagesList.add(R.drawable.bed5);
        myCategoryImagesList.add(R.drawable.bed7);

        for (int i = 0; i < title.length; i++) {
            MyCategory category = new MyCategory(title[i], myCategoryImagesList.get(i));
            myCategoryList.add(category);

        }
    }

}
