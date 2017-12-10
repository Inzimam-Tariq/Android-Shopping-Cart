package com.qemasoft.alhabibshop.app.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.ItemAdapter;
import com.qemasoft.alhabibshop.app.model.MyItem;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.PRODUCT_REQUEST_CODE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragProduct extends MyBaseFragment {

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
        initUtils();
        myItemList = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            requestData(bundle.getString("id"));
        }else {
            utils.showErrorDialog("No Data to Show");
        }

        return view;
    }

    private void requestData(String id) {
        boolean isFromSearch = getArguments().getBoolean("isFromSearch", false);
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), FetchData.class);
        Map<String, String> map = new HashMap<>();
        if (isFromSearch){
            AppConstants.setMidFixApi("searchProduct");
            map.put("search", id);
            bundle.putBoolean("hasParameters", true);
            bundle.putSerializable("parameters", (Serializable) map);
        }else {
            AppConstants.setMidFixApi("products/category_id/" + id);
        }
        intent.putExtras(bundle);
        startActivityForResult(intent, PRODUCT_REQUEST_CODE);
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.product_recycler_view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PRODUCT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
                    JSONArray products = response.optJSONArray("products");
                    utils.printLog("Products", products.toString());
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject productObj = products.optJSONObject(i);
                        MyItem item = new MyItem(productObj.optString("product_id")
                                , productObj.optString("name"), productObj.optString("disc_price")
                                , productObj.optString("price"), productObj.optString("thumb"));
                        myItemList.add(item);
                    }
                    itemAdapter = new ItemAdapter(myItemList);
                    RecyclerView.LayoutManager mLayoutManagerCat =
                            new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLayoutManagerCat);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                    mRecyclerView.setAdapter(itemAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == AppConstants.FORCED_CANCEL) {
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    String error = response.optString("error");
                    if (!error.isEmpty()) {
                        utils.showErrorDialog(error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.showErrorDialog("Error Fetching Data! ...");
            }
        }
    }


}
