package com.qemasoft.alhabibshop.app.view.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.ADD_ADDRESS_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.COUNTRIES_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.RIGHT;
import static com.qemasoft.alhabibshop.app.AppConstants.STATES_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAddress extends MyBaseFragment implements View.OnClickListener {

    private String countryId, zoneId;
    private EditText fNameET, lNameET, companyNameET, addressET, postalCodeET, cityET;
    private Button continueBtn, countryBtn, stateBtn;
    private RadioGroup radioGroup;
    private RadioButton rbYes, rbNo;
    private List<String> countryList, countryIdList, stateList, stateIdList;

    public AddAddress() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_add_address, container, false);
        initUtils();
        initViews(view);
//        Toast.makeText(context,"Click and hold to Edit or Delete Address",
//                Toast.LENGTH_LONG).show();

        radioGroup.check(R.id.rbNo);
        countryBtn.setOnClickListener(this);
        stateBtn.setOnClickListener(this);
        getCountries();
        continueBtn.setOnClickListener(this);

        return view;
    }

    private void getCountries() {
        AppConstants.setMidFixApi("getCountries");
        Intent intent = new Intent(getContext(), FetchData.class);
        startActivityForResult(intent, COUNTRIES_REQUEST_CODE);
    }

    private void getStates(String countryId) {
        AppConstants.setMidFixApi("getZones");
        Map<String, String> map = new HashMap<>();
        map.put("country_id", countryId);
        Bundle bundle = new Bundle();
        bundle.putBoolean("hasParameters", true);
        bundle.putSerializable("parameters", (Serializable) map);
        Intent intent = new Intent(getContext(), FetchData.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, STATES_REQUEST_CODE);
    }


    private void initViews(View view) {
        fNameET = view.findViewById(R.id.f_name_et);
        lNameET = view.findViewById(R.id.l_name_et);
        companyNameET = view.findViewById(R.id.company_et);
        addressET = view.findViewById(R.id.address_et);
        cityET = view.findViewById(R.id.city_et);
        postalCodeET = view.findViewById(R.id.post_code_et);
        countryBtn = view.findViewById(R.id.country_btn);
        stateBtn = view.findViewById(R.id.state_btn);
        radioGroup = view.findViewById(R.id.radio_group);
        rbYes = view.findViewById(R.id.rbYes);
        rbNo = view.findViewById(R.id.rbNo);

        continueBtn = view.findViewById(R.id.continue_btn);

        utils.setCompoundDrawable(stateBtn, RIGHT, R.drawable.ic_expand_more_black);
        utils.setCompoundDrawable(countryBtn, RIGHT, R.drawable.ic_expand_more_black);
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
                if (requestCode == ADD_ADDRESS_REQUEST_CODE) {
                    utils.switchFragment(new AddressBook());
                } else if (requestCode == COUNTRIES_REQUEST_CODE) {
                    try {
                        JSONArray countries = response.optJSONArray("countries");
                        countryList = new ArrayList<>();
                        countryIdList = new ArrayList<>();
                        for (int i = 0; i < countries.length(); i++) {
                            JSONObject country = countries.optJSONObject(i);
                            countryList.add(country.getString("name"));
                            countryIdList.add(country.getString("country_id"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (requestCode == STATES_REQUEST_CODE) {
                    try {
                        JSONArray states = response.optJSONArray("Zones");
                        stateList = new ArrayList<>();
                        stateIdList = new ArrayList<>();
                        if (states != null) {
                            for (int i = 0; i < states.length(); i++) {
                                JSONObject country = states.optJSONObject(i);
                                stateList.add(country.getString("name"));
                                stateIdList.add(country.getString("zone_id"));
                            }
                        } else utils.showErrorDialog("No State Available");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == AppConstants.FORCED_CANCEL) {
                String message = response.optString("message");
                if (!message.isEmpty()) {
                    utils.showAlertDialog("Message", message);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.showErrorDialog("Unable to Get Data From Server");
            }
        } else utils.showErrorDialog("Response is null");
    }

    @Override
    public void onClick(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog;

        switch (v.getId()) {
            case R.id.country_btn:
                builder.setTitle("Select Country");
                builder.setSingleChoiceItems(countryList.toArray(new String[countryList.size()]),
                        -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                utils.printLog("CountryId", countryIdList.get(which));
                                countryBtn.setHint(countryList.get(which));
                                getStates(countryIdList.get(which));
                                countryId = countryIdList.get(which);
                                dialog.dismiss();
                                countryBtn.setHintTextColor(getResources()
                                        .getColor(R.color.text_color));
                            }
                        });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.state_btn:
                builder.setTitle("Select State");
                builder.setSingleChoiceItems(stateList.toArray(new String[stateList.size()]),
                        -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                stateBtn.setHint(stateList.get(which));
                                dialog.dismiss();
                                zoneId = stateIdList.get(which);
                                stateBtn.setHintTextColor(getResources().getColor(R.color.text_color));
                            }
                        });
                dialog = builder.create();
                dialog.show();
                break;
            case R.id.continue_btn:
                utils.printLog("ContinueButtonClicked", "Success");
                String fNameVal = fNameET.getText().toString().trim();
                String lNameVal = lNameET.getText().toString().trim();
                String companyVal = companyNameET.getText().toString().trim();
                String addressVal = addressET.getText().toString().trim();
                String postalCodeVal = postalCodeET.getText().toString().trim();
                String cityVal = cityET.getText().toString().trim();
                String countryVal = countryBtn.getHint().toString().trim();
                String stateVal = stateBtn.getHint().toString().trim();
                utils.printLog("CountryVal", countryVal + "CVal");
                if (fNameVal.isEmpty()) {
                    utils.setError(fNameET);
                } else if (lNameVal.isEmpty()) {
                    utils.setError(lNameET);
                } else if (addressVal.isEmpty()) {
                    utils.setError(addressET);
                    ;
                } else if (cityVal.isEmpty()) {
                    utils.setError(cityET);
                } else if (countryVal.isEmpty() || countryVal.contains("select")) {
                    countryBtn.setError("Required!");
                } else if (stateVal.isEmpty()) {
                    stateBtn.setError("Required!");
                } else {
                    AppConstants.setMidFixApi("addAddress");
                    Map<String, String> map = new HashMap<>();
                    map.put("customer_id", Preferences.getSharedPreferenceString(appContext,
                            CUSTOMER_KEY, DEFAULT_STRING_VAL));
                    map.put("firstname", fNameVal);
                    map.put("lastname", lNameVal);
                    map.put("company", companyVal);
                    map.put("address_1", addressVal);
                    map.put("city", cityVal);
                    map.put("postcode", postalCodeVal);
                    map.put("country_id", countryId);
                    map.put("zone_id", zoneId);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, ADD_ADDRESS_REQUEST_CODE);
                }
                break;
        }
    }
}