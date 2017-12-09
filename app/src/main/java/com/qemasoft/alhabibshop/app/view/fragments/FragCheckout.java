package com.qemasoft.alhabibshop.app.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.model.Address;
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

import static com.qemasoft.alhabibshop.app.AppConstants.ADDRESS_BOOK_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.FORCED_CANCEL;
import static com.qemasoft.alhabibshop.app.AppConstants.PAYMENT_METHOD_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.SHIPPING_METHOD_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.UNIQUE_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;

public class FragCheckout extends MyBaseFragment implements View.OnClickListener {

    List<Address> addressList;
    Bundle bundle;
    private StateProgressBar stateProgressBar;
    private RadioGroup radioGroupShippingMethod, radioGroupPaymentMethod;
    private Button selectAddressDelivery, backBtn, nextBtn;
    private CheckBox termsCB;
    private LinearLayout step1, step2, step3, step4, step5;
    private List<String> list;

    public FragCheckout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_checkout, container, false);
        initViews(view);
        initUtils();

        String[] descriptionData = {"Delivery", "Shipping", "Payment", "Confirm"};
        stateProgressBar.setStateDescriptionData(descriptionData);
        backBtn.setOnClickListener(this);
        selectAddressDelivery.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        bundle = new Bundle();
        getAddresses();

        return view;
    }

    private void getAddresses() {
        AppConstants.setMidFixApi("getAddresses");
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", Preferences.getSharedPreferenceString(appContext
                , CUSTOMER_KEY, DEFAULT_STRING_VAL));
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
        selectAddressDelivery = view.findViewById(R.id.select_delivery_address_btn);
        step2 = view.findViewById(R.id.step2);
        radioGroupShippingMethod = view.findViewById(R.id.rg_shipping_method);
        step3 = view.findViewById(R.id.step3);
        radioGroupPaymentMethod = view.findViewById(R.id.rg_payment_method);
        step4 = view.findViewById(R.id.step4);

        termsCB = view.findViewById(R.id.terms_cb);
        nextBtn = view.findViewById(R.id.next_btn);
    }

    @Override
    public void onClick(View v) {
        list = new ArrayList<>();
        if (addressList.size() > 0) {
            for (int i = 0; i < addressList.size(); i++) {
                list.add(addressList.get(i).getAddress());
            }
        }
        switch (v.getId()) {
            case R.id.select_delivery_address_btn:
                utils.showRadioAlertDialog(selectAddressDelivery, "Select Address", list, -0);
                break;
            case R.id.next_btn:
                if (step1.getVisibility() == View.VISIBLE) {
                    AppConstants.setMidFixApi("shippingMethod");
                    Map<String, String> map = new HashMap<>();
                    map.put("customer_id", Preferences.getSharedPreferenceString(appContext
                            , CUSTOMER_KEY, DEFAULT_STRING_VAL));
                    map.put("address_id", "2");
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, SHIPPING_METHOD_REQUEST_CODE);
                    step1.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                } else if (step2.getVisibility() == View.VISIBLE) {
                    AppConstants.setMidFixApi("paymentMethod");
                    Map<String, String> map = new HashMap<>();
                    map.put("customer_id", Preferences.getSharedPreferenceString(appContext
                            , CUSTOMER_KEY, DEFAULT_STRING_VAL));
                    map.put("address_id", "2");
                    map.put("session_id", Preferences.getSharedPreferenceString(appContext
                            , UNIQUE_ID_KEY, DEFAULT_STRING_VAL));
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, SHIPPING_METHOD_REQUEST_CODE);
                    step2.setVisibility(View.GONE);
                    step3.setVisibility(View.VISIBLE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                } else if (step3.getVisibility() == View.VISIBLE) {
                    if (isTermsCBChecked()) {
                        step3.setVisibility(View.GONE);
                        step4.setVisibility(View.VISIBLE);
                        nextBtn.setText("Confirm");
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                    } else {
                        utils.showAlertDialog("Read Terms First!", "You have to Accept Terms and Condition to Continue");
                    }
                } else if (step4.getVisibility() == View.VISIBLE) {
                    stateProgressBar.setAllStatesCompleted(true);
                }
                break;

            case R.id.back_btn:
                if (step4.getVisibility() == View.VISIBLE) {
                    step4.setVisibility(View.GONE);
                    step3.setVisibility(View.VISIBLE);
                    nextBtn.setText("Next");
                    stateProgressBar.setAllStatesCompleted(false);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                } else if (step3.getVisibility() == View.VISIBLE) {
                    step3.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                } else if (step2.getVisibility() == View.VISIBLE) {
                    step2.setVisibility(View.GONE);
                    step1.setVisibility(View.VISIBLE);
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
        try {
            response = new JSONObject(data.getStringExtra("result"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (response != null) {
            if (resultCode == Activity.RESULT_OK) {
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
                        if (addressList.size() > 0) {
                            selectAddressDelivery.setHint(addressList.get(0).getAddress());
                        }
                    }
                } else if (requestCode == SHIPPING_METHOD_REQUEST_CODE) {
                    JSONArray shippingMethods = response.optJSONArray("shippingMethod");
                    List<ShippingMethod> shippingMethodList = new ArrayList<>();
                    List<String> keysList = new ArrayList<>();
                    radioGroupShippingMethod.removeAllViews();
                    for (int i = 0; i < shippingMethods.length(); i++) {
                        JSONObject shippingObj = shippingMethods.optJSONObject(i);
                        Iterator<?> keys = shippingObj.keys();

                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            keysList.add(key);
                            Log.e("KeyStr", key);
                        }
                        Log.e("KeyStr", "Size = " + keysList.size());
                        JSONObject shippingMethod = shippingObj.optJSONObject(keysList.get(i));
                        shippingMethodList.add(new ShippingMethod(shippingMethod.optString("code")
                                , shippingMethod.optString("cost")
                                , shippingMethod.optString("tax_class_id")
                                , shippingMethod.optString("text")
                                , shippingMethod.optString("title"))
                        );
                        radioGroupShippingMethod.removeAllViews();
                        RadioButton radioButton = new RadioButton(getActivity());
                        radioButton.setText(shippingMethodList.get(i).getTitle()
                                + " - " + shippingMethodList.get(i).getText());
                        radioButton.setId(i);
                        RadioGroup.LayoutParams rgParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                                RadioGroup.LayoutParams.WRAP_CONTENT);
                        radioGroupShippingMethod.addView(radioButton, rgParams);
                    }
                } else if (requestCode == PAYMENT_METHOD_REQUEST_CODE) {
                    JSONArray paymentMethods = response.optJSONArray("paymentMethod");
                    List<ShippingMethod> paymentMethodList = new ArrayList<>();
                    List<String> keysList = new ArrayList<>();
                    radioGroupPaymentMethod.removeAllViews();
                    for (int i = 0; i < paymentMethods.length(); i++) {
                        JSONObject paymentObj = paymentMethods.optJSONObject(i);
                        Iterator<?> keys = paymentObj.keys();

                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            keysList.add(key);
                            Log.e("KeyStr", key);
                        }
                        Log.e("KeyStr", "Size = " + keysList.size());
                        JSONObject shippingMethod = paymentObj.optJSONObject(keysList.get(i));
                        paymentMethodList.add(new ShippingMethod(shippingMethod.optString("code")
                                , shippingMethod.optString("cost")
                                , shippingMethod.optString("tax_class_id")
                                , shippingMethod.optString("text")
                                , shippingMethod.optString("title"))
                        );
                        radioGroupPaymentMethod.removeAllViews();
                        RadioButton radioButton = new RadioButton(getActivity());
                        radioButton.setText(paymentMethodList.get(i).getTitle()
                                + " - " + paymentMethodList.get(i).getText());
                        radioButton.setId(i);
                        RadioGroup.LayoutParams rgParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                                RadioGroup.LayoutParams.WRAP_CONTENT);
                        radioGroupPaymentMethod.addView(radioButton, rgParams);
                    }

                }
            } else if (resultCode == FORCED_CANCEL) {
                String message = response.optString("message");
                if (!message.isEmpty()) {
                    utils.showAlertDialog("Message", message);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.showErrorDialog("Unable to Get Data From Server");
            }
        } else utils.showErrorDialog("Response is null");
    }
}
