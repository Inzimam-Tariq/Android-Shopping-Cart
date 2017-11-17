package com.qemasoft.alhabibshop.app.view.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.view.activities.MainActivity;

import org.json.JSONObject;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.qemasoft.alhabibshop.app.AppConstants.getApiCallUrl;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragEditAddress extends MyBaseFragment {

    private EditText fName, lName, companyName, address1, address2, postalCode, country, State;
    private Button continueBtn;

    public FragEditAddress() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_register, container, false);
        initUtils();
        initViews(view);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fNameVal = fName.getText().toString().trim();
                String lNameVal = lName.getText().toString().trim();
                String companyVal = companyName.getText().toString().trim();
                String address1val = address1.getText().toString().trim();
                String address2Val = address2.getText().toString().trim();
                String postalCodeVal = postalCode.getText().toString().trim();
                String cityVal = postalCode.getText().toString().trim();
                String countryVal = postalCode.getText().toString().trim();
                String stateVal = postalCode.getText().toString().trim();


                if (fNameVal.length() > 0 && lNameVal.length() > 0 && cityVal.length() > 0
                        && address1val.length() > 0 && countryVal.length() > 0
                        && stateVal.length() > 0) {
                    Log.e("InsideLoginClicked = ", "Inside if");
                    if (utils.isNetworkConnected()) {
                        Log.e("InsideLoginClicked = ", "isNetwork");
                        AppConstants.setMidFixApi("register");
                        Log.e("RegisterUrl = ", getApiCallUrl());
                        AndroidNetworking.post(getApiCallUrl())
                                .addBodyParameter("firstname", fNameVal)
                                .addBodyParameter("lastname", lNameVal)
                                .addBodyParameter("email", companyVal)
                                .addBodyParameter("telephone", address1val)
                                .addBodyParameter("password", address2Val)
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        boolean success = response.optBoolean("success");
                                        if (success) {
                                            String userData = response.optString("userdata");
                                            AlertDialog dialog = utils.showAlertDialogReturnDialog(
                                                    "Confirmation Message!", "Account Info Changed Successfully");
                                            dialog.setButton(BUTTON_POSITIVE, "OK",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            ((MainActivity) getActivity()).changeFragment(0);
                                                        }
                                                    });
                                            dialog.setButton(BUTTON_NEGATIVE, "Cancel",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                        }
                                                    });

                                            dialog.show();

                                        } else {
                                            utils.showErrorDialog("Success False");
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        anError.printStackTrace();
                                        utils.showErrorDialog("Error Getting Data From Server");
                                        Toast.makeText(context, "ErrorGettingDataFromServer", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        utils.showInternetErrorDialog();
                    }
                } else {
                    // set empty field error message
                    utils.showErrorDialog("Some Fields are Empty");
                }
            }
        });

        return view;
    }


    private void initViews(View view) {
        fName = view.findViewById(R.id.f_name_et);
        lName = view.findViewById(R.id.l_name_et);
        companyName = view.findViewById(R.id.company_et);
        address1 = view.findViewById(R.id.address1_et);
        address2 = view.findViewById(R.id.address2_et);
        postalCode = view.findViewById(R.id.post_code_et);

        continueBtn = view.findViewById(R.id.continue_btn);
    }

}
