package com.qemasoft.alhabibshop.app.view.fragments;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.FORCE_CANCELED;
import static com.qemasoft.alhabibshop.app.AppConstants.UNIQUE_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;
import static com.qemasoft.alhabibshop.app.AppConstants.optionsList;

/**
 * Created by Inzimam Tariq on 24-Oct-17.
 */

public class FragCartDetail extends MyBaseFragment {
    
    private CheckBox useCouponCB;
    private CartDetailAdapter cartDetailAdapter;
    private Bundle bundle;
    private Button checkoutBtn;
    private LinearLayout totalContainer, innerLayout;
    
    public FragCartDetail() {
        // Required empty public constructor
    }
    
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_cart, container, false);
        initViews(view);
        initUtils();
        
        
        bundle = getArguments();
        if (bundle != null) {
            String id = bundle.getString("id");
            utils.printLog("ProductId", "ID=" + id);
            requestData(id);
        }
        
        useCouponCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showCouponDialog();
                }
            }
        });
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (utils.isLoggedIn()) {
                    utils.switchFragment(new FragCheckout());
                } else {
                    utils.showAlert(R.string.continue_text, R.string.login_or_register,
                            true,
                            R.string.login_text, new FragLogin(),
                            R.string.action_register_text, new FragRegister());
                }
            }
        });
        
        
        return view;
    }
    
    private void requestData(String id) {
        
        Map<String, String> map = new HashMap<>();
        map.put("session_id", Preferences.getSharedPreferenceString(appContext
                , UNIQUE_ID_KEY, DEFAULT_STRING_VAL));
        String midFix = bundle.getString("midFix", "");
        utils.printLog("MidFix = " + midFix);
        AppConstants.setMidFixApi(midFix);
        String couponCode = Preferences.getSharedPreferenceString(appContext,
                "couponCode", "");
        if (!couponCode.isEmpty()) map.put("coupon", couponCode);
        
        String customerId = Preferences.getSharedPreferenceString(appContext,
                CUSTOMER_ID_KEY, DEFAULT_STRING_VAL);
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
    
    private void showCouponDialog() {
        
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_alert_dialog_input, null);
        
        builder.setView(dialogView);
        
        TextView title = dialogView.findViewById(R.id.dialog_title);
        title.setText(R.string.use_coupon_text);
        Button buttonPositive = dialogView.findViewById(R.id.positive_btn);
        buttonPositive.setText(R.string.apply);
        Button buttonNegative = dialogView.findViewById(R.id.negative_btn);
        buttonNegative.setText(R.string.cancel_text);
        
        final EditText input = dialogView.findViewById(R.id.input);
        input.setHint(R.string.enter_coupon_code);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        buttonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.printLog("CouponCode = " + input.getText().toString());
                String couponCode = input.getText().toString().trim();
                if (couponCode.isEmpty()) {
                    utils.showAlert(R.string.information_text, R.string.enter_coupon_code,
                            false,
                            R.string.ok, null,
                            R.string.cancel_text, null);
                    useCouponCB.setChecked(false);
                } else utils.applyCoupon(couponCode);
            }
        });
        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                useCouponCB.setChecked(false);
            }
        });
    }
    
    private void initViews(View view) {
        
        mRecyclerView = view.findViewById(R.id.cart_detail_recycler_view);
        useCouponCB = view.findViewById(R.id.use_coupon_cb);
        checkoutBtn = view.findViewById(R.id.cart_checkout_btn);
        totalContainer = view.findViewById(R.id.total_container);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        super.onActivityResult(requestCode, resultCode, data);
        
        if (data != null) {
            
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == ADD_TO_CART_REQUEST_CODE) {
                    JSONObject response = null;
                    try {
                        response = new JSONObject(data.getStringExtra("result"));
                        
                        utils.setItemCount();
                        
                        JSONArray cartProducts = response.optJSONArray("cartProducts");
                        List<MyCartDetail> cartDetailList = new ArrayList<>();
                        if (cartProducts == null || cartProducts.toString().isEmpty()) {
                            utils.showAlert(R.string.information_text, R.string.empty_cart_text,
                                    true,
                                    R.string.ok, null,
                                    R.string.cancel_text, null);
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
                        
                        LayoutInflater inflater = LayoutInflater.from(context);
                        totalContainer.removeAllViews();
                        
                        JSONArray totals = response.optJSONArray("totals");
                        
                        if (totals != null && !totals.toString().isEmpty()) {
                            for (int i = 0; i < totals.length(); i++) {
                                JSONObject totalsObj = totals.optJSONObject(i);
                                utils.printLog("" + totalsObj);
                                View layout = inflater.inflate(R.layout.layout_totals, null);
                                TextView textTV = layout.findViewById(R.id.text_holder);
                                TextView valTV = layout.findViewById(R.id.val_holder);
                                
                                textTV.setId(i);
                                valTV.setId(i + 10);
                                utils.printLog("Title = " + totalsObj.optString("title"
                                        + "Value = " + totalsObj.optString("text")));
                                textTV.setText(totalsObj.optString("title"));
                                
                                valTV.setText(symbol.concat("").concat(
                                        totalsObj.optString("text")));
                                
                                totalContainer.addView(layout);
                            }
                        }
                        
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == FORCE_CANCELED) {
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    String msg = response.optString("message");
                    if (!msg.isEmpty()) {
                        utils.showAlert(R.string.information_text, msg,
                                false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                        false,
                        R.string.ok, null,
                        R.string.cancel_text, null);
            }
        }
    }
    
}
