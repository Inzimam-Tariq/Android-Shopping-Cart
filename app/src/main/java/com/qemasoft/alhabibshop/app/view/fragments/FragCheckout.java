package com.qemasoft.alhabibshop.app.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.CartDetailAdapter;
import com.qemasoft.alhabibshop.app.model.Address;
import com.qemasoft.alhabibshop.app.model.MyCartDetail;
import com.qemasoft.alhabibshop.app.model.PaymentMethod;
import com.qemasoft.alhabibshop.app.model.ShippingMethod;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.ACCENT_COLOR;
import static com.qemasoft.alhabibshop.app.AppConstants.ADDRESS_BOOK_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.CONFIRM_CHECKOUT_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.FORCE_CANCELED;
import static com.qemasoft.alhabibshop.app.AppConstants.LEFT;
import static com.qemasoft.alhabibshop.app.AppConstants.PAYMENT_METHOD_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.PLACE_ORDER_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.PRIMARY_COLOR;
import static com.qemasoft.alhabibshop.app.AppConstants.RIGHT;
import static com.qemasoft.alhabibshop.app.AppConstants.SHIPPING_METHOD_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.THEME_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.UNIQUE_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;
import static com.qemasoft.alhabibshop.app.AppConstants.findStringByName;
import static com.qemasoft.alhabibshop.app.AppConstants.getShippingSelectedIndex;

public class FragCheckout extends MyBaseFragment implements View.OnClickListener {
    
