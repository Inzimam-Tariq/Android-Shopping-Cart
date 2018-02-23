package com.qemasoft.alhabibshop.app.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.OrderAdapter;
import com.qemasoft.alhabibshop.app.model.MyOrder;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.ORDER_HISTORY_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragOrderHistory extends MyBaseFragment {
    
    private OrderAdapter orderAdapter;
    private List<MyOrder> myOrderList = new ArrayList<>();
    
    public FragOrderHistory() {
        // Required empty public constructor
    }
    
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_order_history, container, false);
        initUtils();
        initViews(view);
        this.context = getContext();
        
        loadData();
        setupAdaptersAndShowData();
        
        return view;
    }
    
    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.order_recycler_view);
    }
    
    private void setupAdaptersAndShowData() {
        
        // for Orders
        utils.printLog("Item Data list populated");
        orderAdapter = new OrderAdapter(myOrderList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(context
                        , LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(orderAdapter);
        
        utils.printLog("Adapter Set Success");
    }
    
    private void loadData() {
        
        AppConstants.setMidFixApi("getOrders");
        
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", Preferences.getSharedPreferenceString(appContext,
                CUSTOMER_ID_KEY, DEFAULT_STRING_VAL));
        
        Bundle bundle = new Bundle();
        bundle.putBoolean("hasParameters", true);
        bundle.putSerializable("parameters", (Serializable) map);
        Intent intent = new Intent(getContext(), FetchData.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ORDER_HISTORY_REQUEST_CODE);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ORDER_HISTORY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
                    
                    utils.printLog("InsideOnResult");
                    JSONArray orders = response.optJSONArray("orders");
                    if (orders == null || orders.toString().isEmpty()) {
                        utils.showAlert(R.string.information_text, R.string.no_data,
                                false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                        return;
                    }
                    for (int i = 0; i < orders.length(); i++) {
                        JSONObject orderObj = orders.optJSONObject(i);
                        MyOrder myOrder = new MyOrder(orderObj.optString("order_id"),
                                orderObj.optString("status"), orderObj.optString("products"),
                                orderObj.optString("total"), orderObj.optString("date_added"));
                        myOrderList.add(myOrder);
                        orderAdapter.notifyDataSetChanged();
                    }
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                        false,
                        R.string.ok, null,
                        R.string.cancel_text, null);
            }
        }
    }
    
}
