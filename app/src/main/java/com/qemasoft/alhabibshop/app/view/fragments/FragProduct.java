package com.qemasoft.alhabibshop.app.view.fragments;


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
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.controller.ItemAdapter;
import com.qemasoft.alhabibshop.app.model.MyItem;
import com.qemasoft.alhabibshop.app.view.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.qemasoft.alhabibshop.app.AppConstants.getProductExtra;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragProduct extends Fragment {

    private Context context;
    private Utils utils;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<MyItem> myItemList;
    public FragProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_products, container, false);
        initViews(view);
        this.context = getContext();
        this.utils = new Utils(getActivity());
        utils.showProgress();
        myItemList = new ArrayList<>();

        String extra = getProductExtra();

        setupAdapter(extra);

        return view;
    }

    private void setupAdapter(String extra) {

        try {
            JSONObject responseObject = new JSONObject(extra);
            Log.e("JSON_Response", "" + responseObject);
            boolean success = responseObject.optBoolean("success");
            if (success) {
                JSONArray products = responseObject.optJSONArray("products");
                Log.e("Categories", products.toString());
                for (int i =0; i<products.length();i++){
                    JSONObject productObj = products.optJSONObject(i);
                    MyItem item = new MyItem(productObj.optString("id")
                            ,productObj.optString("name"),productObj.optString("price"));
                    myItemList.add(item);
                }
                itemAdapter = new ItemAdapter(myItemList);
                RecyclerView.LayoutManager mLayoutManagerCat =
                        new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(mLayoutManagerCat);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setAdapter(itemAdapter);
                utils.hideProgress();

            } else {
                utils.hideProgress();
                utils.showErrorDialog("Error Getting Data From Server");
                Log.e("SuccessFalse", "Within getCategories");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONObjEx_MainAct", extra);
            utils.showErrorDialog("Exception Parsing JSON");
        }
    }

    private void changeFragment(int frag) {
        ((MainActivity) getActivity()).changeFragment(frag);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.product_recycler_view);
    }
}