    private List<Address> addressList;
    private Bundle bundle;
    private StateProgressBar stateProgressBar;
    private RadioGroup radioGroupShippingMethod, radioGroupPaymentMethod;
    private Button selectDeliveryAddress, backBtn, nextBtn;
    private CheckBox termsCB;
    private LinearLayout step1, step2, step3, step4, totalContainer, step5;
    private List<String> list;
    private TextView confirmOrderTV, bankHeadingTV, bankInstructionsTV, termsTV;
    private int selectedAddressIndex;
    private EditText commentET;
    private List<ShippingMethod> shippingMethodList;
    private List<PaymentMethod> paymentMethodList;
    
    
    public FragCheckout() {
        // Required empty public constructor
    }
    
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_checkout, container, false);
        initViews(view);
        initUtils();
        AppConstants.setPaymentSelectedIndex(-1);
        AppConstants.setShippingSelectedIndex(-1);
        
        String[] descriptionData = {findStringByName("delivery_text"),
                findStringByName("shipping_text"),
                findStringByName("payment_text"),
                findStringByName("confirm_text")};
        String theme = Preferences.getSharedPreferenceString(
                appContext, THEME_CODE, "default");
        if (theme != null && !theme.equalsIgnoreCase("default")) {
            stateProgressBar.setStateDescriptionData(descriptionData);
            String pColor = Preferences.getSharedPreferenceString(
                    appContext, PRIMARY_COLOR, "#EC7625");
            String aColor = Preferences.getSharedPreferenceString(
                    appContext, ACCENT_COLOR, "#555555");
            stateProgressBar.setBackgroundColor(Color.parseColor(pColor));
            stateProgressBar.setCurrentStateDescriptionColor(Color.parseColor(pColor));
            stateProgressBar.setStateDescriptionColor(Color.parseColor(aColor));
            stateProgressBar.setForegroundColor(Color.parseColor(aColor));
        }
        backBtn.setOnClickListener(this);
        selectDeliveryAddress.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        bundle = new Bundle();
        getAddresses();
        setDrawables();
        
        return view;
    }
    
    private void setDrawables() {
        utils.applyAccentColor(termsTV);
        utils.setCompoundDrawable(backBtn, RIGHT, R.drawable.ic_navigate_next);
        utils.setCompoundDrawable(nextBtn, LEFT, R.drawable.ic_navigate_back);
        utils.setCompoundDrawable(selectDeliveryAddress, LEFT, R.drawable.ic_expand_more_black);
    }
    
    private void getAddresses() {
        AppConstants.setMidFixApi("getAddresses");
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", Preferences.getSharedPreferenceString(appContext
                , CUSTOMER_ID_KEY, DEFAULT_STRING_VAL));
        bundle.putBoolean("hasParameters", true);
        bundle.putSerializable("parameters", (Serializable) map);
        Intent intent = new Intent(getContext(), FetchData.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ADDRESS_BOOK_REQUEST_CODE);
    }
    
    private void initViews(View view) {
        stateProgressBar = view.findViewById(R.id.state_progress_bar);
        backBtn = view.findViewById(R.id.back_btn);
        step1 = view.findViewById(R.id.step1);
        selectDeliveryAddress = view.findViewById(R.id.select_delivery_address_btn);
        step2 = view.findViewById(R.id.step2);
        radioGroupShippingMethod = view.findViewById(R.id.rg_shipping_method);
        step3 = view.findViewById(R.id.step3);
        radioGroupPaymentMethod = view.findViewById(R.id.rg_payment_method);
        commentET = view.findViewById(R.id.order_comment_et);
        step4 = view.findViewById(R.id.step4);
        totalContainer = view.findViewById(R.id.total_container);
        step5 = view.findViewById(R.id.step5);
        confirmOrderTV = view.findViewById(R.id.confirm_order_tv);
        bankHeadingTV = view.findViewById(R.id.bank_heading_tv);
        bankInstructionsTV = view.findViewById(R.id.bank_instructions_tv);
        
        termsTV = view.findViewById(R.id.terms_tv);
        termsCB = view.findViewById(R.id.terms_cb);
        nextBtn = view.findViewById(R.id.next_btn);
        mRecyclerView = view.findViewById(R.id.cart_detail_recycler_view);
    }
    
    @Override
    public void onClick(View v) {
        list = new ArrayList<>();
        if (addressList != null && !addressList.isEmpty()) {
            for (int i = 0; i < addressList.size(); i++) {
                list.add(addressList.get(i).getAddress());
            }
        }
        switch (v.getId()) {
            case R.id.select_delivery_address_btn:
                if (addressList == null || addressList.isEmpty()) {
                    utils.showAlert(R.string.information_text,
                            R.string.no_address_msg, false,
                            R.string.ok, null,
                            R.string.cancel_text, null);
                } else {
                    utils.showRadioAlertDialog(selectDeliveryAddress
                            , findStringByName("please_select_text"), list,
                            0, null);
                    if (selectedAddressIndex < 0) {
                        selectedAddressIndex = 0;
                    }
                }
                break;
            case R.id.next_btn:
                if (step1.getVisibility() == View.VISIBLE) {
                    if (addressList == null || addressList.isEmpty() ||
                            addressList.size() < 1) {
                        utils.showAlert(R.string.information_text,
                                R.string.no_address_msg, false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                        return;
                    }
                    AppConstants.setMidFixApi("shippingMethod");
                    Map<String, String> map = new HashMap<>();
                    map.put("customer_id", Preferences.getSharedPreferenceString(appContext
                            , CUSTOMER_ID_KEY, DEFAULT_STRING_VAL));
                    map.put("address_id", addressList.get(selectedAddressIndex).getId());
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, SHIPPING_METHOD_REQUEST_CODE);
                    step1.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                    backBtn.setVisibility(View.VISIBLE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                } else if (step2.getVisibility() == View.VISIBLE) {
                    AppConstants.setShippingSelectedIndex(
                            utils.getSelectedRadioIndex(radioGroupShippingMethod));
                    if (getShippingSelectedIndex() > -1) {
                        AppConstants.setMidFixApi("paymentMethod");
                        Map<String, String> map = new HashMap<>();
                        map.put("customer_id", Preferences.getSharedPreferenceString(appContext
                                , CUSTOMER_ID_KEY, DEFAULT_STRING_VAL));
                        map.put("address_id", addressList.get(selectedAddressIndex).getId());
                        map.put("session_id", Preferences.getSharedPreferenceString(appContext
                                , UNIQUE_ID_KEY, DEFAULT_STRING_VAL));
                        bundle.putBoolean("hasParameters", true);
                        bundle.putSerializable("parameters", (Serializable) map);
                        Intent intent = new Intent(getContext(), FetchData.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, PAYMENT_METHOD_REQUEST_CODE);
                        step2.setVisibility(View.GONE);
                        step3.setVisibility(View.VISIBLE);
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                    }
                } else if (step3.getVisibility() == View.VISIBLE) {
                    
                    if (isTermsCBChecked()) {
                        step3.setVisibility(View.GONE);
                        step4.setVisibility(View.VISIBLE);
                        nextBtn.setText(R.string.confirm_text);
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                    } else {
                        utils.showAlert(R.string.information_text,
                                R.string.accept_terms_statement, false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                        return;
                    }
                    AppConstants.setPaymentSelectedIndex(
                            utils.getSelectedRadioIndex(radioGroupPaymentMethod));
                    if (AppConstants.getPaymentSelectedIndex() > -1) {
                        AppConstants.setMidFixApi("confirm");
                        Map<String, String> map = new HashMap<>();
                        map.put("customer_id", Preferences.getSharedPreferenceString(appContext
                                , CUSTOMER_ID_KEY, DEFAULT_STRING_VAL));
//                    map.put("address_id", addressList.get(selectedAddressIndex).getId());
                        map.put("session_id", Preferences.getSharedPreferenceString(appContext
                                , UNIQUE_ID_KEY, DEFAULT_STRING_VAL));
                        int index = getShippingSelectedIndex();
                        String shippingCode = shippingMethodList.get(index).getCode();
                        String shippingMethod = shippingMethodList.get(index).getTitle();
                        String shippingCost = shippingMethodList.get(index).getCost();
                        index = AppConstants.getPaymentSelectedIndex();
                        String paymentCode = paymentMethodList.get(index).getCode();
                        String paymentMethod = paymentMethodList.get(index).getTitle();
                        utils.printLog("shippingCode = " + shippingCode
                                + "\npaymentCode = " + paymentCode);
                        map.put("shipping_method", shippingMethod);
                        map.put("shipping_code", shippingCode);
                        map.put("shipping_cost", shippingCost);
                        map.put("payment_method", paymentMethod);
                        map.put("payment_code", paymentCode);
                        bundle.putBoolean("hasParameters", true);
                        bundle.putSerializable("parameters", (Serializable) map);
                        Intent intent = new Intent(getContext(), FetchData.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, CONFIRM_CHECKOUT_REQUEST_CODE);
                    }
                    
                } else if (step4.getVisibility() == View.VISIBLE) {
                    backBtn.setVisibility(View.GONE);
                    step4.setVisibility(View.INVISIBLE);
                    step5.setVisibility(View.VISIBLE);
                    nextBtn.setText(R.string.continue_text);
                    stateProgressBar.setAllStatesCompleted(true);
                    AppConstants.setMidFixApi("addOrder");
                    Map<String, String> map = new HashMap<>();
                    map.put("customer_id", Preferences.getSharedPreferenceString(appContext
                            , CUSTOMER_ID_KEY, DEFAULT_STRING_VAL));
                    map.put("address_id", addressList.get(selectedAddressIndex).getId());
                    map.put("session_id", Preferences.getSharedPreferenceString(appContext
                            , UNIQUE_ID_KEY, DEFAULT_STRING_VAL));
                    int index = getShippingSelectedIndex();
                    String shippingCode = shippingMethodList.get(index).getCode();
                    String shippingMethod = shippingMethodList.get(index).getTitle();
                    String shippingCost = shippingMethodList.get(index).getCost();
                    index = AppConstants.getPaymentSelectedIndex();
                    String paymentCode = paymentMethodList.get(index).getCode();
                    String paymentMethod = paymentMethodList.get(index).getTitle();
                    utils.printLog("shippingCode = " + shippingCode
                            + "\npaymentCode = " + paymentCode);
                    map.put("shipping_method", shippingMethod);
                    map.put("shipping_code", shippingCode);
                    map.put("shipping_cost", shippingCost);
                    map.put("payment_method", paymentMethod);
                    map.put("payment_code", paymentCode);
                    map.put("comment", "" + commentET.getText().toString());
                    String couponCode = Preferences.getSharedPreferenceString(appContext,
                            "couponCode", "");
                    if (!couponCode.isEmpty()) map.put("coupon", couponCode);
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, PLACE_ORDER_REQUEST_CODE);
                } else if (step5.getVisibility() == View.VISIBLE) {
                    utils.switchFragment(new MainFrag());
                }
                break;
            
            case R.id.back_btn:
                if (step4.getVisibility() == View.VISIBLE) {
                    step4.setVisibility(View.GONE);
                    step3.setVisibility(View.VISIBLE);
                    nextBtn.setText(R.string.next_text);
                    stateProgressBar.setAllStatesCompleted(false);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                } else if (step3.getVisibility() == View.VISIBLE) {
                    step3.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                } else if (step2.getVisibility() == View.VISIBLE) {
                    step2.setVisibility(View.GONE);
                    step1.setVisibility(View.VISIBLE);
                    backBtn.setVisibility(View.GONE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                }
                break;
            
        }
    }
    
    private boolean isTermsCBChecked() {
        return termsCB.isChecked();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JSONObject response = null;
        if (data != null && resultCode == Activity.RESULT_OK) {
            try {
                response = new JSONObject(data.getStringExtra("result"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (response != null) {
                if (requestCode == ADDRESS_BOOK_REQUEST_CODE) {
                    JSONArray addresses = response.optJSONArray("addresses");
                    addressList = new ArrayList<>();
                    for (int i = 0; i < addresses.length(); i++) {
                        JSONObject addressObj = addresses.optJSONObject(i);
                        addressList.add(new Address(addressObj.optString("address_id"),
                                        addressObj.optString("firstname"),
                                        addressObj.optString("lastname"),
                                        addressObj.optString("company"),
                                        addressObj.optString("address_1"),
                                        addressObj.optString("city"),
                                        addressObj.optString("postcode"),
                                        addressObj.optString("country"),
                                        addressObj.optString("zone"),
                                        addressObj.optBoolean("default_address")
                                )
                        );
                        selectDeliveryAddress.setHint(addressList.get(0).getAddress());
                    }
                } else if (requestCode == SHIPPING_METHOD_REQUEST_CODE) {
                    JSONArray shippingMethods = response.optJSONArray("shippingMethods");
                    shippingMethodList = new ArrayList<>();
                    List<String> keysList = new ArrayList<>();
                    radioGroupShippingMethod.removeAllViews();
                    if (shippingMethods == null || shippingMethods.toString().isEmpty()) {
                        utils.showAlert(R.string.an_error,
                                R.string.no_shipping_method, false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                        return;
                    }
                    for (int i = 0; i < shippingMethods.length(); i++) {
                        JSONObject shippingObj = shippingMethods.optJSONObject(i);
                        Iterator<?> keys = shippingObj.keys();
                        
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            keysList.add(key);
                            utils.printLog("KeyStr", key);
                        }
                        utils.printLog("KeyStr", "Size = " + keysList.size());
                        JSONObject shippingMethod = shippingObj.optJSONObject(keysList.get(i));
                        shippingMethodList.add(new ShippingMethod(shippingMethod.optString("code")
                                , shippingMethod.optString("cost")
                                , shippingMethod.optString("text")
                                , shippingMethod.optString("title"))
                        );
                        RadioButton radioButton = new RadioButton(getActivity());
                        radioButton.setText(shippingMethodList.get(i).getTitle()
                                .concat(" - ").concat(symbol).concat(shippingMethodList.get(i).getCost()));
                        radioButton.setId(i);
                        RadioGroup.LayoutParams rgParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                                RadioGroup.LayoutParams.WRAP_CONTENT);
                        radioButton.setTypeface(Typeface.createFromAsset(context.getAssets(),
                                "fonts/DroidKufi-Regular.ttf"));
                        radioGroupShippingMethod.addView(radioButton, rgParams);
                    }
                    if (getShippingSelectedIndex() > -1) {
                        radioGroupShippingMethod.check(getShippingSelectedIndex());
                    } else {
                        radioGroupShippingMethod.check(0);
                    }
                } else if (requestCode == PAYMENT_METHOD_REQUEST_CODE) {
                    JSONArray paymentMethods = response.optJSONArray("paymentMethods");
                    paymentMethodList = new ArrayList<>();
                    List<String> keysList = new ArrayList<>();
                    radioGroupPaymentMethod.removeAllViews();
                    if (paymentMethods == null || paymentMethods.toString().isEmpty()) {
                        utils.showAlert(R.string.an_error,
                                R.string.no_payment_method, false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                        return;
                    }
                    for (int i = 0; i < paymentMethods.length(); i++) {
                        JSONObject paymentObj = paymentMethods.optJSONObject(i);
                        Iterator<?> keys = paymentObj.keys();
                        
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            keysList.add(key);
                            utils.printLog("KeyStr", key);
                        }
                        utils.printLog("KeyStr", "Size = " + keysList.size());
                        JSONObject paymentMethod = paymentObj.optJSONObject(keysList.get(i));
                        paymentMethodList.add(new PaymentMethod(paymentMethod.optString("code")
                                , paymentMethod.optString("title")
                                , paymentMethod.optString("terms"))
                        );
                        RadioButton radioButton = new RadioButton(getActivity());
                        radioButton.setText(paymentMethodList.get(i).getTitle());
                        radioButton.setId(i);
                        RadioGroup.LayoutParams rgParams = new RadioGroup.LayoutParams(
                                RadioGroup.LayoutParams.MATCH_PARENT,
                                RadioGroup.LayoutParams.WRAP_CONTENT);
                        radioButton.setTypeface(Typeface.createFromAsset(context.getAssets(),
                                "fonts/DroidKufi-Regular.ttf"));
                        radioGroupPaymentMethod.addView(radioButton, rgParams);
                    }
                    if (getShippingSelectedIndex() > -1) {
                        radioGroupPaymentMethod.check(getShippingSelectedIndex());
                    } else {
                        radioGroupPaymentMethod.check(0);
                    }
                } else if (requestCode == CONFIRM_CHECKOUT_REQUEST_CODE) {
                    
                    JSONArray cartProducts = response.optJSONArray("cartProducts");
                    List<MyCartDetail> cartDetailList = new ArrayList<>();
                    if (cartProducts == null || cartProducts.toString().isEmpty()) {
                        utils.showAlert(R.string.information_text,
                                R.string.empty_cart_text, false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                        nextBtn.setEnabled(false);
                        return;
                    }
                    for (int i = 0; i < cartProducts.length(); i++) {
                        JSONObject objectCP = cartProducts.optJSONObject(i);
                        cartDetailList.add(new MyCartDetail(objectCP.optString("product_id"),
                                objectCP.optString("name"),
                                objectCP.optString("image"),
                                objectCP.optString("model"),
                                objectCP.optString("quantity"),
                                objectCP.optString("price"),
                                objectCP.optString("total")));
                    }
                    
                    CartDetailAdapter cartDetailAdapter = new CartDetailAdapter(cartDetailList
                            , true);
                    RecyclerView.LayoutManager mLayoutManager =
                            new LinearLayoutManager(context
                                    , LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    String className = FragCheckout.class.getSimpleName();
                    utils.printLog(className + "Adapter", "Before Cart list Adapter");
                    if (!cartDetailList.isEmpty() || cartDetailList.size() > 0)
                        mRecyclerView.setAdapter(cartDetailAdapter);
                    
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
                    
                    bankHeadingTV.setText(response.optString("instruction"));
                    bankInstructionsTV.setText(response.optString("bank_transfer"));
                    
                } else if (requestCode == PLACE_ORDER_REQUEST_CODE) {
                    stateProgressBar.setVisibility(View.GONE);
                    utils.setItemCount();
                    String message = response.optString("message");
                    message = message.trim();
                    if (!message.isEmpty()) {
                        Preferences.setSharedPreferenceString(appContext, "couponCode", "");
                        confirmOrderTV.getLayoutParams().height = 300;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            confirmOrderTV.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
                        else confirmOrderTV.setText(Html.fromHtml(message));
                    }
                }
            } else {
                utils.showAlert(R.string.information_text,
                        R.string.no_data, false,
                        R.string.ok, null,
                        R.string.cancel_text, null);
            }
        } else if (resultCode == FORCE_CANCELED) {
            try {
                assert data != null;
                response = new JSONObject(data.getStringExtra("result"));
                String message = response.optString("message");
                message = message.trim();
                if (!message.isEmpty()) {
                    utils.showAlert(R.string.information_text,
                            message, false,
                            R.string.ok, null,
                            R.string.cancel_text, null);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            utils.showAlert(R.string.an_error,
                    R.string.error_fetching_data, false,
                    R.string.ok, null,
                    R.string.cancel_text, null);
        }
    }
}
