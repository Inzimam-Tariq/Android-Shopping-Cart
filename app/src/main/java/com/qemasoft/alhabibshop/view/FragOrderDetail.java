package com.qemasoft.alhabibshop.view;

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
import android.widget.TextView;

import com.qemasoft.alhabibshop.Utils;
import com.qemasoft.alhabibshop.controller.OrderDetailAdapter;
import com.qemasoft.alhabibshop.model.MyOrderDetail;

import java.util.ArrayList;
import java.util.List;

import hostflippa.com.opencart_android.R;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragOrderDetail extends Fragment {

    Context context;
    private RecyclerView mRecyclerView;
    private TextView orderId, orderDate, orderStatus, paymentMethod, shippingMethod,
            subTotal, shippingCost, grandTotal;
    private OrderDetailAdapter orderDetailAdapter;
    private List<MyOrderDetail> myOrderList = new ArrayList<>();

    public FragOrderDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_order_detail, container, false);
        initViews(view);
        this.context = getContext();

        loadDummyData();
        setupAdaptersAndShowData();

        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.order_detail_recycler_view);
        orderId = view.findViewById(R.id.order_id_value);
        orderDate = view.findViewById(R.id.order_date);
        orderStatus = view.findViewById(R.id.order_status);
        paymentMethod = view.findViewById(R.id.payment_method);
        shippingMethod = view.findViewById(R.id.delivery_method);
        subTotal = view.findViewById(R.id.sub_total);
        shippingCost = view.findViewById(R.id.shipping_cost);
        grandTotal = view.findViewById(R.id.grand_total);
    }

    // lkj

    private void setupAdaptersAndShowData() {

        // for Orders
        Log.e("ItemDataListPopulated", "Item Data list populated");
        orderDetailAdapter = new OrderDetailAdapter(myOrderList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(context
                        , LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.e("SettingAdapterForItems", "Setting Adapter For Items");
        mRecyclerView.setAdapter(orderDetailAdapter);
        Log.e("AdapterSet", "Adapter Set Success");
    }

    private void loadDummyData() {

        int orderIdInt = 1;
        String[] fullPrice = {"3000", "2490", "4965", "3000", "2490", "4965"};
        String[] qty = {"2", "1", "3", "1", "1", "4"};
        String[] productName = {"IPhone", "Laptop", "LCD", "Speaker", "Headphone"};
        orderId.setText("" + orderIdInt);
        orderDate.setText("24-Oct-2017");
        paymentMethod.setText("Cash On Delivery");
        shippingMethod.setText("Aramex Shipping");

        for (int i = 0; i < 2; i++) {

            MyOrderDetail data = new MyOrderDetail(productName[i], qty[i], fullPrice[i]);
            Log.e("LogsInForLoop",
                    "\nProductName = " + data.getProductName() +
                            "\nOrderQuantity = " + data.getOrderQty() +
                            "\nPriceTotal = " + data.getProductPrice());
            myOrderList.add(data);
        }
        int sTotal = Utils.subTotalDummy;
        subTotal.setText("" + sTotal);
        Log.e("SubTotalInMain = ", "" + sTotal);
        shippingCost.setText("26");
        int gTotalInt = Integer.parseInt(subTotal.getText().toString()) -
                Integer.parseInt(shippingCost.getText().toString());
        grandTotal.setText("" + gTotalInt);

    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.subTotalDummy = 0;
    }
}
