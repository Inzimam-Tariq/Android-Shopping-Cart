package com.qemasoft.alhabibshop.app.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.AddressBookAdapter;
import com.qemasoft.alhabibshop.app.model.Address;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.ADDRESS_BOOK_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_ID_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.FORCE_CANCELED;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddressBook extends MyBaseFragment {
    
    private ImageView addAddressIcon;
    
    public AddressBook() {
        // Required empty public constructor
    }
    
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_address_book, container, false);
        initUtils();
        initViews(view);
        
        requestData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            utils.showToast(R.string.edit_address_context, 1);
        }
        
        addAddressIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.switchFragment(new AddAddress());
            }
        });
        
        return view;
    }
    
    private void initViews(View view) {
        addAddressIcon = view.findViewById(R.id.add_address_icon);
        mRecyclerView = view.findViewById(R.id.addresses_recycler_view);
        
    }
    
    private void requestData() {
        
        AppConstants.setMidFixApi("getAddresses");
        
        Map<String, String> map = new HashMap<>();
        map.put("customer_id", Preferences.getSharedPreferenceString(appContext,
                CUSTOMER_ID_KEY, DEFAULT_STRING_VAL));
        
        Bundle bundle = new Bundle();
        bundle.putBoolean("hasParameters", true);
        bundle.putSerializable("parameters", (Serializable) map);
        Intent intent = new Intent(getContext(), FetchData.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, ADDRESS_BOOK_REQUEST_CODE);
        
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
                    if (addresses == null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                            utils.showToast(R.string.add_address_context);
                        else {
                            utils.showAlert(R.string.information_text, R.string.add_address_context,
                                    false,
                                    R.string.ok, null,
                                    R.string.cancel_text, null);
                        }
                        return;
                    }
                    List<Address> addressList = new ArrayList<>();
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
                    }
                    if (!addressList.isEmpty() && addressList.size() > 0) {
                        RecyclerView.LayoutManager mLayoutManagerOptions =
                                new LinearLayoutManager(getActivity()
                                        , LinearLayoutManager.VERTICAL, false);
                        mRecyclerView.setLayoutManager(mLayoutManagerOptions);
                        mRecyclerView.setAdapter(new AddressBookAdapter(addressList));
                    }
                }
            } else if (resultCode == FORCE_CANCELED) {
                String message = response.optString("message");
                if (!message.isEmpty()) {
                    utils.printLog("AddressBook", "Force close if");
                    utils.showAlert(R.string.information_text, message,
                            false,
                            R.string.ok, null,
                            R.string.cancel_text, null);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.printLog("AddressBook", "Result Cancel");
                utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                        false,
                        R.string.ok, null,
                        R.string.cancel_text, null);
            }
        }
    }
}
