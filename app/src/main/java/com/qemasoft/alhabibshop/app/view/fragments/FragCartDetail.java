package com.qemasoft.alhabibshop.app.view.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.CartDetailAdapter;
import com.qemasoft.alhabibshop.app.model.MyCartDetail;
import com.qemasoft.alhabibshop.app.model.Options;
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
import static com.qemasoft.alhabibshop.app.AppConstants.FORCED_CANCEL;
import static com.qemasoft.alhabibshop.app.AppConstants.UNIQUE_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;
import static com.qemasoft.alhabibshop.app.AppConstants.optionsList;

/**
 * Created by Inzimam Tariq on 24-Oct-17.
 */

public class FragCartDetail extends MyBaseFragment {
    
    private CheckBox useCoupon;
    private CartDetailAdapter cartDetailAdapter;
    private Bundle bundle;
    private TextView subTotalVal, grandTotalVal;
    
    public FragCartDetail() {
        // Required empty public constructor
    }
    
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_cart, container, false);
        initViews(view);
        initUtils();
        
        
        bundle = getArguments();
        if (bundle != null) {
            String id = getArguments().getString("id");
            utils.printLog("ProductId", "ID=" + id);
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
        
        
        return view;
    }
    
    private void requestData(String id) {
        
        Map<String, String> map = new HashMap<>();
        map.put("session_id", Preferences.getSharedPreferenceString(appContext
                , UNIQUE_ID_KEY, DEFAULT_STRING_VAL));
        String midFix = getArguments().getString("midFix", "");
        utils.printLog("MidFix = " + midFix);
        AppConstants.setMidFixApi(midFix);
        String customerId = Preferences.getSharedPreferenceString(appContext,
                CUSTOMER_KEY, DEFAULT_STRING_VAL);
        map.put("customer_id", customerId);
        if (midFix.contains("removeCart")) {
            map.put("cart_id", id);
        } else if (midFix.contains("updateCart")) {
            map.put("cart_id", id);
            map.put("quantity", bundle.getString("qty"));
        } else if (midFix.contains("addCart")) {
            utils.printLog("ProductId", "Id=" + id);
            map.put("product_id", id);
            for (int i = 0; i < optionsList.size(); i++) {
                map.put("option[" + optionsList.get(i).getOptionValueId() + "]",
                        optionsList.get(i).getName());
            }
            utils.printLog("Inside Add Cart Working");
        } else if (midFix.contains("cartProducts")) {
            utils.printLog("Inside Show Cart Products, Working Do Nothing Extra!");
        }
        utils.printLog("Code Executing...");
        bundle.putBoolean("hasParameters", true);
        bundle.putSerializable("parameters", (Serializable) map);
        Intent intent = new Intent(getContext(), FetchData.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_TO_CART_REQUEST_CODE);
        utils.printLog("OptionsListSize", "size = " + optionsList.size());
        optionsList.clear();
        utils.printLog("OptionsListSize", "size = " + optionsList.size());
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
//                utils.showToast("" + input.getText());
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
        subTotalVal = view.findViewById(R.id.sub_total_val_tv);
        grandTotalVal = view.findViewById(R.id.grand_total_val_tv);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            JSONObject response = null;
            try {
                response = new JSONObject(data.getStringExtra("result"));
            } catch (JSONException e) {
                e.printStackTrace();
                
                utils.showErrorDialog(String.format("ResponseIs = %s", response.toString()));
                utils.printLog("ResponseIs = " + response.toString());
            }
            
            utils.printLog("ResponseStep1!", "" + response.toString());
            utils.printLog("RespInFragCDabcd", "" + response.toString());
            utils.printLog("ResponseStep2!" + response.toString());
            if (resultCode == Activity.RESULT_OK) {
                utils.printLog("ResponseStep3!" + response.toString());
                if (requestCode == ADD_TO_CART_REQUEST_CODE) {
                    utils.printLog("ResponseStep4!", response.toString());
//                    utils.showAlertDialog("ResponseResultOK!", response.toString());
                    JSONArray cartProducts = response.optJSONArray("cartProducts");
                    List<MyCartDetail> cartDetailList = new ArrayList<>();
                    if (cartProducts == null || cartProducts.toString().isEmpty()) {
                        utils.showErrorDialog("You have no products in cart");
                        return;
                    }
                    for (int i = 0; i < cartProducts.length(); i++) {
                        
                        JSONObject objectCP = cartProducts.optJSONObject(i);
                        
                        JSONArray selectedOptions = objectCP.optJSONArray("option");
                        utils.printLog("ProductOptionsRaw = " + selectedOptions);
                        List<Options> optionsList = new ArrayList<>();
                        
                        if (selectedOptions != null && selectedOptions.length() > 0)
                            for (int j = 0; j < selectedOptions.length(); j++) {
                                JSONObject objOptions = selectedOptions.optJSONObject(j);
                                optionsList.add(new Options(objOptions.optString("value")));
                            }
                        utils.printLog("OptionListInFragCart = " + optionsList.toString());
                        
                        
                        cartDetailList.add(new MyCartDetail(objectCP.optString("cart_id"),
                                objectCP.optString("product_id"),
                                objectCP.optString("image"),
                                objectCP.optString("name"),
                                objectCP.optString("quantity"),
                                objectCP.optString("price"),
                                objectCP.optString("total")
                                , optionsList));
                    }
                    
                    cartDetailAdapter = new CartDetailAdapter(cartDetailList, false);
                    RecyclerView.LayoutManager mLayoutManager =
                            new LinearLayoutManager(context
                                    , LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    utils.printLog("Setting Adapter For Cart Items");
                    mRecyclerView.setAdapter(cartDetailAdapter);
                    utils.printLog("Adapter Set Success");
                    
                    JSONArray totals = response.optJSONArray("totals");
                    
                    if (totals != null && !totals.toString().isEmpty()) {
                        JSONObject object0 = totals.optJSONObject(0);
                        JSONObject object1 = totals.optJSONObject(1);
                        
                        subTotalVal.setText(object0.optString("text").concat("").concat(symbol));
                        grandTotalVal.setText(object1.optString("text").concat("").concat(symbol));
                    }
                    
                } else {
                    utils.showAlertDialog("ResponseOtherCode!", response.toString());
                }
            } else if (resultCode == FORCED_CANCEL) {
                utils.showErrorDialog("Response!"
                        + response.optString("message"));
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.showErrorDialog("Error Getting Data From Server!");
            }
        }
    }
    
}
