package com.qemasoft.alhabibshop.app.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.OrderDetailAdapter;
import com.qemasoft.alhabibshop.app.model.Product;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VALUE;
import static com.qemasoft.alhabibshop.app.AppConstants.ORDER_DETAIL_REQUEST_CODE;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragOrderDetail extends MyBaseFragment {

    private RecyclerView mRecyclerView;
    private TextView orderIdTV, orderDateTV, orderStatusTV, paymentMethodTV, shippingMethodTV,
            subTotalTV, shippingCostTV, grandTotalTV,
            subTotalTextTV, shippingCostTextTV, grandTotalTextTV;
    private OrderDetailAdapter orderDetailAdapter;
    private List<Product> myOrderList = new ArrayList<>();

    public FragOrderDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_order_detail, container, false);
        initViews(view);
        initUtils();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Log.e("Message: ", "" + bundle.getString("message", DEFAULT_STRING_VALUE));
            requestData(bundle.getString("message", DEFAULT_STRING_VALUE));
        }

        setupAdaptersAndShowData();

        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.order_detail_recycler_view);
        orderIdTV = view.findViewById(R.id.order_id_value);
        orderDateTV = view.findViewById(R.id.order_date);
        orderStatusTV = view.findViewById(R.id.order_status);
        paymentMethodTV = view.findViewById(R.id.payment_method);
        shippingMethodTV = view.findViewById(R.id.delivery_method);
        subTotalTV = view.findViewById(R.id.sub_total_val_tv);
        shippingCostTV = view.findViewById(R.id.shipping_cost_val_tv);
        grandTotalTV = view.findViewById(R.id.grand_total_val_tv);
    }


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

    private void requestData(String orderId) {

        AppConstants.setMidFixApi("getOrder");

        Map<String, String> map = new HashMap<>();
        map.put("order_id", orderId);
        Log.e("order_id", orderId);

        Bundle bundle = new Bundle();
        bundle.putBoolean("hasParameters", true);
        bundle.putSerializable("parameters", (Serializable) map);
        Intent intent = new Intent(getContext(), FetchData.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ORDER_DETAIL_REQUEST_CODE);


//        int orderIdInt = 1;
//        String[] fullPrice = {"3000", "2490", "4965", "3000", "2490", "4965"};
//        String[] qty = {"2", "1", "3", "1", "1", "4"};
//        String[] productName = {"IPhone", "Laptop", "LCD", "Speaker", "Headphone"};
//        orderId.setText("" + orderIdInt);
//        orderDate.setText("24-Oct-2017");
//        paymentMethod.setText("Cash On Delivery");
//        shippingMethod.setText("Aramex Shipping");

//        for (int i = 0; i < 2; i++) {
//
//            MyOrderDetail data = new MyOrderDetail(productName[i], qty[i], fullPrice[i]);
//            Log.e("LogsInForLoop",
//                    "\nProductName = " + data.getProductName() +
//                            "\nOrderQuantity = " + data.getOrderQty() +
//                            "\nPriceTotal = " + data.getProductPrice());
//            myOrderList.add(data);
//        }
//        int sTotal = Utils.subTotalOrderDetail;
//        subTotal.setText("" + sTotal);
//        Log.e("SubTotalInMain = ", "" + sTotal);
//        shippingCost.setText("26");
//        int gTotalInt = Integer.parseInt(subTotal.getText().toString()) -
//                Integer.parseInt(shippingCost.getText().toString());
//        grandTotal.setText("" + gTotalInt);

    }

    @Override
    public void onPause() {
        super.onPause();
//        Utils.subTotalOrderDetail = 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ORDER_DETAIL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));

                    Log.e("InsideOnResult", "FragOrderDetail");

                    JSONObject order = response.optJSONObject("order");
                    JSONObject orderInfo = order.optJSONObject("order_info");
                    JSONArray products = order.optJSONArray("products");
//                    List<Product> productList = new ArrayList<>();
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject productObj = products.optJSONObject(i);
                        Product product = new Product(productObj.optString("name"),
                                productObj.optString("quantity"), productObj.optString("price"),
                                productObj.optString("total"));
                        myOrderList.add(product);
                        orderDetailAdapter.notifyDataSetChanged();
                    }

                    orderIdTV.setText(orderInfo.optString("order_id"));
                    orderDateTV.setText(orderInfo.optString("date_added"));
                    orderStatusTV.setText(orderInfo.optString("order_status_id"));
                    paymentMethodTV.setText(orderInfo.optString("payment_method"));
                    shippingMethodTV.setText(orderInfo.optString("shipping_method"));

                    JSONArray totals = order.optJSONArray("totals");
                    List<String> totalListText = new ArrayList<>();
                    List<String> totalListValue = new ArrayList<>();
                    for (int i = 0; i < totals.length(); i++) {
                        JSONObject productObj = totals.optJSONObject(i);
                        totalListText.add(productObj.optString("title"));
                        totalListValue.add(productObj.optString("text"));
                    }

                    subTotalTV.setText(totalListValue.get(0));
                    shippingCostTV.setText(totalListValue.get(1));
                    grandTotalTV.setText(totalListValue.get(2));
                    subTotalTextTV.setText(totalListText.get(0));
                    shippingCostTextTV.setText(totalListText.get(1));
                    grandTotalTextTV.setText(totalListText.get(2));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                utils.showAlertDialog("Invalid Request!", "Either the request is invalid or no relevant record found");
            }
        }
    }
}
