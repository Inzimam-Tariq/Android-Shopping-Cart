package com.qemasoft.alhabibshop.app.view.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.CartDetailAdapter;
import com.qemasoft.alhabibshop.app.model.MyCartDetail;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.ADD_TO_CART_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.UNIQUE_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragCartDetail extends MyBaseFragment {

    private CheckBox useCoupon;
    private RecyclerView mRecyclerView;
    private Button checkoutBtn;
    private CartDetailAdapter cartDetailAdapter;
    private Bundle bundle;

    public FragCartDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_cart, container, false);
        initViews(view);
        initUtils();

        bundle = getArguments();
        if (bundle != null) {
            String id = getArguments().getString("id");
            requestData(id);
        } else {
            utils.showErrorDialog("No Data to Show");
        }
        useCoupon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    createAndShowCustomAlertDialog();
                }
            }
        });
        this.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.switchFragment(new FragCheckout());
            }
        });

        return view;
    }

    private void requestData(String id) {


        Map<String, String> map = new HashMap<>();
        map.put("session_id", Preferences.getSharedPreferenceString(appContext
                , UNIQUE_ID_KEY, DEFAULT_STRING_VAL));
        String self = getArguments().getString("self", "");
        if (getArguments().containsKey("self")) {
            map.put("key", id);
            AppConstants.setMidFixApi("removeCart/");
        } else if (getArguments().containsKey("addCart")) {
            AppConstants.setMidFixApi("addCart/");
            map.put("product_id", id);
        } else {
            AppConstants.setMidFixApi("confirm");
            map.put("customer_id", Preferences.getSharedPreferenceString(appContext
                    , CUSTOMER_KEY, DEFAULT_STRING_VAL));
        }
        bundle.putBoolean("hasParameters", true);
        bundle.putSerializable("parameters", (Serializable) map);
        Intent intent = new Intent(getContext(), FetchData.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_TO_CART_REQUEST_CODE);
    }

    private void createAndShowCustomAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);

        builder.setTitle("Apply Coupon");
        builder.setCancelable(true);
        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(30, 0, 30, 0);
        input.setLayoutParams(lp);
        input.setHint(AppConstants.findStringByName("enter_coupon_text"));
        builder.setView(input);
//        builder.setIcon(R.drawable.galleryalart);
        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                utils.showToast("" + input.getText());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.cart_detail_recycler_view);
        useCoupon = view.findViewById(R.id.use_coupon_cb);
        checkoutBtn = view.findViewById(R.id.cart_checkout_btn);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TO_CART_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
                    utils.printLog("RespInFragCartDetail", response.toString());
                    JSONArray cartProducts = response.optJSONArray("cartProducts");
                    List<MyCartDetail> cartDetailList = new ArrayList<>();
                    if (cartProducts == null || cartProducts.toString().isEmpty()) {
                        utils.showErrorDialog("You have no products in cart");
                        return;
                    }
                    for (int i = 0; i < cartProducts.length(); i++) {
                        JSONObject objectCP = cartProducts.optJSONObject(i);
                        cartDetailList.add(new MyCartDetail(objectCP.optString("cart_id"),
                                objectCP.optString("product_id"),
                                objectCP.optString("thumb"),
                                objectCP.optString("name"),
                                objectCP.optString("quantity"),
                                objectCP.optString("price"),
                                objectCP.optString("total")));
                    }

                    cartDetailAdapter = new CartDetailAdapter(cartDetailList, false);
                    RecyclerView.LayoutManager mLayoutManager =
                            new LinearLayoutManager(context
                                    , LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    utils.printLog("SettingAdapterForItems", "Setting Adapter For Items");
                    mRecyclerView.setAdapter(cartDetailAdapter);
                    utils.printLog("AdapterSet", "Adapter Set Success");

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
