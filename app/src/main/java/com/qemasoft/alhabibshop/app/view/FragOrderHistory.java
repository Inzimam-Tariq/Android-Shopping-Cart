package com.qemasoft.alhabibshop.app.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.OrderAdapter;
import com.qemasoft.alhabibshop.app.model.MyOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragOrderHistory extends Fragment {

    Context context;
    private RecyclerView mRecyclerView;
    private OrderAdapter orderAdapter;
    private List<MyOrder> myOrderList = new ArrayList<>();

    public FragOrderHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_order_history, container, false);
        initViews(view);
        this.context = getContext();

        loadDummyData();
        setupAdaptersAndShowData();

        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.order_recycler_view);
    }

    // lkj

    private void setupAdaptersAndShowData() {

        // for Orders
        Log.e("ItemDataListPopulated", "Item Data list populated");
        orderAdapter = new OrderAdapter(myOrderList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(context
                        , LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.e("SettingAdapterForItems", "Setting Adapter For Items");
        mRecyclerView.setAdapter(orderAdapter);
        Log.e("AdapterSet", "Adapter Set Success");
    }

    private void loadDummyData() {

        String[] fullPrice = {"$3000", "$2490", "$4965", "$3000", "$2490", "$4965"};
        String[] qty = {"2", "1", "3", "1", "1", "4"};
        String[] status = {"Processing", "Complete", "Complete", "Complete", "Processing"};
        String[] date = {"20-Oct-2017", "22-Oct-2017", "22-Oct-2017",
                "23-Oct-2017", "24-Oct-2017", "22-Oct-2017"};
        int orderId = 1;
        for (int i = 0; i < status.length; i++) {

            MyOrder data = new MyOrder("" + orderId, status[i], qty[i], fullPrice[i], date[i]);
            Log.e("Question" + " " + orderId,
                    "\nQuestion id = " + data.getOrderId() +
                            "\nQuestion text = " + data.getOrderQty());
            myOrderList.add(data);
//            orderAdapter.notifyDataSetChanged();
            orderId++;
        }
    }

}
