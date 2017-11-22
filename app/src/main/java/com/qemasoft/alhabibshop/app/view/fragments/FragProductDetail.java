package com.qemasoft.alhabibshop.app.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONException;
import org.json.JSONObject;

import static com.qemasoft.alhabibshop.app.AppConstants.ITEM_COUNTER;
import static com.qemasoft.alhabibshop.app.AppConstants.ORDER_DETAIL_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragProductDetail extends MyBaseFragment {

    private TextView productTitle, productDetail, discPrice, fullPrice, percentDisc, reviews;
    private Button addToCartBtn;

    public FragProductDetail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_product_detail, container, false);
        initViews(view);
        initUtils();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String id = getArguments().getString("id");
            requestData(id);
        } else {
            utils.showErrorDialog("No Data to Show");
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView itemCountTV = getActivity().findViewById(R.id.actionbar_notification_tv);
                int val = Preferences.getSharedPreferenceInt(appContext, ITEM_COUNTER, 0);
                val++;
                itemCountTV.setText(String.valueOf(val));
                Preferences.setSharedPreferenceInt(appContext, ITEM_COUNTER,
                        Integer.parseInt(itemCountTV.getText().toString()));
            }
        });


        return view;
    }


    private void initViews(View view) {
        productTitle = view.findViewById(R.id.product_title);
        productDetail = view.findViewById(R.id.product_detail);
        discPrice = view.findViewById(R.id.product_price_disc);
        fullPrice = view.findViewById(R.id.full_price);
        percentDisc = view.findViewById(R.id.disc_percent);
        reviews = view.findViewById(R.id.review_tv);

        addToCartBtn = view.findViewById(R.id.add_to_cart_btn);
    }

    private void requestData(String orderId) {

        AppConstants.setMidFixApi("getProduct/product_id/" + orderId);

        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), FetchData.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ORDER_DETAIL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ORDER_DETAIL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));

                    Log.e("InsideOnResult", "FragOrderHistory");
//                    JSONArray orders = response.optJSONArray("orders");
//                    for (int i = 0; i < orders.length(); i++) {
//                        JSONObject orderObj = orders.optJSONObject(i);
//                        MyOrder myOrder = new MyOrder(orderObj.optString("order_id"),
//                                orderObj.optString("status"), orderObj.optString("products"),
//                                orderObj.optString("total"), orderObj.optString("date_added"));
//
//                    }

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
