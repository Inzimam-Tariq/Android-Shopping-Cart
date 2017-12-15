package com.qemasoft.alhabibshop.app.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.CategoryAdapter;
import com.qemasoft.alhabibshop.app.model.MyCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.qemasoft.alhabibshop.app.AppConstants.getProductExtra;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragCategories extends MyBaseFragment {


    private CategoryAdapter categoryAdapter;
    private List<MyCategory> myCategoryList = new ArrayList<>();

    public FragCategories() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_categories, container, false);
        initUtils();
        initViews(view);

        setAdaptersAndData();
        loadData();


        return view;
    }

    private void loadData() {
        String response = getProductExtra();
            utils.printLog("ResponseInCategoryFrag", response);
        try {
            JSONObject object = new JSONObject(response);
            JSONArray categoryArray = object.optJSONArray("products");
            for (int i = 0; i<categoryArray.length(); i++){
                JSONObject categoryObj = categoryArray.getJSONObject(i);
                MyCategory category = new MyCategory(categoryObj.optString("product_id"),
                        categoryObj.optString("name"),categoryObj.optString("image"));
                myCategoryList.add(category);
                categoryAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.cat_recycler_view);
    }

    private void setAdaptersAndData() {

        // for Categories
        utils.printLog("Data list populated");
        categoryAdapter = new CategoryAdapter(myCategoryList);

        RecyclerView.LayoutManager mLayoutManagerCat =
                new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManagerCat);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        utils.printLog("Setting Adapter");
        mRecyclerView.setAdapter(categoryAdapter);
        utils.printLog("Adapter Set Success");

    }

}
